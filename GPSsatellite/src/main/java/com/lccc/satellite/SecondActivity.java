package com.lccc.satellite;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  SecondActivity
 */
public class SecondActivity extends Activity {

    private LocationListener listener;
    private LocationManager locationManager;
    private int mSatelliteNum, point_Number;
    private double mLatitude, mLongitude;

    private DecimalFormat df = new DecimalFormat("#.########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    getLocation(location);
                }
            }
        };
        startService();
    }

    private synchronized void getLocation(Location location) {
        double Latitude, Longitude;
        Latitude = location.getLatitude();
        Longitude = location.getLongitude();

        if (Latitude > 0 || Longitude > 0) {
            mLatitude = Latitude;
            mLongitude = Longitude;
        }
    }

    public void startService() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 15, listener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.addGpsStatusListener(gpslistener);
    }

    GpsStatus.Listener gpslistener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            List<GpsSatellite> numSatelliteList = new ArrayList<GpsSatellite>();
            GpsStatus status = locationManager.getGpsStatus(null);
            if (status == null) {
                // 卫星状态改变
            } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                // 获取最大的卫星数（这个只是一个预设值）
                Iterator<GpsSatellite> it = status.getSatellites().iterator();

                numSatelliteList.clear();
                // 记录实际的卫星数目
                while (it.hasNext()) {
                    // 保存卫星的数据到一个队列，用于刷新界面
                    GpsSatellite s = it.next();
                    if (s.usedInFix()) {// 正在使用的卫星数量
                        numSatelliteList.add(s);
                    }

                }
                mSatelliteNum = numSatelliteList.size();
                Log.e("lcc","卫星"+mSatelliteNum);
            }
        };
    };

}
