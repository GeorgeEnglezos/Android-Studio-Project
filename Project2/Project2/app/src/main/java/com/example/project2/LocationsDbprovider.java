package com.example.project2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationsDbprovider  extends ContentProvider {
    private UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    private final String AUTHORITY="locations_db";
    @Override
    public boolean onCreate() {
        uriMatcher.addURI(AUTHORITY,"locations",1);
        uriMatcher.addURI(AUTHORITY,"locations/#",2);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        LocationDB dbHelper=new LocationDB(getContext());
        SQLiteDatabase database=dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)){
            case 1:
                break;
            case 2:
                String id=uri.getLastPathSegment();
                selection="_ID=?";
                selectionArgs[0]=id;
                break;
            default:
                return null;

        }
        Cursor cursor=database.query(LocationDB.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database=new LocationDB(getContext()).getWritableDatabase();
        long id=database.insert(LocationDB.TABLE_NAME,null,values);
        return Uri.parse("content://"+AUTHORITY+"locations/"+id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}