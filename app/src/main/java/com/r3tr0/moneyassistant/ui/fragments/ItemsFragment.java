package com.r3tr0.moneyassistant.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.ui.activities.MainActivity;
import com.r3tr0.moneyassistant.ui.adapters.ItemsRecyclerAdapter;
import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.utils.dialogs.PopupMenu;
import com.r3tr0.moneyassistant.core.models.Item;

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

    Thread databaseHandler;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            model = (ItemsDatabaseModel) ((MainActivity)getActivity()).getDatabaseManager().getModelByClass(ItemsDatabaseModel.class);
            if (model != null && adapter != null){
                final List<Item> items = model.getAllItems();
                itemsRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getItems().clear();
                        adapter.getItems().addAll(items);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
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

        yearsButton.setOnClickListener(this);
        monthsButton.setOnClickListener(this);
        daysButton.setOnClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 3, LinearLayoutManager.VERTICAL, false);

        adapter = new ItemsRecyclerAdapter(this.getContext(), new ArrayList<Item>());

        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setAdapter(adapter);

        databaseHandler = new Thread(runnable);

    }

    @Override
    public void onClick(View v) {
        if (strings == null) strings = new ArrayList<>();

        if (v.getId() == R.id.yearsButton){
            strings.clear();

            strings.add("All");
            strings.add("1990");
            strings.add("1991");
            strings.add("1992");
            strings.add("1993");
            strings.add("1994");
        }

        else if (v.getId() == R.id.monthsButton){
            strings.clear();

            strings.add("All");
            strings.add("Jan");
            strings.add("Feb");
            strings.add("Mar");
            strings.add("Apr");
            strings.add("May");
        }

        else {
            strings.clear();

            strings.add("All");
            strings.add("1");
            strings.add("2");
            strings.add("3");
            strings.add("4");
            strings.add("5");
        }

        menu.getAdapter().getStringsList().clear();
        menu.getAdapter().getStringsList().addAll(strings);
        menu.getAdapter().notifyDataSetChanged();

        menu.setParentView(v);
        menu.showList(300);
    }

    @Override
    public String toString() {
        return "Items";
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
