package com.example.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.Model.SanPhamMoi;
import com.example.app.R;

import java.text.DecimalFormat;

public class DienThoaiChiTietAcivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh, reg_back;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai_chi_tiet_acivity);
        initView();
        reg_back();
        initData();

    }

    private void reg_back() {
        reg_back = (ImageView) findViewById(R.id.reg_back);
        reg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_reg_back = new Intent(getApplicationContext(), PhoneActivity.class);
                startActivity(intent_reg_back);
            }
        });
    }

    private void initData() {
        SanPhamMoi sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
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
    }

}