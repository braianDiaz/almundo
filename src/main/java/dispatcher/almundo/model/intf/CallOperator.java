package dispatcher.almundo.model.intf;

import dispatcher.almundo.model.Call;

/**
 * Inteface para clases que puedan atender llamados
 * 
 * @author Braian
 *
 */
public interface CallOperator {

	public void takeCall(Call call);
	public String getName();
	public int getPriority();
}
