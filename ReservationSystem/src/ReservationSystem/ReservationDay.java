package ReservationSystem;

import java.util.HashMap;

public class ReservationDay {
	String date;
	HashMap<Integer, Boolean> map;
	
	
	public ReservationDay() {
		date="";
		map = new HashMap<Integer, Boolean>();
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				map.put((i * 100) + j, false);
			}
		}
	}
	
}
