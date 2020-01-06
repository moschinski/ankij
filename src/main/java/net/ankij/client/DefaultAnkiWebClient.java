package net.ankij.client;

import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;

import net.ankij.type.Card;

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
	public void addCard(Card card) throws IOException {
		new EditPage(httpClient, pageValue).add(card);
	}

}