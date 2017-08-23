package dispatcher.almundo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import dispatcher.almundo.model.Call;
import dispatcher.almundo.model.Call.CALL_STATUS;
import dispatcher.almundo.model.intf.CallOperator;
import dispatcher.almundo.service.intf.Listener;

/**
 * Clase destinada al manejo de empleados operadores de llamadas ,
 * y las llamadas entrantes
 * 
 * @author Braian
 *
 */
public class DispatcherService extends TimerTask implements Listener {
	
	final static Logger logger = Logger.getLogger(DispatcherService.class);
	
	private long callTimerConfigurationInSeconds;
	
	//Cola de llamadas
	Queue<Call> calls =  new LinkedBlockingQueue<Call>();
	//Cola de prioridad de empleados
	PriorityQueue<CallOperator> callOperators = new PriorityQueue<CallOperator>(1 ,new Comparator<CallOperator>() {
		//comparador para ordenar por prioridades
        public int compare(CallOperator cp, CallOperator cp2) {
            if(cp.getPriority() < cp2.getPriority()) return 1;
            else if(cp.getPriority() > cp2.getPriority()) return -1;
            else return 0;
        }
    });
	
	
	/**
	 * 
	 * @param callTimerConfigurationInSeconds para saber cuanto tiempo pueden estar en espera una llamada en segundos
	 */
	public DispatcherService(long callTimerConfigurationInSeconds) {
		this.callTimerConfigurationInSeconds = callTimerConfigurationInSeconds;
		Timer time = new Timer(); 
		//Tarea para chequear si hay llamadas en espera 
		time.schedule(this, 0, 1000);
	}
	
	
	/**
	 * Llamada entrante
	 * @param p
	 */
    public void dispatchCall(Call call){
    	//si la cola de empleados esta vacia se almacena la llamada por X cantidad
    	//de tiempo
        if(callOperators.isEmpty()){
            calls.offer(call);
        }else{
        	//sino chequea la cantidad de llamadas atendidas en el momento
        	//TODO: chequear
        	//se toma la llamada
        	takeCall( callOperators.poll() , call);
        }
    }
	
	
    /**
     *Toma un nuevo empleado que pueda recibir llamadas
     *chequea si no hay llamadas en esperas , si hay toma una 
     * 
     * @param callOperator
     * @return si se agrego o uno el nuevo operador
     */
	public boolean addCallOperator(CallOperator callOperator){
		if(callOperators.offer( callOperator  )){
			if(!calls.isEmpty()){
				Call call = calls.poll();
				if(CALL_STATUS.ON_HOLD.equals(call.getCallStatus())){
					takeCall( callOperators.poll() , call);
				}
			}
			
			return true;		
		}
		
		return false;
	}

	
	/**
	 * Hace la coneccion entre el empleado y la llamada
	 * 
	 * @param callOperator
	 * @param call
	 */
	private void takeCall(CallOperator callOperator , Call  call) {
		//Ejecuta la llamada en un hilo aparte , para soportar concurrencia
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(new CallService(this , callOperator , call ));
	}
	
	/**
	 * Al terminar una llamada se vuelve a la cola de espera de llamados
	 * 
	 *  @param callOperator
	 */
	public void callTermination(CallOperator callOperator) {
		addCallOperator( callOperator );
	}

	private void checkCalls() {
		List<Call> callsRemove = new ArrayList<Call>();
		
		for (Call call : calls) {
			if(DateUtils.getDateDiff(call.getTimeEntry(), new Date()) > callTimerConfigurationInSeconds){
				call.setCallStatus(CALL_STATUS.CANCELED);
				logger.info("Cancelada llamada numero : " + call.getCallNumber());
				callsRemove.add(call);
			}
		}
		
		calls.removeAll(callsRemove);
	}


	@Override
	public void run() {
		logger.info("Chequeando llamadas en esperas");
		checkCalls();
	}

	
	
}
