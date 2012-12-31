package blankenship.william.NewYearsResolution;

/**
 * The calendar class is used to store accrued time in the calendar.
 * Keeps up to one year worth of data.
 * @author Crackers
 *
 */
public class AppCalendar {


	private int year = 2012;
	private long thisYear[];
	
	public AppCalendar(int year) {
		thisYear = new long[getDaysInYear(year)];
	}
	
	public boolean updateValue(long val, int day, int month, int year) {
		if(year!=this.year || thisYear[getIndex(day,month,year)]>val) {
			return false;
		}
		thisYear[getIndex(day,month,year)] = val;
		return true;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
		thisYear = new long[getDaysInYear(year)];
	}
	
	public long getValue(int day, int month, int year) {
		if(year!=this.year) {
			return -1;
		}
		return thisYear[getIndex(day,month,year)];
	}
	
	/**
	 * Gets the array index of a day in a month in a year.
	 */
	private int getIndex(int day, int month, int year) {
		return getMonthOffset(month,year)+day-1;
	}
	
	private int getMonthOffset(int month, int year) {
		int result = 0;
		for(int i = 0; i < month; i++) {
			result+=getDaysInMonth(i,year);
		}
		return result;
	}
	
	private int getDaysInYear(int year) {
		return (isLeapYear(year)) ? 366 : 365;
	}
	
	/**
	 * 
	 * @param month
	 * January==0
	 * @param year
	 * @return
	 * the number of days in the month of the year provided
	 */
	private int getDaysInMonth(int month, int year) {
		if(month==1) {
			return (this.isLeapYear(year)) ? 29 : 28;
		} else {
			return 31 - (month%7%2);
		}
	}
	
	private boolean isLeapYear(int year) {
		return ((year%4==0&&year%100!=0)||(year%400==0));
	}

}
