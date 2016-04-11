package zeolite.com.obd1.view.register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.okhttp.FormEncodingBuilder;
import com.avos.avoscloud.okhttp.OkHttpClient;
import com.avos.avoscloud.okhttp.Request;
import com.avos.avoscloud.okhttp.RequestBody;
import com.avos.avoscloud.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import zeolite.com.obd1.MainActivity;
import zeolite.com.obd1.R;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.Account;
import zeolite.com.obd1.network.URLAddress;
import zeolite.com.obd1.view.fragment.MeFragment;
import zeolite.com.obd1.view.login.LoginActivity;

/**
 * Created by Zeolite on 16/1/11.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Handler mHandler;
    private EditText userNameET;
    private EditText passwordET;
    private EditText repectPasswordET;
    private Button registerBtn;
    private String sUserName;
    private String sPassWord;
    private final int MSG_USERNAME = 0;
    private final int MSG_USERPWD = 1;
    private final int RESULT = 2;
    boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        initView();

    }



    private void initView() {
        userNameET = (EditText) findViewById(R.id.et_userName);
        passwordET = (EditText) findViewById(R.id.et_password);
        repectPasswordET = (EditText) findViewById(R.id.et_repect_password);
        registerBtn = (Button) findViewById(R.id.btn_register);

        registerBtn.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_register:

                sUserName = userNameET.getText().toString();
                sPassWord =passwordET.getText().toString();
                String repectPassword = repectPasswordET.getText().toString();

                if(!sPassWord.equals(repectPassword)){
                    Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }

                JSONObject registerJson = new JSONObject();
                registerJson.put("Id", "123");
                registerJson.put("Code", "test");
                registerJson.put("UserName", sUserName);
                registerJson.put("PassWord", sPassWord);
                registerJson.put("RepectPassword", repectPassword);

                final String url =URLAddress.USER_REGISTER+"?option="+registerJson.toJSONString();

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

                            finish();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
        }
    }


}