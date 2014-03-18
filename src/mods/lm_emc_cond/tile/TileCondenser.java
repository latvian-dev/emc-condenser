package mods.lm_emc_cond.tile;
import cpw.mods.fml.relauncher.*;
import mods.lm_core.*;
import mods.lm_emc_cond.Alchemy;
import mods.lm_emc_cond.ev.*;
import mods.lm_emc_cond.gui.*;
import mods.lm_emc_cond.net.PacketGuiButton;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.ForgeDirection;

public class TileCondenser extends TileAlchemy implements IGuiTile, ISidedInventory
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
	
	public TileCondenser()
	{
		items = new ItemStack[46];
	}
	
	public void onUpdate()
	{
		if(!worldObj.isRemote && tick % 3 == 0)
		{
			for(int i = 0; i < SIDE_SLOTS.length; i++)
			{
				ItemStack is = items[SIDE_SLOTS[i]];
				if(is != null && is.stackSize > 0 && canCondense(is))
				{
					if(is.itemID == Alchemy.i_battery.itemID)
					{
						if(is.hasTagCompound() && is.stackTagCompound.hasKey("StoredEV"))
						{
							double ev = is.stackTagCompound.getDouble("StoredEV");
							
							if(ev == Double.POSITIVE_INFINITY)
							{
								if(storedEMC != Double.POSITIVE_INFINITY)
									isDirty = true;
								storedEMC = Double.POSITIVE_INFINITY;
							}
							else
							{
								storedEMC += ev;
								is.stackTagCompound.removeTag("StoredEV");
							}
						}
					}
					else
					{
						float iev = EnergyValues.inst.getValue(is);
						
						if(iev > 0F)
						{
							if(safeMode)
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
		
		if(!worldObj.isRemote && storedEMC > 0D && items[UP_SLOT] != null && canMakeProduct())
		{
			if(items[UP_SLOT].itemID == Alchemy.i_battery.itemID)
			{
				if(storedEMC > 0D)
				{
					NBTTagCompound tag = items[UP_SLOT].stackTagCompound;
					if(tag == null) tag = new NBTTagCompound();
					
					double ev = tag.hasKey("StoredEV") ? tag.getDouble("StoredEV") : 0D;
					
					tag.setDouble("StoredEV", ev + storedEMC);
					storedEMC = 0D;
					items[UP_SLOT].setTagCompound(tag);
					isDirty = true;
				}
			}
			else
			{
				double ev = EnergyValues.inst.getValue(items[UP_SLOT]);
				
				if(ev > 0D)
				{
					double d1 = (storedEMC == Double.POSITIVE_INFINITY) ? (45D * 64D) : (long)(storedEMC / ev);
					
					if(d1 > 0D)
					{
						for(double d = 0D; d < d1; d++)
						{
							if(InvUtils.addItemToInv(this, InvUtils.single(items[UP_SLOT]), ForgeDirection.DOWN))
								storedEMC -= ev;
						}
					}
				}
			}
		}
	}
	
	public boolean canMakeProduct()
	{ return true; }
	
	public boolean canCondense(ItemStack is)
	{ return true; }
	
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
		storedEMC = tag.getDouble("StoredEMC");
		safeMode = tag.getBoolean("SafeMode");
		redstoneMode = tag.getByte("RSMode");
		items = InvUtils.readItemsFromNBT(items.length, tag, "Items");
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setDouble("StoredEMC", storedEMC);
		tag.setBoolean("SafeMode", safeMode);
		tag.setByte("RSMode", (byte)redstoneMode);
		InvUtils.writeItemsToNBT(items, tag, "Items");
	}
	
	@Override
	public Container getContainer(EntityPlayer ep, int ID)
	{ return new ContainerCondenser(ep, this); }
	
	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGui(EntityPlayer ep, int ID)
	{ return new GuiCondenser(new ContainerCondenser(ep, this)); }
	
	public void handleGuiButton(int i)
	{
		if(LatCore.canUpdate() && !worldObj.isRemote)
		{
			if(i == 0) toggleSafeMode(true);
			else if(i == 1) clearBuffer(true);
			else if(i == 2) toggleRedstoneMode(true);
		}
	}
	
	public void toggleSafeMode(boolean serverSide)
	{
		if(serverSide) { safeMode = !safeMode; isDirty = true; }
		else new PacketGuiButton(0).sendToServer(this);
	}
	
	public void clearBuffer(boolean serverSide)
	{
		if(serverSide) { storedEMC = 0; isDirty = true; }
		else new PacketGuiButton(1).sendToServer(this);
	}
	
	public void toggleRedstoneMode(boolean serverSide)
	{
		if(serverSide) { redstoneMode = (redstoneMode + 1) % 3; isDirty = true; }
		else new PacketGuiButton(2).sendToServer(this);
	}

	@Override
	public int getSizeInventory()
	{ return items.length; }

	@Override
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		
		if (stack != null)
		{
			if (stack.stackSize <= amt) setInventorySlotContents(slot, null);
			else
			{
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0)
				setInventorySlotContents(slot, null);
			}
		}
		
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		ItemStack is = getStackInSlot(i);
		if(is != null) setInventorySlotContents(i, null);
		return is;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{ items[i] = itemstack; }
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return (s == UP) ? UP_SLOTS : SIDE_SLOTS; }

	@Override
	public boolean canInsertItem(int i, ItemStack is, int j)
	{ return true; }

	@Override
	public boolean canExtractItem(int i, ItemStack is, int j)
	{ return true; }

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
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{ return true; }
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return security.canPlayerInteract(ep); } // && ep.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D
}