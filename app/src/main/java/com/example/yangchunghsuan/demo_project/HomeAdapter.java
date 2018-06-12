package com.example.yangchunghsuan.demo_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private List<HomeInfo> items;
    private int lastPosition = -1;
    private Context context;


    public HomeAdapter(List<HomeInfo> items,Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        HomeInfo homeInfo = items.get(position);
        holder.imageView.setImageBitmap(homeInfo.getBitmap());
        holder.textView_name.setText(homeInfo.getId());
        holder.textView_address.setText(homeInfo.getAddress());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name;
        TextView textView_address;
        ImageView imageView;

        protected View rootView;

        public ViewHolder(View view) {
            super(view);
            imageView = itemView.findViewById(R.id.imageView);
            textView_name = itemView.findViewById(R.id.textView_store_name);
            textView_address = itemView.findViewById(R.id.textView_address_content);

            rootView = view;
        }
    }

    public void add(HomeInfo homeInfo){
        items.add(homeInfo);
    }

}
