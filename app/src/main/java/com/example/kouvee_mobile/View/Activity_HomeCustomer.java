package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.R;

public class Activity_HomeCustomer extends AppCompatActivity {
    Button btnAboutUs, btnLihatProduk, btnLihatLayanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__customer);

        /*
        btnLihatProduk = findViewById(R.id.btnLihatProduk);
        btnLihatProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihatsparepart = new Intent(Activity_HomeCustomer.this, Activity_LihatProduk.class);
                startActivity(lihatsparepart);
            }
        });

        btnLihatLayanan = findViewById(R.id.btnLihatLayanan);
        btnLihatLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihatkendaraan = new Intent(Activity_HomeCustomer.this, Activity_LihatLayananxxx.class);
                startActivity(lihatkendaraan);
            }
        }); */

        btnAboutUs = findViewById(R.id.btnAboutUs);
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutus = new Intent(Activity_HomeCustomer.this, Activity_About_Us.class);
                startActivity(aboutus);
            }
        });
    }
}
