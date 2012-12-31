package blankenship.william.NewYearsResolution;

/**
 * The calendar class is used to store accrued time in the calendar.
 * Keeps up to two years worth of data.
 * @author Crackers
 *
 */
public class Calendar {

	private int lastYear[];
	private int thisYear[];
	
	public void main() {
		for(int i = 0; i < 12; i++) {
			getDaysInMonth(i,2012);
		}
	}
	
	private void newYear() {
		
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
		return (year%4==0&&year%100!=0||year%400==0);
	}

}
