import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	Scanner sc =new Scanner(System.in);
		
			int N = sc.nextInt(); //node 수 입력받기
			
			// 이차원 배열 생성
			
			int[][] arr = new int[N][N];
			
			
			//배열값 입력받기 [r][c] = r 에서 시작해서 c 까지의 거리
			for(int r = 0 ; r < N ; r++) {
				for(int c = 0 ; c< N ;c ++) {			
					if(r==c ) continue;	// 자기자신 부터 자기 자신까지의 거리는 0입니다..
					
					// [r][c]= 0 이다? 아직 최단 경로를 모른다 
					// 플루이드 워셜로 최단 경로 구해줘야하기에 적당히 큰 수 100000으로 설정
					if(arr[r][c] == 0)
					arr[r][c] = 100000;
				}
			}
			
			int M = sc.nextInt();
			
			for(int i = 0 ; i < M ;i++) {
				arr[sc.nextInt()-1][sc.nextInt()-1] = 1;
			}
			
			
			//플루이드 워셜 알고리즘 수행 경출도
			for(int k = 0 ; k < N ; k++) {
				for(int i = 0 ; i < N ;i++) {
					if(i == k) continue; // 경로와 출발값이 같다면 continue;
					for(int j = 0 ; j < N ;j++) {
						// 거쳐오는 경로와 도착 점 이 같거나 출발과 도착이 같다면 continue; 
						if(j == k || j == i) continue; 
						arr[i][j] = Math.min(arr[i][k]+ arr[k][j], arr[i][j]);
						
					}
				}
			}
			
			
			int result = 0 ;
			for(int i = 0 ; i < N ; i++) {
				int count = 0 ;
				for(int j = 0 ; j < N ;j++) {
					if(arr[i][j] != 0 && arr[i][j] != 100000) count++;
					if(arr[j][i] != 0 && arr[j][i] != 100000) count++;
					
					
				}
				
				if(count >= N-1) result++;
			
			}
			
			System.out.println(result);
			
		}
	

	}