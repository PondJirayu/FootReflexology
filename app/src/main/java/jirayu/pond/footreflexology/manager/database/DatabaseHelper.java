package jirayu.pond.footreflexology.manager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lp700 on 1/5/2560.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String databaseName = "footreflexology";
    private static final int databaseVersion = 1;
    public static final String tableName = "Members";
    public static final String columnIdentificationNumber = "identification_number";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    /*
     * onCreate จะถูกเรียกใช้งานตอนที่ new object ครั้งแรกและครั้งเดียว
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + columnIdentificationNumber + " TEXT UNIQUE NOT NULL);");
        // TODO : delete insert ทั้งหมดด้วย
        db.execSQL("INSERT INTO " + tableName + " (" + columnIdentificationNumber + ") VALUES ('1769900332760');");
        db.execSQL("INSERT INTO " + tableName + " (" + columnIdentificationNumber + ") VALUES ('1769900332761');");
        db.execSQL("INSERT INTO " + tableName + " (" + columnIdentificationNumber + ") VALUES ('1769900332762');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

}
