package procesos;

import java.sql.Date;
import java.util.List;
import java.util.Timer;


public class ControlProcesos {
	private List<Proceso> procesos;
	private ManejoDeResultadosProcesos manejoResultados;
	
	
	public void agregarProceso(Proceso procesoNuevo, Date fechaYhorarioEjecucion){
		Timer timer = new Timer();
		timer.schedule(procesoNuevo, fechaYhorarioEjecucion);  
	}
	
	
	public List<Proceso> getProcesos() {
		return procesos;
	}


	public void setProcesos(List<Proceso> procesos) {
		this.procesos=procesos;
		
	}


	public void manejarFallas(Proceso proceso) {
		manejoResultados.manejarError(proceso);
	}


}
