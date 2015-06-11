package com.fireblink.minder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.fireblink.minder.Entity.Mind;
import com.fireblink.minder.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.SnackBar;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.text.SimpleDateFormat;

public class ViewMindActivity extends ActionBarActivity {

    private TextView title;
    private TextView body;
    private TextView forDate;
    private Target viewTarget;
    private Long currentId;
    private ButtonRectangle hiddenButtonSave;
    private ButtonFlat hiddenButtonCancel;
    private EditText editTextName;
    private EditText editTextBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mind);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM  H:mm ");
        int primary = getResources().getColor(R.color.colorPrimaryBlue500);
        int secondary = getResources().getColor(R.color.colorPrimaryDarkBlue700);
        setupSlidr(primary, secondary);
        setupToolBar();
        setupSystemBarColor();
        setTitle(null);
        forDate = (TextView) findViewById(R.id.forDate);
        forDate.setText(dateFormat.format(getIntent().getExtras().get("date")));
        getCurrentId();
        setInformationToFields();
        viewTarget = new ViewTarget(R.id.customToolbar, this);
        new ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setContentTitle("How to go back?")
                .setContentText("You can swipe from left side")
                .hideOnTouchOutside()
                .singleShot(112)
                .setStyle(R.style.CustomShowcaseTheme2)
                .build();
    }

    private void setInformationToFields() {
        Mind mind = Mind.load(Mind.class, currentId);
        title = (TextView) findViewById(R.id.titleTxt);
        body = (TextView) findViewById(R.id.bodyTxt);
        title.setText(mind.getName().toString().toUpperCase());
        body.setText(mind.getBody().toString());
    }

    public void editMindSave(MenuItem item) {
        editTextName = (EditText) findViewById(R.id.name);
        editTextBody = (EditText) findViewById(R.id.body);
        hiddenButtonSave = (ButtonRectangle) findViewById(R.id.saveMindBtn);
        hiddenButtonCancel = (ButtonFlat) findViewById(R.id.cancelFlatViewMind);
        hiddenButtonSave.setVisibility(View.VISIBLE);
        hiddenButtonCancel.setVisibility(View.VISIBLE);
        editTextBody.setVisibility(View.VISIBLE);
        editTextName.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        body.setVisibility(View.GONE);
        editTextName.setText(Mind.load(Mind.class, currentId).getName());
        editTextBody.setText(Mind.load(Mind.class, currentId).getBody());
        hiddenButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mind mind = Mind.load(Mind.class, currentId);
                if (editTextName.length() < 3 || editTextBody.length() < 5) {
                    final SnackBar snackbar = new SnackBar(ViewMindActivity.this, "All fields are required.", "OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            return;
                        }
                    });
                    snackbar.show();
                } else {
                    mind.setName(editTextName.getText().toString());
                    mind.setBody(editTextBody.getText().toString());
                    mind.save();
                    editTextBody.setVisibility(View.GONE);
                    editTextName.setVisibility(View.GONE);
                    hiddenButtonSave.setVisibility(View.GONE);
                    hiddenButtonCancel.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    body.setVisibility(View.VISIBLE);
                    setInformationToFields();
                }
            }
        });
    }


    private void getCurrentId() {
        if (currentId == null) {
            Mind mind = new Select("Id").from(Mind.class).where("name LIKE" + "'" +
                    getIntent().getExtras().getString("title") + "'").executeSingle();
            this.currentId = mind.getId();
            Log.i("Data", "ID of current mind: " + this.currentId);
        }
    }

    private void setupSystemBarColor() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDarkBlue700));
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.customToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void setupSlidr(int primary, int secondary) {
        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(
                        SlidrPosition.LEFT
                )
                .sensitivity(0.2f)
                .build();
        Slidr.attach(this, config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mind, menu);
        return true;
    }

    public void backToMain(MenuItem item) {
        finish();
    }

    public void cancel(View view) {
        editTextBody.setVisibility(View.GONE);
        editTextName.setVisibility(View.GONE);
        hiddenButtonSave.setVisibility(View.GONE);
        hiddenButtonCancel.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        body.setVisibility(View.VISIBLE);
        setInformationToFields();
    }

    public void deleteMind(MenuItem item) {
        Mind.load(Mind.class, currentId).delete();
        Intent intent = new Intent(ViewMindActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // finish all previous activities
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}