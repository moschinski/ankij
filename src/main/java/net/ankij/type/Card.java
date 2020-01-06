package net.ankij.type;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Represents an Anki card
 */
public final class Card {

	private final String deck;
	private final String cardType;
	private final Map<String, Value> fieldMap;

	/**
	 * @return a {@link Builder} that is used to create an {@link Card}
	 */
	public static Builder builder() {
		return new Builder();
	}

	private Card(String deck, String cardType, Map<String, Value> map) {
		this.deck = deck;
		this.cardType = cardType;
		this.fieldMap = unmodifiableMap(new HashMap<>(map));
	}

	/**
	 * @return the name of the deck to which the card should be added
	 */
	public String getDeck() {
		return deck;
	}

	/**
	 * @return the type of the card which should be added (e.g., "Basic")
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @return the fields and their respective values
	 */
	public Map<String, Value> getFields() {
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
		public Builder addField(String key, Object value) {
			fieldMap.put(requireNonNull(key, "key"), new StringValue(value.toString()));
			return this;
		}

		/**
		 * Creates an {@link Card}
		 * 
		 * @return a new {@link Card}
		 */
		public Card build() {
			requireNonNull(deck, "deck");
			requireNonNull(cardType, "cardType");
			return new Card(deck, cardType, fieldMap);
		}
	}
}
