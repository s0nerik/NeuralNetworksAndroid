package com.sonerik.neuralnetworksandroid.views

import android.content.Context
import android.util.AttributeSet
import com.sonerik.neuralnetworksandroid.R
import groovy.transform.CompileStatic

@CompileStatic
class RecyclerView extends android.support.v7.widget.RecyclerView {
    RecyclerView(Context context) {
        super(context)
    }

    RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs)
    }

    RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle)
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, childCount * resources.getDimension(R.dimen.table_item_height) as int)
    }
}