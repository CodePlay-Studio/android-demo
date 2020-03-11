/*
 * Copyright 2017 (C) CodePlay Studio. All rights reserved.
 *
 * All source code within this app is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package my.com.codeplay.android_demo;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import my.com.codeplay.android_demo.animations.FlippingActivity;
import my.com.codeplay.android_demo.animations.FlyingBirdLaunchActivity;
import my.com.codeplay.android_demo.animations.InterpolatorDemoActivity;
import my.com.codeplay.android_demo.animations.TextSwitchingActivity;
import my.com.codeplay.android_demo.components.BroadcastReceiverActivity;
import my.com.codeplay.android_demo.components.ServiceDemoActivity;
import my.com.codeplay.android_demo.multimedia.VideoPlaybackActivity;
import my.com.codeplay.android_demo.networks.ThreadsDemoActivity;
import my.com.codeplay.android_demo.networks.WebViewDemoActivity;
import my.com.codeplay.android_demo.objects.ListItem;
import my.com.codeplay.android_demo.storages.DatabaseDemoActivity;
import my.com.codeplay.android_demo.storages.LoaderManagerDemoActivity;
import my.com.codeplay.android_demo.viewgroups.BottomSheetsActivity;
import my.com.codeplay.android_demo.viewgroups.GridViewActivity;
import my.com.codeplay.android_demo.viewgroups.ListViewActivity;
import my.com.codeplay.android_demo.viewgroups.RecyclerViewActivity;
import my.com.codeplay.android_demo.viewgroups.StackViewActivity;
import my.com.codeplay.android_demo.viewgroups.TabLayoutActivity;
import my.com.codeplay.android_demo.viewgroups.ViewFlipperActivity;
import my.com.codeplay.android_demo.viewgroups.ViewGroupsActivity;

/**
 * Created by Tham on 05/08/2017.
 */

public class DemoListFragment extends ListFragment {
    public static final int TYPE_VIEWGROUPS = 1;
    public static final int TYPE_ANIMATIONS = 2;
    public static final int TYPE_COMPONENTS = 3;
    public static final int TYPE_MULTIMEDIA = 4;
    public static final int TYPE_NETWORK = 5;
    public static final int TYPE_STORAGES = 6;
    private static final String EXTRA_TYPE = "android_demo.demolistfragment.extra.TYPE";

    private List<ListItem> itemList;
    private FragmentEventListener callback;

    public static DemoListFragment newInstance(int type) {
        DemoListFragment fragment = new DemoListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (FragmentEventListener) context;
        } catch (ClassCastException ignored) {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemList = new ArrayList<>();

        switch (getArguments().getInt(EXTRA_TYPE)) {
            case TYPE_VIEWGROUPS:
                itemList.add(new ListItem(0, R.string.framelayout, R.string.framelayout_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_framelayout));
                itemList.add(new ListItem(R.drawable.ic_relativelayout_black_48dp, R.string.relativelayout,
                        R.string.relativelayout_short_desc, ViewGroupsActivity.class, R.layout.activity_relativelayout));
                itemList.add(new ListItem(R.drawable.ic_linearlayout_black_24dp, R.string.linearlayout, R.string.linearlayout_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_linearlayout));
                itemList.add(new ListItem(0, R.string.scrollview, R.string.scrollview_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_scrollview));
                itemList.add(new ListItem(0, R.string.tablelayout, R.string.tablelayout_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_tablelayout));
                itemList.add(new ListItem(0, R.string.gridlayout, R.string.gridlayout_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_gridlayout));
                itemList.add(new ListItem(0, R.string.coordinatorlayout, R.string.coordinatorlayout_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_coordinatorlayout));
                itemList.add(new ListItem(0, R.string.slidingdrawer, R.string.slidingdrawer_short_desc,
                        ViewGroupsActivity.class, R.layout.activity_sliding_drawer));
                itemList.add(new ListItem(R.drawable.ic_bottom_sheet_48dp, R.string.bottom_sheets, R.string.bottom_sheets_short_desc,
                        BottomSheetsActivity.class, 0));
                itemList.add(new ListItem(R.drawable.ic_tabs, R.string.tablayout,
                        R.string.tablayout_desc_short, TabLayoutActivity.class, 0));
                itemList.add(new ListItem(R.drawable.ic_viewflipper_black_48dp, R.string.viewflipper,
                        R.string.viewflipper_short_desc, ViewFlipperActivity.class, 0));
                itemList.add(new ListItem(R.drawable.ic_gridview_black_48dp, R.string.gridview,
                        R.string.gridview_short_desc, GridViewActivity.class, 0));
                itemList.add(new ListItem(R.drawable.ic_listview_black_48dp, R.string.listview,
                        R.string.listview_short_desc, ListViewActivity.class, 0));
                itemList.add(new ListItem(0, R.string.stackview,
                        R.string.stackview_short_desc, StackViewActivity.class, 0));
                itemList.add(new ListItem(0, R.string.recyclerview, R.string.recyclerview_short_desc,
                        RecyclerViewActivity.class, 0));
                break;
            case TYPE_ANIMATIONS:
                itemList.add(new ListItem(0, R.string.flying_bird_demo, R.string.view_animation_desc, FlyingBirdLaunchActivity.class, 0));
                itemList.add(new ListItem(0, R.string.flipping, R.string.property_animation_desc, FlippingActivity.class, 0));
                itemList.add(new ListItem(0, R.string.text_switching, R.string.viewpropertyanimator_desc, TextSwitchingActivity.class, 0));
                itemList.add(new ListItem(0, R.string.interpolator, R.string.interpolator_desc, InterpolatorDemoActivity.class, 0));
                break;
            case TYPE_COMPONENTS:
                itemList.add(new ListItem(0, R.string.service, R.string.service_desc,
                        ServiceDemoActivity.class, 0));
                itemList.add(new ListItem(0, R.string.broadcast_receiver, R.string.broadcast_receiver_desc,
                        BroadcastReceiverActivity.class, 0));
                itemList.add(new ListItem(0, R.string.content_provider, R.string.content_provider_desc,
                        LoaderManagerDemoActivity.class, 0));
                break;
            case TYPE_MULTIMEDIA:
                itemList.add(new ListItem(0, R.string.videoview, R.string.videoview_short_desc, VideoPlaybackActivity.class, 0));
                break;
            case TYPE_NETWORK:
                itemList.add(new ListItem(0, R.string.webview, R.string.webview_desc,
                        WebViewDemoActivity.class, 0));
                itemList.add(new ListItem(0, R.string.asynctask, R.string.asynctask_desc,
                        ThreadsDemoActivity.class, 0));
                break;
            case TYPE_STORAGES:
                itemList.add(new ListItem(0, R.string.database, R.string.database_desc,
                        DatabaseDemoActivity.class, 0));
                itemList.add(new ListItem(0, R.string.loadermanager, R.string.loadermanager_desc,
                        LoaderManagerDemoActivity.class, 0));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listview, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ItemListAdapter(getActivity(), itemList));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (callback!=null)
            callback.onFragmentListItemClick(itemList.get(position).getTargetComponent(),
                    itemList.get(position).getLayoutId());
    }
}
