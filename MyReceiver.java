package se.jacobsvensson.oneplustouchfix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            int result = RootCommand.runTouchFixCommand();
            if (result != 0) {
                Toast.makeText(context, "Failed to execute command. Is the phone rooted?", Toast.LENGTH_LONG).show();
            }

            SharedPreferences sharedPref = context.getSharedPreferences("OnePlusTouchFixV3", Context.MODE_PRIVATE);
            int vibrationDuration = sharedPref.getInt("vibrationDuration", 25);

            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(vibrationDuration);

            //Log.v("OnePlusTouchFix", "vibrationDuration=" + vibrationDuration);
        } else if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            context.startService(new Intent(context, MyService.class));
            //Log.v("OnePlusTouchFix", "Service loaded at start");
        }
    }
}
