package net.ankij.type;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

public final class CardModel {

	public String type;
	private final List<String> fields;

	public CardModel(String type, List<String> fields) {
		this.type = type;
		this.fields = unmodifiableList(new ArrayList<>(fields));
	}

	public String getType() {
		return type;
	}

	public List<String> getFields() {
		return fields;
	}

	public static Builder builder(String type) {
		return new Builder(type);
	}

	public static class Builder {
		public String type;
		public List<String> fields;

		public Builder(String type) {
			this.type = requireNonNull(type, "type");
			this.fields = new ArrayList<>();
		}

		public void addField(String field) {
			fields.add(field);
		}

		public CardModel build() {
			return new CardModel(type, fields);
		}
	}

}
