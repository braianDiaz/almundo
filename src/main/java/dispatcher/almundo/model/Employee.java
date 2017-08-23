package dispatcher.almundo.model;

import dispatcher.almundo.model.intf.CallOperator;

/**
 * Empleados que pueden atender llamados
 * 
 * @author Braian
 *
 */
public abstract class Employee implements CallOperator{
	
	
	public enum TELEPHONE_ATTENTION_PRIORITY{
		
		MINIMA(1), MEDIA(2), MAXIMA(3) ;
		
		private int priority;

		TELEPHONE_ATTENTION_PRIORITY(int priority){
			this.priority = priority;
		}
		
		public int getPriority(){
			return this.priority;
		}
	}
	
	private TELEPHONE_ATTENTION_PRIORITY telephoneAttentionPriority = TELEPHONE_ATTENTION_PRIORITY.MAXIMA;
	private String name;
	
	public Employee(String name){
		this.name = name;
	}
	
	
	/**
	 * 
	 * @param call
	 */
	public void takeCall(Call call) {
		
	}

	public String getName(){
		return this.name;
	}
	
	public int getPriority() {
		return telephoneAttentionPriority.getPriority();
	}

	public void setTelephoneAttentionPriority(TELEPHONE_ATTENTION_PRIORITY telephoneAttentionPriority) {
		this.telephoneAttentionPriority = telephoneAttentionPriority;
	}

}
