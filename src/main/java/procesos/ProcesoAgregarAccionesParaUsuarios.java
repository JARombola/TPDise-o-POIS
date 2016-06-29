package procesos;

import principal.POIS.Comuna;
import principal.Terminales.ControlTerminales;
import principal.Terminales.Terminal;

public class ProcesoAgregarAccionesParaUsuarios extends Proceso{
	private ControlTerminales centralTerminales;
	private Comuna comuna;
	private Terminal terminal;
	private boolean todos;
	private String accion;					//las terminales usan strings para activar/desactivar acciones
	
	public ProcesoAgregarAccionesParaUsuarios(ControlTerminales centralTerminales,Comuna unaComuna,String accion) {
		setCentralTerminales(centralTerminales);
		setComuna(unaComuna);
		setAccion(accion);
	}
	
	public ProcesoAgregarAccionesParaUsuarios(ControlTerminales centralTerminales,Terminal unaTerminal, String accion) {
		setCentralTerminales(centralTerminales);
		setTerminal(unaTerminal);
		setAccion(accion);
	}
	
	public ProcesoAgregarAccionesParaUsuarios(ControlTerminales centralTerminales,String accion) {
		setCentralTerminales(centralTerminales);
		setTodos(true);
		setAccion(accion);
	}

	public void run() {
		if(isTodos()){
		getCentralTerminales().setearOpcion(getAccion());}
		if(getComuna()!=null){
			getCentralTerminales().setearOpcion(getComuna(),getAccion());}
		if(getTerminal()!=null){
			getCentralTerminales().setearOpcion(getTerminal(),getAccion());}
	}

	public ControlTerminales getCentralTerminales() {
		return centralTerminales;
	}

	public void setCentralTerminales(ControlTerminales centralTerminales) {
		this.centralTerminales = centralTerminales;
	}

	public Comuna getComuna() {
		return comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	

}
