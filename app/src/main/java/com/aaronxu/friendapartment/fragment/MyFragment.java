package com.aaronxu.friendapartment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaronxu.friendapartment.R;

/**
 * Created by AaronXu on 2016-10-14.
 */
public class MyFragment extends Fragment {
    private static final String TAG = "FindFragment";
    private Context mContext;
    private View rootView;
    public MyFragment(){
    }
    public static MyFragment newInstance(String title){
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "FindFragment onCreate: ");
        mContext = getContext();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my,container,false);

        return rootView;
    }
}
