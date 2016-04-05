package com.xiong.myconclusion;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.xiong.myconclusion.contact.PinyinComparator;
import Utils.PinyinUtils;
import com.xiong.myconclusion.contact.SideBar;
import com.xiong.myconclusion.contact.TestBaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;

public class Contact extends Activity implements AdapterView.OnItemClickListener,
        SideBar.OnTouchingLetterChangedListener,TextView.OnClickListener {

    private Context context = this;

    private TestBaseAdapter mAdapter;
    private StickyListHeadersListView mStickyList;

    private String[] data = new String[] { "伊拉克","阿富汗","孟加拉国","文莱", "柬埔寨", "朝鲜",
            "印度", "伊朗", "以色列", "约旦", "老挝", "中国澳门", "马尔代夫", "尼泊尔", "巴基斯坦",
            "菲律宾", "沙特阿拉伯", "韩国", "叙利亚", "ss","SS","土耳其", "也门共和国", "中国", "东帝汶", "吉尔吉斯斯坦",
            "土库曼斯坦", "阿尔及利亚","开江","aa","bb","BB"};
    private ArrayList<String> dataList;
    private TextView mTvSearch;
    private EditText mEtInput;
    private SideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact);
        setView();
        setListener();
        Arrays.sort(data, new PinyinComparator());
        dataList = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            dataList.add(data[i]);
        }
        mAdapter = new TestBaseAdapter(this, dataList);
        mStickyList.setAdapter(mAdapter);
    }

    private void setView() {
        sideBar = (SideBar) findViewById(R.id.sideBar);
        mStickyList = (StickyListHeadersListView) findViewById(R.id.list);
        mTvSearch = (TextView) findViewById(R.id.begin_search);// 搜索
        mEtInput = (EditText) findViewById(R.id.search_input);// 输入搜索内容
    }

    private void setListener() {
        sideBar.setOnTouchingLetterChangedListener(this);
        mStickyList.setOnItemClickListener(this);
        mTvSearch.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(context, "Item " + position + " clicked!", LENGTH_SHORT)
                .show();
    }

    @Override
    public void onTouchingLetterChanged(String s) {
		/*if (!"".equals(s) && s != null) {
			show.setVisibility(View.VISIBLE);
			show.setText(s);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					show.setVisibility(View.GONE);
				}
			}, 1500);*/
        if(!TextUtils.isEmpty(s)){
            Toast.makeText(context,"***"+s, LENGTH_SHORT).show();

            int position = alphaIndexer(s);
            if (position > 0) {
                mStickyList.setSelection(position);
            }
        }
    }

    public int alphaIndexer(String s) {
        int position = 0;
        String st = "";
        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                st = dataList.get(i).toString().trim();
                if (!"".equals(st) && st != null) {
                    if (PinyinUtils.getInstance().getAlpha(st).startsWith(s)) {
                        position = i;
                        break;
                    }
                }
            }
        }
        return position;
    }

    @Override
    public void onClick(View v) {

        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
       /* isSearch = true;
        mSourceDateList = searchPassenger();
        sortAdapter = new SortTranvelAdapter(mActivity, mSourceDateList);
        mNationListView.setAdapter(sortAdapter);
        sortAdapter.notifyDataSetChanged();*/
    }
}

