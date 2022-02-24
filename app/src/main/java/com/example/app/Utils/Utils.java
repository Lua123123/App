package com.example.app.Utils;

import com.example.app.Activity.User;
import com.example.app.Model.GioHang;

import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.115.173/banhang/";

    //giỏ hàng
    public static List<GioHang> manggiohang;

    //login + cho hiện luôn đỡ mất công nhập login lại
    public static User user_current = new User();

}