import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Solution {
	
	static Node[] graph;
	static int subtreeSize = 0;
	
	static class Node {
		int parent;
		HashSet<Integer> child = new HashSet<>();
		
		Node() {}
	}
	
	static void calculateSubtreeSize(int vertex) {
		subtreeSize++;
		
		for(int child : graph[vertex].child) {
			calculateSubtreeSize(child);
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());					//테스트케이스의 수
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			
			int V = Integer.parseInt(st.nextToken());				//정점의 개수 V(10 ≤ V ≤ 10000)
			int E = Integer.parseInt(st.nextToken());				//간선의 개수 E
			int target1 = Integer.parseInt(st.nextToken());			//공통 조상을 찾는 정점 번호 1
			int target2 = Integer.parseInt(st.nextToken());			//공통 조상을 찾는 정점 번호 2
			graph = new Node[V+1];									//그래프
			ArrayList<Integer> ancestor1 = new ArrayList<>();
			subtreeSize = 0;
			int commonAncestor = 0;
			
			for(int j=0; j<V+1; j++) {
				graph[j] = new Node();
			}
			
			//E개의 간선 입력받음 (간선은 항상 "부모 자식" 순서로 표기된다.)
			st = new StringTokenizer(br.readLine());
			
			while(st.hasMoreTokens()) {
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				
				graph[parent].child.add(child);
				graph[child].parent = parent;
			}
			
			//target1 (공통 조상을 찾는 정점 번호 1)의 수를 작게 함 (그래프에서 target2보다 더 높은 곳에 위치함 -> target2에 비해 조상의 수가 적음 -> 먼저 탐색 (안쪽 for문에서 사용하면 횟수가 줄어들 것이라 생각))
			int[] target = {target1, target2};
			Arrays.sort(target);
			
			target1 = target[0];
			target2 = target[1];
			
			//target1의 조상 찾기
			for(int parent = graph[target1].parent; parent != 0; parent = graph[parent].parent) {
				ancestor1.add(parent);
			}
			
			//target2의 조상 찾기
			for(int parent = graph[target2].parent; parent != 0; parent = graph[parent].parent) {
				if(ancestor1.contains(parent)) {
					sb.append("#" + i + " " + parent + " ");
					commonAncestor = parent;
					break;
				}
			}
			
			calculateSubtreeSize(commonAncestor);
			
			sb.append(subtreeSize + "\n");
		}
		System.out.println(sb.toString());
	}

}