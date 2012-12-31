package blankenship.william.NewYearsResolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

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
	 * PersistantData is a class that will be translated to and from
	 * JSON for saving data when closing the app.<br><br>
	 * 
	 * All legacy versions should be supported when parsing JSON files.<br>
	 * Version Log:<br>
	 * v 0.1: added (time long) and (isStarted, isRunning boolean).<br>
	 * 
	 * @author Crackers
	 */
	public class PersistantData {
		public long jsonTime;
		public boolean jsonStarted;
		public boolean jsonRunning;
		
		public PersistantData(long time, boolean isStarted, boolean isRunning) {
			jsonTime = time;
			jsonStarted = isStarted;
			jsonRunning = isRunning;
		}
	}
	
	Chronometer timer;
	boolean isStarted=false;
	boolean isRunning=false;
	boolean startlFirstCall=true;
	startListener startl = new startListener();
	stopListener stopl = new stopListener();
	long time; //Used for pausing the timer.
	Context c;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        c=this;
        
        //Import any previous data
        loadFile();
        
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
    
    @Override
    public void onPause() {
    	super.onPause();
    	if(isStarted) {
    		if(isRunning)
    			time = timer.getBase();
    	}
    	Gson gson = new Gson();
    	PersistantData data = new PersistantData(time,isStarted,isRunning);
    	String json = gson.toJson(data);
    	fileOutput(json);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	loadFile();
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
    	String state = Environment.getExternalStorageState();
    	//If mounted use external storage
    	if(state.equals(Environment.MEDIA_MOUNTED)) {
    		File root = Environment.getExternalStorageDirectory();
    		File file = new File(root,"/newYears/");
    		try {
    			if(!file.mkdirs()) {
    				Toast.makeText(c, "Failed to create directories", Toast.LENGTH_SHORT);
    			}
    			file = new File(file,"data.json");
    			if(!file.exists()) {
    				file.createNewFile();
    			}
    			FileOutputStream fout = new FileOutputStream(file);
				fout.write(contents.getBytes());
			} catch (FileNotFoundException e) {
				Toast.makeText(c, "File "+file.getAbsolutePath()+" not found!", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
    	}
    }
    
    public void loadFile() {
    	String state = Environment.getExternalStorageState();
    	if(state.equals(Environment.MEDIA_MOUNTED)) {
    		File root = Environment.getExternalStorageDirectory();
    		File file = new File(root,"/newYears/data.json");
    		BufferedReader fin;
			try {
				fin = new BufferedReader(new FileReader(file));
				String json = "";
				while(true) {
					String nextLine;
					nextLine = fin.readLine();
					if(nextLine==null) break;
					json+=nextLine;
				}
				Gson parse = new Gson();
				PersistantData data = (PersistantData)parse.fromJson(json, PersistantData.class);
				startlFirstCall = false;
				time = data.jsonTime;
				isStarted = data.jsonStarted;
				isRunning = data.jsonRunning;
			} catch (FileNotFoundException e) {
				Toast.makeText(c, "Could not find data file", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				Toast.makeText(c, "Problem encountered while reading data file", Toast.LENGTH_SHORT).show();
			}
    	}
    }
    
    public class startListener implements View.OnClickListener {
		public void onClick(View v) {
			if(!isRunning) {
				if(startlFirstCall) {
					timer.setBase(SystemClock.elapsedRealtime());
					timer.start();
					startlFirstCall = false;
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
