import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * <pre>
 * 백준 1516번 게임 개발
 * https://www.acmicpc.net/problem/1516
 * 
 * 실행시간: 220ms (백준)
 * 메모리: 19,672 KB
 * 
 * 위상정렬(Topological Sort) 문제입니다.
 * 
 * 어떤 건물을 짓기 위해서 다른 건물을 먼저 지어야할 수 있습니다.
 * 건물을 짓기 전 먼저 지어야 하는 건물의 번호 대신 다음 지을 수 있는 건물의 번호를 저장해서 건물이 지어졌을 때 (PriorityQueue에서 삭제될 때) 다음 건물들의 진입차수를 낮추고 그 진입차수가 0이 되면 PriorityQueue에 넣습니다.
 * 
 * 한 건물이 건설이 완료되는데까지 걸리는 시간은 해당 건물을 건설하기 직전 건물들의 건설 시간 중 최댓값 + 해당 건물의 건설 시간입니다.
 * </pre>
 * 
 *  @author 이현호
 */

class Building {
	int index, time, prevMaxTime = 0;							//index: 건물의 번호, time: 해당 건물을 짓는데 걸리는 시간, prevMaxTime: 해당 건물을 짓기 전까지 걸리는 시간 (이전 건물들이 모두 건설될 때까지 걸리는 시간)
	ArrayList<Integer> postBuildingIndex = new ArrayList<>();	//해당 건물의 건설이 완료되었을 때 다음 착공할 수 있는 건물의 번호를 저장하는 ArrayList
	
	Building() {}
	
	Building(int index) {
		this.index = index;
	}
}

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		int N = Integer.parseInt(br.readLine());
		Building[] buildingList = new Building[N];
		int[] inorder = new int[N];
		ArrayDeque<Building> queue = new ArrayDeque<>();
		
		for(int i=0; i<N; i++) {
			buildingList[i] = new Building(i);
		}
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			buildingList[i].time = Integer.parseInt(st.nextToken());	//각 건물을 짓는데 걸리는 시간 저장
			
			for(int j = Integer.parseInt(st.nextToken()); j != -1; j = Integer.parseInt(st.nextToken())) {
				inorder[i]++;
				buildingList[j-1].postBuildingIndex.add(i);				//이 건물을 지어야 지을 수 있는 (다음에 건설 가능한) 건물들의 번호 저장
			}
			
			//진입차수가 0인 빌딩은 PriorityQueue에 추가
			if(inorder[i] == 0) {										//모든 건물을 짓는 것이 가능한 입력만 주어지므로.. -> 진입차수가 0인 건물이 존재함
				queue.add(buildingList[i]);
			}
		}
		
		while(!queue.isEmpty()) {
			Building pollItem = queue.poll();								//진입차수가 0인 건물을 PriorityQueue에서 빼냄
			pollItem.time += pollItem.prevMaxTime;						//(이 건물을 짓기 전 건물들의 건설 시간 중 최댓값 + 현재 건물을 짓는데 걸리는 시간)을 저장
			
			for(int nextBuildingIndex : pollItem.postBuildingIndex) {	//이 건물을 지은 뒤 지을 수 있는 건물들을 순회하면서
				inorder[nextBuildingIndex]--;							//진입차수 감소 (선행 건물을 지었으므로)
				//이 건물을 짓기 전 건물들의 건설 시간 중 최댓값을 저장
				buildingList[nextBuildingIndex].prevMaxTime = Math.max(buildingList[nextBuildingIndex].prevMaxTime, pollItem.time);
				
				if(inorder[nextBuildingIndex] == 0) {					//진입차수가 0이면
					queue.add(buildingList[nextBuildingIndex]);			//PriorityQueue에 추가
				}
			}
		}
		
		for(int i=0; i<N; i++) {
			sb.append(buildingList[i].time + "\n");
		}
		
		System.out.println(sb.toString());
	}

}