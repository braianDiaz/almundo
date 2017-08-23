package dispatcher.almundo;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dispatcher.almundo.model.Call;
import dispatcher.almundo.model.Director;
import dispatcher.almundo.model.Operator;
import dispatcher.almundo.model.Supervisor;
import dispatcher.almundo.service.DispatcherService;

public class DispatcherServiceTest {

	DispatcherService dispatcherService;

	@Before
	public void init() {
    	org.apache.log4j.BasicConfigurator.configure();
		dispatcherService = new DispatcherService(2);
	}

	@Test
	public void calls() throws InterruptedException {
		prepareCallOperator();
		
		for (int i = 0; i < 12; i++) {
			dispatcherService.dispatchCall(new Call(new Date() , i));
			Thread.sleep(1000);
		}

		Thread.sleep(1000);

		for (int i = 12; i < 20; i++) {
			dispatcherService.dispatchCall(new Call(new Date() , i));
			Thread.sleep(2500);
		}
	}
	
	
	//Test para cancelar una callada por espera
	//@Test
	public void callToRemove() throws InterruptedException {
		dispatcherService.addCallOperator(new Operator("Operador 1"));
		dispatcherService.dispatchCall(new Call(new Date(), 20));
		dispatcherService.dispatchCall(new Call(new Date(), 21));
		Thread.sleep(1000);
	}
	
	@Test
	public void callWithoutOperator() throws InterruptedException {
		dispatcherService.dispatchCall(new Call(new Date(), 20));
		Thread.sleep(10000);
	}
	
	private void prepareCallOperator(){
		dispatcherService.addCallOperator(new Director("Director 1"));
		dispatcherService.addCallOperator(new Supervisor("Supervisor 1"));
		dispatcherService.addCallOperator(new Operator("Operador 1"));
		dispatcherService.addCallOperator(new Operator("Operador 2"));
		dispatcherService.addCallOperator(new Supervisor("Supervisor 2"));
		dispatcherService.addCallOperator(new Operator("Operador 3"));
		dispatcherService.addCallOperator(new Operator("Operador 4"));
		dispatcherService.addCallOperator(new Director("Director 2"));
		dispatcherService.addCallOperator(new Operator("Operador 5"));
		dispatcherService.addCallOperator(new Operator("Operador 6"));
	}
}
