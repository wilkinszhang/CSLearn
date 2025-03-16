package com.wilkinszhang;//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

public class JsonComparator {

    // 比较两个 JSON 字符串是否相等
    /*public static boolean areJsonEqual(String json1, String json2) {
        try {
            Object obj1 = new JSONObject(json1); // 尝试解析为 JSONObject
            Object obj2 = new JSONObject(json2);
            return areObjectsEqual(obj1, obj2);
        } catch (JSONException e) {
            try {
                Object obj1 = new JSONArray(json1); // 尝试解析为 JSONArray
                Object obj2 = new JSONArray(json2);
                return areObjectsEqual(obj1, obj2);
            } catch (JSONException ex) {
                return false; // 如果都不是合法的 JSON 格式
            }
        }
    }

    // 递归比较两个 JSON 对象
    private static boolean areObjectsEqual(Object obj1, Object obj2) {
        if (obj1 instanceof JSONObject && obj2 instanceof JSONObject) {
            return areJsonObjectsEqual((JSONObject) obj1, (JSONObject) obj2);
        } else if (obj1 instanceof JSONArray && obj2 instanceof JSONArray) {
            return areJsonArraysEqual((JSONArray) obj1, (JSONArray) obj2);
        } else {
            return obj1.equals(obj2);
        }
    }

    // 比较两个 JSONObject 是否相等
    private static boolean areJsonObjectsEqual(JSONObject json1, JSONObject json2) {
        if (json1.length() != json2.length()) {
            return false; // 键值对数量不同
        }
        for (String key : json1.keySet()) {
            if (!json2.has(key)) {
                return false; // key 不存在于 json2
            }
            if (!areObjectsEqual(json1.get(key), json2.get(key))) {
                return false; // 值不同
            }
        }
        return true;
    }

    // 比较两个 JSONArray 是否相等（忽略顺序）
    private static boolean areJsonArraysEqual(JSONArray arr1, JSONArray arr2) {
        if (arr1.length() != arr2.length()) {
            return false; // 长度不同
        }
        // 创建副本以避免修改原始数组
        JSONArray copyArr2 = new JSONArray(arr2.toList());
        for (int i = 0; i < arr1.length(); i++) {
            boolean found = false;
            for (int j = 0; j < copyArr2.length(); j++) {
                if (areObjectsEqual(arr1.get(i), copyArr2.get(j))) {
                    copyArr2.remove(j); // 匹配成功，从副本中移除
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false; // 当前元素无法匹配
            }
        }
        return true;
    }*/

    // 测试用例
    public static void main(String[] args) {
        String json1 = "{ \"name\": \"Alice\", \"age\": 30, \"hobbies\": [\"reading\", \"cycling\"] }";
        String json2 = "{ \"age\": 30, \"name\": \"Alice\", \"hobbies\": [\"cycling\", \"reading\"] }";

        String json3 = "[ { \"id\": 1, \"name\": \"item1\" }, { \"id\": 2, \"name\": \"item2\" } ]";
        String json4 = "[ { \"name\": \"item2\", \"id\": 2 }, { \"id\": 1, \"name\": \"item1\" } ]";

//        System.out.println("JSON 1 and JSON 2 are equal: " + areJsonEqual(json1, json2)); // true
//        System.out.println("JSON 3 and JSON 4 are equal: " + areJsonEqual(json3, json4)); // true
    }
}
