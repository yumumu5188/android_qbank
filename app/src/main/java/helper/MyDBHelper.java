package helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context) {
        super(context, "helper.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table question (id integer,pubTime text,typeid integer,cataid integer,content text,answer text,options text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
