package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class typeTestSeries extends AppCompatActivity {
    RecyclerView listView;
    List<testDataType> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_test_series);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listView = findViewById(R.id.listView_test);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));

        //intent recived
        String TYPE = getIntent().getStringExtra("TYPE");

        //condition check
        switch (TYPE){

            case "syllabus_jee":
                Syllabus();
                break;

            case "unit_jee":
                unit_jee();
                break;

            case "minor_jee":
                minor_jee();
                break;

            case "major_jee":
                major_jee();
                break;

            case "syllabus_neet":
                Syllabus_neet();
                break;

            case "unit_neet":
                unit_neet();
                break;

            case "minor_neet":
                minor_neet();
                break;

            case "major_neet":
                major_neet();
                break;

            case "partTest_jee":
                partTest_jee();
                break;
        }

    }


    //Syllabus for Jee
    private void Syllabus () {


        mlist.add(new testDataType("Unit Test", "https://drive.google.com/file/d/1w3WU3E2urw-63vus2gvs9DnbVxwv4PD3/view?usp=sharing"));
        mlist.add(new testDataType("Minor Test", "https://drive.google.com/file/d/1w91QtLjPtE2gfHPcjya8UxxnP9nK_3hw/view?usp=sharing"));
        mlist.add(new testDataType("Major Test (Full Syllabus)", "url"));
        mlist.add(new testDataType("Part Test (JEE Advance)","https://drive.google.com/file/d/12Q1EmLv397gk15uMpxKnWs8YUwAKjvB_/view?usp=sharing"));

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                if (mlist.get(position).getUrl().startsWith("https://")) {
                    Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class);
                    intent.putExtra("pdfUrl", mlist.get(position).getUrl());
                    startActivity(intent);
                }else{
                    StyleableToast.makeText(getApplicationContext(),"Full Syllabus",R.style.fullySpactilToast_HOMEACTIVITY).show();
                }
            }
        });
    }

    //Syllabus for Neet
    private void Syllabus_neet (){

        mlist.add(new testDataType("Unit Test", "https://drive.google.com/file/d/1R-tySKF8XJRsfzvXUtcGODcRGQxPi6B9/view?usp=sharing"));
        mlist.add(new testDataType("Minor Test", "https://drive.google.com/file/d/1jR98-GeBPtv08uO2hzciahnMViBQe3iO/view?usp=sharing"));
        mlist.add(new testDataType("Major Test (Full Syllabus)", "url"));

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                if (mlist.get(position).getUrl().startsWith("https://")) {
                    Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class);
                    intent.putExtra("pdfUrl", mlist.get(position).getUrl());
                    startActivity(intent);
                }else{
                    StyleableToast.makeText(getApplicationContext(),"Full Syllabus",R.style.fullySpactilToast_HOMEACTIVITY).show();
                }
            }
        });

    }

    private void unit_jee () {

        mlist.add(new testDataType("Test Paper 1 (With Solutions)","https://drive.google.com/file/d/17nfZKPb3riSI9UgapdjtiRaRtbtXYiFg/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 2 (With Solutions)","https://drive.google.com/file/d/1XsrAmmT3KudsNhKBQkkiSUNC7RAvKLMo/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 3 (With Solutions)","https://drive.google.com/file/d/1JRKla3tEIRax9AOtk4UiySQpQhcd2u-4/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 4 (With Solutions)","https://drive.google.com/file/d/1qBCy3QP0KWf7tfNQY_mCojOtC-jh96Cj/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 5 (With Solutions)","https://drive.google.com/file/d/1RiF3kh82v-jsUlyrJNlzYsJ0JF1T6Wu9/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 6 (With Solutions)","https://drive.google.com/file/d/1TDQOiUXOfc_M64kTVcVtfEYTZnuFOTYx/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 7 (With Solutions)","https://drive.google.com/file/d/1NkJtfijec7cA47UNo954rKz0weP--3Il/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 8 (With Solutions)","https://drive.google.com/file/d/1LcKBvLC9TFxsZEr9mYgiiVk3n2Za7Pko/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 9 (With Solutions)","https://drive.google.com/file/d/18hhzc-3LZEPtj2Qdo3Jgqopmsx9qVnyx/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 10 (With Solutions)","https://drive.google.com/file/d/12VCiDhALvr_OGnNpFYGxotWP-sT48gGA/view?usp=sharing"));


        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });


    }

    private void minor_jee () {

        mlist.add(new testDataType("Test Paper 1 (With Solutions)","https://drive.google.com/file/d/1IdkK6tXEJo8Ux64_92-K3pk4GEYKT56X/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 2 (With Solutions)","https://drive.google.com/file/d/1mRrf4JEGhYf3ukG7S572qXKCQiDYZI6a/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 3 (With Solutions)","https://drive.google.com/file/d/1KSy2fuGucjmwiNl2MqEcBtOQ_BFG12dW/view?usp=sharing"));

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });


    }

    private void major_jee() {


        mlist.add(new testDataType("Test Paper 1 (With Solutions)","https://drive.google.com/file/d/1DwsuilwdozbwaBvaaeOZva91pjqZ-Bib/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 2 (With Solutions)","https://drive.google.com/file/d/1EgLd3Kxx1rXijIZU1-7JOzds37SWlcfM/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 3 (With Solutions)","https://drive.google.com/file/d/159nP8VWd_uHZlUnK1Gs4cJwQoySKqvmc/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 4 (With Solutions)","https://drive.google.com/file/d/19mihO4ayzXRM5bsr9XjSxhE3jAoxik9l/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 5 (With Solutions)","https://drive.google.com/file/d/18x7fUrkgW6tsneA6I7jJ9Sj1MzMuzCzB/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 6 (With Solutions)","https://drive.google.com/file/d/1Vpfya7T41TvGt2l1TNXJGzsjYD_5Os0l/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 7 (With Solutions)","https://drive.google.com/file/d/1AyIPsSKNwzUg92WSlUYrNT_liimiP4_3/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 8 (With Solutions)","https://drive.google.com/file/d/1wP_MQPqt_JJp3BKKI-RRCzr5aOiF9v1x/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 9 (With Solutions)","https://drive.google.com/file/d/1C7Ob5q90LQ9_YNsjcdCAvQPK-H3PFVPQ/view?usp=sharing"));

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });

    }

    private void unit_neet() {

        mlist.add(new testDataType("Test Paper 1","https://drive.google.com/file/d/1sU9aMdKb0RjhKIFvOJSwg5ILJTTLdTaW/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 2","https://drive.google.com/file/d/1zC9aRwPA_ry2lSxouVXVwDvrnm4rahX4/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 3","https://drive.google.com/file/d/1bZEwPLxzUapChQhafTbLruDWFNaqoe_j/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 4","https://drive.google.com/file/d/1HJanU8qyDvMRXUM_6XBSXYm7ShrwJPm0/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 5","https://drive.google.com/file/d/1SBMh0zhKkzIBJl2ppVlZZg_uzas4m3-M/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 6","https://drive.google.com/file/d/12m-DLiltnChEFFA473edd5MNiGC7sTzP/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 7","https://drive.google.com/file/d/1uEgeKrQBgs1x2rEwxoFJEHDVSPcUK1vM/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 8","https://drive.google.com/file/d/1pR4H0kSy7vNUS7-fdo58ljqcdjgTTW_m/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 9","https://drive.google.com/file/d/1sLvHRfJPXUtUd5AKqClu6UHcgWy3brNV/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 10","https://drive.google.com/file/d/19rmonAf2WiK1Ovw64IpiTzNKelaZkfv3/view?usp=sharing"));

        //solution

        mlist.add(new testDataType("Solution Paper 1","https://drive.google.com/file/d/1fIgXr07TIZmO2rdNOEY_LHCie5XmpG8L/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 2","https://drive.google.com/file/d/1DkNa5pKNHULkSUJ3MWn7AwjFpOEmYU5m/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 3","https://drive.google.com/file/d/1sawal7UymxfxOebe3beUdhUwjWaWT4Gd/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 4","https://drive.google.com/file/d/1_TmgkMpubbV7AU8-IyoscJqgWcD-ycsD/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 5","https://drive.google.com/file/d/1b0V3zrl7AHCrJ7ZvATnkHjwLXxL3rBMM/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 6","https://drive.google.com/file/d/1KEtSrANlOBC-fQIq4NrkdQ-KaBpZA8yM/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 7","https://drive.google.com/file/d/1iRKnWnagSLplGOzk39Vje_ltp00rMyhG/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 8","https://drive.google.com/file/d/1zXVzPYprPpYK8Gh59vajr_Nu2j66PQGl/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 9","https://drive.google.com/file/d/1SL6qI4oAgR1Vrdk01QvKspDej4KL9RCf/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 10","https://drive.google.com/file/d/1H7iTjLLc7WQSa6lSRPtgAZbWyqNMrwOm/view?usp=sharing"));


        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });


    }

    private void minor_neet () {

        mlist.add(new testDataType("Test Paper 1", "https://drive.google.com/file/d/1d_Alpgud0nqWrWSeeRzZIpdGBMd7_A6R/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 2", "https://drive.google.com/file/d/1JmGMwD2cjDJyqyfylUjAn3Sf2FBFHr7c/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 3", "https://drive.google.com/file/d/1MsqfT3vx4KNbffoN5YPgqOwfkMlKRf0x/view?usp=sharing"));

        //solution

        mlist.add(new testDataType("Solution Paper 1", "https://drive.google.com/file/d/1rex_Q4ithKl7b7Nldb11Vue8PktrexFU/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 2", "https://drive.google.com/file/d/14qp7mJ63Vdb-kzba6b5Ms_8J2fzCx7e8/view?usp=sharing"));
        mlist.add(new testDataType("Solution Paper 3", "https://drive.google.com/file/d/14qp7mJ63Vdb-kzba6b5Ms_8J2fzCx7e8/view?usp=sharing"));

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });

    }

    private void major_neet() {

        mlist.add(new testDataType("Test Paper 1 (Half Syllabus 11th Class)","https://drive.google.com/file/d/1rRpqOUMOjb_VFk-iYoIM_Nm_ViiSQvao/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 2 (Half Syllabus 12th Class)","https://drive.google.com/file/d/1oat0jgna9nmWVbM0sCO64n0uvtNRiv6u/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 3 (Full Syllabus)","https://drive.google.com/file/d/14HE6ucoaxFOFukpoT6gVTmXpcFKkGIoU/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 4 (Full Syllabus)","https://drive.google.com/file/d/1c76cEjY2x2ydkmIHG_AAP2LGbR9vsnLN/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 5 (Full Syllabus)","https://drive.google.com/file/d/1noce4F3mUuvH6_-arRDfsv7zc6kFgm38/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 6 (Full Syllabus)","https://drive.google.com/file/d/1LaLY-HpKTmuirM6qJitJVoyWkKzfj0Nd/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 7 (Full Syllabus)","https://drive.google.com/file/d/1NSA0Nnao2XmLuoUE6pNbAvN1vb-USl1G/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 8 (Full Syllabus)","https://drive.google.com/file/d/129fIvChn0QKdFIKQSbD9dDmfhVARuASx/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 9 (Full Syllabus)","https://drive.google.com/file/d/1vGpOdyQdgxQ5BojdKQzIqLPA3e6K8jPJ/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 10 (Full Syllabus)","https://drive.google.com/file/d/1X070Kr6koZgYfpA_kqVwb1q9b26dHYtt/view?usp=sharing"));
        mlist.add(new testDataType("Test Paper 11 (Full Syllabus)","https://drive.google.com/file/d/1uq3DEXfXjDTOh_zTWzvdPP-NAsGi4uGH/view?usp=sharing"));

        //solutions

        mlist.add(new testDataType("Solutions Paper 1 (Half Syllabus 11th Class)","https://drive.google.com/file/d/1wIzaR63Xi2JO94tGOjrf1Z1eucIh7Tn6/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 2 (Half Syllabus 12th Class)","https://drive.google.com/file/d/1wWl5ZvaMUR1scYFLRX_QAL2AO5bgcJ1F/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 3 (Full Syllabus)","https://drive.google.com/file/d/1p675OnAhStDBt5-2mzxsfjUHlbRl9jLL/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 4 (Full Syllabus)","https://drive.google.com/file/d/1GmFOjDrBtujjW8UnDH8TXMG2LHYEiYa6/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 5 (Full Syllabus)","https://drive.google.com/file/d/1a3-7TAcFcjmoE4oBBXJrLJyu0na2i_Q5/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 6 (Full Syllabus)","https://drive.google.com/file/d/1PUZ23JyemoJnUdck0dIEGERDcKeqMteu/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 7 (Full Syllabus)","https://drive.google.com/file/d/1AlNckuuc9Id7i-J_-YfsoBHLPzZv6X1f/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 8 (Full Syllabus)","https://drive.google.com/file/d/1d1NUVO3xCT4ZrwH6SOCCOmuye_icMpKy/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 9 (Full Syllabus)","https://drive.google.com/file/d/1j8LSTgk48WXgylYes3Mml-pML4ghs7t0/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 10 (Full Syllabus)","https://drive.google.com/file/d/1OO-TfVp6HnCjVWMQyM_0Sbvvz4crRlUM/view?usp=sharing"));
        mlist.add(new testDataType("Solutions Paper 11 (Full Syllabus)","https://drive.google.com/file/d/1V1JwoqpY2oaFYQLdlF2T42H2iZSLV4Qd/view?usp=sharing"));


        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });

    }

    private void partTest_jee() {

        //part 1

        mlist.add(new testDataType("Paper 1 (Part 1)","https://drive.google.com/file/d/1qELTt0NBGDTuv4Yv5LiAwAGWegduBtls/view?usp=sharing"));
        mlist.add(new testDataType("Paper 2 (Part 1)","https://drive.google.com/file/d/1MkXz4RFUdKju7hs5iSvvF_VuFDX4qkwI/view?usp=sharing"));
        mlist.add(new testDataType("Solution (part 1)","https://drive.google.com/file/d/1ZS7Z15dwmCab6VQbCM0KOiu61K2Xr0hC/view?usp=sharing"));

        //part 2

        mlist.add(new testDataType("Paper 1 (Part 2)","https://drive.google.com/file/d/1ujlO89gDWfdIujWbecKGFra75ashYHft/view?usp=sharing"));
        mlist.add(new testDataType("Paper 2 (Part 2)","https://drive.google.com/file/d/1GaJfsj7272x-6CGLaqIcMQsNElhN_BJh/view?usp=sharing"));
        mlist.add(new testDataType("Solution (part 2)","https://drive.google.com/file/d/1224fJSdDpVxbrMr85RKQS-NtZ-CaeOFZ/view?usp=sharing"));

        //part 3

        mlist.add(new testDataType("Paper 1 (Part 3)","https://drive.google.com/file/d/15bPXG6TFnqdfWFILh-Xmco71hPemw-AT/view?usp=sharing"));
        mlist.add(new testDataType("Paper 2 (Part 3)","https://drive.google.com/file/d/1G7Eb2kEgxmcriM2jha_AZL953vQ8Bwbj/view?usp=sharing"));
        mlist.add(new testDataType("Solution (part 3)","https://drive.google.com/file/d/1ISE-GQY5lRnVoA2Sbj_6QV9bnxJ-VKon/view?usp=sharing"));

        //part 4

        mlist.add(new testDataType("Paper 1 (Part 4)","https://drive.google.com/file/d/1xataHjSGDGtRT_eCEAVe4nlx81ZUmOIW/view?usp=sharing"));
        mlist.add(new testDataType("Paper 2 (Part 4)","https://drive.google.com/file/d/1S9RLt3KlfcY_Qceen8iEaEKaJO69mF2u/view?usp=sharing"));
        mlist.add(new testDataType("Solution (part 4)","https://drive.google.com/file/d/1LuEF4kibmo_sueS_VGYyfY69mMVagC43/view?usp=sharing"));

        //part 5

        mlist.add(new testDataType("Paper 1 (Part 5)","https://drive.google.com/file/d/1_phdEbKoLYb42R4miQTN2W8B-8yNbTEh/view?usp=sharing"));
        mlist.add(new testDataType("Paper 2 (Part 5)","https://drive.google.com/file/d/1wdBQNoQUJTNAzfYlOVudQB7F9BhJbHgl/view?usp=sharing"));
        mlist.add(new testDataType("Solution (part 5)","https://drive.google.com/file/d/1bw32uyQPZqW64a3sKezxbUVNt-Yy9kLq/view?usp=sharing"));

        //part 6

        mlist.add(new testDataType("Paper 1 (Part 6)","https://drive.google.com/file/d/1qlS81knERo4uwDfCt5F2S052b1M6KM35/view?usp=sharing"));
        mlist.add(new testDataType("Paper 2 (Part 6)","https://drive.google.com/file/d/1ZIHn9_GcHrCu5NVL0HUUB6auqef2UdWk/view?usp=sharing"));
        mlist.add(new testDataType("Solution (part 6)","https://drive.google.com/file/d/1d2hWPmsIFfc9TYzN2KJEPKlU0TAmFTrG/view?usp=sharing"));


        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), mlist);
        listView.setAdapter(adapter);

        adapter.setOncLickListener(new CustomAdapter.onClickListener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), webviewOpenPdf.class );
                intent.putExtra("pdfUrl",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });



    }



    //Inner class for DataTypes

    private class testDataType {
        String name;
        String url;

        public testDataType(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    //Making Inner CustomAdapter

    private static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private Context context;
        private List<testDataType> mlist;
        private onClickListener mListener;

        public interface onClickListener {
            void onClickItem(int position);
        }

        public CustomAdapter(Context context, List<testDataType> mlist) {
            this.context = context;
            this.mlist = mlist;
        }

        public void setOncLickListener(onClickListener mListener){
            this.mListener = mListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.testrow,null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            return new ViewHolder(view, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText(mlist.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout layout;
            TextView name;

            public ViewHolder(@NonNull View itemView, onClickListener mListener) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                layout = itemView.findViewById(R.id.test_row);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener!=null){
                            int position = getAdapterPosition();
                            if (position!= RecyclerView.NO_POSITION){
                                mListener.onClickItem(position);
                            }
                        }
                    }
                });
            }
        }
    }
}