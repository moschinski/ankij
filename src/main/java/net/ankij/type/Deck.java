package net.ankij.type;

import static java.util.Objects.requireNonNull;

public final class Deck {

	private final String name;
	private final String mid;

	public Deck(String name, String mid) {
		this.name = requireNonNull(name, "name");
		this.mid = requireNonNull(mid, "mid");
	}

	public String getName() {
		return name;
	}

	public String getMid() {
		return mid;
	}

}
