import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 5607. [Professional] 조합
 * 
 * 실행시간: 232ms (SWEA)
 * 메모리: 30,072 KB
 * 
 * 페르마 소정리와 모듈러의 성질 문제입니다.
 * 
 * [페르마 소정리]
 * A * a^(p-2) ≡ A / a * (mod p) #(단, A/a는 정수, p는 소수)
 * 
 * 예) 5C3 (mod 7)
 * ≡ (5! / (2! * 3!)) (mod 7) ≡ (5! * (2!3!)^(7-2)) (mod 7)
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
    
    static final int MOD = 1234567891;              	//문제에서 주어진 나눌 수 (12억 3천 4백 5십 6만 7천 8백 9십 일)
    static long[] NFact = new long[1000000 + 1];        //N! % MOD값을 저장하는 배열 (N은 최대 100만이므로 미리 계산해둠 -여러 테스트 케이스에서 사용)
    
    /**
     * N! % MOD값을 구해두는 메소드 (1 ≤ N ≤ 1000000) #여러 테스트케이스에서 사용
     */
    static void preProcess() {
        NFact[0] = NFact[1] = 1;
        
        for(int i=1; i<=1000000; i++) {
            NFact[i] = (NFact[i-1] * i) % MOD;
        }
    }
    
    /**
     * 매개변수로 받은 값만큼 지수 승 후 모듈러 연산을 취한 값을 리턴하는 메소드
     * 
     * @param base: 밑
     * @param exponential: 지수
     * @return
     */
    static long calc(long base, int exponential) {
        if(exponential == 1) {
            return base % MOD;
        }
        
        long halfExpCalcResult = calc(base, exponential / 2) % MOD;
        long expCalcResult = (halfExpCalcResult * halfExpCalcResult) % MOD;
        
        //(base^(exponential/2) * base^(exponential/2)) % MOD 리턴 #exponential는 홀수이므로 (exponential/2) 연산에서 나머지 버려짐. 따라서 다시 base를 곱해주어야 함
        //지수가 홀수이면 calc(base, exponential/2)
        if(exponential % 2 == 1) {
            return (expCalcResult * base) % MOD;
        }
        
        //지수가 짝수이면 (base^(exponential/2) * base^(exponential/2)) % MOD 리턴
        else {
            return expCalcResult;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = null;
        
        preProcess();									//N! % MOD값 구해둠 (여러 테스트케이스에서 사용) 
        
        int T = Integer.parseInt(br.readLine());		//테스트케이스의 개수 T
        
        for(int i=1; i<=T; i++) {
            st = new StringTokenizer(br.readLine());
            
            //(1 ≤ N ≤ 1000000, 0 ≤ R ≤ N)
            int N = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            
            long N_RFact = 1;							//(N-R)! % 1234567891 값 저장
            long RFact = 1;								//R! % 1234567891 값 저장
            
            /**
             * N combination R mod 1234567891
             * ≡ (N! / ((N-R)! * R!) (mod 1234567891)
             * ≡ N! ((N-R)! * R!)^(1234567891 - 2) (mod 1234567891)
             */
            
            for(int j=1; j<=N-R; j++) {
                N_RFact = (N_RFact * j) % MOD;
            }
            
            for(int j=1; j<=R; j++) {
                RFact = (RFact * j) % MOD;
            }
            
            //N! * {((N-R)! * R!)^(1234567891 - 2)} % 1234567891를 순차적으로 실행하면 오버플로우가 발생할 수 있으므로 먼저 모듈러 연산 시행 #N!에 MOD연산을 취한 뒤 곱해도 오버플로우 발생했음
            long temp = (calc(N_RFact, MOD - 2) * calc(RFact, MOD - 2)) % MOD;
            long answer = (NFact[N] * temp) % MOD;
            
            sb.append("#" + i + " " + answer + "\n");
        }
        
        System.out.println(sb.toString());
    }

}