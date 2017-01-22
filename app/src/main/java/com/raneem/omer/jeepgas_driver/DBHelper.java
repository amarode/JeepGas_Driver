package com.raneem.omer.jeepgas_driver;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final  DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
    private static final String DATABASE_NAME = "Driverdb";
    private static final int DATABASE_VERSION = 22;

    // the driver unquie ID
    private static String DriverID = mDataBaseRef.child("Driver").push().getKey();

    private static final String TABLE_DRIVER = "Driver";
    private static final String DRIVERNAME = "companyname";
    private static final String DRIVERPHONE = "phone";
    private static final String WORKINGAREA = "workingarea";
    private static final String WORKINGHOURS = "workinghours";
    private static final String WORKINGHOURSFROM = "workinghoursFrom";
    private static final String WORKINGHOURSTILL = "workinghoursTill";
    private static final String SERVICETYPE = "servicetype";
    private static final String DELIVER = "deliver";
    private static final String REPAIR = "repair";
    private static final String GASPRICE = "gasprice";
    private static final String GASBIG = "gasbig";
    private static final String GASSMALL= "gassmall";

    private static final String TABLE_ORDER = "_Order";
    private static final String CLIENT_NAME = "Name";
    private static final String CLIENT_PHONE = "Phone";
    private static final String CLIENT_AREA = "Area";
    private static final String CLIENT_SERVICE = "Service";
    private static final String CLIENT_STATUS = "Status";


    private String CREATE_DRIVER_TABLE = "create table if not exists " + TABLE_DRIVER +
            " (_id integer primary key AUTOINCREMENT, "
            + DRIVERNAME + " text, "
            + DRIVERPHONE + " integer, "
            + WORKINGAREA + " text, "
            + WORKINGHOURSFROM + " text, "
            + WORKINGHOURSTILL + " text, "
            + GASBIG + " integer, "
            + GASSMALL + " integer, "
            + DELIVER + " integer, "
            + REPAIR + " integer)";

    private String CREATE_ORDER_TABLE1 = "create table if not exists " + TABLE_ORDER
            + " (_id integer primary key AUTOINCREMENT, "
            + CLIENT_NAME + " text, "
            + CLIENT_PHONE + " text, "
            + CLIENT_AREA + " text, "
            + CLIENT_SERVICE + " text, "
            + CLIENT_STATUS + " text)";

    private String CREATE_ORDER_TABLE = "create table if not exists " + TABLE_ORDER +
            " (_id integer primary key AUTOINCREMENT, "
            + CLIENT_NAME + " text, "
            + CLIENT_PHONE + " text, "
            + CLIENT_AREA + " text, "
            + CLIENT_SERVICE + " text, "
            + CLIENT_STATUS + " text)";





/*    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE, null, 1);
    }*/

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DRIVER_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DRIVER + "';");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_ORDER + "';");
        db.execSQL(CREATE_DRIVER_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
    }

    public boolean insertDriver_bk(JeebGasDrivers jeebgasdriver) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {

            String deleteQuery = "delete from " + TABLE_DRIVER + ";";
            getWritableDatabase().execSQL(deleteQuery);

            contentValues.put(DRIVERNAME, jeebgasdriver.getDrivername());
            contentValues.put(DRIVERPHONE, jeebgasdriver.getDriverphone());
            contentValues.put(WORKINGAREA, jeebgasdriver.getWorkingarea());
            contentValues.put(WORKINGHOURS, jeebgasdriver.getWorkinghours());
            contentValues.put(GASPRICE, jeebgasdriver.getGasprice());
            contentValues.put(SERVICETYPE, jeebgasdriver.getServicetype());
            db.insert(TABLE_DRIVER, null, contentValues);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean insertDriver(String name, String phone, String area, String hours_from, String hours_till, String price_big, String price_small, String service) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {

            String deleteQuery = "delete from " + TABLE_DRIVER + ";";
            getWritableDatabase().execSQL(deleteQuery);

            /*
             private static final String DRIVERNAME = "companyname";
    private static final String DRIVERPHONE = "phone";
    private static final String WORKINGAREA = "workingarea";
    private static final String WORKINGHOURS = "workinghours";
    private static final String WORKINGHOURSFROM = "workinghoursFrom";
    private static final String WORKINGHOURSTILL = "workinghoursTill";
    private static final String SERVICETYPE = "servicetype";
    private static final String DELIVER = "deliver";
    private static final String REPAIR = "repair";
    private static final String GASPRICE = "gasprice";
    private static final String GASBIG = "gasbig";
    private static final String GASSMALL= "gassmall";
             */

            //save in sqlite
            contentValues.put(DRIVERNAME, name);
            contentValues.put(DRIVERPHONE, phone);
            contentValues.put(WORKINGAREA, area);
            contentValues.put(WORKINGHOURSFROM, hours_from);
            contentValues.put(WORKINGHOURSTILL, hours_till);
            if(service.equals(0)) {
                contentValues.put(DELIVER, 1);
                contentValues.put(REPAIR, 0);
            }
            if(service.equals(1)) {
                contentValues.put(REPAIR, 1);
                contentValues.put(DELIVER, 0);
            }
            if(service.equals(2)) {
                contentValues.put(REPAIR, 1);
                contentValues.put(DELIVER, 1);
            }
            contentValues.put(GASBIG, price_big);
            contentValues.put(GASSMALL, price_small);
            db.insert(TABLE_DRIVER, null, contentValues);



            //Save in firebase
            Map<String, String> FBmap = new HashMap<String, String>();
            FBmap.put("DRIVERNAME",name);
            FBmap.put("DRIVERPHONE",phone);
            FBmap.put("WORKINGAREA",area);
            FBmap.put("WORKINGHOURSFROM",hours_from);
            FBmap.put("WORKINGHOURSTILL",hours_till);

            if(service.equals(0)) {
                FBmap.put("DELIVER","1");
                FBmap.put("REPAIR","0");
            }
            if(service.equals(1)) {
                FBmap.put("DELIVER","0");
                FBmap.put("REPAIR","1");
            }
            if(service.equals(2)) {
                FBmap.put("DELIVER","1");
                FBmap.put("REPAIR","1");
            }

            FBmap.put("GASSMALL",price_small);
            FBmap.put("GASBIG",price_big);


            mDataBaseRef.child("Driver").child(DriverID).setValue(FBmap);




            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public Cursor getDriver() {
        String selectQuery = "SELECT * FROM " + TABLE_DRIVER + " LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor;
    }

    //TODO : change insert params to OBJECT type Order
    // Order Queries
    public void emptyOrder() {

        String deleteQuery = "delete from " + TABLE_ORDER + ";";
        getWritableDatabase().execSQL(deleteQuery);
    }

    public void deleteOrder(long id) {

        String deleteQuery = "delete from " + TABLE_ORDER + " WHERE _id = " + id + ";";
        getWritableDatabase().execSQL(deleteQuery);
    }

    public boolean insertOrder(String name, String phone, String area, String service, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {

            contentValues.put(CLIENT_NAME, name);
            contentValues.put(CLIENT_PHONE, phone);
            contentValues.put(CLIENT_AREA, area);
            contentValues.put(CLIENT_SERVICE, service);
            contentValues.put(CLIENT_STATUS, status);
            db.insert(TABLE_ORDER, null, contentValues);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    // Getting a specific order
    public Cursor getOrder(long id) {

        String driversList = "SELECT * FROM " + TABLE_ORDER + " WHERE _id = " + id + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(driversList, null);
        cursor.moveToFirst();
        return cursor;
    }

    // Getting all orders
    public Cursor getOrders() {

        String driversList = "SELECT * FROM " + TABLE_ORDER + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(driversList, null);
        cursor.moveToFirst();
        return cursor;
    }

}



