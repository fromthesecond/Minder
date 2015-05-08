package com.fireblink.minder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.widgets.SnackBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;


public class CreateNoteActivity extends ActionBarActivity {

    private EditText name;
    private EditText body;
    private Toolbar toolbar;
    private CalendarPickerView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        setupCalendarDemo();
        setupSystemBarColor();
        setupToolBar();
        name = (EditText) findViewById(R.id.name);
        body = (EditText) findViewById(R.id.body);
        name.setHint("Header");
        body.setHint("Details");
        name.setHintTextColor(getResources().getColor(R.color.colorPrimaryBlue500));
        body.setHintTextColor(getResources().getColor(R.color.colorPrimaryBlue500));
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

    private void setupCalendarDemo() {
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        final Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today);
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

    public void remindMe(View v) {

    }

    public void createMind(View v) {
        name = (EditText) findViewById(R.id.name);
        body = (EditText) findViewById(R.id.body);
        if (name.getText().toString().isEmpty() || body.getText().toString().isEmpty()) {
            final SnackBar snackbar = new SnackBar(this, "All fields are required.", "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.show();
        } else {
            DataBaseHandler db = new DataBaseHandler(this);
            db.addMind(new Mind(name.getText().toString(), body.getText().toString()));
            finish();
            db.close();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
