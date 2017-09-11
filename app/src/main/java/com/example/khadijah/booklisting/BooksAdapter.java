package com.example.khadijah.booklisting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by khadijah on 7/30/2017.
 */
public class BooksAdapter extends ArrayAdapter<Books> {

    public BooksAdapter(Activity context, ArrayList<Books> books) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for three TextViews , the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_item, parent, false);
        }

        // Get the {@link Books} object located at this position in the list
        Books currentBook = getItem(position);

        // Find the TextView in the book_item.xml layout with the ID book_title
        TextView bookTitleTextView = (TextView) listItemView.findViewById(R.id.book_title);
        // Display the location of the current earthquake in that TextView
        bookTitleTextView.setText(currentBook.getmBookName());

        // Find the TextView in the book_item.xml layout with the ID book_author
        TextView bookAuthorTextView = (TextView) listItemView.findViewById(R.id.book_author);
        // Display the author name of the current earthquake in that TextView
        bookAuthorTextView.setText(currentBook.getmBookAuther());

        // Find the TextView in the book_item.xml layout with the ID LinkText
       // TextView bookLinkTextView = (TextView) listItemView.findViewById(R.id.LinkText);
        // Get the Link from the current Books object and
        // set this text on the name TextView
      //  bookLinkTextView.setText(currentBook.getmBookUrl());

        // Find the TextView in the book_item.xml layout with the ID priceText
        TextView bookPriceTextView = (TextView) listItemView.findViewById(R.id.DateText);
        // Get the price integer from the current Books object and
        // set this text on the name TextView
        bookPriceTextView.setText(currentBook.getmBookDate());


        return listItemView;
    }

}

