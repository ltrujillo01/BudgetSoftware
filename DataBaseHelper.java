package com.example.budgetsoftware;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.util.Calendar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String PURCHASE_TABLE = "PURCHASE_TABLE";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_COST = "COST";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DATE = "DATE";
    private String Array;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "purchase.DB", null, 1);
    }

    //This is called when a database is called
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PURCHASE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_COST + " INT, " + COLUMN_DATE + " TEXT ) ";

        db.execSQL(createTableStatement);




    }
    //Whenever version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(PurchaseModel purchaseModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_TITLE, purchaseModel.getTitle());
        cv.put(COLUMN_COST, purchaseModel.getPrice());
        cv.put(COLUMN_DATE, purchaseModel.getCurrentTime());


        long insert = db.insert(PURCHASE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else
        {
            return true;
        }


    }

    public int getDailyExpense()
    {
        dateRetrieve day = new dateRetrieve();
        String strDate = day.getCurrentDay();



        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT SUM(" + COLUMN_COST + ") as sum_cost FROM " + PURCHASE_TABLE + " WHERE DATE LIKE '%" + strDate + "%'";
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        else
        {

            return 0;

        }




    }

    public int getWeeklyExpense()
    {
        String[] week;
        int amount = 0;
        SQLiteDatabase db = this.getReadableDatabase();


        dateRetrieve day = new dateRetrieve();

        week = day.getCurrentWeek();
        //System.out.println(Arrays.toString(week));
        //1 Sunday 2 Monday ........... 7 Saturday

        for (int i = 0; i < week.length; i++) {

            String queryString = "SELECT SUM(" + COLUMN_COST + ") as sum_cost FROM " + PURCHASE_TABLE + " WHERE DATE LIKE '%" + week[i] + "%'";

            Cursor cursor = db.rawQuery(queryString, null);

            if (cursor.moveToFirst()) {
                //System.out.println( "Day " + i + " amount: " + cursor.getInt(0));
                amount = amount + cursor.getInt(0);

            } else {

                return 0;

            }


        }
        return amount;




    }



    public List<PurchaseModel> getEverythingFromCurrentDay()
    {
        List<PurchaseModel> returnList =  new ArrayList<>();
        dateRetrieve day = new dateRetrieve();
        String strDate = day.getCurrentDay();



        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + PURCHASE_TABLE + " WHERE DATE LIKE '%" + strDate + "%'";

        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst())
        {
            // loop through the cursor
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int price = cursor.getInt(2);

                PurchaseModel newPurchase = new PurchaseModel(id, title, price);
                returnList.add(newPurchase);

            } while(cursor.moveToNext());
        }
        else
        {
            //Fail
        }

        cursor.close();
        db.close();

        ;
        //System.out.println(returnList.get(0));
        return returnList; 

        
    }



    public boolean deleteOne (PurchaseModel purchaseModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "DELETE FROM " + PURCHASE_TABLE + " WHERE " + COLUMN_ID + " = " + purchaseModel.getId();
        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst())
        {
            return true;

        }
        else
        {
            return false;
        }

    }


    }


