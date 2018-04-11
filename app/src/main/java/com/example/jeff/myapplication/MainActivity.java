package com.example.jeff.myapplication;


import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.*;

public class MainActivity extends AppCompatActivity implements ExpenseDialog.dialogListener {
    private String name_of_category;
    private String allocated_budget;
    private Button button;
    private ListView catList;
    private ArrayList<String> arrList;
    private String catQuery;
    private ArrayAdapter<String> arrAdapter;
    public Cursor c;
    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        myDb = new DataBaseHelper(this);


        catList = findViewById(R.id.catList);
        catList.setClickable(true);

        catQuery = "SELECT Name || ' ' || Amount from category";
        arrList = queryData(catQuery,myDb);
        arrAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrList );

        catList.setAdapter(arrAdapter);

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(view.getContext(), Category.class));
            }
        });

       /* catList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                //grab id
                myDb.removeItem("Category", "ID", "");
                return true;
            }
        });
*/
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newDialog(v);  //have they typed new category yet?
            }
        });


    }



    public ArrayList<String> queryData(String query, DataBaseHelper myDb) {
        ArrayList<String> a = new ArrayList<>();
        try {
            c = myDb.getWritableDatabase().rawQuery(query,null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                a.add(c.getString(0));
            }
            c.close();
        }
        catch (SQLException e){
            Log.d("queryData", "cursor error");
        }
        return a;
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

    public void newDialog(View view) {
        ExpenseDialog dialog = new ExpenseDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");

    }

    //
    @Override
    public void applyTexts(String name, String amount) {
        myDb.insertCategory(name, amount);
        arrAdapter.notifyDataSetChanged();
        arrList = queryData(catQuery,myDb);
        arrAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrList );
        catList.setAdapter(arrAdapter);


    }

    // basic method to switching activities
    public void gotoAct1(View V) {
        Intent intent = new Intent(this, Category.class);
        startActivity(intent);
    }






}


