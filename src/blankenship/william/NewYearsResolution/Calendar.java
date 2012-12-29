package blankenship.william.NewYearsResolution;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.CalendarView;
import android.widget.Toast;

public class Calendar extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	final Context c = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView cal = (CalendarView) this.findViewById(R.id.calendarView);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				Toast.makeText(c,month+"/"+dayOfMonth+"/"+year,Toast.LENGTH_SHORT).show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calendar, menu);
        return true;
    }
}
