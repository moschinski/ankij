package net.ankij.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;

public interface AnkiHttpClient {

	HttpResponse get(String url) throws ClientProtocolException, IOException;

	HttpResponse post(String url, List<Header> headers, HttpEntity entity) throws ClientProtocolException, IOException;

}
