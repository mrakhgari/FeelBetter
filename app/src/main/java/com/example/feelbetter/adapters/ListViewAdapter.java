package com.example.feelbetter.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.feelbetter.R;
import com.example.feelbetter.activities.MainActivity;
import com.example.feelbetter.fragments.children.TodoFragment;

import java.util.ArrayList;

/**
 * @author mgh
 * this is a cutom adaptor for view list of TodoFragment
 * I used it to have remove and edit options
 */
public class ListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;


    public ListViewAdapter (Context context , ArrayList<String> items){
        super(context, R.layout.list_row , items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row , null);

            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.name);
            name.setText(list.get(position));

            ImageView edit = convertView.findViewById(R.id.edit);
            ImageView remove = convertView.findViewById(R.id.remove);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoFragment.removeItem(position);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageEditDialog(layoutInflater , position);

                }
            });
        }
        return convertView;
    }

    public  void manageEditDialog(LayoutInflater layoutInflater , int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Base_V14_Theme_MaterialComponents_Dialog);

        View customLayout = layoutInflater.inflate(R.layout.layout_dialog , null);
        EditText text = customLayout.findViewById(R.id.edit_text);
        text.setText(TodoFragment.todoList.get(position));
        builder.setView(customLayout);
        builder.setTitle("Edit dialog");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TodoFragment.editItem(position , text.getText().toString());
                Toast.makeText(getContext(), text.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
