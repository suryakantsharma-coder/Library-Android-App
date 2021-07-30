package com.smart.neet_jeefullpakage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class joiningActivity extends AppCompatActivity {
    private final String TAG = "joiningActivity".getClass().getSimpleName();
    Button telegram;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining);

        telegram = findViewById(R.id.joinusTelegram);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAdsPermission();
                checkInterstitialAdsPermission();
            }
        },2000);


        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean Available = appInsatlledorNot("org.telegram.messenger");

                if (Available) {
                    Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                    String TelegramUrl = "https://t.me/competitionprep";
                    telegramIntent.setData(Uri.parse(TelegramUrl));
                    telegramIntent.setPackage("org.telegram.messenger");
                    startActivity(telegramIntent);
                }else {
                    Toast.makeText(joiningActivity.this, "Application not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean appInsatlledorNot(String pakageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(pakageName,PackageManager.GET_ACTIVITIES);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            Log.d("Availablity",""+e.getMessage());
        }
        return false;
    }


    private void checkAdsPermission() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("BannerAds");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean quotalimit = snapshot.getValue(Boolean.class);

                if (quotalimit){
                    //permission from server for ads
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void checkInterstitialAdsPermission() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("InterstitialAds");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean quotalimit = snapshot.getValue(Boolean.class);

                if (quotalimit){
                    //permission from server for ads
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}