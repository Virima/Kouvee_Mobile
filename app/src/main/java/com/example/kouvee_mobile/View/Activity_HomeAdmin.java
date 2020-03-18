package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.R;


public class Activity_HomeAdmin extends AppCompatActivity {

    Button btnDataHewan, btnCustomer, btnSupplier, btnLayanan, btnUkuran;

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
    }
}
