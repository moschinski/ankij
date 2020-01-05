package net.ankij.type;

import static java.util.Objects.requireNonNull;

public final class Deck {

	private final String name;
	private final String defaultMid;

	public Deck(String name, String defaultMid) {
		this.name = requireNonNull(name, "name");
		this.defaultMid = defaultMid;
	}

	public String getName() {
		return name;
	}

	public String getDefaultMid() {
		return defaultMid;
	}

}
