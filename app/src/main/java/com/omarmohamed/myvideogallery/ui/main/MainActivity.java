package com.omarmohamed.myvideogallery.ui.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.omarmohamed.myvideogallery.R;
import com.omarmohamed.myvideogallery.adapter.RecyclerViewAdapter;
import com.omarmohamed.myvideogallery.component.AppComponent;
import com.omarmohamed.myvideogallery.component.DaggerMainComponent;
import com.omarmohamed.myvideogallery.models.PetModel;
import com.omarmohamed.myvideogallery.modules.MainModule;
import com.omarmohamed.myvideogallery.ui.common.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter presenter;

    @InjectView(R.id.recycler)
    RecyclerView recyclerView;

    @InjectView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }


    @Override protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override public void setItems(List<PetModel> items) {
        if(adapter != null){
            adapter.setItems(items);
            recyclerView.setAdapter(adapter);
        }
    }

}
