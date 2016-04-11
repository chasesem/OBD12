package zeolite.com.obd1.view.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import zeolite.com.obd1.R;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.entity.account.CarOwnerInfo;
import zeolite.com.obd1.network.URLAddress;

/**
 * Created by Zeolite on 16/4/10.
 */
public class CarOwnerMessage extends Activity implements View.OnClickListener{


    private EditText userCodeET;
    private EditText userNameET;
    private EditText sexET;
    private EditText phoneET;
    private EditText telephoneET;
    private EditText provinceET;
    private EditText cityET;
    private EditText areaET;
    private EditText streetET;
    private Button saveBtn;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(CarOwnerMessage.this, "error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_owner_message);

        initView();
        initDate();
    }

    private void initDate() {
        saveBtn.setOnClickListener(this);
    }

    private void initView() {
        userCodeET=(EditText)findViewById(R.id.et_user_code);
        userNameET=(EditText)findViewById(R.id.et_user_name);
        sexET=(EditText)findViewById(R.id.et_sex);
        phoneET=(EditText)findViewById(R.id.et_phone);
        telephoneET=(EditText)findViewById(R.id.et_telePhone);
        provinceET=(EditText)findViewById(R.id.et_province);
        cityET=(EditText)findViewById(R.id.et_city);
        areaET=(EditText)findViewById(R.id.et_area);
        streetET=(EditText)findViewById(R.id.et_street);
        saveBtn=(Button)findViewById(R.id.save_car_owner_message_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_car_owner_message_btn:

                final CarOwnerInfo carOwnerInfo=new CarOwnerInfo(1,userCodeET.getText().toString(),userNameET.getText().toString(),sexET.getText().toString(),phoneET.getText().toString(),
                        telephoneET.getText().toString(),provinceET.getText().toString(),cityET.getText().toString(),areaET.getText().toString(),streetET.getText().toString());

                JSONObject temp = new JSONObject();
                temp.put("Id", carOwnerInfo.getId());
                temp.put("userCode", carOwnerInfo.getUserCode());
                temp.put("userName", carOwnerInfo.getUserName());
                temp.put("sex", carOwnerInfo.getSex());
                temp.put("phone", carOwnerInfo.getPhone());
                temp.put("telephone", carOwnerInfo.getTelephone());
                temp.put("province", carOwnerInfo.getProvince());
                temp.put("city", carOwnerInfo.getCity());
                temp.put("area", carOwnerInfo.getArea());
                temp.put("street", carOwnerInfo.getStreet());

                final String url= URLAddress.CREATE_OWN_CAR+"?option="+temp.toJSONString();
                Log.i("url",url);

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
                            while ( (line = reader.readLine()) != null ){
                                result.append(line);
                            }
                            Log.i("temp", result.toString());

                            if (result.toString().contains("True")){

                                RecordCRUB recordCRUB=new RecordCRUB(CarOwnerMessage.this);
                                recordCRUB.saveCarOwnerInfo(carOwnerInfo);

                                Intent intent=new Intent(CarOwnerMessage.this,ShowCarMessage.class);
                                CarOwnerMessage.this.setResult(1, intent);
                                finish();

                            }else {
                                handler.sendEmptyMessage(1);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
        }
    }
}
