package net.ankij.client;

import static java.nio.charset.StandardCharsets.UTF_8;
import static net.ankij.client.Util.escapeForAnki;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec;

import net.ankij.type.CardModel;

final class EditPage {

	private static final Logger log = Logger.getLogger(EditPage.class.getName());

	private static final String EDIT_PAGE_URL = "https://ankiuser.net/edit/";
	private static final Pattern CSRF_TOKEN = Pattern.compile("editor.csrf_token2 = '(.*?)'");

	// we could use also just one regex with an OR condition or an JSON parser..
	private static final Pattern EDIT_MID_PATTERN = Pattern
			.compile("\\{[^\\{]*?\"name\": \"English -> German\"[^\\}]+?\"mid\": \"(\\d*?)\"[^\\}]*?\\}");
	private static final Pattern EDIT_MID_PATTERN_END = Pattern
			.compile("\\{[^\\{]*?\"mid\": \"(\\d*?)\"[^\\}]+?\"name\": \"English -> German\"[^\\}]*?\\}");

	private static final Pattern CARD_MODELS = Pattern.compile("editor.models = (.*);");

	private final AnkiHttpClient httpClient;
	private final PageValue pageValue;

	EditPage(AnkiHttpClient httpClient, PageValue pageValue) {
		this.httpClient = httpClient;
		this.pageValue = pageValue;
	}

	void addEntry(String word, String meaning, String example, String pronunciation, String translation)
			throws IOException {
		String editPage = httpClient.get(EDIT_PAGE_URL).response;
		String csrfToken = pageValue.extractFrom(editPage, CSRF_TOKEN)
				.orElseThrow(() -> new IOException("No CSRF token found"));
		Optional<String> mid = pageValue.extractFrom(editPage, EDIT_MID_PATTERN);
		if (!mid.isPresent()) {
			mid = pageValue.extractFrom(editPage, EDIT_MID_PATTERN_END);
		}
		String midValue = mid.orElseThrow(() -> new IOException("Failed to find mid token on page:\n" + editPage));
		postEntry(word, meaning, example, pronunciation, translation, editPage, csrfToken, midValue);
	}

	void add(AddEntryRequest request) throws ClientProtocolException, IOException {
		String editPage = httpClient.get(EDIT_PAGE_URL).response;
		String csrfToken = pageValue.extractFrom(editPage, CSRF_TOKEN)
				.orElseThrow(() -> new IOException("No CSRF token found"));

		String cardModelsJson = pageValue.extractFrom(editPage, CARD_MODELS).orElseThrow(NoSuchElementException::new);
		EditPageParser parser = new EditPageParser(JSON.std.with(new JacksonJrsTreeCodec()));

		Collection<CardModel> cardModels = parser.parseCardModels(cardModelsJson);
	}

	private void postEntry(String word, String meaning, String example, String pronunciation, String translation,
			String editPage, String csrf, String mid) throws ClientProtocolException, IOException {
		String value = "[[\"" + format(word) + "\",\"" + format(meaning) + "\",\"" + format(pronunciation) + "\",\""
				+ format(example) + "\",\"" + format(translation) + "\"],\"\"]";
		log.fine("Sending following request\ncsrf_token: " + csrf + "\nmid: " + mid + "\ndata: " + value);

		List<NameValuePair> body = Form.form().add("csrf_token", csrf).add("mid", mid).add("deck", "German -> English")
				.add("data", value).build();

		HttpResponse response = httpClient.post(EDIT_PAGE_URL,
				Arrays.asList(
						new BasicHeader("Content-Type",
								ContentType.create("application/x-www-form-urlencoded", UTF_8).toString()),
						new BasicHeader("X-Requested-With", "XMLHttpRequest")),
				new UrlEncodedFormEntity(body));
		if (response.statusCode != SC_OK) {
			throw new IOException(
					"Failed to add " + word + "! Got status: " + response.statusCode + "\n\n Edit Page:\n" + editPage);
		}
	}

	private String format(String string) {
		if (string.isEmpty()) {
			return "";
		}
		String[] parts = string.split("\n");
		if (parts.length == 1) {
			return escapeForAnki(string);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append("<br>");
			}
			sb.append("<div>").append(escapeForAnki(parts[i])).append("</div>");
		}
		return sb.toString();
	}
}
