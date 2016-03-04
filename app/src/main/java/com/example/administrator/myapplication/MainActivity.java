package com.example.administrator.myapplication;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cgutech.newbluetoothapi.BluetoothObuCallback;
import com.cgutech.newbluetoothapi.BluetoothObuHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnScan;
    private Button mBtnConnect;
    private BluetoothObuHandler mObuHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnConnect = (Button) findViewById(R.id.btn_scan);
        mBtnScan.setOnClickListener(this);
        mBtnConnect.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private BluetoothObuHandler getObuHandler() {
        if (mObuHandler == null) {
            mObuHandler = BluetoothObuHandler.getInstance();
        }
        return mObuHandler;
    }

    private BluetoothObuCallback mObuCallback = new BluetoothObuCallback() {
        @Override
        public void onConnect() {
            System.out.println("onConnect");
        }

        @Override
        public void onConnectTimeout() {
            System.out.println("onConnectTimeout");
        }

        @Override
        public void onDisconnect() {
            System.out.println("onDisconnect");
        }

        @Override
        public void onReceiveObuCmd(String s, String s1) {
            System.out.println("onReceiveObuCmd------------------");
            System.out.println(s);
            System.out.println(s1);
        }

        @Override
        public void onScanSuccess(BluetoothDevice bluetoothDevice) {
            System.out.println("onScanSuccess");
            System.out.println("name" + bluetoothDevice.getName());
        }

        @Override
        public void onScanTimeout() {
            System.out.println("onScanTimeout");
        }

        @Override
        public void onSendTimeout(String s, String s1) {
            System.out.println("onSendTimeout");
        }

        @Override
        public void onError(String s, String s1) {
            System.out.println("------------onError--------------");
            System.out.println(s);
            System.out.println(s1);
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_scan:
                if (getObuHandler().isBluetoothOpen()) {
                    getObuHandler().initializeObu(this, mObuCallback);
                    getObuHandler().startScan(15000);
                } else {
                    System.out.println("蓝牙不可用");
                }
                break;

            case R.id.btn_connect:
                getObuHandler().stopScan();
                break;

            default:

                break;
        }
    }
}
