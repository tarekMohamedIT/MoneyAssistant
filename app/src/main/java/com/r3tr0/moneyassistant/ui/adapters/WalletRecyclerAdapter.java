package com.r3tr0.moneyassistant.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

/**
 * Created by r3tr0 on 3/26/18.
 */

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

        holder.chart.replaceItems(Arrays.asList(
                new PieChart.PieItem("total", (float) wallets.get(pos).getTotalMoney(), Color.parseColor("#66bb6a"))
                , new PieChart.PieItem("remaining", (float) (wallets.get(pos).getTotalMoney() - wallets.get(pos).getRemainingMoney()), Color.parseColor("#795548"))));

        holder.walletNameTextView.setText(String.format("<%s>\n%s", (wallets.get(pos).isPrimary()? "primary" : "secondary"), wallets.get(pos).getName()));

        holder.walletInfoTextView.setText(String.format("Total:$%s\nRemaining(Cyan):$%s\nSpent(Gray):$%s"
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

        TextView walletNameTextView;
        TextView walletInfoTextView;
        PieChart chart;

        public MainViewHolder(View itemView) {
            super(itemView);
                chart = itemView.findViewById(R.id.mainPieChart);
                walletNameTextView = itemView.findViewById(R.id.walletNameTextView);
                walletInfoTextView = itemView.findViewById(R.id.walletInfoTextView);
        }
    }
}
