package com.aaronxu.friendapartment.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.HouseIntroduceActivity;
import com.aaronxu.friendapartment.bean.CardBean;
import com.aaronxu.friendapartment.R;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

/**
 * Created by AaronXu on 2016-10-14.
 */
public class FindFragment extends Fragment{
    private static final String TAG = "FindFragment";
    private Context mContext;
    private View rootView;
    private BGARefreshLayout mRefreshLayout;
    private ListView houseListView;
    private List<CardBean> mList;
    private LinearLayout filter;
    private boolean isFilter;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;

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
        initmList();
        initAnimation();

        super.onCreate(savedInstanceState);
    }

    private void initBGA() {
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        // 加载完毕后在UI线程结束下拉刷新
                        mRefreshLayout.endRefreshing();
                    }
                }.execute();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                Toast.makeText(mContext,"没有更多的数据了",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGAStickinessRefreshViewHolder refreshViewHolder = new BGAStickinessRefreshViewHolder(mContext, true);
        refreshViewHolder.setRotateImage(R.mipmap.refresh);
        refreshViewHolder.setStickinessColor(R.color.colorPrimary);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

    }

    private void initmList() {
        mList = new ArrayList<>();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test_item_pic);
        String name = "漓江塔";
        int price = 8000;
        List<Bitmap> list = new  ArrayList<>();
        list.add(BitmapFactory.decodeResource(getResources(),R.drawable.item_default));

        CardBean cardBean = new CardBean(bitmap,name,price,list,"王思聪","geo:121.943719,30.897085?q=上海滴水湖皇冠假日酒店","8618069559388");
        for (int i = 0;i<20;i++)    mList.add(cardBean);

    }
    private void initAnimation() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(200);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find,container,false);
        mRefreshLayout = (BGARefreshLayout) rootView.findViewById(R.id.bga_refresh);
        houseListView = (ListView) rootView.findViewById(R.id.houseListView);
        filter = (LinearLayout) rootView.findViewById(R.id.linearLayout);
        isFilter = false;
        initBGA();
        houseListView.setAdapter(new HouseAdapter(mList,getContext()));
        return rootView;
    }
    public void changeFilterVisible(){
        isFilter = true;
        filter.startAnimation(mShowAction);
        filter.setVisibility(View.VISIBLE);
    }
    public void changeFilterGone(){
        isFilter = false;
        filter.startAnimation(mHiddenAction);
        filter.setVisibility(View.GONE);
    }
    class HouseAdapter extends BaseAdapter{
        private Context context;
        private List<CardBean> list;
        private LayoutInflater mInflater;
        public HouseAdapter(List<CardBean> list,Context context){
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            final CardBean temp = list.get(i);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = mInflater.inflate(R.layout.house_item,null);
                viewHolder.mPic = (ImageView) view.findViewById(R.id.item_pic);
                viewHolder.mName = (TextView) view.findViewById(R.id.item_name);
                viewHolder.mPrice = (TextView) view.findViewById(R.id.item_price);
                viewHolder.mPeopleWants = (TextView) view.findViewById(R.id.item_people_wants);
                viewHolder.mMapIcon = (ImageButton) view.findViewById(R.id.item_map_icon);
                viewHolder.theRelative = (RelativeLayout) view.findViewById(R.id.the_relative);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.mName.setText(temp.getmName());
            viewHolder.mPic.setImageBitmap(temp.getmBitmap());
            viewHolder.mPrice.setText("¥"+temp.getmPrice()+"/月");
            viewHolder.theRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent j = new Intent(mContext, HouseIntroduceActivity.class);
                    j.putExtra("item",temp);
                    startActivity(j);
                }
            });
            viewHolder.mMapIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"跳转至自带地图应用",Toast.LENGTH_SHORT).show();
                    Uri mUri = Uri.parse(temp.getmMapLocation());
                    Intent mIntent = new Intent(Intent.ACTION_VIEW,mUri);
                    startActivity(mIntent);
                }
            });
            return view;
        }
        class ViewHolder{
            ImageView mPic;
            TextView mName;
            TextView mPrice;
            TextView mPeopleWants;
            ImageButton mMapIcon;
            RelativeLayout theRelative;
        }
    }
}
