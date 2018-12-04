package com.example.berna.naotenhonome.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.berna.naotenhonome.R;
import com.example.berna.naotenhonome.util.api.BaseApiService;
import com.example.berna.naotenhonome.util.api.UtilsApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahMatkulActivity2 extends AppCompatActivity {

    @BindView(R.id.etNamaDosen)
    EditText etNamaDosen;
    @BindView(R.id.etNamaMatkul)
    EditText etNamaMatkul;
    @BindView(R.id.btnSimpanMatkul)
    Button btnSimpanMatkul;
    @BindView(R.id.button5)
    Button button5;
    ProgressDialog loading;

    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_matkul_2);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        btnSimpanMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSimpanMatkul();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahMatkulActivity2.this, MapsActivity.class));
            }
        });

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahMatkulActivity2.this, MapsActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });*/
    }

    private void requestSimpanMatkul(){
        loading = ProgressDialog.show(mContext, null, "Por favor, aguarde...", true, false);

        mApiService.simpanMatkulRequest(etNamaDosen.getText().toString(), etNamaMatkul.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            Toast.makeText(mContext, "Dados adicionados com sucesso.", Toast.LENGTH_LONG).show();
                        } else {
                            loading.dismiss();
                            Toast.makeText(mContext, "Falha ao salvar dados.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(mContext, "Não foi possível conectar-se a rede.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
