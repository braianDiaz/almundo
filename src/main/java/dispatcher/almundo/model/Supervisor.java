package dispatcher.almundo.model;

/**
 * 
 * @author Braian
 *
 */
public class Supervisor extends Employee {

	public Supervisor(String name) {
		super(name);
		setTelephoneAttentionPriority(TELEPHONE_ATTENTION_PRIORITY.MEDIA);
	}

}
