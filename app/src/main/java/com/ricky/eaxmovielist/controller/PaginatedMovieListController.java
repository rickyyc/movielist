package com.ricky.eaxmovielist.controller;

import android.support.annotation.NonNull;

import com.ricky.eaxmovielist.model.Movie;
import com.ricky.eaxmovielist.model.ResultList;
import com.ricky.eaxmovielist.network.MovieDbApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Manages the data as fetched from the server.
 */
public class PaginatedMovieListController {
    /**
     * Listener to be notified when new data has arrived.
     */
    public interface PaginatedListControllerListener {
        void isLoading(boolean isLoading);
        void rangeInserted(int positionStart, int itemCount);
        void reloaded();
    }

    private int mCurrentPage = 0;
    private List<Movie> mMovieList;
    private PaginatedListControllerListener mListener;
    private boolean mIsLoading = false;

    private PaginatedMovieListController() {
        // do nothing
    }

    public PaginatedMovieListController(@NonNull PaginatedListControllerListener listener) {
        mMovieList = new ArrayList<>();
        mListener = listener;
    }

    /**
     * Fetches more movies by advancing to the next page, and appends to the movie list.
     */
    public void fetchMore() {
        mIsLoading = true;
        mListener.isLoading(true);

        MovieDbApi.discover(++mCurrentPage, new Callback<ResultList<Movie>>() {
            @Override
            public void onResponse(Call<ResultList<Movie>> call, Response<ResultList<Movie>> response) {
                ResultList<Movie> resultList = response.body();

                if (resultList != null && resultList.getResults() != null && resultList.getResults().size() > 0 && resultList.getPage() == mCurrentPage) {
                    int positionStart = mMovieList.size();
                    mMovieList.addAll(resultList.getResults());

                    if (mCurrentPage == 1) {
                        mListener.reloaded();
                    } else {
                        mListener.rangeInserted(positionStart, resultList.getResults().size());
                    }
                }

                mIsLoading = false;
                mListener.isLoading(false);
            }

            @Override
            public void onFailure(Call<ResultList<Movie>> call, Throwable t) {
                call.cancel();
                mIsLoading = false;
                mListener.isLoading(false);
            }
        });
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public List<Movie> getMovieList() {
        return mMovieList;
    }
}
