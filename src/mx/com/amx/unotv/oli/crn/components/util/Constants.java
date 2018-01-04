/**
 * 
 */
package mx.com.amx.unotv.oli.crn.components.util;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public final class Constants {

	private Constants() {
		// restrict instantiation
	}

	public static final String URL_DEV = "http://dev-unotv.tmx-internacional.net/";
	public static final String URL_PROD = "https://www.unotv.com/";
	
	public static final String ENVIROMENT_DEV = "desarrollo";
	public static final String ENVIROMENT_PROD = "produccion";
	
	
	public static final String MAGAZINE_PANEL = "magazine-panel";
	public static final String MAGAZINE_HOME = "magazine-home";
	
	
	public static final String CATEGORIA_DEPORTES = "deportes";
	public static final String CATEGORIA_NOTICIAS = "noticias";
	public static final String CATEGORIA_ATLETAS = "atletas";
	public static final String CATEGORIA_VIDEOS = "videos";
	
	public static final int LIMIT_4 = 4;
	public static final int LIMIT_50 = 50;
	
	/* controllers para los call ws*/
	public static final String CONTROLLER_NNOTA ="nNota";
	public static final String CONTROLLER_INOTA_MAGAZINE ="iNotaMagazine";
	
	/* metodos */
	public static final String METHOD_LAST_NOTES_NOT_IN_INOTA_MAGAZINE ="lastNotesNotInINotaMagazine";
	public static final String METHOD_LAST_NOTES_FIND_BY_IDCATEGORIA_LIMIT ="lastNotesFindByIdCategoriaLimit";
	public static final String METHOD_FIND_BY_IDMAGAZINE ="findByIdMagazine";
	
	
	/* plantillas y constantes de contenido de cada plantilla */
	
	public static final String TEMPLATE_PANEL_MAGAZINE = " <section class='panel-magazine'> $CONTENT_PANEL_MAGAZINE$  </section>  ";
	public static final String TEMPLATE_PANEL_NEWS = " <div class=\\\"panel-news\\\"> $CONTENT_PANEL_NEWS$      </div>  ";
	
	public static final String  CONSTANT_CONTENT_PANEL_MAGAZINE = "$CONTENT_PANEL_MAGAZINE$";
	public static final String  CONSTANT_CONTENT_PANEL_NEWS = "$CONTENT_PANEL_NEWS$";
	
	
	public static final String TEMPLATE_TOP_NEWS = " <div class=\\\"panel-top-news\\\"> $CONTENT_PANEL_TOP_NEWS$  </div>  ";
	public static final String  CONSTANT_CONTENT_PANEL_TOP_NEWS = "$CONTENT_PANEL_TOP_NEWS$";
	
	
	public static final String TEMPLATE_MORE_NEWS = "  <section class=\\\"panel-more-news\\\"> <div> <div class=\\\"featured\\\"> $CONTENT_PANEL_MORE_NEWS_FETURED$ </div>  <div class=\\\"more\\\">  $CONTENT_PANEL_MORE_NEWS$ </div> </div>  <aside class=\\\"sidebar\\\" role=\\\"complementary\\\"></aside>   </section> " ;
	public static final String CONSTANT_CONTENT_PANEL_MORE_NEWS_FETURED = "$CONTENT_PANEL_MORE_NEWS_FETURED$";
	public static final String CONSTANT_CONTENT_PANEL_MORE_NEWS = "$CONTENT_PANEL_MORE_NEWS$";
	
	public static final String  TEMPLATE_HOME_VIDEOS = " <main class=\\\"main\\\" role=\\\"main\\\"> <section class=\\\"section-news\\\"> <h1>Videos</h1>  <div class=\\\"panel-news\\\"> $CONTENT_HOME_VIDEOS$  </div> </section> <aside class=\\\"sidebar\\\" role=\\\"complementary\\\"></aside> </main> ";
	public static final String CONSTANT_CONTENT_HOME_VIDEOS = "$CONTENT_HOME_VIDEOS$";
	
	
	
	public static final String  TEMPLATE_HOME_NOTICIAS = " <main class=\\\"main\\\" role=\\\"main\\\"> <section class=\\\"section-news\\\"> <h1>Noticias</h1> <div class=\\\"panel-news\\\">     $CONTENT_HOME_NOTICIAS$   </div><a class=\\\"btn-more\\\" href=\\\"#\\\" id=\\\"btn-more\\\">Cargar más</a>        </section> <aside class=\\\"sidebar\\\" role=\\\"complementary\\\"></aside> </main> ";
	public static final String CONSTANT_CONTENT_HOME_NOTICIAS ="$CONTENT_HOME_NOTICIAS$";
}
