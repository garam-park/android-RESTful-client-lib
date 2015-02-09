package com.parkgaram.lib.restful;

import java.util.Map;

import org.apache.http.HttpEntity;
/***
 * RESTful에서 Multipart Body를 사용하는 REST
 * @author garam
 *
 */
public class MutipartBodyRest extends AbstRest{

	/***
	 * HttpEntity를 이용하여 Multipart를 사용
	 */
	private HttpEntity body;
	
	@Override
	public HttpEntity getBody() {
		return body;
	}

	/***
	 * @param body HttpEntity
	 * @see {@link HttpEntity}
	 */
	@Override
	public void setBody(Object body) {
		this.body = (HttpEntity) body;
	}

	public static class Factory {
		
		private MutipartBodyRest bodyRest;
		
		private Factory() {
			bodyRest = new MutipartBodyRest();
		}
		
		public static  Factory create(){
			return new Factory();
		}
		
		public MutipartBodyRest done() {
			return bodyRest;
		}
		
		/***
		 * @param body HttpEntity
		 * @see {@link HttpEntity}
		 */
		public Factory setBody(HttpEntity body) {
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
