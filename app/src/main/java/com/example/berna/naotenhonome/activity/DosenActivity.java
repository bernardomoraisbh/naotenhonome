package com.example.berna.naotenhonome.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.berna.naotenhonome.R;
import com.example.berna.naotenhonome.adapter.DosenAdapter;
import com.example.berna.naotenhonome.model.ResponseDosen;
import com.example.berna.naotenhonome.model.SemuadosenItem;
import com.example.berna.naotenhonome.util.api.BaseApiService;
import com.example.berna.naotenhonome.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DosenActivity extends AppCompatActivity {

    @BindView(R.id.rvDosen)
    RecyclerView rvDosen;
    ProgressDialog loading;

    Context mContext;
    List<SemuadosenItem> semuadosenItemList = new ArrayList<>();
    DosenAdapter dosenAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen);

        getSupportActionBar().setTitle("Teste");

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        dosenAdapter = new DosenAdapter(this, semuadosenItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvDosen.setLayoutManager(mLayoutManager);
        rvDosen.setItemAnimator(new DefaultItemAnimator());

        getResultListDosen();
    }

    private void getResultListDosen(){
        loading = ProgressDialog.show(this, null, "Por favor, aguarde...", true, false);

        mApiService.getSemuaDosen().enqueue(new Callback<ResponseDosen>() {
            @Override
            public void onResponse(Call<ResponseDosen> call, Response<ResponseDosen> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<SemuadosenItem> semuaDosenItems = response.body().getSemuadosen();

                    rvDosen.setAdapter(new DosenAdapter(mContext, semuaDosenItems));
                    dosenAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Falha ao recuperar os dados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDosen> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Conexão com a internet ruim.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
