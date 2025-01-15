package ReservationSystem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ReservationMain {

	public static void printMenu() {

		System.out.println();
		System.out.println("=============================");
		System.out.println("OO 호텔 예약 시스템");
		System.out.println("OO Hotel Reservation System");
		System.out.println("-----------------------------");
		System.out.println("1. 룸 예약하기");
		System.out.println("2. 고객 검색");
		System.out.println("3. 룸 예약 현황");
		System.out.println("4. 영업 현황 확인");
		System.out.println("5. 프로그램 종료");
		System.out.println("-----------------------------");
		System.out.print("메뉴 입력 : ");

	}

	public static void roomSet(HashMap<Integer, Boolean> map1) {

		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				map1.put((i * 100) + j, false);
			}
		}

	}

	public static int search(Scanner sc, ArrayList<Guest> arr, int hotelmoney, HashMap<Integer, Boolean> map1) {
		System.out.print("고객명을 입력하세요 : ");
		String guest = sc.nextLine();
		int room_n = 0;

		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).name.equals(guest)) {
				System.out.println();
				System.out.println("검색하신 고객님의 정보 입니다.");
				arr.get(i).getInfo();
				room_n = i;
				break;
			} else {
				room_n = -1;
			}
		}

		return room_n;

	}

	public static int change(Scanner sc, ArrayList<Guest> arr, int hotelmoney, HashMap<Integer, Boolean> map1,
			int room_n, DecimalFormat format) {
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("1. 예약 취소");
		System.out.println("2. 예약 연장");
		System.out.println("3. 이전 메뉴");
		System.out.println("-----------------------------");
		System.out.print("메뉴 입력 : ");
		int user2 = sc.nextInt();

		switch (user2) {
		case 1:
			hotelmoney = arr.get(room_n).roomCancle(sc, hotelmoney, map1, format);
			break;
		case 2:
			hotelmoney = arr.get(room_n).roomReservationChange(sc, hotelmoney, format);
			break;
		case 3:
			System.out.println("이전 메뉴로 돌아갑니다.");
			break;
		default:
			System.out.println("잘못 입력하였습니다.");
		}

		return hotelmoney;
	}

	public static void getRoomInfo(HashMap<Integer, Boolean> map1, ArrayList<Guest> arr) {
		System.out.println();
		System.out.println("호텔 룸 예약 현황");
		System.out.println();
		System.out.println("-----------------------------");
		Iterator<Integer> keys = map1.keySet().iterator();
		ArrayList<Integer> key = new ArrayList<Integer>();
		int g = 0;

		while (keys.hasNext()) {
			key.add(keys.next());
		}

		for (int i = 0; i < key.size(); i++) {
			for (int j = 0; j < arr.size(); j++) {
				if (arr.get(j).room == key.get(i)) {
					g = j;
					break;
				}
			}

			if (map1.get(key.get(i))) {
				System.out.println(key.get(i) + "호실 - 예약중");
				System.out.println();
				System.out.println("고객명 : " + arr.get(g).name);
				System.out.println("체크인 : " + arr.get(g).year + "년 " + arr.get(g).month + "월 " + arr.get(g).date + "일");
				System.out.println("체크아웃: " + arr.get(g).year + "년 " + arr.get(g).month + "월 "
						+ (arr.get(g).date + arr.get(g).day) + "일");
				System.out.println("-----------------------------");
			} else {
				System.out.println(key.get(i) + "호실 - 빈방");
				System.out.println("-----------------");
			}
		}
	}

	public static void main(String[] args) {
		ArrayList<Guest> arr = new ArrayList<Guest>();
		HashMap<Integer, Boolean> map1 = new HashMap<Integer, Boolean>();
		DecimalFormat format = new DecimalFormat("#,###");

		Scanner sc = new Scanner(System.in);

		int hotelmoney = 100000;
		roomSet(map1);
		int count = 0;
		int visit = 0;

		while (true) {

			printMenu();

			int user = sc.nextInt();
			sc.nextLine();

			switch (user) {
			case 1:
				arr.add(new Guest());
				hotelmoney = arr.get(count).roomReservation(sc, hotelmoney, map1, format);
				visit++;

				break;
			case 2:
				int room_n = search(sc, arr, hotelmoney, map1);

				if (room_n == -1) {
					System.out.println("해당 고객명으로 예약한 방이 없습니다.");
					continue;
				}

				hotelmoney = change(sc, arr, hotelmoney, map1, room_n, format);

				break;
			case 3:
				getRoomInfo(map1, arr);

				break;
			case 4:
				int count_room = 0;
				for (int i = 101; i <= 303; i++) {
					if (map1.get(i) == true) {
						count_room++;
					}
					if (i % 100 == 3) {
						i += 98;
					}
				}

				String hotelmoney_s = format.format(hotelmoney);
				System.out.println();
				System.out.println("현재 이용 고객 수 : " + count_room + "명");
				System.out.println("누적 방문 고객 수 : " + visit + "명");
				System.out.println("누적 매출액 : " + format.format(hotelmoney - 100000)+"원");
				System.out.println("현재 금고 잔액 : " + hotelmoney_s + "원");

				break;
			case 5:
				System.out.println();
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break;
			}

		}

	}

}
