package tipos;


import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import principal.EntesConServicios;
import principal.POI;

public class Banco extends POI implements Disponibilidad{
	int LUNES=1,VIERNES=5;
	private EntesConServicios servicios;
	//------------------------DISPONIBILIDAD------------------
	
	LocalTime INICIO=new LocalTime(10,00),
			  FIN= new LocalTime(15,00);
	
	public Banco(){
		servicios=new EntesConServicios();
	}
	

	public void agregarServicio(Servicio unServicio){
		this.servicios.agregarServicio(unServicio);
	}
	
	
	public boolean estaDisponible(int dia, String hora){
		DateTimeFormatter formato= DateTimeFormat.forPattern("HH:mm");
		LocalTime horaP=LocalTime.parse(hora,formato);
		return ((dia>=LUNES) && (dia<=VIERNES) && (horaP.isAfter(INICIO))&& (horaP.isBefore(FIN)));
	}
	
	public boolean estaDisponible(int dia, String hora,String servicioBuscado){
		return getServicios().estaDisponible(dia, hora, servicioBuscado);
	}
	
	//---------------BUSQUEDA-----------------------------------
	
	public boolean tienePalabra(String texto){
		return (super.tienePalabra(texto) || tienePalabraEnServicio(texto));
		
	}
	
	public boolean tienePalabraEnServicio(String texto){
		return getServicios().tienePalabra(texto);
	}
	
		
	// -------------------GETTERS,SETTERS-----------------
	public EntesConServicios getServicios() {
		return servicios;
	}
	public void setServicios(Servicio servicio) {
		this.getServicios().agregarServicio(servicio);
	}
}