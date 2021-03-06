/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.administrator.myapplication.recycler_swipe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.recycler_swipe.swpie.Closeable;
import com.example.administrator.myapplication.recycler_swipe.swpie.OnSwipeMenuItemClickListener;
import com.example.administrator.myapplication.recycler_swipe.swpie.SwipeMenu;
import com.example.administrator.myapplication.recycler_swipe.swpie.SwipeMenuCreator;
import com.example.administrator.myapplication.recycler_swipe.swpie.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>左右两侧Item菜单：横向排布。</p>
 * Created by Yan Zhenjie on 2016/7/27.
 */
public class MenuHorizontalActivity extends AppCompatActivity {

    private Activity mContext;

    private MenuAdapter mMenuAdapter;

    private List<String> mDataList;

    private SwipeXRecyclerView mMenuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        mContext = this;
        mDataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDataList.add("我是第" + i + "个。");
        }
        mMenuRecyclerView = (SwipeXRecyclerView) findViewById(R.id.recycler_view);
        mMenuAdapter = new MenuAdapter(this);
        mMenuAdapter.setData(mDataList);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);


    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            {
//                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_green)// 点击的背景。
//                        .setImage(R.mipmap.ic_action_add) // 图标。
//                        .setWidth(width) // 宽度。
//                        .setHeight(height); // 高度。
//                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。
//
//                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_red)
//                        .setImage(R.mipmap.ic_action_close)
//                        .setWidth(width)
//                        .setHeight(height);
//
//                swipeLeftMenu.addMenuItem(closeItem); // 添加一个按钮到左侧菜单。
//            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_launcher)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

//                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_purple)
//                        .setImage(R.mipmap.ic_action_close)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。
//
//                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_green)
//                        .setText("添加")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }
        }
    };


    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener
            () {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition.
         *                        这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction
         * 如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int
                direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeXRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition,
                        Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeXRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition,
                        Toast.LENGTH_SHORT).show();
            }

            //   如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                mDataList.remove(adapterPosition);
                mMenuAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

}
