package com.smart.neet_jeefullpakage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DownloadProgress extends AppCompatActivity {
    private final String TAG = "DownloadProgress";
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private TransferUtility transferUtility;
    private String DownloadKey ;
    boolean PERMISSION, isDownloaded, isConnected;
    private InterstitialAd mInterstitialAd;
    private DO d;
    TextView filename, progressNo;
    String DownloadURL, categories,bookname, title,name, chno;
    ProgressBar progressBar;
    LinearLayout linearLayoutSnakebar;
    LinearLayout linearLayout;
    ArrayList<selfDelData> mlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_progress);

        checkNetworkIsOnOrOff();
        checkInterstitialAdsPermission();

        progressBar = findViewById(R.id.prgresssbar_DownloadProgress);
        linearLayout = findViewById(R.id.Linear_downloadProgress);
        linearLayoutSnakebar = findViewById(R.id.snakebarLinearLayout);
        progressNo = findViewById(R.id.persentageShow);
        filename = findViewById(R.id.filename);



        DownloadURL = getIntent().getStringExtra("DownladURL");
        chno = getIntent().getStringExtra("ChapterNo");
        categories = getIntent().getStringExtra("categories");
        title = getIntent().getStringExtra("ChapterName");
        bookname = getIntent().getStringExtra("bookName");


        name  = title+"("+bookname+")";


        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        PERMISSION = true;
                        if (isConnected) {
                            getSystemKeys();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        PERMISSION = false;
                        Toast.makeText(DownloadProgress.this, "Deny", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (DownloadURL != null){
                    Log.d(TAG, DownloadURL);
                    Log.d("SAMMY", DownloadURL);
                }else{
                    Log.d(TAG, "NUll url passed");
                    Log.d("SAMMY", "GONE WRONG");
                }

            }
        },2000);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void openPdf () {
        String filepath = getApplicationContext().getCacheDir() +"/JEE-NEET/"+categories+"/"+bookname+"/"+title+".pdf";
        Log.d("temppre",""+filepath);
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.putExtra("filepath",filepath);
        intent.putExtra("fileName",name);
        intent.putExtra("DownloadActivity", true);
        startActivity(intent);
        finish();
    }

    private void downloadFile() {

        String file = "/JEE-NEET/"+categories+"/"+"/"+bookname+"/"+ title + ".pdf";

        final File localFile = new File(getApplicationContext().getCacheDir() + file);

        credentials = new BasicAWSCredentials(d.getStudent_Name(), d.getStudent_Unique_ID());
        s3Client = new AmazonS3Client(credentials);
        s3Client.setEndpoint("END_POINT");

        TransferNetworkLossHandler.getInstance(getApplicationContext());

        transferUtility = new TransferUtility(s3Client, this);

        String tempsub = categories;
        tempsub = tempsub.replace(" ","_");

        String tempbook = bookname;
        tempbook = tempbook.replace(" ","_");

        DownloadKey = title;

        transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();

        TransferObserver downloadObserver =
                transferUtility.download("jee-nit",tempsub+"/"+tempbook+"/"+DownloadKey, localFile);

        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    isDownloaded = true;
                    StyleableToast.makeText(getApplicationContext(),"Download Completed",R.style.fullySpactilToast_HOMEACTIVITY).show();
                    Snackbar.make(linearLayoutSnakebar,"Please Wait PDF Opening...", 4000).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openPdf();
                        }
                    },4000);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                double progress = (100 * bytesCurrent) / bytesTotal;
                progressBar.setProgress((int) progress);
                progressNo.setText((int) progress+"%");
                filename.setText("Chapter Name : "+title);

                if (bytesCurrent==bytesTotal){
                    filename.setText("Downloaded");
                }
            }

            @Override
            public void onError(int id, Exception ex) {
                ex.printStackTrace();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isDownloaded) {
           // StyleableToast.makeText(getApplicationContext(), "Downloading In Background", R.style.fullySpactilToast_HOMEACTIVITY).show();
        }
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


    private void getSystemKeys () {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("References").document("DO").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot data = task.getResult();
                d = data.toObject(DO.class);
                downloadFile();
            }
        });
    }

    public void checkNetworkIsOnOrOff(){


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                isConnected = true;
        }
        else {
            isConnected = false;
            StyleableToast.makeText(getApplicationContext(), "Please Turn On Network Connection And Try Again....", R.style.fullySpactilToast_HOMEACTIVITY).show();
        }

    }

}