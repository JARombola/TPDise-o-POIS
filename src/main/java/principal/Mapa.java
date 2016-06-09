package principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import externos.BufferBusquedas;
import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;


public class Mapa {

	static List<POI> pois;
	BufferBusquedas buffer = new BufferBusquedas();
	
	
	//---------------BUSQUEDA-----------------------------------
	public List<POI> buscar(String texto1, String texto2) {
		//System.out.println("Busc�: "+texto1);

		buffer.busquedaExterna(texto1, texto2);
		buffer.getResultados().forEach(poi->agregarOmodificar(poi));			//Primero busqueda externa
			
		List<POI> resultadosBusqueda;
		resultadosBusqueda= getListaPOIS().stream()
							.filter(poi->poi.tienePalabra(texto1))
							.collect(Collectors.toList());

		return resultadosBusqueda;
	}
	
	
	public void agregarOmodificar (POI poiEntrante){
		int posPOI=pois.indexOf(poiEntrante);
		if(posPOI!=-1){
			pois.get(posPOI).modificar(poiEntrante);;
		}else{
			pois.add(poiEntrante);
		}
	}
	
	// -------------------GETTERS,SETTERS-----------------

	public BufferBusquedas getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferBusquedas buffer) {
		this.buffer = buffer;
	}

	public Mapa() {
		pois = new ArrayList<POI>();
	}

	public List<POI> getListaPOIS() {
		return pois;
	}

	public void setPOI(POI poi) {
		pois.add(poi);
	}

	// -------------------ABM POIS-----------------------
	public void registrarPOI(String tipo){
		POI puntoNuevo=crearPoi(tipo);
		setPOI(puntoNuevo);
	}
	
	public POI crearPoi(String tipo) {
		Map<String,POI>map=new HashMap<String,POI>();
		map.put("Banco", new Banco());
		map.put("Parada", new ParadaColectivo());
		map.put("Local", new Local());
		map.put("CGP",new CGP());
		return map.get(tipo);
	}
		
	public void eliminarPOI (POI poiEntrante){
		int posPOI=pois.indexOf(poiEntrante);
		if(posPOI!=-1){
			pois.remove(posPOI);
		}else{
		     System.out.println("No existe el POI ingresado");
		}
	}

	
	
}
