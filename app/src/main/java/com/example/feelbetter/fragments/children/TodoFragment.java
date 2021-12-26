package com.example.feelbetter.fragments.children;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.feelbetter.R;
import com.example.feelbetter.activities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoFragment extends Fragment implements AdapterView.OnItemClickListener {


    EditText ed;
    Button bt;
    ListView lv;
    ArrayList<String> todoList;
    ArrayAdapter<String> adapter;
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
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_todo, container, false);
       ed = view.findViewById(R.id.todo_edit_text);
       bt = view.findViewById(R.id.add_todo);
       lv = view.findViewById(R.id.list_view);
        todoList = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity() , android.R.layout.simple_list_item_multiple_choice, todoList);

        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setOnItemClickListener(this);
        onBtClick();
        return view;
    }


    public void onBtClick(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result= ed.getText().toString();
                CheckBox cb = new CheckBox(getContext());
                cb.setText(result);
                todoList.add(result);

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String items = parent.getItemAtPosition(position).toString();
        DoneFragment.doneList.add(items);

//        FragmentManager fragmentManager = getChildFragmentManager();
//        Fragment fragmentA = fragmentManager.findFragmentByTag("done_fragment");
//        if (fragmentA == null) {
//
//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&***********");
//            DoneFragment done =new DoneFragment();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.done_fragment, done);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }
//        Fragment fragmentA2 = fragmentManager.findFragmentByTag("done_fragment");
//        if(fragmentA2 == null){
//            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        }

//            DoneFragment done =new DoneFragment();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.todo_fragment, done);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            done.arrayList.add(items);
//            done.adapter2.notifyDataSetChanged();
//            // not exist
//        } else {
//            System.out.println("******************8");
            // fragment exist
//        }
//        finish();
//        doneFragment.arrayList.add(items.toString());
////
//        doneFragment.adapter2.notifyDataSetChanged();
        Toast.makeText(getContext(), items , Toast.LENGTH_LONG).show();

    }


}