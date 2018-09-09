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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.OnItemClickListener;
import com.r3tr0.moneyassistant.core.interfaces.OnProcessingEndListener;
import com.r3tr0.moneyassistant.core.models.Item;
import com.r3tr0.moneyassistant.logic.threadding.GetAllItemsTask;
import com.r3tr0.moneyassistant.ui.activities.MainActivity;
import com.r3tr0.moneyassistant.ui.adapters.ItemsRecyclerAdapter;
import com.r3tr0.moneyassistant.utils.dialogs.PopupMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment implements View.OnClickListener {
    Button daysButton, monthsButton, yearsButton;
    RecyclerView itemsRecyclerView;
    ItemsRecyclerAdapter adapter;
    PopupMenu menu;
    ArrayList<String> strings;
    ItemsDatabaseModel model;
    GetAllItemsTask task;

    int year = 0, month = 0, day = 0;

    OnProcessingEndListener<List<Item>> listOnProcessingEndListener = new OnProcessingEndListener<List<Item>>() {
        @Override
        public void onProcessingEnd(List<Item> items) {
            adapter.getItems().clear();
            adapter.getItems().addAll(items);
            adapter.notifyDataSetChanged();
        }
    };


    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        init(view);

        return view;
    }

    void init(View view){
        yearsButton = view.findViewById(R.id.yearsButton);
        monthsButton = view.findViewById(R.id.monthsButton);
        daysButton = view.findViewById(R.id.daysButton);
        itemsRecyclerView = view.findViewById(R.id.itemsRecyclerView);

        menu = new PopupMenu(getContext(), null);

        model = (ItemsDatabaseModel) ((MainActivity) getActivity()).getDatabaseManager().getModelByClass(ItemsDatabaseModel.class);

        yearsButton.setOnClickListener(this);
        monthsButton.setOnClickListener(this);
        daysButton.setOnClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position);
            }
        });

        adapter = new ItemsRecyclerAdapter(this.getContext(), new ArrayList<Item>());

        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (strings == null) strings = new ArrayList<>();

        if (v.getId() == R.id.yearsButton){
            strings.clear();
            strings = (ArrayList<String>) model.getAllYears();
            menu.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(int position, View view) {
                    if (position == 0) {
                        year = 0;
                        month = 0;
                        day = 0;

                        daysButton.setText("All");
                        monthsButton.setText("All");
                        yearsButton.setText("All");
                    } else {
                        year = Integer.parseInt(strings.get(position));
                        month = 0;
                        day = 0;

                        daysButton.setText("All");
                        monthsButton.setText("All");
                        yearsButton.setText(String.format("%s", year));
                    }
                    fetchData();
                }
            });
        }

        else if (v.getId() == R.id.monthsButton){
            strings.clear();
            if (year == 0)
                strings.add("all");
            else {
                strings = (ArrayList<String>) model.getMonths(year);
                menu.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(int position, View view) {
                        if (position == 0) {
                            month = 0;
                            day = 0;

                            daysButton.setText("All");
                            monthsButton.setText("All");
                        } else {
                            month = Integer.parseInt(strings.get(position));
                            day = 0;

                            daysButton.setText("All");
                            monthsButton.setText(String.format("%02d", month));
                        }
                        fetchData();
                    }
                });
            }
        }

        else {
            strings.clear();
            if (month == 0)
                strings.add("all");
            else {
                strings = (ArrayList<String>) model.getDays(year, month);
                menu.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(int position, View view) {
                        if (position == 0) {
                            day = 0;
                            daysButton.setText("All");
                        } else {
                            day = Integer.parseInt(strings.get(position));
                            daysButton.setText(String.format("%02d", day));
                        }
                        fetchData();
                    }
                });
            }
        }

        menu.getAdapter().getStringsList().clear();
        menu.getAdapter().getStringsList().addAll(strings);
        menu.getAdapter().notifyDataSetChanged();

        menu.setParentView(v);
        menu.showList(300);
    }

    void fetchData() {
        Log.e("fetching", "fetched from " + day + "-" + month + "-" + year);
        if (task != null)
            task.cancel(true);
        task = new GetAllItemsTask(model, year, month, day);
        task.setOnProcessingEndListener(listOnProcessingEndListener);
        task.execute();
    }

    @Override
    public String toString() {
        return "Items";
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
        task = null;
    }
}
