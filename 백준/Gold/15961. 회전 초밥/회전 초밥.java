import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * <pre>
 * 백준 15961번 회전 초밥
 * https://www.acmicpc.net/problem/15961
 * 
 * 실행시간: 684ms (백준)
 * 메모리: 257,580 KB
 * 
 * 원형 리스트에 1개의 수를 추가하여 고유한 숫자의 개수를 파악하는 문제입니다.
 * 
 * ArrayList클래스의 subList메소드를 사용해 HashSet에 Argument로 전달해 고유한 숫자의 개수를 파악하도록 했으나 시간 초과했습니다.
 * 따라서 슬라이딩 윈도우 알고리즘을 사용합니다.
 * 
 * [선언부]
 * 초밥의 번호를 입력받아 저장하기 위해 N 길이의 ArrayList를 할당합니다. (배열을 사용해도 무방)
 * 현재 큐에 담고 있는 초밥 번호들의 등장 횟수를 저장하기 위해 (d+1) 길이의 (초밥의 번호는 1부터 시작 -> 0번 인덱스 버림) int 배열을 할당합니다. #등장 횟수 저장 이유: 고유한 초밥 번호의 개수를 파악하기 위함
 * 연속해서 먹는 접시의 수 k 길이의 큐를 할당합니다. #초밥의 시작 인덱스를 1씩 증가시킬 때 원소 삭제를 쉽게 하려고 큐를 사용합니다. (선입선출)
 * 
 * [구현부]
 * 0번 인덱스부터 (k-1) 번 인덱스까지 리스트에 추가하고 초밥 번호의 등장 횟수를 계산합니다. HashSet을 이용해 고유한 초밥 번호의 개수를 파악합니다.
 * 
 * 아래를 회전하는 벨트의 길이인 N번 반복합니다.
 * 1. 큐에서 원소 1개를 빼냅니다. 빠져나가는 초밥의 번호의 등장 횟수가 0이 되면 고유한 초밥 번호의 개수는 1 감소합니다.
 * 2. 큐에 원소 1개를 추가합니다. 추가하는 원소의 번호는 N 이상이 될 수 있으므로 %N 연산을 취합니다.
 * 추가하는 원소가 등장한 적 없는 원소이면 (등장 횟수가 0이면) 고유한 초밥 번호의 개수는 1 증가합니다.
 * 3. 현재까지 저장된 정답 후보가 새로 계산한 고유한 초밥 번호의 개수 이상이면 고유한 초밥 번호의 개수로 갱신합니다.
 * 4. 쿠폰 번호 c가 등장한 적 없는 원소이면 고유한 초밥 번호의 개수 + 1이 정답이 됩니다.
 * </pre>
 * 
 *  @author 이현호
 */

public class Main {
	
	public static void main(String[] args) throws IOException {
		//버퍼 사이즈가 Scanner (1024 Char)에 비해 8배 큰 BufferedReader (8192 Char) 사용
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());	//문자열을 토큰 단위로 나누는 객체
		
		int N =  Integer.parseInt(st.nextToken());					//회전 초밥 벨트에 놓인 접시의 수 N
		int d =  Integer.parseInt(st.nextToken());					//초밥의 가짓수 d
		int k =  Integer.parseInt(st.nextToken());					//연속해서 먹는 접시의 수 k
		int c =  Integer.parseInt(st.nextToken());					//쿠폰 번호 c
		
		ArrayList<Integer> sushis = new ArrayList<>(N);				//벨트 위에 올려진 초밥의 번호를 저장하는 ArrayList
		int[] eatenSushiCount = new int[d+1];						//초밥 번호별 먹은 개수 (고유한 초밥 번호의 개수 파악 위함)
		ArrayDeque<Integer> queue = new ArrayDeque<>(k);			//현재 먹은 초밥의 번호를 저장하는 큐
		int addingElement = 0;										//큐에 추가하는 원소 번호
		int deletingElement = 0;									//큐에서 삭제하는 원소 번호
		int uniqueSushiCount = 0;									//고유한 초밥 번호의 개수
		int answer = Integer.MIN_VALUE;								//정답 변수 최솟값으로 초기화 (최댓값 찾기 위함)
		
		//벨트 위의 모든 초밥 번호 입력받음
		for(int i=0; i<N; i++) {
			sushis.add(Integer.parseInt(br.readLine()));
		}
		
		//0번부터 k개의 초밥 번호를 큐에 추가 및 등장 횟수 계산
		for(int i=0; i<k; i++) {
			addingElement = sushis.get(i);
			
			queue.add(addingElement);
			eatenSushiCount[addingElement]++;
		}
		
		//0번부터 (k-1)번 까지의 고유한 초밥 번호의 개수 파악
		HashSet<Integer> initialUniqueSushi = new HashSet<>(queue);
		uniqueSushiCount = initialUniqueSushi.size();
		
		//0번부터 (N-1번)까지의 초밥 번호를 삭제하고 k번부터 (k-1) 번까지 새로운 초밥을 추가하면서 고유한 초밥의 개수 파악 (쿠폰 번호 c 또한 고유한 초밥의 번호인지 고려)
		for(int i=0; i<N; i++) {
			addingElement = sushis.get((i+k) % N);					//k번부터 (k-1) 번까지 새로운 초밥 추가 (반복 도중 배열의 범위를 벗어나므로(0번 부터 다시 시작) %N 연산 취함)
			
			deletingElement = queue.pollFirst();					//큐의 앞에서 원소를 빼냄
			queue.offerLast(addingElement);							//큐의 뒤에 새로운 원소 추가
			
			//빼낸 원소의 등장횟수가 0이 되면
			if(--eatenSushiCount[deletingElement] == 0) {
				uniqueSushiCount--;									//고유한 초밥 번호의 개수 1 감소
			}
			
			//추가한 원소의 등장횟수가 추가 전 0"이었으면"
			if(eatenSushiCount[addingElement]++ == 0) {
				uniqueSushiCount++;									//고유한 초밥 번호의 개수 1 증가
			}
			
			//고유한 초밥 번호의 개수가 이전 정답 이상인 경우 (쿠폰 번호 c가 고유한 초밥 번호라면 정답 갱신 가능성 있음)
			if(uniqueSushiCount >= answer) {
				answer = uniqueSushiCount;							//정답 변수를 고유한 초밥 번호로 갱신
				
				//쿠폰 번호 c의 등장 횟수가 0이면 (고유한 초밥 번호이면)
				if(eatenSushiCount[c] == 0) {
					answer++;										//정답 변수 1 증가
				}
			}
		}
		
		//정답 출력
		System.out.println(answer);
	}

}
