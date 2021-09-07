package com.applicationdevelopers.lordoffood.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.Model.Person.Person;

import java.util.LinkedList;
import java.util.List;


public class PersonDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="LordOfFood.db";
    private static final int DATABASE_VERSION =7;
    public static final String TABLE_NAME="ORDERS_RECORD";
    public static final String TABLE_NAME_FAVOURITE="FAVOURITE_RECORD";
    public static final String TABLE_NAME_ORDERS="ORDER_RECORDS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PNAME = "product_name";
    public static final String COLUMN_PRICE = "product_price";
    public static final String COLUMN_QTY = "product_quantity";
    public static final String COLUMN_CATEGORY ="product_category";
    private static final String COL_PRODUCT_IMAGE = "PRODUCT_IMAGE";
    private static final String COL_PRODUCT_Category = "PRODUCT_CATEGORY";
    private static final String COL_ORDER_DATE = "ORDER_DATE";
    private static final String COL_ORDER_ADDRESS = "ORDER_ADDRESS";
    private static final String COL_ORDER_NAME = "ORDER_NAME";
    private static final String COL_ORDER_EMAIL = "ORDER_EMAIL";
    private static final String COL_ORDER_PHONE = "ORDER_PHONE";

    public PersonDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PNAME + " TEXT NOT NULL, " +
                COLUMN_PRICE+ " NUMBER NOT NULL, " +
                COLUMN_QTY + " NUMBER NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COL_PRODUCT_IMAGE + " TEXT NOT NULL);"
        );

        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME_FAVOURITE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PNAME + " TEXT NOT NULL, " +
                COLUMN_PRICE+ " NUMBER NOT NULL, " +
                COL_PRODUCT_IMAGE + " TEXT NOT NULL, "+
                COL_PRODUCT_Category + " TEXT NOT NULL);"
        );

        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PNAME + " TEXT NOT NULL, " +
                COLUMN_PRICE+ " NUMBER NOT NULL, " +
                COLUMN_QTY + " NUMBER NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COL_PRODUCT_IMAGE + " TEXT NOT NULL, " +
                COL_ORDER_DATE + "TEXT NOT NULL, " +
                COL_ORDER_ADDRESS + "TEXT NOT NULL, " +
                COL_ORDER_NAME + "TEXT NOT NULL, " +
                COL_ORDER_EMAIL + "TEXT NOT NULL, " +
                COL_ORDER_PHONE + "TEXT NOT NULL);"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ORDERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAVOURITE);
        this.onCreate(sqLiteDatabase);
    }
    /**create record**/
    public  void insertProductOrder( String pname, String price, String qty,String category,String pro_image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PNAME, pname);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QTY, qty);
        values.put(COLUMN_CATEGORY,category);
        values.put(COL_PRODUCT_IMAGE,pro_image);
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
    /**create record**/
    public  void insertOrder( String pname, String price, String qty,String category,String pro_image,String orderDate,String orderAddress
            ,String orderName,String orderEmail,String orderPhone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PNAME, pname);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QTY, qty);
        values.put(COLUMN_CATEGORY,category);
        values.put(COL_PRODUCT_IMAGE,pro_image);
        values.put(COL_ORDER_DATE,orderDate);
        values.put(COL_ORDER_ADDRESS,orderAddress);
        values.put(COL_ORDER_NAME,orderName);
        values.put(COL_ORDER_EMAIL,orderEmail);
        values.put(COL_ORDER_PHONE,orderPhone);
        // insert
        db.insert(TABLE_NAME_ORDERS,null, values);
        db.close();
    }

    public void deletetableRecord(Context context) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
        //Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
    /**delete record**/
    public void deleteOrder(String id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE product_name='"+id+"'");
        //Snackbar.make((Activity)context.getApplicationContext(),"Added to cart",Snackbar.LENGTH_SHORT).show();
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
        db.close();

    }
    /**delete record**/
    public void deleteFavourite(String id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_FAVOURITE+" WHERE product_name='"+id+"'");
        //Snackbar.make((Activity)context.getApplicationContext(),"Added to cart",Snackbar.LENGTH_SHORT).show();
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        res.close();
        return res;
    }
    public Cursor getSpecificData(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + TABLE_NAME + " where product_name = ? " ,new String[]{name});
        return res;
    }
    public Cursor getSpecificDataFavourite(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + TABLE_NAME_FAVOURITE + " where product_name = ? " ,new String[]{name});
        return res;
    }
    /**Update data**/
    public boolean updateData(String name,String price,String qty,String category){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_PNAME,name);
        contentValues.put(COLUMN_PRICE,price);
        contentValues.put(COLUMN_QTY,qty);
        contentValues.put(COLUMN_CATEGORY,category);
        db.update(TABLE_NAME,contentValues,"product_name = ?",new String[]{name});
        db.close();
        return true;
    }
    /**Query records, give options to filter results**/
    public List<Person> productListinCart(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Person> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Person person;

        if (cursor.moveToFirst()) {
            do {
                person = new Person();
                person.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                person.setPname(cursor.getString(cursor.getColumnIndex(COLUMN_PNAME)));
                person.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                person.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_QTY)));
                person.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                person.setImageUrl(cursor.getString(cursor.getColumnIndex(COL_PRODUCT_IMAGE)));
                personLinkedList.add(person);
            } while (cursor.moveToNext());
        }
        return personLinkedList;
    }
    /**Query records, give options to filter results**/
    public List<Person> productListingFavourite(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME_FAVOURITE;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME_FAVOURITE + " ORDER BY "+ filter;
        }

        List<Person> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Person person;

        if (cursor.moveToFirst()) {
            do {
                person = new Person();
                person.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                person.setPname(cursor.getString(cursor.getColumnIndex(COLUMN_PNAME)));
//                person.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                person.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                person.setImageUrl(cursor.getString(cursor.getColumnIndex(COL_PRODUCT_IMAGE)));
                person.setCategory(cursor.getString(cursor.getColumnIndex(COL_PRODUCT_Category)));
                personLinkedList.add(person);
            } while (cursor.moveToNext());
        }
        return personLinkedList;
    }
    /**create record**/
    public  void insertProductFavourite( String pname, String price,String pro_image,String pCategory) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PNAME, pname);
//        values.put(COLUMN_CATEGORY,pCategory);
        values.put(COLUMN_PRICE, price);
        values.put(COL_PRODUCT_IMAGE,pro_image);
        values.put(COL_PRODUCT_Category,pCategory);
        // insert
        db.insert(TABLE_NAME_FAVOURITE,null, values);
        db.close();
    }
    public long getOrderCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
    public long getFavouriteCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME_FAVOURITE);
        db.close();
        return count;
    }
}
