package com.paulmy.arraylistview.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.paulmy.arraylistview.data.Product;
import com.paulmy.arraylistview.databinding.ActivityMainBinding;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProductAdapter arrayAdapter;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;


    List<Product> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  handler = new Handler(Looper.getMainLooper());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);



        arrayAdapter = new ProductAdapter();

        viewModel.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                tempList = products;
                arrayAdapter.setProductList(products);
            }
        });





        binding.recyclerView.setAdapter(arrayAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Product product = arrayAdapter.getProductList().get(position);
                        showAlert(product);

                    }
                });
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        arrayAdapter.setOnItemProductClickListener(new ProductAdapter.OnItemProductClickListener() {
            @Override
            public void onProductClick(int position) {
                //  showAlert(position);
            }
        });

        binding.edittextList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> searchList = new LinkedList<>();
                for (int i = 0; i < tempList.size(); i++) {
                    if (tempList.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase()) ||
                            tempList.get(i).getDescription().toLowerCase().contains(s.toString().toLowerCase()))
                        searchList.add(tempList.get(i));
                }
                arrayAdapter.setProductList(searchList);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addListData();
        addSortList();
    }


    public void showAlert(Product product) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Удаление элемента")
                .setMessage("Удалить элемент " + product.getTitle() + "?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.remove(product);

                    }
                })
                .setNeutralButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = AddNewProductActivity.newIntent(MainActivity.this,
                                String.valueOf(product.getId()),
                                product.getTitle(),
                                product.getPulse(),
                                product.getPresup(),
                                product.getPresdown(),
                                product.getDrt(),
                                product.getTemp(),
                                product.getWeig(),
                                product.getDescription());

                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }


    private void addSortList() {

        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*  new Thread(new Runnable() {
                    @Override
                    public void run() {

                        tempList.sort(new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o2.getTitle().compareTo(o1.getTitle());
                            }
                        });
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                arrayAdapter.setProductList(tempList);
                            }
                        });

                    }
                }).start();*/


            }
        });

    }

    private void addListData() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNewProductActivity.newIntent(MainActivity.this,
                        String.valueOf(0), "", "", "", "", "", "", "", "");
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshList();
    }
}