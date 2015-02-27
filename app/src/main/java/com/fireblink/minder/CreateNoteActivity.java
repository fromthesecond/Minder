package com.fireblink.minder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.widgets.SnackBar;


public class CreateNoteActivity extends ActionBarActivity {

    private EditText name;
    private EditText body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        name = (EditText) findViewById(R.id.name);
        body = (EditText) findViewById(R.id.body);
        name.setHint("Header");
        body.setHint("Details");
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
        if (id == R.id.back) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void backToMain (MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void cancelButton (View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void createMind (View v) {
        name = (EditText) findViewById(R.id.name);
        body = (EditText) findViewById(R.id.body);
        if (name.getText().toString().isEmpty() || body.getText().toString().isEmpty()) {
            final SnackBar snackbar = new SnackBar(this, "No data has been entered for the note. All fields are required.", "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.show();
        } else {
            DataBaseHandler db = new DataBaseHandler(this);
            db.addMind(new Mind(name.getText().toString(), body.getText().toString()));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
