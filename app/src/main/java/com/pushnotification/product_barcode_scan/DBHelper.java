package com.pushnotification.product_barcode_scan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static String name = "product";
    public static int DB_Version = 25;


    public DBHelper(@Nullable Context context) {
        super(context, name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Category(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Subcategory(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,category TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Brand(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,subcategory TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Model(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,brand TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Name(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,model TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT,email TEXT,password TEXT,type TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Product(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,category TEXT,subcategory TEXT,count TEXT,brand TEXT,model TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CartItem(id INTEGER PRIMARY KEY AUTOINCREMENT,category TEXT,subcategory TEXT,count TEXT,brand TEXT,model TEXT,name TEXT,status TEXT,order_id TEXT)");
    }

    //add data in cartItem
    public void addcartItem(String category, String subcategory, String brand, String model, String count, String name, String status) {
        ContentValues values = new ContentValues();
        values.put("category", category);
        values.put("subcategory", subcategory);
        values.put("brand", brand);
        values.put("model", model);
        values.put("count", "1");
        values.put("name", name);
        values.put("status", "cart");
        values.put("order_id", "");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("CartItem", null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<cart> getcartItem() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CartItem where Status = 'cart'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<cart> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String Name = cursor.getString(cursor.getColumnIndex("name"));
                String Category = cursor.getString(cursor.getColumnIndex("category"));
                String Subcategory = cursor.getString(cursor.getColumnIndex("subcategory"));
                String Brand = cursor.getString(cursor.getColumnIndex("brand"));
                String Model = cursor.getString(cursor.getColumnIndex("model"));
                String Status = cursor.getString(cursor.getColumnIndex("status"));
                String count = cursor.getString(cursor.getColumnIndex("count"));
                list.add(new cart(id, Category, Subcategory, Brand, Model, Name, count, Status));
            }
            while (cursor.moveToNext());
        }

        return list;
    }

    @SuppressLint("Range")
    public List<cart> getcartItembyName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CartItem where Status = 'cart'and name = '" + name + "' ";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<cart> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String Name = cursor.getString(cursor.getColumnIndex("name"));
                String Category = cursor.getString(cursor.getColumnIndex("category"));
                String Subcategory = cursor.getString(cursor.getColumnIndex("subcategory"));
                String Brand = cursor.getString(cursor.getColumnIndex("brand"));
                String Model = cursor.getString(cursor.getColumnIndex("model"));
                String Status = cursor.getString(cursor.getColumnIndex("status"));
                String count = cursor.getString(cursor.getColumnIndex("count"));
                list.add(new cart(id, Category, Subcategory, Brand, Model, Name, count, Status));
            }
            while (cursor.moveToNext());
        }

        return list;
    }

    /* public void DeleteProduct(){
         SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("DELETE FROM CartItem WHERE status = 'Placed'");
         db.close();
     }
    */
    public void UpdateCart(String order_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE CartItem SET status = 'Placed',order_id = '" + order_id + "' WHERE status = 'cart'");
        db.close();
    }


    //getting product
    @SuppressLint("Range")
    List<Product> getproductByname(String name, String category, String subcategory, String brand, String model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Product where name ='" + name + "' and category = '" + category + "' and subcategory = '" + subcategory + "' and brand = '" + brand + "' and model = '" + model + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<Product> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String strname = cursor.getString(cursor.getColumnIndex("name"));
                String strcategory = cursor.getString(cursor.getColumnIndex("category"));
                String strsubcategory = cursor.getString(cursor.getColumnIndex("subcategory"));
                String strbrand = cursor.getString(cursor.getColumnIndex("brand"));
                String srtmodel = cursor.getString(cursor.getColumnIndex("model"));
                String srtcount = cursor.getString(cursor.getColumnIndex("count"));
                list.add(new Product(id, strname, strcategory, strsubcategory, strbrand, srtmodel, srtcount));
            }
            while (cursor.moveToNext());
        }
        return list;
    }
  @SuppressLint("Range")
    List<Product> getproduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Product ";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<Product> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String strname = cursor.getString(cursor.getColumnIndex("name"));
                String strcategory = cursor.getString(cursor.getColumnIndex("category"));
                String strsubcategory = cursor.getString(cursor.getColumnIndex("subcategory"));
                String strbrand = cursor.getString(cursor.getColumnIndex("brand"));
                String srtmodel = cursor.getString(cursor.getColumnIndex("model"));
                String srtcount = cursor.getString(cursor.getColumnIndex("count"));
                list.add(new Product(id, strname, strcategory, strsubcategory, strbrand, srtmodel, srtcount));
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    //add product count
    public void updateproduct(String name, String category, String subcategory, String brand, String model) {
        int count = Integer.parseInt(getproductByname(name, category, subcategory, brand, model).get(0).getCount());
        count++;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Product SET count='" + count + "'");
    }

    //remove product count
    public void updateproductcount(String name, String category, String subcategory, String brand, String model) {
        int count = Integer.parseInt(getproductByname(name, category, subcategory, brand, model).get(0).getCount());
        count--;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE Product SET count = '" + count + "'");
    }
    public void updateCart(String name, String model) {
        int count = Integer.parseInt(getcartItembyName(name).get(0).getCount());
        count++;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE CartItem SET count = '" + count + "' where name ='"+name+"'and model ='"+model+"'");
    }
    public void updateproductcount(String name, String category, String subcategory, String brand, String model, String Count) {
        int productcurrentcount = Integer.parseInt(getproductByname(name, category, subcategory, brand, model).get(0).getCount());
        int count = Integer.parseInt(Count);
        int updatedcount = productcurrentcount - count;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE Product SET count = '" + updatedcount + "' where name = '"+name+"' and model = '"+model+"'");
    }

    public void addProduct(String name, String category, String subcategory, String brand, String model) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category", category);
        values.put("subcategory", subcategory);
        values.put("count", "1");
        values.put("brand", brand);
        values.put("model", model);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Product", null, values);
        db.close();
    }


    //add category table
    public void addcategory(String text) {
        ContentValues values = new ContentValues();
        values.put("name", text);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Category", null, values);
        db.close();
    }

    //get category data
    @SuppressLint("Range")
    public List<category> getcategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Category";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<category> list = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                list.add(new category(id, text));

            }
            while (cursor.moveToNext());
        }
        return list;
    }


    //add subcatogery
    public void addsubcategory(String text, String category) {
        ContentValues values = new ContentValues();
        values.put("name", text);
        values.put("category", category);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Subcategory", null, values);
        db.close();
    }

    //get subcategory data
    @SuppressLint("Range")
    public List<subcategory> getsubcategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        //   String query = "SELECT * FROM Subcategory WHERE TYPE ='"+category+"'";
        String query = "SELECT * FROM Subcategory ";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<subcategory> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String Text = cursor.getString(cursor.getColumnIndex("name"));
                String Category = cursor.getString(cursor.getColumnIndex("category"));
                list.add(new subcategory(id, Text, Category));
            }
            while (cursor.moveToNext());
        }

        return list;
    }

    //update new query and add category
    @SuppressLint("Range")
    public List<subcategory> getsubcategoryforCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   String query = "SELECT * FROM Subcategory WHERE TYPE ='"+category+"'";
        String query = "SELECT * FROM Subcategory where category ='" + category + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<subcategory> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String Text = cursor.getString(cursor.getColumnIndex("name"));
                String Category = cursor.getString(cursor.getColumnIndex("category"));
                list.add(new subcategory(id, Text, Category));
            }
            while (cursor.moveToNext());
        }

        return list;
    }

    //add brand method
    public void addbrand(String text, String subcategory) {
        ContentValues values = new ContentValues();
        values.put("name", text);
        values.put("subcategory", subcategory);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Brand", null, values);
        db.close();
    }

    //get brand method

    @SuppressLint("Range")
    public List<brand> getbrand() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Brand";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<brand> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String subcategory = cursor.getString(cursor.getColumnIndex("subcategory"));
                list.add(new brand(id, text, subcategory));
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    //get brand for subcategory method
    @SuppressLint("Range")
    public List<brand> getbrandforsubcategory(String Subcategory) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Brand WHERE Subcategory = '" + Subcategory + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<brand> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String subcategory = cursor.getString(cursor.getColumnIndex("subcategory"));
                list.add(new brand(id, text, subcategory));
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    //add model method
    public void addmodel(String text, String brand) {
        ContentValues values = new ContentValues();
        values.put("name", text);
        values.put("brand", brand);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Model", null, values);
        db.close();
    }

    //get model method
    @SuppressLint("Range")
    public List<model> getmodel() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Model";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<model> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String brand = cursor.getString(cursor.getColumnIndex("brand"));
                list.add(new model(id, text, brand));
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public List<model> getmodelforbrand(String Brand) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Model WHERE brand ='" + Brand + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<model> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String brand = cursor.getString(cursor.getColumnIndex("brand"));
                list.add(new model(id, text, brand));
            }
            while (cursor.moveToNext());
        }
        return list;
    }


    //add name method
    public void addname(String text, String model) {
        ContentValues values = new ContentValues();
        values.put("name", text);
        values.put("model", model);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Name", null, values);
        db.close();
    }

    //get category data
    @SuppressLint("Range")
    public List<namepro> getname() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Name";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<namepro> list = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String model = cursor.getString(cursor.getColumnIndex("model"));
                list.add(new namepro(id, text, model));

            }
            while (cursor.moveToNext());
        }
        return list;
    }

    //get category data
    @SuppressLint("Range")
    public List<namepro> getnameformodel(String model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Name WHERE model = '" + model + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<namepro> list = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String Model = cursor.getString(cursor.getColumnIndex("model"));
                list.add(new namepro(id, text, Model));

            }
            while (cursor.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public List<namepro> searchname(String productname, String model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Name WHERE name LIKE '%" + productname + "%' and model = '" + model + "'";
        Log.e("TAG", "searchname: " + query);
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<namepro> list = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("name"));
                String Model = cursor.getString(cursor.getColumnIndex("model"));
                list.add(new namepro(id, text, Model));

            }
            while (cursor.moveToNext());
        }
        return list;
    }


    public void adduser(String email, String password, String type) {
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("type", type);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("User", null, values);
        db.close();

    }

    @SuppressLint("Range")
    public List<user> getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<user> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                list.add(new user(id, email, password, type));

            }
            while (cursor.moveToNext());
        }
        return list;

    }

    @SuppressLint("Range")
    public List<user> getUserbyEmailNPass(String em, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User where email= '" + em + "' and password = '" + pass + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        List<user> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                list.add(new user(id, email, password, type));

            }
            while (cursor.moveToNext());
        }
        return list;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS Subcategory");
        db.execSQL("DROP TABLE IF EXISTS Brand");
        db.execSQL("DROP TABLE IF EXISTS model");
                    db.execSQL("DROP TABLE IF EXISTS User");
*/
        db.execSQL("DROP TABLE IF EXISTS CartItem");

        onCreate(db);
//            task.execute("jhbvrkbkvs");
    }


    //Asyntask ex.
/*
    AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog dialog = new ProgressDialog()
        }

        @Override
        protected String doInBackground(String... strings) {
            //download task
            return "sucess";
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("TAG", "onPostExecute: "+s );

            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    };
*/
}
