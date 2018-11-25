/* This is a project that denotes the various sorting algorithms we have gone
over in class. Testing the sorts with various partitions and combinations.
*/


import java.util.Random;


class ArraySorts {

    //Helper 'swap' method to switch two elements in an array
    public static void swap(int [] arr, int a, int b){
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

    private static pair twoPointerPartition( int a[], int left, int right, int pivot){ //#####Done#####
        int val = a[pivot];

        while(left <= right){
            while(a[left]<val){
                left++;
            }
            while(a[right]>val){
                right--;
            }
            if(left <= right){
                swap(a, left, right);

                left++;
                right--;
            }
        }
        return new pair(left,right);
    }

    private static int bookPartition( int a[], int left, int right, int pivot){ // ### Done ###
        boolean switcher = true;

        //select pivot and swap with array[first]
        swap(a, left, pivot);
        int LS = left;
        int FU = left + 1;


        while (FU <= right) {
            if (a[FU] < a[left]) {
                swap(a, LS + 1, FU);
                LS++;
                FU++;

            } else if (a[FU] > a[left]) {
                FU++;

            } else {
                if (switcher = true) {
                    swap(a, LS + 1, FU);
                    LS++;
                    FU++;
                } else {
                    FU++;
                }
            switcher = false;
            }
        }
        swap(a, LS, left);
        return LS;
    }

    //Fix this --- ####Done###
    private static pair twoPivotPartition( int a[], int left, int right, int rightpivot, int leftpivot){

        int LPValue=a[leftpivot];
        int RPValue=a[rightpivot];

        int ls=left;
        int fu=left+1;
        int fb=right;

        //Putting the random pivot in the right spot
        swap(a,rightpivot,right);
        //Putting the random pivot in the left spot.
        swap(a,leftpivot,left);


        if(a[left]>a[right]){
            swap(a,left,right);
        }

        while(fu<fb){

            //Checking to see if smaller first
            if(a[fu]<a[left]){
                ls++;
                swap(a,ls,fu);

            }
            //Check to see if bigger than right
            else if(a[fu]>a[right]){
                fb--;
                swap(a,fb,fu);
            }

            fu++;
        }
        swap(a,left,ls);
        swap(a,right,fb);

        return new pair(ls,fb);
    }

    //first quicksort - Book partition with a random pivot (Driver method)
    private static void QuickSort1(int a[], int n, int cutoff) { // ###Done####

        QuickSort1( a, 0, n-1, cutoff);
        insertionSort(a,n);
    }


    private static void QuickSort1(int a[], int left, int right, int cutoff) {
        //Selecting random pivot location
        Random rand = new Random();
        int check = right+1 - left;
        //recursive call of left side of partitioned array

        while ((right-left) + 1 >= cutoff){
            int pivot = left+rand.nextInt((right-left)+1);
            int index = bookPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index-1 - left;
            int rs = right - index+1;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort1(a, left, index-1, cutoff);
                left = pivot+1;
            } else {
                QuickSort1(a, index+1, right, cutoff);
                right = pivot-1;
            }
        }
    }

    //2 pointer partition with random pivot - (Driver method)
    public static void QuickSort2(int a[], int n, int cutoff) {

        QuickSort2( a, 0, n-1, cutoff);
        insertionSort(a,n);
    }

    public static void QuickSort2(int a[], int left, int right, int cutoff) {
        //Selecting random pivot location
        Random rand = new Random();

        //recursive call of left side of partitioned array
        while ((right-left) + 1 >= cutoff){
            int pivot = left+rand.nextInt((right-left)+1);
            pair index = twoPointerPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index.right - left;
            int rs = right - index.left;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort2(a, left, index.right, cutoff);
                left = pivot+1;
            } else {
                QuickSort2(a, index.left, right, cutoff);
                right = pivot-1;
            }
        }

    }

    //book's partition with pivot = a[lf] - Driver Method
    public static void QuickSort3(int a[], int n, int cutoff) {
        QuickSort3( a, 0, n-1, cutoff);
        insertionSort(a,n);

    }

    public static void QuickSort3(int a[], int left, int right, int cutoff) {


        while ((right-left) + 1 >= cutoff){
            int pivot = left;
            int index = bookPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index-1 - left;
            int rs = right - index+1;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort3(a, left, index-1, cutoff);
                left = pivot+1;
            } else {
                QuickSort3(a, index+1, right, cutoff);
                right = pivot-1;
            }
        }
    }

    // 2 pointer partition, pivot = a[lf]
    public static void QuickSort4(int a[], int n, int cutoff) {

        QuickSort4( a, 0, n-1, cutoff);
        insertionSort(a,n);
    }

    public static void QuickSort4(int a[], int left, int right, int cutoff) {

        //recursive call of left side of partitioned array
        while ((right-left) + 1 >= cutoff){
            int pivot = left;
            pair index = twoPointerPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index.right - left;
            int rs = right - index.left;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort4(a, left, index.right, cutoff);
                left = pivot+1;
            } else {
                QuickSort4(a, index.left, right, cutoff);
                right = pivot-1;
            }
        }
    }


    //3 partitions, 2 random pivots -- Driver
    public static void QuickSort5(int a[], int n, int cutoff) {
        QuickSort5(a,0, n-1, cutoff);
        insertionSort(a,n);
    }

    public static void QuickSort5(int a[], int left, int right, int cutoff) {
        //Selecting random pivot location
        Random rand = new Random();
        Random rand2 = new Random();

        //recursive call of left side of partitioned array
        while ((right-left) + 1 >= cutoff){
            int pivot = left+rand.nextInt((right-left)+1);
            pair index = twoPointerPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index.right - left;
            int rs = right - index.left;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort2(a, left, index.right, cutoff);
                left = pivot+1;
            } else {
                QuickSort2(a, index.left, right, cutoff);
                right = pivot-1;
            }
        }
    }

    // Quicksort + book partition with no insertion sort
    public static void AlmostQS1(int a[], int n, int cutoff){
        AlmostQS1(a, 0, n-1, cutoff);
    }

    public static void AlmostQS1(int a[], int left, int right, int cutoff){
        //Selecting random pivot location
        Random rand = new Random();
        int check = right+1 - left;
        //recursive call of left side of partitioned array

        while ((right-left) + 1 >= cutoff){
            int pivot = left+rand.nextInt((right-left)+1);
            int index = bookPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index-1 - left;
            int rs = right - index+1;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                AlmostQS1(a, left, index-1, cutoff);
                left = pivot+1;
            } else {
                QuickSort1(a, index+1, right, cutoff);
                right = pivot-1;
            }
        }
    }

    //Quicksort + 2 pointer partition with no insertion sort
    public static void AlmostQS2(int a[], int n, int cutoff){
        AlmostQS2(a, 0, n-1, cutoff);
    }

    public static void AlmostQS2(int a[], int left, int right, int cutoff){
        //Selecting random pivot location
        Random rand = new Random();

        //recursive call of left side of partitioned array
        while ((right-left) + 1 >= cutoff){
            int pivot = left+rand.nextInt((right-left)+1);
            pair index = twoPointerPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index.right - left;
            int rs = right - index.left;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                AlmostQS2(a, left, index.right, cutoff);
                left = pivot+1;
            } else {
                AlmostQS2(a, index.left, right, cutoff);
                right = pivot-1;
            }
        }
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