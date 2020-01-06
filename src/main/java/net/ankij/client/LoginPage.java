package net.ankij.client;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.apache.http.HttpStatus.SC_MOVED_TEMPORARILY;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicHeader;

@ParametersAreNonnullByDefault
final class LoginPage {

	private static final String LOGIN_PAGE_URL = "https://ankiweb.net/account/login";
	private static final Pattern CSRF_TOKEN = Pattern.compile("name=\"csrf_token\" value=\"(.*?)\"");

	private final AnkiHttpClient httpClient;
	private final PageValue pageValue;

	LoginPage(AnkiHttpClient httpClient, PageValue pageValue) {
		this.httpClient = requireNonNull(httpClient, "httpClient");
		this.pageValue = requireNonNull(pageValue, "pageValue");
	}

	void login(String username, String password) throws ClientProtocolException, IOException {
		Optional<String> csrfToken = pageValue.extractFrom(httpClient.get(LOGIN_PAGE_URL).response, CSRF_TOKEN);
		String token = csrfToken.orElseThrow(() -> new IOException("No CSRF token found"));
		doLogin(username, password, token);
	}

	private void doLogin(String username, String password, String csrfToken)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		// @formatter:off
		List<NameValuePair> body = Form.form()
				.add("csrf_token", csrfToken)
				.add("submitted", "1")
				.add("username", username)
				.add("password", password).build();
		// @formatter:on
		HttpResponse response = httpClient.post(LOGIN_PAGE_URL,
				asList(new BasicHeader("Content-Type", "application/x-www-form-urlencoded")),
				new UrlEncodedFormEntity(body, StandardCharsets.UTF_8));
		if (response.statusCode != SC_MOVED_TEMPORARILY) {
			throw new IOException("Failed to login; got following status code: " + response.statusCode);
		}
	}
}
