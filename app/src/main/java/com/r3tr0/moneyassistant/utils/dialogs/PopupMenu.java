package com.r3tr0.moneyassistant.utils.dialogs;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.r3tr0.graphengine.core.popups.PopupView;
import com.r3tr0.moneyassistant.core.interfaces.OnItemClickListener;
import com.r3tr0.moneyassistant.ui.adapters.StringsRecyclerAdapter;
import com.r3tr0.moneyassistant.utils.Converters;

import java.util.ArrayList;

public class PopupMenu extends PopupView{
    private Context context;
    private RecyclerView recyclerView;
    private StringsRecyclerAdapter adapter;
    private OnItemClickListener onItemClickListener;


    public PopupMenu(Context context, View anchorView) {
        super(context, new RecyclerView(context), anchorView);
        this.context = context;
        recyclerView = (RecyclerView) this.popupWindow.getContentView();

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new StringsRecyclerAdapter(context, new ArrayList<String>());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(position, view);
                popupWindow.dismiss();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setParentView(View parentView){
        this.parentView = parentView;
        this.popupWindow.setWidth(this.parentView.getWidth());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(params);
    }

    public StringsRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void showList(int offset){

        showView(Converters.dpToPx(8), parentView.getHeight(), Color.parseColor("#FFFFFF"));
    }
}
