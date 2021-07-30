package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chapterAdapter2 extends RecyclerView.Adapter<chapterAdapter2.ViewHolder> implements Filterable {
    Context context;
    List<String> list = new ArrayList<>();
    private onCLickListener mListener;
    ArrayList<File> fileList = new ArrayList<>();
    ArrayList<serverChapterDataset> backup;
    List<serverChapterDataset> chapterDetails = new ArrayList<>();



    public interface onCLickListener {
         void onPdfView(int position);
    }

    public void setOnItemClickListener(onCLickListener mListener){
        this.mListener = mListener;
    }

    public chapterAdapter2(Context context, List<String> list, ArrayList<File> fileList, List<serverChapterDataset> chapterDetails) {
        this.context = context;
        this.list = list;
        this.fileList = fileList;
        this.chapterDetails = chapterDetails;
        backup = new ArrayList<>(chapterDetails);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chapter_layout,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new chapterAdapter2.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(chapterDetails.get(position).getChapterNo()==0){
            holder.titleno.setText("");
        }else{
            holder.titleno.setText(""+chapterDetails.get(position).getChapterNo()+". ");
            holder.titleno.setTextColor(Color.rgb(92, 92, 92));
        }

        holder.title.setText(chapterDetails.get(position).getName());
        holder.title.setTextColor(Color.rgb(92, 92, 92));
        holder.linearLayout.setBackgroundColor(Color.rgb(210, 210, 210));
        holder.title.setEnabled(true);
        holder.dindicator.setVisibility(View.VISIBLE);
        holder.indicate.setVisibility(View.GONE);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotificationService.class);
                intent.putExtra("fileName", chapterDetails.get(position).getName());
                intent.putExtra("filepath", chapterDetails.get(position).getUrl());
                intent.putExtra("DownloadActivity", false);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, titleno;
        ImageView indicate, dindicator;
        LinearLayout linearLayout, linearindigator;
        public ViewHolder(@NonNull View itemView, final onCLickListener mListener) {
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


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList <serverChapterDataset> filteredList = new ArrayList<>();

            if (keyword.toString().isEmpty()){
                filteredList.addAll(backup);
            }else {
                for (serverChapterDataset obj : backup){
                    if (obj.getName().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filteredList.add(obj);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            chapterDetails.clear();
            chapterDetails.addAll((List<serverChapterDataset>) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
