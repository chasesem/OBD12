package zeolite.com.obd1.view.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
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
import zeolite.com.obd1.entity.account.Account;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.network.URLAddress;
import zeolite.com.obd1.view.bluetooth.OBD2MonitorService;
import zeolite.com.obd1.view.fragment.MeFragment;
import zeolite.com.obd1.view.fragment.RecordFragment;

/**
 * Created by Zeolite on 16/3/2.
 */
public class CarMessage extends Activity implements View.OnClickListener {


    private EditText codeET;
    private EditText bCodeET;
    private EditText productYearET;
    private EditText annualDateET;
    private EditText colorET;
    private EditText remarkET;
    private Button saveBtn;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(CarMessage.this,"error",Toast.LENGTH_SHORT).show();
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
    }

    private void initView() {
        codeET=(EditText)findViewById(R.id.et_code);
        bCodeET=(EditText)findViewById(R.id.et_bCode);
        productYearET=(EditText)findViewById(R.id.et_productYear);
        annualDateET=(EditText)findViewById(R.id.et_productYear);
        colorET=(EditText)findViewById(R.id.et_color);
        remarkET=(EditText)findViewById(R.id.et_remark);
        saveBtn=(Button)findViewById(R.id.save_car_message_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_car_message_btn:


                int i=0;
                ++i;
                final CarInfo carInfo=new CarInfo(i,codeET.getText().toString(),bCodeET.getText().toString(),productYearET.getText().toString(),annualDateET.getText().toString(),
                        colorET.getText().toString(),remarkET.getText().toString());

                JSONObject temp = new JSONObject();
                temp.put("Id", carInfo.getId());
                temp.put("Code", carInfo.getCode());
                temp.put("bCode", carInfo.getbCode());
                temp.put("productYear", carInfo.getProductYear());
                temp.put("annualDate", carInfo.getAnnualDate());
                temp.put("color", carInfo.getColor());
                temp.put("remark", carInfo.getRemark());

                final String url= URLAddress.CREATE_OWN_CAR+"?option="+temp.toJSONString();
                Log.i("url",url.toString());

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
                            Log.i("temp",result.toString());

                            if (result.toString().contains("True")){

                                RecordCRUB recordCRUB=new RecordCRUB(CarMessage.this);
                                recordCRUB.saveCarInfo(carInfo);

                                Intent intent=new Intent(CarMessage.this,ShowCarMessage.class);
                                CarMessage.this.setResult(1, intent);
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
