package com.example.fovos.moviesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    //FIELDS
    private static ArrayList<String> selected_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //start new Fragment with movies grid
        getSupportFragmentManager().beginTransaction().add(R.id.container,new DetailFragment()).commit();
    }

    /**
     * A placeholder fragment containing a simple view.Apotelei fragment
     */
    public static class DetailFragment extends Fragment {

        public DetailFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //inflating the fragment layout to Mainactivity container (reference and associate)
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            ImageView image_placeholder=(ImageView)rootView.findViewById(R.id.imageview);

            Intent intent=getActivity().getIntent();
            if(intent!=null){
                selected_movie=intent.getStringArrayListExtra("selected_movie");

                new DownloadImageTask(image_placeholder).execute(selected_movie.get(0));
                ((TextView)rootView.findViewById(R.id.original_title)).setText(selected_movie.get(1));
                ((TextView)rootView.findViewById(R.id.overview)).setText(selected_movie.get(2));
                ((TextView)rootView.findViewById(R.id.vote_average)).setText(selected_movie.get(3));
                ((TextView)rootView.findViewById(R.id.release_date)).setText(selected_movie.get(4));
            }
            return rootView;
        }
    }

    private static class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String urldisplay = params[0];
            Bitmap mIcon11 = null;

            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Image Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
