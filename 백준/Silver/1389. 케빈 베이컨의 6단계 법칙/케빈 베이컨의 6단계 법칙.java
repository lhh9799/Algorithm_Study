import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int minimumBaconNumber = Integer.MAX_VALUE;			//가장 작은 케빈 베이컨의 수를 저장하는 변수
		int minimumBaconNumberOwner = Integer.MAX_VALUE;	//정답을 저장하는 변수 (케빈 베이컨의 수가 가장 작은 사람의 번호)
		
		st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());			//유저의 수 N (2 ≤ N ≤ 100)
		int M = Integer.parseInt(st.nextToken());			//친구 관계의 수 M (1 ≤ M ≤ 5,000)
		
		int[][] isFriend = new int[N+1][N+1];
		int[] baconNumber = new int[N+1];
		
		//1. 초기화
		//1-1. 친구 관계 최댓값으로 초기화
		for(int i=1; i<N+1; i++) {
			for(int j=1; j<N+1; j++) {
				isFriend[i][j] = Integer.MAX_VALUE;
			}
		}
		
		//1-2. 자기 자신 출발, 자기 자신 도착은 0으로 친구 관계 초기화 (기존: Integer.MAX_VALUE)
		for(int i=1; i<N+1; i++) {
			isFriend[i][i] = 0;
		}
		
		//2. 친구 관계 입력받음
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int friendA = Integer.parseInt(st.nextToken());
			int friendB = Integer.parseInt(st.nextToken());
			
			isFriend[friendA][friendB] = isFriend[friendB][friendA] = 1;	//친구 관계 갱신
		}
		
		//3. 친구 관계 갱신
		for(int j=1; j<N+1; j++) {			//경유지
			for(int i=1; i<N+1; i++) {		//출발지
				for(int k=1; k<N+1; k++) {	//도착지
					//출발지에서 경유지, 경유지에서 도착지가 연결된 경우 친구 관계 갱신
					if(isFriend[i][j] != Integer.MAX_VALUE && isFriend[j][k] != Integer.MAX_VALUE) {
						isFriend[i][k] = Math.min(isFriend[i][k], isFriend[i][j] + isFriend[j][k]);
					}
				}
			}
		}
		
		//4. 케빈 베이컨의 수 계산 및 케빈 베이컨의 수가 가장 작은 사람 갱신
		for(int i=1; i<N+1; i++) {
			//4-1. 케빈 베이컨의 수 계산
			for(int j=1; j<N+1; j++) {
				baconNumber[i] += isFriend[i][j];
			}
			
			//4-2. 최소 케빈 베이컨의 수를 찾은 경우 갱신
			if(minimumBaconNumber > baconNumber[i]) {
				minimumBaconNumber = baconNumber[i];
				minimumBaconNumberOwner = i;
			//4-3. 현재 유저의 베이컨의 수와 최소 베이컨의 수가 같고 그 사람의 번호가 현재 최소 베이컨의 수를 가진 유저의 번호보다 더 작은 경우
			} else if(minimumBaconNumber == baconNumber[i] && minimumBaconNumberOwner > i) {
				minimumBaconNumber = baconNumber[i];
				minimumBaconNumberOwner = i;
			}
		}
		
		System.out.println(minimumBaconNumberOwner);
	}

}