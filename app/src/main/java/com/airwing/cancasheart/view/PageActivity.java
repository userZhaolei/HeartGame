package com.airwing.cancasheart.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airwing.cancasheart.R;

public class PageActivity extends AppCompatActivity {

    private PageView pageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        pageView = findViewById(R.id.page);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pageView.startAnimation();
            }
        });
    }
}
