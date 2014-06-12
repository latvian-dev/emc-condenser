package latmod.emcc.tile;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.core.tile.*;
import latmod.emcc.*;
import latmod.emcc.gui.*;
import latmod.emcc.item.*;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.ForgeDirection;

public class TileCondenser extends TileEMCC implements IGuiTile, ISidedInventory
{
	public static final int SLOT_TARGET = 45;
	public static final int[] TARGET_SLOTS = { SLOT_TARGET };
	public static final int[] CHEST_SLOTS = new int[45];
	
	static
	{
		for(int i = 0; i < CHEST_SLOTS.length; i++)
			CHEST_SLOTS[i] = i;
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
			else LatCore.printChat(ep, "You are not the owner of this Condenser!");
		}
		else
		{
			if(security.canPlayerInteract(ep)) openGui(ep, 0);
			else if(!worldObj.isRemote) LatCore.printChat(ep, "Owner: " + security.owner);
		}
		return true;
	}
	
	public void onUpdate()
	{
		if(!worldObj.isRemote)
		{
			if(cooldown <= 0)
			{
				cooldown = EMCCConfig.condenserSleepDelay;
				
				checkForced();
				
				if(redstoneMode > 0)
				{
					boolean b = isPowered(true);
					if(redstoneMode == 1 && !b) return;
					if(redstoneMode == 2 && b) return;
				}
				
				int limit = EMCCConfig.condenserLimitPerTick;
				if(limit == -1) limit = items.length * 64;
				
				for(int i = 0; i < CHEST_SLOTS.length; i++)
				{
					if(items[CHEST_SLOTS[i]] != null && items[CHEST_SLOTS[i]].stackSize > 0)
					{
						if(EMCCConfig.enableBattery && items[CHEST_SLOTS[i]].itemID == EMCC.i_battery.itemID)
						{
							if(items[CHEST_SLOTS[i]].hasTagCompound() && items[CHEST_SLOTS[i]].stackTagCompound.hasKey(ItemBattery.NBT_VAL))
							{
								double ev = items[CHEST_SLOTS[i]].stackTagCompound.getDouble(ItemBattery.NBT_VAL);
								
								storedEMC += ev;
								items[CHEST_SLOTS[i]].stackTagCompound.removeTag(ItemBattery.NBT_VAL);
								isDirty = true;
								break;
							}
						}
						else
						{
							float iev = EMCC.getEMC(items[CHEST_SLOTS[i]]);
							
							if(iev > 0F && !EMCC.blacklist.isBlacklistedFuel(items[CHEST_SLOTS[i]]))
							{
								if(safeMode && items[CHEST_SLOTS[i]].stackSize == 1) continue;
								
								int s = Math.min((safeMode && items[CHEST_SLOTS[i]].stackSize > 1) ? (items[CHEST_SLOTS[i]].stackSize - 1) : items[CHEST_SLOTS[i]].stackSize, limit);
								
								if(s <= 0 || equalsTarget(items[CHEST_SLOTS[i]])) continue;
								
								limit -= s;
								storedEMC += iev * s;
								items[CHEST_SLOTS[i]].stackSize -= s;
								if(items[CHEST_SLOTS[i]].stackSize <= 0)
									items[CHEST_SLOTS[i]] = null;
								isDirty = true;
							}
						}
					}
					
					if(limit <= 0) break;
				}
				
				if(storedEMC > 0D && items[SLOT_TARGET] != null)
				{
					if(EMCCConfig.enableBattery && items[SLOT_TARGET].itemID == EMCC.i_battery.itemID)
					{
						if(storedEMC > 0D)
						{
							NBTTagCompound tag = items[SLOT_TARGET].stackTagCompound;
							if(tag == null) tag = new NBTTagCompound();
							
							double ev = tag.hasKey(ItemBattery.NBT_VAL) ? tag.getDouble(ItemBattery.NBT_VAL) : 0D;
							
							tag.setDouble(ItemBattery.NBT_VAL, ev + storedEMC);
							storedEMC = 0D;
							items[SLOT_TARGET].setTagCompound(tag);
							isDirty = true;
						}
					}
					else
					{
						double ev = EMCC.getEMC(items[SLOT_TARGET]);
						
						if(ev > 0D && !EMCC.blacklist.isBlacklistedTarget(items[SLOT_TARGET]))
						{
							long d1 = (long)(storedEMC / ev);
							
							if(d1 > 0L)
							{
								for(long d = 0L; d < d1; d++)
								{
									if(addSingleItemToContainer(InvUtils.singleCopy(items[SLOT_TARGET])))
									{
										storedEMC -= ev;
										isDirty = true;
									}
									else break;
								}
							}
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
	
	public boolean equalsTarget(ItemStack is)
	{
		if(is == null) return false;
		ItemStack is1 = items[SLOT_TARGET];
		if(is1 == null) return false;
		return is.itemID == is1.itemID && is.getItemDamage() == is1.getItemDamage() && ItemStack.areItemStackTagsEqual(is, is1);
	}
	
	public boolean addSingleItemToContainer(ItemStack is)
	{
		if(is == null) return false;
		
		for(int i = 0; i < CHEST_SLOTS.length; i++)
		{
			ItemStack is1 = items[CHEST_SLOTS[i]];
			if(is1 != null && is1.stackSize > 0 && InvUtils.itemsEquals(is, is1, false, true))
			{
				if(is1.stackSize + 1 <= is1.getMaxStackSize())
				{
					items[CHEST_SLOTS[i]].stackSize++;
					isDirty = true;
					return true;
				}
			}
		}
		
		for(int i = 0; i < CHEST_SLOTS.length; i++)
		{
			ItemStack is1 = items[CHEST_SLOTS[i]];
			if(is1 == null || is1.stackSize == 0)
			{
				items[CHEST_SLOTS[i]] = InvUtils.singleCopy(is);
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
	
	public void checkForced()
	{
		if(EMCCConfig.forcedRedstoneControl != -1 && redstoneMode != EMCCConfig.forcedRedstoneControl)
		{ isDirty = true; redstoneMode = EMCCConfig.forcedRedstoneControl; }
		
		if(EMCCConfig.forcedSecurity != -1 && security.level != EMCCConfig.forcedSecurity)
		{ isDirty = true; security.level = EMCCConfig.forcedSecurity; }
		
		if(EMCCConfig.forcedSafeMode != -1 && safeMode != (EMCCConfig.forcedSafeMode == 1))
		{ isDirty = true; safeMode = EMCCConfig.forcedSafeMode == 1; }
	}
	
	public void handleGuiButton(int i, EntityPlayer ep)
	{
		if(LatCore.canUpdate() && !worldObj.isRemote)
		{
			if(i == 0) toggleSafeMode(true);
			else if(i == 1) clearBuffer(true);
			else if(i == 2) toggleRedstoneMode(true);
			else if(i == 3) toggleSecurity(true, ep);
			
			checkForced();
		}
	}
	
	public void toggleSafeMode(boolean serverSide)
	{
		if(serverSide) { safeMode = !safeMode; isDirty = true; }
		else EMCCNetHandler.sendToServer(this, 0);
	}
	
	public void clearBuffer(boolean serverSide)
	{
		if(serverSide)
		{
			if(EMCCConfig.enableClearBuffer)
			{ storedEMC = 0; isDirty = true; }
		}
		else EMCCNetHandler.sendToServer(this, 1);
	}
	
	public void toggleRedstoneMode(boolean serverSide)
	{
		if(serverSide) { redstoneMode = (redstoneMode + 1) % 3; isDirty = true; }
		else EMCCNetHandler.sendToServer(this, 2);
	}
	
	public void toggleSecurity(boolean serverSide, EntityPlayer ep)
	{
		if(serverSide)
		{
			if(security.owner.equals(ep.username))
			{
				security.level = (security.level + 1) % 3;
				isDirty = true;
			}
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
	{ return EMCCConfig.isCondenserIInventory == 0 ? NO_SLOTS : ((s == UP) ? TARGET_SLOTS : CHEST_SLOTS); }
	
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