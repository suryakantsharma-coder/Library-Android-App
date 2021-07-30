package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class filterDownloadAdapter extends RecyclerView.Adapter<filterDownloadAdapter.ViewHolder> implements Filterable {

    Context context;
    List<fileModel> mlist = new ArrayList<>();
    List<fileModel> backup ;
    private onClickListener mListener;



    public interface onClickListener {
        void onClickListener(int position);
        void onClickDelete(int position);
    }

    public void setOnClickListener(onClickListener mListener){
        this.mListener = mListener;
    }

    public filterDownloadAdapter(Context context, List<fileModel> mlist) {
        this.context = context;
        this.mlist = mlist;
        backup = new ArrayList<>(mlist);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.download_layout, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new filterDownloadAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mlist.get(position).getFile().getName());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView indicate, dindicator;
        LinearLayout linearLayout, linearindigator;
        public ViewHolder(@NonNull View itemView, final onClickListener mListener) {
            super(itemView);


            title = itemView.findViewById(R.id.chapter_chapterLayout);
            indicate = itemView.findViewById(R.id.imageindicate_chaptericon);
            dindicator = itemView.findViewById(R.id.downloadindicate_chaptericon);
            linearLayout = itemView.findViewById(R.id.linearLayout_chapterLayout);
            linearindigator = itemView.findViewById(R.id.Linearindigator_chapterLayout);


            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onClickListener(position);
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
                            mListener.onClickDelete(position);
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
            ArrayList<fileModel> filteredList = new ArrayList<>();

            if (keyword.toString().isEmpty()){
                filteredList.addAll(backup);
            }else {
                for (fileModel file : backup){
                    if (file.getFile().getName().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filteredList.add(file);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mlist.clear();
            mlist.addAll((List<fileModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
