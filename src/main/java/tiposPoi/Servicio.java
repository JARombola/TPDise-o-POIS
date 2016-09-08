package tiposPoi;

import java.util.List;

import org.joda.time.LocalTime;

import pois.ListaHorarios;

public class Servicio{
	private ListaHorarios horarios;
	private String nombre;
	private String descripcion;
	private List<String> tags;
	

	public Servicio(String nombre) {
	//	horarios = new ListaHorarios();
	//	this.setNombre(nombre);
	}

	// -------------------GETTERS,SETTERS-----------------
	public List<String> getTags() {
		return tags;
	}
	
	public void agregarTag(String tag) {
		this.tags.add(tag);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ListaHorarios getHorarios() {
		return horarios;
	}
	public boolean tienePalabra(String palabra){
		return (getNombre().contains(palabra));// || getTags().contains(palabra));
	}
	
	public boolean estaDisponible(int dia,LocalTime hora){
		return getHorarios().estaDisponible(dia,hora);
	}

	public void setHorarios(ListaHorarios horarios) {
		this.horarios = horarios;
	}

}