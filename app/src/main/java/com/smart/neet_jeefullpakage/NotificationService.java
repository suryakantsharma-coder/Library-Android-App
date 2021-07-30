package com.smart.neet_jeefullpakage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationService extends AppCompatActivity {
    PDFView pdfView;
    String downloadedFile = "null";
    ArrayList<File> fileList = new ArrayList<File>();
    View decorView;
    Boolean Download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        pdfView = findViewById(R.id.pdfReader);

        checkInterstitialAdsPermission();

        search_value();

        String name = getIntent().getStringExtra("fileName");
        String path  = getIntent().getStringExtra("filepath");
        Download = getIntent().getBooleanExtra("DownloadActivity",false);

        Log.d("pathChecker",path.toString());
        pdfView.fromFile(new File(path)).password(getApplicationContext().getString(R.string.Winner))
                .spacing(14)
                .scrollHandle( new DefaultScrollHandle(this))
                .enableAnnotationRendering(true)// make a fling change only a single page like ViewPager
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages) {
                        pdfView.fitToWidth(pdfView.getCurrentPage());
                    }
                })
                .load();


        Intent intent = new Intent();
        intent.putExtra("pdf","toast");

        setResult(RESULT_OK,intent);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0){
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBar());
        }
    }

    private int hideSystemBar() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    public ArrayList<File> search_value() {

        final String[] titel = {"Chemistry", "Physics", "Maths", "Coaching\nNotes", "dpp", "Previous\nQuestions", "Biology"};

        for (int k = 0; k < titel.length; k++) {

            File file = new File(Environment.getExternalStorageDirectory().toString() + "/IITJEE/" + titel[k] + "/");

            File root[] = file.listFiles();

            if (root != null && root.length > 0) {

                for (int i = 0; i < root.length; i++) {

                    if (root[i].isDirectory()) {
                        search_value();

                    } else {

                        if (root[i].getName().endsWith(".pdf")) {
                            fileList.add(root[i]);
                        }
                    }
                }
            }
        }
            return fileList;
    }

    @Override
    protected void onDestroy() {
//        if (adView != null) {
//            adView.destroy();
//        }
        super.onDestroy();
    }



    private void checkInterstitialAdsPermission() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("InterstitialAds_For_PDF_Activity");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean quotalimit = snapshot.getValue(Boolean.class);

                if (quotalimit){
                    if (!Download) {
                        //permission from server for ads
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}