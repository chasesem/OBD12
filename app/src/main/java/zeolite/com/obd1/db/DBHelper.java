package zeolite.com.obd1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zeolite on 16/1/26.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME="obd.db";
    private static final int DATABASEVERSION=1;

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS record");
        db.execSQL("CREATE TABLE record (recordid integer primary key autoincrement, time varchar(20), " +
                "currentmeil varchar(20)," +
                "fixtype varchar(10)," +
                "cost varchar(20)," +
                "fixitem varchar(50)," +
                "save varchar(5)," +
                "rating varchar(5)," +
                "comment varchar(50)" +
                ")");

        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("CREATE TABLE account (id integer primary key autoincrement, phone varchar(20))");

//        public CarInfo(int id, String brandCode, String brandStyle, String CCode, String OCode, String productYears, String annualDate, String color, String kilometers, String remark) {


            db.execSQL("DROP TABLE IF EXISTS carInfo");
        db.execSQL("CREATE TABLE carInfo (id integer primary key autoincrement, brandCode varchar(20)," +
                "brandStyle varchar(20)," +
                "CCode varchar(20)," +
                "OCode varchar(20)," +
                "productYears varchar(50)," +
                "annualDate varchar(50)," +
                "color varchar(20)," +
                "kilometers varchar(50)," +
                "remark varchar(50)" +
                ")");


//        public CarOwnerInfo(int id, String userCode, String userName, String sex, String phone, String telephone, String province, String city, String area, String street) {

            db.execSQL("DROP TABLE IF EXISTS carOwnerInfo");
        db.execSQL("CREATE TABLE carOwnerInfo (id integer primary key autoincrement, userCode varchar(20)," +
                "userName varchar(20)," +
                "sex varchar(20)," +
                "phone varchar(20)," +
                "telephone varchar(20)," +
                "province varchar(20)," +
                "city varchar(20)," +
                "area varchar(20)," +
                "street varchar(20)" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS carInfo");
        db.execSQL("DROP TABLE IF EXISTS carOwnerInfo");
        onCreate(db);
    }
}
