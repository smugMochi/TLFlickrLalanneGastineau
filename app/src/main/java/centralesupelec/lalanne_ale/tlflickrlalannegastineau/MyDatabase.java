package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static java.sql.Types.BLOB;

/**
 * Created by Alexandre on 13/04/2018.
 */

public class MyDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE_NAME = "FavoritesDatabase";
    private static final String PKEY = "url";
    private static final String COL1 = "titre";

    MyDatabase(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" + PKEY + " TEXT PRIMARY KEY," + COL1 + " TEXT);";
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(MyImage image) {
        Log.i("SQLite", "Insertion dans la BDD");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(PKEY, image.getUrl());
        Log.i("SQLite", image.getUrl());
        values.put(COL1, image.getTitle());
        db.insertOrThrow(DATABASE_TABLE_NAME,null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void readData(ArrayList<MyImage> array){
        array.clear();
        Log.i("SQLite", "Lecture de la BDD");
        String select = new String("SELECT * from " + DATABASE_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        Log.i("SQLite", "Nombre de lignes : " + cursor.getCount());
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String url = cursor.getString(cursor.getColumnIndex(PKEY));
                String titre = cursor.getString(cursor.getColumnIndex(COL1));
                array.add(new MyImage(titre, url));
            } while (cursor.moveToNext());
        }
    }

    public void removeData(String url) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + DATABASE_TABLE_NAME + " WHERE " + PKEY + "='" + url +"';");
        db.close();
    }

    public boolean hasEntry(String url){
        String[] columns = { PKEY };
        String selection = PKEY + " =?";
        String[] selectionArgs = { url };
        String limit = "1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}


