import java.util.*;

class Solution {
    public int[] solution(String[] keymap, String[] targets) {
        int[] answer = new int[targets.length];
        HashMap<Character, Integer> map = new HashMap<>();
        
        for(int i=0; i<keymap.length; i++) {
            String s = keymap[i];
            
            for(int j=0; j<s.length(); j++) {
                char c = s.charAt(j);
                
                int searchResult = map.getOrDefault(c, Integer.MAX_VALUE);
                map.put(c, Math.min(j + 1, searchResult));  //자판을 누르는 횟수는 1부터 시작
            }
        }
        
        for(int i=0; i<targets.length; i++) {
            int tempResult = 0;
            
            for(char c : targets[i].toCharArray()) {
                try {
                    tempResult += map.get(c);
                } catch(NullPointerException e) {
                    tempResult = -1;
                    break;
                }
            }
            
            answer[i] = tempResult;
        }
        
        return answer;
    }
}