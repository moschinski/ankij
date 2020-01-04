package net.ankij.type;

import java.util.Collection;
import java.util.Optional;

public final class CardModels {

	private final Collection<CardModel> cardModels;

	public CardModels(Collection<CardModel> cardModels) {
		this.cardModels = cardModels;
	}

	public Optional<CardModel> get(String type) {
		for (CardModel cardModel : cardModels) {
			if (cardModel.getType().equals(type)) {
				return Optional.of(cardModel);
			}
		}
		return Optional.empty();
	}
}
