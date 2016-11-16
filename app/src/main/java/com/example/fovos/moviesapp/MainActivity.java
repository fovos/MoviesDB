package com.example.fovos.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add new Fragment with movies grid only the first time
        //ean den to valw  to if, tote tha prosthetei sinexws fragment panw apo to proigoumeno
        //opote allaze to configuration tou device
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new MoviesFragment()).commit();
        }
    }
}
