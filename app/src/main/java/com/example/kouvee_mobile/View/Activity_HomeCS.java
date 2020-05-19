package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.R;

public class Activity_HomeCS extends AppCompatActivity  {
    Button btnDataHewan, btnCustomer, btnLogoutCS;
    Button btnPenjualanProduk, btnPenjualanLayanan;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__cs);

        btnDataHewan = findViewById(R.id.btnDataHewanCS);
        btnDataHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hewan = new Intent(Activity_HomeCS.this, Activity_DataHewan.class);
                startActivity(hewan);
            }
        });

        btnCustomer = findViewById(R.id.btnCustomerCS);
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer = new Intent(Activity_HomeCS.this, Activity_LihatCustomer.class);
                startActivity(customer);
            }
        });

        btnPenjualanProduk = findViewById(R.id.btnPenjualanProdukCS);
        btnPenjualanProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent produk = new Intent(Activity_HomeCS.this, Activity_TransaksiProduk.class);
                startActivity(produk);
            }
        });

        btnPenjualanLayanan = findViewById(R.id.btnPenjualanProdukCS);
        btnPenjualanLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent layanan = new Intent(Activity_HomeCS.this, Activity_TransaksiLayanan.class);
                startActivity(layanan);
            }
        });

        btnLogoutCS = findViewById(R.id.btnLogoutCS);
        btnLogoutCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer = new Intent(Activity_HomeCS.this, Activity_Login.class);
                startActivity(customer);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik Back sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
