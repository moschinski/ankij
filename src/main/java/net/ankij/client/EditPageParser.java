package net.ankij.client;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONObjectException;
import com.fasterxml.jackson.jr.stree.JrsArray;
import com.fasterxml.jackson.jr.stree.JrsValue;

import net.ankij.type.CardModel;
import net.ankij.type.CardModel.Builder;

final class EditPageParser {

	private final JSON json;

	EditPageParser(JSON json) {
		this.json = requireNonNull(json, "json");
	}

	Collection<CardModel> parseCardModels(String jsonModel) throws JSONObjectException, IOException {
		JrsArray root = (JrsArray) json.treeFrom(jsonModel);
		List<CardModel> cards = new ArrayList<>();
		for (int i = 0; i < root.size(); i++) {
			JrsValue entry = root.get(i);
			cards.add(parse(entry));
		}
		return cards;
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

}
