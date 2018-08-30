package com.r3tr0.moneyassistant.utils.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.r3tr0.graphengine.core.popups.PopupView;
import com.r3tr0.moneyassistant.ui.adapters.StringsRecyclerAdapter;
import com.r3tr0.moneyassistant.core.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class PopupMenu extends PopupView{
    private Context context;
    private RecyclerView recyclerView;
    private StringsRecyclerAdapter adapter;

    public PopupMenu(Context context, View anchorView) {
        super(context, new RecyclerView(context), anchorView);
        this.context = context;
        recyclerView = (RecyclerView) this.popupWindow.getContentView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new StringsRecyclerAdapter(context, new ArrayList<String>());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                popupWindow.dismiss();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void setParentView(View parentView){
        this.parentView = parentView;
    }

    public StringsRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void showList(int offset){

        showView(parentView.getWidth() - offset, parentView.getHeight(), Color.parseColor("#FFFFFF"));
    }
}
