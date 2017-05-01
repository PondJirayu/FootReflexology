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
    private static final String tableName = "Members";
    private static final String columnFirstName = "firstname";
    private static final String columnLastName = "lastname";
    private static final String columnIdentificationNumber = "identification_number";
    private static final String columnGender = "gender";
    private static final String columnBirthDate = "birthdate";
    private static final String columnTelephoneNumber = "telephone_number";
    private static final String columnHouseVillage = "house_village";
    private static final String columnSubDistrict = "sub_district";
    private static final String columnDistrict = "district";
    private static final String columnProvince = "province";
    private static final String columnCreatedAt = "created_at";
    private static final String columnUpdatedAt = "updated_at";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
