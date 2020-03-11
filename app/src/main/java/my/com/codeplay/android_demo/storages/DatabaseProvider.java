/*
 * Copyright 2017 (C) CodePlay Studio. All rights reserved.
 *
 * All source code within this app is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package my.com.codeplay.android_demo.storages;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import androidx.annotation.Nullable;

import java.io.File;

import my.com.codeplay.android_demo.data.Dummy;

public class DatabaseProvider extends ContentProvider {
    public static final String TABLENAME = "tablename";
    public static final String COL_NAME = "t_name";
    public static final String COL_IMAGE = "t_image";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLENAME + " (" +
            BaseColumns._ID + " integer primary key autoincrement, " +
            COL_NAME + " text not null, " +
            COL_IMAGE + " integer not null);";
    private MySQLiteOpenHelper dbHelper;

    private class MySQLiteOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "database.db";
        private static final int DATABASE_VERSION = 2;

        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /* called when database is created for the first time */
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                // multiple statements separated by semicolons are not supported in execSQL() method
                db.execSQL(SQL_CREATE_TABLE);

                // batch insert dummy data to the table
                db.beginTransaction();
                for (int i = 0; i< Dummy.NAMES.length; i++) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(COL_NAME, Dummy.NAMES[i]);
                    contentValues.put(COL_IMAGE, Dummy.DRAWABLES[i]);

                    // The second argument of insert() provides the name of a column in which
                    // the framework can insert NULL in the event that the ContentValues is empty
                    // (if you instead set this to "null", then the framework will not insert a row
                    // when there are no values).
                    db.insert(TABLENAME, null, contentValues);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // if the call of endTransaction() without setTransactionSuccessful(),
                // data will be rollback automatically.
                db.endTransaction();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);

            onCreate(db);
        }
    }

    public DatabaseProvider() {};

    public DatabaseProvider(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }

    public Cursor query() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(
                TABLENAME,  // The table to query
                null,       // The columns to return (null to return all columns)
                null,       // The columns for the WHERE clause
                null,       // The values for the WHERE clause
                null,       // the row groups
                null,       // filter by row groups
                null        // The sort order
        );
    }

    public void close() {
        if (dbHelper!=null)
            dbHelper.close();
    }

    /**
     * Implementation of Content Provider
     */
    /**
     * Content URIs
     *
     * A content URI is a URI that identifies data in a provider. It includes the scheme that
     * identifies it is a content URI, the authority (symbolic name of the entire provider), and
     * a path (a name that points to the table.
     */
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "my.com.codeplay.android_demo.data";
    // For the table
    public static final Uri CONTENT_URI =
            Uri.parse(SCHEME + AUTHORITY + File.separator + TABLENAME);
    /**
     * the code that is returned when a URI is matched
     * against the given components. Must be positive.
     */
    public static final int RECORDS = 1;
    public static final int RECORDS_ID = 2;
    /**
     * Content providers can return standard MIME media types, or custom MIME type strings, or both.
     * For example: text/html means a query using that URI will return text containing HTML tags.
     *
     * Custom MIME type strings prefixed with vnd, also called "vendor-specific" MIME types which
     * are introduced by corporate bodies rather than e.g. an Internet consortium.
     * 
     * The type value is always fix. The subtype is provider-specific which usually based on the 
     * provider's authority and table names.
     */
    public static final String MIME_CONTENT_TYPE = "vnd.android.cursor.dir/" // For multiple rows
            + "vnd.codeplay.training.datebase_demo." + TABLENAME;
    public static final String MIME_CONTENT_ITEM = "vnd.android.cursor.item/" // for single row
            + "vnd.codeplay.training.datebase_demo." + TABLENAME;

    private static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLENAME, RECORDS);
        uriMatcher.addURI(AUTHORITY, TABLENAME + "/#", RECORDS_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MySQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case RECORDS:
                return MIME_CONTENT_TYPE;
            case RECORDS_ID:
                return MIME_CONTENT_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case RECORDS:
                return db.query(
                        TABLENAME,  // The table to query
                        projection, // The columns to return (null to return all columns)
                        selection,  // The columns for the WHERE clause
                        selectionArgs, // The values for the WHERE clause
                        null,       // the row groups
                        null,       // filter by row groups
                        sortOrder   // The sort order
                );

            case RECORDS_ID:
                String id = uri.getLastPathSegment();
                return db.query(
                        TABLENAME,  // The table to query
                        projection, // The columns to return (null to return all columns)
                        BaseColumns._ID + "=?",  // The columns for the WHERE clause
                        new String[] { id }, // The values for the WHERE clause
                        null,       // the row groups
                        null,       // filter by row groups
                        sortOrder   // The sort order
                );
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
