package anish.tutorial.mobilewallet;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.ArrayList;

public class DBClass extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase dbs = getWritableDatabase();
    int cb, pb;

    List<ColumnNames> colList;

    public static final String DATABASE_NAME = "MobileWallet";
    public static final String TABLE_NAME = "User_table";
    public static final String ID = "ID";
    public static final String CURRENT_BALANCE = "CB";
    public static final String PREVIOUS_BALANCE = "PB";
    public static final String AD_US_BLNCE = "AUB";
    public static final String STATUS = "STATUS";
    public static final String EDATE = "EDATE";
    public static final String REMARKS = "REMARKS";


    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                " CREATE TABLE " + TABLE_NAME +
                        " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                         CURRENT_BALANCE + " INT , " +
                        PREVIOUS_BALANCE + " INT , " +
                        AD_US_BLNCE + " INT , " +
                        STATUS + " INT , " +
                        EDATE + " VARCHAR(1000) , " +
                        REMARKS + " VARCHAR(1000)    )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    public void deleteTable(){
        dbs.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(dbs);
    }

    public void addBtn(int add, int status, String datetime, String remarks) {

        String query = " SELECT " + CURRENT_BALANCE + " FROM " + TABLE_NAME + " WHERE " + ID + " =  ( SELECT MAX(ID) FROM " + TABLE_NAME + ") ";

        @SuppressLint("Recycle")
        Cursor cus = this.dbs.rawQuery(query, null);
        if (cus != null && cus.getCount() > 0) {
            if (cus.moveToNext()) {
                do {
                    cb = cus.getInt(cus.getColumnIndex(CURRENT_BALANCE));
                } while (cus.moveToNext());
            }

            ContentValues cv = new ContentValues();
            cv.put(AD_US_BLNCE, add);
            cv.put(STATUS, status);
            cv.put(CURRENT_BALANCE, cb + add);
            cv.put(PREVIOUS_BALANCE, cb);
            cv.put(EDATE, datetime);
            cv.put(REMARKS, remarks);
            dbs.insert(TABLE_NAME, null, cv);

        } else {
        ContentValues cv = new ContentValues();
        cv.put(AD_US_BLNCE, add);
        cv.put(STATUS, status);
        cv.put(CURRENT_BALANCE, add);
        cv.put(PREVIOUS_BALANCE, 0);
        cv.put(EDATE, datetime);
        cv.put(REMARKS, remarks);
        dbs.insert(TABLE_NAME, null, cv);

          }


    }

    public void useBtn(int use, int status, String datetime, String remarks) {
        String query = " SELECT " + CURRENT_BALANCE + " FROM " + TABLE_NAME + " WHERE " + ID + " =  ( SELECT MAX(ID) FROM " + TABLE_NAME + ") ";

        @SuppressLint("Recycle")
        Cursor cus = this.dbs.rawQuery(query, null);
        if (cus != null && cus.getCount() > 0) {
            if (cus.moveToNext()) {
                do {
                    cb = cus.getInt(cus.getColumnIndex(CURRENT_BALANCE));
                } while (cus.moveToNext());
            }

            ContentValues cv = new ContentValues();
            cv.put(AD_US_BLNCE, use);
            cv.put(STATUS, status);
            cv.put(CURRENT_BALANCE, cb - use);
            cv.put(PREVIOUS_BALANCE, cb);
            cv.put(EDATE, datetime);
            cv.put(REMARKS, remarks);
            dbs.insert(TABLE_NAME, null, cv);
        } else {
            ContentValues cv = new ContentValues();
            cv.put(AD_US_BLNCE, use);
            cv.put(STATUS, status);
            cv.put(CURRENT_BALANCE, 0);
            cv.put(PREVIOUS_BALANCE, 0);
            cv.put(EDATE, datetime);
            cv.put(REMARKS, remarks);
            dbs.insert(TABLE_NAME, null, cv);

        }


    }

    public int currentBalance() {
        String query = " SELECT " + CURRENT_BALANCE + " FROM " + TABLE_NAME + " WHERE " + ID + " =  ( SELECT MAX(ID) FROM " + TABLE_NAME + ") ";

        @SuppressLint("Recycle")
        Cursor cus = this.dbs.rawQuery(query, null);
        if (cus != null && cus.getCount() > 0) {
            if (cus.moveToNext()) {
                do {
                    cb = cus.getInt(cus.getColumnIndex(CURRENT_BALANCE));
                } while (cus.moveToNext());
            }
            return cb;
        } else
            return cb = 0;

    }

    public int previousBalance() {
        String query = " SELECT " + PREVIOUS_BALANCE + " FROM " + TABLE_NAME + " WHERE " + ID + " =  ( SELECT MAX(ID) FROM " + TABLE_NAME + ") ";

        @SuppressLint("Recycle")
        Cursor cus = this.dbs.rawQuery(query, null);
        if (cus != null && cus.getCount() > 0) {
            if (cus.moveToNext()) {
                do {
                    pb = cus.getInt(cus.getColumnIndex(PREVIOUS_BALANCE));
                } while (cus.moveToNext());
            }
            return pb;
        } else
            return pb = 0;

    }

    public List<ColumnNames> getAllDatas( int a){

        Cursor cus = dbs.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + STATUS + " = " + a + " ORDER BY ID DESC  ", null);
        colList = new ArrayList<>();
        if (cus.moveToNext()){
            do{
                ColumnNames co = new ColumnNames();
                co.setId(cus.getString(cus.getColumnIndex(ID)));
                co.setAdd(cus.getInt(cus.getColumnIndex(AD_US_BLNCE)));
                co.setStatus(cus.getInt(cus.getColumnIndex(STATUS)));
                co.setCb(cus.getInt(cus.getColumnIndex(CURRENT_BALANCE)));
                co.setPb(cus.getInt(cus.getColumnIndex(PREVIOUS_BALANCE)));
                co.setDate(cus.getString(cus.getColumnIndex(EDATE)));
                co.setRemarks(cus.getString(cus.getColumnIndex(REMARKS)));
                colList.add(co);
            }while (cus.moveToNext());
        }
        return colList;

    }

    public List<ColumnNames> getAllDatas(){

        Cursor cus = dbs.rawQuery(" SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC  ", null);
        colList = new ArrayList<>();
        if (cus.moveToNext()){
            do{
                ColumnNames co = new ColumnNames();
                co.setAdd(cus.getInt(cus.getColumnIndex(AD_US_BLNCE)));
                co.setStatus(cus.getInt(cus.getColumnIndex(STATUS)));
                co.setCb(cus.getInt(cus.getColumnIndex(CURRENT_BALANCE)));
                co.setPb(cus.getInt(cus.getColumnIndex(PREVIOUS_BALANCE)));
                co.setDate(cus.getString(cus.getColumnIndex(EDATE)));
                co.setRemarks(cus.getString(cus.getColumnIndex(REMARKS)));
                colList.add(co);
            }while (cus.moveToNext());
        }
        return colList;

    }

    public void delete(){
       String de = " DELETE FROM " + TABLE_NAME;
       dbs.execSQL(de);
    }
}
