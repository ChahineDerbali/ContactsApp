package com.example.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "contacts_db"; // Database name
    private static final int DB_VERSION = 1;  // Database version

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE contacts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "phone TEXT);";
        db.execSQL(createTable);

        db.execSQL("INSERT INTO contacts (name, phone) VALUES ('Foulen BEN FOULEN', '22334455');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("phone", contact.getPhone());
        db.insert("contacts", null, values);
        db.close();
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("phone", contact.getPhone());
        db.update("contacts", values, "id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contacts", "id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public List<HashMap<String, String>> getAllContacts() {
        List<HashMap<String, String>> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM contacts";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<>();
                contact.put("id", cursor.getString(cursor.getColumnIndex("id")));
                contact.put("name", cursor.getString(cursor.getColumnIndex("name")));
                contact.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public List<HashMap<String, String>> getContactsByName(String query) {
        List<HashMap<String, String>> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM contacts WHERE name LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<>();
                contact.put("id", cursor.getString(cursor.getColumnIndex("id")));
                contact.put("name", cursor.getString(cursor.getColumnIndex("name")));
                contact.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }
}

