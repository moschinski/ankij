package net.ankij.client;

import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.http.impl.client.HttpClients;

@ParametersAreNonnullByDefault
public interface AnkiWebClient {

	public static AnkiWebClient login(String username, String password) throws IOException {
		DefaultAnkiWebClient client = new DefaultAnkiWebClient(HttpClients.createDefault());
		client.login(username, password);
		return client;
	}

	void add(AddEntryRequest request) throws IOException;

}
