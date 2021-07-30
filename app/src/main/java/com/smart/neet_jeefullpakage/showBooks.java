package com.smart.neet_jeefullpakage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class showBooks extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<uploadDataSet> list = new ArrayList<>();
    List<importantDataset> Implist = new ArrayList<>();
    FilteredbookShowAdapter adapter;
    importantAdaptar ImpAdapter;
    String categories;
    Toolbar toolbar;
    private String layoutType = "grid";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_books);

        recyclerView = findViewById(R.id.recyclerView_showBooks);
        toolbar = findViewById(R.id.Custom_Toolbar);
        progressBar = findViewById(R.id.progressbar_showBooks);
        checkAdsPermission();

        categories = getIntent().getStringExtra("categories");
        getSupportActionBar().setTitle(categories);

        if (categories.equals("Important")){
            importantBooksFatch();
        }else {
            fetchData();
        }




    }

    private void importantBooksFatch() {
        Implist.clear();
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Important");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    importantDataset dataset = data.getValue(importantDataset.class);
                    Implist.add(dataset);
                }

                ImpAdapter = new importantAdaptar(getApplicationContext(),Implist,layoutType);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                recyclerView.setAdapter(ImpAdapter);
                progressBar.setVisibility(View.GONE);

                ImpAdapter.setOnItemClickListener(new importantAdaptar.onCLickListener() {
                    @Override
                    public void onBookCLick(int position) {
                        Intent intent = new Intent(getApplicationContext(),chapterActivity.class);
                        intent.putExtra("categories",Implist.get(position).getCategories());
                        intent.putExtra("bookName",Implist.get(position).getTitle());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(showBooks.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void importantBooksFatch_vertical() {
        Implist.clear();
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Important");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    importantDataset dataset = data.getValue(importantDataset.class);
                    Implist.add(dataset);
                }

                ImpAdapter = new importantAdaptar(getApplicationContext(),Implist,layoutType);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(ImpAdapter);
                progressBar.setVisibility(View.GONE);

                ImpAdapter.setOnItemClickListener(new importantAdaptar.onCLickListener() {
                    @Override
                    public void onBookCLick(int position) {
                        Intent intent = new Intent(getApplicationContext(),chapterActivity.class);
                        intent.putExtra("categories",Implist.get(position).getCategories());
                        intent.putExtra("bookName",Implist.get(position).getTitle());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(showBooks.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchData (){
        progressBar.setVisibility(View.VISIBLE);
        list.clear();
        db.collection(categories).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                try{
                    for (QueryDocumentSnapshot data : task.getResult()){
                        uploadDataSet dataset = data.toObject(uploadDataSet.class);
                        list.add(dataset);
                    }
                }catch (Exception e){
                    Log.d("ABCD", e.toString());
                }


                Log.d("ABCD",""+list.size());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                adapter = new FilteredbookShowAdapter(getApplicationContext(),list, "grid");
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

                adapter.setOnItemClickListener(new FilteredbookShowAdapter.onCLickListener() {
                    @Override
                    public void onBookCLick(int position) {
                        Intent intent = new Intent(getApplicationContext(),chapterActivity.class);
                        intent.putExtra("categories",categories);
                        intent.putExtra("bookName",list.get(position).getTitle());
                        startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(showBooks.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void fetchDataVertical (){
        progressBar.setVisibility(View.VISIBLE);
        list.clear();
        db.collection(categories).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                try{
                    for (QueryDocumentSnapshot data : task.getResult()){
                        uploadDataSet dataset = data.toObject(uploadDataSet.class);
                        list.add(dataset);
                    }
                }catch (Exception e){
                    Log.d("ABCD", e.toString());
                }


                Log.d("ABCD",""+list.size());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new FilteredbookShowAdapter(getApplicationContext(),list, "vertical");
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

                adapter.setOnItemClickListener(new FilteredbookShowAdapter.onCLickListener() {
                    @Override
                    public void onBookCLick(int position) {
                        Intent intent = new Intent(getApplicationContext(),chapterActivity.class);
                        intent.putExtra("categories",categories);
                        intent.putExtra("bookName",list.get(position).getTitle());
                        startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(showBooks.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_with_listview_change,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!categories.equals("Important")) {
                    adapter.getFilter().filter(s);
                }else {
                    ImpAdapter.getFilter().filter(s);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.layoutChange:

                if (categories.equals("Important")) {

                    if (layoutType.equals("grid")) {
                        importantBooksFatch_vertical();
                        layoutType = "vertical";
                        item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_view_module_24));
                    } else {
                        importantBooksFatch();
                        layoutType = "grid";
                        item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_view_list_24));
                    }
                }else{

                    if (layoutType.equals("grid")) {
                        fetchDataVertical();
                        layoutType = "vertical";
                        item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_view_module_24));
                    } else {
                        fetchData();
                        layoutType = "grid";
                        item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_view_list_24));
                    }
                }


        }

        return super.onOptionsItemSelected(item);
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