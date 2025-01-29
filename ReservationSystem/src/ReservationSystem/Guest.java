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
	int search;
	String money_s;
	String reser;
	String reser_e;
	String format_s;
	String format_e;
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
		search = 0;
		money_s = "";
		sdf1 = new SimpleDateFormat("yyyyMMdd");
		sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일");
	}

	/*
	 * 고객정보 입력 메서드
	 * 
	 * 고객 정보를 차례로 입력받되 성별 입력에서 1(남), 2(여)가 아닌 
	 * 다른 숫자가 입력되면 잘못 입력 문자 표시 후
	 * 본인 메서드를 다시 실행하여 재입력
	 * 이때 다시 나머지 입력을 반복하지 않기 위해 return
	 */
	public void guestSet(Scanner sc) {
		System.out.println();
		System.out.print("           * 고객 성함 : ");
		name = sc.nextLine();

		System.out.print("           * 고객 성별 (남 1 / 여 2) : ");
		gender = sc.nextInt();
		sc.nextLine();

		if (gender < 0 || gender > 2) {
			System.out.println("            -- 잘못 입력하였습니다. --          ");
			guestSet(sc);
			return;
		}

		System.out.print("           * 고객 나이 : ");
		age = sc.nextInt();
		sc.nextLine();

		System.out.print("           * 숙박 인원 : ");
		people = sc.nextInt();
		sc.nextLine();

		System.out.print("           * 숙박 일수 : ");
		day = sc.nextInt();
		sc.nextLine();

	}

	/*
	 * 예약일 객체 확인 메서드
	 * 
	 * 예약일 객체 모음인 arr2를 체크 arr2의 사이즈만큼 반복하여 확인했을 때 
	 * 예약 시 지정된 날짜(reser)가 확인 중인 객체의 예약일 변수(date)에 이미 있다면 
	 * 카운트(cnt)를 하여 넘어감
	 * 
	 * 그 카운트가 한번도 늘어나지 않고 0이라면 새로운 예약일 객체를 생성 후
	 * 방금 만든 객체(arr2.size() - 1)의 date에
	 * reser 값 입력
	 */
	public void datecheck(ArrayList<ReservationDay> arr2) {
		int cnt = 0;
		for (int i = 0; i < arr2.size(); i++) {
			if (arr2.get(i).date.equals(reser)) {
				cnt++;
			}
		}

		if (cnt == 0) {
			arr2.add(new ReservationDay());
			arr2.get(arr2.size() - 1).date = reser;
		}

	}

	/*
	 * 예약일 연박 생성 메서드
	 * 
	 * 입력된 숙박일수(day)만큼 반복하여 연박 시 체크인과 체크아웃 사이의 날짜도 예약으로 처리
	 * 
	 * Calendar 클래스를 사용하여 체크인 날짜를 멤버변수 ing에 입력
	 * 이때 반복을 위한 변수인 z(0 ~ (day-1))를 일 수에 더해서 입력
	 * 당일, 당일 + 1, 당일 + 2....로 비교하며 진행
	 * 
	 * 날짜를 비교하기 위한 문자열 포맷(ex.20250101)으로 변환 후 reser_i에 저장
	 * 예약일 날짜 객체 모음(arr2)에 해당 날짜(reser_i)가 있다면
	 * 카운트 후 해당 인덱스를 a에 저장
	 * 
	 * 카운트가 한번도 안되어 0이라면
	 * 예약일 객체를 하나 생성하고 해당 객체에 날짜와 방 예약 정보를 입력
	 * 
	 * 카운트가 되어 이미 해당 날짜 객체가 있다면
	 * 아까 저장한 a를 인덱스로 사용하여 해당 날짜에 방 예약 정보를 입력
	 */
	public void roomcheck(ArrayList<ReservationDay> arr2) {
		for (int z = 0; z < day; z++) {
			int cnt = 0;
			int a = 0;

			ing = Calendar.getInstance();

			ing.set(Calendar.YEAR, year);
			ing.set(Calendar.MONTH, month - 1);
			ing.set(Calendar.DAY_OF_MONTH, date + z);
			String reser_i = sdf1.format(ing.getTime());

			for (int k = 0; k < arr2.size(); k++) {
				if (arr2.get(k).date.equals(reser_i)) {
					cnt++;
					a = k;
				}

			}
			if (cnt == 0) {
				arr2.add(new ReservationDay());
				arr2.get(arr2.size() - 1).date = reser_i;
				arr2.get(arr2.size() - 1).map.put(room, true);
			} else {
				arr2.get(a).map.put(room, true);
			}
		}
	}
	
	/* 룸 예약 메서드
	 * 
	 * 원하는 호실이 있는지 물어보는 프롬프트 출력
	 * 
	 * 있을 시(Y)
	 * 
	 * 호실 입력(room) 후 해당 호실과 이전에 받은 예약일을 예약일 객체모음(arr2)에 비교하여
	 * 날짜도 같고 호실도 예약중일 때(true)
	 * 예약이 이미 있다는 메세지 출력 후 room을 다시 0으로 초기화
	 * 그리고 룸 예약 메서드를 다시 실행 - 반복되지 않게 return
	 * 
	 * 만약 날짜는 같으나 해당 호실이 예약중이 아닐때(false)
	 * 예약 연박 생성 메서드(roomcheck)를 실행 후 break
	 * 
	 * 없을 시(N)
	 * 
	 * 방을 랜덤 배정하기 위해 101~303호까지 반복
	 * 여기서 반복을 할 땐 i가 100으로 나눴을 때 나머지가 3이 되는 수라면
	 * i를 97을 더해서 103->201 로 시작하여 확인할 수 있도록 조건문 처리
	 * 
	 * 호실을 i로 두고 arr2가 예약일도 같고 방이 예약중이 아닐 때(false)
	 * 호실 변수(room)에 i를 저장하고
	 * 예약 연박 생성 메서드(roomcheck)를 실행 후 break
	 * 
	 * 메서드를 실행 후 i가 호실 번호와 일치한다면 break
	 * 하지만 호실 번호가 바뀌지 않고(0) i가 303인 상태라면
	 * 모든 방이 예약중이므로 해당 메세지 출력 후
	 * 날짜 지정 예약 메서드 실행 후 return
	 * */
	public void roomSet(Scanner sc, int hotelmoney, DecimalFormat format, ArrayList<ReservationDay> arr2) {

		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.println("           * 원하시는 호실이 있나요? *            ");
		System.out.println();
		System.out.print("           * Y / N : ");

		String ny = sc.nextLine();
		if (ny.charAt(0) == 'Y' || ny.charAt(0) == 'y') {
			System.out.println();
			System.out.print("       * 원하시는 호실을 입력해 주세요 : ");
			room = sc.nextInt();
			sc.nextLine();

			for (int i = 0; i < arr2.size(); i++) {
				if (arr2.get(i).date.equals(reser)) {
					if (arr2.get(i).map.get(room)) {
						System.out.println();
						System.out.println("          -- " + room + "호는 예약이 있습니다. --          ");
						room = 0;
						roomSet(sc, hotelmoney, format, arr2);
						return;
					}else {
						roomcheck(arr2);
						break;
					}
				}
			}


		} else if (ny.charAt(0) == 'N' || ny.charAt(0) == 'n') {

			System.out.println();
			System.out.println("--------------------------------------------------");
			System.out.println();
			System.out.println("           * 룸을 랜덤 배정합니다. *             ");

			for (int i = 101; i <= 303; i++) {
				for (int j = 0; j < arr2.size(); j++) {
					if (arr2.get(j).date.equals(reser)) {
						if (!(arr2.get(j).map.get(i))) {
							room = i;

							roomcheck(arr2);

							break;
						}
					}
				}

				if (room == i) {
					break;
				} else if (i == 303 && room == 0) {
					System.out.println("--------------------------------------------------");
					System.out.println();
					System.out.println("   -- 해당 날짜의 모든 방이 예약되었습니다. --    ");
					System.out.println("         -- 다른 날짜로 예약해주세요. --          ");
					roomReservation_later(sc, hotelmoney, format, arr2);
					return;
				}

				if (i % 100 == 3) {
					i += 97;
				}

			}

		} else {
			System.out.println();
			System.out.println("            -- 잘못 입력하였습니다. --          ");
			roomSet(sc, hotelmoney, format, arr2);
			return;
		}
	}

	/* 오늘 예약 메서드
	 * 
	 * 체크인 변수(in)에 오늘 날짜를 세팅
	 * 문자열 비교를 위한 포맷 sdf1과 출력을 위한 포맷인 sdf2를 각각
	 * 멤버변수 reser와 format_s에 저장
	 * 
	 * 해당 날짜로 예약일 객체 확인 메서드 실행
	 * 고객정보 입력 메서드 실행
	 * 룸 예약 메서드 실행
	 * 각 메서드를 실행하여 나온 결과를 출력할 수 있도록 getInfo()를 실행
	 * 
	 * 결제 비용을 저장하는 money 멤버변수에 1박 가격인 55,000원 * 숙박일수(day)를 곱하여 저장
	 * 금액에 쉼표를 넣기위한 포맷을 money_s에 저장
	 * 
	 * 결제비용과 결제완료 문구를 sleep으로 천천히 출력
	 * 
	 * 매개변수인 hotelmoney에 결제비용을 더하여 저장 후
	 * hoelmoney를 return
	 * */
	public int roomReservation_now(Scanner sc, int hotelmoney, DecimalFormat format, ArrayList<ReservationDay> arr2) {

		in = Calendar.getInstance();

		year = in.get(Calendar.YEAR);
		month = in.get(Calendar.MONTH) + 1;
		date = in.get(Calendar.DAY_OF_MONTH);

		reser = sdf1.format(in.getTime());
		format_s = sdf2.format(in.getTime());

		datecheck(arr2);

		guestSet(sc);
		roomSet(sc, hotelmoney, format, arr2);

		System.out.println();
		System.out.println("           * 입력하신 정보 입니다.");
		getInfo();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		money = 55000 * day;
		money_s = format.format(money);

		System.out.println();
		System.out.println("        * " + day + "박 결제 비용은 " + money_s + "원 입니다. *");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("          * 결제되었습니다. 감사합니다. *         ");
		System.out.println();
		hotelmoney += money;

		return hotelmoney;

	}
	
	/* 날짜 지정 예약 메서드
	 * 
	 * 오늘 날짜와 비교를 위한 now에 오늘 날짜 입력 후
	 * 날짜 비교를 위한 포맷인 sdf1으로 포맷하여 now_s에 저장
	 * 
	 * 예약날짜를 8자리로 예약일 변수(reser)에 입력받음
	 * 만약 입력한 날짜가 8자리가 아니거나
	 * 연도, 월, 일이 이상하게 입력되거나
	 * 오늘 날짜(now_s)보다 이전으로 입력되면
	 * 오류 문구 출력 후 해당 메서드 다시 실행 후 return
	 * 
	 * 체크인 변수(in)에 입력받은 날짜를 세팅
	 * 출력을 위한 포맷인 sdf2를 format_s에 저장
	 * 
	 * 해당 날짜로 예약일 객체 확인 메서드 실행
	 * 고객정보 입력 메서드 실행
	 * 룸 예약 메서드 실행
	 * 각 메서드를 실행하여 나온 결과를 출력할 수 있도록 getInfo()를 실행
	 * 
	 * 결제 비용을 저장하는 money 멤버변수에 1박 가격인 55,000원 * 숙박일수(day)를 곱하여 저장
	 * 금액에 쉼표를 넣기위한 포맷을 money_s에 저장
	 * 
	 * 결제비용과 결제완료 문구를 sleep으로 천천히 출력
	 * 
	 * 매개변수인 hotelmoney에 결제비용을 더하여 저장 후
	 * hoelmoney를 return
	 * */
	public int roomReservation_later(Scanner sc, int hotelmoney, DecimalFormat format, ArrayList<ReservationDay> arr2) {
		now = Calendar.getInstance();

		String now_s = sdf1.format(now.getTime());

		System.out.println();
		System.out.print("* 예약 날짜를 입력해주세요(ex.20250101) : ");
		reser = sc.nextLine();

		if (reser.length() != 8) {
			System.out.println();
			System.out.println("   -- 잘못 입력하였습니다. 다시 입력해주세요. --  ");
			roomReservation_later(sc, hotelmoney, format, arr2);
			return hotelmoney;
		}

		datecheck(arr2);

		year = Integer.parseInt(reser.substring(0, 4));
		month = Integer.parseInt(reser.substring(4, 6));
		date = Integer.parseInt(reser.substring(6));

		if (year < 0 || month > 12 || date > 31) {
			System.out.println();
			System.out.println("   -- 잘못 입력하였습니다. 다시 입력해주세요. --  ");
			roomReservation_later(sc, hotelmoney, format, arr2);
			return hotelmoney;
		} else if (Integer.parseInt(reser) < Integer.parseInt(now_s)) {
			System.out.println();
			System.out.println("     -- 예약일은 현재 날짜 이후로 해주세요. --    ");
			roomReservation_later(sc, hotelmoney, format, arr2);
			return hotelmoney;
		}

		in = Calendar.getInstance();

		in.set(Calendar.YEAR, year);
		in.set(Calendar.MONTH, month - 1);
		in.set(Calendar.DAY_OF_MONTH, date);
		format_s = sdf2.format(in.getTime());

		guestSet(sc);
		roomSet(sc, hotelmoney, format, arr2);

		System.out.println();
		System.out.println("           * 입력하신 정보 입니다.");
		getInfo();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		money = 55000 * day;
		money_s = format.format(money);

		System.out.println();
		System.out.println("        * " + day + "박 결제 비용은 " + money_s + "원 입니다. *");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("          * 결제되었습니다. 감사합니다. *         ");
		System.out.println();

		return hotelmoney;

	}

	/* 정보 출력 메서드
	 * 
	 * 체크아웃 날짜를 출력하기 위한 out 변수에 체크인 날짜를 입력 후
	 * 숙박일수(day)를 일수에 추가 후 출력을 위한 포맷인 sdf2로 format_e에 저장
	 * 
	 * 입력된 정보들을 출력
	 * */
	public void getInfo() {
		out = Calendar.getInstance();
		out.set(Calendar.YEAR, year);
		out.set(Calendar.MONTH, month - 1);
		out.set(Calendar.DAY_OF_MONTH, date);
		out.add(Calendar.DAY_OF_MONTH, day);
		format_e = sdf2.format(out.getTime());

		System.out.println();
		System.out.println("           * 고객 성함 : " + name);
		System.out.println("           * 고객 성별 : " + (gender == 1 ? "남자" : "여자"));
		System.out.println("           * 고객 나이 : " + age + "세");
		System.out.println("           * 예약 룸 넘버 : " + room + "호");
		System.out.println("           * 숙박 인원 : " + people + "명");
		System.out.println("           * 숙박 일수 : " + day + "박 " + (day + 1) + "일");
		System.out.println("           * 체크인 : " + format_s);
		System.out.println("           * 체크아웃 : " + format_e);

	}
	
	/* 예약 취소 메서드
	 * 
	 * 메서드 실행 시 환불을 할 때는 결제금액에 90%만 차감하므로
	 * money * 0.9를 하고 해당 금액을 쉼표 출력을 위해 format
	 * 
	 * 취소를 동의할 시
	 * 
	 * Calendar 클래스를 사용하여 체크인 날짜를 멤버변수 ing에 입력
	 * 이때 반복을 위한 변수인 z(0 ~ (day-1))를 일 수에 더해서 입력
	 * 당일, 당일 + 1, 당일 + 2....로  진행
	 * 
	 * 날짜를 비교하기 위한 문자열 포맷(ex.20250101)으로 변환 후 reser_i에 저장
	 * 예약일 날짜 객체 모음(arr2)에 해당 날짜(reser_i)가 있다면
	 * 해당 날짜에 고객 객체에 저장되어 있는 키값인 room(호실)을 예약 종료(false)
	 * hotelmoney에 환불금을 차감하여 반환
	 * */

	public int roomCancle(Scanner sc, int hotelmoney, DecimalFormat format, ArrayList<ReservationDay> arr2) {

		try {
			money_s = format.format(money * 0.9);
			System.out.println();
			System.out.println("--------------------------------------------------");
			System.out.println();
			System.out.println("  * " + name + " 고객님의 예약취소를 도와드리겠습니다. *");
			Thread.sleep(1500);
			System.out.println("     * 취소수수료가 10% 부과되어 환불됩니다. *    ");
			Thread.sleep(1500);
			System.out.println("         * 환불 금액은 " + money_s + "원 입니다. *");
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		System.out.println();
		System.out.println("               * 취소하시겠습니까? *              ");
		System.out.print("               * Y/N : ");
		String ny = sc.next();
		if (ny.charAt(0) == 'Y' || ny.charAt(0) == 'y') {
			hotelmoney -= (int) (money * 0.9);
			name = "";

			for (int i = 0; i < arr2.size(); i++) {

				for (int z = 0; z < day; z++) {

					ing = Calendar.getInstance();

					ing.set(Calendar.YEAR, year);
					ing.set(Calendar.MONTH, month - 1);
					ing.set(Calendar.DAY_OF_MONTH, date + z);
					String reser_i = sdf1.format(ing.getTime());

					if (arr2.get(i).date.equals(reser_i)) {
						arr2.get(i).map.put(room, false);
					}

				}

			}

			System.out.println();
			System.out.println("     * " + room + "호실의 예약 취소가 완료되었습니다. *");
			System.out.println("                  * 감사합니다. *                 ");
			room = 0;

		} else if (ny.charAt(0) == 'N' || ny.charAt(0) == 'n') {
			System.out.println();
			System.out.println(" -- 고객님 요청으로 예약이 취소되지 않았습니다. --");
			System.out.println("                 -- 감사합니다. --                ");
		} else {
			System.out.println("            -- 잘못 입력하였습니다. --          ");
			roomCancle(sc, hotelmoney, format, arr2);
			return hotelmoney;
		}

		return hotelmoney;
	}
	
	/* 예약 연장 메서드
	 * 
	 * 현재 예약 정보를 출력한 뒤
	 * 연장할 숙박일수를 입력받아 기존의 숙박일수(day)에 더해서 저장
	 * 
	 * 예약일 연박 생성 메서드를 실행하여
	 * 추가된 숙박일수를 기준으로 다시 예약 과정 진행
	 * 
	 * 추가비용 안내 후 55,000원에 추가 숙박일수를 곱한 값을 
	 * hotelmoney에 더하여 저장 후 반환
	 * 
	 * */
	
	public int roomReservationChange(Scanner sc, int hotelmoney, DecimalFormat format, ArrayList<ReservationDay> arr2) {
		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.println("           * 고객님의 예약 정보 입니다.");
		System.out.println();

		System.out.println("           * 숙박 일수 : " + day + "박 " + (day + 1) + "일");
		System.out.println("           * 체크인 : " + format_s);
		System.out.println("           * 체크아웃 : " + format_e);

		System.out.println();
		System.out.print("           * 몇 박을 연장하시겠습니까? : ");
		int day_c = sc.nextInt();
		int money_c = 55000 * day_c;
		day += day_c;
		money_s = format.format(money_c);

		System.out.println();
		System.out.println("     * " + day_c + "박 추가 결제 비용은 " + money_s + "원 입니다. *");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		System.out.println();
		System.out.println("          * 결제되었습니다. 감사합니다. *         ");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}

		out.set(Calendar.YEAR, year);
		out.set(Calendar.MONTH, month - 1);
		out.set(Calendar.DAY_OF_MONTH, date);
		out.add(Calendar.DAY_OF_MONTH, day);
		format_e = sdf2.format(out.getTime());

		roomcheck(arr2);
		System.out.println();
		System.out.println("           * 변경된 예약정보 입니다.");
		System.out.println();
		System.out.println("           * 숙박 일수 : " + day + "박 " + (day + 1) + "일");
		System.out.println("           * 체크인 : " + format_s);
		System.out.println("           * 체크아웃 : " + format_e);

		hotelmoney += money_c;

		return hotelmoney;

	}

}
