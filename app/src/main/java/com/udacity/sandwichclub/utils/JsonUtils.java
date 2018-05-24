package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        //Defining our list of strings and indexes
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        int indexAlsoKnown, indexIngredients;

        //let's get the root json object passing the json response into it
        try {
            JSONObject rootJsonObject = new JSONObject(json);

            //Now that we have the rootObject of the whole json, let's get the object "name"
            //Once we have the nameObject, we can get the key value of mainName
            JSONObject nameObject = rootJsonObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");

            //We can get the alsoKnownAs values by calling JSONArray method since it's an array on the nameObject passing in the alsoKnownAs key.
            //then loop through each alsoKnownAs value and add it into a list of string
            JSONArray alsoKownAsArray = nameObject.getJSONArray("alsoKnownAs");
            for ( indexAlsoKnown = 0; indexAlsoKnown < alsoKownAsArray.length(); indexAlsoKnown++) {
                alsoKnownAsList.add( alsoKownAsArray.getString(indexAlsoKnown) );
            }

            //let's do the same thing for ingredients, but we need to calling the JSONArray method from the main root json object rootJsonObject
            JSONArray ingredientsArray = rootJsonObject.getJSONArray( "ingredients" );
            for (indexIngredients = 0; indexIngredients <ingredientsArray.length(); indexIngredients++ ) {
                ingredientsList.add( ingredientsArray.getString( indexIngredients ) );
            }

            //Let's get image, description, and place of origin
            String image = rootJsonObject.getString( "image" );
            String description = rootJsonObject.getString( "description" );
            String placeOfOrigin = rootJsonObject.getString( "placeOfOrigin" );

            // return a new sandwich with mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList );


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}




