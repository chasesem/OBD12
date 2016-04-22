package zeolite.com.obd1.view.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import android.annotation.TargetApi;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import zeolite.com.obd1.R;
import zeolite.com.obd1.adapter.record.CardsAdapter;
import zeolite.com.obd1.db.RecordCRUB;
import zeolite.com.obd1.entity.account.CarInfo;
import zeolite.com.obd1.entity.record.RecordEntity;
import zeolite.com.obd1.network.URLAddress;
import zeolite.com.obd1.view.bluetooth.OBD2MonitorService;
import zeolite.com.obd1.view.me.ShowCarMessage;
import zeolite.com.obd1.view.record.AddRecordActivity;


/**
 * Created by Zeolite on 16/1/21.
 */
public class RecordFragment extends Fragment {

    private ListView cardsList;

    private Button addRecordBtn;

    private TextView maintainText;

    private CardsAdapter cardsAdapter;

    private ArrayList<String> items;

    private String maintain;

    private final android.os.Handler mMsgHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    maintainText.setText(maintain);
                    break;
            }
        }
    };


    public RecordFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_card_layout,container,false);

        maintainText=(TextView)rootView.findViewById(R.id.maintain_text);

        maintain="保养计划：\n暂无";
        maintainText.setText(maintain);
        cardsList=(ListView)rootView.findViewById(R.id.cards_list);
        setupList();

        addRecordBtn=(Button)rootView.findViewById(R.id.add_record_btn);

        addRecordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddRecordActivity.class);
                startActivityForResult(intent,1);
            }
        });


        final String maintianURL=URLAddress.MaintenLogUpload+"?option=\"JF1BM95E7BG021997\"";
        Log.i("maintianURL", maintianURL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn;
                InputStream is;
                try {
                    conn = (HttpURLConnection) new URL(maintianURL).openConnection();
                    conn.setRequestMethod("GET");
                    is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    StringBuffer result = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    Log.i("temp", result.toString());
                    maintain=result.toString();
                    if (maintain.contains("True")) {
                        mMsgHandler.sendEmptyMessage(1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return rootView;
    }

    private void setupList() {
        cardsList.setAdapter(createAdapter());
        cardsList.setOnItemClickListener(new ListItemClickListener());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private CardsAdapter createAdapter() {

        //sqlite data
        RecordCRUB recordCRUB=new RecordCRUB(getContext());
        List<RecordEntity> recordEntities=recordCRUB.findAllRecord();
        items=new ArrayList<String>();

        for(int i=0;i<recordEntities.size();i++){
            RecordEntity recordEntity=recordEntities.get(i);
//            Log.i("recordEntity",recordEntity.getFixitem()+"//"+recordEntity.getFixtype()+"//"+recordEntity.getCost());
//            String fixItems=recordEntity.getFixitem().substring(0,recordEntity.getFixitem().length()-1);

            items.add(recordEntity.getFixtype());
        }

        cardsAdapter=new CardsAdapter(getActivity(),items,new ListItemButtonClickListener());
        return cardsAdapter;
    }


    private final class ListItemButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            for (int i = cardsList.getFirstVisiblePosition(); i <= cardsList
                    .getLastVisiblePosition(); i++) {
                if (v == cardsList.getChildAt(i - cardsList.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_1)) {

                } else if (v == cardsList.getChildAt(i - cardsList.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_2)) {

                }
            }
        }
    }

    private final class ListItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(getActivity(), "Clicked on List Item " + position,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:

                RecordCRUB recordCRUB=new RecordCRUB(getContext());
                List<RecordEntity> recordEntities=recordCRUB.findAllRecord();
                items.clear();
                for(int i=0;i<recordEntities.size();i++){
                    RecordEntity recordEntity=recordEntities.get(i);
//                    Log.i("accocunt",recordEntity.getFixtype());
                    items.add(recordEntity.getFixtype());


                    CarInfo carInfo=new CarInfo();

                    JSONObject temp = new JSONObject();
                    temp.put("CCode", carInfo.getCCode());
                    temp.put("CopCode", recordEntity.getFixitem());
                    temp.put("MaintainType", recordEntity.getFixtype());
                    temp.put("Date", recordEntity.getTime());
                    temp.put("Kilometer", recordEntity.getCurrentmeil());
                    temp.put("Unit", "");
                    temp.put("Amount", "1");


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


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }

                cardsAdapter.notifyDataSetChanged();




                break;
        }
    }
}


