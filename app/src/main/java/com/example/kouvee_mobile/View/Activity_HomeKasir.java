package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.R;

public class Activity_HomeKasir extends AppCompatActivity {
    Button btnDataHewan, btnCustomer;
    Button btnPembayaran;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__kasir);

        btnDataHewan = findViewById(R.id.btnDataHewanKasir);
        btnDataHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hewan = new Intent(Activity_HomeKasir.this, Activity_DataHewan.class);
                startActivity(hewan);
            }
        });

        btnCustomer = findViewById(R.id.btnCustomerKasir);
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer = new Intent(Activity_HomeKasir.this, Activity_LihatCustomer.class);
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
