package com.example.user.mobcontacts.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.mobcontacts.models.Contact;
import com.example.user.mobcontacts.models.ContactImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_IMAGES = "images";

    private static final String _ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String PATH = "path";
    private static final String DISCRIPTION = "discription";
    private static final String CONTACT_ID = "contact_id";



    private static final String TEXT_TYPE=" TEXT";
    private static final String INTEGER_TYPE=" INTEGER";
    private static final String CREATE_TABLE="CREATE TABLE ";
    private static final String PRIMARY_KEY_INT=" INTEGER PRIMARY KEY AUTOINCREMENT,";
    private static final String DROP_TABLE_EXISTS="DROP TABLE IF EXISTS ";
    private static final String SELECT_FROM_ALL="SELECT  * FROM ";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = CREATE_TABLE + TABLE_CONTACTS +
                "(" + _ID + PRIMARY_KEY_INT
                + NAME + TEXT_TYPE + ","
                + PHONE + TEXT_TYPE + ","
                + AGE + INTEGER_TYPE +","
                + GENDER + INTEGER_TYPE + ")";

        String CREATE_IMAGES_TABLE= CREATE_TABLE + TABLE_IMAGES +
                "(" + _ID + PRIMARY_KEY_INT
                + CONTACT_ID + INTEGER_TYPE + ","
                + PATH + TEXT_TYPE + ","
                + DISCRIPTION + TEXT_TYPE + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_IMAGES_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE_EXISTS + TABLE_CONTACTS);
        db.execSQL(DROP_TABLE_EXISTS + TABLE_IMAGES);
        onCreate(db);

    }

    public void addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, contact.getName());
        values.put(PHONE, contact.getPhone());
        values.put(AGE, contact.getAge());
        values.put(GENDER, contact.getGender());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }

    public Contact getContact(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{_ID, NAME, PHONE, AGE, GENDER}, _ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
            cursor.moveToFirst();
        Contact contact = new Contact(cursor.getString(cursor.getColumnIndex(DBHelper.NAME)),
                cursor.getString(cursor.getColumnIndex(DBHelper.PHONE)),
                cursor.getInt(cursor.getColumnIndex(DBHelper.AGE)),
                cursor.getInt(cursor.getColumnIndex(DBHelper.GENDER)));
        cursor.close();
        return contact;

    }

    public int getLastContactID(){

        String selectQuery = SELECT_FROM_ALL + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        Contact contact = new Contact(cursor.getInt(cursor.getColumnIndex(DBHelper._ID)),
                cursor.getString(cursor.getColumnIndex(DBHelper.NAME)),
                cursor.getString(cursor.getColumnIndex(DBHelper.PHONE)),
                cursor.getInt(cursor.getColumnIndex(DBHelper.AGE)),
                cursor.getInt(cursor.getColumnIndex(DBHelper.GENDER)));
        cursor.close();

        return contact.getId();

    }

    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<>();
        String selectQuery = SELECT_FROM_ALL + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact(cursor.getInt(cursor.getColumnIndex(DBHelper._ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.PHONE)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.AGE)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.GENDER)));
            contactList.add(contact);

        }
        cursor.close();

        return contactList;

    }


    public int updateContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(PHONE, contact.getPhone());
        values.put(AGE, contact.getAge());
        values.put(GENDER, contact.getGender());

        return db.update(TABLE_CONTACTS, values, _ID + "=?", new String[]{String.valueOf(contact.getId())});

    }

    public void deleteContact(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, _ID + "=?", new String[]{String.valueOf(id)});
        db.close();

    }

    public void addImage(ContactImage contactImage) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CONTACT_ID, contactImage.getContact_id());
        values.put(PATH, contactImage.getPath());
        values.put(DISCRIPTION, contactImage.getDiscription());
        db.insert(TABLE_IMAGES, null, values);
        db.close();

    }

    public ContactImage getImage (int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMAGES, new String[]{_ID, CONTACT_ID, PATH, DISCRIPTION}, _ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        ContactImage contactImage = new ContactImage(cursor.getInt(cursor.getColumnIndex(DBHelper.CONTACT_ID)),
                cursor.getString(cursor.getColumnIndex(DBHelper.PATH)),
                cursor.getString(cursor.getColumnIndex(DBHelper.DISCRIPTION)));
        cursor.close();
        return contactImage;
    }

    public List<ContactImage> getAllImages() {

        List<ContactImage> contactList = new ArrayList<>();
        String selectQuery = SELECT_FROM_ALL + TABLE_IMAGES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            ContactImage contactImage = new ContactImage(cursor.getInt(cursor.getColumnIndex(DBHelper._ID)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.CONTACT_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.PATH)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.DISCRIPTION)));
            contactList.add(contactImage);

        }
        cursor.close();

        return contactList;

    }

    public List<ContactImage> getAllContactImages(int id) {

        List<ContactImage> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_IMAGES, new String[]{_ID, CONTACT_ID, PATH, DISCRIPTION}, CONTACT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()) {
            ContactImage contactImage = new ContactImage(cursor.getInt(cursor.getColumnIndex(DBHelper._ID)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.CONTACT_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.PATH)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.DISCRIPTION)));
            contactList.add(contactImage);

        }
        cursor.close();

        return contactList;

    }

    public int updateContact(ContactImage contactImage) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_ID, contactImage.getContact_id());
        values.put(PATH, contactImage.getPath());
        values.put(DISCRIPTION, contactImage.getDiscription());


        return db.update(TABLE_IMAGES, values, _ID + "=?", new String[]{String.valueOf(contactImage.getId())});

    }

    public void deleteImage(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGES, _ID + "=?", new String[]{String.valueOf(id)});
        db.close();

    }

    public void deleteContactImages(int contact_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGES, CONTACT_ID + "=?", new String[]{String.valueOf(contact_id)});
        db.close();

    }

}
