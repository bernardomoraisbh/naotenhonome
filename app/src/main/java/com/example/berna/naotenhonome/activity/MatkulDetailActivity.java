package com.example.berna.naotenhonome.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.berna.naotenhonome.R;
import com.example.berna.naotenhonome.util.Constant;
import com.example.berna.naotenhonome.util.api.BaseApiService;
import com.example.berna.naotenhonome.util.api.UtilsApi;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatkulDetailActivity extends AppCompatActivity {

    @BindView(R.id.ivTextDrawable)
    ImageView ivTextDrawable;
    @BindView(R.id.tvNamaDosen)
    TextView tvNamaDosen;
    @BindView(R.id.tvNamaMatkul)
    TextView tvNamaMatkul;
    @BindView(R.id.btnHapus)
    Button btnHapus;
    @BindView(R.id.button3)
    Button button3;
    ProgressDialog loading;

    String mId;
    String mNamaDosen;
    String mNamaMatkul;

    Context mContext;
    BaseApiService mApiService;

    public String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul_detail);
        getSupportActionBar().setTitle("Detalhes");

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        Intent intent = getIntent();
        mId = intent.getStringExtra(Constant.KEY_ID_MATKUL);
        mNamaDosen = intent.getStringExtra(Constant.KEY_NAMA_DOSEN);
        mNamaMatkul = intent.getStringExtra(Constant.KEY_MATKUL);

        tvNamaDosen.setText(mNamaDosen);
        tvNamaMatkul.setText(mNamaMatkul);
        String firstCharNamaMatkul = mNamaMatkul.substring(0,1);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstCharNamaMatkul, getColor());
        ivTextDrawable.setImageDrawable(drawable);



        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDeleteMatkul();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatkulDetailActivity.this, MapsActivity.class));
            }
        });


    }

    private void requestDeleteMatkul(){
        loading = ProgressDialog.show(mContext, null, "Por favor, aguarde...", true, false);

        mApiService.deteleMatkul(mId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    Toast.makeText(mContext, "Deletado com sucesso.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mContext, MatkulActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Falha ao deletar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Conexão ruim.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getColor() {
        String color;

        // Randomly select a fact
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }
}
