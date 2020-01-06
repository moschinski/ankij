package net.ankij.type;

public final class StringValue implements Value {

	public static final StringValue EMPTY = new StringValue("");

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
			return string;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append("<br>");
			}
			sb.append("<div>").append(parts[i]).append("</div>");
		}
		return sb.toString();
	}
}
