package com.smart.neet_jeefullpakage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class upgrade_to_DO_thubnail extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private TransferUtility transferUtility;
    private String DownloadKey ;
    private File currentfile = new File("null");
    private DO d;
    TextView showDetails;
    Spinner spinner,chapterSpinner;
    Uri pdfuri;
    CheckBox sol;
    Boolean PERMISSION = false,isConnected;
    chooseAdapter adapter;
    Button choosepdf,chemistry,physics,maths,dpp,coachingNotes,previousYearQuestion,biology, upload,cbse;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> booksname = new ArrayList<>();
    String definepath;
    String path;
    String prepath;
    List<fileModel> filelist = new ArrayList<>();
    String currentsub = "null", currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_to__d_o_thubnail);

        checkNetworkIsOnOrOff();

        showDetails = findViewById(R.id.showName_file);
        sol = findViewById(R.id.uploadSolutionSwitch);
        choosepdf = findViewById(R.id.choosepdf_chapterActivity);
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
        chapterSpinner = findViewById(R.id.chapterselect_updateActivity);
        spinner.setOnItemSelectedListener(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected){
                    getSystemKeys();
                }
            }
        });

        choosepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosefileWindow();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode == RESULT_OK && data != null && data.getData()!=null){
            pdfuri = data.getData();
            if (!pdfuri.equals(null)){
                choosepdf.setText("PDF Selected");
                choosepdf.setBackgroundColor(Color.rgb(181,230,29));
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentBook = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void uploadFile() {
        if (!currentfile.getPath().equals("null") && !currentsub.equals("null")) {
            upload(currentfile);
        }else{
            if (currentfile.getPath().equals("null")) {
                Toast.makeText(getApplicationContext(), "Please Select File First", Toast.LENGTH_SHORT).show();
            }

            if (currentsub.equals("null")){
                Toast.makeText(getApplicationContext(),"Please Select Subject Fist",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void choosefileWindow () {
        AlertDialog.Builder alert = new AlertDialog.Builder(upgrade_to_DO_thubnail.this);
        View view = getLayoutInflater().inflate(R.layout.choosefile,null);

        RecyclerView rv = view.findViewById(R.id.recyclerview_for_file);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ImageView back = view.findViewById(R.id.backfile);
        TextView pathView = view.findViewById(R.id.viewpath);

        AlertDialog alertDialog = alert.create();
        alertDialog.setView(view);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        path = Environment.getExternalStorageDirectory().toString();
        prepath = path;

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        PERMISSION = true;
                        definepath = prepath+"/"+"1t";

                        File file = new File(definepath);
                        if (file.exists()){
                            pathView.setText(definepath);
                            filelist.clear();
                            getFileList(definepath);
                            prepath = definepath;
                        }else {
                            pathView.setText(prepath);
                            getFileList(prepath);
                        }
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


        adapter = new chooseAdapter(getApplicationContext(),filelist);
        rv.setAdapter(adapter);

        adapter.setonItemClickListener(new chooseAdapter.onItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                if (filelist.get(position).isFolder) {
                    String name = filelist.get(position).getFile().getName();
                    filelist.clear();
                    getFileList(prepath + "/" + name);
                    prepath = prepath+"/"+name;
                    adapter.notifyDataSetChanged();
                    pathView.setText(prepath);
                    back.setVisibility(View.VISIBLE);
                }else {
                    currentfile = filelist.get(position).getFile();
                    choosepdf.setText(currentfile.getName());
                    choosepdf.setBackgroundColor(Color.GREEN);
                    alertDialog.dismiss();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temppre = prepath.substring(prepath.indexOf("/"),prepath.lastIndexOf("/"));

                if (!temppre.equals(path)) {
                    filelist.clear();
                    getFileList(temppre);
                    prepath = temppre;
                    adapter.notifyDataSetChanged();
                    pathView.setText(prepath);
                }else{
                    Toast.makeText(getApplicationContext(), "You Are In Root Directory", Toast.LENGTH_SHORT).show();

                    prepath = path;

                    if (prepath.equals(definepath) || prepath.equals(path)){
                        filelist.clear();
                        getFileList(path);
                        prepath = path;
                        adapter.notifyDataSetChanged();
                        pathView.setText(path);
                        back.setVisibility(View.GONE);
                    }
                }
            }
        });

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


    private void upload(File uri){

        final ProgressDialog pd = new ProgressDialog(upgrade_to_DO_thubnail.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("Uploading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String tempsub = currentsub;
        tempsub = tempsub.replace(" ","_");

        String tempbook = currentBook;
        tempbook = tempbook.replace(" ","_");

        credentials = new BasicAWSCredentials(d.getStudent_Name(),d.getStudent_Unique_ID());
        s3Client = new AmazonS3Client(credentials);
        s3Client.setEndpoint("END POINT");

        TransferNetworkLossHandler.getInstance(getApplicationContext());

        transferUtility = new TransferUtility(s3Client, this);
        CannedAccessControlList filePermission = CannedAccessControlList.PublicRead;

        TransferObserver observer = transferUtility.upload(
                "jee-nit", //empty bucket name, included in endpoint
                tempsub+"/"+tempbook+"/"+"Thumbnail",
                uri
                , //a File object that you want to upload
                filePermission
        );


        DownloadKey = observer.getKey();

        Log.d("ID_Tag",observer.getKey()+"\n"+observer.getId());

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.COMPLETED.equals(observer.getState())) {
                    Toast.makeText(getApplicationContext(), "Space upload completed !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                double progress = (100 * bytesCurrent) / bytesTotal;
                pd.setProgress((int)progress);

                if (bytesCurrent==bytesTotal){
                    pd.dismiss();
                }
            }

            @Override
            public void onError(int id, Exception ex) {
                Toast.makeText(getApplicationContext(), "Space upload error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
                uploadFile();
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