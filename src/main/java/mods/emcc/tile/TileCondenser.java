package mods.emcc.tile;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.core.tile.*;
import mods.emcc.*;
import mods.emcc.gui.*;
import mods.emcc.item.*;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.ForgeDirection;

public class TileCondenser extends TileEMCC implements IGuiTile, ISidedInventory
{
	public static final int UP_SLOT = 0;
	public static final int[] UP_SLOTS = { UP_SLOT };
	public static final int[] SIDE_SLOTS = new int[45];
	
	static
	{
		for(int i = 0; i < SIDE_SLOTS.length; i++)
			SIDE_SLOTS[i] = i + 1;
	}
	
	public boolean safeMode = false;
	public double storedEMC = 0D;
	public int redstoneMode = 0;
	public ItemStack[] items = null;
	public int cooldown = 0;
	
	public TileCondenser()
	{
		items = new ItemStack[46];
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		ItemStack heldItem = null;
		if(!worldObj.isRemote && security.level == LMSecurity.RESTRICTED && (heldItem = ep.getHeldItem()) != null && heldItem.itemID == Item.nameTag.itemID && heldItem.hasDisplayName())
		{
			if(security.owner.equals(ep.username))
			{
				String s = heldItem.getDisplayName();
				
				if(!security.friends.contains(s))
				{
					security.friends.add(s);
					LatCore.printChat(ep, "Added '" + s + "' to friends list!");
					isDirty = true;
				}
				else LatCore.printChat(ep, "'" + s + "' already added!");
			}
		}
		else openGui(ep, 0);
		return true;
	}
	
	public void onUpdate()
	{
		if(!worldObj.isRemote)
		{
			if(cooldown <= 0)
			{
				cooldown = EMCCConfig.condenserSleepDelay;
				
				if(EMCCConfig.enableRedstoneControl)
				{
					if(redstoneMode > 0)
					{
						boolean b = isPowered(true);
						if(redstoneMode == 1 && !b) return;
						if(redstoneMode == 2 && b) return;
					}
				}
				else
				{
					if(redstoneMode > 0)
					{
						redstoneMode = 0;
						isDirty = true;
					}
				}
				
				int limit = EMCCConfig.condenserLimitPerTick;
				if(limit == -1) limit = items.length * 64;
				
				for(int i = 0; i < SIDE_SLOTS.length; i++)
				{
					ItemStack is = items[SIDE_SLOTS[i]];
					if(is != null && is.stackSize > 0)
					{
						if(EMCCConfig.enableBattery && is.itemID == EMCC.i_battery.itemID)
						{
							if(is.hasTagCompound() && is.stackTagCompound.hasKey(ItemBattery.NBT_VAL))
							{
								double ev = is.stackTagCompound.getDouble(ItemBattery.NBT_VAL);
								
								storedEMC += ev;
								is.stackTagCompound.removeTag(ItemBattery.NBT_VAL);
								isDirty = true;
							}
						}
						else
						{
							float iev = EMCC.getEMC(is);
							
							if(iev > 0F && !EMCC.blacklist.isBlacklistedFuel(is))
							{
								if(safeMode && EMCCConfig.enableSafeMode)
								{
									if(is.stackSize > 1)
									{
										storedEMC += iev * (is.stackSize - 1);
										items[SIDE_SLOTS[i]].stackSize = 1;
										isDirty = true;
									}
								}
								else
								{
									storedEMC += iev * is.stackSize;
									items[SIDE_SLOTS[i]] = null;
									isDirty = true;
								}
							}
						}
					}
				}
			}
			
			if(!worldObj.isRemote && storedEMC > 0D && items[UP_SLOT] != null)
			{
				if(EMCCConfig.enableBattery && items[UP_SLOT].itemID == EMCC.i_battery.itemID)
				{
					if(storedEMC > 0D)
					{
						NBTTagCompound tag = items[UP_SLOT].stackTagCompound;
						if(tag == null) tag = new NBTTagCompound();
						
						double ev = tag.hasKey(ItemBattery.NBT_VAL) ? tag.getDouble(ItemBattery.NBT_VAL) : 0D;
						
						tag.setDouble(ItemBattery.NBT_VAL, ev + storedEMC);
						storedEMC = 0D;
						items[UP_SLOT].setTagCompound(tag);
						isDirty = true;
					}
				}
				else
				{
					double ev = EMCC.getEMC(items[UP_SLOT]);
					
					if(ev > 0D && !EMCC.blacklist.isBlacklistedTarget(items[UP_SLOT]))
					{
						double d1 = (storedEMC == Double.POSITIVE_INFINITY) ? (45D * 64D) : (long)(storedEMC / ev);
						
						if(d1 > 0D)
						{
							for(double d = 0D; d < d1; d++)
							{
								if(addSingleItemToContainer(InvUtils.singleCopy(items[UP_SLOT])))
								{
									storedEMC -= ev;
								}
							}
							
							isDirty = true;
						}
					}
				}
			}
			else
			{
				cooldown--;
			}
		}
	}
	
	public boolean addSingleItemToContainer(ItemStack is)
	{
		if(is == null) return false;
		
		for(int i = 0; i < SIDE_SLOTS.length; i++)
		{
			ItemStack is1 = items[SIDE_SLOTS[i]];
			if(is1 != null && is1.stackSize > 0 && InvUtils.itemsEquals(is, is1, false, true))
			{
				if(is1.stackSize + 1 <= is1.getMaxStackSize())
				{
					items[SIDE_SLOTS[i]].stackSize++;
					isDirty = true;
					return true;
				}
			}
		}
		
		for(int i = 0; i < SIDE_SLOTS.length; i++)
		{
			ItemStack is1 = items[SIDE_SLOTS[i]];
			if(is1 == null || is1.stackSize == 0)
			{
				items[SIDE_SLOTS[i]] = InvUtils.singleCopy(is);
				isDirty = true;
				return true;
			}
		}
		
		return false;
	}
	
	public void onBroken()
	{
		isDirty = true;
		InvUtils.dropAllItems(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, items);
		items = new ItemStack[items.length];
		isDirty = true;
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		items = InvUtils.readItemsFromNBT(items.length, tag, "Items");
		storedEMC = tag.getDouble("StoredEMC");
		safeMode = tag.getBoolean("SafeMode");
		redstoneMode = tag.getByte("RSMode");
		cooldown = tag.getShort("Cooldown");
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		InvUtils.writeItemsToNBT(items, tag, "Items");
		tag.setDouble("StoredEMC", storedEMC);
		tag.setBoolean("SafeMode", safeMode);
		tag.setByte("RSMode", (byte)redstoneMode);
		tag.setShort("Cooldown", (short)cooldown);
	}
	
	@Override
	public Container getContainer(EntityPlayer ep, int ID)
	{ return new ContainerCondenser(ep, this); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(EntityPlayer ep, int ID)
	{ return new GuiCondenser(new ContainerCondenser(ep, this)); }
	
	public void handleGuiButton(int i)
	{
		if(LatCore.canUpdate() && !worldObj.isRemote)
		{
			if(i == 0) toggleSafeMode(true);
			else if(i == 1) clearBuffer(true);
			else if(i == 2) toggleRedstoneMode(true);
			else if(i == 3) toggleSecurity(true);
		}
	}
	
	public void toggleSafeMode(boolean serverSide)
	{
		if(serverSide) { safeMode = !safeMode; isDirty = true; }
		else EMCCNetHandler.sendToServer(this, 0);
	}
	
	public void clearBuffer(boolean serverSide)
	{
		if(serverSide) { storedEMC = 0; isDirty = true; }
		else EMCCNetHandler.sendToServer(this, 1);
	}
	
	public void toggleRedstoneMode(boolean serverSide)
	{
		if(serverSide) { redstoneMode = (redstoneMode + 1) % 3; isDirty = true; }
		else EMCCNetHandler.sendToServer(this, 2);
	}
	
	public void toggleSecurity(boolean serverSide)
	{
		if(serverSide)
		{
			if(serverSide) { security.level = (security.level + 1) % 3; isDirty = true; }
			isDirty = true;
		}
		else EMCCNetHandler.sendToServer(this, 3);
	}

	@Override
	public int getSizeInventory()
	{ return items.length; }

	@Override
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{ return InvUtils.decrStackSize(this, slot, amt); }

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{ return InvUtils.getStackInSlotOnClosing(this, i); }
	
	@Override
	public void setInventorySlotContents(int i, ItemStack is)
	{ items[i] = is; isDirty = true; }
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return EMCCConfig.isCondenserIInventory == 0 ? NO_SLOTS : ((s == UP) ? UP_SLOTS : SIDE_SLOTS); }
	
	@Override
	public boolean canInsertItem(int i, ItemStack is, int j)
	{ return EMCCConfig.isCondenserIInventory == 2 || EMCCConfig.isCondenserIInventory == 3; }
	
	@Override
	public boolean canExtractItem(int i, ItemStack is, int j)
	{ return EMCCConfig.isCondenserIInventory == 1 || EMCCConfig.isCondenserIInventory == 3; }
	
	@Override
	public String getInvName()
	{ return "Condenser"; }
	
	@Override
	public boolean isInvNameLocalized()
	{ return false; }
	
	@Override
	public int getInventoryStackLimit()
	{ return 64; }
	
	@Override
	public void openChest()
	{
	}
	
	@Override
	public void closeChest()
	{
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return true; }
}