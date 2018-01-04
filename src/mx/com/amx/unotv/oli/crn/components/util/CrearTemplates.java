/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import mx.com.amx.unotv.oli.crn.components.dto.ParametrosDTO;
import mx.com.amx.unotv.oli.crn.components.model.NNota;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class CrearTemplates {
	private final static Logger logger = Logger.getLogger(CrearTemplates.class);

	private static ParametrosDTO parametros;
	private SimpleDateFormat formatter;
	private TimeZone tz ;
	private SimpleDateFormat df ;
	
	

	CrearTemplates() {
		CargaProperties props = new CargaProperties();
		parametros = props.obtenerPropiedades("ambiente.resources.properties");
		
		formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		tz = TimeZone.getTimeZone("America/Mexico_City");
		df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		df.setTimeZone(tz);
	}

	public void creaHomeNoticias(List<NNota> lista) {
		logger.debug(" --- creaHomeNoticias  ---- ");
		StringBuffer TEMPLATE_HOME_NOTICIAS;
		StringBuffer buffer_home_noticias;
		String PANEL_HOME_NOTICIAS = "";
		boolean success = false;

		try {

			buffer_home_noticias = new StringBuffer();
			TEMPLATE_HOME_NOTICIAS = new StringBuffer();
			TEMPLATE_HOME_NOTICIAS.append(Constants.TEMPLATE_HOME_NOTICIAS);
			
			logger.debug(" --- Contenido TEMPLATE_HOME_NOTICIAS --- ");
			
			for (NNota nota : lista) {
				
				Date date = formatter.parse(nota.getFdFechaPublicacion());
				buffer_home_noticias.append(" ");
				buffer_home_noticias.append(" <a class=\"item-news\" href=\""+nota.getFcFriendlyUrl()+"\"> ");
				buffer_home_noticias.append(" 	 <div class=\"thumb\">");
				buffer_home_noticias.append(" 		<i class=\"fas fa-play\"></i> ");
				buffer_home_noticias.append(" 		<img src=\""+nota.getFcImagen()+"\"> ");
				buffer_home_noticias.append(" 		<span class=\"date\">"+df.format(date)+"</span> ");
				buffer_home_noticias.append("    </div> ");
				buffer_home_noticias.append(" 	  <h3>"+ cambiaCaracteres( nota.getFcTitulo() )+"</h3> ");
				buffer_home_noticias.append(" </a>");
			
				
			} 
			
			PANEL_HOME_NOTICIAS = TEMPLATE_HOME_NOTICIAS.toString().replace(Constants.CONSTANT_CONTENT_HOME_NOTICIAS,
					buffer_home_noticias.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = writeHTML(parametros.getRutaArchivos() + "/" + parametros.getNombrePanelHomeNoticias(),
						PANEL_HOME_NOTICIAS);
				logger.info("Genero HTML : " + parametros.getNombrePanelHomeNoticias() + ": status :" + success);
			}

			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void creaHomeVideo(List<NNota> lista) {
		logger.debug(" --- creaHomeVideo  ---- ");
		StringBuffer TEMPLATE_HOME_VIDEOS;
		StringBuffer buffer_home_videos;
		String PANEL_HOME_VIDEOS = "";
		boolean success = false;

		try {
			
			buffer_home_videos = new StringBuffer();
			TEMPLATE_HOME_VIDEOS = new StringBuffer();
			TEMPLATE_HOME_VIDEOS.append(Constants.TEMPLATE_HOME_VIDEOS);
			
			
			
			logger.debug(" --- Contenido TEMPLATE_HOME_VIDEOS --- ");

			for (NNota nota : lista) {

				Date date = formatter.parse(nota.getFdFechaPublicacion());
				
				buffer_home_videos.append(" ");
				buffer_home_videos.append("   <a class=\"item-news\" href=\""+nota.getFcFriendlyUrl()+"\"> ");
				buffer_home_videos.append("     <div class=\"thumb\">");
				buffer_home_videos.append(" 		<i class=\"fas fa-play\"></i>");
				buffer_home_videos.append(" 		<img src=\""+nota.getFcImagen()+"\"> ");
				buffer_home_videos.append(" 		<span class=\"date\">"+df.format(date)+"</span>");
				buffer_home_videos.append(" 	</div>");
				buffer_home_videos.append(" 	 <h3>"+ cambiaCaracteres( nota.getFcTitulo( ))+"</h3>");
				buffer_home_videos.append("   </a>");
				
			}
			
			PANEL_HOME_VIDEOS = TEMPLATE_HOME_VIDEOS.toString().replace(Constants.CONSTANT_CONTENT_HOME_VIDEOS,
					buffer_home_videos.toString());

	

			/* si la carpeta se creo bien o ya esta creada */
			if (createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = writeHTML(parametros.getRutaArchivos() + "/" + parametros.getNombrePanelHomeVideos(),
						PANEL_HOME_VIDEOS);
				logger.info("Genero HTML : " + parametros.getNombrePanelHomeVideos() + ": status :" + success);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/* Metodo para crear HTML de PANEL MORE NEWS */
	public void creaMoreNews(List<NNota> lista) {
		logger.debug(" --- creaMoreNews ---- ");

		StringBuffer TEMPLATE_MORE_NEWS;
		StringBuffer buffer_more_news;
		StringBuffer buffer_more_news_fetured;
		String PANEL_MORE_NEWS = "";
		boolean success = false;

		try {

			buffer_more_news = new StringBuffer();
			buffer_more_news_fetured = new StringBuffer();
			TEMPLATE_MORE_NEWS = new StringBuffer();
			TEMPLATE_MORE_NEWS.append(Constants.TEMPLATE_MORE_NEWS);

			/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
			logger.debug(" --- Contenido PANEL MORE NEWS  --- ");

			
			Date fechaFeatured = formatter.parse(lista.get(0).getFdFechaPublicacion());

			

			buffer_more_news_fetured.append(" ");
			buffer_more_news_fetured
					.append(" <a class=\"item-news\" href=\"" + lista.get(0).getFcFriendlyUrl() + "\">");
			buffer_more_news_fetured.append(" 		<div class=\"thumb\"><img src=\"" + lista.get(0).getFcImagen()
					+ "\"><span class=\"date\">" + df.format(fechaFeatured) + "</span>");
			buffer_more_news_fetured.append(" 		</div>");
			buffer_more_news_fetured.append(" 	<h3>" + cambiaCaracteres( lista.get(0).getFcTitulo() ) + "</h3>");
			buffer_more_news_fetured.append(" </a> ");

			for (int i = 1; i < lista.size(); i++) {

				Date fecha = formatter.parse(lista.get(i).getFdFechaPublicacion());

				buffer_more_news.append(" ");
				buffer_more_news.append(" <a class=\"item-news\" href=\"" + lista.get(i).getFcFriendlyUrl() + "\"> ");
				buffer_more_news.append(" 	<div class=\"thumb\">");
				buffer_more_news.append(" 		<img src=\"" + lista.get(i).getFcImagen() + "\"> ");
				buffer_more_news.append(" 			<span class=\"date\">" + df.format(fecha) + "</span>");
				buffer_more_news.append(" 	 </div>");
				buffer_more_news.append(" 	  <h3>" + cambiaCaracteres( lista.get(i).getFcTitulo() )+ "</h3> ");
				buffer_more_news.append(" </a> ");
			}

			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_MORE_NEWS = TEMPLATE_MORE_NEWS.toString().replace(Constants.CONSTANT_CONTENT_PANEL_MORE_NEWS_FETURED,
					buffer_more_news_fetured.toString()).replace(Constants.CONSTANT_CONTENT_PANEL_MORE_NEWS, buffer_more_news.toString());

			// PANEL_MORE_NEWS = PANEL_MORE_NEWS.replaceAll("$CONTENT_PANEL_MORE_NEWS$", buffer_more_news.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelMoreNews(),
						PANEL_MORE_NEWS);
				logger.info("Genero HTML : " + parametros.getNombrePanelMoreNews() + ": status :" + success);
			}

		} catch (Exception e) {
			logger.error(" ¡ Error en creaMoreNews ! [ GenerarComponentesBO ] : ", e);

		}
	}

	/* Metodo para crear HTML de PANEL TOP NEWS */
	public void creaTopNews(List<NNota> lista) {
		logger.debug(" --- creaTopNews ---- ");

		StringBuffer TEMPLATE_TOP_NEWS;
		StringBuffer buffer_top_news;
		String PANEL_TOP_NEWS = "";
		boolean success = false;

		try {

			buffer_top_news = new StringBuffer();
			TEMPLATE_TOP_NEWS = new StringBuffer();
			TEMPLATE_TOP_NEWS.append(Constants.TEMPLATE_TOP_NEWS);

			/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
			logger.debug(" --- Contenido PANEL TOP NEWS  --- ");

			for (NNota nota : lista) {

				
				Date fecha = formatter.parse(nota.getFdFechaPublicacion());

				buffer_top_news.append("");
				buffer_top_news.append(" <a class=\"item-news\" href=\"" + nota.getFcFriendlyUrl() + "\"> ");
				buffer_top_news.append(" <div class=\"thumb\"><img src=\"" + nota.getFcImagen()
						+ "\"><span class=\"date\">" + df.format(fecha) + "</span>");
				buffer_top_news.append(" </div>");
				buffer_top_news.append("	 <h3>" + cambiaCaracteres( nota.getFcTitulo() )+ "</h3> ");
				buffer_top_news.append(" </a>");

			}

			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_TOP_NEWS = TEMPLATE_TOP_NEWS.toString().replace(Constants.CONSTANT_CONTENT_PANEL_TOP_NEWS ,
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

	/*
	 * Metodo para crear los HTML de PANEL_MAGAZINE Y PANEL_NEWS del magazine-home
	 */
	public void creaMagazineHome(List<NNota> lista) {
		logger.debug(" --- creaMagazineHome  ---- ");

		StringBuffer TEMPLATE_PANEL_MAGAZINE, TEMPLATE_PANEL_NEWS;
		StringBuffer buffer_panel_magazine, buffer_panel_news;
		String PANEL_MAGAZINE = "";
		String PANEL_NEWS = "";
		boolean success = false;

		try {
			buffer_panel_magazine = new StringBuffer();
			buffer_panel_news = new StringBuffer();
			TEMPLATE_PANEL_MAGAZINE = new StringBuffer();
			TEMPLATE_PANEL_NEWS = new StringBuffer();

			TEMPLATE_PANEL_MAGAZINE.append(Constants.TEMPLATE_PANEL_MAGAZINE);

			TEMPLATE_PANEL_NEWS.append(Constants.TEMPLATE_PANEL_NEWS);

			/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
			logger.debug(" --- Contenido PANEL MAGAZINE --- ");
			for (int i = 0; i < 4; i++) {

				buffer_panel_magazine.append("");
				buffer_panel_magazine
						.append(" <a class=\"item-magazine\" href=\"" + lista.get(i).getFcFriendlyUrl() + "\"> ");
				buffer_panel_magazine.append("  <img src=\" " + lista.get(i).getFcImagen() + " \"> ");
				buffer_panel_magazine.append(" 	<div><span class=\"tag\">" + cambiaCaracteres( lista.get(i).getFcTitulo() ) + "</span> ");
				buffer_panel_magazine.append(" 	  <div class=\"item-info\"><span class=\"category\"></span>");
				buffer_panel_magazine.append("          <h2> " + cambiaCaracteres( lista.get(i).getFcDescripcion() ) + "</h2>");
				buffer_panel_magazine.append("   </div></div>");
				buffer_panel_magazine.append(" </a> ");

			}

			/* for para generar el contenido de TEMPLATE_PANEL_NEWS */
			logger.debug(" --- Contenido PANEL NEWS --- ");
			for (int j = 3; j < lista.size(); j++) {

				
				Date fecha = formatter.parse(lista.get(j).getFdFechaPublicacion());

				buffer_panel_news.append("");
				buffer_panel_news.append(" <a class=\"item-news\" href=\"" + lista.get(j) + "\"> ");
				buffer_panel_news.append("   <div class=\"thumb\"><img src=\"" + lista.get(j).getFcImagen() + "\"> ");
				buffer_panel_news.append("		<span class=\\\"date\\\">" + df.format(fecha) + "</span> </div>");
				buffer_panel_news.append("			<h3>" + cambiaCaracteres(lista.get(j).getFcTitulo()) + "</h3>");
				buffer_panel_news.append(" </a>");
			}

			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_MAGAZINE = TEMPLATE_PANEL_MAGAZINE.toString().replace(Constants.CONSTANT_CONTENT_PANEL_MAGAZINE,
					buffer_panel_magazine.toString());
			PANEL_NEWS = TEMPLATE_PANEL_NEWS.toString().replace(Constants.CONSTANT_CONTENT_PANEL_NEWS, buffer_panel_news.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelMagazine(),
						PANEL_MAGAZINE);
				logger.info("Genero HTML : " + parametros.getNombrePanelMagazine() + ": status :" + success);

				success = writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelNews(),
						PANEL_NEWS);
				logger.info("Genero HTML : " + parametros.getNombrePanelNews() + ": status :" + success);

			}

		} catch (Exception e) {
			logger.error(" ¡ Error en creaPanelMagazine ! [ GenerarComponentesBO ] : ", e);

		}
	}

	private static boolean writeHTML(String rutaHMTL, String HTML) {
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

	private static boolean createFolders(String carpetaContenido) {
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

	
	/**
	  * Clase para la codificacion de Caracteres
	  * @param String, Texto a codificar
	  * @return String, Texto codificado
	  * */
	private	static  String cambiaCaracteres(String texto) {
			texto = texto.replaceAll("á", "&#225;");
	        texto = texto.replaceAll("é", "&#233;");
	        texto = texto.replaceAll("í", "&#237;");
	        texto = texto.replaceAll("ó", "&#243;");
	        texto = texto.replaceAll("ú", "&#250;");  
	        texto = texto.replaceAll("Á", "&#193;");
	        texto = texto.replaceAll("É", "&#201;");
	        texto = texto.replaceAll("Í", "&#205;");
	        texto = texto.replaceAll("Ó", "&#211;");
	        texto = texto.replaceAll("Ú", "&#218;");
	        texto = texto.replaceAll("Ñ", "&#209;");
	        texto = texto.replaceAll("ñ", "&#241;");        
	        texto = texto.replaceAll("ª", "&#170;");          
	        texto = texto.replaceAll("ä", "&#228;");
	        texto = texto.replaceAll("ë", "&#235;");
	        texto = texto.replaceAll("ï", "&#239;");
	        texto = texto.replaceAll("ö", "&#246;");
	        texto = texto.replaceAll("ü", "&#252;");    
	        texto = texto.replaceAll("Ä", "&#196;");
	        texto = texto.replaceAll("Ë", "&#203;");
	        texto = texto.replaceAll("Ï", "&#207;");
	        texto = texto.replaceAll("Ö", "&#214;");
	        texto = texto.replaceAll("Ü", "&#220;");
	        texto = texto.replaceAll("¿", "&#191;");
	        texto = texto.replaceAll("“", "&#8220;");        
	        texto = texto.replaceAll("”", "&#8221;");
	        texto = texto.replaceAll("‘", "&#8216;");
	        texto = texto.replaceAll("’", "&#8217;");
	        texto = texto.replaceAll("…", "...");
	        texto = texto.replaceAll("¡", "&#161;");
	        texto = texto.replaceAll("¿", "&#191;");
	        texto = texto.replaceAll("°", "&#176;");
	        
	        texto = texto.replaceAll("–", "&#8211;");
	        texto = texto.replaceAll("—", "&#8212;");
	        //texto = texto.replaceAll("\"", "&#34;");
			return texto;
		}
}
