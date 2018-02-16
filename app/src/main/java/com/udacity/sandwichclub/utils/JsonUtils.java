package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //constant use in json elements
    private static final String NOT_APPLICABLE = "Not applicable";
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGERDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            //initialize JsonObject
            JSONObject jsonObject = new JSONObject(json);

            //Get the name of the sandwich (mainName and alsoKnownAs)
            JSONObject jsonObjectName = jsonObject.getJSONObject(NAME);
            String mainName = jsonObjectName.getString(MAIN_NAME);
            JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray(ALSO_KNOWN_AS);
            List<String> listAKA = getList(jsonArrayAlsoKnownAs);


            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            String description = jsonObject.getString(DESCRIPTION);
            String image = jsonObject.getString(IMAGE);
            JSONArray jsonArrayIngredients = jsonObject.getJSONArray(INGERDIENTS);
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
     * This helper method is used to convert list to string and remove the brackets in the List
     *
     * @param list
     * @return
     */
    public static String getFormattedString(List<String> list) {
        if (list.size() == 0) {
            return NOT_APPLICABLE;
        } else {
            return list.toString().replace("[", "")
                    .replace("]", "");
        }
    }


}
