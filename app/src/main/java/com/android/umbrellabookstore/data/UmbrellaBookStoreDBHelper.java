package com.android.umbrellabookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.android.umbrellabookstore.data.UmbrellaBookStoreContract.UmbrellaBookStoreEntry;

/**
 * Created by sangeetha_gsk on 8/28/18.
 */

public class UmbrellaBookStoreDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = UmbrellaBookStoreDBHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "umbrellabookstore.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link UmbrellaBookStoreDBHelper}.
     *
     * @param context of the app
     */
    public UmbrellaBookStoreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG, "Creating the table - " + UmbrellaBookStoreEntry.TABLE_NAME);
        // Create a String that contains the SQL statement to create the Bookstore table
        String SQL_CREATE_BOOK_DETAILS_TABLE = "CREATE TABLE " + UmbrellaBookStoreEntry.TABLE_NAME + " ("
                + UmbrellaBookStoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UmbrellaBookStoreEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + UmbrellaBookStoreEntry.COLUMN_BOOK_PRICE + " TEXT, "
                + UmbrellaBookStoreEntry.COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL, "
                + UmbrellaBookStoreEntry.COLUMN_BOOK_SUPPLIER_NAME + " TEXT, "
                + UmbrellaBookStoreEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOK_DETAILS_TABLE);
        Log.i(LOG_TAG, "Table created successfully" );

    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
