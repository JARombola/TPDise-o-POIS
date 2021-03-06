package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;


import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import configuracionTerminales.FuncionesExtra;
import externos.BuscadorCGPExterno;
import pois.POI;
import terminales.BufferBusquedas;
import terminales.Busqueda;
import terminales.RepositorioTerminales;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.ParadaColectivo;

public class TestReportes extends AbstractPersistenceTest implements WithGlobalEntityManager {
	Terminal terminal,terminal2;
	Busqueda busqueda1,busqueda2,busqueda3,busqueda4;
	LocalDate fecha1, fecha2, fecha3, fecha4;
	List<Busqueda> almacenamientoBusquedas;
	Mapa mapa;
	ParadaColectivo parada1, parada2;
	RepositorioTerminales controlMaestro;
	BufferBusquedas buffer;
	BuscadorCGPExterno buscadorCgp;
	FuncionesExtra extra;
	
	@SuppressWarnings("unchecked")
	@After
	public void eliminarPois(){
		List<POI> p = createQuery("from POI").getResultList();
		p.stream().forEach(e->mapa.eliminarPOI(e.getId()));
	}
	
	@Before
	public void initialize(){
		mapa=new Mapa();
		buffer=new BufferBusquedas();
		controlMaestro=new RepositorioTerminales();
		parada1 = new ParadaColectivo();
		parada2 = new ParadaColectivo();
		parada1.setNombre("primer parada de la linea 114");
		parada2.setNombre("segunda parada de la linea 114");
		parada1.agregarTag("ASD");
		parada2.agregarTag("PERRO");
		fecha1 = new LocalDate(2016,02,01);
		fecha2 = new LocalDate(2016,02,02);
		fecha3 = new LocalDate(2016,02,02);
		fecha4 = new LocalDate(2016,12,23);
		busqueda1 = new Busqueda();
		busqueda1.setFecha(fecha1);
		busqueda1.setFraseBuscada("Perro");
		busqueda2 = new Busqueda();
		busqueda2.setFecha(fecha2);
		busqueda2.setFraseBuscada("Gato");
		busqueda3 = new Busqueda();
		busqueda3.setFecha(fecha3);
		busqueda3.setFraseBuscada("Operativos");
		busqueda4 = new Busqueda();
		busqueda4.setFecha(fecha4);
		busqueda4.setFraseBuscada("Dejar_la_facultad");
		almacenamientoBusquedas = new ArrayList<Busqueda>();
		
		mapa.agregarOmodificar(parada1);
		mapa.agregarOmodificar(parada2);
		
		terminal = new Terminal();
		terminal2 = new Terminal();

		terminal.activarOpcion("historial");
		terminal2.activarOpcion("historial");
		
		
		terminal.setMapa(mapa);
		terminal.setNombre("Terminal_1");
		terminal2.setMapa(mapa);
		terminal2.setNombre("Terminal_2");
		

		terminal.setBuffer(buffer);
		terminal2.setBuffer(buffer);
		controlMaestro.actualizar(terminal);
		controlMaestro.actualizar(terminal2);
	}
	
	@Test
	public void testReportePorFecha (){
		terminal.getOpciones().persistirBusquedasMongo(busqueda1);
		terminal.getOpciones().persistirBusquedasMongo(busqueda2);
		terminal.getOpciones().persistirBusquedasMongo(busqueda3);
		terminal.getOpciones().persistirBusquedasMongo(busqueda4);
		assertEquals(terminal.reporteFechas().getDatos().size(),3,0);			//4 busquedas, pero 2 son del mismo dia => cuentan como una
		terminal.eliminarBusquedas();
	}
	
	@Test
	public void testOpcionBusqueda() throws Exception{
		terminal.desactivarOpcion("historial");
		terminal.realizarBusqueda("Hola", "Chau");
		terminal.realizarBusqueda("114", "");
		terminal.realizarBusqueda("Julian", "Crack");
		assertTrue(terminal.reporteTotalResultados().getDatos().isEmpty());			//No se registraron, estaba desactivado
		terminal.activarOpcion("HISTORIAL");
		terminal.realizarBusqueda("Hola", "Chau");
		terminal.realizarBusqueda("114", "");
		terminal.realizarBusqueda("Julian", "Crack");
		assertEquals(terminal.getHistorialBusquedas().size(),3);
		assertEquals(terminal.reporteTotalResultados().getDatos().get(0).getResultados(),2,0);			//Registrados, hubieron 2 aciertos (114)
		terminal.realizarBusqueda("114", "");
		assertEquals(terminal.reporteTotalResultados().getDatos().get(0).getResultados(),4,0);			//2 aciertos mas
		terminal.desactivarOpcion("HISTORIAL");
		terminal.realizarBusqueda("114", "");
		assertEquals(terminal.reporteTotalResultados().getDatos().get(0).getResultados(),4,0);
		terminal.eliminarBusquedas();
	}
	
	@Test
	public void testReportesParcialesTerminal1() throws Exception{
		terminal.realizarBusqueda("Dise�o", "");
		int cantidadBusquedas=terminal.reporteResultadosParciales().getDatos().size();
		assertEquals(cantidadBusquedas, 1);

		terminal.realizarBusqueda("ASD", "");
		cantidadBusquedas=terminal.reporteResultadosParciales().getDatos().size();
		assertEquals(cantidadBusquedas, 2);
		
		terminal.realizarBusqueda("ASD", "Ejemplo");
		cantidadBusquedas=terminal.reporteResultadosParciales().getDatos().size();
		assertEquals(cantidadBusquedas, 3);
		terminal.eliminarBusquedas();
	}
	
	
}



