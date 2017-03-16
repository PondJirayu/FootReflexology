package jirayu.pond.footreflexology.util;

/**
 * Created by lp700 on 16/3/2560.
 */

public class AlertViewPositionUtils {

    /*
       COL[0 = topMargin]
       COL[1 = leftMargin]
    */

//    public static int[][] getAlertViewRightFootPosition() {
//        int position[][] = new int[][];
//
//        return position;
//    }
//
//    public static int[][] getAlertViewLeftFootPosition() {
//        int position[][] = new int[][];
//
//        return position;
//    }

    public static int[][] getAlertViewInTheFootPosition() {
        int position[][] = new int[14+1][2]; // เขตตอบสนองหลัก 14 จุด ซ้ำ 1 จุด

        // 1
        position[0][0] = 96;
        position[0][1] = 106;
        // 2
        position[1][0] = 130;
        position[1][1] = 87;
        // 3
        position[2][0] = 229;
        position[2][1] = 18;
        // 4
        position[3][0] = 255;
        position[3][1] = 96;
        // 5
        position[4][0] = 293;
        position[4][1] = 57;
        // 6
        position[5][0] = 415;
        position[5][1] = 171;
        // 7
        position[6][0] = 389;
        position[6][1] = 279;
        // 8
        position[7][0] = 319;
        position[7][1] = 334;
        // 9
        position[8][0] = 356;
        position[8][1] = 490;
        // 10
        position[9][0] = 415;
        position[9][1] = 575;
        // 11
        position[10][0] = 415;
        position[10][1] = 623;
        // 12
        position[11][0] = 213;
        position[11][1] = 528;
        // 13
        position[12][0] = 119;
        position[12][1] = 370;
        // 14
        position[13][0] = 80;
        position[13][1] = 339;
        // 5 Double
        position[13+1][0] = 349;
        position[13+1][1] = 153;

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

        // 1
        position[0][0] = 308;
        position[0][1] = 144;
        // 2
        position[1][0] = 307;
        position[1][1] = 277;
        // 3
        position[2][0] = 215;
        position[2][1] = 305;
        // 4
        position[3][0] = 90;
        position[3][1] = 172;
        // 5
        position[4][0] = 120;
        position[4][1] = 455;
        // 6
        position[5][0] = 329;
        position[5][1] = 508;
        // 7
        position[6][0] = 380;
        position[6][1] = 500;
        // 8
        position[7][0] = 430;
        position[7][1] = 486;
        // 9
        position[8][0] = 466;
        position[8][1] = 302;
        // 10
        position[9][0] = 274;
        position[9][1] = 348;
        // 11
        position[10][0] = 349;
        position[10][1] = 349;
        // 12
        position[11][0] = 295;
        position[11][1] = 487;

        return position;
    }
}
