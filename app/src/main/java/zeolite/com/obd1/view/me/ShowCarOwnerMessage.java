package zeolite.com.obd1.view.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import zeolite.com.obd1.R;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.entity.account.CarOwnerInfo;

/**
 * Created by Zeolite on 16/4/11.
 */
public class ShowCarOwnerMessage extends Activity{
    private TextView addText;
    private TextView showCarOwnerMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_car_owner_msg);

        initView();
        initData();

    }

    private void initData() {

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowCarOwnerMessage.this,CarOwnerMessage.class);
                startActivityForResult(intent, 1);
            }
        });

        RecordCRUB recordCRUB=new RecordCRUB(ShowCarOwnerMessage.this);

        CarOwnerInfo carOwnerInfo=recordCRUB.queryCarOwnerInfo();
        if (carOwnerInfo==null){
            carOwnerInfo=new CarOwnerInfo(0,"","","","","","","","","");
        }
//                Log.i("carInfo",carInfo.getbCode());
        showCarOwnerMsg.setText("车主编码:" + carOwnerInfo.getUserCode() + "\n" +
                "车主姓名:" + carOwnerInfo.getUserName() + "\n" +
                "性别" + carOwnerInfo.getSex() + "\n" +
                "手机" + carOwnerInfo.getPhone() + "\n" +
                "家庭电话" + carOwnerInfo.getTelephone() + "\n" +
                "省份" + carOwnerInfo.getProvince() + "\n" +
                "市" + carOwnerInfo.getCity() + "\n" +
                "区" + carOwnerInfo.getArea() + "\n" +
                "街道地址" + carOwnerInfo.getStreet());

    }

    private void initView() {

        addText=(TextView)findViewById(R.id.add_own_msg_text);
        showCarOwnerMsg=(TextView)findViewById(R.id.car_owner_show);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                Log.i("res", "ff");

                RecordCRUB recordCRUB=new RecordCRUB(ShowCarOwnerMessage.this);

                CarOwnerInfo carOwnerInfo=recordCRUB.queryCarOwnerInfo();
//                Log.i("carInfo",carInfo.getbCode());
                showCarOwnerMsg.setText("车主编码:"+carOwnerInfo.getUserCode()+"\n" +
                        "车主姓名:"+carOwnerInfo.getUserName()+"\n" +
                        "性别"+carOwnerInfo.getSex()+"\n" +
                        "手机"+carOwnerInfo.getPhone()+"\n" +
                        "家庭电话"+carOwnerInfo.getTelephone()+"\n" +
                        "省份"+carOwnerInfo.getProvince()+"\n" +
                        "市"+carOwnerInfo.getCity()+"\n" +
                        "区"+carOwnerInfo.getArea()+"\n" +
                        "街道地址"+carOwnerInfo.getStreet());


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
