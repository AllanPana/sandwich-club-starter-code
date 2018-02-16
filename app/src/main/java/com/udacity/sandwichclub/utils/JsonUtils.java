package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            //initialize JsonObject
            JSONObject jsonObject = new JSONObject(json);

            //Get the name of the sandwich (mainName and alsoKnownAs)
            JSONObject jsonObjectName = jsonObject.getJSONObject("name");
            String mainName = jsonObjectName.getString("mainName");
            JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray("alsoKnownAs");
            List<String> listAKA = getList(jsonArrayAlsoKnownAs);


            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = getList(jsonArrayIngredients);

            return new Sandwich(mainName, listAKA, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Helper method to get the Strings from the JsonArray
     *
     * @param jsonArray
     * @return list of String
     */
    private static List<String> getList(JSONArray jsonArray) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

    /**
     * This helper method is used to remove the brackets in the List
     *
     * @param list
     * @return
     */
    public static String getFormattedString(List<String> list) {
        if (list.size() == 0) {
            return "Not applicable";
        } else {
            return list.toString().replace("[", "")
                    .replace("]", "");
        }
    }


}
