package tests;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import externos.BuscadorCGPExterno;
import externos.CentroDTO;
import externos.OrigenDatos;
import externos.RangosServiciosDTO;
import externos.ServiciosDTO;
import pois.Horario;
import pois.POI;
import terminales.BufferBusquedas;
import terminales.Busqueda;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.CGP;
import tiposPoi.Servicio;

public class TestExternosCGP extends AbstractPersistenceTest implements WithGlobalEntityManager {
	RangosServiciosDTO rangoServicio, rangoServicio2, rangoServicio3;
	ServiciosDTO servicioDTOTramite, servicioDTOCobro;
	CentroDTO cgp;
	OrigenDatos cgpMock;
	BufferBusquedas buffer;
	List<ServiciosDTO> servicios;
	List<CentroDTO> centros;
	Mapa mapa;
	Terminal terminal;
	BuscadorCGPExterno buscadorExterno;
	
	@SuppressWarnings("unchecked")
	@After
	public void eliminarPois(){
		List<POI> p = createQuery("from POI").getResultList();
		p.stream().forEach(e->remove(e));
		p = createQuery("from POI").getResultList();
	}
	
	@Before
	public void initialize() {
		terminal=new Terminal();
		mapa = new Mapa();
		cgpMock=Mockito.mock(OrigenDatos.class);
		cgp = new CentroDTO();
			cgp.setDomicilio("Av. 9 de Julio 4322");
			
		rangoServicio = new RangosServiciosDTO();
			rangoServicio.setDia(1);
			rangoServicio.setHoraInicio(8);
			rangoServicio.setMinutoInicio(30);
			rangoServicio.setHoraFin(19);
			rangoServicio.setMinutoFin(15);
			
		rangoServicio2 = new RangosServiciosDTO();
			rangoServicio2.setDia(2);
			rangoServicio2.setHoraInicio(7);
			rangoServicio2.setMinutoInicio(00);
			rangoServicio2.setHoraFin(21);
			rangoServicio2.setMinutoFin(15);
			
		rangoServicio3 = new RangosServiciosDTO();
			rangoServicio3.setDia(3);
			rangoServicio3.setHoraInicio(7);
			rangoServicio3.setMinutoInicio(00);
			rangoServicio3.setHoraFin(21);
			rangoServicio3.setMinutoFin(15);
			
		servicioDTOTramite = new ServiciosDTO();
			servicioDTOTramite.agregarRango(rangoServicio);
			servicioDTOTramite.agregarRango(rangoServicio2);
			servicioDTOTramite.setNombre("Tramite Jubilacion");
			
		servicioDTOCobro = new ServiciosDTO();
			servicioDTOCobro.agregarRango(rangoServicio3);
			servicioDTOCobro.setNombre("Cobro Jubilacion");
			
		servicios=new ArrayList<ServiciosDTO>();
		servicios.add(servicioDTOCobro);
		servicios.add(servicioDTOTramite);
		//Para probar el EXTERNO
		cgp.setServicios(servicios);
		centros = new ArrayList<CentroDTO>();
		centros.add(cgp);
		Mockito.when(cgpMock.buscar("9 de julio")).thenReturn(centros);
		buffer = new BufferBusquedas();

			terminal.setBuffer(buffer);
			terminal.setMapa(mapa);
		
		buscadorExterno = new BuscadorCGPExterno();
		buscadorExterno.setComponente(cgpMock);
		
		buffer.agregarExterno(buscadorExterno);
	}
	
	@Test
	public void testDomicilioCalle() {
		terminal.buscar("Av. 9 de Julio","");
		Assert.assertEquals(cgp.getCalle(),"Av. 9 de Julio");
		Mockito.verify(cgpMock,Mockito.times(1)).buscar("Av. 9 de Julio");
		buffer.borrarBusquedaCache("Av. 9 de Julio");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBusquedaExternaCGP() {
		List<POI>p = createQuery("from POI").getResultList();
		Assert.assertEquals(p.size(), 0);
		p = terminal.buscar("9 de julio","");				//Consulta al externo y guarda el POI en la cache
		Assert.assertEquals(p.size(), 1);			
		terminal.buscar("9 de julio","");						//estaba en la cache => NO consulta al externo
		Mockito.verify(cgpMock,Mockito.times(1)).buscar("9 de julio");	
		cgpMock.buscar("9 de julio");
		
		Mockito.verify(cgpMock,Mockito.times(2)).buscar("9 de julio");	
		buffer.borrarBusquedaCache("9 de julio");
	}
	
	@Test
	public void testDomicilioNumero() {
		Assert.assertEquals(cgp.getNumero(),4322);
	}
	
	@Test
	public void testAdaptarAHorarioLocalTime(){
		Horario horario = buscadorExterno.adaptarAHorarioLocalTime(rangoServicio);
		Assert.assertEquals(horario.getInicio(),new LocalTime(8,30));
		Assert.assertEquals(horario.getFin(),new LocalTime(19,15));
	}
	
	@Test
	public void testAdaptarSerivicio(){
		Servicio servicioPOI = buscadorExterno.adaptarServicio(servicioDTOTramite);
		Assert.assertEquals(servicioPOI.getNombre(),"Tramite Jubilacion");
		Assert.assertEquals(servicioPOI.getHorarios().getHorariosAtencion().get(0).getInicio(),new LocalTime(8,30));
		Assert.assertEquals(servicioPOI.getHorarios().getHorariosAtencion().get(0).getDia(),1);
		Assert.assertEquals(servicioPOI.getHorarios().getHorariosAtencion().get(1).getFin(),new LocalTime(21,15));
	}
	
	@Test
	public void testAdaptarCgp(){
		cgp.setServicios(servicios);
		CGP poicgp= buscadorExterno.adaptarCGP(cgp);
		Assert.assertEquals(poicgp.getNombre(),("Av. 9 de Julio 4322"));
		Assert.assertEquals(poicgp.getListaServicios().getServicios().size(),cgp.getServicios().size());
	}
}