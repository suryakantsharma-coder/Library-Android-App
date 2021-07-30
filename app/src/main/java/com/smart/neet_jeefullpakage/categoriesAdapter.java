package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class categoriesAdapter extends RecyclerView.Adapter<com.smart.neet_jeefullpakage.categoriesAdapter.ViewHolder> {
    Context context;
    String [] titel ;
    int [] icons ;
    private onItemClick mListener;

    public interface onItemClick {
        void onItemClick(int postion);
    }

    public void setOnItemClickListener(onItemClick mListener){
        this.mListener = mListener;
    }

    public categoriesAdapter(Context context, String[] titel, int[] icons) {
        this.context = context;
        this.titel = titel;
        this.icons = icons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categories_layout,null);
        return new categoriesAdapter.ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titel.setText(titel[position]);
        Picasso.get().load(icons[position]).fit().into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return titel.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView titel;
        public ViewHolder(@NonNull View itemView, final onItemClick mListener) {
            super(itemView);

            icon = itemView.findViewById(R.id.imageView_categories);
            titel = itemView.findViewById(R.id.nameCategories);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
