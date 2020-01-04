package net.ankij.client;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

@ParametersAreNonnullByDefault
final class HttpClient implements AnkiHttpClient {

	private final CloseableHttpClient httpClient;

	public HttpClient(CloseableHttpClient httpClient) {
		this.httpClient = requireNonNull(httpClient, "httpClient");
	}

	@Override
	public HttpResponse get(String url) throws ClientProtocolException, IOException {
		try (CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
			String responseBody = EntityUtils.toString(response.getEntity(), UTF_8);
			int statusCode = response.getStatusLine().getStatusCode();
			return new HttpResponse(statusCode, responseBody);
		}
	}

	@Override
	public HttpResponse post(String url, List<Header> headers, HttpEntity entity)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders(headers.toArray(new Header[0]));
		httpPost.setEntity(entity);
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			String responseBody = EntityUtils.toString(response.getEntity(), UTF_8);
			int statusCode = response.getStatusLine().getStatusCode();
			return new HttpResponse(statusCode, responseBody);
		}
	}
}
