//헤더 정리하기
#include <cmath>	//pow함수를 사용하기 위한 헤더파일
#include <ctime>	//코드 실행시간 측정을 위한 헤더파일
#include <iostream>	//입출력 함수를 사용하기 위한 헤더파일
using namespace std;//표준 이름공간을 사용함

const int gear_count = 4;	//톱니바퀴의 수를 const 상수로 선언(값을 변경할 수 없음)
const int direction = 8;	//어떤 수 N의 최댓값을 const 상수로 선언(값을 변경할 수 없음)

//사용자가 입력한 회전시킬 톱니바퀴의 정보를 저장할 클래스
class Trigger {
public:
	int trigger;	//회전시킬 톱니바퀴의 번호
	int clockwise;	//회전방향
};

//decision과 clockwise 배열을 초기화 하는 함수
void array_clear(int* array) {
	for (int i = 0; i < gear_count; i++) {
		array[i] = 0;
	}
}

//톱니를 회전시키는 함수
void rotate(int gear[][direction], int* decision, int* clockwise) {
	int temp[direction];							//값을 복사할 임시 배열

	for (int i = 0; i < gear_count; i++) {
		if (decision[i] == 1) {			//decision의 값이 1이어야 회전시킴
			for (int j = 0; j < direction; j++) {	//임시 배열에 값 복사
				temp[j] = gear[i][j];
			}
			if (clockwise[i] == 1) {				//시계방향
				for (int j = 0; j < direction; j++) {
					gear[i][(j + 1) % 8] = temp[j];	//j번째 값을 j+1번째 값에 저장. 7번 데이터를 8번 인덱스에 저장할 수 없으므로 mod8연산을 취한 뒤 저장
				}
			}
			else if (clockwise[i] == -1) {			//반시계 방향
				for (int j = 0; j < direction; j++) {
					gear[i][(j + 7) % 8] = temp[j];	//j번째 값을 j-1번째 값에 저장. 0번 데이터를 -1번 인덱스에 저장할 수 없으므로 j+7(=j-1+8)번 인덱스에 mod8연산을 취한 뒤 저장
				}
			}
		}
	}
}

int main() {
	string buf[gear_count];						//사용자에게 입력받은 톱니바퀴의 상태를 저장할 임시 문자열 배열
	int gear[gear_count][direction];			//톱니바퀴의 상태를 저장할 정수형 배열
	int* decision;								//톱니를 회전시킬지 말지를 결정하는 배열
	int* clockwise;								//회전방향을 저장하는 배열
	int K, temp_clockwise, score = 0;			//K: 회전시킬 횟수, temp_clockwise: 회전방향 임시변수, score: 최종 점수(0으로 초기화)

	for (int i = 0; i < gear_count; i++) {		//원시 톱니바퀴 상태 입력받음
		cin >> buf[i];
	}

	cin >> K;									//회전시킬 횟수 입력받음
	Trigger* trigger = new Trigger[K];			//회전시킬 톱니바퀴의 정보를 저장하는 배열을 동적으로 할당

	for (int i = 0; i < K; i++) {
		cin >> trigger[i].trigger >> trigger[i].clockwise;	//첫 번째 정수 : 회전시킨 톱니바퀴의 번호, 두 번째 정수 : 회전방향
	}

	clock_t start = clock();					//시작 시간 저장

	decision = new int[K];						//톱니를 회전시킬지 말지를 결정하는 배열을 동적으로 할당
	clockwise = new int[K];						//회전방향을 저장하는 배열을 동적으로 할당

	for (int i = 0; i < gear_count; i++) {
		decision[i] = 0;	//결정 배열 초기화
		clockwise[i] = 0;	//방향 배열 초기화
	}

	//문자열로 입력받은 톱니바퀴의 상태를 정수형으로 변환
	for (int i = 0; i < gear_count; i++) {
		string temp = buf[i];

		for (int j = 0; j < direction; j++) {
			gear[i][j] = temp[j] - '0';			//정수형으로 바꾸기 위해 '0'의 아스키코드 값을 뺌
		}
	}

	for (int i = 0; i < K; i++) {
		temp_clockwise = trigger[i].clockwise;							//가장 처음 회전하는(사용자가 회전시킨) 톱니바퀴의 회전방향을 저장
		array_clear(decision);											//회전할 때만 1로 값을 변경하기 때문에 미리 배열을 초기화함
		array_clear(clockwise);											//쓰레기값 제거(하지 않아도 됨)
		decision[trigger[i].trigger - 1] = 1;							//가장 처음 회전하는(사용자가 회전시킨) 톱니바퀴는 무조건 회전하기 때문에 1을 저장
		clockwise[trigger[i].trigger - 1] = trigger[i].clockwise;		//가장 처음 회전하는(사용자가 회전시킨) 톱니바퀴의 회전방향 저장
		switch (trigger[i].trigger - 1) {								//사용자가 입력한 톱니번호와 배열의 인덱스를 맞춰주기 위함
		case 0:	//1번 톱니
			for (int j = 0; j < gear_count - 1; j++) {					//총 3번 반복
				if (gear[j][2] != gear[j + 1][6]) {						//극이 다르면
					decision[j + 1] = 1;								//1로 설정함
					clockwise[j + 1] = temp_clockwise * pow(-1, j+1);	//-1의 1승, 3승이면 역방향 2승이면 정방향
				}
				else													//극이 같으면 이후 톱니는 회전하지 않으므로
					break;												//반복문 탈출
			}
			break;														//아래에 있는 case문으로 진행되지 않도록 막음
		case 1:	//2번 톱니
			for (int j = 1; j < gear_count - 1; j++) {					//총 2번 반복
				if (gear[j][2] != gear[j + 1][6]) {						//극이 다르면
					decision[j + 1] = 1;								//1로 설정함
					clockwise[j + 1] = temp_clockwise * pow(-1, j);		//-1의 1승, 3승이면 역방향 2승이면 정방향
				}
				else													//극이 같으면 이후 톱니는 회전하지 않으므로
					break;												//반복문 탈출
			}
			if (gear[0][2] != gear[1][6]) {								//1번 톱니와 극이 다르다면
				decision[0] = 1;										//1로 설정함
				clockwise[0] = -1 * temp_clockwise;						//2번톱니와 다른 방향으로 회전
			}
			break;														//아래에 있는 case문으로 진행되지 않도록 막음
		case 2:	//3번 톱니
			for (int j = 2; j > 0; j--) {								//총 2번 반복
				if (gear[j][6] != gear[j - 1][2]) {						//극이 다르면
					decision[j - 1] = 1;								//1로 설정함
					clockwise[j - 1] = temp_clockwise * pow(-1, j - 1);	//-1의 1승, 3승이면 역방향 2승이면 정방향
				}
				else													//극이 같으면 이후 톱니는 회전하지 않으므로
					break;												//반복문 탈출
			}
			if (gear[3][6] != gear[2][2]) {								//4번 톱니와 극이 다르다면
				decision[3] = 1;										//1로 설정함
				clockwise[3] = -1 * temp_clockwise;						//3번톱니와 다른 방향으로 회전
			}
			break;														//아래에 있는 case문으로 진행되지 않도록 막음
		case 3:	//4번 톱니
			for (int j = gear_count - 1; j > 0; j--) {					//총 3번 반복
				if (gear[j][6] != gear[j - 1][2]) {						//극이 다르면
					decision[j - 1] = 1;
					clockwise[j - 1] = temp_clockwise * pow(-1, j);		//-1의 1승, 3승이면 역방향 2승이면 정방향
				}
				else													//극이 같으면 이후 톱니는 회전하지 않으므로
					break;												//반복문 탈출
			}
			break;														//아래에 있는 case문으로 진행되지 않도록 막음
		}
		rotate(gear, decision, clockwise);								//decision 인자로 호출하여 기어들을 회전 시킴
	}

	/*
	 *<점수계산 규칙>
	 *1번 톱니바퀴의 12시 방향이 N극이면 0번, S극이면 1점 / 2번 톱니바퀴의 12시 방향이 N극이면 0번, S극이면 2점
	 *3번 톱니바퀴의 12시 방향이 N극이면 0번, S극이면 4점 / 4번 톱니바퀴의 12시 방향이 N극이면 0번, S극이면 8점
	 *=> 일반화: 2^(n-1)점
	 */

	for (int i = 0; i < gear_count; i++) {	//gear_count(톱니바퀴의 개수)번 반복하여
		if (gear[i][0] == 1) {				//12시 방향이 S극(값이 1)이라면
			score += pow(2, i);				//기존 score에 2^(현재 톱니바퀴의 번호)를 더함
		}
	}

	cout << score << endl;					//네 톱니바퀴의 점수의 합 출력

	delete[]trigger;		//동적할당한 배열의 메모리 해제
	delete[] decision;		//동적할당한 배열의 메모리 해제
	delete[] clockwise;		//동적할당한 배열의 메모리 해제
	trigger = nullptr;		//배열을 가리키는 포인터에 저장된 주소값 삭제
	decision = nullptr;		//배열을 가리키는 포인터에 저장된 주소값 삭제
	clockwise = nullptr;	//배열을 가리키는 포인터에 저장된 주소값 삭제

	clock_t end = clock();	//코드가 끝난 시간 저장
//	cout << "실행시간: " << (double)(end - start) / CLOCKS_PER_SEC << "초" << endl;	//실행시간(단위: 초) 출력

	return 0;
}
