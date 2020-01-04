package net.ankij.client;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONObjectException;
import com.fasterxml.jackson.jr.private_.JsonParser;
import com.fasterxml.jackson.jr.private_.JsonToken;
import com.fasterxml.jackson.jr.stree.JrsArray;
import com.fasterxml.jackson.jr.stree.JrsValue;

import net.ankij.type.CardModel;
import net.ankij.type.CardModel.Builder;
import net.ankij.type.CardModels;
import net.ankij.type.Deck;
import net.ankij.type.Decks;

final class EditPageParser {

	private final JSON json;

	EditPageParser(JSON json) {
		this.json = requireNonNull(json, "json");
	}

	CardModels parseCardModels(String jsonModel) throws JSONObjectException, IOException {
		JrsArray root = (JrsArray) json.treeFrom(jsonModel);
		List<CardModel> cards = new ArrayList<>();
		for (int i = 0; i < root.size(); i++) {
			JrsValue entry = root.get(i);
			cards.add(parse(entry));
		}
		return new CardModels(cards);
	}

	private CardModel parse(JrsValue value) {
		JrsValue type = value.get("name");
		Builder builder = CardModel.builder(type.asText());
		JrsArray fields = (JrsArray) value.get("flds");
		for (int i = 0; i < fields.size(); i++) {
			JrsValue field = fields.get(i);
			JrsValue name = field.get("name");
			builder.addField(name.asText());
		}
		return builder.build();
	}

	Decks parseDecks(String jsonModel) throws JSONObjectException, IOException {
		JsonParser parser = json.createParser(jsonModel);
		List<Deck> decks = new ArrayList<>();
		String name = null;
		String mid = null;
		boolean deck = false;

		for (JsonToken jsonToken = parser.nextToken(); !parser.isClosed()
				&& jsonToken != null; jsonToken = parser.nextToken()) {
			switch (jsonToken) {
			case START_OBJECT:
				deck = parser.getCurrentName() != null;
			case VALUE_STRING:
				if ("mid".equals(parser.getCurrentName())) {
					if (mid != null) {
						throw new IllegalStateException("mid has already been set");
					}
					mid = parser.getText();
				} else if ("name".equals(parser.getCurrentName())) {
					if (name != null) {
						throw new IllegalStateException("name has already been set");
					}
					name = parser.getText();
				}
				break;
			case END_OBJECT:
				// the mid may be null for some decks
				if (deck && mid != null) {
					decks.add(new Deck(name, mid));
				}
				name = null;
				mid = null;
				deck = false;
				break;
			default:
				break;
			}
		}
		return new Decks(decks);
	}

}
