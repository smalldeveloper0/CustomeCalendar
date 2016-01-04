package com.lxl.view;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.calendar.R;

public class CalendarAdapter extends BaseAdapter {

	public interface onDateSelectedListener{
		public void onSelected(Date date);
	}
	
	private int mYear;
	private int mMonth;
	private Context context;
	private int offset;
	private int select_date=-1;
	private onDateSelectedListener listener;
	public CalendarAdapter(Context context,onDateSelectedListener listener) {
		this.context=context;
		Calendar calendar=Calendar.getInstance();
		mYear=calendar.get(Calendar.YEAR);
		mMonth=calendar.get(Calendar.MONTH);
		this.listener=listener;
		offset=(getFirstDayWeekofMonth(mYear, mMonth)-1)%7;
	}
	
	
	
	
	public int getYear() {
		return mYear;
	}
	



	public String getCurYearMonth(){
		return mYear+"年"+(mMonth+1)+"月";
	}

	public void setYear(int curYear) {
		this.mYear = curYear;
		offset=(getFirstDayWeekofMonth(curYear, mMonth)-1)%7;
		select_date=-1;
		this.notifyDataSetChanged();
	}




	public int getMonth() {
		return mMonth+1;
	}




	public void setMonth(int curMonth) {
		this.mMonth = curMonth-1;
		select_date=-1;
		offset=(getFirstDayWeekofMonth(mYear, curMonth)-1)%7;
		this.notifyDataSetChanged();

	}


	private int getDayofMonth(int year,int month){
		
		Calendar calendar=Calendar.getInstance(Locale.CHINA);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	private int getFirstDayWeekofMonth(int year,int month){
		Calendar calendar=Calendar.getInstance(Locale.CHINA);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	@Override
	public int getCount() {
		return getDayofMonth(mYear, mMonth)+offset;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_calendar,parent,false);
			textView=(TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(textView);
		
		}else{
			textView=(TextView) convertView.getTag();
		}
		
		final int day=position-offset+1;
		
		if(position>=offset){
		
			textView.setText(day+"");
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, mYear);
			calendar.set(Calendar.MONTH, mMonth);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			Date item_Date =calendar.getTime();
			//获取现在的日期 往后加3天  
			Calendar calendar2=Calendar.getInstance();
			calendar2.add(Calendar.DAY_OF_MONTH, 3);
			Date now=calendar2.getTime();
			
			
			if (now.before(item_Date)) {
				
				
				textView.setTextColor(Color.RED);
				
				textView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						select_date=day;
						CalendarAdapter.this.notifyDataSetChanged();
						if(listener!=null){
							Calendar calendar=Calendar.getInstance();
							calendar.set(Calendar.YEAR, mYear);
							calendar.set(Calendar.MONTH, mMonth);
							calendar.set(Calendar.DAY_OF_MONTH, day);
							listener.onSelected(calendar.getTime());
						}
					}
				});
				
				if(select_date==day){
					textView.setBackgroundResource(R.drawable.bg_calendar_item);
					textView.setTextColor(Color.WHITE);
				}else{
					textView.setBackgroundColor(Color.WHITE);
					textView.setTextColor(Color.RED);
				}
				
				
				
				
				
			}else{
				textView.setTextColor(Color.BLACK);
				textView.setBackgroundColor(Color.WHITE);
			}
			
			
		}else{
			textView.setText(null);
		}
		return convertView;
	}

}
