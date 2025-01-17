package ReservationSystem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
	String money_s;
	String reser;
	String reser_e;
	String format_s;
	String format_e;
	boolean today;
	Calendar now;
	Calendar in;
	Calendar out;
	Calendar ing;
	SimpleDateFormat sdf1;
	SimpleDateFormat sdf2;

	public Guest() {
		room = 0;
		name = "무명";
		gender = 0;
		age = 0;
		people = 0;
		day = 0;
		money = 0;
		year = 0;
		month = 0;
		date = 0;
		money_s = "";
		today = false;
		sdf1 = new SimpleDateFormat("yyyyMMdd");
		sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일");
	}

	public void guestSet(Scanner sc, ArrayList<ReservationDay> arr2) {
		System.out.println();
		System.out.print("고객 성함 : ");
		name = sc.nextLine();

		System.out.print("고객 성별 (남 1 / 여 2) : ");
		gender = sc.nextInt();
		sc.nextLine();

		System.out.print("고객 나이 : ");
		age = sc.nextInt();
		sc.nextLine();

		System.out.print("숙박 인원 : ");
		people = sc.nextInt();
		sc.nextLine();

		System.out.print("숙박 일수 : ");
		day = sc.nextInt();
		sc.nextLine();


	}

	public void roomSet(Scanner sc, int hotelmoney, HashMap<Integer, Boolean> map1, DecimalFormat format,
			ArrayList<ReservationDay> arr2, int count) {

		System.out.println();
		System.out.println("원하시는 호실이 있나요?");
		System.out.print("Y/N : ");

		String ny = sc.nextLine();
		if (ny.charAt(0) == 'Y' || ny.charAt(0) == 'y') {
			System.out.print("원하시는 호실을 입력해 주세요 : ");
			room = sc.nextInt();
			sc.nextLine();

			for (int i = 0; i < arr2.size(); i++) {
				if (arr2.get(i).date.equals(reser)) {
					if (arr2.get(i).map.get(room)) {
						System.out.println();
						System.out.println(room + "호는 예약이 있습니다.");
						room = 0;
						roomSet(sc, hotelmoney, map1, format, arr2, count);
						return;
					}
				}
			}
			for (int j = 0; j < arr2.size(); j++) {

				if (arr2.get(j).date.equals(reser)) {
					if (!(arr2.get(j).map.get(room))) {

						for (int z = 0; z < day; z++) {
							int cnt = 0;
							int a=0;

							ing = Calendar.getInstance();

							ing.set(Calendar.YEAR, year);
							ing.set(Calendar.MONTH, month - 1);
							ing.set(Calendar.DAY_OF_MONTH, date+z);
							String reser_i = sdf1.format(ing.getTime());

							if (arr2.size() != 0) {
								for (int k = 0; k < arr2.size(); k++) {
									if (arr2.get(k).date.equals(reser_i)) {
										cnt++;
									}
									if(arr2.get(k).date.equals(reser_i)) {
										a=k;
									}
								}
								if (cnt == 0) {
									arr2.add(new ReservationDay());
									arr2.get(arr2.size() - 1).date = reser_i;
									arr2.get(arr2.size() - 1).map.put(room, true);
								}else {
									arr2.get(a).date=reser_i;
									arr2.get(a).map.put(room, true);
								}
							} else {
								arr2.add(new ReservationDay());
								arr2.get(arr2.size() - 1).date = reser_i;
								arr2.get(arr2.size() - 1).map.put(room, true);
							}
						}
						
						break;
					}

				}
			}

		} else if (ny.charAt(0) == 'N' || ny.charAt(0) == 'n') {

			System.out.println();
			System.out.println("룸을 랜덤 배정합니다.");
			System.out.println(arr2.size());
			for(int i=0;i<arr2.size();i++) {
				System.out.println(arr2.get(i).map.get(101));
				System.out.println(arr2.get(i).date);
				System.out.println(arr2.get(i).room);
			}

			for (int i = 101; i <= 303; i++) {
				System.out.println(i);
				

				for (int j = 0; j < arr2.size(); j++) {
					if (arr2.get(j).date.equals(reser)) {
						if (!(arr2.get(j).map.get(i))) {
							room = i;
							
							for (int z = 0; z < day; z++) {
								int cnt = 0;
								int a=0;

								ing = Calendar.getInstance();

								ing.set(Calendar.YEAR, year);
								ing.set(Calendar.MONTH, month - 1);
								ing.set(Calendar.DAY_OF_MONTH, date+z);
								String reser_i = sdf1.format(ing.getTime());

								if (arr2.size() != 0) {
									for (int k = 0; k < arr2.size(); k++) {
										if (arr2.get(k).date.equals(reser_i)) {
											cnt++;
										}
										if(arr2.get(k).date.equals(reser_i)) {
											a=k;
										}
									}
									if (cnt == 0) {
										arr2.add(new ReservationDay());
										arr2.get(arr2.size() - 1).date = reser_i;
										arr2.get(arr2.size() - 1).map.put(room, true);
									}else {
										arr2.get(a).date=reser_i;
										arr2.get(a).map.put(room, true);
									}
								} else {
									arr2.add(new ReservationDay());
									arr2.get(arr2.size() - 1).date = reser_i;
									arr2.get(arr2.size() - 1).map.put(room, true);
								}
							}
							

							break;
						}
					}
				}

				if (room == i) {
					break;
				} else if (i == 303 && room == 0) {
					System.out.println("해당 날짜의 모든 방이 예약되었습니다.");
					System.out.println("다른 날짜로 예약해주세요.");
					roomReservation_later(sc, hotelmoney, map1, format, arr2, count);
					return;
				}

				if (i % 100 == 3) {
					i += 97;
				}

			}

		} else {
			System.out.println("잘못 입력하였습니다.");
			roomSet(sc, hotelmoney, map1, format, arr2, count);
			return;
		}
	}

	public void datecheck(ArrayList<ReservationDay> arr2) {
		int cnt = 0;
		if (arr2.size() != 0) {
			for (int i = 0; i < arr2.size(); i++) {
				if (arr2.get(i).date.equals(reser)) {
					cnt++;
				}
			}
			if (cnt == 0) {
				arr2.add(new ReservationDay());
				arr2.get(arr2.size() - 1).date = reser;
			}
		} else {
			arr2.add(new ReservationDay());
			arr2.get(arr2.size() - 1).date = reser;
		}
	}

	public int roomReservation_now(Scanner sc, int hotelmoney, HashMap<Integer, Boolean> map1, DecimalFormat format,
			ArrayList<ReservationDay> arr2, int count) {

		in = Calendar.getInstance();

		year = in.get(Calendar.YEAR);
		month = in.get(Calendar.MONTH) + 1;
		date = in.get(Calendar.DAY_OF_MONTH);

		reser = sdf1.format(in.getTime());
		format_s = sdf2.format(in.getTime());

		datecheck(arr2);

		guestSet(sc, arr2);
		roomSet(sc, hotelmoney, map1, format, arr2, count);

		System.out.println();
		System.out.println("입력하신 정보 입니다.");
		getInfo();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		money = 55000 * day;
		money_s = format.format(money);

		System.out.println();
		System.out.println(day + "박 결제 비용은 " + money_s + "원 입니다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("결제되었습니다. 감사합니다.");
		hotelmoney += money;
		today = true;

		return hotelmoney;

	}

	public int roomReservation_later(Scanner sc, int hotelmoney, HashMap<Integer, Boolean> map1, DecimalFormat format,
			ArrayList<ReservationDay> arr2, int count) {
		now = Calendar.getInstance();

		String now_s = sdf1.format(now.getTime());

		System.out.println();
		System.out.print("예약 날짜를 입력해주세요.(ex.20250101) : ");
		reser = sc.nextLine();

		if (reser.length() != 8) {
			System.out.println("잘못 입력하였습니다. 다시 입력해주세요.");
			roomReservation_later(sc, hotelmoney, map1, format, arr2, count);
			return hotelmoney;
		}

		datecheck(arr2);

		year = Integer.parseInt(reser.substring(0, 4));
		month = Integer.parseInt(reser.substring(4, 6));
		date = Integer.parseInt(reser.substring(6));

		if (year < 0 || month > 12 || date > 31) {
			System.out.println("잘못 입력하였습니다. 다시 입력해주세요.");
			roomReservation_later(sc, hotelmoney, map1, format, arr2, count);
			return hotelmoney;
		} else if (Integer.parseInt(reser) < Integer.parseInt(now_s)) {
			System.out.println("예약일은 현재 날짜 이후로 해주세요.");
			roomReservation_later(sc, hotelmoney, map1, format, arr2, count);
			return hotelmoney;
		}

		in = Calendar.getInstance();

		in.set(Calendar.YEAR, year);
		in.set(Calendar.MONTH, month - 1);
		in.set(Calendar.DAY_OF_MONTH, date);
		format_s = sdf2.format(in.getTime());

		guestSet(sc, arr2);
		roomSet(sc, hotelmoney, map1, format, arr2, count);

		System.out.println();
		System.out.println("입력하신 정보 입니다.");
		getInfo();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		money = 55000 * day;
		money_s = format.format(money);

		System.out.println();
		System.out.println(day + "박 결제 비용은 " + money_s + "원 입니다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("결제되었습니다. 감사합니다.");
		hotelmoney += money;

		if (reser.equals(now_s)) {
			today = true;
		}

		return hotelmoney;

	}

	public void getInfo() {
		out = Calendar.getInstance();
		out.set(Calendar.YEAR, year);
		out.set(Calendar.MONTH, month - 1);
		out.set(Calendar.DAY_OF_MONTH, date);
		out.add(Calendar.DAY_OF_MONTH, day);
		reser_e = sdf1.format(out.getTime());
		format_e = sdf2.format(out.getTime());

		System.out.println();
		System.out.println("고객 성함 : " + name);
		System.out.println("고객 성별 : " + (gender == 1 ? "남자" : "여자"));
		System.out.println("고객 나이 : " + age + "세");
		System.out.println("예약 룸 넘버 : " + room + "호");
		System.out.println("숙박 인원 : " + people + "명");
		System.out.println("숙박 일수 : " + day + "박 " + (day + 1) + "일");
		System.out.println("체크인 : " + format_s);
		System.out.println("체크아웃 : " + format_e);

	}

	public int roomCancle(Scanner sc, int hotelmoney, HashMap<Integer, Boolean> map1, DecimalFormat format,
			ArrayList<ReservationDay> arr2) {

		try {
			money_s = format.format(money * 0.9);
			System.out.println();
			System.out.println(name + " 고객님의 예약취소를 도와드리겠습니다.");
			Thread.sleep(1500);
			System.out.println("취소수수료가 10% 부과되어 환불됩니다.");
			Thread.sleep(1500);
			System.out.println("환불 금액은 " + money_s + "원 입니다.");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		System.out.println();
		System.out.println("취소하시겠습니까?");
		System.out.print("Y/N : ");
		String ny = sc.next();
		if (ny.charAt(0) == 'Y' || ny.charAt(0) == 'y') {
			hotelmoney -= (int) (money * 0.9);
			name = "";

			for (int i = 0; i < arr2.size(); i++) {
				if (arr2.get(i).date.equals(reser)) {
					arr2.get(i).map.put(room, false);
				}
				break;
			}

			room = 0;
			System.out.println();
			System.out.println(room + "호실의 예약 취소가 완료되었습니다.");
			System.out.println("감사합니다.");

		} else if (ny.charAt(0) == 'N' || ny.charAt(0) == 'n') {
			System.out.println();
			System.out.println("고객님 요청으로 예약이 취소되지 않았습니다.");
			System.out.println("감사합니다.");
		}

		return hotelmoney;
	}

	public int roomReservationChange(Scanner sc, int hotelmoney, DecimalFormat format) {
		System.out.println();
		System.out.println("고객님의 예약 정보 입니다.");
		System.out.println();

		System.out.println("숙박 일수 : " + day + "박 " + (day + 1) + "일");
		System.out.println("체크인 : " + format_s);
		System.out.println("체크아웃 : " + format_e);

		System.out.println();
		System.out.print("몇 박을 연장하시겠습니까? : ");
		int day_c = sc.nextInt();
		int money_c = 55000 * day_c;
		day += day_c;
		money_s = format.format(money_c);

		System.out.println();
		System.out.println(day_c + "박 추가 결제 비용은 " + money_s + "원 입니다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		System.out.println();
		System.out.println("결제되었습니다. 감사합니다.");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		out.set(Calendar.YEAR, year);
		out.set(Calendar.MONTH, month - 1);
		out.set(Calendar.DAY_OF_MONTH, date);
		out.add(Calendar.DAY_OF_MONTH, day);
		reser_e = sdf1.format(out.getTime());
		format_e = sdf2.format(out.getTime());

		System.out.println();
		System.out.println("변경된 예약정보 입니다.");
		System.out.println();
		System.out.println("숙박 일수 : " + day + "박 " + (day + 1) + "일");
		System.out.println("체크인 : " + format_s);
		System.out.println("체크아웃 : " + format_e);

		hotelmoney += money_c;

		return hotelmoney;

	}

}
