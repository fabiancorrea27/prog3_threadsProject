package co.edu.uptc.utils;

import co.edu.uptc.views.DirectEnum;

public class Util {
    
    public static DirectEnum randomDirection(){
        DirectEnum[] directEnums = DirectEnum.values();
        return directEnums[(int) (Math.random() * directEnums.length)];
    }

}
