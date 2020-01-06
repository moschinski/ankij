package net.ankij.client;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.http.impl.client.HttpClients;

import net.ankij.type.Card;

public interface AnkiWebClient {

	/**
	 * Creates an {@link AnkiWebClient} that is logged in to Anki web
	 * 
	 * @param username
	 *            the username of the Anki account
	 * @param password
	 *            the password of the Anki account
	 * @return a new {@link AnkiWebClient}
	 * @throws IOException
	 *             if a login to Anki web is not possible an {@link IOException}
	 */
	public static AnkiWebClient login(String username, String password) throws IOException {
		DefaultAnkiWebClient client = new DefaultAnkiWebClient(HttpClients.createDefault());
		client.login(username, password);
		return client;
	}

	/**
	 * Adds a new card to the given deck.
	 * 
	 * @param request
	 *            the request describing the card the should be added
	 * @throws IOException
	 *             if the card cannot be added an {@link IOException} is thrown
	 * @throws NoSuchElementException
	 *             if the given card type or deck does not exist
	 */
	void addCard(Card request) throws IOException;

}
