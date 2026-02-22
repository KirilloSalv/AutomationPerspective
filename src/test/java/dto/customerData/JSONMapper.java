package dto.customerData;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONMapper {


    private static final ObjectMapper mapper = new ObjectMapper();


    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }


    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON", e);
        }
    }

    public static String toPrettyJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize response JSON", e);
        }
    }

}
