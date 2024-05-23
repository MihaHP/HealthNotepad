package com.paulmy.arraylistview.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulmy.arraylistview.R;
import com.paulmy.arraylistview.data.Product;
import com.paulmy.arraylistview.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList = new ArrayList<>();

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public List<Product> getProductList() {
        return productList;
    }

    interface OnItemProductClickListener {
        void onProductClick(int position);
    }

    private OnItemProductClickListener onItemProductClickListener;

    public void setOnItemProductClickListener(OnItemProductClickListener onItemProductClickListener) {
        this.onItemProductClickListener = onItemProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        return new ProductViewHolder(ListItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);
        holder.binding.tvTitle.setText(product.getTitle());
        holder.binding.tvDescription.setText(product.getDescription());

        holder.binding.getRoot().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemProductClickListener.onProductClick(position);
                    }
                }
        );



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding binding;

        public ProductViewHolder(ListItemBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
