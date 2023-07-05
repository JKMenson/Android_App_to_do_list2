package com.example.mobilecomputingcat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText taskId, title, description;
    Button add, update, delete, view;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskId = findViewById(R.id.txtID);
        title = findViewById(R.id.txtTitle);
        description = findViewById(R.id.txtDescription);

        add = findViewById(R.id.btnAdd);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskIdTXT = taskId.getText().toString();
                String titleTXT = title.getText().toString();
                String descriptionTXT = description.getText().toString();

                Boolean checkinsertdata = DB.insertUserData(taskIdTXT, titleTXT, descriptionTXT);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "New task has been added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Data hasn't been Inserted", Toast.LENGTH_SHORT).show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskIdTXT = taskId.getText().toString();
                String titleTXT = title.getText().toString();
                String descriptionTXT = description.getText().toString();

                Boolean checkupdatetdata = DB.updateUserData(taskIdTXT, titleTXT, descriptionTXT);
                if(checkupdatetdata==true)
                    Toast.makeText(MainActivity.this, "Task has been Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Please select a task to Update", Toast.LENGTH_SHORT).show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskIdTXT = taskId.getText().toString();

                Boolean checkdeletedata = DB.deleteUserData(taskIdTXT);
                if(checkdeletedata==true)
                    Toast.makeText(MainActivity.this, "Task has been Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Please Select a Task to delete!", Toast.LENGTH_SHORT).show();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Tasks Exist,Please Insert Tasks!", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("TaskId: "+res.getString(0)+"\n");
                    buffer.append("Title: "+res.getString(1)+"\n");
                    buffer.append("Description: "+res.getString(2)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("All Tasks:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }
}