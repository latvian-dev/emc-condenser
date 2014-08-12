package latmod.emcc.api;
import latmod.core.mod.tile.ITileInterface;

public interface IEmcMachine extends ITileInterface
{
	public double getEmcCapacity();
	public double getStoredEmc();
	public double setStoredEmc(double emc);
	public double getEmcTransferLimit();
	public void onEmcReceived();
}