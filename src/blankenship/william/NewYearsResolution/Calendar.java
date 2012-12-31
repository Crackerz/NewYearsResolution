package blankenship.william.NewYearsResolution;

/**
 * The calendar class is used to store accrued time in the calendar.
 * Keeps up to one year worth of data.
 * @author Crackers
 *
 */
public class Calendar {


	int year = 2012;
	
	private int thisYear[];
	
	public static void main(String args[]) {
		for(int y = 2012; y < 2012 + 2000; y++) {
			Calendar test = new Calendar(y);
			int offset=1;
			for(int i = 0; i < 12; i++) {
				for(int j = 1; j <= test.getDaysInMonth(i,y); j++) {
					test.thisYear[test.getIndex(j,i,y)] = offset++;
				}
			}
			offset = 1;
			for(int i = 0; i < test.thisYear.length; i++) {
				if(offset!=test.thisYear[i]) {
					System.out.println("Error in year " + y);
					System.exit(0);
				}
				offset++;
				System.out.print(test.thisYear[i]+",");
			}
			System.out.println();
		}
	}
	
	public Calendar() {
		thisYear = new int[getDaysInYear(year)];
	}
	
	public Calendar(int year) {
		thisYear = new int[getDaysInYear(year)];
	}
	
	/**
	 * Gets the array index of a day in a month in a year.
	 */
	private int getIndex(int day, int month, int year) {
		return (getMonthOffset(month,year)+day)-1;
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
