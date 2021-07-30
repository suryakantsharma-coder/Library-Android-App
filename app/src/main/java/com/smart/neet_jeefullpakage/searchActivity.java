package com.smart.neet_jeefullpakage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class searchActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    List<searchDataset> list = new ArrayList<>();
    searchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setElevation(3);


        recyclerView = findViewById(R.id.recyclerView_SearchActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Search();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAdsPermission();
            }
        },2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (s.equals(getApplicationContext().getString(R.string.Null))){
                    UploadDialogBox();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }


    private void Search(){
        final DatabaseReference reference = database.getReference("Search/");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    searchDataset data = dataSnapshot.getValue(searchDataset.class);
                    list.add(data);
                }

                adapter = new searchAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemSelected(new searchAdapter.OnitemSelected() {
                    @Override
                    public void OnItemSelected(int position) {
                        Intent intent = new Intent(getApplicationContext(),chapterActivity.class);
                        intent.putExtra("categories",list.get(position).getCollection());
                        intent.putExtra("bookName",list.get(position).getTitle());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UploadDialogBox () {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.upload_dialog_box,null);

        LinearLayout uploadBook = (LinearLayout) view.findViewById(R.id.uploadBook_DialogBox);
        final LinearLayout uploadChapter = (LinearLayout) view.findViewById(R.id.uploadChapter_DialogBox);
        LinearLayout uploadSearch = (LinearLayout) view.findViewById(R.id.addBooksearchBar_DialogBox);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setView(view);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        uploadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),uploadActivity.class));
            }
        });


        uploadChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),uploadChapter.class));
            }
        });

        uploadSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),makeSearchVisible.class));
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