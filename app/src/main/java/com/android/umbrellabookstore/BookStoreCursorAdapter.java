package com.android.umbrellabookstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.umbrellabookstore.data.UmbrellaBookStoreContract.UmbrellaBookStoreEntry;

/**
 * Created by sangeetha_gsk on 9/10/18.
 */

public class BookStoreCursorAdapter extends CursorAdapter {
    private static String TAG = "BookStoreCursorAdapter";

    /**
     * Constructs a new {@link BookStoreCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookStoreCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_items_books, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Find fields to populate in inflated template
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow(
                UmbrellaBookStoreEntry.COLUMN_PRODUCT_NAME));
        String bookPrice = cursor.getString(cursor.getColumnIndexOrThrow(
                UmbrellaBookStoreEntry.COLUMN_BOOK_PRICE));
        // Populate fields with extracted properties
        name.setText(body);
        priceTextView.setText(context.getString(R.string.price) + String.valueOf(bookPrice) + context.getString(R.string.dollar));
        int bookQuantity = 0;

        final TextView bookQuantityView = (TextView) view.findViewById(R.id.bookQuantity);
        final Button saleBookButton = (Button) view.findViewById(R.id.saleBookButton);
        bookQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(
                UmbrellaBookStoreEntry.COLUMN_BOOK_QUANTITY));
        final BookDetails bookDetails = new BookDetails();
        bookDetails.setBookQuantity(bookQuantity);
        if (bookQuantity == 0) {
            //disable the sales button, when quantity is 0.
            saleBookButton.setEnabled(false);
        }else {
            //enable the sales button, when quantity > 0.
            saleBookButton.setEnabled(true);

        }
        saleBookButton.setOnClickListener(new View.OnClickListener() {
            int currentBookId = cursor.getInt(cursor.getColumnIndex(UmbrellaBookStoreEntry._ID));
            Uri contentUri = Uri.withAppendedPath(UmbrellaBookStoreEntry.CONTENT_URI, Integer.toString(currentBookId));
            public void onClick(View v) {
                // Create a ContentValues object where column names are the keys,
                // and books attributes are the values.
                ContentValues values = new ContentValues();
                // decrease the book quantity by 1.
                int bookQuantityValue = bookDetails.getBookQuantity();
                bookQuantityValue = bookQuantityValue - 1;
                    if (bookQuantityValue == 0) {
                        //disable the sales button, when quantity is 0.
                        saleBookButton.setEnabled(false);
                    }
                values.put(UmbrellaBookStoreEntry.COLUMN_BOOK_QUANTITY, bookQuantityValue);
                bookDetails.setBookQuantity(bookQuantityValue);

                // Insert a new row for the book into the provider using the ContentResolver.
                // Use the {@link BookEntry#CONTENT_URI} to indicate that we want to insert
                // into the books database table.
                // Receive the new content URI that will allow us to access the book's data in the future.
                try {
                    int rowUpdatedId = context.getContentResolver().update(contentUri, values, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bookQuantityView.setText(String.valueOf(bookDetails.getBookQuantity()));

    }

    static class BookDetails{

        int bookQuantity;

        public int getBookQuantity() {
            return bookQuantity;
        }

        public void setBookQuantity(int bookQuantity) {
            this.bookQuantity = bookQuantity;
        }

    }
}