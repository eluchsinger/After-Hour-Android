package ch.hsr.afterhour.service.database;

import android.provider.BaseColumns;


public final class UserContract {
    private UserContract() { }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_FULLNAME = "fullname";
    }

}