package com.example.feelbetter.fragments.children;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.feelbetter.R;
import com.example.feelbetter.adapters.ListViewAdapter;
import com.example.feelbetter.firestore.FirestoreClass;
import com.example.feelbetter.models.TodoItem;


import java.util.ArrayList;
import java.util.Locale;

/**
 * @author mgh
 * fragment of todoTasks
 * user can add item,delete it, and edit it
 */
public class TodoFragment extends Fragment {


    EditText ed;
    Button bt;
    Button doneBt;
    Button startTimeBtn;
    Button endTimeBtn;
    int startHour = -1, startMinute = -1;
    int endHour = -1, endMinute = -1;
    public static ListView lv;
    public static ArrayList<String> todoList = new ArrayList<>();
    public static ArrayList<TodoItem> todoObjList = new ArrayList<>();
    public static ListViewAdapter adapter; //custom adaptor
    static ArrayList tempDoneArrayList = new ArrayList();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodoFragment() {

        // Required empty public constructor
    }


    public static TodoFragment newInstance(String param1, String param2) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tempDoneArrayList.clear();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ed = view.findViewById(R.id.todo_edit_text);
        bt = view.findViewById(R.id.add_todo);
        startTimeBtn = view.findViewById(R.id.start_time);
        endTimeBtn = view.findViewById(R.id.end_time);
        doneBt = view.findViewById(R.id.done_Button);
        lv = view.findViewById(R.id.list_view);
        lv.setFocusable(false);
        lv.setItemsCanFocus(false);
        adapter = new ListViewAdapter(getContext(), todoList);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        onBtClick();
        onDoneBtClick();

        //time picker for choosing start time for the current task
        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(true);
            }
        });
        //time picker for choosing end time for the current task
        endTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(false);
            }
        });

        return view;
    }


    /**
     * add new task button at the top of the page.
     */
    public void onBtClick() {
        bt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String result = ed.getText().toString();
                if ((startHour == -1)) {
                    Toast.makeText(getContext(), "Select a start time", Toast.LENGTH_LONG).show();
                } else if ((endHour == -1)) {
                    Toast.makeText(getContext(), "Select an end time", Toast.LENGTH_LONG).show();
                } else if (result.equals("")) {
                    Toast.makeText(getContext(), "There is no task to be added", Toast.LENGTH_LONG).show();
                } else {
                    TodoItem ti = new TodoItem(String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute), String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute), result, new FirestoreClass().getCurrentUserId());
                    todoObjList.add(ti);
//                    List<String> field1List = todoObjList.stream().map(TodoItem::getTask).collect(Collectors.toList());
                    todoList.add(result);
                    startHour = -1;
                    endHour = -1;
                    ed.setText("");
                    startTimeBtn.setText("starttime");
                    endTimeBtn.setText("duetime");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static TodoItem findObj(String item) {
        TodoItem todoObj = todoObjList.stream()
                .filter(todo -> item.equals(todo.getTask()))
                .findAny()
                .orElse(null);
        return todoObj;
    }

    /**
     * done button at the end of the fragment. replaces current fragment with DoneFragment
     */
    public void onDoneBtClick() {
        doneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                DoneFragment doneFragment = new DoneFragment();
                bundle.putStringArrayList("key", tempDoneArrayList);
                doneFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, doneFragment).commit();

            }
        });
    }


    /**
     * remove item from todoList
     *
     * @param remove
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void removeItem(int remove) {
        TodoItem item = findObj(todoList.get(remove));
        if (item != null)
            todoObjList.remove(item);
        todoList.remove(remove);
        lv.setAdapter(adapter);
    }

    /**
     * when the user edits pre tasks, this method updates that item
     *
     * @param position
     * @param newData
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void editItem(int position, String newData) {
        TodoItem item = findObj(todoList.get(position));
        if (item != null) {
            TodoItem todoItem = new TodoItem(item.getStartTime(), item.getDueTime(), newData, new FirestoreClass().getCurrentUserId());
            todoObjList.add(todoItem);
            todoList.remove(item);
        }
        todoList.set(position, newData);
        lv.setAdapter(adapter);
    }

    /**
     * makes an Item from todolist done
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void doneItem(int position) {
        TodoItem item = findObj(todoList.get(position));
        if (item != null) {
            todoList.remove(item);
        }
        tempDoneArrayList.add(todoList.get(position));
        todoList.remove(position);
        lv.setAdapter(adapter);
    }


    public void popTimePicker(Boolean isStart) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isStart) {
                    startHour = hourOfDay;
                    startMinute = minute;
                    startTimeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute));
                } else {
                    endHour = hourOfDay;
                    endMinute = minute;
                    endTimeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute));
                }
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog;
        if (isStart) {
            timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, startHour, startMinute, true);
        } else
            timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, endHour, endMinute, true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();

    }


}