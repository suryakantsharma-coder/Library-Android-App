package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> implements Filterable {
    Context context;
    List<searchDataset> list = new ArrayList<>();
    List<searchDataset> backup;
    private OnitemSelected mListener;

    public interface OnitemSelected {
        void OnItemSelected(int position);
    }

    public void setOnItemSelected(OnitemSelected mListener){
        this.mListener = mListener;
    }

    public searchAdapter(Context context, List<searchDataset> list) {
        this.context = context;
        this.list = list;
        backup = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.searchlayout, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new searchAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView, final OnitemSelected mListener) {
            super(itemView);

            textView = itemView.findViewById(R.id.title_searchbar);
            linearLayout = itemView.findViewById(R.id.ClickID_searchLayout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.OnItemSelected(position);
                        }
                    }
                }
            });
        }
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            List<searchDataset> filteredData = new ArrayList<>();

            if (keyword.toString().isEmpty()){
                filteredData.addAll(backup);
            }else {

                for (searchDataset data : backup){

                    if (data.getTitle().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filteredData.add(data);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredData;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((List<searchDataset>)filterResults.values);
            notifyDataSetChanged();

        }
    };
}
