package ch.hsr.afterhour.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.database.UserContract.UserEntry;

import static android.content.ContentValues.TAG;


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "afterhourdb.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserEntry.TABLE_NAME +
                " (" +
                UserEntry._ID + " TEXT PRIMARY KEY," +
                UserEntry.COLUMN_NAME_FULLNAME + " TEXT," +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveCredentialsToDatabase(SQLiteDatabase db, User user) {
        List<String> locallySavedCredentials = getCredentialsFromDatabase(db);
        if (!locallySavedCredentials.isEmpty()) {
            return;
        }
        Log.i(TAG, "Saving Credentials");
        user = Application.get().getUser();
        ContentValues values = new ContentValues();
        values.put(UserEntry._ID, user.getId());
        values.put(UserEntry.COLUMN_NAME_FULLNAME, user.getName());
        long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);
        final int ERROR_ID = -1;
        if (newRowId == ERROR_ID) {
            Log.e(TAG, "Could not save credentials");
        } else {
            Log.i(TAG, "Credentials saved");
        }
    }

    public List<String> getCredentialsFromDatabase(SQLiteDatabase db) {
        List<String> itemIds = new ArrayList<>();
        Cursor resultSet = db.rawQuery("SELECT * from " + UserEntry.TABLE_NAME, null);
        if (resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            final int COLUMN_USERNAME = 2;
            final int COLUMN_PASSWORD = 3;
            itemIds.add(resultSet.getString(COLUMN_USERNAME));
            itemIds.add(resultSet.getString(COLUMN_PASSWORD));
            resultSet.close();
        }
        return itemIds;

    }

    public void removeCredentialsFromDatabase(SQLiteDatabase db) {
        String selection = UserEntry.COLUMN_NAME_FULLNAME + " LIKE ?";
        String[] selectionArgs = {Application.get().getUser().getName()};
        db.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
    }
}
