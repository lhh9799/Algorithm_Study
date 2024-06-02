import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
	
	final static int BOARD_SIZE = 8;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		HashMap<String, int[]> directionMap = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		directionMap.put("R", new int[] {1, 0});		//R : 한 칸 오른쪽으로
		directionMap.put("L", new int[] {-1, 0});		//L : 한 칸 왼쪽으로
		directionMap.put("B", new int[] {0, -1});		//B : 한 칸 아래로
		directionMap.put("T", new int[] {0, 1});		//T : 한 칸 위로
		directionMap.put("RT", new int[] {1, 1});		//RT : 오른쪽 위 대각선으로
		directionMap.put("LT", new int[] {-1, 1});		//LT : 왼쪽 위 대각선으로
		directionMap.put("RB", new int[] {1, -1});		//RB : 오른쪽 아래 대각선으로
		directionMap.put("LB", new int[] {-1, -1});		//LB : 왼쪽 아래 대각선으로
		
		st = new StringTokenizer(br.readLine());
		
		String king = st.nextToken();					//킹의 위치
		String stone = st.nextToken();					//돌의 위치
		int times = Integer.parseInt(st.nextToken());	//움직이는 횟수 N
		
		int kingPositionX = king.charAt(0) - 'A' + 1;
		int kingPositionY = Integer.parseInt(king.substring(1));
		
		int stonePositionX = stone.charAt(0) - 'A' + 1;
		int stonePositionY = Integer.parseInt(stone.substring(1));
		
		for(int i=0; i<times; i++) {
			String direction = br.readLine();
			
			int[] weight = directionMap.get(direction);
			int newKingPositionX = kingPositionX + weight[0];
			int newKingPositionY = kingPositionY + weight[1];
			int newStonePositionX = stonePositionX, newStonePositionY = stonePositionY;
			
			//돌과 같은 곳으로 이동할 때는, 돌을 킹이 움직인 방향과 같은 방향으로 한 칸 이동시킨다.
			if(newKingPositionX == stonePositionX && newKingPositionY == stonePositionY) {
				newStonePositionX = stonePositionX + weight[0];
				newStonePositionY = stonePositionY + weight[1];
			}
			
			if(newKingPositionX > 0 && newKingPositionX <= BOARD_SIZE && newKingPositionY > 0 && newKingPositionY <= BOARD_SIZE && newStonePositionX > 0 && newStonePositionX <= BOARD_SIZE && newStonePositionY > 0 && newStonePositionY <= BOARD_SIZE) {
				kingPositionX = newKingPositionX;
				kingPositionY = newKingPositionY;
				stonePositionX = newStonePositionX;
				stonePositionY = newStonePositionY;
			}
		}
		
		sb.append((char) ('A' - 1 + kingPositionX));
		sb.append(kingPositionY);
		sb.append("\n");
		sb.append((char) ('A' - 1 + stonePositionX));
		sb.append(stonePositionY);
		
		System.out.println(sb.toString());
	}

}