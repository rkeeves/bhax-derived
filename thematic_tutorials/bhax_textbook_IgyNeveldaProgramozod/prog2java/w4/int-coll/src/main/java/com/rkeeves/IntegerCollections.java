package com.rkeeves;

public class IntegerCollections {

    public static void sort(IntegerCollection coll){
        sort_bubble(coll);
    }

    public static void sort_bubble(IntegerCollection coll){
        int i = coll.getSize()-1;
        while(i>0) {
            for (int j = 0; j < i; j++) {
                if(coll.get(j+1)<coll.get(j)) {
                    coll.swap(j, j+1);
                }
            }
            i--;
        }
    }

    public static void sort_shell(IntegerCollection coll){
        IntegerCollection steps = new IntegerCollection(new int[]{100,30,8,3,1});
        for (int k = 0; k <= steps.getSize()-1; k++) {
            int step = steps.get(k);
            for (int eltolas = 1; eltolas <= step; eltolas++) {
                int i = step + eltolas;
                while(i<=coll.getSize()-1) {
                    int key = coll.get(i);
                    int j = i - step;
                    while(j>=0 && coll.get(j) > key) {
                        coll.set(j+step,coll.get(j));
                        j = j - step;
                    }
                    coll.set(j+step, key);
                    i = i + step;
                }
            }
        }
    }
}
