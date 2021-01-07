package com.madhumankatha.plantas.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.ProductsItemholderBinding;
import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.utlis.AppUtils;
import com.madhumankatha.plantas.utlis.RecyclerViewClickInterface;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolders> {
    private List<Products> productsList;
    private RecyclerViewClickInterface clickInterface;
    private Context context;

    public ProductsAdapter(List<Products> productsList, RecyclerViewClickInterface clickInterface, Context context) {
        this.productsList = productsList;
        this.clickInterface = clickInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProductsItemholderBinding binding = ProductsItemholderBinding.inflate(inflater,parent,false);
        return new ViewHolders(binding);
    }

    public Products getArticleAt(int position){
        return productsList.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        Glide.with(context)
                .load(AppUtils.IMAGE_PATH + productsList.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.image_eeror)
                .error(R.drawable.image_eeror)
                .fallback(R.drawable.image_eeror)
                .into(holder.binding.imageView);

        holder.binding.tvTitle.setText(productsList.get(position).getName());
        holder.binding.tvPrice.setText("₹ " + productsList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        private ProductsItemholderBinding binding;

        public ViewHolders(@NonNull ProductsItemholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(view -> {
                clickInterface.onItemClickListener(view,getAdapterPosition());
            });
        }
    }
}
