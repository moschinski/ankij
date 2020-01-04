package net.ankij.client;

final class HttpResponse {

	final int statusCode;
	final String response;

	HttpResponse(int statusCode, String response) {
		this.statusCode = statusCode;
		this.response = response;
	}

}
