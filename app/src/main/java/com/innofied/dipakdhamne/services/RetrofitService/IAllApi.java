package com.innofied.dipakdhamne.services.RetrofitService;

import com.innofied.dipakdhamne.model.ListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAllApi {
    @GET("users")
    Call<ListResponse> getList(@Query("page") String page_number,
                               @Query("per_page") String page_size);
}
