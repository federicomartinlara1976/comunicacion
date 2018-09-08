package net.bounceme.chronos.comunicacion.services.emisores.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.bounceme.chronos.comunicacion.services.emisores.Emisor;

/**
 * Clase base con funcionalidades comunes a todos los emisores
 * 
 * @author frederik
 *
 */
public abstract class EmisorBase implements Emisor {
	
	private static final Logger log = Logger.getLogger(EmisorBase.class);
	
	protected HttpClient httpClient;

	public EmisorBase() {
		httpClient = HttpClients.createDefault();
	}
	
	/**
	 * Efectúa una llamada get a una url pasándole unos parámetros
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	protected String get(String url, Map<String, String> parameters) throws Exception {
		try {
			HttpGet getRequest = new HttpGet(url);
			List<NameValuePair> nvPairList = parseParameters(parameters);
			URI uri = new URIBuilder(getRequest.getURI()).addParameters(nvPairList).build();
			getRequest.setURI(uri);
			
			log.info("URL:" + getRequest.toString());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					}
					else {
						log.error("Unexpected response status: " + status);
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};

			return httpClient.execute(getRequest, responseHandler);

		}
		catch (URISyntaxException | IOException e) {
			log.error(e);
			throw new Exception(e);
		}
	}

	/**
	 * Método de conversión de los parámetros
	 * 
	 * @return
	 */
	private List<NameValuePair> parseParameters(Map<String, String> parameters) {
		if (!parameters.isEmpty()) {
			List<NameValuePair> nvPairList = new ArrayList<NameValuePair>();
			for (String key : parameters.keySet()) {
				NameValuePair nv = new BasicNameValuePair(key, parameters.get(key));
				log.info("Parameter: " + nv.toString());
				nvPairList.add(nv);
			}
			return nvPairList;
		}
		else {
			return null;
		}
	}

	protected void finalize() {
		log.info("Finalize");
	}
}
