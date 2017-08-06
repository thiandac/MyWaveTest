package thiandac.mywavetest;

import android.view.View;

/**
 * Created by Thi An Dac on 2017/7/28.
 */

public interface ValueBarSelectionListener {
    /**
     * Called every time the user moves the finger on the ValueBar.
     *
     * @param val
     * @param maxval
     * @param minval
     * @param bar
     */
    public void onSelectionUpdate(float val, float maxval, float minval, View waveView);

    /**
     * Called when the user releases his finger from the ValueBar.
     *
     * @param val
     * @param maxval
     * @param minval
     * @param bar
     */
    public void onValueSelected(float val, float maxval, float minval, View waveView);
}
