
package adapter;

public interface AutoUpdatable {
	
	public void updateOptionSetName(String model, String optSetName, String newSetName);
	public void updateOptionPrice(String model, String optSetName, String optName, float newOptPrice);
	
}
