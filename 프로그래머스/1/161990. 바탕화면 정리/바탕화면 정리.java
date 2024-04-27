class Solution {
    public int[] solution(String[] wallpaper) {
        int[] answer = new int[4];
        int leftUpX = Integer.MAX_VALUE, leftUpY = Integer.MAX_VALUE;
        int rightDownX = Integer.MIN_VALUE, rightDownY = Integer.MIN_VALUE;
        
        //TODO: 좌상, 좌하, 우상, 우하 구하기?
        for(int i=0; i<wallpaper.length; i++) {
            for(int j=0; j<wallpaper[i].length(); j++) {
                String s = wallpaper[i];
                
                if(s.charAt(j) == '#') {
                    leftUpX = Math.min(leftUpX, i);
                    leftUpY = Math.min(leftUpY, j);
                    
                    rightDownX = Math.max(rightDownX, i);
                    rightDownY = Math.max(rightDownY, j);
                }
            }
        }
        answer[0] = leftUpX;
        answer[1] = leftUpY;
        answer[2] = rightDownX + 1;
        answer[3] = rightDownY + 1;
        
        return answer;
    }
}