package latmod.emcc.tile;
import latmod.emcc.*;
import latmod.emcc.api.*;
import latmod.emcc.client.container.ContainerCondenser;
import latmod.emcc.client.gui.GuiCondenser;
import latmod.emcc.emc.EMCHandler;
import latmod.ftbu.core.*;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.tile.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class TileCondenser extends TileInvLM implements ISidedInventory, IEmcWrenchable, IClientActionTile, IGuiTile, ISecureTile
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
	
	public TileCondenser()
	{
		super(SLOT_COUNT);
		safeMode = SafeMode.DISABLED;
		redstoneMode = RedstoneMode.DISABLED;
		invMode = InvMode.ENABLED;
		checkForced();
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) LatCoreMC.openGui(ep, this, null); return true; }
	
	public void onUpdate()
	{
		if(isServer())
		{
			if(cooldown <= 0)
			{
				cooldown = EMCCConfig.Condenser.condenserSleepDelay;
				
				if(redstoneMode != RedstoneMode.DISABLED && redstoneMode.cancel(redstonePowered))
					return;
				
				int limit = EMCCConfig.Condenser.condenserLimitPerTick;
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
						
						double iev = EMCHandler.instance().getEMC(items[INPUT_SLOTS[i]]);
						
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
							double a = Math.min(Math.min(storedEMC, storageItem.getEmcTrasferLimit(tar)), storageItem.getMaxStoredEmc(tar) - ev);
							
							if(a > 0D)
							{
								storageItem.setStoredEmc(items[SLOT_TARGET], ev + a);
								storedEMC -= a;
								markDirty();
							}
						}
					}
					else
					{
						double ev = EMCHandler.instance().getEMC(tar);
						
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
		tag.setShort("Cooldown", (short)cooldown);
	}
	
	public void checkForced()
	{
		if(EMCCConfig.Condenser.forcedRedstoneControl != null && redstoneMode.ID != EMCCConfig.Condenser.forcedRedstoneControl.ID)
		{ redstoneMode = EMCCConfig.Condenser.forcedRedstoneControl; markDirty(); }
		
		if(EMCCConfig.Condenser.forcedSecurity != null && security.level.ID != EMCCConfig.Condenser.forcedSecurity.ID)
		{ security.level = EMCCConfig.Condenser.forcedSecurity; markDirty(); }
		
		if(EMCCConfig.Condenser.forcedSafeMode != null && safeMode.ID != EMCCConfig.Condenser.forcedSafeMode.ID)
		{ safeMode = EMCCConfig.Condenser.forcedSafeMode; markDirty(); }
		
		if(EMCCConfig.Condenser.forcedInvMode != null && invMode.ID != EMCCConfig.Condenser.forcedInvMode.ID)
		{ invMode = EMCCConfig.Condenser.forcedInvMode; markDirty(); }
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return (invMode == InvMode.DISABLED) ? NO_SLOTS : ((s == 0) ? OUTPUT_SLOTS : INPUT_SLOTS); }
	
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
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
	public boolean canWrench(EntityPlayer ep)
	{ return security.canInteract(ep); }
	
	public void readFromWrench(NBTTagCompound tag)
	{ readTileData(tag); }
	
	public void writeToWrench(NBTTagCompound tag)
	{ writeTileData(tag); }
	
	public void onWrenched(EntityPlayer ep, ItemStack is)
	{ dropItems = false; }
	
	public ItemStack getBlockToPlace()
	{ return new ItemStack(EMCCItems.b_condenser); }
	
	public void handleButton(String button, int mouseButton, NBTTagCompound data, EntityPlayer ep)
	{
		if(button.equals(EMCCGuis.Buttons.SAFE_MODE))
			safeMode = safeMode.next();
		else if(button.equals(LMGuiButtons.REDSTONE))
			redstoneMode = (mouseButton == 0) ? redstoneMode.next() : redstoneMode.prev();
		else if(button.equals(LMGuiButtons.INV_MODE))
			invMode = (mouseButton == 0) ? invMode.next() : invMode.prev();
		else if(button.equals(LMGuiButtons.SECURITY))
		{
			if(ep != null && security.isOwner(ep))
				security.level = (mouseButton == 0) ? security.level.next(LMSecurity.Level.VALUES) : security.level.prev(LMSecurity.Level.VALUES);
			else printOwner(ep);
		}
		
		checkForced();
		markDirty();
	}
	
	public void onClientAction(EntityPlayer ep, String action, NBTTagCompound data)
	{
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
		else super.onClientAction(ep, action, data);
			
	}
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerCondenser(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiCondenser(new ContainerCondenser(ep, this)); }
	
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return security.canInteract(ep); }
	
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }
}