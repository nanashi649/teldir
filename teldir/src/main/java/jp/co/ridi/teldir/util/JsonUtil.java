package jp.co.ridi.teldir.util;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

/**
 * JSONユーティリティ
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 検索データ → JSON変換処理(DataTables用)
     *
     * @param value
     * @return
     * @throws JsonProcessingException
     */
    public static String convSearchData(Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", value);
        return convJson(map);
    }

    /**
     * 検索データ → JSON変換処理(DataTables用)
     *
     * @param value
     * @param recordsTotal
     * @param recordsFiltered
     * @return
     * @throws JsonProcessingException
     */
    public static String convSearchData(Object value, long recordsTotal, long recordsFiltered) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", value);
        map.put("recordsTotal", recordsTotal);
        map.put("recordsFiltered", recordsFiltered);
        return convJson(map);
    }

    /**
     * Object → JSON変換処理
     *
     * @param value
     * @return JSON
     * @throws JsonProcessingException
     */
    public static String convJson(Object value) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON → Object変換処理
     *
     * @param json
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T convObject(String json, Class<T> clazz) {
        try {
            return (StringUtils.isEmpty(json)) ? null : mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON → Object変換処理(配列 → リスト変換用)
     *
     * @param json
     * @param type
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T convObject(String json, TypeReference<T> type) {
        try {
            return (StringUtils.isEmpty(json)) ? null : mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
