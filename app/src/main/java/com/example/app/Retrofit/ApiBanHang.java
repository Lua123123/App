package com.example.app.Retrofit;

import com.example.app.Model.LoaiSpModel;
import io.reactivex.rxjava3.core.Observable;

import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
}
