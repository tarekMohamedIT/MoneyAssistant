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
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r3tr0.graphengine.core.circles.PieChart;
import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.interfaces.OnItemClickListener;
import com.r3tr0.moneyassistant.core.models.Wallet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class WalletRecyclerAdapter extends RecyclerView.Adapter<WalletRecyclerAdapter.MainViewHolder> {


    private OnItemClickListener onItemClickListener;
    private Context context;
    private ArrayList<Wallet> wallets;

    public WalletRecyclerAdapter(Context context) {
        this.context = context;
        this.wallets = new ArrayList<>();
    }

    public WalletRecyclerAdapter(Context context, ArrayList<Wallet> wallets) {
        this.context = context;
        this.wallets = wallets;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<Wallet> getWallets() {
        return wallets;
    }

    @Override
    public int getItemViewType(int position) {
        return ((position % 2 == 0 || position == 0) ? 0 : 1);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        view = inflater.inflate(R.layout.view_main_pie, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        final int pos = position;

        if (getItemViewType(pos) == 0) {
            GridLayoutManager.LayoutParams p = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.rightMargin = (int) context.getResources().getDimension(R.dimen.items_offset);
            holder.itemView.setLayoutParams(p);
        }

        holder.chart.replaceItems(Arrays.asList(
                new PieChart.PieItem("total", (float) wallets.get(pos).getTotalMoney(), Color.parseColor("#66bb6a"))
                , new PieChart.PieItem("remaining", (float) (wallets.get(pos).getTotalMoney() - wallets.get(pos).getRemainingMoney()), Color.parseColor("#795548"))));

        holder.walletNameTextView.setText(wallets.get(pos).getName());
        holder.walletTypeTextView.setText((wallets.get(pos).isPrimary() ? "primary" : "secondary"));

        holder.walletInfoTextView.setText(String.format("Total:$%s\nRemaining:$%s\nSpent:$%s"
                , wallets.get(position).getTotalMoney()
                , wallets.get(pos).getRemainingMoney()
                , wallets.get(position).getTotalMoney() - wallets.get(pos).getRemainingMoney()));

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(pos, view);
                }
            });
        }
    }

    public void replaceItems(Collection<Wallet> wallets){
        this.wallets.clear();
        this.wallets.addAll(wallets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        TextView walletTypeTextView;
        TextView walletNameTextView;
        TextView walletInfoTextView;
        PieChart chart;

        public MainViewHolder(View itemView) {
            super(itemView);
                chart = itemView.findViewById(R.id.mainPieChart);
            walletTypeTextView = itemView.findViewById(R.id.typeTextView);
                walletNameTextView = itemView.findViewById(R.id.walletNameTextView);
                walletInfoTextView = itemView.findViewById(R.id.walletInfoTextView);
        }
    }
}
