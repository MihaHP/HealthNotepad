package com.paulmy.arraylistview.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.paulmy.arraylistview.data.Product;
import com.paulmy.arraylistview.databinding.ActivityAddNewProductBinding;

public class AddNewProductActivity extends AppCompatActivity {


    private final static String EXTRA_TITLE = "title";
    private final static String EXTRA_ID = "id";
    private final static String EXTRA_PULSE = "pulse";
    private final static String EXTRA_PRES_UP = "pres_up";
    private final static String EXTRA_PRES_DOWN = "pres_down";
    private final static String EXTRA_DR_T = "dr_t";
    private final static String EXTRA_TEMP = "temp";
    private final static String EXTRA_WEIG = "weig";
    private final static String EXTRA_DESCRIPTION = "description";

    private String title;
    private String pulse;
    private String presup;
    private String presdown;
    private String drt;
    private String temp;
    private String weig;
    private String description;
    private String id;
    private AddNewProductViewModel viewModel;
    private ActivityAddNewProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddNewProductViewModel.class);

        viewModel.getShouldClose().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
        title = getIntent().getStringExtra(EXTRA_TITLE);
        pulse = getIntent().getStringExtra(EXTRA_PULSE);
        presup = getIntent().getStringExtra(EXTRA_PRES_UP);
        presdown = getIntent().getStringExtra(EXTRA_PRES_DOWN);
        drt = getIntent().getStringExtra(EXTRA_DR_T);
        temp = getIntent().getStringExtra(EXTRA_TEMP);
        weig = getIntent().getStringExtra(EXTRA_WEIG);
        description = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        id = getIntent().getStringExtra(EXTRA_ID);

        binding.editTextTitle.setText(title);
        binding.editTextPulse.setText(pulse);
        binding.editTextPresUp.setText(presup);
        binding.editTextPresDown.setText(presdown);
        binding.editTextDrT.setText(drt);
        binding.editTextTemp.setText(temp);
        binding.editTextWeig.setText(weig);
        binding.editTextDescription.setText(description);


        binding.btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.editTextTitle.getText().toString();
                String pulse = binding.editTextPulse.getText().toString();
                String presup = binding.editTextPresUp.getText().toString();
                String presdown = binding.editTextPresDown.getText().toString();
                String drt = binding.editTextDrT.getText().toString();
                String temp = binding.editTextTemp.getText().toString();
                String weig = binding.editTextWeig.getText().toString();
                String description = binding.editTextDescription.getText().toString();
                Product product = new Product(title, pulse, presup, presdown, drt, temp, weig, description);

                if (id.equals("0")) {
                    Log.d("LOG", "Create Product" + id + " " + product);

                    viewModel.saveProduct(product);
                  /*  new Thread(new Runnable() {
                        @Override
                        public void run() {
                            repository.productDao().add(product);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        }
                    }).start();*/

                } else {
                    //TODO Добавить метод во ViewModel

                    Log.d("LOG", "UPDATE Product" + id + " " + product);
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            repository.productDao().update(product.getTitle(),
                                    product.getDescription(),
                                    product.getPrice(),
                                    Integer.parseInt(id));
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        }
                    }).start();*/
                }

            }

        });


    }

    public static Intent newIntent(Context context,
                                   String id,
                                   String title,
                                   String pulse,
                                   String presup,
                                   String presdown,
                                   String drt,
                                   String temp,
                                   String weig,
                                   String description) {
        Intent intent = new Intent(context, AddNewProductActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_PULSE, pulse);
        intent.putExtra(EXTRA_PRES_UP, presup);
        intent.putExtra(EXTRA_PRES_DOWN, presdown);
        intent.putExtra(EXTRA_DR_T, drt);
        intent.putExtra(EXTRA_TEMP, temp);
        intent.putExtra(EXTRA_WEIG, weig);
        intent.putExtra(EXTRA_DESCRIPTION, description);


        return intent;
    }


}