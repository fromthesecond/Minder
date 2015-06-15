package com.fireblink.minder.Activity;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.baoyz.widget.PullRefreshLayout;
import com.fireblink.minder.Entity.Mind;
import com.fireblink.minder.R;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private TextView hiddenText;
    private Intent intent;
    private boolean exit;
    private Drawer drawerResult;
    private IProfile profile;
    private AccountHeader headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);
        setSystemBarColor(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile = new ProfileDrawerItem()
                .withName("Mike Penz")
                .withEmail("mikepenz@gmail.com");

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
        drawerResult = new DrawerBuilder()
                .withActivity(MainActivity.this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .withAnimateDrawerItems(true)
                .withSavedInstance(savedInstanceState)
                .build();
        hiddenText = (TextView) findViewById(R.id.hiddenText);
        hiddenText.setVisibility(View.GONE);
        listView = (ListView) findViewById(android.R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Cursor cursor = (Cursor) listView.getItemAtPosition(position); // wtf?
                //Velocity
                List<Mind> minds = new Select().from(Mind.class).execute();
                intent = new Intent(MainActivity.this, ViewMindActivity.class);
                intent.putExtra("title", minds.get(position).name);
                intent.putExtra("body", minds.get(position).body);
                intent.putExtra("id", minds.get(position).getId());
                intent.putExtra("date", minds.get(position).date);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Mind mind = new Select("Id").from(Mind.class).where("name LIKE 'Владос'").executeSingle();
                mind.delete();
                attachDataToList();
                return true;
            }
        });
        attachDataToList();
        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp))
                .withButtonColor(Color.RED)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingButtonClick();
            }
        });
        final PullRefreshLayout layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                attachDataToList();
                layout.setRefreshing(false);
            }
        });
    }

    private void attachDataToList() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,   d MMMM  H:mm ");
        List<Mind> minds = new Select().from(Mind.class).execute();
        if (!minds.isEmpty()) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            for (Mind cn : minds) {
                adapter.add(cn.name);
            }
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.GONE);
            hiddenText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        attachDataToList();
    }

    public void setSystemBarColor(Activity activity) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDarkBlue700));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onFloatingButtonClick() {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
        finish();
    }

    public void testDelAllMinds(MenuItem item) {
        Dialog dialog = new Dialog(this, "Delete all minds", "Do you want to delete all minds ? ");
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().from(Mind.class).execute();
                attachDataToList();
                return;
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
        if (exit) {
            if (!isFinishing()) {
                finish();
            }
        } else {
            Toast.makeText(this, "Press Back Again to Exit", Toast.LENGTH_LONG).show();
            exit = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "asasa", Toast.LENGTH_SHORT).show();
        String message = "Error";
        if (resultCode == RESULT_OK) {
            message = "All minds have been deleted";
        }
        SnackBar snackbar = new SnackBar(MainActivity.this, message, "OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }
}
