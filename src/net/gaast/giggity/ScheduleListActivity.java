package net.gaast.giggity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.AbstractList;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ScheduleListActivity extends ListActivity {
	AbstractList<Schedule.Item> list;
    
    protected void setList(AbstractList<Schedule.Item> list_) {
    	list = list_;
		setListAdapter(new EventAdapter(list));
    }
    
    protected AbstractList<Schedule.Item> getList() {
    	return list;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Log.d("ctx", ""+getApplicationContext());
		EventDialog evd = new EventDialog(this, list.get(position));
		evd.show();
    }
    
    private class EventAdapter extends BaseAdapter {
    	AbstractList<Schedule.Item> items;
    	
    	public EventAdapter(AbstractList<Schedule.Item> items_) {
    		items = items_;
    	}
    	
		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return (long) position;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int n = 0;
			Schedule.Item i = items.get(position);
			RelativeLayout v = new RelativeLayout(getApplication());
			Format df = new SimpleDateFormat("EE d MMM");
			Format tf = new SimpleDateFormat("HH:mm");
			
			/*
			if (position == 3) {
				TextView tv = new TextView(getApplication());
				tv.setText("\nKopje");
				tv.setTextSize(18);
				tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
				return tv;
			}*/
			
			TextView title, room, time, date;
			RelativeLayout.LayoutParams p;
			
			time = new TextView(getApplication());
			time.setText(tf.format(i.getStartTime()) + "-" + tf.format(i.getEndTime()) + "  ");
			time.setTextSize(16);
			time.setId(++n);
			p = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			v.addView(time, p);
			
			date = new TextView(getApplication());
			date.setText(df.format(i.getStartTime()) + "  ");
			date.setTextSize(12);
			date.setId(++n);
			p = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			p.addRule(RelativeLayout.BELOW, time.getId());
			p.addRule(RelativeLayout.ALIGN_LEFT, time.getId());
			p.addRule(RelativeLayout.ALIGN_RIGHT, time.getId());
			v.addView(date, p);
			
			title = new TextView(getApplication());
			title.setText(i.getTitle());
			title.setTextSize(16);
			title.setId(++n);
			p = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			p.addRule(RelativeLayout.RIGHT_OF, time.getId());
			p.addRule(RelativeLayout.ALIGN_TOP, time.getId());
			v.addView(title, p);
			
			room = new TextView(getApplication());
			room.setText(i.getLine().getTitle());
			room.setTextSize(12);
			room.setId(++n);
			p = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			p.addRule(RelativeLayout.BELOW, title.getId());
			p.addRule(RelativeLayout.ALIGN_LEFT, title.getId());
			p.addRule(RelativeLayout.ALIGN_RIGHT, title.getId());
			v.addView(room, p);
			
			return v;
		}
    }
}