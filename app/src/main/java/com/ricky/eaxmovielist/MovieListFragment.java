package com.ricky.eaxmovielist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ricky.eaxmovielist.controller.PaginatedMovieListController;
import com.ricky.eaxmovielist.model.Movie;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MovieListRecyclerViewAdapter mAdapter;
    private PaginatedMovieListController mMovieListController = new PaginatedMovieListController(new PaginatedMovieListController.PaginatedListControllerListener() {
        @Override
        public void isLoading(boolean isLoading) {
            ProgressBar progressBar = getView().findViewById(R.id.progress_bar);
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }

        @Override
        public void rangeInserted(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void reloaded() {
            mAdapter.notifyDataSetChanged();
        }
    });

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(int columnCount) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movielist_list, container, false);

        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            mLayoutManager = new LinearLayoutManager(context);
        } else {
            mLayoutManager = new GridLayoutManager(context, mColumnCount);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MovieListRecyclerViewAdapter(mMovieListController.getMovieList(), mListener);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisible = mLayoutManager.findLastVisibleItemPosition();
                int total = mLayoutManager.getItemCount();

                if (!mMovieListController.isLoading() && total <= lastVisible + 20) {
                    mMovieListController.fetchMore();
                }
            }
        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieListController.fetchMore();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Movie item);
    }
}
