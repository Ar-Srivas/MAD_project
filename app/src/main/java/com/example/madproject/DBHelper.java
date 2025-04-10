package com.example.madproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name and Columns
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY = "city_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table query
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CITY + " TEXT)";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Add city to favorites
    public void addFavorite(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, cityName);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    // Get all favorite cities
    public ArrayList<String> getFavorites() {
        ArrayList<String> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{COLUMN_CITY}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                favorites.add(city);
            }
            cursor.close();
        }
        db.close();
        return favorites;
    }

    // Remove city from favorites
    public void removeFavorite(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_CITY + " = ?", new String[]{cityName});
        db.close();
    }

    // Check if city is already in favorites
    public boolean isFavorite(String cityName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{COLUMN_CITY}, COLUMN_CITY + " = ?", new String[]{cityName}, null, null, null);

        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }
}
