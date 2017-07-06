package com.stutzel.bateria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Bateria extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bateria);
        startService(new Intent(Bateria.this, Monitor.class));
        findViewById(R.id.bto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(Bateria.this, Monitor.class));
            }
        });
    }
}
