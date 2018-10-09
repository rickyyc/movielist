package com.ricky.eaxmovielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ricky.eaxmovielist.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = MovieListFragment.newInstance(1);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_holder, fragment)
                .commitNow();
    }

    @Override
    public void onListFragmentInteraction(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailFragment.ARG_POSTER, movie.getPosterPath());
        intent.putExtra(MovieDetailFragment.ARG_TITLE, movie.getTitle());
        intent.putExtra(MovieDetailFragment.ARG_RELEASE_DATE, movie.getReleaseDate());
        intent.putExtra(MovieDetailFragment.ARG_OVERVIEW, movie.getOverview());
        intent.putExtra(MovieDetailFragment.ARG_BACKDROP, movie.getBackdropPath());

        startActivity(intent);
    }
}
