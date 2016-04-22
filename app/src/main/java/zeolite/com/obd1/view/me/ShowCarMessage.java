package zeolite.com.obd1.view.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import zeolite.com.obd1.R;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.entity.record.RecordEntity;

/**
 * Created by Zeolite on 16/4/11.
 */
public class ShowCarMessage extends Activity {

    private TextView addText;
    private TextView showCarMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_car_message);

        initView();
        initData();

    }

    private void initData() {

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCarMessage.this, CarMessage.class);
                startActivityForResult(intent, 1);
            }
        });


        RecordCRUB recordCRUB = new RecordCRUB(ShowCarMessage.this);

        CarInfo carInfo = recordCRUB.queryCarInfo();
        if (carInfo == null) {
            carInfo=new CarInfo(0,"","","","","","","","","");
//            recordCRUB.saveCarInfo(carInfo);
        }
//            Log.i("carInfo", carInfo.getbCode());
        showCarMsg.setText("品牌编码:"+carInfo.getBrandCode()+"\n" +
                "型号编码:"+carInfo.getBrandStyle()+"\n" +
                "车架号:"+carInfo.getCCode()+"\n" +
                "用户编码"+carInfo.getOCode()+"\n" +
                "出厂年份"+carInfo.getProductYears()+"\n" +
                "年审日期"+carInfo.getAnnualDate()+"\n" +
                "颜色"+carInfo.getColor()+"\n" +
                "公里数"+carInfo.getColor()+"\n" +
                "备注"+carInfo.getRemark());

    }

    private void initView() {

        addText=(TextView)findViewById(R.id.add_text);
        showCarMsg=(TextView)findViewById(R.id.car_message_show);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                Log.i("res","ff");

                RecordCRUB recordCRUB=new RecordCRUB(ShowCarMessage.this);


                CarInfo carInfo=recordCRUB.queryCarInfo();
//                Log.i("carInfo",carInfo.getBrandCode());
                if(carInfo==null){
                    carInfo=new CarInfo(1,"","","","","","","","","");
                }
                showCarMsg.setText("品牌编码:"+carInfo.getBrandCode()+"\n" +
                        "型号编码:"+carInfo.getBrandStyle()+"\n" +
                        "车架号:"+carInfo.getCCode()+"\n" +
                        "用户编码"+carInfo.getOCode()+"\n" +
                        "出厂年份"+carInfo.getProductYears()+"\n" +
                        "年审日期"+carInfo.getAnnualDate()+"\n" +
                        "颜色"+carInfo.getColor()+"\n" +
                        "公里数"+carInfo.getColor()+"\n" +
                        "备注"+carInfo.getRemark());


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
