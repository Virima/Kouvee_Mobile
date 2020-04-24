package com.example.kouvee_mobile.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.Controller.Login_Interface;
import com.example.kouvee_mobile.Model.Login_Model;
import com.example.kouvee_mobile.Model.Pegawai_Model;
import com.example.kouvee_mobile.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {
    Button btn_login, btn_customer;
    private EditText inputusername, inputpassword;
    private ProgressDialog dialog;

    SharedPreferences sp;
    public String sp_NamaPegawai="";
    private List<Pegawai_Model> pegawaiList;

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
            Toast.makeText(Activity_Login.this, "Username atau Password Kosong",
                    Toast.LENGTH_SHORT).show();
        } else {

            dialog = new ProgressDialog(Activity_Login.this);
            dialog.setTitle("Please Wait");
            dialog.setMessage("Loading...");
            dialog.show();

            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://192.168.1.6:8181/api_android/");
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            Login_Interface interface_login = retrofit.create(Login_Interface.class);

            Call<Login_Model> call = interface_login.loginRequest(
                    inputusername.getText().toString(),
                    inputpassword.getText().toString());
            call.enqueue(new Callback<Login_Model>() {
                @Override
                public void onResponse(Call<Login_Model> call, Response<Login_Model> response) {
                    if (response.isSuccessful()) {

                        dialog.dismiss();
                        Toast.makeText(Activity_Login.this, "Login Berhasil!",
                                Toast.LENGTH_SHORT).show();

                        //
                        Log.i(Activity_Login.class.getSimpleName(), response.toString());
                        sp_NamaPegawai = response.body().getValue();
                        savePreference(sp_NamaPegawai);

                        String message = response.body().getMessage();
                        if (message.equals("Owner"))
                        {
                            Intent i = new Intent(Activity_Login.this, Activity_HomeAdmin.class);
                            startActivity(i);
                        } else if (message.equals("Customer Service"))
                        {
                            Intent i = new Intent(Activity_Login.this, Activity_HomeCS.class);
                            startActivity(i);
                        } else if (message.equals("Kasir"))
                        {
                            Intent i = new Intent(Activity_Login.this, Activity_HomeKasir.class);
                            startActivity(i);
                        }

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

    /*
    public void identifikasiRole()
    {
        Pegawai_Interface apiPegawai = API_client.getApiClient().create(Pegawai_Interface.class);
        Call<List<Pegawai_Model>> listCall = apiPegawai.getPegawai();

        listCall.enqueue(new Callback<List<Pegawai_Model>>() {
            @Override
            public void onResponse(Call<List<Pegawai_Model>> call, Response<List<Pegawai_Model>> response) {
                List<Pegawai_Model> pegawaiModels = response.body();

            }

            @Override
            public void onFailure(Call<List<Pegawai_Model>> call, Throwable t) {
                Toast.makeText(Activity_Login.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        inputusername.getText().clear();
        inputpassword.getText().clear();
    }
    */

    //-  Fungsi simpan dan load teks sementara [SharedPreferences]  -//
    /*
    private void setForm()
    {
        pNamaHewan = findViewById(R.id.NamaHewan);
        pTglLahirHewan = findViewById(R.id.TglLahirHewan);
        pIdJenis = findViewById(R.id.JenisHewanJoinHewan);
        pIdUkuran = findViewById(R.id.UkuranHewanJoinHewan);
        pId_Customer = findViewById(R.id.PemilikHewanJoinHewan);

        pNamaHewan.setText(sp_nama);
        pTglLahirHewan.setText(sp_tanggalLahir);
        pIdJenis.setText(sp_jenis);
        pIdUkuran.setText(sp_ukuran);
        pId_Customer.setText(sp_customer);
    }

    private void loadPreference()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_nama = sp.getString("sp_nama", "");
            sp_tanggalLahir = sp.getString("sp_tanggalLahir", "");
            sp_jenis = sp.getString("sp_jenis", "");
            sp_ukuran = sp.getString("sp_ukuran", "");
            sp_customer = sp.getString("sp_customer", "");
        }
    }
    */

    private void savePreference(String string)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sp_nama_pegawai", string);
        editor.apply();
    }

}
