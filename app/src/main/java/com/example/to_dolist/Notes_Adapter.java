package com.example.to_dolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Notes_Adapter extends RecyclerView.Adapter<Notes_Adapter.viewHolder> {
    Context context;
    ArrayList<NoteModel> arrayList;
    DBHelper database_helper;
    Activity activity;

    public Notes_Adapter(Context context, Activity activity, ArrayList<NoteModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }
    @Override
    public Notes_Adapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_rowitem, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(Notes_Adapter.viewHolder holder, final int position) {
        holder.txt_title.setText(arrayList.get(position).getTitle());
        database_helper = new DBHelper(context);
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showaAlert(position);
//                database_helper.delete(arrayList.get(position).getId());
//                arrayList.remove(position);
//                Toast.makeText(context,"item removed",Toast.LENGTH_LONG).show();
//                notifyDataSetChanged();
            }
        });
        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showaAlert(final int position) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(activity);
        dialog.setMessage("Do You Want to Delete This To-do?");
        dialog.setTitle("");

        dialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        database_helper.delete(arrayList.get(position).getId());
                arrayList.remove(position);
                notifyDataSetChanged();
                        Toast.makeText(activity,"To-do Deleted",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(activity,"cancel",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getColor(R.color.red));

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        ImageView img_delete, img_update;

        public viewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
            img_update = (ImageView) itemView.findViewById(R.id.img_edit);
        }
    }

    public void showDialog(final int pos) {
        final EditText txt_update;
        Button update;
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.edit_dialog);
        dialog.show();
        txt_update = (EditText) dialog.findViewById(R.id.editText_update);
        update = (Button) dialog.findViewById(R.id.btn_update);
        txt_update.setText(arrayList.get(pos).getTitle());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_update.getText().toString().isEmpty()) {
                    txt_update.setError("Please Enter Your Update.");

                }else {
                    database_helper.update(txt_update.getText().toString(),arrayList.get(pos).getId());
                    arrayList.get(pos).setTitle(txt_update.getText().toString());
                    dialog.cancel();
                    notifyDataSetChanged();
                }
            }

            ;
        });
    }
}
