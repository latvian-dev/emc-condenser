package latmod.emcc.tile;
import latmod.core.*;
import latmod.core.mod.LCGuis;
import latmod.core.mod.tile.*;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileCondenser extends TileLM implements ISidedInventory, IEmcWrenchable, IClientActionTile
{
	public static final String ACTION_TRANS_ITEMS = "transItems";
	
	public static final int SLOT_TARGET = 0;
	public static final int[] TARGET_SLOTS = { SLOT_TARGET };
	public static final int[] INPUT_SLOTS = new int[36];
	public static final int[] OUTPUT_SLOTS = new int[18];
	public static final int SLOT_COUNT = INPUT_SLOTS.length + OUTPUT_SLOTS.length + 1;
	
	static
	{
		for(int i = 0; i < INPUT_SLOTS.length; i++)
			INPUT_SLOTS[i] = i + 1;
		
		for(int i = 0; i < OUTPUT_SLOTS.length; i++)
			OUTPUT_SLOTS[i] = INPUT_SLOTS.length + 1 + i;
	}
	
	public double storedEMC = 0D;
	public int cooldown = 0;
	public SafeMode safeMode;
	public RedstoneMode redstoneMode;
	public InvMode invMode;
	public RepairTools repairTools;
	
	public TileCondenser()
	{
		items = new ItemStack[SLOT_COUNT];
		safeMode = SafeMode.DISABLED;
		redstoneMode = RedstoneMode.DISABLED;
		invMode = InvMode.ENABLED;
		repairTools = RepairTools.DISABLED;
		checkForced();
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(!worldObj.isRemote)
			openGui(EMCCGuis.CONDENSER, ep);
		return true;
	}
	
	public void onUpdate()
	{
		if(!worldObj.isRemote)
		{
			if(cooldown <= 0)
			{
				cooldown = EMCC.config.condenser.condenserSleepDelay;
				
				if(redstoneMode != RedstoneMode.DISABLED && redstoneMode.cancel(redstonePowered))
					return;
				
				int limit = EMCC.config.condenser.condenserLimitPerTick;
				if(limit == -1) limit = INPUT_SLOTS.length * 64;
				
				for(int i = 0; i < INPUT_SLOTS.length; i++)
				{
					if(items[INPUT_SLOTS[i]] != null && items[INPUT_SLOTS[i]].stackSize > 0)
					{
						if(items[INPUT_SLOTS[i]].getItem() instanceof IEmcStorageItem)
						{
							IEmcStorageItem storageItem = (IEmcStorageItem)items[INPUT_SLOTS[i]].getItem();
							
							double ev = storageItem.getStoredEmc(items[INPUT_SLOTS[i]]);
							
							storedEMC += ev;
							storageItem.setStoredEmc(items[INPUT_SLOTS[i]], 0D);
							markDirty();
							continue;
						}
						
						double iev = EMCC.getEMC(items[INPUT_SLOTS[i]]);
						
						if(iev > 0D && !EMCC.blacklist.isBlacklistedFuel(items[INPUT_SLOTS[i]]))
						{
							if(safeMode.isOn() && items[INPUT_SLOTS[i]].stackSize == 1) continue;
							
							int s = Math.min((safeMode.isOn() && items[INPUT_SLOTS[i]].stackSize > 1) ? (items[INPUT_SLOTS[i]].stackSize - 1) : items[INPUT_SLOTS[i]].stackSize, limit);
							
							if(s <= 0) continue;
							
							limit -= s;
							storedEMC += iev * s;
							items[INPUT_SLOTS[i]].stackSize -= s;
							if(items[INPUT_SLOTS[i]].stackSize <= 0)
								items[INPUT_SLOTS[i]] = null;
							markDirty();
						}
					}
					
					if(limit <= 0) break;
				}
				
				if(storedEMC > 0D && items[SLOT_TARGET] != null)
				{
					ItemStack tar = items[SLOT_TARGET];
					
					if(tar.getItem() instanceof IEmcStorageItem)
					{
						IEmcStorageItem storageItem = (IEmcStorageItem)tar.getItem();
						
						if(storageItem.canChargeEmc(tar))
						{
							double ev = storageItem.getStoredEmc(tar);
							double a = Math.min(storedEMC, storageItem.getEmcTrasferLimit(tar));
							storageItem.setStoredEmc(items[SLOT_TARGET], ev + a);
							storedEMC -= a;
							markDirty();
						}
					}
					else if(repairTools.isOn() && tar.isItemStackDamageable() && !tar.isStackable())
					{
						if(tar.getItemDamage() > 0)
						{
							ItemStack tar1 = tar.copy();
							if(tar1.hasTagCompound())
								tar1.stackTagCompound.removeTag("ench");
							
							ItemStack tar2 = tar1.copy();
							tar2.setItemDamage(tar1.getItemDamage() - 1);
							
							double ev = EMCC.getEMC(tar1);
							double ev2 = EMCC.getEMC(tar2);
							
							double a = ev2 - ev;
							
							if(storedEMC >= a && a > 0D)
							{
								storedEMC -= a;
								items[SLOT_TARGET].setItemDamage(tar2.getItemDamage());
								markDirty();
							}
						}
					}
					else
					{
						double ev = EMCC.getEMC(tar);
						
						if(ev > 0D && !EMCC.blacklist.isBlacklistedTarget(tar))
						{
							long d1 = (long)(storedEMC / ev);
							
							if(d1 > 0L)
							{
								for(long d = 0L; d < d1; d++)
								{
									if(InvUtils.addSingleItemToInv(InvUtils.singleCopy(tar), this, OUTPUT_SLOTS, -1, true))
									{
										storedEMC -= ev;
										markDirty();
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
	
	protected boolean customNBT()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		storedEMC = tag.getDouble("StoredEMC");
		safeMode = SafeMode.VALUES[tag.getByte("SafeMode")];
		redstoneMode = RedstoneMode.VALUES[tag.getByte("RSMode")];
		invMode = InvMode.VALUES[tag.getByte("InvMode")];
		repairTools = RepairTools.VALUES[tag.getByte("RepairTools")];
		cooldown = tag.getShort("Cooldown");
		
		checkForced();
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		
		tag.setDouble("StoredEMC", storedEMC);
		tag.setByte("SafeMode", (byte)safeMode.ID);
		tag.setByte("RSMode", (byte)redstoneMode.ID);
		tag.setByte("InvMode", (byte)invMode.ID);
		tag.setByte("RepairTools", (byte)repairTools.ID);
		tag.setShort("Cooldown", (short)cooldown);
	}
	
	public void checkForced()
	{
		if(EMCC.config.condenser.forcedRedstoneControl != null && redstoneMode.ID != EMCC.config.condenser.forcedRedstoneControl.ID)
		{ redstoneMode = EMCC.config.condenser.forcedRedstoneControl; markDirty(); }
		
		if(EMCC.config.condenser.forcedSecurity != null && security.level != EMCC.config.condenser.forcedSecurity)
		{ security.level = EMCC.config.condenser.forcedSecurity; markDirty(); }
		
		if(EMCC.config.condenser.forcedSafeMode != null && safeMode.ID != EMCC.config.condenser.forcedSafeMode.ID)
		{ safeMode = EMCC.config.condenser.forcedSafeMode; markDirty(); }
		
		if(EMCC.config.condenser.forcedInvMode != null && invMode.ID != EMCC.config.condenser.forcedInvMode.ID)
		{ invMode = EMCC.config.condenser.forcedInvMode; markDirty(); }
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return (invMode == InvMode.DISABLED) ? NO_SLOTS : ((s == LatCoreMC.BOTTOM) ? OUTPUT_SLOTS : INPUT_SLOTS); }
	
	@Override
	public boolean canInsertItem(int i, ItemStack is, int j)
	{ return invMode.canInsertItem() && anyEquals(i, INPUT_SLOTS); }
	
	@Override
	public boolean canExtractItem(int i, ItemStack is, int j)
	{ return invMode.canExtractItem() && anyEquals(i, OUTPUT_SLOTS); }
	
	private static boolean anyEquals(int s, int[] slots)
	{
		for(int i = 0; i < slots.length; i++)
		{ if(s == slots[i]) return true; }
		return false;
	}
	
	@Override
	public String getInventoryName()
	{ return "Condenser"; }
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return true; }
	
	public boolean canWrench(EntityPlayer ep)
	{ return security.canInteract(ep); }
	
	public void readFromWrench(NBTTagCompound tag)
	{ readTileData(tag); }
	
	public void writeToWrench(NBTTagCompound tag)
	{ writeTileData(tag); }
	
	public void onWrenched(EntityPlayer ep, ItemStack is)
	{
		dropItems = false;
	}
	
	public ItemStack getBlockToPlace()
	{ return EMCCItems.CONDENSER; }
	
	public void handleButton(String button, int mouseButton, EntityPlayer ep)
	{
		if(button.equals(EMCCGuis.Buttons.SAFE_MODE))
			safeMode = safeMode.next();
		else if(button.equals(LCGuis.Buttons.REDSTONE))
			redstoneMode = (mouseButton == 0) ? redstoneMode.next() : redstoneMode.prev();
		else if(button.equals(LCGuis.Buttons.INV_MODE))
			invMode = (mouseButton == 0) ? invMode.next() : invMode.prev();
		else if(button.equals(EMCCGuis.Buttons.REPAIR_TOOLS))
			repairTools = repairTools.next();
		else if(button.equals(LCGuis.Buttons.SECURITY))
		{
			if(ep != null && security.isOwner(ep))
				security.level = security.level.next();
			else printOwner(ep);
		}
		
		checkForced();
		markDirty();
	}
	
	public void openGui(int guiID, EntityPlayer ep)
	{
		if(security.canInteract(ep))
			ep.openGui(EMCC.inst, guiID, worldObj, xCoord, yCoord, zCoord);
		else printOwner(ep);
	}
	
	public void onClientAction(EntityPlayer ep, String action, NBTTagCompound data)
	{
		super.onClientAction(ep, action, data);
		
		if(action.equals(ACTION_TRANS_ITEMS))
		{
			int[] invSlots = InvUtils.getPlayerSlots(ep);
			
			for(int i = 0; i < OUTPUT_SLOTS.length; i++)
			if(items[OUTPUT_SLOTS[i]] != null)
			{
				int ss = items[OUTPUT_SLOTS[i]].stackSize;
				
				for(int j = 0; j < ss; j++)
				{
					if(InvUtils.addSingleItemToInv(items[OUTPUT_SLOTS[i]].copy(), ep.inventory, invSlots, -1, true))
					{
						items[OUTPUT_SLOTS[i]].stackSize--;
						if(items[OUTPUT_SLOTS[i]].stackSize <= 0)
							items[OUTPUT_SLOTS[i]] = null;
						
						markDirty();
					}
				}
			}
		}
	}
}