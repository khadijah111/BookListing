package com.example.khadijah.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static  String GOOGLE_BOOKS_REQUEST_URL ="";

    /** Adapter for the list of earthquakes */
    private BooksAdapter mAdapter;
    /** TextView that is displayed when the list is empty */
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get data "keyword" from Main activity
        Intent i = getIntent();
        String keywordSearch = i.getStringExtra("keyword");

        //create GOOGLE URL REQUEST with the typed keyword from the user
        GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + keywordSearch.trim() + "&maxResults=20";

        //TEST THE INTERNET CONNECTION
        // If there is a network connection, fetch data
        if(isOnline())
        {
            // Perform the HTTP request for earthquake data and process the response. ALL in the background
            // Kick off an {@link AsyncTask} to perform the network request
            TsunamiAsyncTask task = new TsunamiAsyncTask();
            task.execute(GOOGLE_BOOKS_REQUEST_URL);
            Toast.makeText(this, "search about books by keyword: " + keywordSearch, Toast.LENGTH_LONG).show();       }
        else
        {
            // STOP THE loading progress view because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_progress);
            loadingIndicator.setVisibility(View.GONE);

            //set the empty view message
            emptyTextView.setText("NO INTERNET CONNECTION");
        }

        // Find a reference to the {@link ListView} in the layout
        ListView booksListView = (ListView) findViewById(R.id.list);

        emptyTextView = (TextView)findViewById(R.id.emptyTextView);
        booksListView.setEmptyView(emptyTextView);


        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(mAdapter);



        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link AndroidFlavor} object located at this position in the Arraylist
                Books currentBook = mAdapter.getItem(position); //or earthquakes.get(position);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentBook.getmBookUrl()));
                startActivity(browserIntent);
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private class TsunamiAsyncTask extends AsyncTask<String , Void, List<Books>>
    {
        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread, so we just return an
         * {@link ArrayList<Books>} object as the result.
         */
        @Override
        protected List<Books> doInBackground(String... urls) {

            // Create a fake list of earthquake locations.
            List<Books> booksList = QueryUtils.extractEarthquakes(urls[0]);
            return booksList;
        }

        @Override
        protected void onPostExecute(List<Books> data) {
            // Set empty state text to display "No Books found."
            emptyTextView.setText("NO BOOKS FOUND !!!");

            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }

            // STOP THE loading progress view because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_progress);
            loadingIndicator.setVisibility(View.GONE);
        }
    }

}
