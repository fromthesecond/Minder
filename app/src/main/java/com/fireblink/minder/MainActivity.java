package com.fireblink.minder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class MainActivity extends ActionBarActivity  {

    private ArrayAdapter<String> adapter;
    private FloatingActionButton fab;
    private ListView listView;
    private DataBaseHandler db;
    private List<Mind> minds;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(android.R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);
        db = new DataBaseHandler(this);
        minds = db.getAllMinds();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (Mind cn : minds) {
            adapter.add(cn.get_name());
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int dbId = (int) parent.getAdapter().getItemId(position) + 1;
                Log.i("Debug: ", String.valueOf(dbId)+ " dbID");
                intent = new Intent(MainActivity.this, ViewMindActivity.class);
                intent.putExtra("title", db.getMindById(dbId).get_name());
                intent.putExtra("body", db.getMindById(dbId).get_body());
                Log.i("Debug: ", db.getMindById(dbId).get_body()+ " body value");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    public void onFloatingButtonClick (View v){
        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
    }
    public void testDelAllMinds(MenuItem item) {
        Dialog dialog = new Dialog(this,"Delete all minds", "Do you want to delete all minds ? ");
        dialog.show();
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "All minds have been deleted", Toast.LENGTH_LONG).show();
            }
        });

        dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

    }

}
