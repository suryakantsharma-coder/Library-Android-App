package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilteredchapterAdapter extends RecyclerView.Adapter<FilteredchapterAdapter.ViewHolder> {
    Context context;
    List<String> names;
    List<chapterDatasetV2> chapter;
    List<chapterDatasetV2> list_Download = new ArrayList<>();
    List<chapterDataset> chapterDetails;
    private onCLickListener mListener;
    ArrayList<File> fileList = new ArrayList<>();

    public interface onCLickListener {
        void onPdfView(int position);
    }

    public void setOnItemClickListener(onCLickListener mListener){
        this.mListener = mListener;
    }

    public FilteredchapterAdapter(Context context, List<String> names, List<chapterDatasetV2> chapter, List<chapterDataset> chapterDetails, ArrayList<File> fileList) {
        this.context = context;
        this.names = names;
        this.chapter = chapter;
        this.chapterDetails = chapterDetails;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chapter_layout,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FilteredchapterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("testRecycler","start = "+chapter.size());
        boolean foundOrNot = false;
        for (int i=0; i<chapter.size();i++) {
            for (int j=0; j<names.size();j++) {
                Log.d("testRecycler", "index = " + i);
                if (chapter.get(i).getName().toLowerCase().contains(names.get(j).toLowerCase())) {
                    foundOrNot = true;
                    chapter.remove(i);
                    Log.d("testRecycler", "after remove = " + chapter.size());


                } else {
                    foundOrNot = false;
                }
            }
        }



        if (foundOrNot) {
            Log.d("testRecycler","foundornot ");
                holder.title.setText(names.get(position));
                holder.title.setEnabled(true);
                holder.title.setTextColor(Color.rgb(92, 92, 92));
                holder.linearLayout.setBackgroundColor(Color.rgb(210, 210, 210));
                holder.dindicator.setVisibility(View.VISIBLE);
                holder.indicate.setVisibility(View.GONE);
                holder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, NotificationService.class);
                        intent.putExtra("fileName", chapter.get(position).getName());
                        intent.putExtra("filepath", chapter.get(position).getPath());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

            } else {
                holder.title.setText(names.get(position));
                holder.title.setTextColor(Color.rgb(92, 92, 92));
                holder.linearLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                holder.dindicator.setVisibility(View.GONE);
                holder.indicate.setVisibility(View.VISIBLE);
                if (chapterDetails.get(position).getChapterNo() == 0) {
                    holder.titleno.setVisibility(View.GONE);
                } else {
                    holder.titleno.setText(chapterDetails.get(position).getChapterNo() + ".");
                }
            }

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, titleno;
        ImageView indicate, dindicator;
        LinearLayout linearLayout, linearindigator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.chapter_chapterLayout);
            titleno = itemView.findViewById(R.id.chapterNo_chapterLayout);
            indicate = itemView.findViewById(R.id.imageindicate_chaptericon);
            dindicator = itemView.findViewById(R.id.downloadindicate_chaptericon);
            linearLayout = itemView.findViewById(R.id.linearLayout_chapterLayout);
            linearindigator = itemView.findViewById(R.id.Linearindigator_chapterLayout);


            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dindicator.getVisibility()!=View.VISIBLE){
                        if (mListener!=null){
                            int position = getAdapterPosition();
                            if (position!= RecyclerView.NO_POSITION){

                            }
                        }
                    }
                }
            });


            indicate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onPdfView(position);
                        }
                    }
                }
            });
        }
    }
}
