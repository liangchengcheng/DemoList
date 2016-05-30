package com.lccc.satellite;

import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv_gps, tv_satellites;
    LocationManager locationManager;
    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_next_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        tv_gps= (TextView) findViewById(R.id.tv_gps);
        tv_satellites= (TextView) findViewById(R.id.tv_satellites);

        openGPSSettings();
        locationManager = (LocationManager)MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;

        LocationListener ll = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                String locInfo = updateMsg(location);
                tv_gps.setText(null);
                tv_gps.setText(locInfo);
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.e("lcc","onStatusChanged"+provider+"status:"+status);
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.e("lcc","onProviderDisabled"+provider);
            }

        };

        locationManager.requestLocationUpdates(provider, 1000, 1, ll);
        locationManager.addGpsStatusListener(statusListener);
    }

    private String updateMsg(Location loc) {
        sb = null;
        sb = new StringBuilder("位置信息：\n");
        if (loc != null) {
            double lat = loc.getLatitude();
            double lng = loc.getLongitude();

            sb.append("纬度：" + lat + "\n经度：" + lng);

            if (loc.hasAccuracy()) {
                sb.append("\n精度：" + loc.getAccuracy());
            }

            if (loc.hasAltitude()) {
                sb.append("\n海拔：" + loc.getAltitude() + "m");
            }

            if (loc.hasBearing()) {// 偏离正北方向的角度
                sb.append("\n方向：" + loc.getBearing());
            }

            if (loc.hasSpeed()) {
                if (loc.getSpeed() * 3.6 < 5) {
                    sb.append("\n速度：0.0km/h");
                } else {
                    sb.append("\n速度：" + loc.getSpeed() * 3.6 + "km/h");
                }

            }
        } else {
            sb.append("没有位置信息！");
        }

        return sb.toString();
    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0);
    }


    /**
     * 卫星状态监听器
     */
    private List<GpsSatellite> numSatelliteList = new ArrayList<GpsSatellite>();

    private final GpsStatus.Listener statusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            // GPS状态变化时的回调，如卫星数
            LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
            //取当前状态
            GpsStatus status = locationManager.getGpsStatus(null);
            String satelliteInfo = updateGpsStatus(event, status);
            tv_satellites.setText(null);
            tv_satellites.setText(satelliteInfo);
        }
    };

    private String updateGpsStatus(int event, GpsStatus status) {
        StringBuilder sb2 = new StringBuilder("");
        if (status == null) {
            sb2.append("搜索到卫星个数：" +0);
        } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            numSatelliteList.clear();
            int count = 0;
            while (it.hasNext() && count <= maxSatellites) {
                GpsSatellite s = it.next();
                numSatelliteList.add(s);
                count++;
            }
            sb2.append("搜索到卫星个数：" + numSatelliteList.size());
        }

        return sb2.toString();
    }

}
