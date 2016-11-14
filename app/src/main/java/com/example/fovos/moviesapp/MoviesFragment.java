package com.example.fovos.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MoviesFragment extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {


    //FIELDS
    GridView grd_view;
    Spinner btn_select;
    ImageAdapter mImageAdapter;

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
        View rootVIew = inflater.inflate(R.layout.fragment_movies, container, false);
        grd_view = (GridView) rootVIew.findViewById(R.id.grd_movies);
        grd_view.setOnItemClickListener(this);
        btn_select = (Spinner) rootVIew.findViewById(R.id.sp_sort);
        btn_select.setOnItemSelectedListener(this);

        //TODO: Create another Array which will include info about each movie (not just image_path)

        ArrayList<String> arr = new ArrayList<>();
        mImageAdapter = new ImageAdapter(getActivity(), arr);
        grd_view.setAdapter(mImageAdapter);

        // Inflate the layout for this fragment
        return rootVIew;
    }

    private void updateMovies(String movie_type) {
        mImageAdapter.clear();
        new FetchMoviesData().execute(movie_type);   //api location_id, metric_system
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        if (btn_select.getSelectedItem().toString().equals(getString(R.string.top_rated))) {
            Log.e("Clicked", "Top Rated");
            updateMovies(getString(R.string.top_rated_value));
        } else {
            Log.e("Clicked", "Most Popular");
            updateMovies(getString(R.string.most_popular_value));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String selectedItem = parent.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(),selectedItem +" position"+position,Toast.LENGTH_LONG).show();
    }

    public class FetchMoviesData extends AsyncTask<String, Void, String[]> {

        //FIELDS
        private final String LOG_TAG = FetchMoviesData.class.getSimpleName();   //shows class name in Logcat
        private String movies_base_url = "https://api.themoviedb.org/3/movie/";

        @Override
        protected String[] doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            movies_base_url += params[0] + "?";

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String MoviesJsonString = null;

            try {
                // Construct the URL for the MoviesData query
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(movies_base_url).buildUpon()
                        .appendQueryParameter(APPID_PARAM, "fcc4f01ec81bfde6e2c48f82d9404eeb")
                        .build();

                //convert Uri to Url
                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to themoviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                MoviesJsonString = buffer.toString();

                Log.v(LOG_TAG, "Forecast JSON String: " + MoviesJsonString);
            } catch (Exception e) {
                Log.e("ForecastFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ForecastFragment", "Error closing stream", e);
                    }
                }
            }

            //parsing the data acquired by the http request into Json format and handle them appropriately
            try {
                //auto tha einai to apotelesma tou thread!!!!
                //auto tha paei stin onPostExecute
                //diladi to String[] me ta dedomena
                return getMoviesDataFromJson(MoviesJsonString);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {      //to argument tha prepei na simfwnei me to trito argument tou asycTask.
            //ektelesi sto main thread
            if (result != null) {
                ArrayList<String> results = new ArrayList<>();

                for (String str : result) {
                    results.add(str);
                }
                mImageAdapter.clear();
                mImageAdapter = new ImageAdapter(getActivity(), results);

                grd_view.setAdapter(mImageAdapter);
            }
        }

    }

    private String[] getMoviesDataFromJson(String JsonString) throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "results";
        final String OWM_POSTER_PATH = "poster_path";

        JSONObject forecastJson = new JSONObject(JsonString);      //metatropi tou string se Json Object
        JSONArray moviesArray = forecastJson.getJSONArray(OWM_LIST);   //fetch the list object from JSON (7 objects)


        //auto tha epistrafei
        String[] resultStrs = new String[moviesArray.length()];


        for (int i = 0; i < moviesArray.length(); i++) {
            // Get the nth list item in weatherArray array
            JSONObject movieObject = moviesArray.getJSONObject(i);                                 //list[0],list[1],...list[7]

            // description is in a child array called "weather", which is 1 element long.
            String poster_path = movieObject.getString(OWM_POSTER_PATH);      //movie->poster_path

            resultStrs[i] = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + poster_path.toString();
        }

        for (String s : resultStrs) {
            Log.v("Poster Paths", s);
        }

        return resultStrs;
    }

    @Override
    public void onStart() {
        super.onStart();

        //opote ekkinei to fragment ginetai updateweather.
        updateMovies("popular");
    }


}
