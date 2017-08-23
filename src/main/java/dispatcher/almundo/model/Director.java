package dispatcher.almundo.model;

/**
 * 
 * @author Braian
 *
 */
public class Director extends Employee{

	public Director(String name) {
		super(name);
		setTelephoneAttentionPriority(TELEPHONE_ATTENTION_PRIORITY.MINIMA);

	}

}
