package dispatcher.almundo.service.intf;

import dispatcher.almundo.model.intf.CallOperator;

public interface Listener {

	public void callTermination(CallOperator callOperator);
}
