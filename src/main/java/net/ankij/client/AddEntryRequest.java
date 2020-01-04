package net.ankij.client;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import net.ankij.type.StringValue;
import net.ankij.type.Value;

public final class AddEntryRequest {

	private final String deck;
	private final String type;
	private final Map<String, Value> fieldMap;

	public static Builder builder(String deck, String type) {
		return new Builder(deck, type);
	}

	private AddEntryRequest(String deck, String type, Map<String, Value> map) {
		this.deck = deck;
		this.type = type;
		this.fieldMap = unmodifiableMap(new HashMap<>(map));
	}

	public String getDeck() {
		return deck;
	}

	public String getType() {
		return type;
	}

	public Map<String, Value> getFieldMap() {
		return fieldMap;
	}

	public static final class Builder {

		private final String deck;
		private final String type;
		private final Map<String, Value> map;

		public Builder(String deck, String type) {
			this.deck = requireNonNull(deck, "deck");
			this.type = requireNonNull(type, "type");
			this.map = new HashMap<>(8);
		}

		public Builder add(String key, String value) {
			map.put(key, new StringValue(value));
			return this;
		}

		public Builder add(String key, Number value) {
			map.put(key, new StringValue(value));
			return this;
		}

		public AddEntryRequest build() {
			return new AddEntryRequest(deck, type, map);
		}
	}
}
