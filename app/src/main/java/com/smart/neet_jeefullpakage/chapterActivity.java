package com.smart.neet_jeefullpakage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class chapterActivity extends AppCompatActivity {
    RecyclerView recyclerView, DownloadedRecyclerView;
    ProgressBar progressBar;
    ImageView Ddropicon, UDdropicon;
    String categories,bookname;
    LinearLayout download,Undownload;
    LinearLayout downloadLinear,UnDownloadLinear;
    List<String> listName  = new ArrayList<>();
    ArrayList<File> fileList = new ArrayList<>();
    List<serverChapterDataset> DownloadList  = new ArrayList<>();
    List<serverChapterDataset> UnDownoaldedchapter  = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean boolean_permission;
    boolean PERMISSION;
    int permission = 10;
    chapterAdapter adapter;
    chapterAdapter2 adapter2;
    SwipeRefreshLayout swipeRefreshLayout;
    Boolean quotalimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        PERMISSION = true;
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



        Ddropicon = findViewById(R.id.dropiconDowload_chapterActivity);
        UDdropicon = findViewById(R.id.dropiconUNDowload_chapterActivity);
        recyclerView = findViewById(R.id.recyclerview_chapterActivity);
        downloadLinear = findViewById(R.id.DownloadSection_LinearLayout);
        UnDownloadLinear = findViewById(R.id.unDownloadSection_LinearLayout);
        DownloadedRecyclerView = findViewById(R.id.recyclerviewDownload_chapterActivity);
        DownloadedRecyclerView.setHasFixedSize(true);
        DownloadedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar_chapterActivity);
        swipeRefreshLayout = findViewById(R.id.swipeTOReferesh_chapterActivity);
        download = findViewById(R.id.DownloadSection);
        Undownload = findViewById(R.id.unDownloadSection);
        recyclerView.setVisibility(View.GONE);
        categories = getIntent().getStringExtra("categories");
        bookname = getIntent().getStringExtra("bookName");

        getSupportActionBar().setTitle(bookname);

       checkAdsPermission();

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DownloadedRecyclerView.getVisibility() == View.VISIBLE) {
                    DownloadedRecyclerView.setVisibility(View.GONE);
                    Picasso.get().load(R.drawable.arrow).rotate(180).into(Ddropicon);
                    if (DownloadList.size()<=0){
                        StyleableToast.makeText(getApplicationContext(),"You are not downloading any chapter yet",R.style.fullySpactilToast).show();
                    }
                }else{
                    DownloadedRecyclerView.setVisibility(View.VISIBLE);
                    Picasso.get().load(R.drawable.arrow).rotate(0).into(Ddropicon);
                }

            }
        });
        Undownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    Picasso.get().load(R.drawable.arrow).rotate(180).into(UDdropicon);
                    if (UnDownoaldedchapter.size()<=0 && DownloadList.size()>0){
                        StyleableToast.makeText(getApplicationContext(),"You Already Downloaded All Chapters Of "+bookname.toUpperCase(),R.style.fullySpactilToast).show();
                    }else if (UnDownoaldedchapter.size()<=0){
                        StyleableToast.makeText(getApplicationContext(),"No Data Available Here", R.style.fullySpactilToast);
                    }


                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    Picasso.get().load(R.drawable.arrow).rotate(0).into(UDdropicon);


                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchData();
                downloaded();
                shortFileList();
                filterData(fileList,listName);
                fixEveryThing();
                //update();
                DownloadedPDFS();
                swipeRefreshLayout.setColorScheme(android.R.color.holo_red_dark,android.R.color.darker_gray,android.R.color.holo_green_dark,android.R.color.holo_purple);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });

    }

    private void FetchData () {
        UnDownoaldedchapter.clear();
        listName.clear();
        DownloadList.clear();
        db.collection(categories).document(bookname).collection("Chapters").orderBy("timeStamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot data : task.getResult()){
                    listName.add(data.getId());
                    chapterDataset dataset = data.toObject(chapterDataset.class);
                    UnDownoaldedchapter.add(new serverChapterDataset(data.getId(),dataset.getChapterNo(),dataset.getTimeStamp(),dataset.getUrl()));
                }
                Log.d("size",""+UnDownoaldedchapter.size());
                downloaded();
                shortFileList();
                progressBar.setVisibility(View.GONE);
                filterData(fileList,listName);

                fixEveryThing();
                for (File f : fileList){
                    Log.d("check_fileList",""+f.getName());
                }

                DownloadedPDFS();
                adapter = new chapterAdapter(getApplicationContext(),listName,fileList,UnDownoaldedchapter);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new chapterAdapter.onCLickListener() {
                    @Override
                    public void onPdfView(int position) {

                         if (quotalimit){
                            QuotaDialog(chapterActivity.this);
                        }

                        if (PERMISSION && !quotalimit) {
                            Intent intent = new Intent(getApplicationContext(),DownloadProgress.class);
                            intent.putExtra("DownladURL", UnDownoaldedchapter.get(position).getUrl());
                            try {
                                Log.d("SAMMY", UnDownoaldedchapter.get(position).getUrl());
                            }
                            catch(Exception e)
                            {
                                Log.d("SAMMY", e.toString());
                            }
                            intent.putExtra("ChapterNo", ""+UnDownoaldedchapter.get(position).getChapterNo());
                            intent.putExtra("categories", categories);
                            intent.putExtra("bookName", bookname);
                            intent.putExtra("ChapterName", UnDownoaldedchapter.get(position).getName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            UnDownoaldedchapter.remove(position);
                            adapter.notifyItemRemoved(position);
                        }else{
                            Dexter.withContext(chapterActivity.this)
                                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                            PERMISSION = true;
                                        }

                                        @Override
                                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                            PERMISSION = false;
                                            Toast.makeText(chapterActivity.this, "Please Allow Permission To Download File", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                            permissionToken.continuePermissionRequest();
                                        }
                                    }).check();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chapterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DownloadedPDFS(){
        adapter2 = new chapterAdapter2(getApplicationContext(),listName,fileList,DownloadList);
        DownloadedRecyclerView.setAdapter(adapter2);

        if (DownloadList.size()>0){
            DownloadedRecyclerView.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.arrow).rotate(180).into(Ddropicon);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.arrow).rotate(180).into(UDdropicon);
        }

    }


    public List<File> downloaded (){
        File file = new File(getApplicationContext().getCacheDir() + "/JEE-NEET/"+ categories +"/"+bookname+"/");
        if (file.exists()){
            File [] flist = file.listFiles();
            for (File f : flist){
                if (f.isFile()){
                    fileList.add(f);
                }
            }
        }
        return fileList;
    }

    private void shortFileList () {
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file, File t1) {
                return file.getName().compareTo(t1.getName());
            }
        });

        Collections.sort(UnDownoaldedchapter, new Comparator<serverChapterDataset>() {
            @Override
            public int compare(serverChapterDataset serverChapterDataset, serverChapterDataset t1) {
                return serverChapterDataset.getName().compareTo(t1.getName());
            }
        });

    }

    private void fixEveryThing () {
        Collections.sort(UnDownoaldedchapter, new Comparator<serverChapterDataset>() {
            @Override
            public int compare(serverChapterDataset serverChapterDataset, serverChapterDataset t1) {
                return serverChapterDataset.getTimeStemp().compareTo(t1.getTimeStemp());
            }
        });

        for(serverChapterDataset d : UnDownoaldedchapter){
            Log.d("check_Undownload",""+d.getName());
        }

        Collections.sort(DownloadList, new Comparator<serverChapterDataset>() {
            @Override
            public int compare(serverChapterDataset serverChapterDataset, serverChapterDataset t1) {
                return serverChapterDataset.getTimeStemp().compareTo(t1.getTimeStemp());
            }
        });
    }


    private List<serverChapterDataset> filterData(List<File> fileList, List<String> names){
        if (UnDownoaldedchapter.size() == listName.size()) {
            try {
                for (int i=0; i<UnDownoaldedchapter.size(); i++){
                    for (int j=0;j<fileList.size();j++) {
                        if (UnDownoaldedchapter.size()>=i) {
                            if (fileList.get(j).getName().toLowerCase().equals(UnDownoaldedchapter.get(i).getName().toLowerCase()+".pdf")) {
                                DownloadList.add(new serverChapterDataset(UnDownoaldedchapter.get(i).getName(), UnDownoaldedchapter.get(i).getChapterNo(),UnDownoaldedchapter.get(i).getTimeStemp(), fileList.get(j).getPath()));
                                UnDownoaldedchapter.remove(i);
                            }
                        }

                    }
                }


            }catch (Exception ex){
                Log.d("ERROR",""+ex.getMessage());
            }

        }
        return UnDownoaldedchapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                adapter2.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permission) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true;
            }
        } else {
            Toast.makeText(this, "Please Allow Permissions", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        FetchData();
        downloaded();
        shortFileList();
        fixEveryThing();
        DownloadedPDFS();
        checkQuota();
        super.onResume();
    }



    private void checkQuota() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Daily Quota");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quotalimit = snapshot.getValue(boolean.class);

                if (quotalimit){
                    QuotaDialog(chapterActivity.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void QuotaDialog(Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View view  = getLayoutInflater().inflate(R.layout.quota_layout, null);
        TextView ok = view.findViewById(R.id.cancel_Quota_layout);
        AlertDialog alertDialog = alert.create();
        alertDialog.setView(view);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
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
                   // Show Adds From Server Side
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
