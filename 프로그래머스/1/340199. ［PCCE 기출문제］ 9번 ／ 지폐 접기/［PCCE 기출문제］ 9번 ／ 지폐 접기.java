import java.util.Arrays;

class Solution {
    public int solution(int[] wallet, int[] bill) {
        int answer = 0;
        
        int walletBig = Arrays.stream(wallet).max().getAsInt();
        int walletSmall = Arrays.stream(wallet).min().getAsInt();
        
        int billBig = Arrays.stream(bill).max().getAsInt();
        int billSmall = Arrays.stream(bill).min().getAsInt();
        
        while(true) {
        	if(billBig > walletBig || billSmall > walletSmall) {
        		billBig /= 2;
        		
        		if(billBig < billSmall) {
        			int temp = billBig;
        			
        			billBig = billSmall;
        			billSmall = temp;
        		}
        	} else {
        		break;
        	}
        	
        	answer++;
        }
        
        return answer;
    }
}