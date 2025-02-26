package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.kouvee_mobile.R;

public class Activity_HomeAdmin extends AppCompatActivity {

    Button btnDataHewan, btnCustomer, btnSupplier, btnLayanan, btnUkuran, btnProduk, btnPengadaan, btnLogout;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDataHewan = findViewById(R.id.btnDataHewan);
        btnDataHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hewan = new Intent(Activity_HomeAdmin.this, Activity_DataHewan.class);
                startActivity(hewan);
            }
        });

        btnCustomer = findViewById(R.id.btnCustomer);
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer = new Intent(Activity_HomeAdmin.this, Activity_LihatCustomer.class);
                startActivity(customer);
            }
        });

        btnSupplier = findViewById(R.id.btnSupplier);
        btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent supplier = new Intent(Activity_HomeAdmin.this, Activity_Supplier.class);
                startActivity(supplier);
            }
        });

        btnLayanan = findViewById(R.id.btnLayanan);
        btnLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent layanan = new Intent(Activity_HomeAdmin.this, Activity_LihatLayanan.class);
                startActivity(layanan);
            }
        });

        btnLayanan = findViewById(R.id.btnJenisHewan);
        btnLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jenis = new Intent(Activity_HomeAdmin.this, Activity_JenisHewan.class);
                startActivity(jenis);
            }
        });

        btnUkuran = findViewById(R.id.btnUkuranHewan);
        btnUkuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ukuran = new Intent(Activity_HomeAdmin.this, Activity_UkuranHewan.class);
                startActivity(ukuran);
            }
        });

        btnProduk = findViewById(R.id.btnProduk);
        btnProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent produk = new Intent(Activity_HomeAdmin.this, Activity_Produk.class);
                startActivity(produk);
            }
        });

        btnPengadaan = findViewById(R.id.btnPengadaanProduk);
        btnPengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent produk = new Intent(Activity_HomeAdmin.this, Activity_Pengadaan.class);
                startActivity(produk);
            }
        });

        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(Activity_HomeAdmin.this, Activity_Login.class);
                Toast.makeText(Activity_HomeAdmin.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(logout);
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
