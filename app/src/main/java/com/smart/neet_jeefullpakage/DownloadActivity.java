package com.smart.neet_jeefullpakage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DownloadActivity extends AppCompatActivity {

    private final String TAG = "DownloadActivity".getClass().getSimpleName();
    private Boolean PERMISSION = false;
    public ArrayList <File> fileList = new ArrayList<>();
    public ArrayList <fileModel> filelist = new ArrayList<>();
    RecyclerView listView;
    TextView nofile;
    chooseAdapterDownload adapter;
    String path;
    String prepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        nofile = findViewById(R.id.download_message);
        listView = findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));


        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        PERMISSION = true;
                        loadData();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        PERMISSION = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        loadData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              checkAdsPermission();
            }
        },2000);

    }

    private void loadData() {
        if (PERMISSION) {
            fileList.clear();
            filelist.clear();
            File isavailable = new File(getApplicationContext().getCacheDir()+"/JEE-NEET/");
            prepath = getApplicationContext().getCacheDir()+"/JEE-NEET/";
            if (isavailable.exists()){
                getFileList(getApplicationContext().getCacheDir()+"/JEE-NEET/");
            }else{
                nofile.setVisibility(View.VISIBLE);
            }
            //listOfAllfiles(getApplicationContext().getCacheDir()  + "/JEE-NEET/");
            adapter = new chooseAdapterDownload(getApplicationContext(),filelist);
            listView.setAdapter(adapter);

            adapter.setonItemClickListener(new chooseAdapterDownload.onItemClickListener() {
                @Override
                public void onItemCLick(int position) {
                    if (filelist.get(position).isFolder) {
                        String name = filelist.get(position).getFile().getName();
                        filelist.clear();
                        getFileList(prepath + "/" + name);
                        prepath = prepath+"/"+name;
                        adapter.notifyDataSetChanged();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                        intent.putExtra("fileName", filelist.get(position).getFile().getName());
                        intent.putExtra("filepath", filelist.get(position).getFile().getPath());
                        startActivity(intent);
                    }
                }

                @Override
                public void onItemRemove(int position) {
                    File file = filelist.get(position).getFile();
                    file.delete();
                    StyleableToast.makeText(getApplicationContext(),"File Deleted",R.style.fullySpactilToast_HOMEACTIVITY).show();
                    filelist.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyDataSetChanged();
                }
            });



        }else{
            checkPermission();
        }
    }

    private void checkPermission() {
        Dexter.withContext(DownloadActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                PERMISSION = true;
                loadData();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                PERMISSION = false;
                checkPermission();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }



    private void listOfAllfiles(String path){

        File file = new File(path);

        File [] flist = file.listFiles();

        for (File f : flist){
            if (f.isFile()){
                fileList.add(new File(f.getAbsolutePath()));
            }else if (f.isDirectory()){
                listOfAllfiles(f.getAbsolutePath());
            }
        }

    }

    public ArrayList<File> search_value() {

        final String[] titel = {"Chemistry", "Physics", "Maths", "Coaching\nNotes", "dpp", "Previous\nQuestions", "Biology"};

            File file = new File(Environment.getExternalStorageDirectory().toString() + "/documents"+"/JEE-NEET/");
            Log.d("pathDIR", file.toString());
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

        return fileList;
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<fileModel> getFileList(String path){
        File file = new File(path);

        File [] flist = file.listFiles();

        for (File f : flist){
            if (f.isFile()){
                filelist.add(new fileModel(new File(f.getAbsolutePath()),false));
            }else if (f.isDirectory()){
                filelist.add(new fileModel(new File(f.getAbsolutePath()),true));
                //listOfAllfiles(f.getAbsolutePath());
            }
        }
        return filelist;
    }

    @Override
    public void onBackPressed() {

        path = getApplicationContext().getCacheDir()+"/JEE-NEET";
        String temppre = prepath.substring(prepath.indexOf("/"), prepath.lastIndexOf("/"));

        if (filelist.size() != 0) {
            if (!temppre.equals(path) && temppre.length() > path.length()) {
                filelist.clear();
                getFileList(temppre);
                prepath = temppre;
                adapter.notifyDataSetChanged();
            } else {
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }

    }

    private void checkAdsPermission() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("BannerAds_For_Download_Activity");
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