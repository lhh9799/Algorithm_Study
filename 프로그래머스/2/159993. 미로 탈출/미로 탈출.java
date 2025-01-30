import java.util.ArrayDeque;

class Solution {
	//상, 하, 좌, 우
	int[] dx = {-1, 1, 0, 0};
	int[] dy = {0, 0, -1, 1};
	
	public int[] findCoord(String[] maps, char c) {
        for(int i=0; i<maps.length; i++) {
        	for(int j=0; j<maps[i].length(); j++) {
        		if(maps[i].charAt(j) == c) {
        			return new int[] {i, j};
        		}
        	}
        }
        
        //Should never reach here
        return new int[] {-1, -1};
	}
	
	public int bfs(String[] maps, int startX, int startY, int targetX, int targetY) {
		boolean[][] visited = new boolean[maps.length][maps[0].length()];
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] {startX, startY, 0});
		visited[startX][startY] = true;
		int[] currentCoordWithTime;
		
		while(!queue.isEmpty()) {
			currentCoordWithTime = queue.pollFirst();
			
			if(currentCoordWithTime[0] == targetX && currentCoordWithTime[1] == targetY) {
				return currentCoordWithTime[2];
			}
			
			for(int i=0; i<dx.length; i++) {
				for(int j=0; j<dy.length; j++) {
					int[] nextCoordWithTime = new int[3];
					
					nextCoordWithTime[0] = currentCoordWithTime[0] + dx[i];
					nextCoordWithTime[1] = currentCoordWithTime[1] + dy[i];
					nextCoordWithTime[2] = currentCoordWithTime[2] + 1;
					
					if(nextCoordWithTime[0] >= 0 && nextCoordWithTime[0] < maps.length && nextCoordWithTime[1] >= 0 && nextCoordWithTime[1] < maps[0].length()) {
						if(!visited[nextCoordWithTime[0]][nextCoordWithTime[1]]) {
							visited[nextCoordWithTime[0]][nextCoordWithTime[1]] = true;
							
							if(maps[nextCoordWithTime[0]].charAt(nextCoordWithTime[1]) != 'X') {
								queue.offer(nextCoordWithTime);
							}
						}
					}
				}
			}
		}
		
		return -1;
	}
	
    public int solution(String[] maps) {
        int[] targetCoordArray;
        int startX, startY;
        int leverX, leverY;
        int exitX, exitY;
        
        targetCoordArray = findCoord(maps, 'S');
        startX = targetCoordArray[0];
        startY = targetCoordArray[1];
        
        targetCoordArray = findCoord(maps, 'L');
        leverX = targetCoordArray[0];
        leverY = targetCoordArray[1];
        
        targetCoordArray = findCoord(maps, 'E');
        exitX = targetCoordArray[0];
        exitY = targetCoordArray[1];
        
        int leverTime = bfs(maps, startX, startY, leverX, leverY);
        if(leverTime == -1) {
        	return -1;
        }
        
        int exitTime = bfs(maps, leverX, leverY, exitX, exitY);
        if(exitTime == -1) {
        	return -1;
        }
        
        return leverTime + exitTime;
    }
}