package mergeSort;

import java.io.*;

public class DriverClass {
    public static void main(String[] args) throws IOException, InterruptedException {
        int[] arr=new int[8];
        arr[0]=6;
        arr[1]=5;
        arr[2]=3;
        arr[3]=1;
        arr[4]=8;
        arr[5]=7;
        arr[6]=2;
        arr[7]=4;
        String threadName="T";
        ParallelMergeSort mergesort=new ParallelMergeSort(threadName,0,7,arr);
        mergesort.start();
        mergesort.join();
        System.out.println("check files in project folder for results...");

    }
}
