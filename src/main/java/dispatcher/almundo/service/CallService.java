package dispatcher.almundo.service;

import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import dispatcher.almundo.model.Call;
import dispatcher.almundo.model.Call.CALL_STATUS;
import dispatcher.almundo.model.intf.CallOperator;
import dispatcher.almundo.service.intf.Listener;

/**
 * Servicio de llamado , simula la coneccion de llamada
 * 
 * @author Braian
 *
 */
public class CallService implements Callable<CallOperator>{
	
	final static Logger logger = Logger.getLogger(CallService.class);
	
	private CallOperator callOperator ;
	private Listener listener;
	private Call call;
	
    public CallService(Listener listener , CallOperator callOperator , Call call) {
    	call.setCallStatus(CALL_STATUS.ATTENDED);
    	this.call = call;
    	this.callOperator = callOperator;
    	this.listener = listener;
    }


	public CallOperator call() {
		try {
			
			logger.info("Comenzando llamada nro: " + this.call.getCallNumber() +" con empleado: " + callOperator.getName()  + " "+  Thread.currentThread().getName());
			
			Random random = new Random();
			Thread.sleep(random.nextInt(10000 - 5000) + 5000);
		
			logger.info("Finalizando llamada nro: " + this.call.getCallNumber() +" con empleado: " + callOperator.getName()  + " "+  Thread.currentThread().getName());
			
		} catch (Exception e) {
			this.call.setCallStatus(CALL_STATUS.CANCELED);
			logger.info("Cancelando llamada nro: " + this.call.getCallNumber() +" exception : " +  ExceptionUtils.getMessage(e));
			
		} finally {
			listener.callTermination(callOperator);
		}
				
		return callOperator;
	}
	

}
