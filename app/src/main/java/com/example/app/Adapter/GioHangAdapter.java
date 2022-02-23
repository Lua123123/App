package com.example.app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.Interface.IImageClickListener;
import com.example.app.Model.EventBus.TinhTongEvent;
import com.example.app.Model.GioHang;
import com.example.app.R;
import com.example.app.Utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+ " ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        //Tinh chỉnh lại phông giá sản phẩm ở màn hình chính
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick: " + pos + " ..." + giatri);
                if (giatri == 1) {
                    if (gioHangList.get(pos).getSoluong() > 1) {
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        //set lại giá khi bấm + nó sẽ tăng, trừ nó sẽ giảm
                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+ " ");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        //sự kiện bắn qua hàm tính tổng tiền bên GioHangActivity
                        EventBus.getDefault().postSticky(new TinhTongEvent());

                    } else if (gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Notification");
                        builder.setMessage("Do you want remove this product?");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();

                                //sự kiện bắn qua hàm tính tổng tiền bên GioHangActivity
                                EventBus.getDefault().postSticky(new TinhTongEvent());

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else if (giatri == 2) {
                    if (gioHangList.get(pos).getSoluong() < 11) {
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }

                    //set lại giá khi bấm + nó sẽ tăng, trừ nó sẽ giảm
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+ " ");
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    //sự kiện bắn qua hàm tính tổng tiền bên GioHangActivity
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
            }
        });

    }
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        IImageClickListener listener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);

            //event click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru) {
                listener.onImageClick(view, getAdapterPosition(), 1);
                // 1 tru
            } else if (view == imgcong){
                listener.onImageClick(view, getAdapterPosition(), 2);
                // 2 cong
            }
        }
    }
}
