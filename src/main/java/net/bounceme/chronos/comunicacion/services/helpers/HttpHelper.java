package net.bounceme.chronos.comunicacion.services.helpers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpHelper {
	
	private static final Logger log = LoggerFactory.getLogger(HttpHelper.class);
	
	private HttpClient httpClient;

	public HttpHelper() {
		httpClient = HttpClients.createDefault();
	}
	
	/**
	 * Efectúa una llamada get a una url pasándole unos parámetros
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> parameters) throws IOException {
		try {
			HttpGet getRequest = new HttpGet(url);
			List<NameValuePair> nvPairList = parseParameters(parameters);
			URI uri = new URIBuilder(getRequest.getURI()).addParameters(nvPairList).build();
			getRequest.setURI(uri);
			
			log.info("URL: {}", getRequest);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					}
					else {
						log.error("Unexpected response status: {}", status);
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};

			return httpClient.execute(getRequest, responseHandler);

		}
		catch (URISyntaxException | IOException e) {
			log.error("ERROR: ", e);
			throw new IOException(e);
		}
	}

	/**
	 * Método de conversión de los parámetros
	 * 
	 * @return
	 */
	private List<NameValuePair> parseParameters(Map<String, String> parameters) {
		if (!parameters.isEmpty()) {
			List<NameValuePair> nvPairList = new ArrayList<>();
			for (Entry<String, String> entry : parameters.entrySet()) {
				NameValuePair nv = new BasicNameValuePair(entry.getKey(), entry.getValue());
				log.debug("Parameter: {}", nv);
				nvPairList.add(nv);
			}
			return nvPairList;
		}
		else {
			return Collections.emptyList();
		}
	}
}
