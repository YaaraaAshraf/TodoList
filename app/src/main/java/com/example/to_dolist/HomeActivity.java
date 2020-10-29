package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
  Button btn_Add;
  EditText edt_add;
  RecyclerView recyclerView;
  DBHelper dbHelper;
  ArrayList<NoteModel>arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=(RecyclerView)findViewById(R.id.Recycler);
        btn_Add=(Button)findViewById(R.id.btn_add);
        dbHelper=new DBHelper(this);
        Displaynotes();
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });

    }

    private void ShowDialog() {
        final EditText Add_notes;
        Button Save;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        Add_notes=(EditText)dialog.findViewById(R.id.editText_add);
        Save=(Button)dialog.findViewById(R.id.btn_save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Add_notes.getText().toString().isEmpty()) {
                    Add_notes.setError("Please Enter Title");
                }else {
                    dbHelper.addNotes(Add_notes.getText().toString());
                    dialog.cancel();
                    Displaynotes();
                }
            }
        });
    }
    @SuppressLint("WrongConstant")
    private void Displaynotes() {
        arrayList=new ArrayList<>(dbHelper.getNotes());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Notes_Adapter adapter = new Notes_Adapter(getApplicationContext(), this, arrayList);
        recyclerView.setAdapter(adapter);

    }
}
