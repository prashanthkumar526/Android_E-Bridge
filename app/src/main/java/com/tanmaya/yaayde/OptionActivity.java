package com.tanmaya.yaayde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionActivity extends AppCompatActivity {
Button TL,SL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        TL=findViewById(R.id.button);
        SL=findViewById(R.id.button2);
        TL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionActivity.this,TeacherLogin.class);
                startActivity(intent);

            }
        });
        SL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionActivity.this,StudentLogin.class);
                startActivity(intent);
            }
        });
    }
}