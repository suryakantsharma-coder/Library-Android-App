package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
    Boolean network_coneected = false;
    ArrayList<selfDelData> mlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        checkNetworkIsOnOrOff();


    }

    public void checkNetworkIsOnOrOff(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            network_coneected = true;


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    CustomIntent.customType(MainActivity.this,"left-to-right");
                    finish();
                }
            },2000);

        }
        else {
            network_coneected = false;
            networkDialog();

        }

    }

    private void networkDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.offline_dialog,null);

        TextView retry = view.findViewById(R.id.retry);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setView(view);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNetworkIsOnOrOff();
                alertDialog.dismiss();
            }
        });

    }


}