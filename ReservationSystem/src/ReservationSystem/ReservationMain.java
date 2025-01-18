package ReservationSystem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	public static int search(Scanner sc, ArrayList<Guest> arr, int hotelmoney) {
		System.out.println();
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

	public static int change(Scanner sc, ArrayList<Guest> arr, int hotelmoney, int room_n, DecimalFormat format,
			ArrayList<ReservationDay> arr2) {
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("1. 예약 취소");
		System.out.println("2. 예약 연장");
		System.out.println("3. 이전 메뉴");
		System.out.println("-----------------------------");
		System.out.print("메뉴 입력 : ");
		int user2 = sc.nextInt();

		try {
			switch (user2) {
			case 1:
				hotelmoney = arr.get(room_n).roomCancle(sc, hotelmoney, format, arr2);

				break;
			case 2:
				hotelmoney = arr.get(room_n).roomReservationChange(sc, hotelmoney, format, arr2);
				break;
			case 3:
				System.out.println("이전 메뉴로 돌아갑니다.");
				break;
			default:
				System.out.println("잘못 입력하였습니다.");
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println();
			System.out.println("예약한 고객이 없습니다.");
		}

		return hotelmoney;
	}

	public static void getRoomInfo(ArrayList<Guest> arr, ArrayList<ReservationDay> arr2) {
		Iterator<Integer> keys = arr2.get(0).map.keySet().iterator();

		ArrayList<Integer> key = new ArrayList<Integer>();
		Calendar now = Calendar.getInstance();
		boolean check = false;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

		String today = sdf1.format(now.getTime());

		System.out.println();
		System.out.println("호텔 룸 예약 현황");
		System.out.println();
		System.out.println("-----------------------------");

		while (keys.hasNext()) {
			key.add(keys.next());
		}

		for (int i = 0; i < key.size(); i++) {
			System.out.println();
			System.out.println("***" + key.get(i) + "호실***");
			System.out.println("--------------------------------");

			for (int j = 0; j < arr.size(); j++) {
				check = false;
				if (arr.get(j).reser.equals(today) && arr.get(j).room == key.get(i)) {
					System.out.println("- 현재 이용 중");
					System.out.println();
					System.out.println("고객 성함 : " + arr.get(j).name);
					System.out.println("숙박 인원 : " + arr.get(j).people + "명");
					System.out.println("체크인 : " + arr.get(j).format_s);
					System.out.println("체크아웃 : " + arr.get(j).format_e);
					System.out.println();
					check = true;
					break;
				}
			}

			if (!check) {
				System.out.println("- 현재 빈방");
				System.out.println();
			}

			for (int j = 0; j < arr.size(); j++) {
				if (!(arr.get(j).reser.equals(today)) && arr.get(j).room == key.get(i)) {
					System.out.println("- 예약자");
					System.out.println();
					System.out.println("고객 성함 : " + arr.get(j).name);
					System.out.println("숙박 인원 : " + arr.get(j).people + "명");
					System.out.println("체크인 : " + arr.get(j).format_s);
					System.out.println("체크아웃 : " + arr.get(j).format_e);
					System.out.println();
				}
			}

		}

	}

	public static void getHotelInfo(ArrayList<Guest> arr, ArrayList<ReservationDay> arr2, DecimalFormat format,
			int hotelmoney, int visit) {
		int count_room = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH - 2);
		int date = now.get(Calendar.DATE);
		String today = year + "" + (month >= 10 ? month : "0" + month) + "" + date;

		for (int i = 0; i < arr2.size(); i++) {
			if (arr2.get(i).date.equals(today)) {
				for (int j = 101; j <= 303; j++) {
					if (arr2.get(i).map.get(j)) {
						count_room++;
					}
					if (j % 100 == 3) {
						j += 97;
					}
				}
				break;
			}
		}

		String hotelmoney_s = format.format(hotelmoney);
		System.out.println();
		System.out.println("현재 이용 고객 수 : " + count_room + "명");
		System.out.println("누적 방문 고객 수 : " + visit + "명");
		System.out.println("누적 매출액 : " + format.format(hotelmoney - 100000) + "원");
		System.out.println("현재 금고 잔액 : " + hotelmoney_s + "원");

	}

	public static void main(String[] args) {
		ArrayList<Guest> arr = new ArrayList<Guest>();
		ArrayList<ReservationDay> arr2 = new ArrayList<ReservationDay>();
		arr2.add(new ReservationDay());
		DecimalFormat format = new DecimalFormat("#,###");

		Scanner sc = new Scanner(System.in);

		int hotelmoney = 100000;
		int visit = 0;

		while (true) {

			printMenu();

			int user = sc.nextInt();
			sc.nextLine();

			switch (user) {
			case 1:

				System.out.println();
				System.out.println("-----------------------------");
				System.out.println("1. 오늘 예약");
				System.out.println("2. 예약일 지정");
				System.out.println("3. 이전 메뉴");
				System.out.println("-----------------------------");
				System.out.print("메뉴 입력 : ");
				int user_a = sc.nextInt();
				sc.nextLine();

				switch (user_a) {
				case 1:
					arr.add(new Guest());
					System.out.println();
					System.out.println("*오늘 예약*");
					hotelmoney = arr.get(arr.size() - 1).roomReservation_now(sc, hotelmoney, format, arr2);
					visit++;
					break;
				case 2:
					arr.add(new Guest());
					System.out.println();
					System.out.println("*예약일 지정*");
					hotelmoney = arr.get(arr.size() - 1).roomReservation_later(sc, hotelmoney, format, arr2);
					visit++;
					break;
				case 3:
					System.out.println();
					System.out.println("이전 메뉴로 돌아갑니다.");
					break;
				default:
					System.out.println();
					System.out.println("잘못 입력하였습니다.");
				}

				break;
			case 2:
				int room_n = search(sc, arr, hotelmoney);

				if (room_n == -1) {
					System.out.println("해당 고객명으로 예약한 방이 없습니다.");
					continue;
				}

				hotelmoney = change(sc, arr, hotelmoney, room_n, format, arr2);

				break;
			case 3:
				getRoomInfo(arr, arr2);

				break;
			case 4:
				getHotelInfo(arr, arr2, format, hotelmoney, visit);
				break;
			case 5:
				System.out.println();
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break;
			default:
				System.out.println();
				System.out.println("잘못 입력하였습니다.");
			}

		}

	}

}
