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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        phoneNumTV=(TextView)view.findViewById(R.id.phoneNum);

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
                        startActivity(intent);
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
        }

    }
}
