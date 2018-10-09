package com.ricky.eaxmovielist;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import static com.ricky.eaxmovielist.MovieDetailFragment.ARG_BACKDROP;
import static com.ricky.eaxmovielist.MovieDetailFragment.ARG_OVERVIEW;
import static com.ricky.eaxmovielist.MovieDetailFragment.ARG_POSTER;
import static com.ricky.eaxmovielist.MovieDetailFragment.ARG_RELEASE_DATE;
import static com.ricky.eaxmovielist.MovieDetailFragment.ARG_TITLE;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String coverArt = getIntent().getStringExtra(ARG_POSTER);
        String title = getIntent().getStringExtra(ARG_TITLE);
        String releaseDate = getIntent().getStringExtra(ARG_RELEASE_DATE);
        String overview = getIntent().getStringExtra(ARG_OVERVIEW);
        String backdrop = getIntent().getStringExtra(ARG_BACKDROP);
        Fragment fragment = MovieDetailFragment.newInstance(coverArt, title, releaseDate, overview, backdrop);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_holder, fragment)
                .commitNow();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
