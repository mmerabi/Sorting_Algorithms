/* This is a project that denotes the various sorting algorithms we have gone
over in class. Including various partitions and
*/

import java.util.Random;


// use this class to contain everything related to your sorts
class ArraySorts {
    // Some sample driver method headers

    //Helper 'swap' method to switch two elements in an array
    public static void Swap(int a, int b, int [] arr){//swap to be used throughout sorts
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] =  temp;
    }

    public static void insertionSort(int a[], int n) {  // #######Done###########
      for (int i=1; i<n; ++i){
          int current = a[i];
          int moveup = i-1;

          //Small elements get moved ahead by one position one by one
          while (moveup >= 0 && a[moveup] > current){
            a[moveup+1] = a[moveup];
            moveup = moveup-1;
          }
          a[moveup+1] = current;
      }
    }

    private static int twoPointerPartition( int a[], int left, int right, int pivot){ //#####Done#####
        while(left <= right){
            while(a[left]<pivot){
                left++;
            }
            while(a[right]>pivot){
                right--;
            }
            if(left <=right){
                int temp = a[left];
                a[left] = a[right];
                a[left] = temp;

                left++;
                right--;
            }
        }
        return left;
    }

    private static int bookPartition( int a[], int left, int right, int pivot){

    }

    private static int twoPivotPartition( int a[], int left, int right, int pivot){

    }

    //first quicksort - Book partition with a random pivot
    private static void QuickSort1(int a[], int n, int cutoff) {

        //Selecting random pivot location
        int length = a.length;
        length = length-1;
        int pivot = (int)(Math.random()*length);

        //recursive call of left side of partitioned array
        int index=bookPartition(a,n,cutoff,pivot);
        if (n < index-1){
            QuickSort1(a,n,index-1);
        }

        //Recursive call of right side of partitioned array
        if (cutoff > index){
            QuickSort1(a,index,cutoff);
        }
    }

    //2 pointer partition with random pivot
    public static void QuickSort2(int a[], int n, int cutoff) {

    }

    //book's partition with pivor = a[lf]
    public static void QuickSort3(int a[], int n, int cutoff) {

    }

    // 2 pointer partition, pivot = a[lf]
    public static void QuickSort4(int a[], int n, int cutoff) {

    }

    //3 partitions, 2 random pivots
    public static void QuickSort5(int a[], int n, int cutoff) {

    }

    public static void HeapSortBU(int a[], int n) { // heapsort with linear build heap

    }

    public static void HeapSortTD(int a[], int n){

    }

    public static String myName() {
        return "Michael Merabi";
    }
}

// use this class so that you can return two pivots in your quicksort5
// partition method
class pair {
    public int left, right;

    public pair(int left, int right) {
        this.left = left;
        this.right = right;
    }
}