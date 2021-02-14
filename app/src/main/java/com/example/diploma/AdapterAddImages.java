package com.example.diploma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAddImages extends RecyclerView.Adapter<AdapterAddImages.MyViewHolder> {
    Context context;
    List<ModalAddImages> mList;


    public AdapterAddImages(Context context, List<ModalAddImages> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public AdapterAddImages.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fileaddimages, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddImages.MyViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).getImagename());
        holder.imageView.setImageURI(mList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.filename);
            imageView = itemView.findViewById(R.id.icon);
        }
    }
}
