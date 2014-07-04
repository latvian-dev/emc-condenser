package latmod.emcc.tile;
import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.*;
import latmod.emcc.net.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.ForgeDirection;

public class TileCondenser extends TileEMCC implements ISidedInventory, IEmcWrenchable
{
	public static final byte VERSION = 1;
	
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
	public EnumCond.SafeMode safeMode;
	public EnumCond.Redstone redstoneMode;
	public EnumCond.InvMode invMode;
	public EnumCond.AutoExport autoExport;
	public InventoryRestricted restrictedItems;
	
	public TileCondenser()
	{
		items = new ItemStack[SLOT_COUNT];
		safeMode = EnumCond.SafeMode.DISABLED;
		redstoneMode = EnumCond.Redstone.DISABLED;
		invMode = EnumCond.InvMode.ENABLED;
		autoExport = EnumCond.AutoExport.DISABLED;
		restrictedItems = new InventoryRestricted(this);
		checkForced();
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(security.canPlayerInteract(ep)) openGui(ep, EMCCGuis.CONDENSER);
		else if(!worldObj.isRemote) LatCore.printChat(ep, "Owner: " + security.owner);
		
		return true;
	}
	
	public void onUpdate()
	{
		if(!worldObj.isRemote)
		{
			if(cooldown <= 0)
			{
				cooldown = EMCC.config.condenser.condenserSleepDelay;
				
				if(redstoneMode.cancel(isPowered(true)))
					return;
				
				int limit = EMCC.config.condenser.condenserLimitPerTick;
				if(limit == -1) limit = items.length * 64;
				
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
							onInventoryChanged();
						}
						
						float iev = EMCC.getEMC(items[INPUT_SLOTS[i]]);
						
						if(iev > 0F && !EMCC.blacklist.isBlacklistedFuel(items[INPUT_SLOTS[i]]))
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
							onInventoryChanged();
						}
					}
					
					if(limit <= 0) break;
				}
				
				if(storedEMC > 0D && items[SLOT_TARGET] != null)
				{
					if(items[SLOT_TARGET].getItem() instanceof IEmcStorageItem)
					{
						IEmcStorageItem storageItem = (IEmcStorageItem)items[SLOT_TARGET].getItem();
						
						if(storageItem.canChargeEmc(items[SLOT_TARGET]))
						{
							double ev = storageItem.getStoredEmc(items[SLOT_TARGET]);
							double a = Math.min(storedEMC, storageItem.getEmcTrasferLimit(items[SLOT_TARGET]));
							storageItem.setStoredEmc(items[SLOT_TARGET], ev + a);
							storedEMC -= a;
							markDirty();
							onInventoryChanged();
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
									if(InvUtils.addSingleItemToInv(InvUtils.singleCopy(items[SLOT_TARGET]), this, OUTPUT_SLOTS, -1, true))
									{
										storedEMC -= ev;
										markDirty();
										onInventoryChanged();
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
		
		//spawnParticles();
	}
	
	protected boolean customNBT()
	{ return true; }
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readFromWrench(tag);
		checkForced();
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeToWrench(tag);
	}
	
	public void checkForced()
	{
		if(EMCC.config.condenser.forcedRedstoneControl != null && redstoneMode.ID != EMCC.config.condenser.forcedRedstoneControl.ID)
		{ redstoneMode = EMCC.config.condenser.forcedRedstoneControl; markDirty(); }
		
		if(EMCC.config.condenser.forcedSecurity != null && security.level != EMCC.config.condenser.forcedSecurity.ID)
		{ security.level = EMCC.config.condenser.forcedSecurity.ID; markDirty(); }
		
		if(EMCC.config.condenser.forcedSafeMode != null && safeMode.ID != EMCC.config.condenser.forcedSafeMode.ID)
		{ safeMode = EMCC.config.condenser.forcedSafeMode; markDirty(); }
		
		if(EMCC.config.condenser.forcedInvMode != null && invMode.ID != EMCC.config.condenser.forcedInvMode.ID)
		{ invMode = EMCC.config.condenser.forcedInvMode; markDirty(); }
	}
	
	public void onRestrictedInvChanged()
	{
		ItemStack isA = restrictedItems.items[0];
		
		if(isA != null && isA.getItem() == Item.nameTag)
		{
			String s = isA.hasDisplayName() ? isA.getDisplayName() : null;
			
			if(s != null && !security.restricted.contains(s) && security.restricted.size() < restrictedItems.items.length - 2)
				security.restricted.add(s);
		}
		
		ItemStack isR = restrictedItems.items[6];
		
		if(isR != null && isR.getItem() == Item.nameTag)
		{
			String s = isR.hasDisplayName() ? isR.getDisplayName() : null;
			
			if(s != null && security.restricted.contains(s))
				security.restricted.remove(s);
		}
		
		security.restricted.sort();
		
		for(int i = 1; i <= 5; i++)
			restrictedItems.items[i] = null;
		
		for(int i = 0; i < security.restricted.size(); i++)
		{
			restrictedItems.items[i + 1] = new ItemStack(Item.slimeBall);
			restrictedItems.items[i + 1].setItemName(security.restricted.get(i));
		}
		
		markDirty();
	}
	
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		
		if(autoExport.isOn())
		{
			for(ForgeDirection f : ForgeDirection.VALID_DIRECTIONS)
			{
				if(f != ForgeDirection.UP)
				{
					IInventory inv = InvUtils.getInvAt(this, f, false);
					
					if(inv != null)
					{
						int[] invSlots = InvUtils.getAllSlots(inv, f);
						boolean invChanged = false;
						
						for(int i = 0; i < OUTPUT_SLOTS.length; i++)
						if(items[OUTPUT_SLOTS[i]] != null)
						{
							int ss = items[OUTPUT_SLOTS[i]].stackSize;
							
							for(int j = 0; j < ss; j++)
							{
								if(InvUtils.addSingleItemToInv(items[OUTPUT_SLOTS[i]].copy(), inv, invSlots, f.ordinal(), true))
								{
									items[OUTPUT_SLOTS[i]].stackSize--;
									if(items[OUTPUT_SLOTS[i]].stackSize <= 0)
										items[OUTPUT_SLOTS[i]] = null;
									
									invChanged = true;
								}
							}
						}
						
						if(invChanged) inv.onInventoryChanged();
					}
				}
			}
		}
	}
	
	public void handleGuiButton(boolean serverSide, EntityPlayer ep, int i)
	{
		if(serverSide)
		{
			if(i == EnumCond.Buttons.SAFE_MODE)
				safeMode = safeMode.next();
			else if(i == EnumCond.Buttons.REDSTONE)
				redstoneMode = redstoneMode.next();
			else if(i == EnumCond.Buttons.INV_MODE)
				invMode = invMode.next();
			else if(i == EnumCond.Buttons.AUTO_EXPORT)
				autoExport = autoExport.next();
			else if(i == EnumCond.Buttons.SECURITY)
			{
				if(ep != null && security.owner.equals(ep.getCommandSenderName()))
					security.level = (security.level + 1) % 4;
			}
			
			checkForced();
			markDirty();
		}
		else
		{
			EMCCNetHandler.sendToServer(this, new PacketButtonPressed(i));
		}
	}
	
	public void openGui(boolean serverSide, EntityPlayer ep, int guiID)
	{
		if(serverSide) { openGui(ep, guiID); }
		else EMCCNetHandler.sendToServer(this, new PacketOpenGui(guiID));
	}
	
	public void transferItems(boolean serverSide, EntityPlayer ep)
	{
		if(serverSide)
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
						
						onInventoryChanged();
					}
				}
			}
		}
		else EMCCNetHandler.sendToServer(this, new PacketTransItems());
	}
	
	public EnumCond.Security getSecurityEnum()
	{ return EnumCond.Security.VALUES[security.level]; }
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return (invMode == EnumCond.InvMode.DISABLED) ? NO_SLOTS : ((s == UP) ? INPUT_SLOTS : OUTPUT_SLOTS); }
	
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
	public String getInvName()
	{ return "Condenser"; }
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return true; }
	
	public boolean canWrench(EntityPlayer ep)
	{ return security.canPlayerInteract(ep); }
	
	public void readFromWrench(NBTTagCompound tag)
	{
		EnumCond.NBT.readAll(this, tag);
	}
	
	public void writeToWrench(NBTTagCompound tag)
	{
		EnumCond.NBT.writeAll(this, tag);
	}
	
	public void onWrenched(EntityPlayer ep, ItemStack is)
	{
		dropItems = false;
	}
	
	public ItemStack getBlockToPlace()
	{ return EMCCItems.CONDENSER; }
	
	/*
	
	private static final int BONUS_BLOCKS_1[][] = { { 0, 2, }, { 0, -2, }, { 2, -1, }, { 2, 1, }, { -2, -1, }, { -2, 1, }, };
	private static final int BONUS_BLOCKS_2[][] = { { 2, 0, }, { -2, 0, }, { -1, 2, }, { 1, 2, }, { -1, -2, }, { 1, -2, }, };
	
	private int[][] getBonusBlocks()
	{
		int count = 0;
		
		for(int i = 0; i < 6; i++)
		{
			int ox = BONUS_BLOCKS_1[i][0];
			int oy = BONUS_BLOCKS_1[i][1];
			
			if(!(worldObj.getBlockId(xCoord + ox, yCoord, zCoord + oy) == Block.netherBrick.blockID && worldObj.getBlockId(xCoord + ox, yCoord + 1, zCoord + oy) == EMCCItems.b_blocks.blockID && worldObj.getBlockMetadata(xCoord + ox, yCoord + 1, zCoord + oy) == 0))
				break; else count++;
		}
		
		if(count == 6) return BONUS_BLOCKS_1;
		
		for(int i = 0; i < 6; i++)
		{
			int ox = BONUS_BLOCKS_2[i][0];
			int oy = BONUS_BLOCKS_2[i][1];
			
			if(!(worldObj.getBlockId(xCoord + ox, yCoord, zCoord + oy) == Block.netherBrick.blockID && worldObj.getBlockId(xCoord + ox, yCoord + 1, zCoord + oy) == EMCCItems.b_blocks.blockID && worldObj.getBlockMetadata(xCoord + ox, yCoord + 1, zCoord + oy) == 0))
				return null;
		}
		
		return BONUS_BLOCKS_2;
	}
	
	public void spawnParticles()
	{
		if(!worldObj.isRemote) return;
		
		Random r = ParticleHelper.rand;
		
		int[][] blocks = getBonusBlocks();
		if(blocks == null) return;
		
		for(int i = 0; i < 6; i++)
		{
			int bx = blocks[i][0] + xCoord;
			int bz = blocks[i][1] + zCoord;
			
			if (!worldObj.isAirBlock((bx - xCoord) / 2 + xCoord, yCoord + 1, (bz - zCoord) / 2 + zCoord)) break;
			
			worldObj.spawnParticle("enchantmenttable", xCoord + 0.5D, yCoord + 2D, zCoord + 0.5D, ((bx - xCoord) + r.nextFloat()) - 0.5D, (1 - r.nextFloat() - 1F), ((bz - zCoord) + r.nextFloat()) - 0.5D);
		}
	}
	
	*/
}