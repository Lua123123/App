package com.example.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.app.Adapter.LoaiSpAdapter;
import com.example.app.Model.LoaiSp;
import com.example.app.R;
import com.example.app.Retrofit.ApiBanHang;
import com.example.app.Retrofit.RetrofitClient;
import com.example.app.Utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity2 extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;

    //Adapter
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;

    //Kết nối với PHP
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();
        ActionViewFlipper();

        //Hàm kiểm tra kết nối Internet
        if (isConnected(this)){
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            //Hàm kết nối với PHP
            getLoaiSanpham();
        } else {
            Toast.makeText(getApplicationContext(), "Failed: Internet Unconnected", Toast.LENGTH_LONG).show();
        }
    }

    //Hàm kết nối với PHP
    private void getLoaiSanpham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                loaiSpModel -> {
                    if (loaiSpModel.isSuccess()){
                        //Lấy tên của loại sản phẩm
                        Toast.makeText(getApplicationContext(), loaiSpModel.getResult().get(0).getTensanpham(), Toast.LENGTH_LONG).show();
                    }
                }
        ));
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://image.thanhnien.vn/w660/Uploaded/2022/xdrkxrvekx/2021_03_30/830_cgoz.jpg");
        mangquangcao.add("https://sonypro.vn/wp-content/uploads/2021/04/Dien-thoai-gia-re-1.jpg");
        mangquangcao.add("https://media.vov.vn/sites/default/files/styles/front_large/public/2020-11/2_183.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2013/11/29/523609/thu-thuat-chup-anh-dep-bang-dien-thoai_800x450.jpg");
        for(int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        //set animation cho viewFlipper
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolBarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleView);
        listViewManHinhChinh = findViewById(R.id.listViewmanhinhchinh);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        //Adapter : Khỏi tạo List
        mangloaisp = new ArrayList<>();

        //Adapter : Khởi tạo Adapter
        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
        listViewManHinhChinh.setAdapter(loaiSpAdapter);
    }

    //Hàm kiểm tra kết nối Internet : Bài 5: Kết nối Server để lấy dữ liệu
    private boolean isConnected (Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //thêm quyền ACCESS_STATE ở manifests
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) )
        {
            return true;
        } else {
            return false;
        }
    }

}












