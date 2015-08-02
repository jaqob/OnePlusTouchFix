package se.jacobsvensson.oneplustouchfix;

import java.io.IOException;

class RootCommand {

    public static int runTouchFixCommand()
    {
        int result = -1;
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"su","-c","cat /sys/class/input/input0/baseline_test"});
            result = p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
