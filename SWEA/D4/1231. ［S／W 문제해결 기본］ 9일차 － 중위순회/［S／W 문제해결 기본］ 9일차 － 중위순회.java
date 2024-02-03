import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 1231. [S/W 문제해결 기본] 9일차 - 중위순회
 * 
 * 실행시간: 104ms (SWEA)
 * 메모리: 18,412 KB
 * 
 * 트리를 중위 순회한 결과를 묻는 문제입니다.
 * 
 * 탐색하려는 정점의 왼쪽 자식, 자신, 오른쪽 자식 순으로 재귀를 이용해 탐색합니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static Node[] graph;
	static StringBuilder sb = new StringBuilder();
	
	static class Node {
		public char value;
		public int left, right;
		
		Node() {}
	}
	
	static void inOrder(int vertex) {
		if(graph[vertex].left != 0) {
			inOrder(graph[vertex].left);
		}
		
		sb.append(graph[vertex].value);
		
		if(graph[vertex].right != 0) {
			inOrder(graph[vertex].right);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		for(int i=1; i<=10; i++) {	//총 10개의 테스트 케이스
			int N = Integer.parseInt(br.readLine());
			graph = new Node[N+1];
			
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				int vertex = Integer.parseInt(st.nextToken());
				char value = st.nextToken().charAt(0);
				int left = 0, right = 0;
				
				if(st.hasMoreTokens()) {
					left = Integer.parseInt(st.nextToken());
				}
				
				if(st.hasMoreTokens()) {
					right = Integer.parseInt(st.nextToken());
				}
				
				Node node = new Node();
				node.value = value;
				node.left = left;
				node.right = right;
				
				graph[vertex] = node;
			}
			
			sb.append("#" + i + " ");
			inOrder(1);
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

}