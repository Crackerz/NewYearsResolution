import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import blankenship.william.NewYearsResolution.AppCalendar;

import junit.framework.TestCase;


public class CalendarTest extends TestCase {

	@Test
	public void testGetIndex() {
		for(int y = 2012; y < 2012 + 2000; y++) {
			AppCalendar test = new AppCalendar(y);
			int[] thisYear = getCalArray(test);
			int offset=1;
			for(int i = 0; i < 12; i++) {
				for(int j = 1; j <= getDaysInMonth(test,i,y); j++) {
					thisYear[getIndex(test,j,i,y)] = offset++;
				}
			}
			offset = 1;
			for(int i = 0; i < thisYear.length; i++) {
				assertTrue("Index:"+i+" Offset:"+offset+" Value: "+thisYear[i],offset==thisYear[i]);
				offset++;
			}
		}
	}
	
	public int[] getCalArray(AppCalendar cal) {
		Field array;
		try {
			array = cal.getClass().getDeclaredField("thisYear");
			array.setAccessible(true);
			return (int[]) array.get(cal);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public int getIndex(AppCalendar cal, int d, int m , int y) {
		try {
			Method method = cal.getClass().getDeclaredMethod("getIndex", int.class,int.class,int.class);
			method.setAccessible(true);
			return ((Integer)method.invoke(cal,d,m,y)).intValue();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public int getDaysInMonth(AppCalendar cal, int m, int y) {
		try {
			Method method = cal.getClass().getDeclaredMethod("getDaysInMonth", int.class,int.class);
			method.setAccessible(true);
			return ((Integer)method.invoke(cal, m,y)).intValue();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
}
