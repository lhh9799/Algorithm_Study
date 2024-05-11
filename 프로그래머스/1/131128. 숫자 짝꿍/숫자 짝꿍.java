import java.util.*;

class Solution {
    public String solution(String X, String Y) {
        String answer = "-1";
        int count1[] = new int[10];
        int count2[] = new int[10];
        StringBuilder sb = new StringBuilder();
        
        for(char c : X.toCharArray()) {
            count1[c - '0']++;
        }
        
        for(char c : Y.toCharArray()) {
            count2[c - '0']++;
        }
        
        for(int i=9; i>=0; i--) {
            int value = Math.min(count1[i], count2[i]);
            
            if(value != 0) {
                for(int j=0; j<value; j++) {
                    sb.append(i);
                }
            }
        }
        
        if(sb.length() > 0) {
            //0이 여러 개인 경우 해결 (예: 00 -> 0)
            int startingPoint = 0;
            
            while(startingPoint < sb.length() && sb.charAt(startingPoint++) == '0');
            
            if(startingPoint == sb.length()+1) {
                answer = "0";
            } else {
                answer = sb.substring(startingPoint-1);
            }
        }
        
        return answer;
    }
}