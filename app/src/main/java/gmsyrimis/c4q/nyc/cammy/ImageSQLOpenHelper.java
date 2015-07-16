package gmsyrimis.c4q.nyc.cammy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by July on 7/12/15.
 */
public class ImageSQLOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "myDB";

    private static final String CREATE_SQL = "CREATE TABLE " + TableRows.TABLE_NAME + " (" +
            TableRows._ID + "INTEGER PRIMARY KEY," +
            TableRows.COLUMN_NAME + " Text," +
            TableRows.COLUMN_IMAGE + " BLOB" + ")";

    private static final String DELETE_SQL = "DROP TABLE IF EXISTS " + TableRows.TABLE_NAME;

    private static ImageSQLOpenHelper INSTANCE;

    public static synchronized ImageSQLOpenHelper getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new ImageSQLOpenHelper (context.getApplicationContext());
        }

        return INSTANCE;
    }

    private ImageSQLOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_SQL);
        onCreate(db);
    }

    public static abstract class TableRows implements BaseColumns {
        public static final String TABLE_NAME = "images";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
    }

    public void insertImage(ImageTemplate imageTemplate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableRows.COLUMN_NAME, imageTemplate.getName());
        cv.put(TableRows.COLUMN_IMAGE, imageTemplate.getImage());

        db.insert(TableRows.TABLE_NAME, null, cv);
        db.close();
    }

    public List<ImageTemplate> getAllImages() {
        List<ImageTemplate> images = new ArrayList<>();

        String[] projection = {
                TableRows.COLUMN_NAME,
                TableRows.COLUMN_IMAGE,
        };

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TableRows.TABLE_NAME, projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ImageTemplate imageTemplate = new ImageTemplate();
            imageTemplate.setName(cursor.getString(0));
            imageTemplate.setImage(cursor.getBlob(1));
            images.add(imageTemplate);
        }
        cursor.close();
        return images;
    }
}
