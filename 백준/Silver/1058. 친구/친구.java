import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());		//사람의 수 N
//		boolean[][][] friend = new boolean[N][N][2];	//[][][0]: 친구, [][][1]: 2-친구
		boolean[][] friend = new boolean[N][N];
		char[] line = new char[N];
		int answer = 0;
		
		for(int i=0; i<N; i++) {
			line = br.readLine().toCharArray();
			
			for(int j=0; j<N; j++) {
				if(line[j] == 'Y') {
					friend[i][j] = friend[j][i] = true;
				}
			}
		}
		
		//플로이드-워셜
		for(int i=0; i<N; i++) {			//출발지
			int tempNumberOfFriends = 0;
			
			for(int j=0; j<N; j++) {		//도착지
				if(i == j) continue;
				
				if(friend[i][j]) {
					tempNumberOfFriends++;
					
					continue;
				}
				
				for(int k=0; k<N; k++) {	//경유지
					if(k == i || k == j) continue;
					
					if(friend[i][k] && friend[k][j]) {
						tempNumberOfFriends++;
						
						break;
					}
				}
			}
			
			answer = Math.max(answer, tempNumberOfFriends);
		}
		
		System.out.println(answer);
	}

}