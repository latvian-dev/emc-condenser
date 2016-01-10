package latmod.emcc.block;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import ftb.lib.api.gui.IGuiTile;
import ftb.lib.item.LMInvUtils;
import latmod.emcc.*;
import latmod.emcc.api.*;
import latmod.emcc.client.gui.*;
import latmod.emcc.config.EMCCConfigCondenser;
import latmod.emcc.emc.EMCHandler;
import latmod.ftbu.api.tile.ISecureTile;
import latmod.ftbu.tile.*;
import latmod.ftbu.util.LMSecurityLevel;
import latmod.ftbu.util.client.LMGuiButtons;
import latmod.latblocks.api.IQuartzNetTile;
import latmod.lib.config.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@Optional.Interface(modid = OtherMods.LATBLOCKS, iface = "latmod.latblocks.tile.IQuartzNetTile")
public class TileCondenser extends TileInvLM implements ISidedInventory, IEmcWrenchable, IGuiTile, ISecureTile, IQuartzNetTile
{
	public static final String ACTION_TRANS_ITEMS = "transItems";
	
	public static final int SLOT_TARGET = 0;
	public static final int[] TARGET_SLOTS = {SLOT_TARGET};
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
	
	public final ConfigEntryBool safe_mode = new ConfigEntryBool("safe_mode", false);
	public final ConfigEntryEnum<RedstoneMode> redstone_mode = new ConfigEntryEnum<RedstoneMode>("redstone_mode", RedstoneMode.class, RedstoneMode.VALUES, RedstoneMode.DISABLED, false);
	public final ConfigEntryEnum<InvMode> inv_mode = new ConfigEntryEnum<InvMode>("inv_mode", InvMode.class, InvMode.VALUES, InvMode.ENABLED, false);
	
	public TileCondenser()
	{
		super(SLOT_COUNT);
		checkForced();
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer()) FTBLib.openGui(ep, this, null);
		return true;
	}
	
	public void onUpdate()
	{
		if(isServer())
		{
			if(cooldown <= 0)
			{
				cooldown = EMCCConfigCondenser.sleep_delay.get();
				
				if(redstone_mode.get() != RedstoneMode.DISABLED && redstone_mode.get().cancel(redstonePowered)) return;
				
				int limit = EMCCConfigCondenser.limit_per_tick.get();
				if(limit == -1) limit = INPUT_SLOTS.length * 64;
				
				for(int i = 0; i < INPUT_SLOTS.length; i++)
				{
					if(items[INPUT_SLOTS[i]] != null && items[INPUT_SLOTS[i]].stackSize > 0)
					{
						if(items[INPUT_SLOTS[i]].getItem() instanceof IEmcStorageItem)
						{
							IEmcStorageItem storageItem = (IEmcStorageItem) items[INPUT_SLOTS[i]].getItem();
							
							double ev = storageItem.getStoredEmc(items[INPUT_SLOTS[i]]);
							
							storedEMC += ev;
							storageItem.setStoredEmc(items[INPUT_SLOTS[i]], 0D);
							markDirty();
							continue;
						}
						
						double iev = EMCHandler.instance().getEMC(items[INPUT_SLOTS[i]]);
						
						if(iev > 0D && !EMCC.blacklist.isBlacklistedFuel(items[INPUT_SLOTS[i]]))
						{
							if(safe_mode.get() && items[INPUT_SLOTS[i]].stackSize == 1) continue;
							
							int s = Math.min((safe_mode.get() && items[INPUT_SLOTS[i]].stackSize > 1) ? (items[INPUT_SLOTS[i]].stackSize - 1) : items[INPUT_SLOTS[i]].stackSize, limit);
							
							if(s <= 0) continue;
							
							limit -= s;
							storedEMC += iev * s;
							items[INPUT_SLOTS[i]].stackSize -= s;
							if(items[INPUT_SLOTS[i]].stackSize <= 0) items[INPUT_SLOTS[i]] = null;
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
						IEmcStorageItem storageItem = (IEmcStorageItem) tar.getItem();
						
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
							long d1 = (long) (storedEMC / ev);
							
							if(d1 > 0L)
							{
								for(long d = 0L; d < d1; d++)
								{
									if(LMInvUtils.addSingleItemToInv(LMInvUtils.singleCopy(tar), this, OUTPUT_SLOTS, -1, true))
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
		safe_mode.set(tag.getBoolean("SafeMode"));
		redstone_mode.set(RedstoneMode.VALUES[tag.getByte("RSMode")]);
		inv_mode.set(InvMode.VALUES[tag.getByte("InvMode")]);
		cooldown = tag.getShort("Cooldown");
		
		checkForced();
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		
		tag.setDouble("StoredEMC", storedEMC);
		tag.setBoolean("SafeMode", safe_mode.get());
		tag.setByte("RSMode", (byte) redstone_mode.get().ID);
		tag.setByte("InvMode", (byte) inv_mode.get().ID);
		tag.setShort("Cooldown", (short) cooldown);
	}
	
	public void checkForced()
	{
		if(EMCCConfigCondenser.Forced.redstone_control.get() != null && redstone_mode.get() != EMCCConfigCondenser.Forced.redstone_control.get())
		{
			redstone_mode.set(EMCCConfigCondenser.Forced.redstone_control.get());
			markDirty();
		}
		
		if(EMCCConfigCondenser.Forced.security.get() != null && security.level != EMCCConfigCondenser.Forced.security.get())
		{
			security.level = EMCCConfigCondenser.Forced.security.get();
			markDirty();
		}
		
		if(EMCCConfigCondenser.Forced.safe_mode.get() != null && safe_mode.get() != EMCCConfigCondenser.Forced.safe_mode.get().isEnabled())
		{
			safe_mode.set(EMCCConfigCondenser.Forced.safe_mode.get().isEnabled());
			markDirty();
		}
		
		if(EMCCConfigCondenser.Forced.inv_mode.get() != null && inv_mode.get() != EMCCConfigCondenser.Forced.inv_mode.get())
		{
			inv_mode.set(EMCCConfigCondenser.Forced.inv_mode.get());
			markDirty();
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return (inv_mode.get() == InvMode.DISABLED) ? NO_SLOTS : ((s == 0) ? OUTPUT_SLOTS : INPUT_SLOTS); }
	
	@Override
	public boolean canInsertItem(int i, ItemStack is, int j)
	{ return inv_mode.get().canInsertItem() && anyEquals(i, INPUT_SLOTS); }
	
	@Override
	public boolean canExtractItem(int i, ItemStack is, int j)
	{ return inv_mode.get().canExtractItem() && anyEquals(i, OUTPUT_SLOTS); }
	
	private static boolean anyEquals(int s, int[] slots)
	{
		for(int i = 0; i < slots.length; i++)
		{ if(s == slots[i]) return true; }
		return false;
	}
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{
		if(i == SLOT_TARGET || anyEquals(i, INPUT_SLOTS))
			return is.getItem() instanceof IEmcStorageItem || EMCHandler.instance().getEMC(is) > 0F;
		return false;
	}
	
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
	
	public void handleButton(String button, int mouseButton, NBTTagCompound data, EntityPlayerMP ep)
	{
		if(button.equals(EMCCGuis.Buttons.SAFE_MODE)) safe_mode.set(!safe_mode.get());
		else if(button.equals(LMGuiButtons.REDSTONE)) redstone_mode.onClicked();
		else if(button.equals(LMGuiButtons.INV_MODE)) inv_mode.onClicked();
		else if(button.equals(LMGuiButtons.SECURITY))
		{
			if(ep != null && security.isOwner(ep))
				security.level = (mouseButton == 0) ? security.level.next(LMSecurityLevel.VALUES_3) : security.level.prev(LMSecurityLevel.VALUES_3);
			else printOwner(ep);
		}
		
		checkForced();
		markDirty();
	}
	
	public void onClientAction(EntityPlayerMP ep, String action, NBTTagCompound data)
	{
		if(action.equals(ACTION_TRANS_ITEMS))
		{
			int[] invSlots = LMInvUtils.getPlayerSlots(ep);
			
			for(int i = 0; i < OUTPUT_SLOTS.length; i++)
				if(items[OUTPUT_SLOTS[i]] != null)
				{
					int ss = items[OUTPUT_SLOTS[i]].stackSize;
					
					for(int j = 0; j < ss; j++)
					{
						if(LMInvUtils.addSingleItemToInv(items[OUTPUT_SLOTS[i]].copy(), ep.inventory, invSlots, -1, true))
						{
							items[OUTPUT_SLOTS[i]].stackSize--;
							if(items[OUTPUT_SLOTS[i]].stackSize <= 0) items[OUTPUT_SLOTS[i]] = null;
							
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
	
	public ItemStack getQIconItem()
	{ return new ItemStack(EMCCItems.b_condenser); }
	
	public void onQClicked(EntityPlayer ep, int button)
	{
		if(!isServer()) return;
		if(!security.canInteract(ep))
		{
			printOwner(ep);
			return;
		}
		FTBLib.openGui(ep, this, null);
	}
}