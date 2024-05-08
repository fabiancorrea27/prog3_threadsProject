package co.edu.uptc.utils;

public class Util {

    public static DirectEnum randomDirection() {
        DirectEnum[] directEnums = DirectEnum.values();
        return directEnums[(int) (Math.random() * directEnums.length)];
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
          
        }
    }

}
