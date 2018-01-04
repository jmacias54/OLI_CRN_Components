/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.bo;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import mx.com.amx.unotv.oli.crn.components.bo.exception.GenerarComponentesBOException;
import mx.com.amx.unotv.oli.crn.components.model.NNota;
import mx.com.amx.unotv.oli.crn.components.util.Constants;
import mx.com.amx.unotv.oli.crn.components.util.CrearTemplates;
import mx.com.amx.unotv.oli.crn.components.ws.NNotaCallWS;


/**
 * @author Jesus A. Macias Benitez
 *
 */
public class GenerarComponentesBO {

	private final Logger logger = Logger.getLogger(this.getClass().getName());


	@Autowired
	private NNotaCallWS nNotaCallWS;
	
	@Autowired
	private CrearTemplates crearTemplates;
	

	public void writeHtml() throws GenerarComponentesBOException {

		logger.debug(" --- writeHtml  [ GenerarComponentesBO ]---- ");

		List<NNota> listaHome = null;
		List<NNota> listaNoticias = null;
		List<NNota> listaVideos = null;
		List<NNota> listaPanel = null;
		List<NNota> listaLastNotesNotInINotaMagazine= null;

		try {
			
			logger.debug(" --- listaHome ---- ");
			listaHome = nNotaCallWS.findByIdMagazine(Constants.MAGAZINE_HOME);
			crearTemplates.creaMagazineHome(listaHome);
			
			
			logger.debug(" --- listaPanel ---- ");
			listaPanel = nNotaCallWS.findByIdMagazine(Constants.MAGAZINE_PANEL);
			crearTemplates.creaTopNews(listaPanel);
			
			logger.debug(" --- Las últiams 4 notas quitando las que estén en el magazine-home y magazine panel.  ---- ");
			listaLastNotesNotInINotaMagazine = nNotaCallWS.lastNotesNotInINotaMagazine(Constants.LIMIT_4);
			crearTemplates.creaMoreNews(listaLastNotesNotInINotaMagazine);
			
			
			/* oli_mx_n_nota que id_categoria = noticias 50 notas más recientes */
			logger.debug(" --- id_categoria = noticias 50 notas más recientes ---- ");
			listaNoticias = nNotaCallWS.lastNotesFindByIdCategoriaLimit(Constants.CATEGORIA_NOTICIAS, Constants.LIMIT_50);
			crearTemplates.creaHomeNoticias(listaNoticias);
			
			
			logger.debug(" --- id_categoria = videos 50 notas más recientes---- ");
			listaVideos = nNotaCallWS.lastNotesFindByIdCategoriaLimit(Constants.CATEGORIA_VIDEOS, Constants.LIMIT_50);
			crearTemplates.creaHomeVideo(listaVideos);
			

		} catch (Exception e) {
			logger.error(" ¡ Error  writeHtml  [ GenerarComponentesBO ] !" + e.getMessage());
			throw new GenerarComponentesBOException(e.getMessage());
		}

	

	}
	
	
}
