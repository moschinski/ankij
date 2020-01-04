package net.ankij.client;

import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;

@ParametersAreNonnullByDefault
final class DefaultAnkiWebClient implements AnkiWebClient {

	private final HttpClient httpClient;
	private final PageValue pageValue;

	DefaultAnkiWebClient(CloseableHttpClient httpClient) {
		this.httpClient = new HttpClient(httpClient);
		this.pageValue = new PageValue();
	}

	void login(String username, String password) throws ClientProtocolException, IOException {
		new LoginPage(httpClient, pageValue).login(username, password);
	}

	@Override
	public void add(AddEntryRequest request) throws IOException {
		new EditPage(httpClient, pageValue).add(request);
	}

}
