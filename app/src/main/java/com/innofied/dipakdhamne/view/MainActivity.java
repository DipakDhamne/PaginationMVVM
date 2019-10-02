package com.innofied.dipakdhamne.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.innofied.dipakdhamne.R;
import com.innofied.dipakdhamne.adapters.CustomeListAdapter;
import com.innofied.dipakdhamne.databinding.ActivityMainBinding;
import com.innofied.dipakdhamne.model.ListResponse;
import com.innofied.dipakdhamne.services.CommonCode;
import com.innofied.dipakdhamne.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private static final int FIRST_START = 1;
    private CustomeListAdapter adapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 5, currentPage = FIRST_START, SCROLL_STATE = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();
        initVar();
        loadList();

        viewModel.lstData.observe(this, new Observer<ListResponse>() {
            @Override
            public void onChanged(ListResponse data) {
                activityMainBinding.setListingVisible(true);
                activityMainBinding.setProgressVisible(false);
                activityMainBinding.layoutSwipe.setRefreshing(false);
                isLoading = false;
                if (data != null) {
                    if (data.getPage() == data.getTotalPages()) {
                        isLastPage = true;
                    } else {
                        isLastPage = false;
                    }

                    if (currentPage == FIRST_START) {
                        adapter.clearRecord();
                        adapter.notifyDataSetChanged();
                    }
                    adapter.addAll(data.getData());

                } else {
                    Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        activityMainBinding.layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityMainBinding.layoutSwipe.setRefreshing(true);
                currentPage = FIRST_START;
                loadList();
            }
        });

        activityMainBinding.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                SCROLL_STATE = newState;
                Log.d("Scroll State", newState + " , " + SCROLL_STATE);

                if (SCROLL_STATE == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && !isLastPage) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            currentPage = currentPage + 1;
                            loadList();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });


    }

    private void initVar() {
        layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        activityMainBinding.rvList.setLayoutManager(layoutManager);
        activityMainBinding.setProgressVisible(false);
        adapter = new CustomeListAdapter(MainActivity.this);
        activityMainBinding.rvList.setAdapter(adapter);
    }

    private void loadList() {
        if (CommonCode.isConnectingToInternet(this)) {
            isLoading = true;
            activityMainBinding.setListingVisible(true);
            activityMainBinding.setNoNetVisible(false);
            activityMainBinding.setProgressVisible(true);
            viewModel.getList(currentPage + "", PAGE_SIZE + "");
        } else {
            activityMainBinding.setListingVisible(false);
            activityMainBinding.setNoNetVisible(true);
            isLoading = false;
        }
    }
}
