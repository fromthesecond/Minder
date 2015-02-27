package com.fireblink.minder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewMindActivity extends ActionBarActivity {

    private TextView title;
    private TextView body;

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
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void backToMain (MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
