package com.r3tr0.moneyassistant.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.r3tr0.moneyassistant.logic.managers.MultiModelDatabaseManager;
import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.utils.enums.TransitionFlags;
import com.r3tr0.moneyassistant.ui.fragments.ItemsFragment;
import com.r3tr0.moneyassistant.ui.fragments.WalletFragment;
import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.ui.adapters.MainPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabLayout;
    MainPagerAdapter adapter;
    ImageView addButton;
    MultiModelDatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init(){
        this.pager = findViewById(R.id.mainPager);
        this.tabLayout = findViewById(R.id.mainTabLayout);
        this.addButton = findViewById(R.id.addButton);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new WalletFragment());
        fragments.add(new ItemsFragment());

        this.adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){//Regex
                    addButton.setImageResource(R.drawable.add_wallet);
                }

                else {//items
                    addButton.setImageResource(R.drawable.add_icon);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (tabLayout.getSelectedTabPosition() == 0){
                    intent = new Intent(MainActivity.this, WalletManageActivity.class);
                    intent.putExtra("flag", TransitionFlags.flag_new);
                    startActivity(intent);
                }

                else {
                    intent = new Intent(MainActivity.this, ItemManageActivity.class);
                    intent.putExtra("flag", TransitionFlags.flag_new);
                    startActivity(intent);
                }
            }
        });

    }

    void initDatabase(){
        databaseManager = new MultiModelDatabaseManager(this);
        databaseManager.addNewModel(new ItemsDatabaseModel(databaseManager));
        databaseManager.addNewModel(new WalletsDatabaseModel(databaseManager));
    }

    public MultiModelDatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    protected void onPause() {
        this.databaseManager.closeConnection();
        this.databaseManager.clearModels();
        this.databaseManager = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatabase();
    }
}
