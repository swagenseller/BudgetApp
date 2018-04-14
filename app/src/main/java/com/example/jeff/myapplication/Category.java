package com.example.jeff.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;



public class Category extends AppCompatActivity implements ChargeDialog.dialogListener {


    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LISTVIEW-------------------------------------
        ListView listView1 = (ListView) findViewById(R.id.chargelist);
        String[] items = {"Milk","Butter","Yogurt","Toothpaste","Ice Cream",
                "Oranges","Apples","Snacks","Gas","Etc","Etc","Etc","Etc"
                ,"Etc","Etc","Etc","Etc","Etc","Etc","Etc","Etc","Etc","Etc",
                "Etc","Etc","Etc","Etc","Etc","Etc"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                items);
        listView1.setAdapter(adapter);
        button = findViewById(R.id.addchargebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newDialog(v);  //have they typed new category yet?
            }
        });



    }

    //called when the user clicks button1
//    button = findViewById(R.id.addchargebutton);
//        button.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View v) {
//            newDialog(v);  //have they typed new category yet?
//        }
//    });
//    public void onPause() {
//        super.onPause();
//    }
//    public void onResume() {
//        super.onResume();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        cursor.close();
//        newCursor.close();
//    }



    public void newDialog(View view) {
        ChargeDialog dialog = new ChargeDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");

    }

}
