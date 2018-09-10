package com.r3tr0.moneyassistant.ui.adapters;

/**
 * Copyright 2018 Tarek Mohamed
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.interfaces.OnItemClickListener;

import java.util.List;

import static com.r3tr0.moneyassistant.utils.Converters.dpToPx;

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
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = dpToPx(16);
        params.rightMargin = dpToPx(16);
        params.topMargin = dpToPx(4);
        params.bottomMargin = dpToPx(4);
        Log.e("test margins", dpToPx(8) + " : " + context.getResources().getDimension(R.dimen.items_offset));
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
