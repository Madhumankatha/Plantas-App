package com.madhumankatha.plantas.utlis;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by madhu on 24-03-2018.
 */

public class VerticalSpaceItemDeco extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDeco(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {

        // 1. Determine if we want to add a spacing decorator
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {

            // 2. Set the bottom offset to the specified height
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
