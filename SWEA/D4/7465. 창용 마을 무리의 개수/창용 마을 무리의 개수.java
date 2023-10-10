import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * <pre>
 * SWEA 7465. 창용 마을 무리의 개수
 * 
 * 실행시간: 212ms (SWEA)
 * 메모리: 31,308 KB
 * 
 * Union-Find 또는 BFS 문제인 것 같지만 플로이드-워셜로 풀었습니다.
 * 
 * N명의 사람이 주어지고, 서로 아는 관계인 사람들을 모두 묶어서 하나의 무리라고 했을 때 총 몇 개의 무리가 존재하는지 구하는 문제입니다.
 * 
 * 1. 입력받을 때 무향 그래프로 고유한 값 (무리의 번호)을 부여합니다.
 * 2. 플로이드-워셜처럼 3중 for문을 사용합니다. 출발지와 경유지, 경유지와 도착지가 연결되어 있을 때 다음을 수행합니다.
 * 2-1. (경유지 필요 없이) 출발지와 도착지가 바로 연결되어 있으면 둘 중 작은 무리의 번호로 출발지, 도착지, 경유지의 번호를 갱신합니다.
 * 2-2. 출발지와 도착지가 바로 연결되어 있지 않으면 출발지와 경유지를 둘 중 작은 무리의 번호로 갱신합니다.
 * 3. 2중 for문을 사용해 고유(unique)한 저장된 무리의 번호만을 TreeSet에 저장합니다.
 * 4. N명의 사람 중 입력부에서 처리하지 않은 번호를 가진 사람의 수 만큼 정답 변수에 더해줍니다.
 * 예)
 * 1
 * 7 3
 * 1 3
 * 3 4
 * 6
 * 처럼 7명 중 (1, 3, 4, 6, 7)처럼 언급이 없는 (2, 5)번의 수(2명)를 더해줍니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());							//테스트 케이스의 수 T
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			int groupNo = 1;												//서로를 알고 있는 사람들에게 부여할 무리의 번호
			TreeSet<Integer> groupNoSet = new TreeSet<>();					//고유한 무리의 번호를 저장할 TreeSet
			TreeSet<Integer> aloneNoSet = new TreeSet<>();					//N명의 사람이 있지만 M개의 줄에서 언급이 되지 않은 사람들의 번호를 저장하는 TreeSet
			int a, b;
			
			int N = Integer.parseInt(st.nextToken());						//창용 마을에는 N명의 사람이 살고 있다.
			int M = Integer.parseInt(st.nextToken());						//이후 M개의 줄에 걸쳐서 서로를 알고 있는 두 사람의 번호가 주어진다.
			
			int[][] knowEachOther = new int[N][N];							//서로 알고 있으면 true, 아니면 false
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				groupNoSet.clear();
				
				a = Integer.parseInt(st.nextToken()) - 1;					//사람의 번호는 0번부터 시작 (인접행렬 처리)
				b = Integer.parseInt(st.nextToken()) - 1;					//사람의 번호는 0번부터 시작 (인접행렬 처리)
				
				knowEachOther[a][b] = knowEachOther[b][a] = groupNo++;		//고유한 무리의 번호 저장
			}
			
			for(int j=0; j<N; j++) {										//경유지
				for(int k=0; k<N; k++) {									//출발지
					if(j==k) continue;										//경유지와 출발지 같으면 스킵
					
					for(int l=0; l<N; l++) {								//도착지
						if(j==l || k==l) continue;							//경유지와 도착지가 같거나 출발지와 도착지가 같으면 스킵
						
						//출발지와 경유지가 연결되어 있고 경유지와 도착지가 연결되어 있으면 출발지와 경유지는 연결된 것임
						if(knowEachOther[k][j] > 0 && knowEachOther[j][l] > 0) {
							//출발지와 경유지 중 더 작은 무리의 번호 계산
							int candidate = Math.min(knowEachOther[k][j], knowEachOther[j][l]);
							
							if(knowEachOther[k][l] > 0) {					//출발지와 도착지가 직접 연결된 경우
								knowEachOther[k][l] = Math.min(knowEachOther[k][l], candidate);	//자기 자신과 경유지를 거친 무리의 번호 중 작은 값 대입
							} else {										//출발지와 도착지가 직접 연결되지 않은 경우
								knowEachOther[k][l] = candidate;			//출발지와 경유지 중 작은 무리의 번호 대입
							}
							//출발지, 경유지의 무리의 번호를 둘 중 작은 값(작은 무리의 번호)으로 갱신
							knowEachOther[k][j] = knowEachOther[j][l] = candidate;
						}
					}
				}
			}
			
			//N명의 사람 중 M개의 줄에서 언급이 되지 않은 사람들의 번호를 제거해나가기 위한 루프 (N명의 사람의 번호의 수 만큼 저장)
			for(int j=0; j<N; j++) {
				aloneNoSet.add(j);
			}
			
			//고유한 무리의 번호를 추출하기 위한 루프 (+N명의 사람이 있지만 M개의 줄에서 언급이 되지 않은 사람들의 번호 추출)
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					if(j==k) continue;
					
					if(knowEachOther[j][k] != 0) {
						groupNoSet.add(knowEachOther[j][k]);
						aloneNoSet.remove(Integer.valueOf(j));
					}
				}
			}
			
			sb.append("#" + i + " " + (groupNoSet.size() + aloneNoSet.size()) + "\n");
		}
		
		System.out.println(sb.toString());
	}

}