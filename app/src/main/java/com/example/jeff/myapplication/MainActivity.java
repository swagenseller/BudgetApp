package com.example.jeff.myapplication;


import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import java.io.File;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.*;

public class MainActivity extends AppCompatActivity implements ExpenseDialog.dialogListener {
    private String name_of_category;
    private String allocated_budget;
    private Button button;
    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DataBaseHelper(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        ListView catList = findViewById(R.id.catList);
        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        String q = "SELECT Name || ' ' || Amount from category";
        ArrayList<String> your_array_list = queryData(q,myDb);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        catList.setAdapter(arrayAdapter);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialog();
            }
        });

    }


    public ArrayList<String> queryData(String query, DataBaseHelper myDb) {
        ArrayList<String> arrlist = new ArrayList<>();
        try {
            Cursor c = myDb.getWritableDatabase().rawQuery(query,null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                arrlist.add(c.getString(0));
            }
            c.close();
        }
        catch (SQLException e){
            Log.d("queryData", "cursor error");
        }
        return arrlist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newDialog() {
        ExpenseDialog dialog = new ExpenseDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");

    }

    //
    @Override
    public void applyTexts(String name, String amount) {
            myDb.insertCategory(name, amount);
    }

    // basic method to switching activities
    public void gotoAct1(View V) {
        Intent intent = new Intent(this, NewExpense.class);
        startActivity(intent);
    }






}


