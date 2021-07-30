package com.smart.neet_jeefullpakage;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smart.neet_jeefullpakage.TestSeries.jeeTestSeriesFragments;
import com.smart.neet_jeefullpakage.TestSeries.neetTestSeriesFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class VideosLecture extends AppCompatActivity {
    private final String TAG = "VideosLectures".getClass().getSimpleName();
    TabLayout tabLayout;
    TabItem jee_tab,neet_tab;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_lecture);

        getSupportActionBar().hide();

        tabLayout = findViewById(R.id.tablayout);
        jee_tab = findViewById(R.id.jee_tab);
        neet_tab = findViewById(R.id.neet_tab);
        frameLayout = findViewById(R.id.frame_Layout);

        JeeFragment();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){

                    case 0:
                        JeeFragment();
                        break;

                    case 1:
                        NeetFragment();
                        break;

                    default:
                        Toast.makeText(VideosLecture.this, "Default", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void JeeFragment() {
        jeeTestSeriesFragments jf = new jeeTestSeriesFragments();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_Layout,jf);
        transaction.commit();
    }

    private void NeetFragment() {
        neetTestSeriesFragments nf = new neetTestSeriesFragments();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_Layout, nf);
        transaction.commit();
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

                DatabaseReference reference2 = database.getReference("NativeAds");
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}