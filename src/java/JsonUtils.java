import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;
import java.util.*;

/**
 * 
 * @author laert.gjoni
 *
 */
public class JsonUtils {
	
	private static final String JSON_READ_FAILED = "Json read failed";
	private static final String JSON_WRITE_FAILED = "Json write failed";

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @param allowMissingQuote
	 * @return List of Objects of type T from a json string
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> listOfObjectsFromJson(String json, Class<T> clazz, boolean allowMissingQuote) {
		return collectionFromJson(json, clazz, List.class, allowMissingQuote);
	}

    /**
     *
     * @param json
     * @param clazz
     * @return List of Objects of type T from a json string
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> listOfObjectsFromJson(String json, Class<T> clazz) {
        return collectionFromJson(json, clazz, List.class);
    }

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @return List of Objects of type T from a json string
	 */
	@SuppressWarnings("unchecked")
	public static <T> TreeSet<T> setOfObjectsFromJson(String json, Class<T> clazz) {
		return collectionFromJson(json, clazz, TreeSet.class);
	}

    /**
     *
     * @param json
     * @param clazzObj
     * @param clazzCollection
     * @return Collection from json String
     */
    @SuppressWarnings("rawtypes")
    public static <T extends Collection> T collectionFromJson(String json, Class<?> clazzObj, Class<T> clazzCollection) {
        return collectionFromJson(json, clazzObj, clazzCollection, false);
    }
	/**
	 * 
	 * @param json
     * @param allowMissingQuote
	 * @param clazzObj
	 * @param clazzCollection
	 * @return Collection from json String
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Collection> T collectionFromJson(String json, Class<?> clazzObj, Class<T> clazzCollection, boolean allowMissingQuote) {
		ObjectMapper objMapper = buildObjectMapper(allowMissingQuote);
		
		JavaType javaType = objMapper.getTypeFactory().constructParametricType(clazzCollection, clazzObj);
		try {
			return objMapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		} catch (IOException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		}
	}

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @return Object of type T from json String
	 */
	public static <T> T objectFromJson(String json, Class<T> clazz) {
		ObjectMapper objMapper = buildObjectMapper();
		try {
			return objMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		} catch (IOException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		}
	}
	
	/**
	 * 
	 * @param json
	 * @param clazz
	 * @return Object of type T from json String
	 */
	public static <T> T objectFromJsonBasedOnFieldOnly(String json, Class<T> clazz) {
		ObjectMapper objMapper = buildObjectMapperBasedOnFieldOnly();
		try {
			return objMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		} catch (IOException e) {
			throw new RuntimeException(JSON_READ_FAILED, e);
		}
	}
	
	
	/**
	 * 
	 * @param object
	 * @return json as String from given object
	 */
	public static <T> String objectToJson(T object) {
		ObjectMapper objMapper = buildObjectMapper();
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(JSON_WRITE_FAILED, e);
		}
	}
	
	/**
	 * 
	 * @param object
	 * @return json as String from given object
	 */
	public static <T> String objectToJsonBasedOnFieldOnly(T object) {
		ObjectMapper objMapper = buildObjectMapperBasedOnFieldOnly();
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(JSON_WRITE_FAILED, e);
		}
	}


    /**
     *
     * @param object
     * @return json as String from given object
     */
    @SuppressWarnings("rawtypes")
    public static <T extends Collection> String collectionToJson(T object) {
        return collectionToJson(object, false);
    }

	/**
	 * 
	 * @param object
	 * @return json as String from given object
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Collection> String collectionToJson(T object, boolean allowMissingQuote) {
		ObjectMapper objMapper = buildObjectMapper(allowMissingQuote);
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(JSON_WRITE_FAILED, e);
		}
	}

	/**
	 * 
	 * @param lista
	 * @return json as String from a list
	 */
	public static String listToJson(List<?> lista) {
		return collectionToJson(lista);
	}

    /**
     *
     * @param lista
     * @return json as String from a list
     */
    public static String listToJson(List<?> lista, boolean allowMissingQuote) {
        return collectionToJson(lista, allowMissingQuote);
    }

    /**
	 * 
	 * @param lista
	 * @return json as String from a Set
	 */
	public static String setToJson(Set<?> lista) {
		return collectionToJson(lista);
	}

	private static ObjectMapper buildObjectMapper() {
		return buildObjectMapper(false);
	}

	private static ObjectMapper buildObjectMapper(boolean allowMissingQuote) {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JodaModule());
		objMapper.setTimeZone(TimeZone.getTimeZone("CET"));
		objMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, allowMissingQuote);
		objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return objMapper;
	}

	private static ObjectMapper buildObjectMapperBasedOnFieldOnly() {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.registerModule(new JodaModule());
		objMapper.setTimeZone(TimeZone.getTimeZone("CET"));

		objMapper.setVisibilityChecker(objMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

		return objMapper;
	}
}