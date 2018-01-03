/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.ws;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class NNotaCallWS {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	private RestTemplate restTemplate;
	private String URL_WS_BASE = "";
	private HttpHeaders headers = new HttpHeaders();
	private final Properties props = new Properties();

	public NNotaCallWS() {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

		if (factory instanceof SimpleClientHttpRequestFactory) {
			((SimpleClientHttpRequestFactory) factory).setConnectTimeout(15 * 1000);
			((SimpleClientHttpRequestFactory) factory).setReadTimeout(15 * 1000);
			logger.debug("Inicializando rest template 1");
		} else if (factory instanceof HttpComponentsClientHttpRequestFactory) {
			((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout(15 * 1000);
			((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout(15 * 1000);
			logger.debug("Inicializando rest template 2");
		}

		restTemplate.setRequestFactory(factory);
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			props.load(this.getClass().getResourceAsStream("/general.properties"));
		} catch (Exception e) {
			logger.error("[NNotaCallWS::init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
			logger.debug("[NNotaCallWS::init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
		}
		String ambiente = props.getProperty("ambiente");
		URL_WS_BASE = props.getProperty(ambiente + ".url.ws.base");
	}


}
