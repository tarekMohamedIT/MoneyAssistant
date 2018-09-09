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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.core.models.Wallet;
import com.r3tr0.moneyassistant.logic.managers.DatabaseManager;
import com.r3tr0.moneyassistant.utils.Regex;
import com.r3tr0.moneyassistant.utils.enums.TransitionFlags;

public class WalletManageActivity extends AppCompatActivity {

    TextView operationTextView;
    EditText nameEditText, totalEditText, remainingEditText;
    CheckBox primaryCheckBox;
    Button saveButton;

    Wallet wallet;

    DatabaseManager manager;
    WalletsDatabaseModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallets_manage);
        init();

        TransitionFlags flag = (TransitionFlags) getIntent().getSerializableExtra("flag");

        if (flag == TransitionFlags.flag_new){
            operationTextView.setText("New wallet");
            saveButton.setText("Create");
        }

        else {
            operationTextView.setText("Modify wallet");
            saveButton.setText("Update");
            wallet = getIntent().getParcelableExtra("wallet");
        }

    }

    void init(){
        operationTextView = findViewById(R.id.typeTextView);
        nameEditText = findViewById(R.id.nameEditText);
        totalEditText = findViewById(R.id.totalEditText);
        remainingEditText = findViewById(R.id.remainingEditText);
        primaryCheckBox = findViewById(R.id.primaryCheckBox);
        saveButton = findViewById(R.id.saveButton);

        manager = new DatabaseManager(this);
        model = new WalletsDatabaseModel(manager);
        manager.setModel(model);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().equals("") || !nameEditText.getText().toString().matches(Regex.REGEX_NAME))
                    showAlert("error", "Please enter a valid name!", null);

                else if (totalEditText.getText().toString().equals("") || !totalEditText.getText().toString().matches(Regex.REGEX_MONEY))
                    showAlert("error", "Please enter a valid digit in the total balance!", null);

                else { //data is correct!
                    Wallet newWallet = new Wallet(nameEditText.getText().toString()
                            , Double.parseDouble(totalEditText.getText().toString().replace("$", ""))
                            , Double.parseDouble(remainingEditText.getText().toString().replace("$", ""))
                            , 0.0d
                            , primaryCheckBox.isChecked());

                    if (wallet != null) { //wallets update process
                        if (!newWallet.getName().equals(wallet.getName()) && ((WalletsDatabaseModel)manager.getModel()).getWalletByName(newWallet.getName()) != null)
                            showAlert("error", "This wallet name already exists!", null);

                        else {
                            ((WalletsDatabaseModel)manager.getModel()).replaceItem(wallet.getWalletID(), newWallet);
                            showAlert("Done", "Wallet is updated", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }
                    }

                    else {
                        if (((WalletsDatabaseModel)manager.getModel()).getWalletByName(newWallet.getName()) != null)
                            showAlert("error", "This wallet name already exists!", null);

                        else {
                            ((WalletsDatabaseModel)manager.getModel()).addNew(newWallet);
                            showAlert("Done", "Wallet is created", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }
                    }
                }

            }
        });
    }

    void showAlert(String title, String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Ok", listener)
                .create().show();
    }
}
