/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.bo;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import mx.com.amx.unotv.oli.crn.components.bo.exception.GenerarComponentesBOException;
import mx.com.amx.unotv.oli.crn.components.dto.ParametrosDTO;
import mx.com.amx.unotv.oli.crn.components.model.NNota;
import mx.com.amx.unotv.oli.crn.components.util.CargaProperties;
import mx.com.amx.unotv.oli.crn.components.util.Constants;
import mx.com.amx.unotv.oli.crn.components.ws.INotaMagazineCallWS;
import mx.com.amx.unotv.oli.crn.components.ws.NNotaCallWS;


/**
 * @author Jesus A. Macias Benitez
 *
 */
public class GenerarComponentesBO {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private INotaMagazineCallWS iNotaMagazineCallWS;

	@Autowired
	private NNotaCallWS nNotaCallWS;
	

	
	private ParametrosDTO parametros;

	
	GenerarComponentesBO (){
		CargaProperties props = new CargaProperties();
		parametros = props.obtenerPropiedades("ambiente.resources.properties");
	}

	public org.w3c.dom.Document writeHtml() throws GenerarComponentesBOException {

		logger.debug(" --- writeHtml  [ GenerarComponentesBO ]---- ");

		List<NNota> listaHome = null;
		List<NNota> listaPanel = null;
		List<NNota> listaNoticias = null;

		try {
			
			
			listaHome = nNotaCallWS.findByIdMagazine(Constants.MAGAZINE_HOME);
			logger.debug(" --- listaHome ---- ");
			creaMagazineHome(listaHome);
			
			listaPanel = nNotaCallWS.findByIdMagazine(Constants.MAGAZINE_PANEL);
			logger.debug(" --- listaPanel ---- ");
			creaTopNews(listaPanel);
			

		} catch (Exception e) {
			logger.error(" ¡ Error  writeHtml  [ GenerarComponentesBO ] !" + e.getMessage());
			throw new GenerarComponentesBOException(e.getMessage());
		}

		return null;

	}
	
	/* Metodo para crear  HTML de PANEL TOP NEWS */
	public void creaTopNews(List<NNota> lista) {
		logger.debug(" --- creaTopNews ---- ");

		StringBuffer TEMPLATE_TOP_NEWS;
		StringBuffer buffer_top_news;
		String PANEL_TOP_NEWS = "";
		boolean success = false;

		try {

			buffer_top_news = new StringBuffer();
			TEMPLATE_TOP_NEWS = new StringBuffer();
			TEMPLATE_TOP_NEWS.append(" <div class=\"panel-top-news\"> $CONTENT_PANEL_TOP_NEWS$  </div> ");

			/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
			logger.debug(" --- Contenido PANEL TOP NEWS  --- ");
			
			
			 for (NNota nota : lista) {
				 
				 
				 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					Date f=formatter.parse(nota.getFdFechaPublicacion());
					
					TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
			        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
					df.setTimeZone(tz);
					
					
					
				 buffer_top_news.append("");
				 buffer_top_news.append(" <a class=\"item-news\" href=\""+nota.getFcFriendlyUrl()+"\"> ");
				 buffer_top_news.append(" <div class=\"thumb\"><img src=\""+nota.getFcImagen()+"\"><span class=\"date\">"+df.format(f)+"</span>");
				 buffer_top_news.append(" </div>");
				 buffer_top_news.append("	 <h3>"+nota.getFcTitulo()+"</h3> ");
				 buffer_top_news.append(" </a>");
				
				
			}

			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_TOP_NEWS = TEMPLATE_TOP_NEWS.toString().replace("$CONTENT_PANEL_TOP_NEWS$",
					buffer_top_news.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelTopNews(),
						PANEL_TOP_NEWS);
				logger.info("Genero HTML : " + parametros.getNombrePanelTopNews() + ": status :" + success);
			}

		} catch (Exception e) {
			logger.error(" ¡ Error en creaTopNews ! [ GenerarComponentesBO ] : ", e);

		}
	}
	
	/* Metodo para crear los HTML de PANEL_MAGAZINE Y PANEL_NEWS del magazine-home */
	public void creaMagazineHome( List<NNota> lista) {
		logger.debug(" --- creaMagazineHome  ---- ");
		
		StringBuffer TEMPLATE_PANEL_MAGAZINE ,TEMPLATE_PANEL_NEWS ;
		StringBuffer buffer_panel_magazine , buffer_panel_news;
		String PANEL_MAGAZINE ="";
		String PANEL_NEWS ="";
		boolean success = false;
		
		   
		
		try {
			buffer_panel_magazine = new StringBuffer();
			buffer_panel_news = new StringBuffer();
			TEMPLATE_PANEL_MAGAZINE = new StringBuffer();
			TEMPLATE_PANEL_NEWS = new StringBuffer();
			
			TEMPLATE_PANEL_MAGAZINE.append(" <section class='panel-magazine'> $CONTENT_PANEL_MAGAZINE$  </section> ");
			
			TEMPLATE_PANEL_NEWS.append(" <div class=\"panel-news\"> $CONTENT_PANEL_NEWS$      </div> ");
			
			/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
			logger.debug(" --- Contenido PANEL MAGAZINE --- ");
			for (int i= 0 ; i < 4 ; i ++) {
				
				buffer_panel_magazine.append("");
				buffer_panel_magazine.append(" <a class=\"item-magazine\" href=\""+lista.get(i).getFcFriendlyUrl()+"\"> ");
				buffer_panel_magazine.append("  <img src=\" "+lista.get(i).getFcImagen()+" \"> ");
				buffer_panel_magazine.append(" 	<div><span class=\"tag\">"+lista.get(i).getFcTitulo()+"</span> ");
				buffer_panel_magazine.append(" 	  <div class=\"item-info\"><span class=\"category\"></span>");
				buffer_panel_magazine.append("          <h2> "+lista.get(i).getFcDescripcion()+"</h2>");
				buffer_panel_magazine.append("   </div></div>");
				buffer_panel_magazine.append(" </a> ");
				
				
			}  
			
			/* for para generar el contenido de TEMPLATE_PANEL_NEWS */
			logger.debug(" --- Contenido PANEL NEWS --- ");
			for (int j= 3 ; j < lista.size() ; j ++) {
				
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date f=formatter.parse(lista.get(j).getFdFechaPublicacion());
				
				TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
		        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
				df.setTimeZone(tz);
				
				
				buffer_panel_news.append("");
				buffer_panel_news.append(" <a class=\"item-news\" href=\""+lista.get(j)+"\"> ");
				buffer_panel_news.append("   <div class=\"thumb\"><img src=\""+lista.get(j).getFcImagen()+"\"> ");
				buffer_panel_news.append("		<span class=\\\"date\\\">"+ df.format(f) +"</span> </div>");
				buffer_panel_news.append("			<h3>"+lista.get(j).getFcTitulo()+"</h3>");
				buffer_panel_news.append(" </a>");
			}  
			
			/* se agrega el contenido a cada plantilla por medio de  su constante correspondiente */
			PANEL_MAGAZINE= TEMPLATE_PANEL_MAGAZINE.toString().replace("$CONTENT_PANEL_MAGAZINE$", buffer_panel_magazine.toString());
			PANEL_NEWS =  TEMPLATE_PANEL_NEWS.toString().replace("$CONTENT_PANEL_NEWS$", buffer_panel_news.toString());
			
			
			/* si la carpeta se creo bien o ya esta creada */
			if (createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelMagazine(), PANEL_MAGAZINE);
				logger.info("Genero HTML : " + parametros.getNombrePanelMagazine() + ": status :" + success);
				
				success = writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelNews(), PANEL_NEWS);
				logger.info("Genero HTML : " + parametros.getNombrePanelNews() + ": status :" + success);

			}
			
			
		} catch (Exception e) {
			logger.error(" ¡ Error en creaPanelMagazine ! [ GenerarComponentesBO ] : ", e);

		}
	}
	
	
	
	public  boolean writeHTML(String rutaHMTL, String HTML) {
		boolean success = false;
		try {
			FileWriter fichero = null;
			PrintWriter pw = null;
			try {
				fichero = new FileWriter(rutaHMTL);
				pw = new PrintWriter(fichero);
				pw.println(HTML);
				pw.close();
				success = true;
			} catch (Exception e) {
				logger.error("Error al obtener la plantilla " + rutaHMTL + ": ", e);
				success = false;
			} finally {
				try {
					if (null != fichero)
						fichero.close();
				} catch (Exception e2) {
					success = false;
					logger.error("Error al cerrar el file: ", e2);
				}
			}
		} catch (Exception e) {
			success = false;
			logger.error("Fallo al crear la plantilla: ", e);
		}
		return success;
	}
	
	public boolean createFolders(String carpetaContenido) {
		boolean success = false;
		try {
			File carpetas = new File(carpetaContenido);
			if (!carpetas.exists()) {
				success = carpetas.mkdirs();
			} else
				success = true;
		} catch (Exception e) {
			success = false;
			logger.error("Ocurrio error al crear las carpetas: ", e);
			logger.debug("Ocurrio error al crear las carpetas: ", e);
		}
		return success;
	}

}
