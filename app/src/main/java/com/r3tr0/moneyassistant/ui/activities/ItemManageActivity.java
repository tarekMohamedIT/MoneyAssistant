package com.r3tr0.moneyassistant.ui.activities;

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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.interfaces.OnProcessingEndListener;
import com.r3tr0.moneyassistant.core.models.Item;
import com.r3tr0.moneyassistant.core.models.Wallet;
import com.r3tr0.moneyassistant.logic.managers.InteractionsDatabaseManager;
import com.r3tr0.moneyassistant.logic.threadding.GetAllWalletsTask;
import com.r3tr0.moneyassistant.utils.Regex;
import com.r3tr0.moneyassistant.utils.enums.TransitionFlags;

import java.util.List;

import CSTime.DateTime;

public class ItemManageActivity extends AppCompatActivity {

    TextView operationTextView;
    EditText nameEditText, quantityEditText, priceEditText;
    Button saveButton;
    Spinner walletsSpinner;

    InteractionsDatabaseManager manager;
    GetAllWalletsTask task;

    Item item;
    List<Wallet> wallets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_manage);
        init();

        TransitionFlags flag = (TransitionFlags) getIntent().getSerializableExtra("flag");

        if (flag == TransitionFlags.flag_new) {
            operationTextView.setText("New item");
            saveButton.setText("Create");
        } else {
            operationTextView.setText("Modify item");
            saveButton.setText("Update");
            item = getIntent().getParcelableExtra("item");
        }
    }

    void init() {
        operationTextView = findViewById(R.id.typeTextView);
        nameEditText = findViewById(R.id.nameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        priceEditText = findViewById(R.id.priceEditText);
        walletsSpinner = findViewById(R.id.walletsSpinner);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().equals("") || !nameEditText.getText().toString().matches(Regex.REGEX_NAME))
                    showAlert("error", "Please enter a valid name!", null);

                else if (priceEditText.getText().toString().equals("") || !priceEditText.getText().toString().matches(Regex.REGEX_MONEY))
                    showAlert("error", "Please enter a valid digit in the price!", null);

                else if (quantityEditText.getText().toString().equals("") || !quantityEditText.getText().toString().matches(Regex.REGEX_MONEY))
                    showAlert("error", "Please enter a valid digit in the quantity!", null);

                else { //data is correct!
                    DateTime purchaseDate = DateTime.getNow();
                    DateTime realDate = new DateTime(purchaseDate.getYears(), purchaseDate.getMonthNumeric(), purchaseDate.getDayOfMonth());
                    Log.e("item date", realDate.getDateTime(true, true));
                    Item newItem = new Item(0
                            , nameEditText.getText().toString()
                            , Double.parseDouble(priceEditText.getText().toString())
                            , Double.parseDouble(quantityEditText.getText().toString())
                            , realDate
                            , wallets.get(walletsSpinner.getSelectedItemPosition()).getWalletID());

                    if (item != null) { //Item update process
                        ((ItemsDatabaseModel) manager.getModelByClass(ItemsDatabaseModel.class)).replaceItem(item.getId(), newItem);
                        showAlert("Done", "Wallet is updated", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    } else { //New item
                        manager.addItemToWallet(newItem, newItem.getWalletID());
                        showAlert("Done", "The item is added", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    }
                }

            }
        });

    }

    void initWallets() {
        manager = new InteractionsDatabaseManager(this);
        task = new GetAllWalletsTask((WalletsDatabaseModel) manager.getModelByClass(WalletsDatabaseModel.class));
        task.setOnProcessingEndListener(new OnProcessingEndListener<List<Wallet>>() {
            @Override
            public void onProcessingEnd(List<Wallet> walletsList) {
                wallets = walletsList;
                ArrayAdapter adapter = new ArrayAdapter<>(ItemManageActivity.this, android.R.layout.simple_list_item_1, wallets);
                walletsSpinner.setAdapter(adapter);
            }
        });
        task.execute();
    }

    void showAlert(String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Ok", listener)
                .create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWallets();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
