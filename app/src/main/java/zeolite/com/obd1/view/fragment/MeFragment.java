package zeolite.com.obd1.view.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zeolite.com.obd1.R;
import zeolite.com.obd1.adapter.me.MeListAdapter;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.Account;
import zeolite.com.obd1.entity.account.CarOwnerInfo;
import zeolite.com.obd1.entity.me.MeListEntity;

import zeolite.com.obd1.entity.record.RecordEntity;
import zeolite.com.obd1.network.URLAddress;
import zeolite.com.obd1.view.login.LoginActivity;
import zeolite.com.obd1.view.map.MapActivity;
import zeolite.com.obd1.view.me.CarMessage;
import zeolite.com.obd1.view.me.CarOwnerMessage;
import zeolite.com.obd1.view.me.ShowCarMessage;
import zeolite.com.obd1.view.me.ShowCarOwnerMessage;


/**
 * Created by Zeolite on 16/1/20.
 */
public class MeFragment extends Fragment {

    private static int REFLASH_PHONE_NUMBER=1;

    private TextView phoneNumTV;

    private ListView meListView;
    private MeListAdapter meListAdapter;
    private List<MeListEntity> meListEntities;

    private CircleImageView circleImageView;
    Intent intent;

    private TextView storeMsgText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        phoneNumTV=(TextView)view.findViewById(R.id.phoneNum);
        storeMsgText=(TextView)view.findViewById(R.id.store_msg);

        Log.i("phoneNumTV","Account.phoneNum:");
        circleImageView=(CircleImageView)view.findViewById(R.id.imageView);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivityForResult(intent,REFLASH_PHONE_NUMBER);
            }
        });

        meListView=(ListView)view.findViewById(R.id.me_list);

        MeListEntity meListEntity=new MeListEntity("message","完善信息");
        MeListEntity meListEntity1=new MeListEntity("message","添加车辆");
        MeListEntity meListEntity2=new MeListEntity("locate","搜索附近维修厂");
        meListEntities=new ArrayList<MeListEntity>();
        meListEntities.add(meListEntity);
        meListEntities.add(meListEntity1);
        meListEntities.add(meListEntity2);

        meListAdapter=new MeListAdapter(getContext(),meListEntities);
        meListView.setAdapter(meListAdapter);


        meListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        intent = new Intent(getActivity(), ShowCarOwnerMessage.class);
                        startActivityForResult(intent,0);
                       break;
                    case 1:
                        intent = new Intent(getActivity(), ShowCarMessage.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), MapActivity.class);
                        startActivityForResult(intent, 2);
                        break;
                }


            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode",requestCode+"");
        switch (requestCode){
            case 1:
                RecordCRUB recordCRUB=new RecordCRUB(getContext());
                Account account=recordCRUB.queryAccount();
                Log.i("accocunt",account.getPhoneNum());
                phoneNumTV.setText(account.getPhoneNum());
                break;
            case 2:
                Log.i("map", "map");

                Bundle bundle=data.getExtras();
                double latitude=bundle.getDouble("latitude");
                double longitude=bundle.getDouble("longitude");

                Log.i("location","latitude"+latitude+"longitude:"+longitude);


                JSONObject temp = new JSONObject();
                temp.put("Id", "1");
                temp.put("Code", "Car1");
                temp.put("Latitude", latitude);
                temp.put("longitude", longitude);
                temp.put("MaxDistance", "30");


                final String url= "http://139.129.117.26:9150/ajaxService/cms/UserHandler.asmx/GetBestStoreGarageInfo?option="+temp.toJSONString();
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



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                break;
        }

    }
}
