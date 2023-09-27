import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 1263. [S/W 문제해결 응용] 8일차 - 사람 네트워크2
 * 
 * 실행시간: 3,238ms (SWEA)
 * 메모리: 98,504 KB
 * 
 * 플로이드-워셜 문제입니다.
 * 
 * 다른 정점들로부터 한 정점까지의 비용의 합을 Closeness Centrality(CC):Closeness 라고 정의했을 때, CC의 최솟값을 구하는 문제입니다.
 * 플로이드-워셜 알고리즘을 적용하고 정점 별 CC를 구해 (2차원 배열 행의 합) 최솟값을 출력합니다.
 * 
 * </pre>
 * 
 *  @author 이현호
 */

public class Solution {
 
    static int numberOfUsers = 0;						//사람 네트워크의 사용자 수
    static int[][] network;								//사람 네트워크 인접행렬
    
    static void floyd() {
        for(int i=0; i<numberOfUsers; i++) {			//i: 경유지
            for(int j=0; j<numberOfUsers; j++) {		//j: 출발지
                if(i == j) continue;
                
                for(int k=0; k<numberOfUsers; k++) {	//k: 목적지
                    if(i == k || j == k) continue;		//출발지와 목적지가 같으면 '가까운 정도' 갱신 건너뜀 (0으로 갱신됨)
                    
                    //'가까운 정도'의 최솟값은 직접 연결된 값과 다른 한 사용자를 경유한 값 중 최솟값임
                    network[j][k] = Math.min(network[j][k], network[j][i] + network[i][k]);
                }
            }
        }
    }
    
    //CC의 최솟값을 리턴하는 메소드 (행의 합 계산 후 리턴)
    static int findMin() {
        int minCC = Integer.MAX_VALUE;
        
        for(int i=0; i<numberOfUsers; i++) {
            int tempSumCC = 0;
            
            for(int j=0; j<numberOfUsers; j++) {
                tempSumCC += network[i][j];
            }
            minCC = Math.min(tempSumCC, minCC);
        }
        
        return minCC;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = null;
        final int max = 1000 * 1000;    						//사람 네트워크의 최대 사용자 수는 1,000 이하이므로 '가까운 정도'는 1000^2 이하이다. => 최댓값
        
        
        int T = Integer.parseInt(br.readLine());				//테스트 케이스의 수
        
        for(int i=1; i<=T; i++) {
            st = new StringTokenizer(br.readLine());
            numberOfUsers = Integer.parseInt(st.nextToken());	//사람 네트워크의 사용자 수
            network = new int[numberOfUsers][numberOfUsers];	//사람 네트워크 인접행렬
            
            for(int j=0; j<numberOfUsers; j++) {
                for(int k=0; k<numberOfUsers; k++) {
                    network[j][k] = Integer.parseInt(st.nextToken());
                    
                    //값이 0 => 연결되지 않았음을 의미 => 최댓값으로 초기화
                    if(network[j][k] == 0) {
                        network[j][k] = max;
                    }
                }
            }
            
            floyd();											//플로이드-워셜 알고리즘
            
            //자기 자신은 최댓값으로 초기화되어있으므로 행의 합에서 max만큼 감소
            sb.append("#" + i + " " + (findMin() - max) + "\n");
        }
        
        System.out.println(sb.toString());
    }

}