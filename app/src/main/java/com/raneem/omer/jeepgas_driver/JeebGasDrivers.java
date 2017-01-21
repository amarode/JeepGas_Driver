package com.raneem.omer.jeepgas_driver;


import android.content.Context;
import android.database.Cursor;



public class JeebGasDrivers {

    private static String drivername;
    private static String driverphone;
    private static String workingarea;
    private static String workinghours;
    private static String gasprice;
    private static String servicetype;


    public JeebGasDrivers(Context context) {

        DBHelper dbHelper = new DBHelper(context);
        Cursor cursor = dbHelper.getDriver();
        cursor.moveToFirst();
        if(cursor != null && cursor.moveToFirst()) {
            int drivernameIndex = cursor.getColumnIndex("drivername");
            int phoneIndex = cursor.getColumnIndex("driverphone");
            int workingareaIndex = cursor.getColumnIndex("workingarea");
            int workinghoursIndex = cursor.getColumnIndex("workinghoursIndex");
            int gaspriceIndex = cursor.getColumnIndex("gaspriceIndex");
            int servicetypeIndex = cursor.getColumnIndex("servicetypeIndex");


            drivername = cursor.getString(drivernameIndex);
            driverphone = cursor.getString(phoneIndex);
            workingarea = cursor.getString(workingareaIndex);
            workinghours = cursor.getString(workinghoursIndex);
            gasprice = cursor.getString(gaspriceIndex);
            servicetype = cursor.getString(servicetypeIndex);
        }
    }


    public static String getServicetype() {
        return servicetype;
    }

    public static void setServicetype(String servicetype) {
        JeebGasDrivers.servicetype = servicetype;
    }

    public static String getGasprice() {
        return gasprice;
    }

    public static void setGasprice(String gasprice) {
        JeebGasDrivers.gasprice = gasprice;
    }

    public static String getWorkinghours() {
        return workinghours;
    }

    public static void setWorkinghours(String workinghours) {
        JeebGasDrivers.workinghours = workinghours;
    }

    public static String getWorkingarea() {
        return workingarea;
    }

    public static void setWorkingarea(String workingarea) {
        JeebGasDrivers.workingarea = workingarea;
    }

    public static String getDriverphone() {
        return driverphone;
    }

    public static void setDriverphone(String phone) {
        JeebGasDrivers.driverphone = phone;
    }

    public static String getDrivername() {
        return drivername;
    }

    public static void setDrivername(String drivername) {
        JeebGasDrivers.drivername = drivername;
    }
}

