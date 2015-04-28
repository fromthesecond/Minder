package com.fireblink.minder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class ViewMindActivity extends ActionBarActivity {

    private TextView title;
    private TextView body;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mind);
        int primary = getResources().getColor(R.color.colorPrimaryBlue500);
        int secondary = getResources().getColor(R.color.colorPrimaryDarkBlue700);
        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(
                        SlidrPosition.LEFT
                )
                .sensitivity(0.2f)
                .build();
        Slidr.attach(this,config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.customToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewMindActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDarkBlue700));
        setTitle(getIntent().getExtras().getString("title"));
        title = (TextView) findViewById(R.id.titleTxt);
        body = (TextView) findViewById(R.id.bodyTxt);
        title.setText(getIntent().getExtras().getString("title").toUpperCase());
        body.setText(getIntent().getExtras().getString("body"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mind, menu);
        return true;
    }

    public void backToMain (MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void deleteMind (MenuItem item) {
        db = new DataBaseHandler(this);
        db.deleteMind(db.getMindById(getIntent().getIntExtra("id", 0)));
        startActivity(new Intent(ViewMindActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
