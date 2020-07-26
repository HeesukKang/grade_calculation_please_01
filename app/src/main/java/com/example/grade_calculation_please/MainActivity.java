package com.example.grade_calculation_please;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    Button bt11, bt12, bt21, bt22, bt31, bt32, bt41, bt42, bt_etc;
    private Intent intent;
    TextView a2, a4, b2, b4;
    double oneonetKgs, oneonetKgsM, oneonetEgs, oneonetEgsM, oneonerealcr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a2 = findViewById(R.id.a2);
        a4 = findViewById(R.id.a4);
        b2 = findViewById(R.id.b2);
        b4 = findViewById(R.id.b4);

        /*Intent intent = getIntent();
        oneonetKgs = intent.getDoubleExtra("oneonetKgs", 0);
        oneonetKgsM = intent.getDoubleExtra("oneonetKgsM", 0);
        oneonetEgs = intent.getDoubleExtra("oneonetKgs", 0);
        oneonetEgsM = intent.getDoubleExtra("oneonetKgsM", 0);
        oneonerealcr = intent.getDoubleExtra("oneonerealcr", 0);
        */

        bt11 = findViewById(R.id.bt11);
        bt11.setOnClickListener(this);
        bt12 = findViewById(R.id.bt12);
        bt12.setOnClickListener(this);
        bt21 = findViewById(R.id.bt21);
        bt21.setOnClickListener(this);
        bt22 = findViewById(R.id.bt22);
        bt22.setOnClickListener(this);
        bt31 = findViewById(R.id.bt31);
        bt31.setOnClickListener(this);
        bt32 = findViewById(R.id.bt32);
        bt32.setOnClickListener(this);
        bt41 = findViewById(R.id.bt41);
        bt41.setOnClickListener(this);
        bt42 = findViewById(R.id.bt42);
        bt42.setOnClickListener(this);
        bt_etc = findViewById(R.id.bt_etc);
        bt_etc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent_viewchange = new Intent(MainActivity.this, zerozero.class);
        switch (v.getId()) {
            case R.id.bt11 :
                intent_viewchange.putExtra("name", "1학년 1학기");
                break ;
            case R.id.bt12 :
                intent_viewchange.putExtra("name", "1학년 2학기");
                break ;
            case R.id.bt21 :
                intent_viewchange.putExtra("name", "2학년 1학기");
                break ;
            case R.id.bt22 :
                intent_viewchange.putExtra("name", "2학년 2학기");
                break ;
            case R.id.bt31 :
                intent_viewchange.putExtra("name", "3학년 1학기");
                break ;
            case R.id.bt32 :
                intent_viewchange.putExtra("name", "3학년 2학기");
                break ;
            case R.id.bt41 :
                intent_viewchange.putExtra("name", "4학년 1학기");
                break ;
            case R.id.bt42 :
                intent_viewchange.putExtra("name", "4학년 2학기");
                break ;
            case R.id.bt_etc:
                intent_viewchange.putExtra("name", "기타학기");
                break ;
        }
        Log.d("abc", "ok");
        startActivity(intent_viewchange);
    }


}