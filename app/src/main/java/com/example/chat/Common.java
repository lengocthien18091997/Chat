package com.example.chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Common {

    public static String[] jsonToArr(String response) {
        String arr[] = null;
        response = response.substring(1, response.length() - 1);
        arr = response.split(",");
        if (arr.length > 0 && !arr[0].equals("")) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].substring(1, arr[i].length() - 1);
            }
        }
        return arr;
    }
}
