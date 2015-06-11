package com.fireblink.minder.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fireblink.minder.Entity.Mind;
import com.fireblink.minder.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import java.util.Date;

public class CreateNoteActivity extends ActionBarActivity {

    private EditText name;
    private EditText body;
    private Toolbar toolbar;
    private ButtonFlat buttonFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        setupSystemBarColor();
        setupToolBar();
        name = (EditText) findViewById(R.id.name);
        body = (EditText) findViewById(R.id.body);
        name.setHint("Header");
        body.setHint("Details");
        name.setHintTextColor(getResources().getColor(R.color.colorPrimaryBlue500));
        body.setHintTextColor(getResources().getColor(R.color.colorPrimaryBlue500));
    }

    public void remindMe(View v) {
        View calendarView = getLayoutInflater().inflate(R.layout.calendar, null);
                new AlertDialog.Builder(CreateNoteActivity.this)
                        .setView(calendarView)
                        .setTitle("Set Remind Date")
                        .show();

    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void setupSystemBarColor() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDarkBlue700));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back) {
            return true;
        }
        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void backToMain(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancelButton(View v) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void createMind(View v) {
        name = (EditText) findViewById(R.id.name);
        body = (EditText) findViewById(R.id.body);
        if (name.getText().length() < 3 || body.getText().length() < 5 ) {
            final SnackBar snackbar = new SnackBar(this, "All fields are required.", "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    return;
                }
            });
            snackbar.show();
        } else {
            Mind mind = new Mind();
            mind.name = name.getText().toString();
            mind.body = body.getText().toString();
            mind.date = new Date();
            //TODO create row RemindDate
            mind.save();
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }
}
