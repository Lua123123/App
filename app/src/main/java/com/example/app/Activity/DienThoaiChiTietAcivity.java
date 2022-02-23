package com.example.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.Model.GioHang;
import com.example.app.Model.SanPhamMoi;
import com.example.app.R;
import com.example.app.Utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import okhttp3.internal.Util;

public class DienThoaiChiTietAcivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh, reg_back;
    Spinner spinner;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_chi_tiet_acivity);
        initView();
        reg_back();
        initData();
        //giỏ hàng
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0) {
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getGia()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if (flag = false) {
                long gia = Long.parseLong(sanPhamMoi.getGia()) * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }

        } else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGia()) * soluong;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void reg_back() {
        reg_back = (ImageView) findViewById(R.id.reg_back);
        reg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_reg_back = new Intent(getApplicationContext(), PhoneActivity.class);
                //intent_reg_back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_reg_back);
            }
        });
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);

        //Tinh chỉnh lại phông giá sản phẩm
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPhamMoi.getGia())) + "Đ");

        //Tinh chỉnh số lượng sản phẩm để thêm vào giỏ hàng ở màn hình điện thoại chi tiết
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp = (TextView) findViewById(R.id.txttensp);
        giasp = (TextView) findViewById(R.id.txtgiasp);
        mota = (TextView) findViewById(R.id.txtmotachitiet);
        btnthem = (Button) findViewById(R.id.buttonthemvaogiohang);
        spinner = (Spinner) findViewById(R.id.spinner);
        imghinhanh = (ImageView) findViewById(R.id.imgchitiet);
        badge = (NotificationBadge) findViewById(R.id.menu_sl_dienthoai);

        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        //Hàm cập nhật lại số lượng sản phẩm ở nút Badge
        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Hàm cập nhật lại số lượng sản phẩm ở nút Badge
        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}