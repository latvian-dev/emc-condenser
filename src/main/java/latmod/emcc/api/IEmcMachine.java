package latmod.emcc.api;

public interface IEmcMachine
{
	double getEmcCapacity();
	double getStoredEmc();
	double setStoredEmc(double emc);
	double getEmcTransferLimit();
	void onEmcReceived();
}