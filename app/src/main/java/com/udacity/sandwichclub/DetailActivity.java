package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    private Unbinder unbinder;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @BindView(R.id.image_iv)
    ImageView ingredientsIv;
    @BindView(R.id.tv_main_name)
    TextView tvMainName;
    @BindView(R.id.tv_place_of_origin)
    TextView tvPlaceOfOrigin;
    @BindView(R.id.tv_also_known_as)
    TextView tvAlsoKnownAs;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //bind the views
        unbinder = ButterKnife.bind(this);


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
        /*Log.e("allan", json);*/
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        //ActionBar, set the title and the back arrow button function
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(sandwich.getMainName());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbind the views
        unbinder.unbind();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        tvMainName.setText(sandwich.getMainName());
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        tvAlsoKnownAs.setText(JsonUtils.getFormattedString(sandwich.getAlsoKnownAs()));
        tvDescription.setText(sandwich.getDescription());
        tvIngredients.setText(JsonUtils.getFormattedString(sandwich.getIngredients()));
    }
}
