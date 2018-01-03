/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.bo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.amx.unotv.oli.crn.components.ws.INotaMagazineCallWS;
import mx.com.amx.unotv.oli.crn.components.ws.NNotaCallWS;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class GenerarComponentesBO {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Autowired
	INotaMagazineCallWS iNotaMagazineCallWS;
	
	@Autowired
	NNotaCallWS nNotaCallWS;
	
	
	public org.w3c.dom.Document writeHtml() {
		
		logger.debug(" --- writeHtml  [ GenerarComponentesBO ]---- ");
		
		
		return null;
		
	}

}
