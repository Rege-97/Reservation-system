package ReservationSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ReservationMain {

	public static void printMenu() {
		System.out.println("=============================");
		System.out.println("OO 호텔 예약 시스템");
		System.out.println("OO Hotel Reservation System");
		System.out.println("-----------------------------");
		System.out.println("1. 룸 예약하기");
		System.out.println("2. 고객 검색");
		System.out.println("3. 룸 예약 현황");
		System.out.println("4. 금고 확인");
		System.out.println("5. 프로그램 종료");

	}

	public static void roomSet(HashMap<Integer, Boolean> map1, HashMap<Integer, Guest> map2) {

		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				map1.put((i * 100) + j, false);
			}
		}
		
	}

	public static void main(String[] args) {
		ArrayList<Guest>arr= new ArrayList<Guest>();
		HashMap<Integer, Boolean> map1 = new HashMap<Integer, Boolean>();
		HashMap<Integer, Guest> map2 = new HashMap<Integer, Guest>();

		Scanner sc = new Scanner(System.in);
		


		int hotelmoney = 100000;
		roomSet(map1, map2);
		int count = 0;

		while (true) {

			printMenu();
			int user = sc.nextInt();
			sc.nextLine();

			switch (user) {
			case 1:
				arr.add(new Guest());
				arr.get(count).roomReservation(sc, hotelmoney, map1);
				
				
				
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			}

		}

	}

}
