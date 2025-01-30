import java.util.Arrays;

class Solution {
	public int calcMaxSize(int[] mats, String[][] park, int i, int j) {
		int maxSize = 0;
		
		for(int gap=0; gap<Math.min(park.length - i, park[i].length - j); gap++) {
			for(int k=i; k<=i+gap; k++) {
				for(int l=j; l<=j+gap; l++) {
					if(!park[k][l].equals("-1")) {
						return maxSize;
					}
				}
			}
			
			maxSize = Math.max(maxSize, gap) + 1;
		}
		
		return maxSize;
	}
	
    public int solution(int[] mats, String[][] park) {
        int answer = -1;
        int maxSize = 0;
        
        for(int i=0; i<park.length; i++) {
        	for(int j=0; j<park[i].length; j++) {
        		if(park[i][j].equals("-1")) {
        			maxSize = Math.max(maxSize, calcMaxSize(mats, park, i, j));
        		}
        	}
        }
        
        Arrays.sort(mats);
        
        for(int i : mats) {
    		if(maxSize < i) {
        		break;
        	}
    		
    		answer = i;
        }
        
        return answer;
    }
}