package latmod.emcc.api;

public interface IEmcMachine
{
	public double getEmcCapacity();
	public double getStoredEmc();
	public double setStoredEmc(double emc);
	public double getEmcTransferLimit();
	public void onEmcReceived();
}