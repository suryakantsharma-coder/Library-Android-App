package com.smart.neet_jeefullpakage;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class uploadActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private TransferUtility transferUtility;
    private String DownloadKey ;
    private File currentfile = new File("null");
    private DO d;
    String [] bookName;
    String definepath;
    String path;
    String prepath;
    boolean PERMISSION = false;
    List<fileModel> filelist = new ArrayList<>();
    String currentsub = "null", currentBook, currentChapter, currentTime;
    chooseAdapter adapter;

    Spinner spinner;
    Button choosecover, upload;
    CheckBox  important,newBook, both;
    EditText title, description, Author;
    ImageView previewCover;
    ProgressBar progressBar;
    LinearLayout input,progressSection;
    TextView currentprogress;
    int CODE = 1;
    Uri currentUri, newUri;
    List<Uri> urifile = new ArrayList<>();
    List<String> list = new ArrayList<>();
    String imageurl;
    boolean importantOrNot, newBookorNot, bothorNot;
    String categoriesSelected, getDownloadUrl;
    FirebaseFirestore db;
    String perfectTitel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage mStorage = FirebaseStorage.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        spinner = findViewById(R.id.spinner_uploadActivity);
        spinner.setOnItemSelectedListener(this);
        choosecover = findViewById(R.id.chooseCover_uploadActivity);
        previewCover = findViewById(R.id.coverPreview_uploadActivity);
        newBook = findViewById(R.id.newBook_uploadActivity);
        important = findViewById(R.id.important_uploadActivity);
        both = findViewById(R.id.both_uploadActivity);
        upload = findViewById(R.id.upload_uploadActivity);
        title  = findViewById(R.id.title_uploadActivity);
        progressBar = findViewById(R.id.progressbar_progressdialog);
        currentprogress = findViewById(R.id.cureentprogress);
        input = findViewById(R.id.linearlayout_input_uploadActivity);
        progressSection = findViewById(R.id.progress_horizontal_uploadActivity);
        progressSection.setVisibility(View.GONE);
        description = findViewById(R.id.description_uploadActivity);
        Author = findViewById(R.id.Writer_Author_uploadActivity);
        db = FirebaseFirestore.getInstance();


        String [] categories = {"Physics","Chemistry","Maths","Coaching Notes","Daily Practice Papers","Previous Year Question Papers","Biology","CBSE"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


        choosecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosefileWindow();
                getSystemKeys();
            }
        });





        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (important.isChecked() && newBook.isChecked()){
                    newBook.setChecked(false);
                    Toast.makeText(uploadActivity.this, "\n New Book is Unchecked \n Please Select Both Option ", Toast.LENGTH_SHORT).show();
                }else {

                    if (important.isChecked()) {
                        importantOrNot = true;
                    } else if (newBook.isChecked()) {
                        newBookorNot = true;
                    } else if (both.isChecked()) {
                        bothorNot = true;
                    }

                    if (currentfile != null) {
                        upload(currentfile);
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            currentUri = data.getData();
            newUri = currentUri;
            urifile.add(data.getData());
            Log.d("ABCD2",""+currentUri);
            Picasso.get().load(currentUri).fit().into(previewCover);
            previewCover.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoriesSelected = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, ""+categoriesSelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private void uploadImportant(String cover){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String t = title.getText().toString();
        String UnderscoreT = t.replace(".","_");
        DatabaseReference reference = database.getReference("Important"+"/"+UnderscoreT);
        importantDataset dataset = new importantDataset(perfectTitel.toString(),cover,categoriesSelected);
        reference.setValue(dataset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(uploadActivity.this, "upload Important", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadNewBook (String cover){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh-mm-ss");
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        DatabaseReference reference = database.getReference("New Books"+"/"+date+"_"+time);
        importantDataset dataset = new importantDataset(perfectTitel,cover,categoriesSelected);
        reference.setValue(dataset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(uploadActivity.this, "upload Important", Toast.LENGTH_SHORT).show();
            }
        });

        LimitsOnNewBooks();
    }

    private void LimitsOnNewBooks (){
        list.clear();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("New Books"+"/");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    //importantDataset dataset = data.getValue(importantDataset.class);
                    String name = data.getKey();
                    list.add(name);
                }

                if (list.size() > 8){
                    DatabaseReference referenceDelete = database.getReference("New Books"+"/"+list.get(0));
                    referenceDelete.removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void choosefileWindow () {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                        definepath = prepath+"/"+"1";
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
                    if (currentfile.getName().endsWith("jpg") || currentfile.getName().endsWith("png")) {
                        previewCover.setVisibility(View.VISIBLE);
                        Picasso.get().load(currentfile).fit().into(previewCover);
                    }else{
                        previewCover.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"It's Not Image Format",Toast.LENGTH_SHORT ).show();
                    }
                    choosecover.setText(currentfile.getName());
                    choosecover.setBackgroundColor(Color.GREEN);
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

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("Uploading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String tempsub = categoriesSelected;
        tempsub = tempsub.replace(" ","_");

        String tempbook = title.getText().toString();
        tempbook = tempbook.replace(" ","_");

        credentials = new BasicAWSCredentials(d.getStudent_Name(),d.getStudent_Unique_ID());
        s3Client = new AmazonS3Client(credentials);
        s3Client.setEndpoint("END POINT");

        TransferNetworkLossHandler.getInstance(getApplicationContext());

        transferUtility = new TransferUtility(s3Client, this);
        CannedAccessControlList filePermission = CannedAccessControlList.PublicRead;

        TransferObserver observer = transferUtility.upload(
                "jee-neet-thumbnails", //empty bucket name, included in endpoint
                tempsub+"/"+tempbook+"/"+"Thumbnail",
                uri
                , //a File object that you want to upload
                filePermission
        );



        DownloadKey = "https://jee-neet-thumbnails.nyc3.digitaloceanspaces.com/Physics/"+tempbook+"/Thumbnail";

        Log.d("ID_Tag",observer.getKey()+"\n"+observer.getId());

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.COMPLETED.equals(observer.getState())) {
                    Toast.makeText(getApplicationContext(), "Space upload completed !!", Toast.LENGTH_SHORT).show();
                    uploadDataInFirebase();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                double progress = (100 * bytesCurrent) / bytesTotal;
                pd.setProgress((int) progress);

                if (progress == 100){
                    pd.dismiss();
                }
            }

            @Override
            public void onError(int id, Exception ex) {
                Toast.makeText(getApplicationContext(),"Failed to Upload :"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataInFirebase() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");
        final String date = dateFormat.format(calendar.getTime());


        String perfectTitel = title.getText().toString().replace(".","_");
        uploadDataSet dataset = new uploadDataSet(perfectTitel, description.getText().toString(), Author.getText().toString(), date, DownloadKey);
        db.collection(categoriesSelected).document(perfectTitel).set(dataset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(uploadActivity.this, "Data Stored", Toast.LENGTH_SHORT).show();
                choosecover.setBackgroundColor(Color.BLACK);
            }
        });

        //upload data in realtime Database

        String t = title.getText().toString();
        String UnderscoreT = t.replace(".","_");
        DatabaseReference realRef = database.getReference("Search/" + UnderscoreT + "/");
        searchDataset d = new searchDataset(perfectTitel, categoriesSelected, perfectTitel);
        realRef.setValue(d).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(uploadActivity.this, "Book Cover Upload", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        if (importantOrNot) {
            uploadImportant(imageurl);
            input.setVisibility(View.VISIBLE);
            progressSection.setVisibility(View.GONE);
            upload.setVisibility(View.VISIBLE);
        } else {
            if (newBookorNot) {
                uploadNewBook(imageurl);
                input.setVisibility(View.VISIBLE);
                progressSection.setVisibility(View.GONE);
                upload.setVisibility(View.VISIBLE);
            } else {

                if (bothorNot) {

                    uploadNewBook(imageurl);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadImportant(imageurl);
                            title.setText("");
                            description.setText("");
                            Author.setText("");
                            input.setVisibility(View.VISIBLE);
                            progressSection.setVisibility(View.GONE);
                            upload.setVisibility(View.VISIBLE);
                        }
                    }, 2000);

                }
            }
        }
    }

    private void getSystemKeys () {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("References").document("DO").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot data = task.getResult();
                d = data.toObject(DO.class);
            }
        });
    }
}