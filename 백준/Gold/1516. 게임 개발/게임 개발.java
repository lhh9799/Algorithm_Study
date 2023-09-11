import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Building implements Comparable<Building> {
	int index, time, prevMaxTime = 0;
	ArrayList<Integer> postBuildingIndex = new ArrayList<>();
	
	Building() {}
	
	Building(int index) {
		this.index = index;
	}

	//PriorityQueue (건물 짓는 시간) 오름차순 정렬
	@Override
	public int compareTo(Building o) {
		return Integer.compare(this.time, o.time);
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
		PriorityQueue<Building> pq = new PriorityQueue<>();
		
		for(int i=0; i<N; i++) {
			buildingList[i] = new Building(i);
		}
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			buildingList[i].time = Integer.parseInt(st.nextToken());	//각 건물을 짓는데 걸리는 시간 저장
			
			for(int j = Integer.parseInt(st.nextToken()); j != -1; j = Integer.parseInt(st.nextToken())) {
				inorder[i]++;
				buildingList[j-1].postBuildingIndex.add(i);				//이 건물을 지어야 지을 수 있는 (다음 건설 가능한) 건물들의 번호 저장
			}
			
			//진입차수가 0인 빌딩은 PriorityQueue에 추가
			if(inorder[i] == 0) {	//모든 건물을 짓는 것이 가능한 입력만 주어지므로.. -> 진입차수가 0인 건물이 존재함
				pq.add(buildingList[i]);
			}
		}
		
		while(!pq.isEmpty()) {
			Building pollItem = pq.poll();								//진입차수가 0인 건물을 PriorityQueue에서 빼냄
			pollItem.time += pollItem.prevMaxTime;						//(이 건물을 짓기 전 건물들의 건설 시간 중 최댓값 + 현재 건물을 짓는데 걸리는 시간)을 저장
			
			for(int nextBuildingIndex : pollItem.postBuildingIndex) {	//이 건물을 지은 뒤 지을 수 있는 건물들을 순회하면서
				inorder[nextBuildingIndex]--;							//진입차수 감소 (선행 건물을 지었으므로)
				//이 건물을 짓기 전 건물들의 건설 시간 중 최댓값을 저장
				buildingList[nextBuildingIndex].prevMaxTime = Math.max(buildingList[nextBuildingIndex].prevMaxTime, pollItem.time);
				
				if(inorder[nextBuildingIndex] == 0) {					//진입차수가 0이면
					pq.add(buildingList[nextBuildingIndex]);			//PriorityQueue에 추가
				}
			}
		}
		
		for(int i=0; i<N; i++) {
			sb.append(buildingList[i].time + "\n");
		}
		
		System.out.println(sb.toString());
	}

}