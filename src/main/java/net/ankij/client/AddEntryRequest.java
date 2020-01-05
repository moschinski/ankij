package net.ankij.client;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import net.ankij.type.StringValue;
import net.ankij.type.Value;

public final class AddEntryRequest {

	private final String deck;
	private final String type;
	private final Map<String, Value> fieldMap;

	public static Builder builder() {
		return new Builder();
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

	@Override
	public String toString() {
		return "AddEntryRequest [deck=" + deck + ", type=" + type + ", fieldMap=" + fieldMap + "]";
	}

	@ParametersAreNonnullByDefault
	public static final class Builder {

		private final Map<String, Value> fieldMap;
		private String deck;
		private String type;

		public Builder() {
			this.fieldMap = new HashMap<>(8);
		}

		public Builder deck(String deck) {
			this.deck = deck;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder addField(String key, String value) {
			fieldMap.put(key, new StringValue(value));
			return this;
		}

		public Builder addField(String key, Number value) {
			fieldMap.put(key, new StringValue(value));
			return this;
		}

		public AddEntryRequest build() {
			requireNonNull(deck, "deck");
			requireNonNull(type, "type");
			return new AddEntryRequest(deck, type, fieldMap);
		}
	}
}
