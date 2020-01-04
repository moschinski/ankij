package net.ankij.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

final class Form {

	private final List<NameValuePair> params;

	static Form form() {
		return new Form();
	}

	private Form() {
		this.params = new ArrayList<NameValuePair>();
	}

	Form add(final String name, final String value) {
		this.params.add(new BasicNameValuePair(name, value));
		return this;
	}

	List<NameValuePair> build() {
		return new ArrayList<NameValuePair>(this.params);
	}

}