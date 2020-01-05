package net.ankij.client;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import net.ankij.type.StringValue;
import net.ankij.type.Value;

/**
 * Represents a request to add a card to an Anki deck
 */
public final class AddCardRequest {

	private final String deck;
	private final String cardType;
	private final Map<String, Value> fieldMap;

	/**
	 * @return a {@link Builder} that is used to create an {@link AddCardRequest}
	 */
	public static Builder builder() {
		return new Builder();
	}

	private AddCardRequest(String deck, String cardType, Map<String, Value> map) {
		this.deck = deck;
		this.cardType = cardType;
		this.fieldMap = unmodifiableMap(new HashMap<>(map));
	}

	public String getDeck() {
		return deck;
	}

	public String getCardType() {
		return cardType;
	}

	public Map<String, Value> getFieldMap() {
		return fieldMap;
	}

	@Override
	public String toString() {
		return "AddCardRequest [deck=" + deck + ", cardType=" + cardType + ", fieldMap=" + fieldMap + "]";
	}

	@ParametersAreNonnullByDefault
	public static final class Builder {

		private final Map<String, Value> fieldMap;
		private String deck;
		private String cardType;

		public Builder() {
			this.fieldMap = new HashMap<>(8);
		}

		/**
		 * @param deck
		 *            name of the deck to which the card should be added
		 * @return {@code this} instance
		 */
		public Builder deck(String deck) {
			this.deck = deck;
			return this;
		}

		/**
		 * @param cardType
		 *            type of the card that should be added
		 * @return {@code this} instance
		 */
		public Builder cardType(String cardType) {
			this.cardType = cardType;
			return this;
		}

		/**
		 * Adds a field to the card
		 * 
		 * @param key
		 *            the name of the field
		 * @param value
		 *            the value of the field
		 * @return {@code this} instance
		 */
		public Builder addField(String key, String value) {
			fieldMap.put(key, new StringValue(value));
			return this;
		}

		/**
		 * Adds a field to the card
		 * 
		 * @param key
		 *            the name of the field
		 * @param value
		 *            the value of the field
		 * @return {@code this} instance
		 */
		public Builder addField(String key, Number value) {
			fieldMap.put(key, new StringValue(value));
			return this;
		}

		/**
		 * Creates an {@link AddCardRequest}
		 * 
		 * @return a new {@link AddCardRequest}
		 */
		public AddCardRequest build() {
			requireNonNull(deck, "deck");
			requireNonNull(cardType, "cardType");
			return new AddCardRequest(deck, cardType, fieldMap);
		}
	}
}
