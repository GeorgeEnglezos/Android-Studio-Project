package com.example.sqlitedbcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Selection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UsersDbprovider extends ContentProvider {
    private UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    private final String AUTHORITY="users_db";
    @Override
    public boolean onCreate() {
        uriMatcher.addURI(AUTHORITY,"users",1);
        uriMatcher.addURI(AUTHORITY,"users/#",2);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        UserDB dbHelper=new UserDB(getContext());
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
        Cursor cursor=database.query(UserDB.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
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
        SQLiteDatabase database=new UserDB(getContext()).getWritableDatabase();
        long id=database.insert(UserDB.TABLE_NAME,null,values);
        return Uri.parse("content://"+AUTHORITY+"users/"+id);
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
