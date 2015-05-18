package com.fireblink.minder;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.melnykov.fab.FloatingActionButton;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> adapter;
    private FloatingActionButton fab;
    private ListView listView;
    private DataBaseHandler db;
    private List<Mind> minds;
    private Intent intent;
    private boolean exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSystemBarColor(this);
        listView = (ListView) findViewById(android.R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);
        attachDataToList();
        setOnListItemClickListener();
        final PullRefreshLayout layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.setRefreshing(false);
            }
        });
    }

    private void attachDataToList() {
        db = new DataBaseHandler(this);
        minds = db.getAllMinds();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (Mind cn : minds) {
            adapter.add(cn.get_name());
        }
        listView.setAdapter(adapter);
        db.close();
    }

    private void setOnListItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int dbId = (int) parent.getAdapter().getItemId(position) + 1;
                Log.i("Debug: ", String.valueOf(dbId) + " dbID");
                intent = new Intent(MainActivity.this, ViewMindActivity.class);
                intent.putExtra("title", db.getMindById(dbId).get_name());
                intent.putExtra("body", db.getMindById(dbId).get_body());
                intent.putExtra("id", db.getMindById(dbId).get_id());
                startActivity(intent);
                db.close();
            }
        });
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

    public void onFloatingButtonClick(View v) {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
        finish();
    }

    public void testDelAllMinds(MenuItem item) {
        Dialog dialog = new Dialog(this, "Delete all minds", "Do you want to delete all minds ? ");
        dialog.show();
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "All minds have been deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
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
