import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N =  Integer.parseInt(st.nextToken());	//회전 초밥 벨트에 놓인 접시의 수 N
		int d =  Integer.parseInt(st.nextToken());	//초밥의 가짓수 d
		int k =  Integer.parseInt(st.nextToken());	//연속해서 먹는 접시의 수 k
		int c =  Integer.parseInt(st.nextToken());	//쿠폰 번호 c
		
		ArrayList<Integer> sushis = new ArrayList<>(N);
		int[] eatenSushiCount = new int[d+1];
		ArrayDeque<Integer> queue = new ArrayDeque<>(k);
		int addingElement = 0;
		int deletingElement = 0;
		int uniqueSushiCount = 0;
		int answer = Integer.MIN_VALUE;
		
		for(int i=0; i<N; i++) {
			sushis.add(Integer.parseInt(br.readLine()));
		}
		
		for(int i=0; i<k; i++) {
			addingElement = sushis.get(i);
			
			queue.add(addingElement);
			eatenSushiCount[addingElement]++;
		}
		
		HashSet<Integer> initialUniqueSushi = new HashSet<>(queue);
		uniqueSushiCount = initialUniqueSushi.size();
		
		for(int i=0; i<N; i++) {
			addingElement = sushis.get((i+k) % N);
			
			deletingElement = queue.pollFirst();
			queue.offerLast(addingElement);
			
			if(--eatenSushiCount[deletingElement] == 0) {
				uniqueSushiCount--;
			}
			
			if(eatenSushiCount[addingElement]++ == 0) {
				uniqueSushiCount++;
			}
			
			if(uniqueSushiCount >= answer) {
				answer = uniqueSushiCount;
				
				if(eatenSushiCount[c] == 0) {
					answer++;
				}
			}
		}
		
		System.out.println(answer);
	}

}
