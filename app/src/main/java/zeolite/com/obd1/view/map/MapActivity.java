package zeolite.com.obd1.view.map;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import zeolite.com.obd1.R;

public class MapActivity extends Activity {

    private BMapManager mBMapManager;
    private com.baidu.mapapi.map.MapView mapView = null;
    private MapController mMapController = null;
    MKMapViewListener mMapListener = null;
    private MKSearch mMKSearch;

    private EditText keyWordEditText;
    private EditText cityEditText;
    private Button queryButton;
    private static StringBuilder sb;
    private MyLocationOverlay myLocationOverlay;

    private Button button1;
    private LocationManager locationManager;
    private String provider;

    private BDLocation myLocation;
    private LocationData mLocData;
    private LocationClient mLocClient;
    private MyLocationOverlay locationOverlay = null;
    private PopupOverlay pop;
    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBMapManager = new BMapManager(getApplication());
        mBMapManager.init(null);
        setContentView(R.layout.mao_layout);

        mapView = (com.baidu.mapapi.map.MapView) findViewById(R.id.map_view);
        cityEditText = (EditText) findViewById(R.id.city_edittext);
        keyWordEditText = (EditText) findViewById(R.id.keyword_edittext);
        queryButton = (Button) findViewById(R.id.query_button);
        button1 = (Button) findViewById(R.id.button1);
        Log.i("mapView", String.valueOf(mapView));

        mMapController = mapView.getController();
        mMapController.enableClick(true);
        mMapController.setZoom(16);
        mapView.setBuiltInZoomControls(true);


        mMKSearch = new MKSearch();
        mMKSearch.init(mBMapManager, new MySearchListener());
        queryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1) {
                    pop.hidePop();
                    flag = 2;
                }
                mMapController = mapView.getController();
                mMapController.setZoom(10);
                sb = new StringBuilder();  //内容清空

                String city = cityEditText.getText().toString().trim();
                String keyWord = keyWordEditText.getText().toString().trim();
                if(city.isEmpty()) {
                    city="珠海";
                }

                if(keyWord.isEmpty()) {
                    mMKSearch.geocode(city, city);
                }
                else {

                    mMKSearch.setPoiPageCapacity(10);
                    mMKSearch.poiSearchInCity(city, keyWord);
                }
            }
        });


        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                MapController controller = mapView.getController();
                controller.setZoom(16);

                mLocClient = new LocationClient(getApplicationContext());
                mLocClient.registerLocationListener(new BDLocationListenerImpl());

                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true);
                option.setAddrType("all");
                option.setCoorType("bd09ll");
                option.setPriority(LocationClientOption.GpsFirst);
                option.setScanSpan(5000);
                option.disableCache(false);
                mLocClient.setLocOption(option);
                mLocClient.start();

                mapView.getOverlays().clear();
                locationOverlay= new MyLocationOverlay(mapView);

                mLocData = new LocationData();
                locationOverlay.setData(mLocData);

                mapView.getOverlays().add(locationOverlay);

                mapView.refresh();
            }
        });
    }


    public class BDLocationListenerImpl implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || flag != 1) {
                return;
            }
            MapController controller = mapView.getController();
            //设置经纬度
            MapActivity.this.myLocation = location;
            mLocData.latitude = location.getLatitude();
            mLocData.longitude = location.getLongitude();
            GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
                    (int) (location.getLongitude() * 1E6));
            controller.setCenter(point);

            mLocData.direction = location.getDerect();
            mLocData.accuracy = 0;

            locationOverlay.setData(mLocData);

            if(flag==1) {

                pop = new PopupOverlay(mapView, new PopupClickListener() {
                    @Override
                    public void onClickedPopup(int index) {
                    }
                });
//                Bitmap[] bitmaps = new Bitmap[3];
//                try {
//                    bitmaps[0] = BitmapFactory.decodeResource(getResources(),
//                            R.drawable.left);
//                    bitmaps[1] = BitmapFactory.decodeResource(getResources(),
//                            R.drawable.middle);
//                    bitmaps[2] = BitmapFactory.decodeResource(getResources(),
//                            R.drawable.right);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                pop.showPopup(bitmaps, point, 18);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.middle);
//                pop.showPopup(bitmap,point,18);
            }
        }


        @Override
        public void onReceivePoi(BDLocation poiLocation) {

        }
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        if (mBMapManager != null) {
            mBMapManager.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.destroy();
        if (mBMapManager != null) {
            mBMapManager.destroy();
            mBMapManager = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        if (mBMapManager != null) {
            mBMapManager.stop();
        }
        super.onPause();
    }


    public class MySearchListener implements MKSearchListener {


        @Override
        public void onGetAddrResult(MKAddrInfo result, int iError) {
            if (result == null) {
                return;
            }
            StringBuffer sbcity = new StringBuffer();
            sbcity.append(result.strAddr).append("\n");
            mapView.getOverlays().clear();
            mMapController.setCenter(result.geoPt);

            LocationData locationData = new LocationData();
            locationData.latitude = result.geoPt.getLatitudeE6();
            locationData.longitude = result.geoPt.getLongitudeE6();
            myLocationOverlay = new MyLocationOverlay(mapView);
            myLocationOverlay.setData(locationData);
            mapView.getOverlays().add(myLocationOverlay);
            mapView.refresh();

            new AlertDialog.Builder(MapActivity.this)
                    .setTitle("显示当前城市地图")
                    .setMessage(sbcity.toString())
                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }


        @Override
        public void onGetPoiResult(MKPoiResult result, int type, int iError) {
            if (result == null) {
                return;
            }

            mapView.getOverlays().clear();
            PoiOverlay poioverlay = new PoiOverlay(MapActivity.this, mapView);
            poioverlay.setData(result.getAllPoi());
            mapView.getOverlays().add(poioverlay);
            mapView.refresh();

            if(result.getNumPois() > 0) {
                MKPoiInfo poiInfo = result.getPoi(0);
                mMapController.setCenter(poiInfo.pt);
            }

            sb.append("共搜索到").append(result.getNumPois()).append("个\n");
            for (MKPoiInfo poiInfo : result.getAllPoi()) {
                sb.append("名称：").append(poiInfo.name).append("\n");
            }
            // 通过AlertDialog显示当前页搜索到的POI
            new AlertDialog.Builder(MapActivity.this)
                    .setTitle("搜索到的信息")
                    .setMessage(sb.toString())
                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }


        @Override
        public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {
        }


        @Override
        public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {
        }


        @Override
        public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {
        }

        @Override
        public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onGetPoiDetailSearchResult(int arg0, int arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
            // TODO Auto-generated method stub
        }
    }

}
