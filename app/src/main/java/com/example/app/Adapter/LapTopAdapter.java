package com.example.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.Activity.DienThoaiChiTietAcivity;
import com.example.app.Activity.LapTopChiTietActivity;
import com.example.app.Interface.ItemClickListener;
import com.example.app.Model.SanPhamMoi;
import com.example.app.R;

import java.text.DecimalFormat;
import java.util.List;

public class LapTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SanPhamMoi> array;
    //nút progressBar xoay vòng
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public LapTopAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laptop, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SanPhamMoi sanPhamLaptop = array.get(position);
            myViewHolder.tensplaptop.setText(sanPhamLaptop.getTensp().trim()); //(sanPham.getTensp());

            //Tinh chỉnh lại phông giá sản phẩm ở màn hình chính
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasplaptop.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPhamLaptop.getGia())) + "Đ");

            myViewHolder.motalaptop.setText(sanPhamLaptop.getMota());
            Glide.with(context).load(sanPhamLaptop.getHinhanh()).into(myViewHolder.hinhanhlaptop);

            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick) {
                        //click
                        Intent intent2 = new Intent(context, LapTopChiTietActivity.class);
                        intent2.putExtra("chitiet", sanPhamLaptop);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent2);
                    }
                }

            });
        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensplaptop, giasplaptop, motalaptop;
        ImageView hinhanhlaptop;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensplaptop = itemView.findViewById(R.id.itemdt_ten_laptop);
            giasplaptop = itemView.findViewById(R.id.itemdt_gia_laptop);
            motalaptop = itemView.findViewById(R.id.itemdt_mota_laptop);
            hinhanhlaptop = itemView.findViewById(R.id.itemdt_image_laptop);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
