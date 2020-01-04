package net.ankij.client;

public final class Util {

	public static String escapeForAnki(String s) {
		StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\r') {
				// ignore (may happen on windows)
			} else if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
				out.append("&#");
				out.append((int) c);
				out.append(';');
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	private Util() {
		// not for initialization
	}

}
