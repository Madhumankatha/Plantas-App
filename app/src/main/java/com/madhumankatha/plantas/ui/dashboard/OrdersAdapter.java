package com.madhumankatha.plantas.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.internal.$Gson$Preconditions;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.OrdersItemholderBinding;
import com.madhumankatha.plantas.databinding.ProductsItemholderBinding;
import com.madhumankatha.plantas.models.Orders;
import com.madhumankatha.plantas.ui.home.ProductsAdapter;
import com.madhumankatha.plantas.utlis.AppUtils;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private List<Orders> orders;
    private Context context;

    public OrdersAdapter(List<Orders> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OrdersItemholderBinding binding = OrdersItemholderBinding.inflate(inflater,parent,false);
        return new OrdersAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(AppUtils.IMAGE_PATH + orders.get(position).getProducts().getImage())
                .centerCrop()
                .placeholder(R.drawable.image_eeror)
                .error(R.drawable.image_eeror)
                .fallback(R.drawable.image_eeror)
                .into(holder.binding.imdOrderHead);

        holder.binding.tvOrderTitle.setText(orders.get(position).getProducts().getName());
        holder.binding.tvOrderPrice.setText("₹ " + orders.get(position).getProducts().getPrice());
        holder.binding.tvOrderQty.setText("Qty : x" + orders.get(position).getTotal());
        holder.binding.tvOrderStatus.setText(orders.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private OrdersItemholderBinding binding;

        public ViewHolder(@NonNull OrdersItemholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
