import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static void clear2DArray(int[][] array) {
		for(int i=0; i<array.length; i++) {
			Arrays.fill(array[i], 0);
		}
	}
	
	static int solution(int N, int L, int R, int[][] population) {
		int answer = 0;										//인구 이동 지속 일(日)
		int[][] union = new int[N][N];						//국가 별 연합의 번호 저장
		ArrayDeque<int[]> queue = new ArrayDeque<>();		//탐색할 국가의 번호를 저장하는 큐 (연합 확장)
		boolean endCondition = true;						//인구 이동이 되었는지 판단하기 위한 변수
		
		//상, 하, 좌, 우
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		
		int[] populationSumByUnion = new int[N * N + 1];	//연합 별 총 인원 수 (인덱스 1번 부터 시작)
		int[] numberOfJoinedNation = new int[N * N + 1];	//연합에 가입한 국가의 수 (인덱스 1번 부터 시작)
		
		for(;; answer++) {
			int unionNo = 1;								//연합의 번호는 1 부터 시작 (0: 연합이 없음)
			clear2DArray(union);							//국가 별 연합의 번호 저장 배열 초기화
			queue.clear();									//방문해야 하는 국가 초기화
			Arrays.fill(populationSumByUnion, 0);			//연합 별 총 인원 수 초기화
			Arrays.fill(numberOfJoinedNation, 0);			//연합에 가입한 국가의 수 초기화
			endCondition = true;
			
			//1. 국경선을 공유하는 두 나라 중 인구 차이가 L명 이상, R명 이하인 국가가 있는지 검사
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(union[i][j] != 0) continue;						//이미 연합에 가입된 경우 스킵
					
					queue.add(new int[] {i, j});						//방문해야 할 국가에 추가
					union[i][j] = unionNo;								//해당 국가(i행 j열)에 연합 번호 부여
					populationSumByUnion[unionNo] += population[i][j];	//해당 연합의 총 인원 수 증가
					numberOfJoinedNation[unionNo]++;					//연합에 가입한 국가의 수 증가
					
					while(!queue.isEmpty()) {
						int startX = queue.peek()[0];
						int startY = queue.peek()[1];
						queue.poll();
						
						for(int k=0; k<4; k++) {
							int x = startX + dx[k];
							int y = startY + dy[k];
							
							//배열의 범위를 벗어나지 않고 연합에 가입하지 않은 국가인 경우
							if(x >= 0 && x < N && y >= 0 && y < N && union[x][y] == 0) {
								int gap = Math.abs(population[startX][startY] - population[x][y]);
								
								//인구 수 차이 계산
								if(gap >= L && gap <= R) {
									queue.add(new int[] {x, y});
									union[x][y] = unionNo;
									populationSumByUnion[unionNo] += population[x][y];
									numberOfJoinedNation[unionNo]++;
									endCondition = false;
								}
							}
						}
					}
					unionNo++;
				}
			}
			
			//2-1. 큐에 국가가 추가된 적이 없는 경우 (연합에 새로운 국가 추가 X) => 인구 이동은 멈춤 => 정답 리턴
			if(endCondition) {
				return answer;
			}
			
			//2-2. 위의 조건에 의해 열어야하는 국경선이 모두 열렸다면, 인구 이동을 시작한다.
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					unionNo = union[i][j];			//국가가 가입한 연합의 번호를 가져와
					//연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)가 된다. 편의상 소수점은 버린다.
					population[i][j] = populationSumByUnion[unionNo] / numberOfJoinedNation[unionNo];
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//땅의 길이 (크기: N×N)
		int L = Integer.parseInt(st.nextToken());	//국경선을 여는 인구 차이의 기준 - 최소 (이상)
		int R = Integer.parseInt(st.nextToken());	//국경선을 여는 인구 차이의 기준 - 최대 (이하)
		int[][] population = new int[N][N];			//땅 (인구 수 저장)
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<N; j++) {
				population[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(solution(N, L, R, population));
	}

}