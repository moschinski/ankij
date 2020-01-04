package net.ankij.type;

import static net.ankij.client.Util.escapeForAnki;

public final class StringValue implements Value {

	private final String value;

	public StringValue(String value) {
		this.value = value;
	}

	public StringValue(Number value) {
		this(value.toString());
	}

	@Override
	public String formatForWeb() {
		return format(value);
	}

	private String format(String string) {
		if (string.isEmpty()) {
			return "";
		}
		String[] parts = string.split("\n");
		if (parts.length == 1) {
			return escapeForAnki(string);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append("<br>");
			}
			sb.append("<div>").append(escapeForAnki(parts[i])).append("</div>");
		}
		return sb.toString();
	}
}
