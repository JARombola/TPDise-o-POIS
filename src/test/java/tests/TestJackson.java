package tests;


import static org.junit.Assert.*;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import json.JsonFactory;
import pois.Direccion;
import pois.POI;
import terminales.BufferBusquedas;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.Banco;
import externos.BancoExterno;
import externos.BuscadorBancoExterno;
import externos.OrigenDatos;


public class TestJackson extends AbstractPersistenceTest implements WithGlobalEntityManager {
/*	
		private JsonFactory jsonFactory = new JsonFactory();
		private BuscadorBancoExterno buscadorExterno;
		
		private Mapa mapa;
		private List<BancoExterno> listaBancosExt;
		private String jsonBanco =  "[{\"nombre\": \"Banco de la Plaza\", "
				+ "\"latitud\": -35.9338322, "
				+ "\"longitud\": 72.348353, "
				+ "\"sucursal\": \"Avellaneda\", "
				+ "\"gerente\": \"Javier Loeschbor\", "
				+ "\"servicios\":[\"cobro cheques\", \"dep�sitos\", \"extracciones\", \"transferencias\", \"cr�ditos\", \"\", \"\", \"\" ]"
				+ "}]";
		OrigenDatos banco;
		private BancoExterno bancoExt;
		private Direccion dire;
		private Terminal terminal;
		private BufferBusquedas buffer;
			
		@SuppressWarnings("unchecked")
		@After
		public void eliminarPois(){
			List<POI> p = createQuery("from POI").getResultList();
			p.stream().forEach(e->mapa.eliminarPOI(e.getId()));
			p = createQuery("from POI").getResultList();
		}
		
		@Before
		public void setUp() {
			bancoExt= new BancoExterno();
				dire = new Direccion();
				dire.setNumero(150);
				dire.setCalle("Siempre Viva");
				bancoExt.setDireccion(dire);
				bancoExt.setGerente("Mr. Burns");
				bancoExt.setNombre("Banco de Madera");
			banco=Mockito.mock(OrigenDatos.class);
			Mockito.when(banco.buscar("Banco de la Plaza", "cobro cheques")).thenReturn(jsonBanco);
			listaBancosExt = jsonFactory.fromJson(jsonBanco);
	
			buscadorExterno = new BuscadorBancoExterno();
				buscadorExterno.setComponente(banco);
			
			buffer = new BufferBusquedas();
			mapa = new Mapa();
			terminal = new Terminal();
				terminal.setBuffer(buffer);
				buffer.agregarExterno(buscadorExterno);
				terminal.setMapa(mapa);
		}
		
		
		@Test
		public void nombreSucursalGerenteDelBancoTest() {
			Banco unBancoDesdeJson=(Banco) buscadorExterno.adaptarJsonBanco(banco.buscar("Banco de la Plaza","cobro cheques")).get(0);
			assertEquals(unBancoDesdeJson.getNombre(), "Banco de la Plaza");
			assertEquals(listaBancosExt.get(0).getGerente(),"Javier Loeschbor");
			assertEquals(listaBancosExt.get(0).getSucursal(),"Avellaneda");
			assertEquals(listaBancosExt.get(0).getServicios().get(0),"cobro cheques");
			assertEquals(listaBancosExt.get(0).getServicios().get(5),"");
		}
		
		@Test
		public void bancoATravesDelMapa() {
			List<POI> pois = terminal.buscar("Banco de la Plaza", "cobro cheques");
			assertEquals(bancoExt.getNombre(), "Banco de Madera");
			Mockito.verify(banco,Mockito.times(1)).buscar("Banco de la Plaza", "cobro cheques");
			assertEquals(pois.size(),1);				//Estaba vacio y agrega el banco		
			buffer.borrarBusquedaCache("Banco de la Plaza");
		}
*/
}
		
		
			

