package se.jacobsvensson.oneplustouchfix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaqob.oneplustouchfix.R;

public class ConfigurationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        SharedPreferences sharedPref = ConfigurationActivity.this.getSharedPreferences("OnePlusTouchFixV3", Context.MODE_PRIVATE);

        int vibrationDuration = sharedPref.getInt("vibrationDuration", 25);
        textView2.setText(vibrationDuration + " ms");

        startService(new Intent(getApplicationContext(), MyService.class));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setText(progresValue + " ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int vibrationDuration = seekBar.getProgress();
                SharedPreferences sharedPref = ConfigurationActivity.this.getSharedPreferences("OnePlusTouchFixV3", Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = sharedPref.edit();
                mEditor.putInt("vibrationDuration", vibrationDuration);
                mEditor.apply();

                Vibrator v = (Vibrator) ConfigurationActivity.this.getSystemService(VIBRATOR_SERVICE);
                v.vibrate(vibrationDuration);

                //Log.v("OnePlusTouchFix", "Stopped at vibrationDuration=" + seekBar.getProgress());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        int result = RootCommand.runTouchFixCommand();
        if (result != 0) {
            Toast.makeText(ConfigurationActivity.this, "Failed to execute command. Is the phone rooted?", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ConfigurationActivity.this, "Command executed successfully", Toast.LENGTH_SHORT).show();
        }
    }


}
