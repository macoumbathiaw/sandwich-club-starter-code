package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Declaring the Views values
    private TextView alsoKnownAsTv, placeOfOriginTv, descriptionTv, ingredientsTv, alsoKnownAsVisibilityTv, placeOfOriginVisibilityTv, ingredientsVisibilityTv, descriptionVisibilityTv ;
    private ImageView sandwichPitcureIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Find the Views
        sandwichPitcureIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownAsVisibilityTv = findViewById(R.id.also_known_tv_visibility);
        placeOfOriginVisibilityTv = findViewById(R.id.origin_tv_visibility);
        ingredientsVisibilityTv = findViewById(R.id.ingredients_tv_visibility);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        setTitle(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichPitcureIv);

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Let's get the values of the key alsoKnownAs, and append them into the TexView alsoKnownAsTv. If the key is empty, we hide
        if (sandwich.getAlsoKnownAs() !=null && !sandwich.getAlsoKnownAs().isEmpty()){
            for ( int i =0; i<sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownAsTv.append(sandwich.getAlsoKnownAs().get(i) + "\n" );
            }
        } else {
            alsoKnownAsTv.setVisibility( View.GONE );
            alsoKnownAsVisibilityTv.setVisibility(View.GONE);
        }

        // Let's get the place of origin, and set them into the TexView placeOfOriginTv. If the key is empty, we hide it
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTv.setText( sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTv.setVisibility(View.GONE);
            placeOfOriginVisibilityTv.setVisibility(View.GONE);
        }

        // Let's get the ingredients, and append them into the TexView ingredientsTv. If the key is empty, we hide

        if (sandwich.getIngredients() !=null && !sandwich.getIngredients().isEmpty()){
            for ( int index =0; index <sandwich.getIngredients().size(); index++) {
                ingredientsTv.append(sandwich.getIngredients().get(index) + "\n"  );
            }
        } else {
            ingredientsTv.setVisibility( View.GONE );
            ingredientsVisibilityTv.setVisibility(View.GONE);

        }

        // Let's get the descriptionTv, and set it into the TexView ingredientsTv. All sandwiches have a description
        descriptionTv.setText( sandwich.getDescription());





    }
}
