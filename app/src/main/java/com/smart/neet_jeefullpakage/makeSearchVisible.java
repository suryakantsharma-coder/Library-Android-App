package com.smart.neet_jeefullpakage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class makeSearchVisible extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Button choosepdf,chemistry,physics,maths,dpp,coachingNotes,previousYearQuestion,biology, upload,cbse;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> booksname = new ArrayList<>();
    String currentsub = "null", currentBook;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_search_visible);

        chemistry = findViewById(R.id.chemistry_chapterActivity);
        physics = findViewById(R.id.physics_chapterActivity);
        maths = findViewById(R.id.maths_chapterActivity);
        dpp = findViewById(R.id.dpp_chapterActivity);
        cbse = findViewById(R.id.CBSE_chapterActivity);
        upload = findViewById(R.id.uploadchapter_chapterActivity);
        previousYearQuestion = findViewById(R.id.pyqp_chapterActivity);
        biology = findViewById(R.id.biology_chapterActivity);
        coachingNotes = findViewById(R.id.coachingNotes_chapterActivity);
        spinner = findViewById(R.id.bookSelect_chapterActivity);
        spinner.setOnItemSelectedListener(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentsub.equals("null")) {
                    uploadFile();
                }else{
                    Toast.makeText(makeSearchVisible.this, "Please Select Subject First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chemistry.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Chemistry";

                chemistry.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Chemistry").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Physics";

                physics.setBackgroundColor(Color.rgb(174,174,174));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Physics").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Maths";

                maths.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Maths").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        dpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Daily Practice Papers";

                dpp.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Daily Practice Papers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        previousYearQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Previous Year Question Papers";

                previousYearQuestion.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Previous Year Question Papers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        biology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Biology";

                biology.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Biology").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        coachingNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "Coaching Notes";

                coachingNotes.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                cbse.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("Coaching Notes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

        cbse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksname.clear();
                currentsub = "CBSE";

                cbse.setBackgroundColor(Color.rgb(174,174,174));
                physics.setBackgroundColor(Color.rgb(255,255,255));
                chemistry.setBackgroundColor(Color.rgb(255,255,255));
                maths.setBackgroundColor(Color.rgb(255,255,255));
                biology.setBackgroundColor(Color.rgb(255,255,255));
                dpp.setBackgroundColor(Color.rgb(255,255,255));
                previousYearQuestion.setBackgroundColor(Color.rgb(255,255,255));
                coachingNotes.setBackgroundColor(Color.rgb(255,255,255));

                db.collection("CBSE").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot data : task.getResult()){
                            booksname.add(data.getId());
                        }

                        ArrayAdapter <String> adapter = new ArrayAdapter <String> (getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,booksname);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("debug",""+e.getMessage());
                    }
                });
            }
        });

    }

    private  void uploadFile(){
        DatabaseReference realRef = database.getReference("Search/" + currentBook.toString() + "/");
        searchDataset d = new searchDataset(currentBook, currentsub, currentBook);
        realRef.setValue(d).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(makeSearchVisible.this, "Upload in Search bar", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(makeSearchVisible.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentBook = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




}