package com.ricky.eaxmovielist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    public static final String ARG_POSTER = "POSTER";
    public static final String ARG_TITLE = "TITLE";
    public static final String ARG_RELEASE_DATE = "RELEASE DATE";
    public static final String ARG_OVERVIEW = "OVERVIEW";
    public static final String ARG_BACKDROP = "BACKDROP";
    public static final String BACKDROP_BASEURL = "https://image.tmdb.org/t/p/w780/";
    public static final String POSTER_BASEURL = "https://image.tmdb.org/t/p/w342/";

    private String mCoverArt;
    private String mTitle;
    private String mReleaseDate;
    private String mOverview;
    private String mBackdrop;

    private OnFragmentInteractionListener mListener;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(String coverArt, String title, String releaseDate, String overview, String backdrop) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POSTER, coverArt);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_RELEASE_DATE, releaseDate);
        args.putString(ARG_OVERVIEW, overview);
        args.putString(ARG_BACKDROP, backdrop);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCoverArt = getArguments().getString(ARG_POSTER);
            mTitle = getArguments().getString(ARG_TITLE);
            mReleaseDate = getArguments().getString(ARG_RELEASE_DATE);
            mOverview = getArguments().getString(ARG_OVERVIEW);
            mBackdrop = getArguments().getString(ARG_BACKDROP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(mTitle);

        ImageView backdrop = getView().findViewById(R.id.backdrop);
        Picasso.get()
                .load(BACKDROP_BASEURL + mBackdrop)
                .into(backdrop);

        ImageView coverArtImageView = getView().findViewById(R.id.cover_art);
        Picasso.get()
                .load(POSTER_BASEURL + mCoverArt)
                .into(coverArtImageView);

        TextView title = getView().findViewById(R.id.title);
        title.setText(mTitle);

        TextView releaseDate = getView().findViewById(R.id.release_date);
        releaseDate.setText(getString(R.string.release_date) + mReleaseDate);

        TextView overview = getView().findViewById(R.id.overview);
        overview.setText(mOverview);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
