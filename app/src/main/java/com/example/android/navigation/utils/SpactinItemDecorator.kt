package com.example.android.navigation.utils

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class SpactinItemDecorator: RecyclerView.ItemDecoration {
    private var verticalSpaceHeight:Int=12

    constructor(verticalSpaceHeigh: Int){
        this.verticalSpaceHeight=verticalSpaceHeight
    }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom=verticalSpaceHeight

    }
}