package com.example.yangchunghsuan.demo_project;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//        holder.textView.setText( homeInfo.getId());
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
        protected TextView textView;

        protected View rootView;

        public ViewHolder(View view) {
            super(view);

//            textView = itemView.findViewById(R.id.textView);
            rootView = view;
        }
    }

    public void add(HomeInfo homeInfo){
        items.add(homeInfo);
    }

}
