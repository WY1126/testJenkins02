package org.wangyao.testjenkins02.code.imgToWord;

public class MainC {

    public static void main(String[] args) {
        System.out.printf("%d", (int)1e9+7);
//        System.out.printf("%d", maxRepeating("aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba"));
    }

    public static int maxRepeating(String sequence, String word) {

        char[] wordArr = word.toCharArray();
        char[] seqArr = sequence.toCharArray();
        int ans = 0;
        int k = 1;
        int[][] dp = new int[wordArr.length+1][seqArr.length+1];
        for (int i = 1; i <= wordArr.length; i++) {
            if (k <= seqArr.length) {
                for (int j = k; j <= seqArr.length; j++) {
                    if (wordArr[i-1] == seqArr[j-1]) {
                        dp[i][j] = dp[i-1][j-1] + 1;
                        if (dp[i][j] == wordArr.length) {
                            ans++;
                            System.out.println(j);
                            dp = new int[wordArr.length+1][seqArr.length+1];
                            i = 0;
                            k = j+1;
                            break;
                        }
                    }  else {
                        dp[i][j] = 0;
                    }
                }
            }
        }
        return ans;

    }
}
