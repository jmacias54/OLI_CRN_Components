/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import mx.com.amx.unotv.oli.crn.components.model.NNota;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class Utils {

	private final static Logger logger = Logger.getLogger(Utils.class);
	
	
	public static String getMediaContent(NNota nota ,String typeTemplate) {
		String template = "";
		
		if( !nota.getFcIdYoutube().trim().equals("") || !nota.getFcIdContentOoyala().trim().equals("")) {
			
			template= getVideo(nota,typeTemplate);
		}else {
			template = getImagen(nota,typeTemplate);
		}
		
		return template;
	}
	
	

	
	
	public static boolean writeHTML(String rutaHMTL, String HTML) {
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

	public static boolean createFolders(String carpetaContenido) {
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
	public static String cambiaCaracteres(String texto) {
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
	

	private static String getVideo(NNota dto,String typeTemplate) {

		StringBuffer mediaContent = new StringBuffer();

		mediaContent.append("<div class=\"thumb\"> <i class=\"fas fa-play\"></i><img src=\"" + dto.getFcImagen() + "\"> </div> ");
		
		
		
		return mediaContent.toString();
	}
	
	/**
	 * Se lleva a cabo el reemplazo del Media Content de la nota, de tipo imagen, se
	 * valida si la nota es de tipo galerÃ­a, infografia o default
	 * 
	 * @param ContentDTO
	 *            Instancia con la informaciÃ³n necesaria para reemplazar
	 * @return String Se devuelve una cadena con el Media Content de tipo imagen
	 * @author jesus
	 */
	private static String getImagen(NNota dto,String typeTemplate) {
		
		StringBuffer mediaImage = new StringBuffer("");
		String imgPrincipal = dto.getFcImagen() == null ? "" : dto.getFcImagen();

		String galeria = dto.getClGaleria() == null ? "" : dto.getClGaleria();

		if (!galeria.trim().equals("")) {
			mediaImage.append("<img src='" + cambiaCaracteres(galeria) + "'>");
		} else {

			mediaImage.append("<img src='" + cambiaCaracteres(imgPrincipal) + "'>");
		}
	
		
		return mediaImage.toString();
	}
}
