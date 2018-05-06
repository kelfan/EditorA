package com.kelfan.utillibrary;

import android.graphics.Color;

/**
 * Created by Administrator on 14/01/2018.
 */

public class ColorWorker {
    public static final int YELLOW_DEEP = Color.rgb(247, 209, 106);
    public static final int YELLOW_VERY_LIGHT = Color.parseColor("#D8D8BF");
    public static final int RED_VERY_LIGHT = Color.parseColor("#D8BFD8");
    public static final int BLUE_SEA = Color.parseColor("#70DB93");
    public static final int ORANGE = Color.parseColor("#FF2400");
    public static final int GREY_LIGHT = Color.parseColor("#D9D9F3");
    public static final int SILVER_LIGHT = Color.parseColor("#E6E8FA");
    public static final int CHOCOLATE = Color.parseColor("#5C3317");
    public static final int PURPLE_BLUE = Color.parseColor("#FF2400");
    public static final int BROWN = Color.parseColor("#A67D3D");
    public static final int BROWN_COLD = Color.parseColor("#D98719");
    public static final int RED_CORAL = Color.parseColor("#FF7F00");
    public static final int BLUE_PURPLE = Color.parseColor("#42426F");
    public static final int BROWN_DEEP = Color.parseColor("#5C4033");
    public static final int PURPLE_DEEP = Color.parseColor("#9932CD");
    public static final int PURPLE_DARK = Color.parseColor("#6B238E");
    public static final int BLUE_LIGHT = Color.parseColor("#7093DB");
    public static final int BROWN_WOOD = Color.parseColor("#855E42");
    public static final int RED_DEEP = Color.parseColor("#8E2323");
    public static final int GREEN_FOREST = Color.parseColor("#238E23");
    public static final int GOLD = Color.parseColor("#CD7F32");
    public static final int YELLOW_LIGHT = Color.parseColor("#DBDB70");
    public static final int GREEN_COPPER = Color.parseColor("#527F76");
    public static final int GREEN_HUNTER = Color.parseColor("#215E21");
    public static final int PURPLE_LIGHT = Color.parseColor("#8F8FBD");
    public static final int BLUE_VERY_LIGHT = Color.parseColor("#C0D9D9");
    public static final int ORANGE_LIGHT = Color.parseColor("#E47833");
    public static final int GREEN_GRASS = Color.parseColor("#7FFF00");
    public static final int BLUE_DEEP = Color.parseColor("#00009C");
    public static final int BLUE = Color.parseColor("#38B0DE");
    public static final int BLUE_SKY = Color.parseColor("#3299CC");
    public static final int RED_CRIMSON = Color.parseColor("#BC1717");
    public static final int SALMON = Color.parseColor("#6F4242");
    public static final int GREEN_SEA = Color.parseColor("#238E68");

    /**
     * Returns the complimentary (opposite) color.
     * @param color int RGB color to return the compliment of
     * @return int RGB of compliment color
     */
    public static int getComplimentColor(int color) {
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }

    public static int strToColor(String txt){
        int th = txt.hashCode()%15;
        return pickColor(th);
    }

    public static int pickColor(int num){
        switch (num) {
            case 0: return YELLOW_DEEP;
            case 1: return BLUE_SKY;
            case 2: return GREEN_GRASS;
            case 3: return SALMON;
            case 4: return GREEN_COPPER;
            case 5: return ORANGE_LIGHT;
            case 6: return PURPLE_LIGHT;
            case 7: return YELLOW_LIGHT;
            case 8: return GOLD;
            case 9: return BROWN_WOOD;
            case 10: return BLUE_LIGHT;
            case 11: return RED_CORAL;
            case 12: return BROWN_COLD;
            case 13: return CHOCOLATE;
            case 14: return SILVER_LIGHT;
            default: return GREEN_SEA;
        }
    }
}
