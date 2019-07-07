package top.ibase4j.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class JsonUtil {
	protected static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static volatile ObjectMapper mapper;

	public static ObjectMapper createObjectMapper() {
		return createObjectMapper(null, null);
	}

	public static ObjectMapper createObjectMapper(Boolean registerJaxb) {
		return createObjectMapper(registerJaxb, null);
	}

	public static ObjectMapper createObjectMapper(Boolean registerJaxb, String propertyNamingStrategy) {

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
		objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		if (propertyNamingStrategy != null) {
			if (propertyNamingStrategy.equalsIgnoreCase("SNAKE_CASE")) {
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			} else if (propertyNamingStrategy.equalsIgnoreCase("UPPER_CAMEL_CASE")) {
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
			} else if (propertyNamingStrategy.equalsIgnoreCase("LOWER_CAMEL_CASE")) {
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
			} else if (propertyNamingStrategy.equalsIgnoreCase("LOWER_CASE")) {
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
			} else if (propertyNamingStrategy.equalsIgnoreCase("KEBAB_CASE")) {
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
			}
		}
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return objectMapper;
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, getObjectMapper());
	}

	public static <T> T fromJson(String json, Class<T> clazz, ObjectMapper mapper) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException ex) {
			logger.error(ex.getMessage() + "\n" + json + "\n", ex);
		} catch (JsonMappingException ex) {
			logger.error(ex.getMessage() + "\n" + json + "\n", ex);
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

	public static <T> T fromJson(String json, JavaType valueType) {
		return fromJson(json, valueType, getObjectMapper());
	}

	public static <T> T fromJson(String json, JavaType valueType, ObjectMapper mapper) {
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException ex) {
			logger.error(ex.getMessage() + "\n" + json + "\n", ex);
		} catch (JsonMappingException ex) {
			logger.error(ex.getMessage() + "\n" + json + "\n", ex);
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
		return fromJson(json, valueTypeRef, getObjectMapper());
	}

	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef, ObjectMapper mapper) {
		try {
			return mapper.readValue(json, valueTypeRef);
		} catch (JsonParseException ex) {
			logger.error(ex.getMessage() + "\n" + json + "\n", ex);
		} catch (JsonMappingException ex) {
			logger.error(ex.getMessage() + "\n" + json + "\n", ex);
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

	public static ObjectMapper getObjectMapper() {
		if (mapper == null) {
			synchronized (JsonUtil.class) {
				if (mapper == null) {
					mapper = createObjectMapper();
				}
			}
		}
		return mapper;
	}

	public static String toJson(Object o) {
		return toJson(o, getObjectMapper());
	}

	public static String toJson(Object o, Consumer<ObjectMapper> config) {
		ObjectMapper objectMapper = null;
		if (config != null) {
			objectMapper = createObjectMapper();
			config.accept(objectMapper);
		}
		if (objectMapper == null) {
			objectMapper = getObjectMapper();
		}
		return toJson(o, objectMapper);
	}

	public static String toJson(Object o, ObjectMapper mapper) {
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "";
	}

	public static StringBuffer tabs(int level) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("\t");
		}
		return sb;
	}

	public static StringBuffer createBeanDefine(String className, LinkedHashMap<String, Object> map) {
		return createBeanDefine(className, map, null, null, 1, true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static StringBuffer createBeanDefine(String className, LinkedHashMap<String, Object> map, Set<String> importx, List<StringBuffer> top, int level, boolean innserClass) {
		StringBuffer sb = new StringBuffer();
		int index = (innserClass ? 1 : 0);
		if (top == null) {
			top = new ArrayList<>();
		}
		Set<String> imports = null;
		if (importx != null) {
			imports = importx;
		} else {
			if (level == 1 && innserClass) {
				imports = new HashSet<>();
			}
		}
		if (innserClass == false) {
			sb.append("import java.io.Serializable;\n@SuppressWarnings(\"serial\")\n");
		}
		sb.append(tabs(index - 1)).append("public ").append(innserClass && level != 1 ? "static " : "").append("class ").append(className).append(" implements Serializable ").append("{").append("\n");
		Set<Entry<String, Object>> entrySet = map.entrySet();
		StringBuffer prop = new StringBuffer();
		StringBuffer toStr = new StringBuffer();
		toStr.append("StringBuilder sb =new StringBuilder();\n");
		toStr.append(tabs(index+1)).append("sb.append(this.getClass().getSimpleName()).append(\"{\");\n");
		imports.add("java.io.Serializable");
		for (Entry<String, Object> entry : entrySet) {
			Object value = entry.getValue();

			String type = value != null ? value.getClass().getSimpleName() : "String";
			String key = entry.getKey();
			boolean isString = value != null && value.getClass() == String.class ? true : false;
			boolean isDate = false;
			/*if (key.contains("time") || key.contains("Time") || key.contains("date") || key.contains("Date")) {
				if ("String".equals(type) || "Long".equals(type) || "Integer".equals(type)) {
					
					imports.add("java.text.SimpleDateFormat");
					isDate=true;
				}
			}*/
		
			toStr.append(tabs(index + 1)).append("sb.append(\"\\\"").append(key).append("\\\":\")");
			
			toStr.append(isString||isDate?".append(\"\\\"\").append(":".append(");
			if(isDate){
				toStr.append(key).append("!=null?").append("new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(").append(key).append("):\"\"");
			}else{
				toStr.append(key).append("!=null?").append("").append(key).append(":").append(isString?"\"\"":"null");
			}
			
			toStr.append(isString||isDate?").append(\"\\\"\")":")");
			
			toStr.append(".append(\" , \");\n");
			String propName = Character.toUpperCase(key.charAt(0)) + key.substring(1);
			if ("LinkedHashMap".equals(type)) {
				StringBuffer s = createBeanDefine(propName, (LinkedHashMap<String, Object>) value, imports, top, level + 1, innserClass);
				top.add(s);

				sb.append(tabs(index)).append("protected ").append(propName).append(" ").append(key).append(";\n");

				prop.append(tabs(index)).append("public ").append(propName).append(" get").append(propName).append("(").append("){\n");
				prop.append(tabs(index + 1)).append("return this.").append(key).append(";\n");
				prop.append(tabs(index)).append("}\n");

				prop.append(tabs(index)).append("public ").append("void").append(" set").append(propName).append("(").append(propName).append(" ").append(key).append("){\n");
				prop.append(tabs(index + 1)).append("this.").append(key).append("=").append(key).append(";\n");
				prop.append(tabs(index)).append("}\n");
			} else if ("ArrayList".equals(type)) {
				List<Object> t = (List<Object>) value;
				imports.add("java.util.List");
				if (t.size() > 0) {
					Class<? extends Object> listType = t.get(0).getClass();
					if (listType.equals(LinkedHashMap.class)) {
						StringBuffer s = createBeanDefine(propName.replaceAll("s$", ""), (LinkedHashMap<String, Object>) t.get(0), imports, top, level + 1, innserClass);
						top.add(s);

						sb.append(tabs(index)).append("protected ").append("List<").append(propName.replaceAll("s$", "")).append("> ").append(key).append(";\n");

						prop.append(tabs(index)).append("public ").append("List<").append(propName.replaceAll("s$", "")).append("> get").append(propName).append("(").append("){\n");
						prop.append(tabs(index + 1)).append("return this.").append(key).append(";\n");
						prop.append(tabs(index)).append("}\n");

						prop.append(tabs(index)).append("public ").append("void").append(" set").append(propName).append("(").append("List<").append(propName.replaceAll("s$", "")).append("> ").append(key).append("){\n");
						prop.append(tabs(index + 1)).append("this.").append(key).append("=").append(key).append(";\n");
						prop.append(tabs(index)).append("}\n");
					} else {
						String typeName = listType.getSimpleName();
						sb.append(tabs(index)).append("protected ").append("List<").append(typeName).append("> ").append(key).append(";\n");

						prop.append(tabs(index)).append("public ").append("List<").append(typeName).append("> get").append(propName).append("(").append("){\n");
						prop.append(tabs(index + 1)).append("return this.").append(key).append(";\n");
						prop.append(tabs(index)).append("}\n");

						prop.append(tabs(index)).append("public ").append("void").append(" set").append(propName).append("(").append("List<").append(typeName).append("> ").append(key).append("){\n");
						prop.append(tabs(index + 1)).append("this.").append(key).append("=").append(key).append(";\n");
						prop.append(tabs(index)).append("}\n");
					}
				}
			} else {

				if (value != null) {
					imports.add(value.getClass().getName());
				}
				/*if (key.contains("time") || key.contains("Time") || key.contains("date") || key.contains("Date")) {
					if ("String".equals(type) || "Long".equals(type) || "Integer".equals(type)) {
						type = "Date";
						imports.add(Date.class.getName());
					}
				}*/
				sb.append(tabs(index)).append("protected ").append(type).append(" ").append(key).append(";\n");

				prop.append(tabs(index)).append("public ").append(type).append(" get").append(propName).append("(").append("){\n");
				prop.append(tabs(index + 1)).append("return this.").append(key).append(";\n");
				prop.append(tabs(index)).append("}\n");

				prop.append(tabs(index)).append("public ").append("void").append(" set").append(propName).append("(").append(type).append(" ").append(key).append("){\n");
				prop.append(tabs(index + 1)).append("this.").append(key).append("=").append(key).append(";\n");
				prop.append(tabs(index)).append("}\n");
			}
		}
		if(entrySet.size()>0){
			toStr.delete(toStr.length() - 6, toStr.length());
		}
		toStr.append("}\");");
		sb.append(prop).append("\n");
		if (level == 1) {
			for (StringBuffer s : top) {
				sb.append(s).append("\n");
			}
		}

		sb.append("\n");
		sb.append(tabs(index)).append("public String toString(){\n");
		sb.append(tabs(index + 1)).append(toStr).append("\n");
		sb.append(tabs(index + 1)).append("return sb.toString();\n");
		
		sb.append(tabs(index)).append("}\n");

		sb.append(tabs(index - 1)).append("}");
		if (innserClass = true && level == 1 || innserClass == false) {
			StringBuffer imp = new StringBuffer();
			for (String p : imports) {
				if (!p.startsWith("java.lang")) {
					imp.append("import ").append(p).append(";\n");
				}
			}
			sb.insert(0, imp);
		}
		return sb;
	}

}
