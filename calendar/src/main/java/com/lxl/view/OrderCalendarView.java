package com.lxl.view;

import java.util.Date;

import com.example.calendar.R;
import com.lxl.view.CalendarAdapter.onDateSelectedListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderCalendarView extends RelativeLayout implements OnTouchListener {

	private OnDateChangedListener dateChangedListener;
	private onMonthChangedListener monthChangedListener;
	private CalendarAdapter adapter;
	
	private TextView curTime;
	private GridView gridView;

	
	

	public void setMonthChangedListener(onMonthChangedListener monthChangedListener) {
		this.monthChangedListener = monthChangedListener;
	}

	public void setDateChangedListener(OnDateChangedListener dateChangedListener) {
		this.dateChangedListener = dateChangedListener;
	}

	public OrderCalendarView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public OrderCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public OrderCalendarView(Context context) {
		super(context);
		init();
	}

	private void init() {
		gestureDetector = new GestureDetector(getContext(), detector);
		LayoutInflater.from(getContext()).inflate(R.layout.calendar, this);
		curTime=(TextView) findViewById(R.id.curtime);
//		curTime.setOnTouchListener(this);
		gridView=(GridView) findViewById(R.id.order_calendar);
		gridView.setOnTouchListener(this);
//		this.setOnTouchListener(this);
		gridView.setAdapter(adapter = new CalendarAdapter(getContext(),
				new onDateSelectedListener() {

					@Override
					public void onSelected(Date date) {
						if (dateChangedListener != null) {
							dateChangedListener.onDateChanged(date);
						}
					}
				}));

		curTime.setText(adapter.getCurYearMonth());
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	public void setYear(int year) {
		adapter.setYear(year);
	}

	public void setMonth(int month) {
		adapter.setMonth(month);
	}

	public int getYear() {
		return adapter.getYear();
	}

	public int getMonth() {
		return adapter.getMonth();
	}

	public interface OnDateChangedListener {
		public void onDateChanged(Date date);
	}

	public interface onMonthChangedListener{
		public void onMonthChanged(int year,int month);
	}
	private GestureDetector gestureDetector;

	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouch(View v, MotionEvent event) {

		return gestureDetector.onTouchEvent(event);
	}

	GestureDetector.OnGestureListener detector = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1 != null && e2 != null) {
				if(e1.getX()-e2.getX()>100 )  
			    {  
			          //animShowNextPage(); 
					   //animShowPrePage(); 
			    	// right
					int month = adapter.getMonth();
					if (month < 12) {
						adapter.setMonth(month + 1);
					} else {
						adapter.setMonth(1);
						adapter.setYear(adapter.getYear() + 1);
					}
					
					curTime.setText(adapter.getCurYearMonth());
					if(monthChangedListener!=null){
						monthChangedListener.onMonthChanged(adapter.getYear(),adapter.getMonth());
					}
					return true;
			    }  
				if(e1.getX()-e2.getX()<-100 )  
			    {  
			       

					int month = adapter.getMonth();
					if (month > 1) {
						adapter.setMonth(month - 1);
					} else {
						adapter.setMonth(12);
						adapter.setYear(adapter.getYear() - 1);
					}
					curTime.setText(adapter.getCurYearMonth());
					if(monthChangedListener!=null){
						monthChangedListener.onMonthChanged(adapter.getYear(),adapter.getMonth());
					}
					return true;
			    } 
				
				
				
			}
			return false;
		}
	};

}
