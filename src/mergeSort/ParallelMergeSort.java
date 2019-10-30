package mergeSort;

import java.io.*;

public class ParallelMergeSort extends Thread {

    String threadName;
    int[] arr;
    int left, right;

    public ParallelMergeSort(String threadName, int left, int right, int[] arr) {
        this.threadName = threadName;
        this.left = left;
        this.right = right;
        this.arr=arr;
    }

    public void run() {
        try {
            mergeSort(arr, left, right);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void merge(int [] array,int[] x,int[] y,int left,int mid,int right) {
        int i=0,j=0;
        int k=left;
        int n1=mid-left+1,n2=right-mid;
        while (i < n1 && j < n2)
        {
            if (x[i] <= y[j])
            {
                arr[k] = x[i];
                i++;
            }
            else
            {
                arr[k] = y[j];
                j++;
            }
            k++;
        }
        while (i < n1)
        {
            arr[k] = x[i];
            i++;
            k++;
        }
        while (j < n2)
        {
            arr[k] = y[j];
            j++;
            k++;
        }
    }

    private void mergeSort(int[] arr, int left, int right) throws IOException, InterruptedException {
        if (right - left <= 2)
        {
            if (right - left <= 2)
            {
                if (right - left == 1) //if there are 2 elements left
                {
                    FileWriter fileWriter = new FileWriter(this.threadName+".txt");
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.print("Input Array:    ");
                    for (int i = left; i <= right; i++) {
                        printWriter.printf("%d     ", arr[i]);
                    }
                    printWriter.println("");
                    if (arr[left] > arr[right]) {
                        int temp = arr[left];
                        arr[left] = arr[right];
                        arr[right] = temp;
                    }
                    printWriter.print("Sorted Array:    ");
                    for (int i = left; i <= right; i++) {
                        printWriter.printf("%d     ", arr[i]);
                    }
                    printWriter.println("");
                    printWriter.close();
                }
            }
            else if (right-left==0) //if there is only one element left
            {
                FileWriter fileWriter = new FileWriter(this.threadName+".txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print("Input Array:    ");
                printWriter.printf("%d\n", arr[left]);
                printWriter.print("Sorted Array:    ");
                printWriter.printf("%d\n", arr[left]);
            }
        }
        else
        {
            FileWriter fileWriter = new FileWriter(this.threadName+".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("Input Array:    ");
            for (int i = left; i <= right; i++) {
                printWriter.printf("%d     ", arr[i]);
            }
            printWriter.println("");
            int mid=(left+right)/2;
            String lThread=this.threadName.concat(".l");
            String rThread=this.threadName.concat(".r");

            //making threads
            ParallelMergeSort leftHalf=new ParallelMergeSort(lThread,left,mid,arr);
            ParallelMergeSort rightHalf=new ParallelMergeSort(rThread,mid+1,right,arr);
            leftHalf.start();
            rightHalf.start();

            //join first thread here
            leftHalf.join();
            //join second thread here
            rightHalf.join();

            int[] x=new int[mid-left+1];
            int[] y=new int[right-mid];
            for(int i=0;i<mid-left+1;i++)
            {
                x[i]=arr[left+i];
            }
            for (int i=0;i<right-mid;i++)
            {
                y[i]=arr[mid+i+1];
            }
            merge(arr,x,y,left,mid,right);
            printWriter.print("Sorted Array:    ");
            for (int i = left; i <= right; i++) {
                printWriter.printf("%d     ", arr[i]);
            }
            printWriter.println("");
            printWriter.close();
        }
    }
}