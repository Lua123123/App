package com.example.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.app.R;
import com.example.app.Utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class ThanhToanActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txttongtien, txtsodt, txtemail;
    EditText edtdiachi;
    AppCompatButton btndathang;
    String authGmail;
    private String authSoDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        initView();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));

        //txtemail.setText(Utils.user_current.getEmail());
        //txtsodt.setText(Utils.user_current.getPhone());

        //Lấy thông tin Email đăng nhập hiện tại ở màn hình thanh toán
        authGmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        txtemail.setText(authGmail);

        ///////////////////////////////////////////////////////////////////////
        authSoDt = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString();
        txtsodt.setText(authSoDt);


        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test", new Gson().toJson(Utils.manggiohang));



                }
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        txttongtien = findViewById(R.id.txttongtien);
        txtemail = findViewById(R.id.txtemail);
        txtsodt = findViewById(R.id.txtsodienthoai);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
    }

}