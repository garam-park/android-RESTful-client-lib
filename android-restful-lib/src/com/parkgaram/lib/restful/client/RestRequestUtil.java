package com.parkgaram.lib.restful.client;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import android.util.Log;

import com.parkgaram.lib.restful.MutipartBodyRest;
import com.parkgaram.lib.restful.StringBodyRest;

/***
 * Http 프로토콜 상에서 REST 요청을 도와주는 클래스
 * <p>RESTful Api 는 Http 위에서 동작 하기 때문에 다음과 같이 도와주는 함수가 필요</p>
 * @author garam
 *
 */
public class RestRequestUtil {

	/**
	 * 로그 출력을 위한 컨스트 변수
	 */
	private static final String TAG  = RestRequestUtil.class.getSimpleName();
	
	/**
	 * 
	 * @param rest 보내려는 REST 요청
	 * @return 요청 결과
	 * @throws Exception
	 */
	public static  HttpResponse request(StringBodyRest rest) throws Exception{
		
		//SSLHttpClient 호출
		HttpClient httpClient = SslHttpClientFactory.getSSLHttpClient();
		
		//REST to Http
		HttpUriRequest httpRequest = getHttpRequest(rest);
		
		int redirectedCount = 0;
		
		//리다이렉트 일경우 3번 까지 시도
		while( redirectedCount++ <= 3 ){
			
			//리다이렉트 횟수
			Log.i(TAG, "redirectedCount : "+redirectedCount);
			
			//요청
			HttpResponse response = httpClient.execute(httpRequest);
			
			//결과
			StatusLine statusLine = response.getStatusLine();
			
			int statusCode = statusLine.getStatusCode();
			
			Log.i(TAG, "Return Status Code : "+redirectedCount);
			//리다이렉트 확인	
			if( statusCode == HttpsURLConnection.HTTP_MOVED_TEMP || statusCode == HttpsURLConnection.HTTP_MOVED_PERM ){
				
				// Redirected URL 받아오기
				rest.setUri(response.getHeaders("Location")[0].getValue());
				//새로운 uri 으로 세팅하기
				httpRequest = getHttpRequest(rest);
				//다시시도
				continue;
			}// if 리다이렉트 
			return response;
		}// end of while
		
		//3번이내 성공하지 못할 경우 '요청 실패'
		throw new Exception("Http Request Failed");
	}
	
	/**
	 * @param rest 보내려는 REST 요청
	 * @return 요청 결과
	 * @throws Exception
	 */
	public static HttpResponse request(MutipartBodyRest rest) throws Exception{
		
		HttpClient httpClient = SslHttpClientFactory.getSSLHttpClient();
		HttpUriRequest httpRequest = getHttpRequest(rest);
		
		int redirectedCount = 0;
		
		//리다이렉트 일경우 3번 까지 시도
		while( redirectedCount++ <= 3 ){
			
			//리다이렉트 횟수
			Log.i(TAG, "redirectedCount : "+redirectedCount);
			
			//요청
			HttpResponse response = httpClient.execute(httpRequest);
			
			//결과
			StatusLine statusLine = response.getStatusLine();
			
			int statusCode = statusLine.getStatusCode();
			
			Log.i(TAG, "Return Status Code : "+redirectedCount);
			//리다이렉트 확인	
			if( statusCode == HttpsURLConnection.HTTP_MOVED_TEMP || statusCode == HttpsURLConnection.HTTP_MOVED_PERM ){
				
				// Redirected URL 받아오기
				rest.setUri(response.getHeaders("Location")[0].getValue());
				//새로운 uri 으로 세팅하기
				httpRequest = getHttpRequest(rest);
				//다시시도
				continue;
			}// if 리다이렉트 
			return response;
		}// end of while
		
		//3번이내 성공하지 못할 경우 '요청 실패'
		throw new Exception("Http Request Failed");
	}
	
	/**
	 * REST 정보를 Http 형태로 변경해줌 
	 * @param rest 요청 하려는 REST
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	static private HttpUriRequest getHttpRequest(StringBodyRest rest) throws UnsupportedEncodingException {
		
		HttpUriRequest httpRequest;
		
		switch (rest.getMethod()) {
		
		case POST:
			httpRequest = new HttpPost(rest.getUri());
			try {
				((HttpPost)httpRequest).setEntity(new StringEntity(rest.getBody(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {throw e;}
			break;
		case GET:
			httpRequest = new HttpGet(rest.getUri());
			break;
		case PUT:
			httpRequest = new HttpPut(rest.getUri());
			try {
				((HttpPut)httpRequest).setEntity(new StringEntity(rest.getBody(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {throw e;}
			break;
		case DELETE:
			httpRequest = new HttpDelete(rest.getUri());
			break;
		case OPTIONS:
			httpRequest = new HttpOptions(rest.getUri());
			break;
		case HEAD:
			httpRequest = new HttpHead(rest.getUri());
			break;
		default:
			httpRequest = new HttpPost(rest.getUri());
			try {
				((HttpPost)httpRequest).setEntity(new StringEntity(rest.getBody(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {throw e;}
			break;
		}
		Map<String, String> header = rest.getHeader();
		if (header!=null) {
			if(header.size()>0){
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpRequest.setHeader(entry.getKey(),entry.getValue());	
				}
			}
		}
		return httpRequest;
	}
	
	/**
	 * REST 정보를 Http 형태로 변경해줌 
	 * @param rest 요청 하려는 REST
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	static private HttpUriRequest getHttpRequest(MutipartBodyRest rest) throws UnsupportedEncodingException {
		HttpUriRequest httpRequest;
		switch (rest.getMethod()) {
		case POST:
			httpRequest = new HttpPost(rest.getUri());
			((HttpPost)httpRequest).setEntity(rest.getBody());
			break;
		case GET:
			httpRequest = new HttpGet(rest.getUri());
			break;
		case PUT:
			httpRequest = new HttpPut(rest.getUri());
			((HttpPut)httpRequest).setEntity(rest.getBody());
			break;
		case DELETE:
			httpRequest = new HttpDelete(rest.getUri());
			break;
		case OPTIONS:
			httpRequest = new HttpOptions(rest.getUri());
			break;
		case HEAD:
			httpRequest = new HttpHead(rest.getUri());
			break;
		default:
			httpRequest = new HttpPost(rest.getUri());
			((HttpPost)httpRequest).setEntity(rest.getBody());
			break;
		}
		Map<String, String> header = rest.getHeader();
		
		if (header!=null) {
			httpRequest.setHeader("Content-Type", "multipart/form-data");
			if(header.size()>0){
				Map<String, String> map = rest.getHeader();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					httpRequest.setHeader(entry.getKey(),entry.getValue());	
				}
			}
		}
		
		return httpRequest;
	}
	
}
