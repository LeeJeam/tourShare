package com.xmyy.search.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.text.SimpleDateFormat;


/********
 * 
 * @author zlp
 *
 */
public class JsonMapper {
	protected final org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());
	
	public static String DEFAULT_JSONP_NAME = "callback";

	public static final JsonMapper DEFAULT_MAPPER = defaultMapper();
	/*****
	 * 忽略Null值
	 */
	public static final JsonMapper IGNORE_NULL = ignoreNull();
	/*****
	 * 忽略空值
	 */
	public static final JsonMapper IGNORE_EMPTY = ignoreEmpty();
	
	public static JsonMapper defaultMapper(){
		JsonMapper jsonm = new JsonMapper(Include.ALWAYS);
		return jsonm;
	}

	public static JsonMapper ignoreNull(){
		JsonMapper jsonm = new JsonMapper(Include.NON_NULL);
		return jsonm;
	}
	
	public static JsonMapper ignoreEmpty(){
		JsonMapper jsonm = new JsonMapper(Include.NON_EMPTY);
		return jsonm;
	}
	
	public static JsonMapper mapper(Include include, boolean fieldVisibility){
		JsonMapper jsonm = new JsonMapper(include, fieldVisibility);
		return jsonm;
	}


	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
   private ObjectMapper objectMapper = new ObjectMapper();

	public JsonMapper(Include include){
		this(include, false);
	}
	public JsonMapper(Include include, boolean fieldVisibility){
		objectMapper.setSerializationInclusion(include);
//		setDateFormat(DATE_TIME);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(Feature.ALLOW_COMMENTS, true);
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if(fieldVisibility)
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	public JsonMapper setDateFormat(String format){
		if(StringUtils.isBlank(format))
			return this;
		objectMapper.setDateFormat(new SimpleDateFormat(format));
		return this;
	}

	public String toJson(Object object){
		return toJson(object, true);
	}
	
	public String toJson(Object object, boolean throwIfError){
		String json = "";
		try {
			json = this.objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			if(throwIfError)
				throw new RuntimeException("parse to json error : " + object, e);
			else
				logger.warn("parse to json error : " + object);
		}
		return json;
	}
	

	public byte[] toJsonBytes(Object object){
		return toJsonBytes(object, true);
	}
	
	public byte[] toJsonBytes(Object object, boolean throwIfError){
		byte[] json = null;
		try {
			json = this.objectMapper.writeValueAsBytes(object);
		} catch (Exception e) {
			if(throwIfError)
				throw new RuntimeException("parse to json error : " + object, e);
			else
				logger.warn("parse to json error : " + object);
		}
		return json;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	public <T> T fromJson(String json, TypeReference<T> valueTypeRef){
		if(StringUtils.isBlank(json))
			return null;
		Assert.notNull(valueTypeRef, "valueTypeRef not null");
		T obj = null;
		try {
			obj = this.objectMapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			throw new RuntimeException("parse json to "+valueTypeRef+" error : " + json, e);
		}
		return obj;
	}

	public <T> T fromJson(String json, Class<T> valueType){
		if(StringUtils.isBlank(json))
			return null;
		Assert.notNull(valueType);
		T obj = null;
		try {
			obj = this.objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			throw new RuntimeException("parse json to object error : " + valueType + " => " + e.getMessage(), e);
		}
		return obj;
	}

	public <T> T fromJson(InputStream in, Class<T> valueType){
		if(in==null)
			return null;
		Assert.notNull(valueType);
		T obj = null;
		try {
			obj = this.objectMapper.readValue(in, valueType);
		} catch (Exception e) {
			throw new RuntimeException("parse json to object error : " + valueType + " => " + e.getMessage(), e);
		}
		return obj;
	}

	public <T> T fromJson(byte[] bytes, Class<T> valueType){
		if(bytes==null)
			return null;
		Assert.notNull(valueType);
		T obj = null;
		try {
			obj = this.objectMapper.readValue(bytes, valueType);
		} catch (Exception e) {
			throw new RuntimeException("parse json to object error : " + valueType + " => " + e.getMessage(), e);
		}
		return obj;
	}

}
