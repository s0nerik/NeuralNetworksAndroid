package com.sonerik.neuralnetworksandroid.adapters
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectViews
import com.sonerik.neuralnetworksandroid.R
import groovy.transform.CompileStatic

@CompileStatic
public class TableAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context
    List<String[]> rows

    private int width
    private LayoutInflater inflater

    TableAdapter(Context context, List<String[]> rows) {
        this.context = context
        this.rows = rows
        width = rows[0].size()
        inflater = LayoutInflater.from(context)
    }

    @Override
    ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.item_table, viewGroup, false))
    }

    @Override
    void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.items.eachWithIndex { TextView item, index ->
            if (index < width) {
                item.setText(rows[i][index].toString())
                item.visibility = View.VISIBLE
            } else {
                item.visibility = View.GONE
            }
        }
        viewHolder.itemView.backgroundResource = i % 2 ? R.color.md_grey_100 : R.color.md_grey_300
    }

    @Override
    int getItemCount() {
        return rows.size()
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectViews([
            R.id.text0,
            R.id.text1,
            R.id.text2,
            R.id.text3,
            R.id.text4,
            R.id.text5,
            R.id.text6,
            R.id.text7,
            R.id.text8,
            R.id.text9
        ])
        ArrayList<TextView> items

        ViewHolder(View itemView) {
            super(itemView)
            SwissKnife.inject(this, itemView)
        }
    }

}
