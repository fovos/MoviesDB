package com.example.fovos.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start new Fragment with movies grid
        getSupportFragmentManager().beginTransaction().add(R.id.container,new MoviesFragment()).commit();
    }
}
