package com.madhumankatha.plantas.network;

import com.madhumankatha.plantas.models.ImageSearch;
import com.madhumankatha.plantas.models.Orders;
import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.models.Response;
import com.madhumankatha.plantas.models.Users;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls {

    @Multipart
    @POST("api/FileUpload/")
    Call<ImageSearch> uploadImage(@Part MultipartBody.Part image,
                                  @Part("image") RequestBody imageSearch);

    @GET("api/Products/")
    Call<List<Products>> getProducts();

    @Headers({"Content-type: application/json"})
    @POST("api/Users/")
    Call<Users> createUsers(@Body Users users);

    @Headers({"Content-type: application/json"})
    @POST("api/Login/")
    Call<Response> doLogin(@Body Users users);

    @GET("api/Details/{userid}")
    Call<List<Orders>> getOrders(@Path("userid") long userid);

    @Headers({"Content-type: application/json"})
    @POST("api/Orders")
    Call<Orders> makeOrder(@Body Orders orders);

}
