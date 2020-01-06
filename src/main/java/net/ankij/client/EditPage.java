package net.ankij.client;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec;

import net.ankij.type.Card;
import net.ankij.type.CardModel;
import net.ankij.type.CardModels;
import net.ankij.type.Deck;
import net.ankij.type.Decks;
import net.ankij.type.StringValue;
import net.ankij.type.Value;

final class EditPage {

	private static final String EDIT_PAGE_URL = "https://ankiuser.net/edit/";
	private static final String SAVE_URL = "https://ankiuser.net/edit/save";
	private static final Pattern CSRF_TOKEN = Pattern.compile("editor.csrf_token2 = '(.*?)'");
	private static final Pattern CARD_MODELS = Pattern.compile("editor.models = (.*);");
	private static final Pattern DECKS = Pattern.compile("editor.decks = (.*);");

	private final AnkiHttpClient httpClient;
	private final PageValue pageValue;
	private final EditPageParser parser;

	EditPage(AnkiHttpClient httpClient, PageValue pageValue) {
		this.httpClient = requireNonNull(httpClient, "httpClient");
		this.pageValue = requireNonNull(pageValue, "pageValue");
		this.parser = new EditPageParser(JSON.std.with(new JacksonJrsTreeCodec()));
	}

	void add(Card request) throws ClientProtocolException, IOException {
		String editPage = httpClient.get(EDIT_PAGE_URL).response;
		String csrfToken = pageValue.extractFrom(editPage, CSRF_TOKEN)
				.orElseThrow(() -> new IOException("No CSRF token found"));

		String cardModelsJson = pageValue.extractFrom(editPage, CARD_MODELS).get();
		CardModels cardModels = parser.parseCardModels(cardModelsJson);
		CardModel cardModel = cardModels.get(request.getCardType())
				.orElseThrow(() -> new NoSuchElementException("No such card type: " + request.getCardType()));
		String payload = createPayload(request, cardModel);

		String decksJson = pageValue.extractFrom(editPage, DECKS).get();
		Decks decks = parser.parseDecks(decksJson);
		Deck deck = decks.get(request.getDeck())
				.orElseThrow(() -> new NoSuchElementException("No such deck: " + request.getDeck()));

		List<NameValuePair> body = Form.form().add("csrf_token", csrfToken).add("mid", cardModel.getId())
				.add("deck", deck.getName()).add("data", payload).build();
		HttpResponse response = httpClient.post(SAVE_URL,
				asList(new BasicHeader("Content-Type",
						ContentType.create("application/x-www-form-urlencoded", UTF_8).toString()),
						new BasicHeader("X-Requested-With", "XMLHttpRequest")),
				new UrlEncodedFormEntity(body, UTF_8));
		if (response.statusCode != SC_OK) {
			throw new IOException("Failed to add " + request + "! Got status: " + response.statusCode
					+ "\n\n Edit Page:\n" + editPage);
		}
	}

	String createPayload(Card request, CardModel cardModel) {
		List<String> fields = cardModel.getFields();
		HashMap<String, Value> fieldMap = new HashMap<>(request.getFields());
		String fieldPayload = fields.stream().map(fieldMap::remove).map(val -> val == null ? StringValue.EMPTY : val)
				.map(Value::formatForWeb).collect(joining("\",\""));
		if (!fieldMap.isEmpty()) {
			throw new IllegalArgumentException("The field(s) " + fieldMap.keySet() + " cannot be mapped for card type "
					+ request.getCardType() + ". The available fields are " + cardModel.getFields());
		}
		return "[[\"" + fieldPayload + "\"],\"\"]";
	}
}
