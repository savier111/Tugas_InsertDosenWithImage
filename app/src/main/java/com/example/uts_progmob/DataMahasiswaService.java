package com.example.uts_progmob;


import com.example.uts_progmob.Model.DataMhs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataMahasiswaService {
    @GET("/api/progmob/mahasiswa/{nim_progmob}")
    Call<List<DataMhs>> getMhsAll(@Path("nim_progmob") String nimProgmob);
}