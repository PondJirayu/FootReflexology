package jirayu.pond.footreflexology.util;

/**
 * Created by lp700 on 16/3/2560.
 */

public class AlertViewPositionUtils {

    /*
       COL[0 = topMargin]
       COL[1 = leftMargin]
    */

    public static int[][] getAlertViewRightFootPosition() {
        int position[][] = new int[][];

        return position;
    }

    public static int[][] getAlertViewLeftFootPosition() {
        int position[][] = new int[][];

        return position;
    }

    public static int[][] getAlertViewInTheFootPosition() {
        int position[][] = new int[][];

        return position;
    }

    public static int[][] getAlertViewOutSideFootPosition() {
        int position[][] = new int[16+1][2]; // เขตตอบสนองหลัก 16 จุด ซ้ำ 1 จุด

        // 1
        position[0][0] = 94;
        position[0][1] = 121;
        // 2
        position[1][0] = 107;
        position[1][1] = 90;
        // 3
        position[2][0] = 58;
        position[2][1] = 287;
        // 4
        position[3][0] = 288;
        position[3][1] = 131;
        // 5
        position[4][0] = 290;
        position[4][1] = 33;
        // 6
        position[5][0] = 426;
        position[5][1] = 106;
        // 7
        position[6][0] = 414;
        position[6][1] = 153;
        // 8
        position[7][0] = 324;
        position[7][1] = 223;
        // 9
        position[8][0] = 399;
        position[8][1] = 316;
        // 10
        position[9][0] = 425;
        position[9][1] = 415;
        // 11
        position[10][0] = 310;
        position[10][1] = 448;
        // 12
        position[11][0] = 265;
        position[11][1] = 445;
        // 13
        position[12][0] = 200;
        position[12][1] = 565;
        // 14
        position[13][0] = 155;
        position[13][1] = 436;
        // 15
        position[14][0] = 275;
        position[14][1] = 336;
        // 16
        position[15][0] = 219;
        position[15][1] = 272;
        // 8 Double
        position[15+1][0] = 66;
        position[15+1][1] = 184;

        return position;
    }

    public static int[][] getAlertViewOnTheBackFootPosition() {
        int position[][] = new int[12][2];



        return position;
    }
}
