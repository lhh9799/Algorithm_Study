import java.util.*;

class Solution {
    public int solution(int n, int m, int[] section) {
        int answer = 0;
        int position = 1;
        
        for(int i=0; i<section.length; i++) {
            if(position <= section[i]) {
                answer++;
                position = section[i] + m;
            }
        }
        
        return answer;
    }
}