package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.testapplication.network.AModel;
import com.example.testapplication.network.BModel;
import com.example.testapplication.network.RequestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonNext;
    int index = 0;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    FrameLayout container;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_content);
        buttonNext = findViewById(R.id.btn_next);
        container = findViewById(R.id.container);
        fragmentManager = getSupportFragmentManager();
        initFragment("");


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
        request();

    }

    public void initFragment(String url) {
        fragment = new WebViewFragment(url);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.container, fragment)
                .commit();
    }

    public void request() {
        RequestService.getInstance()
                .getRequest()
                .getObjects()
                .enqueue(new Callback<List<AModel>>() {
                    @Override
                    public void onResponse(Call<List<AModel>> call, Response<List<AModel>> response) {
                        List<AModel> aModels = response.body();
                        if (!(index < aModels.size())) {
                            index = 0;
                        }
                        printRequest(aModels.get(index).getId());
                        index++;
                    }

                    @Override
                    public void onFailure(Call<List<AModel>> call, Throwable t) {
                        textView.setText(t.getMessage());
                    }
                });
    }

    public void printRequest(int id) {

        RequestService.getInstance()
                .getRequest()
                .getObject(id)
                .enqueue(new Callback<BModel>() {
                    @Override
                    public void onResponse(Call<BModel> call, Response<BModel> response) {
                        BModel bModel = response.body();

                        if (bModel.getType().equals("text")) {
//                            delateFragment();
                            container.setVisibility(View.INVISIBLE);
                            textView.setText(bModel.getContents());
                        } else if (bModel.getType().equals("webview")) {
                            textView.setText("");
                            container.setVisibility(View.VISIBLE);
                            initFragment(bModel.getUrl());

                        }

                    }

                    @Override
                    public void onFailure(Call<BModel> call, Throwable t) {

                    }
                });
    }

}
