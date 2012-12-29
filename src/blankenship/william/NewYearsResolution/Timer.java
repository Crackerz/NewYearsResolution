package blankenship.william.NewYearsResolution;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class Timer extends Activity {
	
	Chronometer timer;
	startListener startl = new startListener();
	stopListener stopl = new stopListener();
	long baseTime;
	long pauseTime;
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
    }
    
    public class startListener implements View.OnClickListener {
    	boolean firstCall=true;
		public void onClick(View v) {
			if(firstCall) {
				timer.start();
				firstCall = false;
			} else {
				timer.setBase(timer.getBase()+(SystemClock.elapsedRealtime()-pauseTime));
				timer.start();
			}
		}
    }
    
    public class stopListener implements View.OnClickListener {
		public void onClick(View v) {
			pauseTime = SystemClock.elapsedRealtime();
			timer.stop();
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
        getMenuInflater().inflate(R.menu.activity_timer, menu);
        return true;
    }
}
