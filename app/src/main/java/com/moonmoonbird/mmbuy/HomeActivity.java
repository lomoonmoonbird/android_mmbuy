package com.moonmoonbird.mmbuy;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.moonmoonbird.mmbuy.adapter.CardRecycleViewAdapter;
import com.moonmoonbird.mmbuy.listeners.EendlessRecylerOnScrollListener;
import com.moonmoonbird.mmbuy.model.Good;
import com.moonmoonbird.mmbuy.model.GoodList;
import com.moonmoonbird.mmbuy.services.APIClient;
import com.moonmoonbird.mmbuy.services.GoodService;
import com.moonmoonbird.mmbuy.wrapper.LoadWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends BaseActivity {

    private TabLayout tagLabel;
    private RecyclerView recyclerView;
    private LoadWrapper loadwrapper;
    private GoodList goods;
    private ArrayList<GoodList.GoodData> dataList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private GoodService service;
    private int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        tagLabel = findViewById(R.id.nav_tag_tablayout);
        recyclerView = findViewById(R.id.home_card);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        initTags();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initService();
        initAdapter();
        loadGoodsList();
        bindEvent();
    }

    private void initService(){
        service = APIClient.getClient().create(GoodService.class);
    }

    private void initAdapter(){
        CardRecycleViewAdapter cardRecycleViewAdapter =
                new CardRecycleViewAdapter(HomeActivity.this, dataList);
        loadwrapper = new LoadWrapper(cardRecycleViewAdapter);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(loadwrapper);

    }

    private void bindEvent(){
        // 上拉加载监听
        recyclerView.addOnScrollListener(new EendlessRecylerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadwrapper.setLoadState(loadwrapper.LOADING);
                int currentSize = dataList.size();
                int nextSize = 100;
                if (dataList.size() < currentSize + nextSize) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadGoodsList();
                                    page ++;
                                    loadwrapper.setLoadState(loadwrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadwrapper.setLoadState(loadwrapper.LOADING_END);
                }
            }
        });

        // 下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                page = 1;
                loadGoodsList();
                loadwrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    private void loadGoodsList(){
        Call<GoodList> call = service.getGoodList("adsasds", page);
        call.enqueue(new Callback<GoodList>() {
            @Override
            public void onResponse(Call<GoodList> call, Response<GoodList> response) {
                goods = response.body();
                if (goods.data.size() > 0) {
                    dataList.addAll(goods.data);
                    loadwrapper.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GoodList> call, Throwable t) {
                Toast.makeText(HomeActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTags() {
        List<String> labels = new ArrayList<String>();
        labels.add("古风");
        labels.add("绘画");
        labels.add("绘画");labels.add("绘画");labels.add("绘画");
        labels.add("绘画");labels.add("绘画");labels.add("绘画");
        labels.add("绘画");labels.add("绘画");labels.add("绘画");
        labels.add("绘画");labels.add("绘画");labels.add("绘画");
        labels.add("绘画");labels.add("绘画");labels.add("绘画");
        labels.add("绘画");labels.add("绘画");

        for (int i=0; i<labels.size(); i++){
            TabLayout.Tab tab = tagLabel.newTab();
            View view = LayoutInflater.from(this).inflate(R.layout.tag, null);
            MaterialTextView tag = view.findViewById(R.id.nav_tag);
            tag.setText(labels.get(i));
            tab.setCustomView(view);
            tagLabel.addTab(tab, false);
        }

        tagLabel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView){
                    view.setBackgroundResource(R.drawable.tag_selected);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView){
                    view.setBackgroundResource(R.drawable.tag_unselected);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
