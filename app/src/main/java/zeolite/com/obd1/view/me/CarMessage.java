package zeolite.com.obd1.view.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import zeolite.com.obd1.R;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.Account;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.entity.record.BrandEntity;
import zeolite.com.obd1.network.URLAddress;
import zeolite.com.obd1.view.bluetooth.OBD2MonitorService;
import zeolite.com.obd1.view.fragment.MeFragment;
import zeolite.com.obd1.view.fragment.RecordFragment;

/**
 * Created by Zeolite on 16/3/2.
 */
public class CarMessage extends Activity implements View.OnClickListener {


    private EditText cCodeET;
    private EditText oCodeET;
    private EditText productYearET;
    private EditText kilometersET;
    private EditText annualDateET;
    private EditText colorET;
    private EditText remarkET;
    private Button saveBtn;

    private List<BrandEntity> brandEntities;

    //brandCode
    private Spinner brandCodeSp;
    private List<String> brandCodeList;
    private ArrayAdapter<String> brandCodeAdapter;
    private String brandCode="";

    //brandStyle
    private Spinner brandStyleSp;
    private List<String> brandStyleList;
    private ArrayAdapter<String> brandStyleAdapter;
    private String brandStyle="";


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(CarMessage.this,"error",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    setUpSpinner(brandCodeSp, brandCodeList, brandCodeAdapter);
                    break;
                case 3:
                    setUpSpinner(brandStyleSp,brandStyleList,brandStyleAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_message_layout);

        initView();
        initDate();
    }

    private void initDate() {
        saveBtn.setOnClickListener(this);


        //getCarINfo
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn;
                InputStream is;
                try {
                    conn = (HttpURLConnection) new URL(URLAddress.GET_BRAND_INFO).openConnection();
                    conn.setRequestMethod("GET");
                    is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    StringBuffer result = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
//                    Log.i("temp", result.toString());

                    String h="<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">";
                    String e="</string>";
                    String temp=result.toString().replace(h,"");
                    temp=temp.replace(e, "");
//                    temp=temp.replace("Data","\"Data\"");
                    temp=temp.replace("{\"Data\":", "");
                    temp=temp.substring(0, temp.length() - 1);
                    Log.i("temp", temp);


                    brandEntities= JSON.parseArray(temp, BrandEntity.class);
                    Log.i("temp", brandEntities.get(0).getBrandCode());

                    brandCodeList=new ArrayList<String>();
                    for (int i=0;i<brandEntities.size();i++) {
                        brandCodeList.add(brandEntities.get(i).getBrandCode());
                    }
                    handler.sendEmptyMessage(2);

                    brandCodeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            brandCode=brandCodeList.get(position);
//                            Log.i("brandCode",brandCode);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    brandStyleList=new ArrayList<String>();
                    for (int i=0;i<brandEntities.size();i++) {
                        brandStyleList.add(brandEntities.get(i).getBrandStyle());
                    }
                    handler.sendEmptyMessage(3);

                    brandStyleSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            brandStyle=brandStyleList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void setUpSpinner(Spinner spinner,List<String> spList,ArrayAdapter<String> adapter){
        //适配器
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spList);
        //设置样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(adapter);
    }


    private void initView() {
        cCodeET=(EditText)findViewById(R.id.et_cCode);
        oCodeET=(EditText)findViewById(R.id.et_oCode);
        productYearET=(EditText)findViewById(R.id.et_productYear);
        annualDateET=(EditText)findViewById(R.id.et_productYear);
        colorET=(EditText)findViewById(R.id.et_color);
        kilometersET=(EditText)findViewById(R.id.et_kilometers);
        remarkET=(EditText)findViewById(R.id.et_remark);
        saveBtn=(Button)findViewById(R.id.save_car_message_btn);

        brandCodeSp=(Spinner)findViewById(R.id.sp_brand_code);
        brandStyleSp=(Spinner)findViewById(R.id.sp_brand_style);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_car_message_btn:


//                if (codeET.getText().toString().length()!=17){
//                    handler.sendEmptyMessage(1);
//                }else {

                int i = 0;
                ++i;

//                public CarInfo(int id, String brandCode, String brandStyle, String CCode, String OCode, String productYears, String annualDate, String color, String kilometers, String remark) {

                final CarInfo carInfo = new CarInfo(i,brandCode, brandStyle,cCodeET.getText().toString(), oCodeET.getText().toString(), productYearET.getText().toString(), annualDateET.getText().toString(),
                        colorET.getText().toString(),kilometersET.getText().toString(), remarkET.getText().toString());



                JSONObject temp = new JSONObject();
                temp.put("Id", carInfo.getId());
                temp.put("BrandCode", carInfo.getBrandCode());
                temp.put("BrandStyle", carInfo.getBrandStyle());
                temp.put("CCode", carInfo.getCCode());
                temp.put("OCode", carInfo.getOCode());
                temp.put("ProductYears", carInfo.getProductYears());
                temp.put("AnnualDate", carInfo.getAnnualDate());
                temp.put("Color", carInfo.getColor());
                temp.put("Kilometers", carInfo.getKilometers());
                temp.put("Remark", carInfo.getRemark());

                final String url = URLAddress.CREATE_OWN_CAR + "?option=" + temp.toJSONString().replaceAll(" ", "");
                Log.i("url", url.toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection conn;
                        InputStream is;
                        try {
                            conn = (HttpURLConnection) new URL(url).openConnection();
                            conn.setRequestMethod("GET");
                            is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            String line = "";
                            StringBuffer result = new StringBuffer();
                            while ((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                            Log.i("temp", result.toString());

                            if (result.toString().contains("True")) {

                                RecordCRUB recordCRUB = new RecordCRUB(CarMessage.this);
                                recordCRUB.saveCarInfo(carInfo);

                                Intent intent = new Intent(CarMessage.this, ShowCarMessage.class);
                                CarMessage.this.setResult(1, intent);
                                finish();

                            } else {
                                handler.sendEmptyMessage(1);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
//                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
