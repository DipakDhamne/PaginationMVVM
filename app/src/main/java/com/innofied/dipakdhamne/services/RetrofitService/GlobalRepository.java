package com.innofied.dipakdhamne.services.RetrofitService;

import androidx.lifecycle.MutableLiveData;

import com.innofied.dipakdhamne.model.ListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalRepository {

    public static GlobalRepository globalRepository;

    public static GlobalRepository getInstance() {
        if (globalRepository == null) {
            globalRepository = new GlobalRepository();
        }
        return globalRepository;
    }

    private IAllApi allApi;

    public GlobalRepository() {
        allApi = RetrofitClass.getRetrofit().create(IAllApi.class);
    }

    public MutableLiveData<ListResponse> getList(String page_number, String page_size) {

        final MutableLiveData<ListResponse> lstData = new MutableLiveData<>();

        allApi.getList(page_number,page_size).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                if (response != null) {
                    if (response.body().getData().size() > 0) {
                        lstData.setValue(response.body());
                    } else {
                        lstData.setValue(null);
                    }
                } else {
                    lstData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                lstData.setValue(null);
            }
        });


        return lstData;
    }

}
