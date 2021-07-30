package com.smart.neet_jeefullpakage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class slideShowAdapter extends SliderViewAdapter<slideShowAdapter.SliderAdapterVH> {

    Context context;
    List<importantDataset> list = new ArrayList<>();
    int [] background = {R.raw.b1,R.raw.b2,R.raw.b3,R.raw.b4,R.raw.b5,R.raw.b6,R.raw.b7,R.raw.b1};



    public slideShowAdapter(Context context, List<importantDataset> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.slider_layout,null);
        return new SliderAdapterVH (view);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        viewHolder.title.setText(list.get(position).getTitle());
        Picasso.get().load(list.get(position).getCover()).fit().into(viewHolder.bookCover);
        Picasso.get().load(background[position]).fit().into(viewHolder.background);

        viewHolder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,chapterActivity.class);
                intent.putExtra("categories",list.get(position).getCategories());
                intent.putExtra("bookName",list.get(position).getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView background;
        ImageView bookCover;
        TextView title, description;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.backgroundImage_slideLayout);
            bookCover = itemView.findViewById(R.id.bookCover_slideLayout);
            title = itemView.findViewById(R.id.title_slideLayout);
        }
    }
}


