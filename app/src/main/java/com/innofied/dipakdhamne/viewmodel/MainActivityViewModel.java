package com.innofied.dipakdhamne.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.innofied.dipakdhamne.model.ListResponse;
import com.innofied.dipakdhamne.services.RetrofitService.GlobalRepository;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<ListResponse> requestData;
    public MutableLiveData<ListResponse> lstData;
    private GlobalRepository globalRepository;


    public void init() {
        globalRepository = GlobalRepository.getInstance();
        requestData = new MutableLiveData<>();
        lstData = new MutableLiveData<>();
    }


    public void getList(String page_number, String page_size) {
        requestData = globalRepository.getList(page_number, page_size);

        requestData.observeForever(new Observer<ListResponse>() {
            @Override
            public void onChanged(ListResponse dataArrays) {
                lstData.setValue(dataArrays);
            }
        });
    }

}
