package com.r3tr0.moneyassistant.ui.fragments;

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
