package thiandac.mywavetest;

import android.graphics.Color;

public class GreenToRedFormatter implements BarColorFormatter {

    public int getColor(float value, float maxVal, float minVal) {


        float hsv[] = new float[] { ((120f * ((maxVal-minVal) - (value-minVal))) / (maxVal-minVal)), 1f, 1f };

        return Color.HSVToColor(hsv);
    }
}