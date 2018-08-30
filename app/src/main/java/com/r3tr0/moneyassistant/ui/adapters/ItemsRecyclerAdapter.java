package com.r3tr0.moneyassistant.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public ItemsRecyclerAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
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
        holder.nameTextView.setText(items.get(position).getName());

        holder.informationTextView.setText(String.format("Price : %s\nQuantity : %s"
                , NumberFormat.getCurrencyInstance().format(items.get(position).getPrice())
                , items.get(position).getQuantity()));
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
