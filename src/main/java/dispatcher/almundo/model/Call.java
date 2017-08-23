package dispatcher.almundo.model;

import java.util.Date;

/**
 * Clase que representa una llamda telefonica
 * 
 * @author Braian
 *
 */
public class Call {

	public enum CALL_STATUS{

		ON_HOLD ,ATTENDED , CANCELED
	}
	
	private Date timeEntry;
	private CALL_STATUS callStatus = CALL_STATUS.ON_HOLD;
	private long callNumber;
	
	public Call(Date timeEntry, long callNumber) {
		this.timeEntry = timeEntry;
		this.callNumber = callNumber;
	}

	public Date getTimeEntry() {
		return timeEntry;
	}

	public CALL_STATUS getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(CALL_STATUS callStatus) {
		this.callStatus = callStatus;
	}

	public long getCallNumber() {
		return callNumber;
	}

}
