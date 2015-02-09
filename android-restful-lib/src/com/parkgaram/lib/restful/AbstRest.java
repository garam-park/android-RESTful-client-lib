package com.parkgaram.lib.restful;

import java.util.Map;

/***
 * RESTful Api 에서 전달되는 정보의 추상 클래스
 * <p>
 * 해당 클래스는 Body가 없다. 상속 하는 클래스는 Body를 구현해 주어야 한다.
 * </p>
 * @author garam
 *
 */
public abstract class AbstRest {

	/***
	 * RESTful api 리소스
	 */
	private String uri;
	/**
	 * Restful 해더
	 */
	private Map<String, String> header;
	
	/***
	 * Restful 메소드
	 * {@link Method}
	 */
	private Method method;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}

	abstract public Object getBody();
	abstract public void setBody(Object body);
	
}
