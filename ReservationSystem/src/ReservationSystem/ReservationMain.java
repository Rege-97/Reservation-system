package ReservationSystem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class ReservationMain {
	
	/* 기본 메뉴 출력 메서드
	 * 
	 * Thread.sleep()을 이용하여
	 * 타이틀과 입력 메뉴에 출력 격차를 주어
	 * 로딩처럼 화면 구현
	 * */
	public static void printMenu() {

		System.out.println(" ________________________________________________");
		System.out.println("|     ___                  ___  _____  __  __    |");
		System.out.println("|    / __\\ /\\  /\\   /\\  /\\/___\\/__   \\/__\\/ /    |");
		System.out.println("|   / /   / /_/ /  / /_/ //  //  / /\\/_\\ / /     |");
		System.out.println("|  / /___/ __  /  / __  / \\_//  / / //__/ /___   |");
		System.out.println("|  \\____/\\/ /_/   \\/ /_/\\___/   \\/  \\__/\\____/   |");
		System.out.println("|________________________________________________|");
		System.out.println();
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("==================================================");
		System.out.println();
		System.out.println("             * CH 호텔 예약 시스템 *");
		System.out.println("         * CH Hotel Reservation System *");
		System.out.println("               * 1 Day 55,000won *");
		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.println("                * 1. 룸 예약하기");
		System.out.println("                * 2. 고객 검색");
		System.out.println("                * 3. 룸 예약 현황");
		System.out.println("                * 4. 영업 현황 확인               ");
		System.out.println("                * 5. 프로그램 종료");
		System.out.println();
		System.out.print("                * 메뉴 입력 : ");

	}
	
	/* 메인메뉴 - 2
	 * 고객 검색 메서드
	 * 
	 * 입력받은 고객명을 현재 고객 객체(arr)수 만큼 반복하여
	 * 해당 고객이 몇 명이 있는지 cnt로 카운팅 (동명이인 체크)
	 * 
	 * 다시 cnt를 0으로 초기화 한 뒤
	 * 고객 객체 수만큼 입력받은 고객명을 비교하여 고객정보 출력
	 * cnt로 카운팅 된 번호를 고객 객체에 저장하여 몇 번째 고객인지 구분
	 * 
	 * 동명이인이 있다면 몇 번째 고객을 찾는건지 입력을 받은 뒤
	 * 고객 객체들 중 입력된 고객 이름이 똑같고 저장된 고객 번호(search)가 같으면
	 * 해당 고객의 정보만 출력 후 해당 고객의 ArrayList 인덱스를 반환
	 * */

	public static int search(Scanner sc, ArrayList<Guest> arr, int hotelmoney) {
		int cnt = 0;
		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.print("          * 고객명을 입력하세요 : ");
		String guest = sc.nextLine();
		int room_n = -1;

		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).name.equals(guest)) {
				cnt++;
			}
		}

		System.out.println();
		System.out.println("         * " + cnt + "건의 예약이 검색되었습니다. *");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		cnt = 0;

		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).name.equals(guest)) {
				cnt++;
				System.out.println();
				System.out.println("               ==== " + (cnt) + "번째 고객 ====");
				arr.get(i).search = cnt;
				arr.get(i).getInfo();

				room_n = i;
			}
		}

		if (cnt >= 2) {
			System.out.println();
			System.out.println("--------------------------------------------------");
			System.out.println();
			System.out.println("            -- 동명이인이 있습니다. --            ");
			System.out.println();
			System.out.print("         * 몇 번째 고객을 검색할까요? : ");
			int num = sc.nextInt();
			sc.nextLine();
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i).name.equals(guest) && arr.get(i).search == num) {
					System.out.println();
					System.out.println("               * " + num + "번째 고객입니다. *");
					arr.get(i).getInfo();
					room_n = i;
				}
			}

		}

		return room_n;

	}
	
	/* 예약 정보 변경 메서드
	 * 
	 * 고객 검색 메서드로 찾은 고객데이터가 들어있는 arr의 인덱스(room_n)로
	 * 취소와 연장을 결정할 수 있는 메뉴 출력
	 * 
	 * 취소 시 
	 * 예약 취소 메서드 실행
	 * 
	 * 연장 시
	 * 예약 연장 메서드 실행
	 * 
	 * 3번을 입력 시 이전 메뉴로 이동
	 *
	 * 취소하거나 연장에서 변경된 hotelmoney를 반환
	 * 
	 * */
	public static int change(Scanner sc, ArrayList<Guest> arr, int hotelmoney, int room_n, DecimalFormat format,
			ArrayList<ReservationDay> arr2) {
		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.println("                  * 1. 예약 취소                  ");
		System.out.println("                  * 2. 예약 연장");
		System.out.println("                  * 3. 이전 메뉴");
		System.out.println();
		System.out.print("                  * 메뉴 입력 : ");
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
				System.out.println("            * 이전 메뉴로 돌아갑니다. *           ");
				break;
			default:
				System.out.println("            -- 잘못 입력하였습니다. --          ");
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println();
			System.out.println("           -- 예약한 고객이 없습니다. --          ");
		}

		return hotelmoney;
	}
	
	/* 예약 현황 메서드
	 * 
	 * 예약일 정보가 들어있는 arr2 안에 있는 해쉬맵의 키 값을 추출하여 key에 저장
	 * 오늘 날짜를 담고 있는 today 변수를 만들고 모든 호실을 체크할 수 있는 반복문을 이용
	 * 그 반복문 안에서 모든 고객 정보를 확인하는 반복문을 이용하여
	 * today의 내용과 해당 고객의 예약일이 같고 지금 확인하는 호실 정보가 같다면
	 * 빈방인지 체크하는 check 변수를 true로 바꾸고 현재 이용중으로 표시
	 * 만약 모든 고객을 확인하였는데도 check가 false라면 현재 빈방으로 표시
	 * 
	 * 오늘의 예약정보를 확인 후 이번에는 모든 고객 정보를 확인하되
	 * 예약일 정보가 today와 같지 않고 호실 정보가 같으면 예약중으로 표시
	 * 
	 * */

	public static void getRoomInfo(ArrayList<Guest> arr, ArrayList<ReservationDay> arr2) {
		Iterator<Integer> keys = arr2.get(0).map.keySet().iterator();

		ArrayList<Integer> key = new ArrayList<Integer>();
		Calendar now = Calendar.getInstance();
		boolean check = false;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

		String today = sdf1.format(now.getTime());

		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.println("             *** 호텔 룸 예약 현황 ***            ");
		System.out.println();

		while (keys.hasNext()) {
			key.add(keys.next());
		}

		for (int i = 0; i < key.size(); i++) {
			System.out.println();
			System.out.println("                  *** " + key.get(i) + "호실 ***");
			System.out.println("--------------------------------------------------");

			for (int j = 0; j < arr.size(); j++) {
				check = false;
				if (arr.get(j).reser.equals(today) && arr.get(j).room == key.get(i)) {
					System.out.println("                 * 현재 이용 중 *                 ");
					System.out.println();
					System.out.println("           * 고객 성함 : " + arr.get(j).name);
					System.out.println("           * 숙박 인원 : " + arr.get(j).people + "명");
					System.out.println("           * 체크인 : " + arr.get(j).format_s);
					System.out.println("           * 체크아웃 : " + arr.get(j).format_e);
					System.out.println();
					check = true;
					break;
				}
			}

			if (!check) {
				System.out.println("                  -- 현재 빈방 --                 ");
				System.out.println();
			}

			for (int j = 0; j < arr.size(); j++) {
				if (!(arr.get(j).reser.equals(today)) && arr.get(j).room == key.get(i)) {
					System.out.println("                    * 예약자 *                    ");
					System.out.println();
					System.out.println("           * 고객 성함 : " + arr.get(j).name);
					System.out.println("           * 숙박 인원 : " + arr.get(j).people + "명");
					System.out.println("           * 체크인 : " + arr.get(j).format_s);
					System.out.println("           * 체크아웃 : " + arr.get(j).format_e);
					System.out.println();
				}
			}

		}

	}
	
	/* 영업 현황 메서드
	 * 
	 * 오늘 날짜를 담고 있는 today 변수 생성
	 * 
	 * 예약일을 모음인 arr2를 모두 확인하여 오늘 날짜의 예약정보를 찾고 
	 * 해당 날짜에 룸 예약수를 카운트 하여 count_room(현재 이용 고객)을 생성
	 * 
	 * visit(방문객 수)는 고객 객체를 생성할 때 마다 +1하여 저장
	 * 
	 * 현재 이용 고객과 방문객 수 그리고 매출을 출력
	 *
	 * */

	public static void getHotelInfo(ArrayList<Guest> arr, ArrayList<ReservationDay> arr2, DecimalFormat format,
			int hotelmoney, int visit) {
		int count_room = 0;
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

		String today = sdf1.format(now.getTime());

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
		System.out.println("--------------------------------------------------");
		System.out.println();
		System.out.println("                * 현재 영업 현황 *                ");
		System.out.println();
		System.out.println("           * 현재 이용 고객 수 : " + count_room + "명");
		System.out.println("           * 누적 방문 고객 수 : " + visit + "명");
		System.out.println("           * 누적 매출액 : " + format.format(hotelmoney - 100000) + "원");
		System.out.println("           * 현재 금고 잔액 : " + hotelmoney_s + "원");
		System.out.println();

	}

	public static void main(String[] args) {
		ArrayList<Guest> arr = new ArrayList<Guest>();
		ArrayList<ReservationDay> arr2 = new ArrayList<ReservationDay>();
		arr2.add(new ReservationDay());
		DecimalFormat format = new DecimalFormat("#,###");

		int hotelmoney = 100000;
		int visit = 0;

		while (true) {
			Scanner sc = new Scanner(System.in);
			try {
				printMenu();

				int user = sc.nextInt();
				sc.nextLine();

				switch (user) {
				case 1:

					System.out.println();
					System.out.println("--------------------------------------------------");
					System.out.println();
					System.out.println("                 * 1. 오늘 예약");
					System.out.println("                 * 2. 예약일 지정                 ");
					System.out.println("                 * 3. 이전 메뉴");
					System.out.println();
					System.out.print("                 * 메뉴 입력 : ");
					int user_a = sc.nextInt();
					sc.nextLine();

					switch (user_a) {
					case 1:
						arr.add(new Guest());
						System.out.println();
						System.out.println("--------------------------------------------------");
						System.out.println();
						System.out.println("                  * 오늘 예약 *                   ");
						hotelmoney = arr.get(arr.size() - 1).roomReservation_now(sc, hotelmoney, format, arr2);
						visit++;
						break;
					case 2:
						arr.add(new Guest());
						System.out.println();
						System.out.println("--------------------------------------------------");
						System.out.println();
						System.out.println("                 * 예약일 지정 *                  ");
						hotelmoney = arr.get(arr.size() - 1).roomReservation_later(sc, hotelmoney, format, arr2);
						visit++;
						break;
					case 3:
						System.out.println();
						System.out.println("            * 이전 메뉴로 돌아갑니다. *           ");
						break;
					default:
						System.out.println();
						System.out.println("             * 잘못 입력하였습니다. *             ");
					}

					break;
				case 2:
					int room_n = search(sc, arr, hotelmoney);

					if (room_n == -1) {
						System.out.println();
						System.out.println("    -- 해당 고객명으로 예약한 방이 없습니다. --");
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
					System.out.println("           -- 프로그램을 종료합니다. --           ");
					System.exit(0);
					break;
				default:
					System.out.println();
					System.out.println("            -- 잘못 입력하였습니다. --            ");
				}
			} catch (InputMismatchException e) {
				System.out.println();
				System.out.println("            -- 잘못 입력하였습니다. --            ");
				System.out.println("             -- 메뉴로 돌아갑니다. --");
			} catch (NullPointerException e) {
				System.out.println();
				System.out.println("       -- 방 번호를 잘못 입력하였습니다. --       ");
				System.out.println("             -- 메뉴로 돌아갑니다. --");
			} catch (Exception e) {
				System.out.println();
				System.out.println("      -- 알 수 없는 오류가 발생하였습니다. --     ");
				System.out.println("        -- 고객센터에 문의하여 주십시오. --       ");
			}

		}

	}

}
