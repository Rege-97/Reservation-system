package ReservationSystem;

import java.util.HashMap;

public class ReservationDay {
	String date;
	HashMap<Integer, Boolean> map;
	
	/* ReservationDay 생성자 메서드
	 * 
	 * 생성과 동시에 해쉬맵인 map안에 101~303호의 호실 데이터를
	 * false(빈방)으로 세팅한다.
	 * 
	 * */
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
