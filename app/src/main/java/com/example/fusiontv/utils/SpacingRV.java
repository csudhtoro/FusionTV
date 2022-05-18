package com.example.fusiontv.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingRV extends RecyclerView.ItemDecoration {
    private final int horizontalSpaceWidthL;
    private final int horizontalSpaceWidthR;

    public SpacingRV(int horizontalSpaceWidthL, int horizontalSpaceWidthR) {
        this.horizontalSpaceWidthL = horizontalSpaceWidthL;
        this.horizontalSpaceWidthR = horizontalSpaceWidthR;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = horizontalSpaceWidthL;
        outRect.right = horizontalSpaceWidthR;
    }
}
