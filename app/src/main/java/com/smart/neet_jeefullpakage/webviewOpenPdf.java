package com.smart.neet_jeefullpakage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class webviewOpenPdf extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    String IntentUrl;
    private boolean network_coneected;
    private Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview_open_pdf);

        getSupportActionBar().hide();

        IntentUrl = getIntent().getStringExtra("pdfUrl");

        if (IntentUrl.equals("")){
            IntentUrl = "Sorry no data";
        }

        webView = findViewById(R.id.webView_openPdf);
        retry = findViewById(R.id.reload_testSeries);
        progressBar = findViewById(R.id.progress_webpdf);

        webView.setWebViewClient(new Browser_home());
        webView.setWebChromeClient(new MyChrome());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);



        if(checkNetworkIsOnOrOff()) {

            if (!IntentUrl.endsWith("Sorry no data")) {
                webView.loadUrl(IntentUrl);
                loadWebsite();
            } else {
                getSupportActionBar().show();
                getSupportActionBar().setTitle(IntentUrl);
                progressBar.setVisibility(View.GONE);
            }

        }else{
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Internet Not Connected");
            retry.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                if (progress==100){
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkNetworkIsOnOrOff();
               if (checkNetworkIsOnOrOff()){
                   getSupportActionBar().hide();
                   retry.setVisibility(View.GONE);
               }
            }
        });
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            webView.setVisibility(View.VISIBLE);
        } else {
            webView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "network is not available", Toast.LENGTH_SHORT).show();
        }
    }

    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            setTitle(view.getTitle());
            progressBar.setVisibility(View.GONE);
            checkInterstitialAdsPermission();
            super.onPageFinished(view, url);

        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }



    private void checkInterstitialAdsPermission() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("InterstitialAds_For_Test_Series");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean quotalimit = snapshot.getValue(Boolean.class);

                if (quotalimit){
                    //show ad permission from server
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    //check networks

    public boolean checkNetworkIsOnOrOff(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            network_coneected = true;

            //Load Web page
            if (!IntentUrl.endsWith("Sorry no data")){
                webView.loadUrl(IntentUrl);
            }else{
                getSupportActionBar().show();
                getSupportActionBar().setTitle(IntentUrl);
                progressBar.setVisibility(View.GONE);
            }
        }
        else {
            network_coneected = false;

        }

        return network_coneected;
    }

}