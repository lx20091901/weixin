package com.bokesoft.thirdparty.weixin.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class SimpleHttpClient {
	
	public static InputStream invokeGet4Stream(String url,int timeout) throws IOException {
		return invokeGet4Stream(url,null,timeout);
	}
	
	public static InputStream invokeGet4Stream(String url, Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				get.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(get);
			return get.getResponseBodyAsStream();
		}finally {
			get.releaseConnection();
		}
	}

	public static String invokeGet4String(String url,int timeout) throws IOException {
		return invokeGet4String(url,null,timeout);
	}
	
	public static String getRedirectURL(String url,int timeout) throws IOException {
		return getRedirectURL(url, null,timeout);
	}
	
	public static String getRedirectURL(String url, Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		get.setFollowRedirects(false);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				get.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(get);
			Header[] resHeaders = get.getResponseHeaders();
			for (Header header :resHeaders) {
				if ("Location".equals(header.getName()) ){
					return header.getValue();
				}
			}
			return null;
		} finally {
			get.releaseConnection();
		}
	}
	
	public static String invokeGet4String(String url, Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				get.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(get);
			return new String(get.getResponseBody(),"UTF-8");
		} finally {
			get.releaseConnection();
		}
	}
	
	public static InputStream invokePost4Stream(String url, Map<String, String> params,int timeout) throws IOException {
		return invokePost4Stream(url, params, null,timeout);
	}
	
	public static InputStream invokePost4Stream(String url, Map<String, String> params,
			Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		if (params != null) {
			Set<Entry<String, String>> entrys = params.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addParameter(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(post);
			return post.getResponseBodyAsStream();
		} finally {
			post.releaseConnection();
		}
	}
	
	public static String invokePost4String(String url, Map<String, String> params,int timeout) throws IOException {
		return invokePost4String(url, params, null,timeout);
	}

	public static String invokePost4String(String url, Map<String, String> params,
			Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		if (params != null) {
			Set<Entry<String, String>> entrys = params.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addParameter(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(post);
			return new String(post.getResponseBody(),"UTF-8");
		} finally {
			post.releaseConnection();
		}
	}

	public static InputStream invokePostWithBody4Stream(String url, InputStream inputStream,int timeout) throws IOException {
		return invokePostWithBody4Stream(url, inputStream,null,timeout);
	}

	public static InputStream invokePostWithBody4Stream(String url, InputStream inputStream, Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream);
		post.setRequestEntity(requestEntity);
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(post);
			return post.getResponseBodyAsStream();
		} finally {
			post.releaseConnection();
		}
	}
	
	public static InputStream invokePostWithBody4Stream(String url, String body,int timeout) throws IOException {
		return invokePostWithBody4Stream(url, body, null,timeout);
	}
	
	public static InputStream invokePostWithBody4Stream(String url, String body,Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		RequestEntity requestEntity = new ByteArrayRequestEntity(body.getBytes("UTF-8"));
		post.setRequestEntity(requestEntity);
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(post);
			return post.getResponseBodyAsStream();
		} finally {
			post.releaseConnection();
		}
	}
	
	public static String invokePostWithBody4String(String url, InputStream inputStream,int timeout) throws IOException {
		return invokePostWithBody4String(url, inputStream, null,timeout);
	}
	
	public static String invokePostWithBody4String(String url, InputStream inputStream,Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream);
		post.setRequestEntity(requestEntity);
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(post);
			return new String(post.getResponseBody(),"UTF-8");
		} finally {
			post.releaseConnection();
		}
	}

	public static String invokePostWithBody4String(String url, String body,int timeout) throws IOException {
		return invokePostWithBody4String(url, body, null,timeout);
	}

	public static String invokePostWithBody4String(String url, String body, Map<String, String> headers,int timeout) throws IOException {
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		RequestEntity requestEntity = new ByteArrayRequestEntity(body.getBytes("UTF-8"));
		post.setRequestEntity(requestEntity);
		try {
			hc.getParams().setSoTimeout(timeout);
			hc.executeMethod(post);
			return new String(post.getResponseBody(),"UTF-8");
		} finally {
			post.releaseConnection();
		}
	}
}
