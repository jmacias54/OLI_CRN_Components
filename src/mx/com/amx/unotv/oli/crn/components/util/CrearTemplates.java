/*
*
 * 
 */
package mx.com.amx.unotv.oli.crn.components.util;


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
	private String URL_SERVER = "";
	
	

	CrearTemplates() {
		CargaProperties props = new CargaProperties();
		parametros = props.obtenerPropiedades("ambiente.resources.properties");
		
		formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		tz = TimeZone.getTimeZone("America/Mexico_City");
		df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		df.setTimeZone(tz);
		
		
		
		if(parametros.getAmbiente().equals(Constants.ENVIROMENT_DEV)) {
			URL_SERVER = Constants.URL_DEV;
		}else if(parametros.getAmbiente().equals(Constants.ENVIROMENT_PROD)) {
			URL_SERVER = Constants.URL_PROD;
		}
		
		
	}

	public void creaHomeNoticias(List<NNota> lista) {
		logger.debug(" --- creaHomeNoticias  ---- ");
		StringBuffer TEMPLATE_HOME_NOTICIAS;
		StringBuffer buffer_home_noticias;
		String PANEL_HOME_NOTICIAS = "";
		boolean success = false;
		String url = "";

		try {

			buffer_home_noticias = new StringBuffer();
			TEMPLATE_HOME_NOTICIAS = new StringBuffer();
			TEMPLATE_HOME_NOTICIAS.append(Constants.TEMPLATE_HOME_NOTICIAS);
			
			logger.debug(" --- Contenido TEMPLATE_HOME_NOTICIAS --- ");
			
			for (NNota nota : lista) {
				
				Date date = formatter.parse(nota.getFdFechaPublicacion());
				
				
				
				if(nota.getFcUrlExterna().trim().equals("")) {
					/* para poder obtener la friendly url con todo y base , */
					String base= URL_SERVER + Constants.BASE_URL_POR_CATEGORIA; 
					base = base.replace(Constants.CONSTANT_CATEGORIA, nota.getFcIdCategoria());
					
					 url = base+nota.getFcFriendlyUrl();
				}else {
					
					url = nota.getFcUrlExterna();
				}
				
				
				
				buffer_home_noticias.append(" ");
				buffer_home_noticias.append(" <a class=\"item-news\" href=\""+url+"\"> ");
				buffer_home_noticias.append(" 	 <div class=\"thumb\">");
		
				if(lista.get(0).getFcTipoNota().equals(Constants.TIPO_NOTA_VIDEO)) {
					buffer_home_noticias.append("				 <i class=\"fas fa-play\"></i> ");
				}else if(lista.get(0).getFcTipoNota().equals(Constants.TIPO_NOTA_GALERIA)) {
					buffer_home_noticias.append("				 <i class=\"fas fa-images\"></i> ");
				}
				
				
				buffer_home_noticias.append(" 		<img src=\""+nota.getFcImagen()+"\"> ");
				
				buffer_home_noticias.append(" 		<span class=\"date\">"+df.format(date)+"</span> ");
				buffer_home_noticias.append("    </div> ");
				buffer_home_noticias.append(" 	  <h3>"+ Utils.cambiaCaracteres( nota.getFcTitulo() )+"</h3> ");
				buffer_home_noticias.append(" </a>");
			
				
			} 
			
			PANEL_HOME_NOTICIAS = TEMPLATE_HOME_NOTICIAS.toString().replace(Constants.CONSTANT_CONTENT_HOME_NOTICIAS,
					buffer_home_noticias.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (Utils.createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = Utils.writeHTML(parametros.getRutaArchivos() + "/" + parametros.getNombrePanelHomeNoticias(),
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
		String url ="";

		try {
			
			buffer_home_videos = new StringBuffer();
			TEMPLATE_HOME_VIDEOS = new StringBuffer();
			TEMPLATE_HOME_VIDEOS.append(Constants.TEMPLATE_HOME_VIDEOS);
			
			
			
			logger.debug(" --- Contenido TEMPLATE_HOME_VIDEOS --- ");

			for (NNota nota : lista) {

				Date date = formatter.parse(nota.getFdFechaPublicacion());
				
				
				
				if(nota.getFcUrlExterna().trim().equals("")) {
					/* para poder obtener la friendly url con todo y base , */
					String base= URL_SERVER + Constants.BASE_URL_POR_CATEGORIA; 
					base = base.replace(Constants.CONSTANT_CATEGORIA, nota.getFcIdCategoria());
					
					 url = base+nota.getFcFriendlyUrl();
				}else {
					
					url = nota.getFcUrlExterna();
				}
				
				
			
				
				buffer_home_videos.append(" ");
				buffer_home_videos.append("   <a class=\"item-news\" href=\""+url+"\"> ");
				buffer_home_videos.append("     <div class=\"thumb\">");
				buffer_home_videos.append(" 		<i class=\"fas fa-play\"></i>");
				buffer_home_videos.append(" 		<img src=\""+nota.getFcImagen()+"\"> ");
				buffer_home_videos.append(" 		<span class=\"date\">"+df.format(date)+"</span>");
				buffer_home_videos.append(" 	</div>");
				buffer_home_videos.append(" 	 <h3>"+ Utils.cambiaCaracteres( nota.getFcTitulo( ))+"</h3>");
				buffer_home_videos.append("   </a>");
				
			}
			
			PANEL_HOME_VIDEOS = TEMPLATE_HOME_VIDEOS.toString().replace(Constants.CONSTANT_CONTENT_HOME_VIDEOS,
					buffer_home_videos.toString());

	

			/* si la carpeta se creo bien o ya esta creada */
			if (Utils.createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = Utils.writeHTML(parametros.getRutaArchivos() + "/" + parametros.getNombrePanelHomeVideos(),
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
		String base ="";
		String url ="" ;
		String url_2 ="" ;
		
		try {

			buffer_more_news = new StringBuffer();
			buffer_more_news_fetured = new StringBuffer();
			TEMPLATE_MORE_NEWS = new StringBuffer();
			TEMPLATE_MORE_NEWS.append(Constants.TEMPLATE_MORE_NEWS);

			
			if(lista.size() > 0) {
				/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
				logger.debug(" --- Contenido PANEL MORE NEWS  --- ");

				
				Date fechaFeatured = formatter.parse(lista.get(0).getFdFechaPublicacion());
			
				if(lista.get(0).getFcUrlExterna().trim().equals("")) {
					/* para poder obtener la friendly url con todo y base , */
					 base= URL_SERVER + Constants.BASE_URL_POR_CATEGORIA; 
					base = base.replace(Constants.CONSTANT_CATEGORIA, lista.get(0).getFcIdCategoria());
					
					 url = base+lista.get(0).getFcFriendlyUrl();
				}else {
					
					url = lista.get(0).getFcUrlExterna();
				}

				
				buffer_more_news_fetured.append(" 	<a class=\"item-news\" href=\""+url+"\"> ");
				buffer_more_news_fetured.append(" 	<div class=\"thumb\">");
				
				if(lista.get(0).getFcTipoNota().equals(Constants.TIPO_NOTA_VIDEO)) {
					buffer_more_news_fetured.append("				 <i class=\"fas fa-play\"></i> ");
				}else if(lista.get(0).getFcTipoNota().equals(Constants.TIPO_NOTA_GALERIA)) {
					buffer_more_news_fetured.append("				 <i class=\"fas fa-images\"></i> ");
				}
				
				
				buffer_more_news_fetured.append(" 		<img src=\""+ lista.get(0).getFcImagen()+"\"> ");
				buffer_more_news_fetured.append(" 	</div>");
				buffer_more_news_fetured.append(" 	<span class=\"date\">"+df.format(fechaFeatured) +"</span> ");
				buffer_more_news_fetured.append(" 	  <h3>"+ Utils.cambiaCaracteres( lista.get(0).getFcTitulo() ) +"</h3>");
				buffer_more_news_fetured.append(" </a>");
				buffer_more_news_fetured.append(" ");
			

				for (int i = 1; i < lista.size(); i++) {

					Date fecha = formatter.parse(lista.get(i).getFdFechaPublicacion());

					if (lista.get(i).getFcUrlExterna().trim().equals("")) {

						url_2 = base + lista.get(i).getFcFriendlyUrl();
					} else {
						url_2 = lista.get(i).getFcUrlExterna();
					}
					
					buffer_more_news.append("   <a class=\"item-news\" href=\""+ url_2 +"\"> ");
					buffer_more_news.append(" 		 <div class=\"thumb\"> ");
					

					if(lista.get(0).getFcTipoNota().equals(Constants.TIPO_NOTA_VIDEO)) {
						buffer_more_news_fetured.append("				 <i class=\"fas fa-play\"></i> ");
					}else if(lista.get(0).getFcTipoNota().equals(Constants.TIPO_NOTA_GALERIA)) {
						buffer_more_news_fetured.append("				 <i class=\"fas fa-images\"></i> ");
					}
					
					
					buffer_more_news.append(" 			<img src=\""+ lista.get(i).getFcImagen() +"\"> ");
					buffer_more_news.append(" 		 </div>");
					buffer_more_news.append(" 		<span class=\"date\">"+df.format(fecha) +"</span>");
					buffer_more_news.append(" 		 <h3>"+Utils.cambiaCaracteres( lista.get(i).getFcTitulo() )+"</h3>");
					buffer_more_news.append(" 	 </a>");
					buffer_more_news.append(" ");
				
				}

				
			}// end if 
			
			
			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_MORE_NEWS = TEMPLATE_MORE_NEWS.toString().replace(Constants.CONSTANT_CONTENT_PANEL_MORE_NEWS_FETURED,
					buffer_more_news_fetured.toString()).replace(Constants.CONSTANT_CONTENT_PANEL_MORE_NEWS, buffer_more_news.toString());

			// PANEL_MORE_NEWS = PANEL_MORE_NEWS.replaceAll("$CONTENT_PANEL_MORE_NEWS$", buffer_more_news.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (Utils.createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success =Utils.writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelMoreNews(),
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
		String url = "";
		

		try {

			buffer_top_news = new StringBuffer();
			TEMPLATE_TOP_NEWS = new StringBuffer();
			TEMPLATE_TOP_NEWS.append(Constants.TEMPLATE_TOP_NEWS);

			/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
			logger.debug(" --- Contenido PANEL TOP NEWS  --- ");

			for (NNota nota : lista) {

				
				Date fecha = formatter.parse(nota.getFdFechaPublicacion());
			
				if (nota.getFcUrlExterna().trim().equals("")) {
					/* para poder obtener la friendly url con todo y base , */
					String base= URL_SERVER + Constants.BASE_URL_POR_CATEGORIA; 
					base = base.replace(Constants.CONSTANT_CATEGORIA, nota.getFcIdCategoria());
					url = base+nota.getFcFriendlyUrl();
				} else {
					url = nota.getFcUrlExterna();
				}
				
				
				buffer_top_news.append("	  <a class=\"item-news\" href=\""+url+"\"> ");
				buffer_top_news.append("		<div class=\"thumb\"> ");
				
				if(nota.getFcTipoNota().equals(Constants.TIPO_NOTA_VIDEO)) {
					buffer_top_news.append("				 <i class=\"fas fa-play\"></i> ");
				}else if(nota.getFcTipoNota().equals(Constants.TIPO_NOTA_GALERIA)) {
					buffer_top_news.append("				 <i class=\"fas fa-images\"></i> ");
				}
				
				buffer_top_news.append("					  <img src=\""+ nota.getFcImagen() +"\"> ");
				buffer_top_news.append("		 </div> ");
				buffer_top_news.append("		   <span class=\"date\">"+ df.format(fecha) +"</span> ");
				buffer_top_news.append("			<h3>" + Utils.cambiaCaracteres( nota.getFcTitulo() )+"</h3>");
				buffer_top_news.append("	 </a>");
				buffer_top_news.append("");
				

			}

			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_TOP_NEWS = TEMPLATE_TOP_NEWS.toString().replace(Constants.CONSTANT_CONTENT_PANEL_TOP_NEWS ,
					buffer_top_news.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (Utils.createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = Utils.writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelTopNews(),
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
		String url = "";
		

		try {
			buffer_panel_magazine = new StringBuffer();
			buffer_panel_news = new StringBuffer();
			TEMPLATE_PANEL_MAGAZINE = new StringBuffer();
			TEMPLATE_PANEL_NEWS = new StringBuffer();

			TEMPLATE_PANEL_MAGAZINE.append(Constants.TEMPLATE_PANEL_MAGAZINE);

			TEMPLATE_PANEL_NEWS.append(Constants.TEMPLATE_PANEL_NEWS);

			
			if(lista.size() > 0) {
				
				
				/* for para generar el contenido de TEMPLATE_PANEL_MAGAZINE */
				logger.debug(" --- Contenido PANEL MAGAZINE --- ");
				for (int i = 0; i < 4; i++) {
					
				
					
					if (lista.get(i).getFcUrlExterna().trim().equals("")) {
						/* para poder obtener la friendly url con todo y base , */
						String base= URL_SERVER + Constants.BASE_URL_POR_CATEGORIA; 
						base = base.replace(Constants.CONSTANT_CATEGORIA, lista.get(i).getFcIdCategoria());
						url = base+lista.get(i).getFcFriendlyUrl();
					} else {
						url = lista.get(i).getFcUrlExterna();
					}
					
					
					
				
					buffer_panel_magazine.append("	<a class=\"item-magazine\" href=\""+url+"\"> ");
					if(lista.get(i).getFcTipoNota().equals(Constants.TIPO_NOTA_VIDEO)) {
						buffer_panel_magazine.append("				 <i class=\"fas fa-play\"></i> ");
					}else if(lista.get(i).getFcTipoNota().equals(Constants.TIPO_NOTA_GALERIA)) {
						buffer_panel_magazine.append("				 <i class=\"fas fa-images\"></i> ");
					}
					buffer_panel_magazine.append("		<img src=\""+lista.get(i).getFcImagen()+"\"> ");
					buffer_panel_magazine.append("		    <div>");
					if(lista.get(i).getFcLiveVod() != null && !lista.get(i).getFcLiveVod().trim().equals("")) {
						buffer_panel_magazine.append(" <span class=\"tag\">"+lista.get(i).getFcLiveVod()+"</span> ");
					}
					buffer_panel_magazine.append("				<div class=\"item-info\"><span class=\"category\"></span> ");
					buffer_panel_magazine.append("				 <h2>"+Utils.cambiaCaracteres(lista.get(i).getFcTitulo())+"</h2>");
					buffer_panel_magazine.append("				</div>");
					buffer_panel_magazine.append("			</div>");
					buffer_panel_magazine.append("	</a>");
					buffer_panel_magazine.append("");
					buffer_panel_magazine.append("");

				}

				/* for para generar el contenido de TEMPLATE_PANEL_NEWS */
				logger.debug(" --- Contenido PANEL NEWS --- ");
				for (int j = 3; j < lista.size(); j++) {

					
					Date fecha = formatter.parse(lista.get(j).getFdFechaPublicacion());
					
					if (lista.get(j).getFcUrlExterna().trim().equals("")) {
						/* para poder obtener la friendly url con todo y base , */
						String base= URL_SERVER + Constants.BASE_URL_POR_CATEGORIA; 
						base = base.replace(Constants.CONSTANT_CATEGORIA, lista.get(j).getFcIdCategoria());
						url = base+lista.get(j).getFcFriendlyUrl();
					} else {
						url = lista.get(j).getFcUrlExterna();
					}
					
					
					
					buffer_panel_news.append(" <a  class=\"item-news\" href=\""+url+"\"> ");
					buffer_panel_news.append("		 <div class=\"thumb\"> ");
					
					if(lista.get(j).getFcTipoNota().equals(Constants.TIPO_NOTA_VIDEO)) {
						buffer_panel_news.append("				 <i class=\"fas fa-play\"></i> ");
					}else if(lista.get(j).getFcTipoNota().equals(Constants.TIPO_NOTA_GALERIA)) {
						buffer_panel_news.append("				 <i class=\"fas fa-images\"></i> ");
					}
					
					buffer_panel_news.append(" 			<img src=\""+lista.get(j).getFcImagen()+"\"> ");
					buffer_panel_news.append("			<span class=\"date\">"+ df.format(fecha) +"</span>");
					buffer_panel_news.append("		 </div> ");
					buffer_panel_news.append("		<h3>"+Utils.cambiaCaracteres(lista.get(j).getFcTitulo())+"</h3>");
					buffer_panel_news.append("	 </a> ");
				
				}

			}
			
			/*
			 * se agrega el contenido a cada plantilla por medio de su constante
			 * correspondiente
			 */
			PANEL_MAGAZINE = TEMPLATE_PANEL_MAGAZINE.toString().replace(Constants.CONSTANT_CONTENT_PANEL_MAGAZINE,
					buffer_panel_magazine.toString());
			
			
			PANEL_NEWS = TEMPLATE_PANEL_NEWS.toString().replace(Constants.CONSTANT_CONTENT_PANEL_NEWS, buffer_panel_news.toString());

			/* si la carpeta se creo bien o ya esta creada */
			if (Utils.createFolders(parametros.getRutaArchivosHome())) {

				/* se crean los archivos HTML */
				success = Utils.writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelMagazine(),
						PANEL_MAGAZINE);
				logger.info("Genero HTML : " + parametros.getNombrePanelMagazine() + ": status :" + success);

				success = Utils.writeHTML(parametros.getRutaArchivosHome() + "/" + parametros.getNombrePanelNews(),
						PANEL_NEWS);
				logger.info("Genero HTML : " + parametros.getNombrePanelNews() + ": status :" + success);

			}

		} catch (Exception e) {
			logger.error(" ¡ Error en creaPanelMagazine ! [ GenerarComponentesBO ] : ", e);

		}
	}


}
