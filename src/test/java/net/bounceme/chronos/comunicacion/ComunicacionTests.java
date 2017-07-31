package net.bounceme.chronos.comunicacion;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Tests de la aplicación. Los tests se ejecutan en una
 * secuencia ordenada por el nombre
 * 
 * @author frederik
 *
 * Para hacer los tests: http://spring.io/guides/tutorials/bookmarks/
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComunicacionTests {
    
    private static final Logger log = Logger.getLogger(ComunicacionTests.class);

    /**
     * URLs para las pruebas
     * 
     * @author frederik
     *
     */
    private enum verbs {
        CLIENTES_NEW("/clientes/new"),
        CLIENTES_GET("/clientes/1"),
        CLIENTES_UPDATE("/clientes/1/update"),
        MEDIOS_COMUNICACION_NEW("/mediosComunicacionCliente/new"),
        MEDIOS_COMUNICACION_UPDATE("/mediosComunicacionCliente/update"),
        AVISOS_NEW("/avisos/new"),
        AVISOS_GET("/avisos/1"),
        NOTIFICACIONES_NEW("/notificaciones/new"),
        NOTIFICACIONES_SEND("/notificaciones/send");
    
        private String value;
        
        private verbs(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * Prueba de creación de un cliente
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AA_createCliente() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("nombre", "Federico");
		parameters.put("apellidos", "Martín Lara");
		parameters.put("dni", "30943266-D");

		String cliente = json(parameters);

		log.info("Method: post\nURL: " + verbs.CLIENTES_NEW.getValue() + "\n " + cliente);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.CLIENTES_NEW.getValue())
				.contentType(contentType)
				.content(cliente))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	/**
	 * Prueba de obtención de un cliente
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AB_readCliente() throws Exception {
	    log.info("Method: get\nURL: " + verbs.CLIENTES_GET.getValue());
		mockMvc.perform(MockMvcRequestBuilders.get(verbs.CLIENTES_GET.getValue()))
		        .andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Federico")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.apellidos", Matchers.is("Martín Lara")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dni", Matchers.is("30943266-D")));
	}

	/**
	 * Prueba de actualización de un cliente
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AC_updateCliente() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dni", "30943266D");

		String datosModificados = json(parameters);

		log.info("Method: put\nURL: " + verbs.CLIENTES_UPDATE.getValue() + "\n " + datosModificados);
		mockMvc.perform(MockMvcRequestBuilders.put(verbs.CLIENTES_UPDATE.getValue())
				.contentType(contentType)
				.content(datosModificados))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("Method: get\nURL: " + verbs.CLIENTES_GET.getValue());
		mockMvc.perform(MockMvcRequestBuilders.get(verbs.CLIENTES_GET.getValue()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dni", Matchers.is("30943266D")));
	}
	
	/**
	 * Prueba de creación de un medio de fax para un cliente 
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AD_nuevoSMSCliente() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idCliente", "1");
		parameters.put("idTipo", "1");
		parameters.put("valor", "600000000");

		String params = json(parameters);

		log.info("Method: post\nURL: " + verbs.MEDIOS_COMUNICACION_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.MEDIOS_COMUNICACION_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	/**
	 * Prueba de creación de un medio de fax para un cliente
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AE_nuevoFAXCliente() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idCliente", "1");
		parameters.put("idTipo", "2");
		parameters.put("valor", "900000000");

		String params = json(parameters);

		log.info("Method: post\nURL: " + verbs.MEDIOS_COMUNICACION_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.MEDIOS_COMUNICACION_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	/**
	 * Prueba de creación de un aviso de un cliente
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AF_nuevoAvisoCliente() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idCliente", "1");
		parameters.put("fechaInicioObra", "2017-06-20");
		parameters.put("mensaje", "EN LA FECHA INDICADA COMENZAREMOS LA OBRA");

		String params = json(parameters);

		log.info("Method: post\nURL: " + verbs.AVISOS_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.AVISOS_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	/**
	 * Prueba de creación de un aviso con error.
	 * El parámetro del mensaje debe estar informado
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AG_nuevoAvisoClienteNotValid() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idCliente", "1");
		parameters.put("fechaInicioObra", "2017-06-20");

		String params = json(parameters);

		log.info("Method: post\nURL: " + verbs.AVISOS_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.AVISOS_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError());
	}
	
	/**
	 * Prueba de creación de una notificación de un aviso
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AH_nuevaNotificacion_a_SMS() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idAviso", "1");
		parameters.put("idTipoMedio", "1");

		String params = json(parameters);

		log.info("Method: post\nURL: " + verbs.NOTIFICACIONES_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.NOTIFICACIONES_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	/**
	 * Prueba de creación de una notificación de un aviso
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AI_nuevaNotificacion_a_FAX() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idAviso", "1");
		parameters.put("idTipoMedio", "2");

		String params = json(parameters);

		log.info("Method: post\nURL: " + verbs.NOTIFICACIONES_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.NOTIFICACIONES_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	/**
	 * Prueba de envío de una notificación a SMS
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AJ_enviarNotificacion_SMS_Fallido() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idNotificacion", "1");

		String notificacion = json(parameters);

		log.info("Method: put\nURL: " + verbs.NOTIFICACIONES_SEND.getValue() + "\n " + notificacion);
		mockMvc.perform(MockMvcRequestBuilders.put(verbs.NOTIFICACIONES_SEND.getValue())
				.contentType(contentType)
				.content(notificacion))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		// Causa una espera de 2 segundos para que al receptor de la cola le de tiempo a procesar la petición
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		
		log.info("Method: get\nURL: " + verbs.AVISOS_GET.getValue());
		mockMvc.perform(MockMvcRequestBuilders.get(verbs.AVISOS_GET.getValue()))
		        .andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType))
				.andExpect(MockMvcResultMatchers.jsonPath("$.estaNotificado", Matchers.is(false)));
	}
	
	/**
	 * Prueba de envío de una notificación a FAX de un número que
	 * no empieza por 91
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_AK_enviarNotificacion_FAX_Fallido() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idNotificacion", "2");

		String notificacion = json(parameters);

		log.info("Method: put\nURL: " + verbs.NOTIFICACIONES_SEND.getValue() + "\n " + notificacion);
		mockMvc.perform(MockMvcRequestBuilders.put(verbs.NOTIFICACIONES_SEND.getValue())
				.contentType(contentType)
				.content(notificacion))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		// Causa una espera de 2 segundos para que al receptor de la cola le de tiempo a procesar la petición
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		
		log.info("Method: get\nURL: " + verbs.AVISOS_GET.getValue());
		mockMvc.perform(MockMvcRequestBuilders.get(verbs.AVISOS_GET.getValue()))
		        .andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType))
				.andExpect(MockMvcResultMatchers.jsonPath("$.estaNotificado", Matchers.is(false)));
	}
	
	/**
     * Prueba de envío de una notificación a FAX de un número que
     * no empieza por 91
     * 
     * @throws Exception
     */
    @Test
    public void test_AL_enviarNotificacion_FAX_Correcto() throws Exception {
        // Cambiar numero de fax
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("idCliente", "1");
        parameters.put("idTipo", "2");
        parameters.put("valor", "910000000");

        String params = json(parameters);

        log.info("Method: put\nURL: " + verbs.MEDIOS_COMUNICACION_UPDATE.getValue() + "\n " + params);
        mockMvc.perform(MockMvcRequestBuilders.put(verbs.MEDIOS_COMUNICACION_UPDATE.getValue())
                .contentType(contentType)
                .content(params))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        parameters.clear();
        
        // Crea una nueva notificación
        parameters.put("idAviso", "1");
		parameters.put("idTipoMedio", "2");

		params = json(parameters);

		log.info("Method: post\nURL: " + verbs.NOTIFICACIONES_NEW.getValue() + "\n " + params);
		mockMvc.perform(MockMvcRequestBuilders.post(verbs.NOTIFICACIONES_NEW.getValue())
				.contentType(contentType)
				.content(params))
				.andExpect(MockMvcResultMatchers.status().isCreated());
        
		parameters.clear();
		
		// Envía la nueva notificación
		parameters.put("idNotificacion", "3");

		String notificacion = json(parameters);

		log.info("Method: put\nURL: " + verbs.NOTIFICACIONES_SEND.getValue() + "\n " + notificacion);
		mockMvc.perform(MockMvcRequestBuilders.put(verbs.NOTIFICACIONES_SEND.getValue())
				.contentType(contentType)
				.content(notificacion))
				.andExpect(MockMvcResultMatchers.status().isOk());
        
        // Causa una espera de 2 segundos para que al receptor de la cola le de tiempo a procesar la petición
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        
        log.info("Method: get\nURL: " + verbs.AVISOS_GET.getValue());
		mockMvc.perform(MockMvcRequestBuilders.get(verbs.AVISOS_GET.getValue()))
		        .andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType))
				.andExpect(MockMvcResultMatchers.jsonPath("$.estaNotificado", Matchers.is(true)));
    }

	/**
	 * Método de conveniencia para convertir un objeto en una cadena json
	 * 
	 * @param o el objeto
	 * @return la cadena json del objeto
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
