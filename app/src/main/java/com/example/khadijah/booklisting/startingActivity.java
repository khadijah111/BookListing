package com.example.khadijah.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class startingActivity extends AppCompatActivity {

    private String k = "";
    private Button button;
    private EditText textKeyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        button = (Button)findViewById(R.id.searchButton);
        textKeyword = (EditText)findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                k = textKeyword.getText().toString();
               // Toast.makeText(this, k, Toast.LENGTH_LONG).show();
                Intent i = new Intent(startingActivity.this, MainActivity.class);
                i.putExtra("keyword", k);

                startActivity(i);            }
        });
    }
}
