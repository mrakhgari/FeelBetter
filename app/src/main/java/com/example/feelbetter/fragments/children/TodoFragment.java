package com.example.feelbetter.fragments.children;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.feelbetter.R;
import com.example.feelbetter.adapters.ListViewAdapter;


import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoFragment extends Fragment{


    EditText ed;
    Button bt;
    Button doneBt;
    public static ListView lv;
    public static ArrayList<String> todoList = new ArrayList<>();
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodoFragment.
     */
    // TODO: Rename and change types and number of parameters
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
       View view =  inflater.inflate(R.layout.fragment_todo, container, false);
       ed = view.findViewById(R.id.todo_edit_text);
       bt = view.findViewById(R.id.add_todo);
       doneBt = view.findViewById(R.id.done_Button);
       lv = view.findViewById(R.id.list_view);
        lv.setFocusable(false);
        lv.setItemsCanFocus(false);
        adapter = new ListViewAdapter(getContext() , todoList);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        onBtClick();
        onDoneBtClick();

        return view;
    }


    /**
     * add new task button at the top of the page.
     */
    public void onBtClick(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result= ed.getText().toString();
                if (result.equals("")){
                    Toast.makeText(getContext() , "There is no task to be added" , Toast.LENGTH_LONG).show();
                }
                else {
                    todoList.add(result);
                    ed.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * done button at the end of the fragment. replaces current fragment with DoneFragment
     */
    public void onDoneBtClick(){
        doneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                DoneFragment doneFragment = new DoneFragment();
                bundle.putStringArrayList("key" , tempDoneArrayList);
                doneFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, doneFragment).commit();

            }
        });
    }


    /**
     * remove item from todoList
     * @param remove
     */
    public static void removeItem(int remove){
        tempDoneArrayList.add(todoList.get(remove));
        todoList.remove(remove);
        lv.setAdapter(adapter);
    }

    /**
     * when the user edits pre tasks, this method updates that item
     * @param position
     * @param newData
     */
    public static void editItem(int position , String newData){
        todoList.set(position , newData);
        lv.setAdapter(adapter);
    }



}