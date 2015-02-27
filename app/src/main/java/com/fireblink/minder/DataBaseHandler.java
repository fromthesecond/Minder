package com.fireblink.minder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper implements IDataBaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mind";
    private static final String TABLE_MIND = "mind";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "title";
    private static final String KEY_BODY = "body";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void addMind(Mind mind) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, mind.get_name());
        values.put(KEY_BODY, mind.get_body());
        db.insert(TABLE_MIND, null, values);
        db.close();
    }

    @Override
    public Mind getMindById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MIND, new String[] { KEY_ID,
                        KEY_NAME, KEY_BODY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        Mind mind = new Mind(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return mind;
    }

    @Override
    public List<Mind> getAllMinds() {
        List<Mind> mindList = new ArrayList<Mind>();
        String selectQuery = "SELECT  * FROM " + TABLE_MIND;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Mind mind = new Mind();
                mind.set_id(Integer.parseInt(cursor.getString(0)));
                mind.set_name(cursor.getString(1));
                mind.set_body(cursor.getString(2));
                mindList.add(mind);
            } while (cursor.moveToNext());
        }

        return mindList;
    }

    @Override
    public int getMindCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MIND;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateMind(Mind mind) {
        return 0;
    }

    @Override
    public void deleteMind(Mind mind) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MIND, KEY_ID + " = ?", new String[] { String.valueOf(mind.get_id()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MIND, null, null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MIND_TABLE = "CREATE TABLE " + TABLE_MIND + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_BODY + " TEXT" + ")";
        db.execSQL(CREATE_MIND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MIND);
        onCreate(db);
    }
}
