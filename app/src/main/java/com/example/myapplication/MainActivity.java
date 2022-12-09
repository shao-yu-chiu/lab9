package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView2_clk;
    private Button button_start;

    private boolean flag=false;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            textView2_clk.setText(
                    String.format(Locale.getDefault(),"%02d:%02d:%02d",
                            b.getInt("H"),
                            b.getInt("M"),
                            b.getInt("S")));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2_clk = findViewById(R.id.textView2_clk);
        button_start = findViewById(R.id.button_start);

        registerReceiver(receiver, new IntentFilter("My Message"));
        flag =MyService.flag;
        if(flag)
            button_start.setText("暫停");
        else
            button_start.setText("開始");
        button_start.setOnClickListener(v -> {

                flag = !flag;
                if(flag){
                    button_start.setText("暫停");
                    Toast.makeText(MainActivity.this,"計時開始",Toast.LENGTH_SHORT).show();
                }
                else{
                    button_start.setText("開始");
                    Toast.makeText(MainActivity.this,"計時停止",Toast.LENGTH_SHORT).show();
                }

                startService(new Intent(MainActivity.this,MyService.class).putExtra("flag",flag));
        });
    }
    @Override
    public void  onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}