package com.parkgaram.lib.restful;

import java.util.Map;

/***
 * RESTful api  String Content를 이용하는 REST
 * @author garam
 * @see
 *	{@link AbstRest } : 상속
 */
public class StringBodyRest extends AbstRest{

	/**HTTP 의 바디 부분에 해당*/
	protected String body;
	
	@Override
	public String getBody() {
		return body;
	}

	/**
	 * @param body String 값을 넣어야한다.
	 */
	@Override
	public void setBody(Object body) {
		this.body = (String) body;
	}

	/***
	 * StringBody를 사용하는 REST를 사용할 경우 팩토리를 사용하여 만든다.<br>
	 * 예시 )<br>
	 *  <b>StringBodyRest = Factory.create().setBody("{"name":"garam"}").setHeader(header).setMethod(Method.POST).setUri("https://api.test.com/books").build();<b>
	 * @author garam
	 *
	 */
	public static class Factory {
		
		private StringBodyRest bodyRest;
		
		private Factory() {
			bodyRest = new StringBodyRest();
		}
		
		public static  Factory create(){
			return new Factory();
		}
		
		public StringBodyRest done() {
			return bodyRest;
		}
		
		public Factory setBody(String body) {
			bodyRest.setBody(body);
			return this;
		}
		
		public Factory setHeader(Map<String, String> header){
			bodyRest.setHeader(header);
			return this;
		}
		public Factory setMethod(Method method){
			bodyRest.setMethod(method);
			return this;
		}
		public Factory setUri(String uri){
			bodyRest.setUri(uri);
			return this;
		}
		
	}
	
}
