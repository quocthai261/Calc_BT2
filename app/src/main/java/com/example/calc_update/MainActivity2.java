package com.example.calc_update;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> His_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.History);
        Intent intent = getIntent();
        His_item = intent.getStringArrayListExtra("LS");
        String [] His_item2 = His_item.toArray(new String[His_item.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_main3, His_item2);
        listView.setAdapter(adapter);
    }
}