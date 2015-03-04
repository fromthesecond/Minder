package com.fireblink.minder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;

public class ViewMindActivity extends ActionBarActivity {

    private TextView title;
    private TextView body;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mind);
        setTitle(getIntent().getExtras().getString("title"));
        Log.i("Debug: ", getIntent().getExtras().getString("title")+ " intent from ViewMind title");
        Log.i("Debug: ", getIntent().getExtras().getString("body")+ " intent from ViewMind body");
        title = (TextView) findViewById(R.id.titleTxt);
        body = (TextView) findViewById(R.id.bodyTxt);
        title.setText(getIntent().getExtras().getString("title"));
        body.setText(getIntent().getExtras().getString("body"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_mind, menu);
        return true;
    }

    public void backToMain (MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
