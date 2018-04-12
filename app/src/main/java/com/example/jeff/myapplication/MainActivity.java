package com.example.jeff.myapplication;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity implements ExpenseDialog.dialogListener {
    public Button button;
    private ListView catList;
    private Cursor cursor;
    private Cursor newCursor;
    DataBaseHelper myDb;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        catList = findViewById(R.id.catList);
        catList.setClickable(true);
        try {
            myDb = new DataBaseHelper(this);
            db = myDb.getReadableDatabase();
            cursor = db.query("CATEGORY", new String[]{"_id", "Name", "Amount"},
                    null, null, null, null, null);

            CursorAdapter listAdapter  = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"Name"},
                    new int[]{android.R.id.text1},
                    0
                    );
            catList.setAdapter(listAdapter);
        } catch(SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
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
    public void onPause() {
        super.onPause();
    }
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        newCursor.close();
    }



    public void newDialog(View view) {
        ExpenseDialog dialog = new ExpenseDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");

    }
    //
    @Override
    public void applyTexts(String name, String amount) {
        myDb.insertCategory(name, amount);
        DataBaseHelper sqlHelper = new DataBaseHelper(this);
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        newCursor = db.query("CATEGORY", new String[]{"_id", "Name", "Amount"},
                null, null, null, null, null);
        CursorAdapter newList =  new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                newCursor,
                new String[]{"Name"},
                new int[]{android.R.id.text1},
                0
        );
        catList.setAdapter(newList);

    }

    // basic method to switching activities
    public void gotoAct1(View V) {
        Intent intent = new Intent(this, Category.class);
        startActivity(intent);
    }








}


