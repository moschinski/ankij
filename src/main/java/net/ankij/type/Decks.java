package net.ankij.type;

import java.util.Collection;
import java.util.Optional;

public final class Decks {

	private final Collection<Deck> decks;

	public Decks(Collection<Deck> decks) {
		this.decks = decks;
	}

	public Optional<Deck> get(String name) {
		for (Deck cardModel : decks) {
			if (cardModel.getName().equals(name)) {
				return Optional.of(cardModel);
			}
		}
		return Optional.empty();
	}
}
