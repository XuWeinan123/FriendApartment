package com.aaronxu.friendapartment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aaronxu.friendapartment.CardBean;
import com.aaronxu.friendapartment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AaronXu on 2016-10-14.
 */
public class FindFragment extends Fragment{
    private static final String TAG = "FindFragment";
    private Context mContext;
    private View rootView;
    private ListView houseListView;
    private List<CardBean> mList;
    public FindFragment(){
    }
    public static FindFragment newInstance(String title){
        FindFragment fragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "FindFragment onCreate: ");
        mContext = getContext();
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find,container,false);
        houseListView = (ListView) rootView.findViewById(R.id.houseListView);
        houseListView.setAdapter(new HouseAdapter(mList));
        return rootView;
    }
    class HouseAdapter extends BaseAdapter{
        private Context context;
        private List<CardBean> list = new ArrayList<>();
        private LayoutInflater mInflater;
        public HouseAdapter(List<CardBean> list){
            this.list = list;
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = mInflater.inflate(R.layout.house_item,null);
                viewHolder.mBitmap = (ImageView) view.findViewById(R.id.item_pic);
                viewHolder.mName = (TextView) view.findViewById(R.id.item_name);
                viewHolder.mPercent = (TextView) view.findViewById(R.id.item_percent);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            return null;
        }
        class ViewHolder{
            ImageView mBitmap;
            TextView mName;
            TextView mPercent;
        }
    }
}
