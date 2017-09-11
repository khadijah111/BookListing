package com.example.khadijah.booklisting;

/**
 * Created by khadijah on 8/31/2017.
 */
public class Books {
    // @param Book name
    private String mBookName;

    // @param  auther of the book
    private String mBookAuther;

    /* URL of the Book*/
    private String mBookUrl;

    /* Price of the Book*/
    private String mBookDate;

    public Books( String bookName, String bookAuther, String bookUrl, String bookPrice)
    {
        mBookName = bookName;
        mBookAuther = bookAuther;
        mBookUrl = bookUrl;
        mBookDate = bookPrice;
    }


    public String getmBookName() {
        return mBookName;
    }

    public String getmBookAuther() {
        return mBookAuther;
    }

    public String getmBookUrl() {
        return mBookUrl;
    }

    public String getmBookDate() {
        return mBookDate;
    }
}
