package externos;

import java.util.ArrayList;
import java.util.List;


public class CentroDTO {
	private int comuna;
	private int id;
	private String zona, domicilio, telefono, nombreDirector;

	List<ServiciosDTO> servicios;
	

	public String getCalle(){
		List<String> direc = separarDomicilio();
		return (direc.get(0));
	}
	
	public int getNumero(){
		List<String> direc = separarDomicilio();
		return (Integer.parseInt(direc.get(1)));
	}
	
	private List<String> separarDomicilio(){
		int n;
		String calle;
		String numero;
		List<String> domicilioSeparado = new ArrayList<String>();
		n = domicilio.lastIndexOf(" ");
		calle = domicilio.substring(0,n).trim();
		numero = domicilio.substring(n+1, domicilio.length());
		
		domicilioSeparado.add(calle);
		domicilioSeparado.add(numero);
		
		return domicilioSeparado;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public List<ServiciosDTO> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServiciosDTO> servicios) {
		this.servicios = servicios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombreDirector() {
		return nombreDirector;
	}

	public void setNombreDirector(String nombreDirector) {
		this.nombreDirector = nombreDirector;
	}
	
}
