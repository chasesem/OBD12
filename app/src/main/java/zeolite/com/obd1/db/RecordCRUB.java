package zeolite.com.obd1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zeolite.com.obd1.entity.account.Account;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.entity.account.CarOwnerInfo;
import zeolite.com.obd1.entity.record.RecordEntity;
import zeolite.com.obd1.view.me.CarMessage;


/**
 * Created by Zeolite on 16/1/26.
 */
public class RecordCRUB {
    private DBHelper dbHelper;

    public RecordCRUB(Context context){
        this.dbHelper=new DBHelper(context);
    }



    public void saveRecord(RecordEntity record){
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("time",record.getTime());
        contentValues.put("currentmeil",record.getCurrentmeil());
        contentValues.put("fixtype",record.getFixtype());
        contentValues.put("cost",record.getCost());
        contentValues.put("fixitem",record.getFixitem());
        contentValues.put("save",record.getSave());
        contentValues.put("rating",record.getRatings());
        contentValues.put("comment",record.getComment());
        db.insert("record", null, contentValues);
        db.close();
    }

    public void deleteRecord(Integer recordid){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL("delete from record where recordid=?", new Object[]{recordid.toString()});
    }

    public List<RecordEntity> findAllRecord(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        List<RecordEntity> recordEntities=new ArrayList<RecordEntity>();

        Cursor cursor=db.query("record",null,null,null,null,null,null);

        while (cursor.moveToNext()){
            String time=cursor.getString(cursor.getColumnIndex("time"));
            String currentmeil=cursor.getString(cursor.getColumnIndex("currentmeil"));
            String fixtype=cursor.getString(cursor.getColumnIndex("fixtype"));
            String cost=cursor.getString(cursor.getColumnIndex("cost"));
            String fixitem=cursor.getString(cursor.getColumnIndex("fixitem"));
            String save=cursor.getString(cursor.getColumnIndex("save"));
            String ratings=cursor.getString(cursor.getColumnIndex("rating"));
            String comment=cursor.getString(cursor.getColumnIndex("comment"));
            Log.i("findAll",time+"--"+currentmeil+"--"+fixtype+"--"+cost+"--"+fixitem+"--"+save+"--"+ratings+"--"+comment);
            RecordEntity recordEntity=new RecordEntity(time,currentmeil,fixtype,cost,fixitem,save,ratings,comment);
            recordEntities.add(recordEntity);
        }
        cursor.close();
        db.close();

        return recordEntities;
    }


    //Account
    public void savePhoneMsg(Account account){
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("phone",account.getPhoneNum());
        db.insert("account", null, contentValues);
        db.close();
    }


    public Account queryAccount(){

        Account account=new Account();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("account",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String phone=cursor.getString(cursor.getColumnIndex("phone"));
            account.setPhoneNum(phone);
        }
        cursor.close();
        db.close();
        return account;
    }

    //car message
    public void saveCarInfo(CarInfo carInfo){
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("code", carInfo.getCode());
        contentValues.put("bCode",carInfo.getCode());
        contentValues.put("productYear",carInfo.getbCode());
        contentValues.put("annualDate",carInfo.getAnnualDate());
        contentValues.put("color",carInfo.getColor());
        contentValues.put("remark",carInfo.getRemark());
        db.insert("carInfo", null, contentValues);
        db.close();
    }


    public CarInfo queryCarInfo(){

        CarInfo carInfo = null;

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("carInfo",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String code=cursor.getString(cursor.getColumnIndex("code"));
            String bCode=cursor.getString(cursor.getColumnIndex("bCode"));
            String productYear=cursor.getString(cursor.getColumnIndex("productYear"));
            String annualDate=cursor.getString(cursor.getColumnIndex("annualDate"));
            String color=cursor.getString(cursor.getColumnIndex("color"));
            String remark=cursor.getString(cursor.getColumnIndex("remark"));

            carInfo=new CarInfo(1,code,bCode,productYear,annualDate,color,remark);
            Log.i("carInfo", carInfo.getCode() + "," + carInfo.getbCode() + "," + carInfo.getProductYear() + "," + carInfo.getAnnualDate() + "," + carInfo.getCode() + "," + carInfo.getRemark());

        }
        cursor.close();
        db.close();
        return carInfo;
    }

    //carOwnerMsg
    public void saveCarOwnerInfo(CarOwnerInfo carOwnerInfo){
        SQLiteDatabase db=dbHelper.getWritableDatabase();

//        public CarOwnerInfo(int id, String userCode, String userName, String sex, String phone, String telephone, String province, String city, String area, String street) {

        ContentValues contentValues=new ContentValues();
        contentValues.put("userCode", carOwnerInfo.getUserCode());
        contentValues.put("userName",carOwnerInfo.getUserName());
        contentValues.put("sex",carOwnerInfo.getSex());
        contentValues.put("phone",carOwnerInfo.getPhone());
        contentValues.put("telephone",carOwnerInfo.getTelephone());
        contentValues.put("province",carOwnerInfo.getProvince());
        contentValues.put("city",carOwnerInfo.getCity());
        contentValues.put("area",carOwnerInfo.getArea());
        contentValues.put("street",carOwnerInfo.getStreet());
        db.insert("carOwnerInfo", null, contentValues);
        db.close();
    }


    public CarOwnerInfo queryCarOwnerInfo(){

        CarOwnerInfo carOwnerInfo = null;

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("carOwnerInfo",null,null,null,null,null,null);
        while (cursor.moveToNext()){

//            public CarOwnerInfo(int id, String userCode, String userName, String sex, String phone, String telephone, String province, String city, String area, String street) {

            String userCode=cursor.getString(cursor.getColumnIndex("userCode"));
            String userName=cursor.getString(cursor.getColumnIndex("userName"));
            String sex=cursor.getString(cursor.getColumnIndex("sex"));
            String phone=cursor.getString(cursor.getColumnIndex("phone"));
            String telephone=cursor.getString(cursor.getColumnIndex("telephone"));
            String province=cursor.getString(cursor.getColumnIndex("province"));
            String city=cursor.getString(cursor.getColumnIndex("city"));
            String area=cursor.getString(cursor.getColumnIndex("area"));
            String street=cursor.getString(cursor.getColumnIndex("street"));

            carOwnerInfo=new CarOwnerInfo(1,userCode,userName,sex,phone,telephone,province,city,area,street);

        }
        cursor.close();
        db.close();
        return carOwnerInfo;
    }


}
