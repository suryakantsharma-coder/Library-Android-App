package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class importantAdaptar extends RecyclerView.Adapter<importantAdaptar.ViewHolder> implements Filterable {
    Context context;
    List<importantDataset> bookList = new ArrayList<>();
    List<importantDataset> backup ;
    private String layoutType;
    private onCLickListener mListener;


    public interface onCLickListener {
        public void onBookCLick(int position);
    }

    public void setOnItemClickListener(onCLickListener mListener){
        this.mListener = mListener;
    }

    public importantAdaptar(Context context, List<importantDataset> bookList, String layoutType) {
        this.context = context;
        this.bookList = bookList;
        this.layoutType = layoutType;
        backup = new ArrayList<>(bookList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (layoutType.equals("grid")) {
            view = inflater.inflate(R.layout.book_list_layout, null);
        }else if (layoutType.equals("vertical")){
            view = inflater.inflate(R.layout.book_list_layout_vertical, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
        }
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);
        return new importantAdaptar.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titel.setText(bookList.get(position).getTitle());
        Picasso.get().load(bookList.get(position).getCover()).placeholder(R.drawable.loading).fit().into(holder.thumbnails);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnails;
        TextView titel,description;
        public ViewHolder(@NonNull View itemView, final onCLickListener mListener) {
            super(itemView);
            thumbnails = itemView.findViewById(R.id.imageView_booklist);
            titel = itemView.findViewById(R.id.bookname_booklist);

            titel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onBookCLick(position);
                        }
                    }
                }
            });

            thumbnails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onBookCLick(position);
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
            List<importantDataset> filteredList = new ArrayList<>();

            if (keyword.toString().toLowerCase().isEmpty()){
                filteredList.addAll(backup);
            }else {

                for (importantDataset data : backup){
                    if (data.getTitle().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filteredList.add(data);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            bookList.clear();
            bookList.addAll((ArrayList<importantDataset>)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
