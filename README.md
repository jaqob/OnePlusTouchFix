# OnePlusTouchFix
Try to reset (and hopefully fix) the touch on the Oneplus One each time the screen is turned on. 

This is done by running the command 
```sh
cat /sys/class/input/input0/baseline_test
```
as root each time the intent ACTION_SCREEN_ON is recived. 
