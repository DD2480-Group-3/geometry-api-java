package com.esri.core.geometry;

public class RefactorExecuteOGC {

    public static boolean[] bCheckConsistentAttributes(int wkbType, boolean bCheckConsistentAttributes, boolean bHasZs, boolean bHasMs){
        boolean[] r_val = new boolean[3];
        
        if(wkbType > 3000){
            if (bCheckConsistentAttributes) {
                RefactorExecuteOGC.checkHasZsHasMs(wkbType, bHasZs, bHasMs);
            } else {
                bHasZs = true;
                bHasMs = true;
                bCheckConsistentAttributes = true;
            }
        } else if (wkbType > 2000) {
            if (bCheckConsistentAttributes) {
                RefactorExecuteOGC.checkHasZsHasMs(wkbType, bHasZs, bHasMs);
            } else {
                bHasZs = false;
                bHasMs = true;
                bCheckConsistentAttributes = true;
            }
        } else if (wkbType > 1000) {
            if (bCheckConsistentAttributes) {
                RefactorExecuteOGC.checkHasZsHasMs(wkbType, bHasZs, bHasMs);
            } else {
                bHasZs = true;
                bHasMs = false;
                bCheckConsistentAttributes = true;
            }

        }else {
            if (bCheckConsistentAttributes) {
                RefactorExecuteOGC.checkHasZsHasMs(wkbType, bHasZs, bHasMs);
            } else {
                bHasZs = false;
                bHasMs = false;
                bCheckConsistentAttributes = true;
            }
        }

        r_val[0] = bHasZs;
        r_val[1] = bHasMs;
        r_val[2] = bCheckConsistentAttributes;

        return r_val;
    }
    
    public static void checkHasZsHasMs(int wkbType, boolean bHasZs, boolean bHasMs) throws IllegalArgumentException {
        if (wkbType > 3000) {
            if (!bHasZs || !bHasMs) {
                throw new IllegalArgumentException();
            } else {
            }
        }
        else if (wkbType > 2000) {
            if (bHasZs || !bHasMs) {
                throw new IllegalArgumentException();
            } else {
            }
        } else if (wkbType > 1000) {
            if (!bHasZs || bHasMs) {
                throw new IllegalArgumentException();
            } else {
            }
        }else {
            if (bHasZs || bHasMs) {
                throw new IllegalArgumentException();
            } else {
            }
        }
    }
}

/* public class BAttributes {
    public boolean bHasZs;
    public boolean bHasMs;
    public boolean bCheckConsistentAttributes;

    public BAttributes(bHas){

    }
} */