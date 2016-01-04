package com.example.calendar;

import android.app.Activity;
import android.os.Bundle;

import com.lxl.view.OrderCalendarView;

public class MainActivity extends Activity {

	private OrderCalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView=(OrderCalendarView) findViewById(R.id.gridView1);
    }
    
}
