package com.r3tr0.moneyassistant.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.r3tr0.moneyassistant.R;
import com.r3tr0.moneyassistant.logic.managers.InteractionsDatabaseManager;
import com.r3tr0.moneyassistant.core.database.models.ItemsDatabaseModel;
import com.r3tr0.moneyassistant.core.database.models.WalletsDatabaseModel;
import com.r3tr0.moneyassistant.utils.enums.TransitionFlags;
import com.r3tr0.moneyassistant.core.models.Item;
import com.r3tr0.moneyassistant.utils.Regex;
import com.r3tr0.moneyassistant.core.models.Wallet;

import java.util.List;

import CSTime.DateTime;

public class ItemManageActivity extends AppCompatActivity {

    TextView operationTextView;
    EditText nameEditText, quantityEditText, priceEditText;
    Button saveButton;
    Spinner walletsSpinner;

    InteractionsDatabaseManager manager;

    Item item;
    List<Wallet> wallets;

    Thread thread;
    Runnable getWalletsRunnable = new Runnable() {
        @Override
        public void run() {
            wallets = ((WalletsDatabaseModel) manager.getModelByClass(WalletsDatabaseModel.class)).getAllItems();
            ArrayAdapter adapter = new ArrayAdapter<>(ItemManageActivity.this, android.R.layout.simple_list_item_1, wallets);
            walletsSpinner.setAdapter(adapter);
        }
    };

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
                    Item newItem = new Item(0
                            , nameEditText.getText().toString()
                            , Double.parseDouble(priceEditText.getText().toString())
                            , Double.parseDouble(quantityEditText.getText().toString())
                            , DateTime.getNow()
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
        thread = new Thread(getWalletsRunnable);
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
        thread.start();
    }

    @Override
    protected void onPause() {
        this.manager.closeConnection();
        this.manager.clearModels();
        this.manager = null;
        thread = null;
        super.onPause();
    }
}
