class Solution {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int lastAttackTime = attacks[attacks.length-1][0];
        int accHealTime = 0;
        int attackArrayIndex = 0;
        int maxHealth = health;
        
    	for(int i=0; i<=lastAttackTime; i++) {
    		//현재 시각에 공격받음
    		if(i == attacks[attackArrayIndex][0]) {
    			//연속 회복 시간 초기화
    			accHealTime = 0;
    			
    			//체력 감소
    			health -= attacks[attackArrayIndex][1];
    			attackArrayIndex++;
    			
            	if(health <= 0) {
            		return -1;
            	}
    		}
    		//공격받지 않음 -> 체력 회복
    		else {
    			health += bandage[1];
    			accHealTime++;
    			
    			//연속 붕대감기 -> 추가 체력 회복
    			if (accHealTime == bandage[0]){
    				accHealTime = 0;
    				health += bandage[2];
        		}
    			
    			//현재 체력이 최대 체력 넘어서는 경우
    			health = Math.min(health, maxHealth);
    		}
        }
        
        return health;
    }
}