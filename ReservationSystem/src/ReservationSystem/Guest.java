package ReservationSystem;

import java.util.*;

public class Guest {

	int room;
	String name;
	int gender;
	int age;
	int people;
	int day;
	int money;
	int year;
	int month;
	int date;

	public void guestSet(Scanner sc) {
		System.out.print("고객 성함 : ");
		name = sc.nextLine();

		System.out.print("고객 성별 (남 1 / 여 2) : ");
		gender = sc.nextInt();

		System.out.print("고객 나이 : ");
		age = sc.nextInt();

		System.out.print("숙박 인원 : ");
		people = sc.nextInt();

		System.out.print("숙박 일수 : ");
		day = sc.nextInt();

	}

	public void roomSet(Scanner sc, HashMap<Integer, Boolean> map1) {

		System.out.println();
		System.out.println("원하시는 호실이 있나요?");
		System.out.print("Y/N : ");

		String ny = sc.next();
		if (ny.charAt(0) == 'Y' || ny.charAt(0) == 'y') {
			System.out.print("원하시는 호실을 입력해 주세요 : ");
			room = sc.nextInt();
			sc.nextLine();

			if (map1.get(room) == true) {
				System.out.println("죄송합니다 현재 그 방은 빈방이 아닙니다.");
				roomSet(sc, map1);
			} else {
				map1.put(room, true);
			}

		} else if (ny.charAt(0) == 'N' || ny.charAt(0) == 'n') {
			System.out.print("룸을 랜덤 배정합니다.");
			for (int i = 101; i <= 303; i++) {
				if (map1.get(i) == false) {
					map1.put(i, true);
					room = i;
					break;
				}
				if (i % 100 == 3) {
					i += 98;
				}
			}

		} else {
			System.out.println("엥");
		}
	}

	public int roomReservation(Scanner sc, int hotelmoney, HashMap<Integer, Boolean> map1) {
		Calendar now = Calendar.getInstance();
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH + 1);
		date = now.get(Calendar.DATE);

		guestSet(sc);
		roomSet(sc, map1);

		System.out.println();
		System.out.println("입력하신 정보 입니다.");
		System.out.println();
		System.out.println("고객 성함 : " + name);
		System.out.println("고객 성별 : " + (gender == 1 ? "남자" : "여자"));
		System.out.println("고객 나이 : " + age + "세");
		System.out.println("예약 룸 넘버 : " + room + "호");
		System.out.println("숙박 인원 : " + people + "명");
		System.out.println("숙박 일수 : " + day + "박 " + day + 1 + "일");
		System.out.println("체크인 : " + year + "년 " + month + "월 " + date + "일");
		System.out.println("체크아웃 : " + year + "년 " + month + "월 " + (date + day) + "일");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		money = 55000 * day;
		System.out.println();
		System.out.println(day + "박 결제 비용은 " + money + "원 입니다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("결제되었습니다. 감사합니다.");
		hotelmoney += money;

		return hotelmoney;

	}

}
