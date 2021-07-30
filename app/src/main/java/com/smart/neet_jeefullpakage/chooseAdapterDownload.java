package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chooseAdapterDownload extends RecyclerView.Adapter<chooseAdapterDownload.ViewHolder> {

    Context context;
    List<fileModel> modelList;
    private onItemClickListener  mListener;



    public interface onItemClickListener{
        void onItemCLick(int position);
        void onItemRemove(int position);
    }

    public void setonItemClickListener (onItemClickListener mListener){
        this.mListener = mListener;
    }

    public chooseAdapterDownload(Context context, List<fileModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_download,null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new chooseAdapterDownload.ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (modelList.get(position).isFolder){
            holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
            holder.textView.setText(modelList.get(position).getFile().getName());
            holder.remove.setVisibility(View.GONE);
        }else {
            if (modelList.get(position).getFile().getName().endsWith(".pdf")) {
                holder.imageView.setImageResource(R.drawable.pdficon);
                holder.textView.setText(modelList.get(position).getFile().getName());
                holder.remove.setVisibility(View.VISIBLE);
            }else{
                holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
                holder.textView.setText(modelList.get(position).getFile().getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, remove;
        TextView textView;

        public ViewHolder(@NonNull View itemView, onItemClickListener mListener) {
            super(itemView);

            textView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
            remove = itemView.findViewById(R.id.remove);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onItemCLick(position);
                        }
                    }
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemRemove(position);
                        }
                    }
                }
            });

        }
    }

}
