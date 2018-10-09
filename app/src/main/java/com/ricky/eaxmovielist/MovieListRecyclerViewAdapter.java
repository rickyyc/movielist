package com.ricky.eaxmovielist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricky.eaxmovielist.MovieListFragment.OnListFragmentInteractionListener;
import com.ricky.eaxmovielist.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Movie} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MovieListRecyclerViewAdapter extends RecyclerView.Adapter<MovieListRecyclerViewAdapter.ViewHolder> {

    public static final String THUMBNAIL_BASE_URL = "https://image.tmdb.org/t/p/w92/";
    public static final int THUMBNAIL_SIZE = 92;
    private final List<Movie> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MovieListRecyclerViewAdapter(List<Movie> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movielist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        Picasso.get()
                .load(THUMBNAIL_BASE_URL + holder.mItem.getPosterPath())
                .resize(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
                .centerCrop()
                .into(holder.mThumbnail);

        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mReleaseDate.setText(mValues.get(position).getReleaseDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mThumbnail;
        public final TextView mTitle;
        public final TextView mReleaseDate;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mThumbnail = view.findViewById(R.id.thumbnail);
            mTitle = view.findViewById(R.id.title);
            mReleaseDate = view.findViewById(R.id.release_date);
        }
    }
}
