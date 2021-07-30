package com.smart.neet_jeefullpakage.TestSeries;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smart.neet_jeefullpakage.R;
import com.smart.neet_jeefullpakage.typeTestSeries;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class neetTestSeriesFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public neetTestSeriesFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nettTestSeriesFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static com.smart.neet_jeefullpakage.TestSeries.neetTestSeriesFragments newInstance(String param1, String param2) {
        com.smart.neet_jeefullpakage.TestSeries.neetTestSeriesFragments fragment = new com.smart.neet_jeefullpakage.TestSeries.neetTestSeriesFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_nett_test_series_fragments, container, false);

        LinearLayout syllabus = view.findViewById(R.id.Syllabus);
        LinearLayout unit = view.findViewById(R.id.Unit_test);
        LinearLayout minor = view.findViewById(R.id.Minor_test);
        LinearLayout major  = view.findViewById(R.id.Major_test);

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), typeTestSeries.class);
                intent.putExtra("TYPE", "syllabus_neet");
                startActivity(intent);
            }
        });

        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), typeTestSeries.class);
                intent.putExtra("TYPE", "unit_neet");
                startActivity(intent);
            }
        });

        minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), typeTestSeries.class);
                intent.putExtra("TYPE", "minor_neet");
                startActivity(intent);
            }
        });

        major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), typeTestSeries.class);
                intent.putExtra("TYPE", "major_neet");
                startActivity(intent);
            }
        });


        return view;
    }
}