package com.example.feelbetter.fragments.children;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.feelbetter.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoneFragment extends Fragment {
    ListView lv;
//    KeepListService keepListService = new KeepListService();
    public static ArrayList<String> doneList = new ArrayList<>();
    ArrayAdapter<String> adapter2 ;


//

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoneFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoneFragment newInstance(String param1, String param2) {
        DoneFragment fragment = new DoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("******************************");
        System.out.println(doneList);
//        doneList = KeepListService.doneList;
//        System.out.println(doneList);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("****^^^^^^^^^^^****");
        System.out.println(doneList);
//        doneList = KeepListService.doneList;
        adapter2.notifyDataSetChanged();
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
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        System.out.println("*&&&&&&&&&&&&&&&&&&&77*");
        System.out.println(doneList);
//        doneList = KeepListService.doneList;
        if (adapter2 != null)
        adapter2.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        lv = view.findViewById(R.id.done_list_view);
        doneList.add("#######");
        adapter2 = new ArrayAdapter(getActivity() , android.R.layout.simple_list_item_1, doneList);
        lv.setAdapter(adapter2);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        return view;


    }


}