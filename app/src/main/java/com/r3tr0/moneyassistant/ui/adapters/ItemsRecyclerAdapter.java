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
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.models.Item;

import java.text.NumberFormat;
import java.util.List;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ItemsViewHolder>{
    Context context;
    List<Item> items;
    LayoutInflater inflater;
    boolean useMargin = false;

    public ItemsRecyclerAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems().get(position).getName().equals("") ? 2 : 1;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ItemsViewHolder(inflater.inflate(R.layout.view_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        GridLayoutManager.LayoutParams p = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();

        if (getItemViewType(position) == 1) {
            holder.nameTextView.setText(items.get(position).getName());

            holder.informationTextView.setVisibility(View.VISIBLE);
            holder.informationTextView.setText(String.format("Price : %s\nQuantity : %s"
                    , NumberFormat.getCurrencyInstance().format(items.get(position).getPrice())
                    , items.get(position).getQuantity()));

            if (useMargin) {
                p.rightMargin = (int) context.getResources().getDimension(R.dimen.items_offset);
                useMargin = !useMargin;
            } else {
                useMargin = !useMargin;
            }
        } else {
            holder.nameTextView.setText(String.format("On %s you bought :", items.get(position).getPurchaseDate().getShortDate()));
            holder.informationTextView.setVisibility(View.GONE);
            useMargin = true;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView informationTextView;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.itemNameTextView);
            informationTextView = itemView.findViewById(R.id.informationTextView);
        }
    }
}
