package net.ankij.type;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

public final class CardModel {

	private final String name;
	private final String id;
	private final List<String> fields;

	private CardModel(String name, String id, List<String> fields) {
		this.name = name;
		this.id = id;
		this.fields = unmodifiableList(new ArrayList<>(fields));
	}

	public String getName() {
		return name;
	}

	public List<String> getFields() {
		return fields;
	}

	public String getId() {
		return id;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		public String type;
		public String id;
		public List<String> fields;

		public Builder() {
			this.fields = new ArrayList<>();
		}

		public Builder name(String type) {
			this.type = type;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder addField(String field) {
			fields.add(field);
			return this;
		}

		public CardModel build() {
			requireNonNull(type, "type");
			requireNonNull(id, "id");
			return new CardModel(type, id, fields);
		}
	}

}
