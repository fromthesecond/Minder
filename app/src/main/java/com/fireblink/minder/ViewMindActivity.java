package com.fireblink.minder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class ViewMindActivity extends ActionBarActivity {

    private TextView title;
    private TextView body;
    private DataBaseHandler db;
    private Target viewTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mind);
        int primary = getResources().getColor(R.color.colorPrimaryBlue500);
        int secondary = getResources().getColor(R.color.colorPrimaryDarkBlue700);
        setupSlidr(primary, secondary);
        setupToolBar();
        setupSystemBarColor();
        setTitle(getIntent().getExtras().getString("title"));
        title = (TextView) findViewById(R.id.titleTxt);
        body = (TextView) findViewById(R.id.bodyTxt);
        title.setText(getIntent().getExtras().getString("title").toUpperCase());
        body.setText(getIntent().getExtras().getString("body"));

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

    public void deleteMind(MenuItem item) {
        db = new DataBaseHandler(this);
        db.deleteMind(db.getMindById(getIntent().getIntExtra("id", 0)));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
