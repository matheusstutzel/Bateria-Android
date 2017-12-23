package br.stutzel.bateria

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, Monitor::class.java))
        findViewById<View>(R.id.turnoff).setOnClickListener({ stopService(Intent(this, Monitor::class.java)) })
    }
}
