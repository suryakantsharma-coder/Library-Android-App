package com.smart.neet_jeefullpakage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class feedbackActivity extends AppCompatActivity {
    private final String TAG = "feedbackActivity".getClass().getSimpleName();
    EditText msg;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        checkInterstitialAdsPermission();
        checkAdsPermission();

        msg = findViewById(R.id.msg_feedback);
        ok = findViewById(R.id.send_feedback);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInterstitialAdsPermission();
            }
        },2000);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode(" sk1421751@gmail.com") +
                        "?subject=" + Uri.encode("Feedback") +
                        "&body=" + Uri.encode(msg.getText().toString());
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
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




}