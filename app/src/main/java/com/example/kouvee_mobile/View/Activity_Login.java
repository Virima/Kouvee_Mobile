package com.example.kouvee_mobile.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.Controller.Login_Interface;
import com.example.kouvee_mobile.Model.Login_Model;
import com.example.kouvee_mobile.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {
    Button btn_login, btn_customer;
    private EditText inputusername, inputpassword;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        inputusername = findViewById(R.id.inputUsername);
        inputpassword = findViewById(R.id.inputPassword);

        btn_customer = findViewById(R.id.btnCustomer);
        btn_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer = new Intent(Activity_Login.this, Activity_HomeCustomer.class);
                startActivity(customer);
            }
        });

        btn_login = findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });
    }

    public void requestLogin() {
        if (inputusername.getText().toString().isEmpty() || inputpassword.getText().toString().isEmpty()) {
            Toast.makeText(Activity_Login.this, "Username atau Password Kosong", Toast.LENGTH_SHORT).show();
        } else {

            dialog = new ProgressDialog(Activity_Login.this);
            dialog.setTitle("Please Wait");
            dialog.setMessage("Loading..");
            dialog.show();

            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://192.168.1.6:8181/api_android/");
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            Login_Interface interface_login = retrofit.create(Login_Interface.class);

            Call<Login_Model> call = interface_login.loginRequest(inputusername.getText().toString(),
                    inputpassword.getText().toString());
            call.enqueue(new Callback<Login_Model>() {
                @Override
                public void onResponse(Call<Login_Model> call, Response<Login_Model> response) {
                    if (response.isSuccessful()) {

                        dialog.dismiss();
                        Toast.makeText(Activity_Login.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Activity_Login.this, Activity_HomeAdmin.class);
                        startActivity(i);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Activity_Login.this, "Username atau Password Salah",
                                Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                }

                @Override
                public void onFailure(Call<Login_Model> call, Throwable t) {
                    Log.d("TAG", t.toString());
                    Toast.makeText(Activity_Login.this, "Username dan Password Salah",
                            Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        inputusername.getText().clear();
        inputpassword.getText().clear();
    }

}
