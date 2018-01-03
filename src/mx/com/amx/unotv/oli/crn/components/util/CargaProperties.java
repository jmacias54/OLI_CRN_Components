package  mx.com.amx.unotv.oli.crn.components.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import mx.com.amx.unotv.oli.crn.components.dto.ParametrosDTO;



public class CargaProperties {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public ParametrosDTO obtenerPropiedades(String properties) {
		ParametrosDTO parametrosDTO = new ParametrosDTO();		 
		try {	    		
			Properties propsTmp = new Properties();
			propsTmp.load(this.getClass().getResourceAsStream( "/general.properties" ));
			String ambiente = propsTmp.getProperty("ambiente");
			String rutaProperties = propsTmp.getProperty(properties.replace("ambiente", ambiente));
			Properties props = new Properties();
			
			props.load(new FileInputStream(new File(rutaProperties)));				
			parametrosDTO.setAmbiente(ambiente);
			parametrosDTO.setTamanioArchivo(props.getProperty("tamanioArchivo"));
			parametrosDTO.setURL_WS_BASE(props.getProperty("URL_WS_BASE"));
			
			parametrosDTO.setNombrePanelHomeNoticias(props.getProperty("nombrePanelHomeNoticias"));
			parametrosDTO.setNombrePanelHomeVideos(props.getProperty("nombrePanelHomeVideos"));
			parametrosDTO.setNombrePanelMagazine(props.getProperty("nombrePanelMagazine"));
			parametrosDTO.setNombrePanelMoreNews(props.getProperty("nombrePanelMoreNews"));
			parametrosDTO.setNombrePanelNews(props.getProperty("nombrePanelNews"));
			parametrosDTO.setNombrePanelTopNews(props.getProperty("nombrePanelTopNews"));
			parametrosDTO.setRutaArchivos(props.getProperty("rutaArchivos"));
			parametrosDTO.setRutaArchivosHome(props.getProperty("rutaArchivosHome"));
			
		} catch (Exception ex) {
			parametrosDTO = new ParametrosDTO();
			logger.error("No se encontro el Archivo de propiedades: ", ex);			
		}
		return parametrosDTO;
    }
	
	
}
