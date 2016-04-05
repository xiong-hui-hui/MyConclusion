package com.xiong.myconclusion.contact;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.xiong.myconclusion.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

import Utils.PinyinUtils;

public class TestBaseAdapter extends BaseAdapter implements
		StickyListHeadersAdapter, SectionIndexer {

	private LayoutInflater inflater;
	private ArrayList<String> dataList;
	/** 选中目录 */
	private int[] sectionIndices;
	/** 选中字母 */
	private String[] sectionsLetters;

	public TestBaseAdapter(Context context, ArrayList<String> outDataList) {
		inflater = LayoutInflater.from(context);
		this.dataList = outDataList;
		sectionIndices = getSectionIndices();
		sectionsLetters = getStartingLetters();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.test_list_item_layout,
					parent, false);
		}
		TextView txt = (TextView) convertView.findViewById(R.id.text);
		txt.setText(dataList.get(position));
		return convertView;
	}

	private int[] getSectionIndices() {
		int[] sections = new int[dataList.size()];
		for (int i = 0; i < dataList.size(); i++) {
			sections[i] = i;
		}
		return sections;
	}

	/***
	 * 得到所有数据的首字的首字母
     */
	private String[] getStartingLetters() {
		String[] letters = new String[sectionIndices.length];
		for (int i = 0; i < sectionIndices.length; i++) {
			letters[i] = PinyinUtils.getInstance()
			.getAlpha(dataList.get(sectionIndices[i])).subSequence(0, 1).toString();
			//Log.i("xiong", "getStartingLetters:" + letters[i]);
		}
		return letters;
	}
	@Override
	public Object[] getSections() {
		return sectionsLetters;
	}

	public int getSectionStart(int itemPosition) {
		return getPositionForSection(getSectionForPosition(itemPosition));
	}

	/**得到第一个文字*/
	@Override
	public long getHeaderId(int position) {
		// return the first character of the country as ID because this is what
		// headers are based upon
		Log.i("xiong", "getHeaderId:" + dataList.get(position).subSequence(0, 1).charAt(0));
		String txt = dataList.get(position);
		
		   Pattern p= Pattern.compile("[a-zA-Z]*");
		     if(p.matcher(txt).matches()){
				 return dataList.get(position).toUpperCase().subSequence(0, 1).charAt(0);
		     }
		     p= Pattern.compile("[\u4e00-\u9fa5]*");
		     if(p.matcher(txt).matches()){
		    	 return PinyinUtils.getInstance().getAlpha(dataList.get(position)).subSequence(0, 1).charAt(0);	
		     }
		    
		     return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < sectionIndices.length; i++) {
			if (position < sectionIndices[i]) {
				return i - 1;
			}
		}
		return sectionIndices.length - 1;
	}

	@Override
	public int getPositionForSection(int section) {
		if (section >= sectionIndices.length) {
			section = sectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return sectionIndices[section];
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.header, parent, false);
		}
		TextView txt = (TextView) convertView.findViewById(R.id.text1);
		//设置第一个字母为头部text的值
		//String str = PinyinUtils.getInstance().getAlpha(dataList.get(position));
		String str = PinyinUtils.getInstance()
				.getAlpha(dataList.get(position)).subSequence(0, 1).toString();
		//转换首字母为大写
		txt.setText(str.toUpperCase());
		return convertView;
	}

	public void clear() {
		dataList = new ArrayList<String>();
		sectionIndices = new int[0];
		sectionsLetters = new String[0];
		notifyDataSetChanged();
	}

	public void restore(ArrayList<String> outDataList) {
		this.dataList = outDataList;
		sectionIndices = getSectionIndices();
		sectionsLetters = getStartingLetters();
		notifyDataSetChanged();
	}

}
