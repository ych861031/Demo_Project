package com.example.yangchunghsuan.demo_project;

public interface ItemTouchHelperAdapter {
    // 長按並移動項目位置
    void onItemMove(int fromPosition, int toPosition);

    // 左右滑動項目
    void onItemDismiss(int position);

}
