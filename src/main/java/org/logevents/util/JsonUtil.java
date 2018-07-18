package org.logevents.util;

import java.util.List;
import java.util.Map;

/**
 * A utility class for minimal JSON functionality. Prints a
 * Map<String, Object> as JSON, supporting Map, Iterable, String,
 * Number and Boolean.
 *
 * This class is standalone and only depends on Java. It can be copied-and-
 * pasted into exising project.
 *
 * This class is independently shared under the BSD license 2.0.
 *
 * @author Johannes Brodwall <johannes@brodwall.com>
 *
 */
public class JsonUtil {

    private String indentSetting = "  ";

    public String toJson(Map<String, Object> jsonObject) {
        StringBuilder result = new StringBuilder();
        objectToJson(jsonObject, result, "");
        return result.toString();
    }

    private void toJson(Map<String, Object> json, StringBuilder result, String indent) {
        result.append("{\n");
        boolean first = true;
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (!first) result.append(",\n");
            result.append(indent + indentSetting + "\"").append(jsonEscape(entry.getKey())).append("\": ");
            objectToJson(entry.getValue(), result, indent + indentSetting);
            first = false;
        }
        result.append("\n").append(indent).append("}");
    }

    private void toJson(Iterable<? extends Object> value, StringBuilder result, String indent) {
        result.append("[\n");
        boolean first = true;
        for (Object entry : value) {
            if (!first) result.append(",\n");
            result.append(indent + indentSetting);
            objectToJson(entry, result, indent + indentSetting);
            first = false;
        }
        result.append("\n").append(indent).append("]");
    }

    @SuppressWarnings("unchecked")
    private void objectToJson(Object value, StringBuilder result, String indent) {
        if (value instanceof Map) {
            toJson((Map<String,Object>)value, result, indent);
        } else if (value instanceof List) {
            toJson((Iterable<? extends Object>)value, result, indent);
        } else if (value instanceof String) {
            toJson((String)value, result);
        } else if (value instanceof Number) {
            result.append(value.toString());
        } else if (value instanceof Boolean) {
            result.append(value.toString());
        } else if (value == null) {
            result.append("null");
        } else {
            throw new IllegalArgumentException("Unsupported JSON element " + value.getClass().getName());
        }
    }

    private void toJson(String value, StringBuilder result) {
        result.append("\"").append(jsonEscape(value)).append("\"");
    }

    private String jsonEscape(String key) {
        StringBuilder result = new StringBuilder();
        for (char c : key.toCharArray()) {
            switch (c) {
            case '\\':
                result.append("\\");
                break;
            case '\n':
                result.append("\\n");
                break;
            case '\r':
                result.append("\\r");
                break;
            case '\t':
                result.append("\\t");
                break;
            case '\b':
                result.append("\\b");
                break;
            default:
                result.append(c);
                break;
            }
        }
        return result.toString();
    }
}