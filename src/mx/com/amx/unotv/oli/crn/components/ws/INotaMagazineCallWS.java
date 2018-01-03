/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.ws;

import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import mx.com.amx.unotv.oli.crn.components.model.INotaMagazine;
import mx.com.amx.unotv.oli.crn.components.response.INotaMagazineResponse;
import mx.com.amx.unotv.oli.crn.components.util.Constants;
import mx.com.amx.unotv.oli.crn.components.ws.exception.INotaMagazineCallWSException;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class INotaMagazineCallWS{
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	private RestTemplate restTemplate;
	private String URL_WS_BASE = "";
	private HttpHeaders headers = new HttpHeaders();
	private final Properties props = new Properties();

	public INotaMagazineCallWS() {
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
			logger.error("[INotaMagazineCallWS::init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
			logger.debug("[INotaMagazineCallWS::init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
		}
		String ambiente = props.getProperty("ambiente");
		URL_WS_BASE = props.getProperty(ambiente + ".url.ws.base");
	}
	
	
	public List<INotaMagazine> findByIdMagazine( String idMagazine) throws INotaMagazineCallWSException{
		logger.debug(" --- findByIdMagazine  [ INotaMagazineCallWS  ]  --- ");
		logger.debug(" ---  IdMagazine : "+idMagazine+"  --- ");
		
		List<INotaMagazine> lista = null;
		
		String metodo = "/"+Constants.CONTROLLER_INOTA_MAGAZINE+"/"+Constants.METHOD_FIND_BY_IDMAGAZINE+"/" + idMagazine;
		String URL_WS = URL_WS_BASE + metodo;
		INotaMagazineResponse response = new INotaMagazineResponse();
		
		
		try {
			logger.debug("URL_WS: " + URL_WS);

			restTemplate = new RestTemplate();
			HttpEntity<String> entity = new HttpEntity<String>("Accept=application/json; charset=utf-8", headers);
			response = restTemplate.postForObject(URL_WS, entity, INotaMagazineResponse.class);

			lista = response.getLista();

		} catch (Exception e) {
			logger.error("Error findByIdMagazine [ INotaMagazineCallWS ]: ", e);
			throw new INotaMagazineCallWSException(e.getMessage());
		}
		
		
		return lista ;
		
	}

}
