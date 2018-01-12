package com.dvb.movie_db.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dvb.movie_db.Data.MovieContract;
import com.dvb.movie_db.Data.MovieCursorAdapter;
import com.dvb.movie_db.R;

import static android.R.attr.data;

/**
 * Created by dmitrybondarenko on 11.01.18.
 */

public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int MOVIE_LOADER = 0;
    MovieCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_catalog_activity);
    }

    private void insertMovie(){

        ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_FILM_NAME, "WOW Movie");
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "find_this_poster");
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "That's a nice WOW movie");
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "12.07.1986");
            values.put(MovieContract.MovieEntry.COLUMN_RATING, 9);
        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    private void deleteAllMovies(){
        int rowsDeleted = getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity ", rowsDeleted + " rows deleted from body database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.COLUMN_FILM_NAME,
                MovieContract.MovieEntry.COLUMN_POSTER_PATH,
                MovieContract.MovieEntry.COLUMN_OVERVIEW,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_RATING
        };
        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertMovie();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





























