import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 1248. [S/W 문제해결 응용] 3일차 - 공통조상
 * 
 * 실행시간: 132ms (SWEA)
 * 메모리: 28,464 KB
 * 
 * 이진 트리에서 임의의 두 정점의 가장 가까운 공통 조상을 찾고, 그 정점을 루트로 하는 서브 트리의 크기를 구하는 문제입니다.
 * 
 * 1. 임의의 두 정점 중 작은 번호를 갖는 정점의 조상을 먼저 찾고나서 큰 번호의 정점의 조상을 찾았습니다.
 * 2. 서브 트리의 크기는 DFS로 구합니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static int[] parentList;									//자신의 부모의 번호
	static int[] leftChildList;									//왼쪽 자식 노드의 번호
	static int[] rightChildList;								//오른쪽 자식 노드의 번호
	static int size = 0;										//공통 조상의 서브 트리의 크기
	
	/**
	 * DFS로 트리 순회 (트리의 크기 계산)
	 * @param start: 순회를 시작할 루트 노드의 번호
	 */
	static void calcSubTreeSize(int start) {
		size++;													//서브 트리의 크기 증가 (자신의 크기 추가)
		
		if(leftChildList[start] != 0) {							//왼쪽 자식노드가 있으면
			calcSubTreeSize(leftChildList[start]);				//왼쪽 자식노드 탐색
		}
		
		if(rightChildList[start] != 0) {						//오른쪽 자식노드가 있으면
			calcSubTreeSize(rightChildList[start]);				//오른쪽 자식노드 탐색
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());				//테스트케이스의 수 T
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			size = 0;											//서브 트리의 크기 초기화
			
			int V = Integer.parseInt(st.nextToken());			//정점의 개수 V
			int E = Integer.parseInt(st.nextToken());			//간선의 개수 E
			int target1 = Integer.parseInt(st.nextToken());		//공통 조상을 찾는 정점의 번호 1
			int target2 = Integer.parseInt(st.nextToken());		//공통 조상을 찾는 정점의 번호 2
			parentList = new int[V+1];							//부모의 번호를 저장하는 배열
			leftChildList = new int[V+1];						//왼쪽 자식의 번호를 저장하는 배열
			rightChildList = new int[V+1];						//오른쪽 자식의 번호를 저장하는 배열
			
			int commonAncestor = 0;
			
			st = new StringTokenizer(br.readLine());			//각 케이스의 두 번째 줄에는 E개 간선이 나열된다. 간선은 항상 "부모 자식" 순서로 표기된다.
			for(int j=0; j<E; j++) {
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				
				parentList[child] = parent;						//부모의 번호 저장
				
				if(leftChildList[parent] == 0) {				//왼쪽 자식이 연결되어 있지 않으면
					leftChildList[parent] = child;				//왼쪽 자식으로 추가
				} else {										//그렇지 않으면
					rightChildList[parent] = child;				//오른쪽 자식으로 추가
				}
			}
			
			//target1이 target2보다 항상 작도록 변경 (노드의 번호가 작으면 더 위에 있을 것이라는 가정)
			int temp = Math.max(target1, target2);
			target1 = Math.min(target1, target2);
			target2 = temp;
			
			ArrayList<Integer> target1AncestorList = new ArrayList<>();
			//레벨이 낮은 정점 먼저 탐색 (조상에 자신 포함 (target2가 target1의 자식일 수 있음))
			for(int j=target1; j!=0; j=parentList[j]) {
				target1AncestorList.add(j);
			}
			
			//레벨이 높은 정점 탐색
			for(int j=target2; j!=0; j=parentList[j]) {
				if(target1AncestorList.contains(Integer.valueOf(j))) {
					commonAncestor = j;
					
					break;
				}
			}
			
			calcSubTreeSize(commonAncestor);					//공통 조상의 번호를 루트로 하는 서브 트리의 크기 계산
			sb.append("#" + i + " " + commonAncestor + " " + size + "\n");
		}
		
		System.out.println(sb.toString());
	}

}
