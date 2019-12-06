package com.example.uts_progmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    DataDosenService dataDosenService;
    DataDosenService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDosenService = RetrofitClient.getRetrofitInstance()
                .create(DataDosenService.class);
        insertDosen();
        updateDosen();
        deleteDosen();
    }

    private void updateDosen() {
        Call<DefaultResult> call = dataDosenService.updateDosen("", "", "", "", "", "", "");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                System.out.println(response.body().getStatus());
            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                System.out.println("message : " + t.getMessage());
                Toast.makeText(MainActivity.this, "data diubah", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteDosen() {
        Call<DefaultResult> call = dataDosenService.deleteDosen("", "");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                System.out.println(response.body().getStatus());
            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                System.out.println("message : " + t.getMessage());
                Toast.makeText(MainActivity.this, "data dihapus", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertDosen() {
        Call<DefaultResult> call = dataDosenService.insertDosen("", "", "", "", "", "", "");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                System.out.println(response.body().getStatus());
            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                System.out.println("message : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void insertDosenWithFoto() {
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard, "/Download/image.jpg");
        String imageToSend = null;
        if (file.exists()) {
            if (!checkPermission()) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        }

        Call<DefaultResult> call = DataDosenService.insertDosenWithPhoto("","","","","",imageToSend,"72140048")
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                System.out.println(response.body().getStatus());
            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                System.out.println("message :" + t.getMessage());
                Toast.makeText(MainActivity.this,
                        "Somerhing went wrong.. ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
