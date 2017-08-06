package thiandac.mywavetest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Callbacks,ValueBarSelectionListener {
    private WaveHelper mWaveHelper1, mWaveHelper2;
    public GreenToRedFormatter greenToRedFormatter;
    WaveView waveView1, waveView2;
    TextView textView, textView2;
    float min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Color.parseColor("#28f16d7a") Color.parseColor("#3cf16d7a")
        textView = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        waveView1 = (WaveView) findViewById(R.id.wave1);
        waveView1.setShapeType(WaveView.ShapeType.SQUARE);
        greenToRedFormatter = new GreenToRedFormatter();

        mWaveHelper1 = new WaveHelper(waveView1);
        mWaveHelper1.initAnimation(0f, 1f, 1000, 0.3f, 0.3f, 1000, 0.0001f, 0.0001f, 5000);
        mWaveHelper1.registerClient(MainActivity.this, waveView1);

        waveView2 = (WaveView) findViewById(R.id.wave2);
        waveView2.setShapeType(WaveView.ShapeType.SQUARE);
        waveView2.setWaveColor(new RedToGreenFormatter(), Color.parseColor("#40b7d28d"));
        mWaveHelper2 = new WaveHelper(waveView2);
        mWaveHelper2.initAnimation(0f, 1f, 1000, 0.3f, 0.3f, 1000, 0.0001f, 0.0001f, 5000);
        mWaveHelper2.registerClient(MainActivity.this, waveView2);
       // minView = (TextView) findViewById(R.id.minView);
    }

    public void updateClient(float data,View v) {
      if(v==waveView1) {
          textView.setText(String.valueOf(data));
          /*if(10*data >= 0 && 10*data <=3){
              waveView1.setWaveColor(Color.parseColor("#40b7d28d"), Color.parseColor("#80b7d28d"));

          } Log.i("COlOR", String.valueOf(greenToRedFormatter.getColor(1,100,1)));*/
          //if (10*data > 3 && 10*data <=6) {
              waveView1.setWaveColor(new RedToGreenFormatter(),Color.parseColor("#40b7d28d"));
          /*}
          if (10*data > 6 && 10*data <=10) {
              waveView1.setWaveColor(Color.RED, Color.parseColor("#3cf16d7a"));
          }*/
      }
      else
             textView2.setText(String.valueOf(data));
    }


    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper1.cancel();
        mWaveHelper2.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper1.start();
        mWaveHelper2.start();
    }

    @Override
    public void onSelectionUpdate(float val, float maxval, float minval, View waveView) {
        Log.i("ValueBar", "Value selection update: " + val);
    }

    @Override
    public void onValueSelected(float val, float maxval, float minval, View waveView) {
        Log.i("ValueBar", "Value selection update: " + val);
    }
}
