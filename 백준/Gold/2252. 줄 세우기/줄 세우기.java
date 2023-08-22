import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int s1 = 0, s2 = 0;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		ArrayList<ArrayList<Integer>> compareInfo = new ArrayList<ArrayList<Integer>>(N);
		int[] studentDegreeArray = new int[N];		//진입차수 저장
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<N; i++) {
			compareInfo.add(new ArrayList<Integer>());
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			s1 = Integer.parseInt(st.nextToken());
			s2 = Integer.parseInt(st.nextToken());
			
			compareInfo.get(s1-1).add(s2-1);
			studentDegreeArray[s2-1]++;
		}
		
		for(int i=0; i<N; i++) {
			if(studentDegreeArray[i] == 0) {		//진입차수가 0이면
				queue.offerLast(i);					//큐 삽입
				studentDegreeArray[i] = -1;			//방문 표시
				
				for(int j : compareInfo.get(i)) {	//큐에서 빼낸 원소가 가리키는 학생들의
					studentDegreeArray[j]--;		//진입차수 감소
				}
			}
		}
		
		while(!queue.isEmpty()) {
			sb.append(queue.pollFirst() + 1).append(" ");
			
			for(int i=0; i<N; i++) {
				if(studentDegreeArray[i] == 0) {		//진입차수가 0이면
					queue.offerLast(i);					//큐 삽입
					studentDegreeArray[i] = -1;			//방문 표시
					
					for(int j : compareInfo.get(i)) {	//큐에서 빼낸 원소가 가리키는 학생들의
						studentDegreeArray[j]--;		//진입차수 감소
					}
				}
			}
		}
		
		System.out.println(sb.toString());
	}

}