package com.example.fusiontv.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingRVVertical extends RecyclerView.ItemDecoration {
    private final int verticalSpaceT;
    private final int verticalSpaceB;

    public SpacingRVVertical(int verticalSpaceT, int verticalSpaceB) {
        this.verticalSpaceT = verticalSpaceT;
        this.verticalSpaceB = verticalSpaceB;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = verticalSpaceT;
        outRect.bottom = verticalSpaceB;
    }
}
