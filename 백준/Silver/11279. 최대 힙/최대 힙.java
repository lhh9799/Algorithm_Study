import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());	//최대 힙, 입력되는 자연수는 231보다 작다. -> Integer
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());	//연산의 개수 N(1 ≤ N ≤ 100,000)
		
		for(int i=0; i<N; i++) {
			int x = Integer.parseInt(br.readLine());
			
			//1. x가 자연수라면 배열에 x라는 값을 넣는(추가하는) 연산
			if(x != 0) {
				heap.offer(x);
			} else {	//2. x가 0이라면 배열에서 가장 큰 값을 출력하고 그 값을 배열에서 제거
				if(heap.isEmpty()) {
					sb.append("0" + "\n");
				} else {
					sb.append(heap.poll() + "\n");
				}
			}
		}
		
		System.out.println(sb.toString());
	}

}