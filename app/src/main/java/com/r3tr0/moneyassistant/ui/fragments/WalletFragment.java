package com.r3tr0.moneyassistant.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.ui.activities.MainActivity;
import com.r3tr0.moneyassistant.ui.adapters.WalletRecyclerAdapter;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.models.Wallet;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {
    RecyclerView recyclerView;
    WalletRecyclerAdapter adapter;
    WalletsDatabaseModel model;
    List<Wallet> wallets;
    Thread databaseHandler;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            model = (WalletsDatabaseModel) ((MainActivity)getActivity()).getDatabaseManager().getModelByClass(WalletsDatabaseModel.class);
            if (model != null && adapter != null){
                wallets = model.getAllItems();
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.replaceItems(wallets);
                    }
                });
            }
        }
    };

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


        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new WalletRecyclerAdapter(this.getContext());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        databaseHandler = new Thread(runnable);
    }

    @Override
    public String toString() {
        return "Wallets";
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseHandler = new Thread(runnable);
        databaseHandler.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (databaseHandler != null) {
            databaseHandler.interrupt();
            databaseHandler = null;
        }
    }
}
