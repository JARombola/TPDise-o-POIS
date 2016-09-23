package tests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import configuracionTerminales.Administrador;
import configuracionTerminales.FuncionesExtra;
import pois.Comuna;
import pois.Coordenadas;
import procesos.ControlProcesos;
import procesos.ManejoDeResultadosProcesos;
import procesos.ProcesoAgregarAccionesParaUsuarios;
import terminales.ControlTerminales;
import terminales.Terminal;


public class TestProceso3 {
	private Administrador admin;
	private ControlProcesos controlProcesos;
	private ManejoDeResultadosProcesos manejoMock;
	private ControlTerminales controlMaestro;
	private Terminal terminal1, terminal2, terminal3, terminal4;
	private Comuna comuna;
	private Coordenadas coordenada1, coordenada2, coordenada3, coordenada4;
	private FuncionesExtra opciones;

	@Before
	public void intialize() {
		admin = new Administrador("ASDASD@gmail.com");
		
		manejoMock=Mockito.mock(ManejoDeResultadosProcesos.class);
		
		controlProcesos = new ControlProcesos();
		controlProcesos.setManejoResultados(manejoMock);

		comuna = new Comuna();
		coordenada1 = new Coordenadas();
		coordenada1.setLatitud(47);
		coordenada1.setLongitud(-120);
		comuna.addPunto(coordenada1);
		
		coordenada2 = new Coordenadas();
		coordenada2.setLatitud(45);
		coordenada2.setLongitud(-130);
		comuna.addPunto(coordenada2);
		
		coordenada3 = new Coordenadas();
		coordenada3.setLatitud(50);
		coordenada3.setLongitud(-130);
		comuna.addPunto(coordenada3);
		
		coordenada4 = new Coordenadas();
		coordenada4.setLatitud(50);
		coordenada4.setLongitud(-130);
		
		controlMaestro = new ControlTerminales();
		
		opciones = new FuncionesExtra(100);
		
		terminal1 = new Terminal();
		terminal2 = new Terminal();
		terminal3 = new Terminal();
		terminal4 = new Terminal();
		
		terminal1.setCoordenadas(coordenada1);
		terminal2.setCoordenadas(coordenada1);
		terminal3.setCoordenadas(coordenada1);
		terminal4.setCoordenadas(coordenada4);
		
		terminal1.setExtra(opciones);
		terminal2.setExtra(opciones);
		terminal3.setExtra(opciones);
		terminal4.setExtra(opciones);
		
		terminal1.setAdministrador(admin);
		
		controlMaestro.agregarTerminal(terminal1);
		controlMaestro.agregarTerminal(terminal2);
		controlMaestro.agregarTerminal(terminal3);
		controlMaestro.agregarTerminal(terminal4);			
		
	}

	
	@Test
	public void testActivarMailEnUnaTerminal() {
		Assert.assertEquals(terminal1.estaActivado("MAIL"), false);
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlProcesos,controlMaestro, "MAIL");
		proceso.agregarAccionTerminal(terminal1);
		proceso.run();
		Assert.assertEquals(terminal1.estaActivado("MAIL"), true);
		Assert.assertEquals(proceso.getCantidadAfectados(),1);						//Se modifico UNA terminal
	}
	
	@Test
	public void testActivarBusquedasEnUnaComuna() {
		Assert.assertEquals(terminal1.estaActivado("HISTORIAL"), false);
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlProcesos,controlMaestro,"HISTORIAL");
		proceso.AgregarAccionComuna(comuna);
		proceso.run();
		Assert.assertEquals(terminal1.estaActivado("HISTORIAL"), true);			//las 2 terminales estan en esa comuna, y se les activa el Historial
		Assert.assertEquals(terminal2.estaActivado("HISTORIAL"), true);
		Assert.assertEquals(terminal3.estaActivado("HISTORIAL"), true);
		Assert.assertEquals(proceso.getCantidadAfectados(),3);					//Se modificaron las 3 terminales
	}
	
	@Test
	public void testActivarMailEnTodasLasTerminales() {
		Assert.assertEquals(terminal1.estaActivado("MAIL"), false);
		Assert.assertEquals(terminal2.estaActivado("MAIL"), false);
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlProcesos,controlMaestro, "MAIL");
		proceso.agregarAccionTodasTerminales();
		proceso.run();
		Assert.assertEquals(terminal1.estaActivado("MAIL"), true);			//las 2 terminales estan en esa comuna, y se les activa el Historial
		Assert.assertEquals(terminal2.estaActivado("MAIL"), true);
		Assert.assertEquals(proceso.getCantidadAfectados(),4);					//Se modificaron todas las terminales (4)
	}
	
	@Test
	public void testFallaProceso() {
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlProcesos,controlMaestro, "ASD");	
		proceso.setControladorProcesos(controlProcesos);
		proceso.agregarAccionTerminal(terminal1);
		proceso.run();						//Falla porque la opcion "ASD" es incorrecta => llama al metodo del mock para el manejo del error.
		Mockito.verify(manejoMock,Mockito.times(1)).tratarResultado(Mockito.any(),Mockito.any());
		
	}

}
