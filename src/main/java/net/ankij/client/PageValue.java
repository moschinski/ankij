package net.ankij.client;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

final class PageValue {

	Optional<String> extractFrom(String content, Pattern pattern) throws ClientProtocolException, IOException {
		return extractFrom(content, pattern, matcher -> matcher.group(1));
	}

	Optional<String> extractFrom(String content, Pattern pattern, Function<Matcher, String> transform)
			throws IOException, ClientProtocolException {
		Matcher matcher = pattern.matcher(content);
		if (!matcher.find()) {
			return Optional.empty();
		}
		return Optional.of(transform.apply(matcher));
	}
}
