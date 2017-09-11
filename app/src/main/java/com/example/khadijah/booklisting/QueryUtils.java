package com.example.khadijah.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khadijah on 7/31/2017.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Books} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Books> extractEarthquakes(String requestUrl) {

//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(requestUrl)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Books> booksList = new ArrayList<>();

        //1--- Create URL object
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e("Error", "Error with creating URL ", e);
        }

        //2--- Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Error", "Error closing input stream", e);
        }


        // 3----Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        String value = "";
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray booksArray = root.getJSONArray("items");

                      // looping through All Books "items"
            for (int i = 0; i <booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);// for each Book
                JSONObject volume = currentBook.getJSONObject("volumeInfo");

                String bookTitle = volume.getString("title");
                String bookAuthor = volume.getString("authors");
                String bookLink = volume.getString("previewLink");
                String bookDate = volume.getString("publishedDate");

               // JSONObject salesInformation = currentBook.getJSONObject("saleInfo");
               // JSONObject price = salesInformation.getJSONObject("listPrice");

              //  int bookPrice = price.getInt("amount");

                //call class instructor to fill Books object
                Books books = new Books(bookTitle, bookAuthor, bookLink, bookDate);

                //add the current earth quack opject to the array list
                booksList.add(books);
            }
        }
             catch (Exception e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of books
        return booksList;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("ERROR", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("ERROR", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
