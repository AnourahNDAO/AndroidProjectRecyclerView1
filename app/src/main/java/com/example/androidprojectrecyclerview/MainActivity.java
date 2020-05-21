package com.example.androidprojectrecyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://bedapi.co/";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        sharedPreferences = getSharedPreferences("bedroom_application", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<bedroom> bedroomList = getDataFromCache();
        if(bedroomList != null){
            showList(bedroomList);
        }else{
            makeApiCall();
        }
        private List<bedroom> getDataFromCache(){
            String jsonBedroomList =  sharedPreferences.getString("jsonBedroomList", jsonString))
            if(jsonBedroomList == null){
                return null;
            }else {
                Type ListType = new TypeToken<List<bedroom>>() {
                }.getType();
                List<bedroom> bedroomList1 = gson.fromJson(jsonBedroomList, List<bedroom>.class);
                return gson.fromJson(jsonBedroomList, ListType);
            }
        }
    }

    private void showList(List<bedroom> bedroomList){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        // define an adapter
        RecyclerView.Adapter mAdapter = new ListAdapter(bedroomList);
        recyclerView.setAdapter(mAdapter);
    }


    private void makeApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BedAPI gerritAPI = retrofit.create(BedAPI.class);

        Call<List<RestResponse>> call = BedAPI.getPokemonResponse();
        call.enqueue(new Callback<List<RestResponse>>() {
            private Call<List<RestResponse>> call;
            private Response<List<RestResponse>> response;

            @Override
            public void onResponse(Call<List<RestResponse>> call, Response<List<RestResponse>> response) {
                this.call = call;
                this.response = response;
                if (response.isSuccessful() && response.body() != null) {
                    Class<? extends List> bedroomList = response.body().getClass();
                    saveList(bedroomList);
                    showList(bedroomList);
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<RestResponse>> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(Class<? extends List> bedroomList) {
        String jsonString = gson.toJson(bedroomList);
        sharedPreferences
                .edit()
                .putString("jsonBedroomList", jsonString)
                .apply();
        Toast.makeText(this, "Saved List", Toast.LENGTH_SHORT).show();
    }

    private void showError(){
        Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
    }
}
