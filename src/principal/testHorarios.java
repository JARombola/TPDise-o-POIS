package principal;

import static org.junit.Assert.*;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Servicio;

public class testHorarios {
	Banco banco;
	ParadaColectivo parada;
	Local carrousel;
	CGP unCGP;
	LocalTime horaInicio,horaCierre,horaX;
	
	@Before
	public void initialize() {
		banco = new Banco();
		Servicio rentas=new Servicio("Rentas");
		rentas.getHorarios().horarioNuevo(1, "10:00", "22:00");
		Servicio jubilacion=new Servicio("jubilacion");
		jubilacion.getHorarios().horarioNuevo(2, "05:00", "09:00");

		banco.agregarServicio(rentas);
		banco.agregarServicio(jubilacion);
		
		unCGP = new CGP();
		unCGP.agregarServicio(rentas);
		unCGP.agregarServicio(jubilacion);
		
		parada = new ParadaColectivo();
		carrousel = new Local();
		for (int dia = 2; dia <= 7; dia++) {		//Horarios Carrousel 
			carrousel.getHorarios().horarioNuevo(dia, "10:00", "13:00");
			carrousel.getHorarios().horarioNuevo(dia, "17:00", "20:30");
		}

	}
//tests disponibilidad bancos
	@Test
	public void testBancoNoEstaDisponibleLosDomingos() {
		Assert.assertFalse(banco.estaDisponible(7, "12:00")); //banco un domingo? Ja
	}
		
	@Test
	public void testBancoEstaDisponibleMartes14hs() {
		Assert.assertTrue(banco.estaDisponible(2, "14:00"));
	}
	
	@Test
	public void testBancoNoEstaDisponibleViernesTarde() {
	Assert.assertFalse(banco.estaDisponible(5, "16:00"));	//demasiado temprano...
		}

//tests disponibilidad servicios de bancos
	@Test
	public void testRentasEstaDisponibleLunesMediodia(){
		Assert.assertTrue(banco.estaDisponible(1, "12:00", "Rentas"));
	}		
		
	@Test
	public void testRentasNOEstaDisponibleLunes9am(){	
		Assert.assertFalse(banco.estaDisponible(1,"09:00","rentas"));
	}
		
	@Test
	public void testJubilacionEstaDisponibleMartes8am(){
		Assert.assertTrue(banco.estaDisponible(2, "08:00", "jUBiLaciOn"));
	}
	
//tests horarios CGP
	@Test
	public void testRentasDisponibleLunes12am(){
		Assert.assertTrue(unCGP.estaDisponible(1, "12:00", "Rentas"));
	}
		
		
	@Test
	public void testCGPNOEstaDisponibleJueves6am(){
		Assert.assertFalse(unCGP.estaDisponible(4, "06:00"));		
	}
		
	@Test
	public void testHayUnCGPAbiertoMartes6am(){
		Assert.assertTrue(unCGP.estaDisponible(2,"06:00"));	//hay jubilacion
	}
	
	
	//tests horarios paradas colectivos
	@Test
	public void testParadasSiempreDisponibles() {
		Assert.assertTrue(parada.estaDisponible());
	}
	
	//tests horarios local
	@Test
	public void testCarrouselAbiertoMiercoles19hs() {
		Assert.assertTrue(carrousel.estaDisponible(3, "19:00"));
	}
	
	@Test
	public void testCarrouselCerradoLunes11am() {
		Assert.assertFalse(carrousel.estaDisponible(1, "11:00"));
	}
	
	@Test
	public void testCarrouselCerradoViernes15hs() {
		Assert.assertFalse(carrousel.estaDisponible(5,"15:00"));
	}

/*
	@Test
	public void testHorarioLocal() {
		boolean abierto=carrousel.estaDisponible(3, "19:00");
		assertEquals(true,abierto);
		boolean domingo=carrousel.estaDisponible(1, "11:00");
		assertEquals(false,domingo);
		boolean abierto2=carrousel.estaDisponible(5,"15:00");
		assertEquals(false,abierto2);
	}
	*/
}
