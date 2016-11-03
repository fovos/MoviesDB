package com.example.fovos.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO:Create Async task for retrieving movies list
        //TODO:Assign the Datasource of Gridview to Movies Images

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }
}
