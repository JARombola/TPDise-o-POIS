package tipos;

import principal.POI;

public class ParadaColectivo extends POI{
	
	private double radioCercania = 0.1;
	
	

	public double getRadioCercania() {
		return radioCercania;
	}



	public boolean estaDisponible(){
		return true;
	}
}
