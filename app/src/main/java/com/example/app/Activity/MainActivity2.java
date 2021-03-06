package com.example.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.app.Adapter.LoaiSpAdapter;
import com.example.app.Adapter.SanPhamMoiAdapter;
import com.example.app.Model.LoaiSp;
import com.example.app.Model.SanPhamMoi;
import com.example.app.Model.SanPhamMoiModel;
import com.example.app.R;
import com.example.app.Retrofit.ApiBanHang;
import com.example.app.Retrofit.RetrofitClient;
import com.example.app.Utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

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

    //K???t n???i v???i PHP
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    //Hi???n th??? d??? li???u l??n Recycleview m??n h??nh ch??nh
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;

    //badge
    NotificationBadge badge;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        ActionBar();

        //H??m ki???m tra k???t n???i Internet
        if (isConnected(this)){
            //Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            //H??m k???t n???i v???i PHP
            getLoaiSanpham();
            //Hi???n th??? d??? li???u l??n Recycleview m??n h??nh ch??nh
            getSpMoi();
            //B???t s??? ki???n chuy???n
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Failed: Internet Unconnected", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent phone = new Intent(getApplicationContext(), PhoneActivity.class);
                        phone.putExtra("loai", 1);
                        startActivity(phone);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(), PhoneActivity.class);
                        laptop.putExtra("loai", 2);
                        startActivity(laptop);
                        break;
                }
            }
        });
    }


    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();
                                //Adapter : Kh???i t???o Adapter
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Server unconnected, Please connect" + throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                ));
    }

    //H??m k???t n???i v???i PHP
    private void getLoaiSanpham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()){
                                mangloaisp = loaiSpModel.getResult();
                                //Adapter : Kh???i t???o Adapter
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
                                listViewManHinhChinh.setAdapter(loaiSpAdapter);
                            }
                        }, throwable -> {
                            Log.d("test", throwable.getMessage());


                        }

                ));
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://image.thanhnien.vn/w660/Uploaded/2022/xdrkxrvekx/2021_03_30/830_cgoz.jpg");
        mangquangcao.add("https://sonypro.vn/wp-content/uploads/2021/04/Dien-thoai-gia-re-1.jpg");
        mangquangcao.add("https://tapchicongnghemaytinh.com/wp-content/uploads/2021/07/macbookair2019vsmacbookpro2019_800x534.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2013/11/29/523609/thu-thuat-chup-anh-dep-bang-dien-thoai_800x450.jpg");
        mangquangcao.add("https://vtv1.mediacdn.vn/thumb_w/650/2019/5/4/iphone-xi-concept-images-15569264542701469883220.jpg");
        mangquangcao.add("https://cellphones.com.vn/sforum/wp-content/uploads/2021/02/DSC03716.jpg");
        mangquangcao.add("https://media.vov.vn/sites/default/files/styles/front_large/public/2020-11/2_183.jpg");


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

        //Adapter : Kh???i t???o List
        mangloaisp = new ArrayList<>();

        //Hi???n th??? d??? li???u l??n Recycleview m??n h??nh ch??nh
        mangSpMoi = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);

        //badge
        badge = findViewById(R.id.menu_sl_dienthoai);
        frameLayout = findViewById(R.id.framegiohang);

        //gi??? h??ng
        if (Utils.manggiohang == null) {
            Utils.manggiohang = new ArrayList<>();
        } else {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    //H??m ki???m tra k???t n???i Internet : B??i 5: K???t n???i Server ????? l???y d??? li???u
    private boolean isConnected (Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //th??m quy???n ACCESS_STATE ??? manifests
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) )
        {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}












