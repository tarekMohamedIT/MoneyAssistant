package com.r3tr0.moneyassistant.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.r3tr0.moneyassistant.core.interfaces.OnItemClickListener;

import java.util.List;

public class StringsRecyclerAdapter extends RecyclerView.Adapter<StringsRecyclerAdapter.StringViewHolder>{
    Context context;
    List<String> stringsList;
    OnItemClickListener onItemClickListener;

    public StringsRecyclerAdapter(Context context, List<String> stringsList) {
        this.context = context;
        this.stringsList = stringsList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<String> getStringsList() {
        return stringsList;
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 24;
        params.rightMargin = 24;
        textView.setLayoutParams(params);
        layout.addView(textView);
        return new StringViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        final int pos = position;
        holder.textView.setText(stringsList.get(position));
        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(pos, null);
                }
            });
    }

    @Override
    public int getItemCount() {
        return stringsList.size();
    }

    public static class StringViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public StringViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) ((ViewGroup)itemView).getChildAt(0);
        }
    }
}
