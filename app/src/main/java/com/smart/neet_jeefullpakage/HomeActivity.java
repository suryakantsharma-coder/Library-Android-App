package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.policy.Resource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView, importantrecyclerview;
    List<importantDataset> list = new ArrayList<>();
    List<importantDataset> slideList = new ArrayList<>();
    SliderView sliderView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout progressBar;
    LinearLayout viewmore, allContent;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    Boolean quotalimit;
    int REQUEST_CODE = 11;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        navigationView = findViewById(R.id.navigationbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        sliderView = findViewById(R.id.imageSlider);
        allContent = findViewById(R.id.linearLayout4);
        progressBar = findViewById(R.id.progressbar_home);
        viewmore = findViewById(R.id.viewmore_importantBooks);
        importantrecyclerview = findViewById(R.id.importantRecyclerview_homeActivity);
        importantrecyclerview.setHasFixedSize(true);
        importantrecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView = findViewById(R.id.recyclerview_for_catagrious);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));

        allContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(HomeActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, HomeActivity.this,REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        AppRate.with(this)
//                .setInstallDays(2)
//                .setLaunchTimes(3)
//                .setRemindInterval(2)
//                .setShowLaterButton(true)
//                .setDebug(false)
//                .setOnClickButtonListener(new OnClickButtonListener() {
//                    @Override
//                    public void onClickButton(int which) {
//                        Log.d(MainActivity.class.getName(), Integer.toString(which));
//                    }
//                })
//                .monitor();
//
//        AppRate.showRateDialogIfMeetsConditions(this);

        getPermission();

        toolbar = findViewById(R.id.Custom_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(4);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_menu_24, this.getTheme());
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (menuItem.getItemId()){

                    case R.id.download_NavDrawer:
                        startActivity(new Intent(getApplicationContext(),DownloadActivity.class));
                        break;

                    case R.id.contactus_NavDrawer:
                        startActivity(new Intent(getApplicationContext(), joiningActivity.class));
                        break;

                    case R.id.feedback_NavDrawer:
                        startActivity(new Intent(getApplicationContext(), feedbackActivity.class));
                        break;

                    case R.id.request_NavDrawer:
                        startActivity(new Intent(getApplicationContext(), requestActivity.class));
                        break;

                    case R.id.privacyPolicy_NavDrawer:
                        startActivity(new Intent(getApplicationContext(), privacyPolicy.class));
                        break;

                    case R.id.videoLecture_NavDrawer:
                        startActivity(new Intent(getApplicationContext(), VideosLecture.class));
                        break;


                    case R.id.share_NavDrawer:
                        ShareApp();
                        break;


                }

                return true;
            }
        });

        final String [] titel = {"Chemistry", "Physics","Maths","Biology","Coaching\nNotes","CBSE","DPP","Previous\nQuestions"};
        final String [] titel2 = {"Chemistry", "Physics","Maths","Biology","Coaching Notes","CBSE","Daily Practice Papers","Previous Year Question Papers"};
        int [] icons = {R.drawable.chemistry1,R.drawable.physics,R.drawable.math,R.drawable.biological,R.drawable.notes1,R.drawable.cbse,R.drawable.dpp,R.drawable.pyqpicon};

        categoriesAdapter adapter = new categoriesAdapter(getApplicationContext(),titel,icons);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new categoriesAdapter.onItemClick() {
            @Override
            public void onItemClick(int postion) {
                String currentCategories = titel2[postion];
                Intent intent = new Intent(getApplicationContext(), showBooks.class);
                intent.putExtra("categories",currentCategories);
                startActivity(intent);
            }
        });

        importantBooksFatch();

        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), showBooks.class);
                intent.putExtra("categories","Important");
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE){
            Toast.makeText(this, "Start Download", Toast.LENGTH_SHORT).show();

            if (resultCode != RESULT_OK){
                Log.d("Update Tag","Update Flow Failed "+ resultCode);
            }
        }
    }

    private void importantBooksFatch() {
        list.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Important");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    importantDataset dataset = data.getValue(importantDataset.class);
                    list.add(dataset);
                }

                importantAdaptar adapter = new importantAdaptar(getApplicationContext(),list,"grid");
                importantrecyclerview.setAdapter(adapter);

                adapter.setOnItemClickListener(new importantAdaptar.onCLickListener() {
                    @Override
                    public void onBookCLick(int position) {
                        Intent intent = new Intent(getApplicationContext(), chapterActivity.class);
                        intent.putExtra("categories",list.get(position).getCategories());
                        intent.putExtra("bookName",list.get(position).getTitle());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_click, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(), searchActivity.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void getSlidershowFatch () {
        slideList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("New Books");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    importantDataset dataset = dataSnapshot.getValue(importantDataset.class);
                    slideList.add(dataset);
                }

                progressBar.setVisibility(View.GONE);
                allContent.setVisibility(View.VISIBLE);
                slideShowAdapter adapter = new slideShowAdapter(getApplicationContext(),slideList);
                sliderView.setSliderAdapter(adapter);
                sliderView.setIndicatorMargin(50);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                sliderView.setSliderTransformAnimation(SliderAnimations.CUBEOUTDEPTHTRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                sliderView.startAutoCycle();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        StyleableToast.makeText(getApplicationContext(),"Please click BACK again to exit",R.style.fullySpactilToast_HOMEACTIVITY).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void ShareApp() {
        Intent shareApp = new Intent(Intent.ACTION_SEND);
        shareApp.setType("text/plan");
        shareApp.putExtra(Intent.EXTRA_SUBJECT,"Chemistry Handbook");
        shareApp.putExtra(Intent.EXTRA_TEXT,"Found a great app in play store for JEE / NEET preparation freely available-try out this app ->https://play.google.com/store/apps/details?id=com.smart.iit_jeeexamprepration");
        startActivity(Intent.createChooser(shareApp,"Share By"));
    }

    private void moreApp() {
//        boolean Available = appAvailableorNot("com.android.chrome");

        try {
            String urlString = "https://play.google.com/store/apps/developer?id=Study+Smartly";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (Exception e){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Study+Smartly"));
            startActivity(intent);
        }



    }

    private void checkQuota() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Daily Quota");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               quotalimit = snapshot.getValue(Boolean.class);

               if (quotalimit){
                   QuotaDialog(HomeActivity.this);
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

    private void getPermission () {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Permission").document("Run").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                DocumentSnapshot data = task.getResult();
                boolean run = data.getBoolean("permission");

                if (run){
                    getSlidershowFatch();
                }else if (! run){
                    Toast.makeText(getApplicationContext(),"404 Server Error : ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        checkQuota();
        super.onResume();
    }
}