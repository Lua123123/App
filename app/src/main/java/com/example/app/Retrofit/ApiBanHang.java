package com.example.app.Retrofit;

import com.example.app.Model.LoaiSpModel;
import com.example.app.Model.SanPhamMoi;
import com.example.app.Model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    //Hiển thị dữ liệu lên Recycleview màn hình chính
    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
}
