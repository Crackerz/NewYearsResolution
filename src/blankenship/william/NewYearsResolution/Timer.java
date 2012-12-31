package blankenship.william.NewYearsResolution;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class Timer extends Activity {
	
	/**
	 * persistantData is a class that will be translated to and from
	 * JSON for saving data when closing the app.<br><br>
	 * 
	 * All legacy versions should be supported when parsing JSON files.<br>
	 * Version Log:<br>
	 * v 0.1: added (time long) and (isStarted, isRunning boolean).<br>
	 * 
	 * @author Crackers
	 */
	public class persistantData {
		public long time;
		public boolean isStarted;
		public boolean isRunning;
	}
	
	Chronometer timer;
	boolean isStarted=false;
	boolean isRunning=false;
	startListener startl = new startListener();
	stopListener stopl = new stopListener();
	long time; //Used for pausing the timer.
	Context c;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        c=this;
        
        //Initialize Variables
        final CalendarView cal = (CalendarView) this.findViewById(R.id.calendarView);
        final TextView calText = (TextView) this.findViewById(R.id.calText);
        final Button start = (Button) findViewById(R.id.startTimer);
        final Button pause = (Button) findViewById(R.id.pauseTimer);

        //Initialize Values
        timer = (Chronometer) findViewById(R.id.timer);
		String value = timer.getText().toString();
        timer.setText(padTime(value));

        //Set listeners
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				calText.setText((month+1)+"/"+dayOfMonth+"/"+year);
			}
		});
        
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {			
			public void onChronometerTick(Chronometer chronometer) {
				String value = timer.getText().toString();
		        timer.setText(padTime(value));
			}
		});
        
        start.setOnClickListener(startl);
        
        pause.setOnClickListener(stopl);
        
        fileOutput("test");
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	if(isStarted) {
    		if(isRunning)
    			time = timer.getBase();
    	}
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	if(isStarted) {
    		if(isRunning) {
    			timer.setBase(time);
    			timer.start();
    		} else {
    			timer.setBase(SystemClock.elapsedRealtime()-time);
    			timer.setText(padTime(timer.getText().toString()));
    		}
    	}
    }
    
    public void fileOutput(String contents) {
    	//TODO Must handle 2 events. Media mounted AND media unaccessable
    	String state = Environment.getExternalStorageState();
    	Toast.makeText(c, state, Toast.LENGTH_SHORT).show();
    }
    
    public class startListener implements View.OnClickListener {
    	boolean firstCall=true;
		public void onClick(View v) {
			if(!isRunning) {
				if(firstCall) {
					timer.setBase(SystemClock.elapsedRealtime());
					timer.start();
					firstCall = false;
					isStarted=true;
				} else {
					timer.setBase(SystemClock.elapsedRealtime()-time);
					timer.start();
				}
				isRunning = true;
			}
		}
    }
    
    public class stopListener implements View.OnClickListener {
		public void onClick(View v) {
			if(isRunning) {
				time = SystemClock.elapsedRealtime() - timer.getBase();
				timer.stop();
				isRunning = false;
			}
		}
    }

    public String padTime(String time) {
    	switch(time.length()) {
    	case 2:
    		return "00:00:"+time;
    	case 4:
    		return "00:0"+time;
    	case 5:
    		return "00:"+time;
    	case 7:
    		return "0"+time;
    	case 8:
    		return time;
    	default:
    		return "NULL";
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_timer, menu);
        return true;
    }
}
