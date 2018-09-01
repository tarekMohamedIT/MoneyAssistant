package com.r3tr0.moneyassistant.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.OnProcessingEndListener;
import com.r3tr0.moneyassistant.core.models.Wallet;
import com.r3tr0.moneyassistant.logic.threadding.GetAllWalletsTask;
import com.r3tr0.moneyassistant.ui.activities.MainActivity;
import com.r3tr0.moneyassistant.ui.adapters.WalletRecyclerAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {
    RecyclerView recyclerView;
    WalletRecyclerAdapter adapter;
    WalletsDatabaseModel model;
    GetAllWalletsTask task;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        init(view);
        return view;
    }
    public void init(View view){
        recyclerView = view.findViewById(R.id.mainRecyclerView);


        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 2, LinearLayoutManager.VERTICAL, false);

        adapter = new WalletRecyclerAdapter(this.getContext());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        model = (WalletsDatabaseModel) ((MainActivity) getActivity()).getDatabaseManager().getModelByClass(WalletsDatabaseModel.class);
        task = new GetAllWalletsTask(model);

        task.setOnProcessingEndListener(new OnProcessingEndListener<List<Wallet>>() {
            @Override
            public void onProcessingEnd(List<Wallet> wallets) {
                adapter.replaceItems(wallets);
            }
        });
    }

    @Override
    public String toString() {
        return "Wallets";
    }

    @Override
    public void onResume() {
        super.onResume();
        task = new GetAllWalletsTask(model);
        task.setOnProcessingEndListener(new OnProcessingEndListener<List<Wallet>>() {
            @Override
            public void onProcessingEnd(List<Wallet> wallets) {
                adapter.replaceItems(wallets);
            }
        });
        task.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
        task = null;
    }
}
