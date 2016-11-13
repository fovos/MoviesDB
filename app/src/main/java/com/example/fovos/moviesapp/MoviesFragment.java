package com.example.fovos.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


public class MoviesFragment extends Fragment {


    //FIELDS
    GridView grd_view;
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

        //inflate fragment in mainactivity and associate with it
        View rootVIew=  inflater.inflate(R.layout.fragment_movies, container, false);
        grd_view=(GridView)rootVIew.findViewById(R.id.grd_movies);

        //TODO:Create Async task for retrieving movies list
        ArrayList<String> arr=new ArrayList<>();
        arr.add("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Star_Wars_Logo.svg/1280px-Star_Wars_Logo.svg.png");
        arr.add("https://mi-od-live-s.legocdn.com/r/www/r/starwars/-/media/franchises/starwars2015/tfa_cta_744x421.jpg?l.r2=-521045527");
        grd_view.setAdapter(new ImageAdapter(getActivity(),arr));
        Log.e("Array", arr.toString());
        //TODO:Assign the Datasource of Gridview to Movies Images

        // Inflate the layout for this fragment
        return rootVIew;
    }



}
