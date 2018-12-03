/* Michael Merabi #1460322
 This is a project that denotes the various sorting algorithms we have gone
over in class. Testing the sorts with various partitions and combinations.
*/


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

class ArraySorts {

    //Helper 'swap' method to switch two elements in an array
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static void insertionSort(int a[], int n) {
        for (int i = 1; i < n; ++i) {
            int current = a[i];
            int moveup = i - 1;

            //Small elements get moved ahead by one position one by one
            while (moveup >= 0 && a[moveup] > current) {
                a[moveup + 1] = a[moveup];
                moveup = moveup - 1;
            }
            a[moveup + 1] = current;
        }
    }

    private static pair twoPointerPartition(int a[], int left, int right, int pivot) {
        int val = a[pivot];
        int leftp = left;
        int rightp = right;

        //pointer on left and pointer on right move towards
        //each other while stopping on values bigger/smaller
        //than pivot and then swap. Stop when pointers cross

        while (leftp <= rightp) {
            while (a[leftp] < val) {
                leftp++;
            }
            while (a[rightp] > val) {
                rightp--;
            }
            if (leftp <= rightp) {
                swap(a, leftp, rightp);

                leftp++;
                rightp--;
            }
        }
        return new pair(leftp, rightp);
    }

    private static int bookPartition(int a[], int left, int right, int pivot) {

        boolean switcher = true;

        //select pivot and swap with array[first]
        swap(a, left, pivot);
        int LS = left;

        for (int FU = left+1; FU <= right; FU++) {
            if (a[FU] < a[left]) {
                swap(a, LS + 1, FU);
                LS++;

             //This will increment on it's own
            } else if (a[FU] > a[left]) {

            } else {
                if (switcher) {
                    swap(a, LS + 1, FU);
                    LS++;
                }
                switcher = !switcher;
            }
        }
        swap(a, LS, left);
        return LS;
    }

    //Fix this --- ####Done###
    private static pair twoPivotPartition(int a[], int left, int right, int leftpivot, int rightpivot) {

        boolean toggle=true;
        //Putting the random pivot in the right spot
        swap(a,rightpivot,right);
        //Putting the random pivot in the left spot.
        swap(a,leftpivot,left);


        if(a[left]>a[right]){
            swap(a,left,right);
        }

        int ls=left;
        int fu=left+1;
        int fb=right;

        while(fu<fb){

            //Checking to see if smaller first
            if(a[fu]<a[left]){
                swap(a,ls+1,fu);
                ls++;
                fu++;
            }

            //Check to see if bigger than right
            else if(a[fu]>a[right]){
                fb--;
                swap(a,fu,fb);
            }

            else{
                if(toggle){
                    if(a[fu]==a[ls]) {
                        ls++;
                        swap(a, fu, ls);
                        fu++;
                    }
                }
                else{
                    fu++;
                }
                toggle=!toggle;
            }

        }
        swap(a, left,ls);
        swap(a, right, fb);

        return new pair(ls,fb);

    }

    //first quicksort - Book partition with a random pivot (Driver method)
    public static void QuickSort1(int a[], int n, int cutoff) { // ###Done####

        QuickSort1(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }


    private static void QuickSort1(int a[], int left, int right, int cutoff) {

        //Selecting random pivot location
        Random rand = new Random();
        //recursive call of left side of partitioned array

        while (right - left + 1 >= cutoff) {
            int pivot = left + rand.nextInt((right - left + 1));
            int index = bookPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index - 1 - left;
            int rs = right - index;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort1(a, left, index - 1, cutoff);
                left = index + 1;
            } else {
                QuickSort1(a, index + 1, right, cutoff);
                right = index - 1;
            }
        }
    }

    //2 pointer partition with random pivot - (Driver method)
    public static void QuickSort2(int a[], int n, int cutoff) {

        QuickSort2(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort2(int a[], int left, int right, int cutoff) {
        //Selecting random pivot location
        Random rand = new Random();

        //recursive call of left side of partitioned array
        while ((right - left + 1) >= cutoff) {
            int pivot = left + rand.nextInt((right - left) + 1);
            pair index = twoPointerPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index.right - left;
            int rs = right - index.left;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort2(a, left, index.right, cutoff);
                left = index.left;
            } else {
                QuickSort2(a, index.left, right, cutoff);
                right = index.right;
            }
        }

    }

    //book's partition with pivot = a[lf] - Driver Method
    public static void QuickSort3(int a[], int n, int cutoff) {
        QuickSort3(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort3(int a[], int left, int right, int cutoff) {
        //consider adding boolean check for all sorted array
        //to reduce n^2 time

        while ((right - left) + 1 >= cutoff) {
            int index = left;
            index = bookPartition(a, left, right, index);

            //split the partitioned array into two sides for comparisons
            int ls = (index - 1) - left;
            int rs = right - (index + 1);

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort3(a, left, index - 1, cutoff);
                left = index + 1;
            } else {
                QuickSort3(a, index + 1, right, cutoff);
                right = index - 1;
            }
        }
    }

    // 2 pointer partition, pivot = a[lf]
    public static void QuickSort4(int a[], int n, int cutoff) {

        QuickSort4(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort4(int a[], int left, int right, int cutoff) {

        //recursive call of left side of partitioned array
        while ((right - left+1) >= cutoff) {
            int pivot = left;
            pair index = twoPointerPartition(a, left, right, pivot);

            //split the partitioned array into two sides for comparisons
            int ls = index.right - left;
            int rs = right - index.left;

            //Recursive call of smaller sized partitioned array
            if (ls < rs) {
                QuickSort4(a, left, index.right, cutoff);
                left = index.left;
            } else {
                QuickSort4(a, index.left, right, cutoff);
                right = index.right;
            }
        }
    }


    //Quicksort with 3 partitions, 2 random pivots -- Driver
    public static void QuickSort5(int a[], int n, int cutoff) {
        QuickSort5(a, 0, n - 1, cutoff);
        insertionSort(a, n);
    }

    private static void QuickSort5(int a[], int left, int right, int cutoff) {
        //randoms used for finding partitions
        Random rand = new Random();
        Random rand2 = new Random();

        int leftpivot, rightpivot, leftsize, rightsize, midsize;

        while ((right - left) + 1 >= cutoff) {
            //Selecting random pivot location
            rightpivot = left + rand.nextInt((right - left) + 1);
            leftpivot = left + rand2.nextInt((right - left) + 1);

            pair index = twoPivotPartition(a, left, right, leftpivot, rightpivot);
            leftsize = index.right - left;
            rightsize = right - index.left;
            midsize = ((index.right - 1) - (index.left + 1));

            //The partitioned array is in three sides, compare biggest and
            //recursively start from two smallest partitions

            //left is biggest partition
            if (leftsize > rightsize && leftsize > midsize) {
                QuickSort5(a, index.left + 1, index.right - 1, cutoff);
                QuickSort5(a, index.right + 1, right, cutoff);
                right = index.left - 1;

                //right is biggest partition
            } else if (rightsize > leftsize && rightsize > midsize) {
                QuickSort5(a, left, index.left - 1, cutoff);
                QuickSort5(a, index.left + 1, index.right - 1, cutoff);
                left = index.right + 1;

                //mid is the biggest partition
            } else {
                QuickSort5(a, left, index.left - 1, cutoff);
                QuickSort5(a, index.right + 1, right, cutoff);
                left = index.left + 1;
                right = index.right - 1;
            }
        }
    }

    // Quicksort + book partition with no insertion sort
    public static void AlmostQS1(int a[], int n, int cutoff) {
        QuickSort1(a, 0, n - 1, cutoff);
    }

    //Quicksort + 2 pointer partition with no insertion sort
    public static void AlmostQS2(int a[], int n, int cutoff) {
        QuickSort2(a, 0, n - 1, cutoff);
    }

    public static void AlmostQS5(int a[], int n, int cutoff) {
        QuickSort5(a, 0, n - 1, cutoff);
    }

    // heapsort with linear build heap
    public static void HeapSortBU(int a[], int n) {
    //requires trickle-down (building the heap)

        for (int index = n-1; index >= 0; index--){
            trickleDown(a, index, n);
        }

       // if (a[0])

    }

    public static void HeapSortTD(int a[], int n) {
        //requires trickle-up (building the heap)

        //check to see if parent is bigger than child
        // when not, then save and shift
        for (int child = 1; child < n; child++) {

            // compare sizes here
            if (a[child] > a[(child - 1) / 2]) {
                int newP = child;
                int save = a[newP];

                // start shifting
                while (save > a[(newP - 1) / 2] && newP > 0) {
                    a[newP] = a[(newP - 1) / 2];
                    newP = (newP - 1) / 2;
                }

                a[newP] = save;
            }
        }

        for (int i = n - 1; i >= 1; i--) {
            // Move current max element to end
            int temp = a[i];
            a[i] = a[0];
            a[0] = temp;
            trickleDown(a, i, 0);
        }
    }

    private static void trickleDown(int[] a, int index, int n) {

        //check to see if the item is a leaf, if it is then nothing needs to be done
        int leafcheck = ((n/2)-1);
        if (index > leafcheck){

        }
    }

    private static void trickleUp(int[] a, int root, int n) {


    }

    public static String myName() {
        return "Michael Merabi";
    }


/*  My output

Testing Fearless Leader's program.
QS: book partition, random pivot, cutoff=50:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: 2ptr partition, random pivot, cutoff=50:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: book partition, lf pivot, cutoff=50:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: 2ptr partition, lf pivot, cutoff=50:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: 3 Partition, cutoff=50:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: book partition, random pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: 2ptr partition, random pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: book partition, lf pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: 2ptr partition, lf pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
QS: 3 Partition, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
Almost QS book partition, random pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
Almost QS 2ptr partition, random pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
Almost QS 3 Partition, random pivot, cutoff=2:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
HeapSortTD:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
HeapSortBU:
    26.25.24.23.22.21.20.19.18.17.16.15.14.13.12.11.10.9.8.7.6.5.4.3.2.1.0.
Bigger, QS: book partition, random pivot, cutoff=50:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: 2ptr partition, random pivot, cutoff=50:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: book partition, lf pivot, cutoff=50:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: 2ptr partition, lf pivot, cutoff=50:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: 3 Partition, cutoff=50:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: book partition, random pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: 2ptr partition, random pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: book partition, lf pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: 2ptr partition, lf pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, QS: 3 Partition, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, Almost QS book partition, random pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, Almost QS 2ptr partition, random pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, Almost QS 3 Partition, random pivot, cutoff=2:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, HeapSortTD:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.
Bigger, HeapSortBU:
    100.631.1162.1693.2224.2755.3286.3817.4348.4879.5410.5941.6472.7003.7534.8065.8596.
    9127.9658.10189.10720.11251.11782.12313.12844.13375.13906.14437.14968.15499.16030.
    16561.17092.17623.18154.18685.19216.19747. done.

Testing Fearless Leader's sorts on a huge array.

Java's QuickSort on random data runs in 5.830 seconds.
Java's QuickSort on the test sets runs in 1.575 seconds.


Fearless Leader's QS: book partition, random pivot, cutoff=50
    on all random values runs in 5.658 seconds.      Java Ratio = 0.97
    on the test sets runs in 3.609 seconds.          Java Ratio = 2.29

Fearless Leader's QS: 2ptr partition, random pivot, cutoff=50
    on all random values runs in 5.400 seconds.      Java Ratio = 0.92
    on the test sets runs in 2.746 seconds.          Java Ratio = 1.74

Fearless Leader's QS: book partition, lf pivot, cutoff=50
    on all random values runs in 5.720 seconds.      Java Ratio = 0.98

Fearless Leader's QS: 2ptr partition, lf pivot, cutoff=50
    on all random values runs in 5.391 seconds.      Java Ratio = 0.92

Fearless Leader's QS: 3 Partition, cutoff=50
    on all random values runs in 6.123 seconds.      Java Ratio = 1.05
    on the test sets runs in 4.018 seconds.          Java Ratio = 2.55

Fearless Leader's QS: book partition, random pivot, cutoff=2
    on all random values runs in 7.472 seconds.      Java Ratio = 1.28
    on the test sets runs in 5.170 seconds.          Java Ratio = 3.28

Fearless Leader's QS: 2ptr partition, random pivot, cutoff=2
    on all random values runs in 7.539 seconds.      Java Ratio = 1.29
    on the test sets runs in 4.653 seconds.          Java Ratio = 2.95

Fearless Leader's QS: book partition, lf pivot, cutoff=2
    on all random values runs in 6.289 seconds.      Java Ratio = 1.07

Fearless Leader's QS: 2ptr partition, lf pivot, cutoff=2
    on all random values runs in 6.330 seconds.      Java Ratio = 1.08

Fearless Leader's QS: 3 Partition, cutoff=2
    on all random values runs in 8.011 seconds.      Java Ratio = 1.37
    on the test sets runs in 5.597 seconds.          Java Ratio = 3.55

Fearless Leader's Almost QS book partition, random pivot, cutoff=2
    on all random values runs in 7.338 seconds.      Java Ratio = 1.25
    on the test sets runs in 5.040 seconds.          Java Ratio = 3.20

Fearless Leader's Almost QS 2ptr partition, random pivot, cutoff=2
    on all random values runs in 7.380 seconds.      Java Ratio = 1.26
    on the test sets runs in 4.510 seconds.          Java Ratio = 2.86

Fearless Leader's Almost QS 3 Partition, random pivot, cutoff=2
    on all random values runs in 7.354 seconds.      Java Ratio = 1.26
    on the test sets runs in 5.446 seconds.          Java Ratio = 3.45

Fearless Leader's HeapSortTD
    on all random values runs in 11.652 seconds.      Java Ratio = 1.99
    on the test sets runs in 7.019 seconds.          Java Ratio = 4.45

Fearless Leader's HeapSortBU
    on all random values runs in 14.027 seconds.      Java Ratio = 2.40
    on the test sets runs in 6.503 seconds.          Java Ratio = 4.12

Done testing Fearless Leader methods.

*/

        public static void main(String[] args) throws IOException {

            final int largeSize = 10000000;
            int[] number, goodone;
            int n, differ, sort, fill, i;
            boolean[] Sort_OK = { true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true };
            long before, after, yourtotal, javarandomtotal, javaothertotal;
            number = new int[20000];
            goodone = new int[20000];
            String[] SortType = {
                    "QS: book partition, random pivot, cutoff=50",
                    "QS: 2ptr partition, random pivot, cutoff=50",
                    "QS: book partition, lf pivot, cutoff=50",
                    "QS: 2ptr partition, lf pivot, cutoff=50",
                    "QS: 3 Partition, cutoff=50",
                    "QS: book partition, random pivot, cutoff=2",
                    "QS: 2ptr partition, random pivot, cutoff=2",
                    "QS: book partition, lf pivot, cutoff=2",
                    "QS: 2ptr partition, lf pivot, cutoff=2",
                    "QS: 3 Partition, cutoff=2",
                    "Almost QS book partition, random pivot, cutoff=2",
                    "Almost QS 2ptr partition, random pivot, cutoff=2",
                    "Almost QS 3 Partition, random pivot, cutoff=2",
                    "HeapSortTD",
                    "HeapSortBU" };
            String[] FillType = { "Increasing", "Decreasing", "Random", "Constant",
                    "Mids" };

            int numSorts = SortType.length;
            System.out.println("Testing " + ArraySorts.myName() + "'s program.");

            // First check to see if they sort small arrays properly
            for (sort = 0; sort < numSorts; sort++) {
                System.out.print(SortType[sort] + ": \n    ");
                for (n = 26; n >= 0 && Sort_OK[sort]; n--) {
                    System.out.print(n + ".");

                    for (fill = 0; fill < FillType.length && Sort_OK[sort]; fill++) {
                        fillArray(fill, number, 0, n);
                        for (i = 0; i < n; i++)
                            goodone[i] = number[i];
                        callSort(sort + 1, number, n);
                        Arrays.sort(goodone, 0, n);
                        differ = same(goodone, number, n);
                        if (differ != -1) {
                            System.out.println();
                            System.out.println("*** " + FillType[fill]
                                    + " sorting error: " + SortType[sort]
                                    + ", position #" + differ);
                            System.out.println("*** No more " + SortType[sort]
                                    + "s will be tested.  Correct values and "
                                    + ArraySorts.myName() + "'s values follow.");
                            Sort_OK[sort] = false;
                            mydisplay(goodone, n);
                            mydisplay(number, n);
                        }
                    }
                }
                System.out.println();
            }

            // Now check larger arrays
            for (sort = 0; sort < numSorts; sort++) {
                System.out.print("Bigger, " + SortType[sort] + ":\n    ");
                for (n = 100; n <= 20000 && Sort_OK[sort]; n = n + 531) {
                    System.out.print(n + ".");
                    if (n == 8596 || n == 16030) {
                        System.out.println();
                        System.out.print("    ");
                    }

                    for (fill = 0; fill < FillType.length && Sort_OK[sort]; fill++) {
                        fillArray(fill, number, 1, n);
                        for (i = 0; i < n; i++)
                            goodone[i] = number[i];
                        callSort(sort + 1, number, n);
                        Arrays.sort(goodone, 0, n);
                        differ = same(goodone, number, n);
                        if (differ != -1) {
                            System.out.println();
                            System.out.println("*** " + FillType[fill]
                                    + " sorting error: " + SortType[sort]
                                    + ", position #" + differ);
                            System.out.println("*** No more " + SortType[sort]
                                    + "s will be tested.  Correct values and "
                                    + ArraySorts.myName() + "'s values follow.");
                            Sort_OK[sort] = false;
                        }
                    }
                }
                System.out.println(" done.");
            }
            System.out.println();

            // Now see how fast your sorts really are
            System.out.println("Testing " + ArraySorts.myName()
                    + "'s sorts on a huge array.");
            number = new int[largeSize];
            javarandomtotal = 0;
            for (fill = 0; fill < 5; fill++) {
                fillArray(2, number, 1, largeSize);
                before = System.currentTimeMillis();
                Arrays.sort(number);
                after = System.currentTimeMillis();
                javarandomtotal = javarandomtotal + after - before;
            }
            System.out.println("\nJava's QuickSort on random data runs in "
                    + javarandomtotal / 1000 + "." + millis(javarandomtotal % 1000)
                    + " seconds.");

            javaothertotal = 0;
            for (fill = 0; fill < 5; fill++) {
                fillArray(fill, number, 1, largeSize);
                before = System.currentTimeMillis();
                Arrays.sort(number);
                after = System.currentTimeMillis();
                javaothertotal = javaothertotal + after - before;
            }
            System.out.println("Java's QuickSort on the test sets runs in "
                    + javaothertotal / 1000 + "." + millis(javaothertotal % 1000)
                    + " seconds.");
            System.out.println();

            for (int sortNum = 0; sortNum < numSorts; sortNum++) {
                if (Sort_OK[sortNum]) {
                    yourtotal = 0;
                    for (fill = 0; fill < 5; fill++) {
                        fillArray(2, number, 1, largeSize);
                        before = System.currentTimeMillis();
                        callSort(sortNum + 1, number, largeSize);
                        after = System.currentTimeMillis();
                        yourtotal = yourtotal + after - before;
                    }
                    System.out.print("\n" + ArraySorts.myName() + "'s "
                            + SortType[sortNum]
                            + "\n    on all random values runs in " + yourtotal
                            / 1000 + "." + millis(yourtotal % 1000) + " seconds.");
                    System.out
                            .println("      Java Ratio = "
                                    + (yourtotal / javarandomtotal)
                                    + "."
                                    + hundreds((int) (((float) (yourtotal)
                                    / javarandomtotal - (yourtotal / javarandomtotal)) * 100)));
                    if ( sortNum != 2 && sortNum != 3 && sortNum != 7 && sortNum != 8 ) { //sortNum == 1 || sortNum == 6) {
                        yourtotal = 0;
                        for (fill = 0; fill < 5; fill++) {
                            fillArray(fill, number, 1, largeSize);
                            before = System.currentTimeMillis();
                            callSort(sortNum + 1, number, largeSize);
                            after = System.currentTimeMillis();
                            yourtotal = yourtotal + after - before;
                        }
                        System.out.print("    on the test sets runs in "
                                + yourtotal / 1000 + "." + millis(yourtotal % 1000)
                                + " seconds.");
                        System.out
                                .print("          Java Ratio = "
                                        + (yourtotal / javaothertotal)
                                        + "."
                                        + hundreds((int) (((float) (yourtotal)
                                        / javaothertotal - (yourtotal / javaothertotal)) * 100)));
                        System.out.println();
                    }
                } else {
                    System.out.println(ArraySorts.myName() + "'s "
                            + SortType[sortNum] + " not tested.");
                    System.out.println();
                }
            }
            System.out.println("\nDone testing " + ArraySorts.myName()
                    + " methods.");
        }

        private static void callSort(int which, int[] a, int n) {
            if (which == 1)
                ArraySorts.QuickSort1(a, n, 50);
            else if (which == 2)
                ArraySorts.QuickSort2(a, n, 50);
            else if (which == 3)
                ArraySorts.QuickSort3(a, n, 50);
            else if (which == 4)
                ArraySorts.QuickSort4(a, n, 50);
            else if (which == 5)
                ArraySorts.QuickSort5(a, n, 50);
            else if (which == 6)
                ArraySorts.QuickSort1(a, n, 2);
            else if (which == 7)
                ArraySorts.QuickSort2(a, n, 2);
            else if (which == 8)
                ArraySorts.QuickSort3(a, n, 2);
            else if (which == 9)
                ArraySorts.QuickSort4(a, n, 2);
            else if (which == 10)
                ArraySorts.QuickSort5(a, n, 2);
            else if (which == 11)
                ArraySorts.AlmostQS1(a, n, 2);
            else if (which == 12)
                ArraySorts.AlmostQS2(a, n, 2);
            else if (which == 13)
                ArraySorts.AlmostQS5(a, n, 2);
            else if (which == 14)
                ArraySorts.HeapSortTD(a, n);
            else if (which == 15)
                ArraySorts.HeapSortBU(a, n);
            else
                System.out.println("No such sort " + which + ".");
        }

        private static void fillArray(int fillNumber, int[] number, int BigorSmall,
                                      int n) {
            if (fillNumber == 0)
                fillIncreasing(number, n);
            else if (fillNumber == 1)
                fillDecreasing(number, n);
            else if (fillNumber == 2) {
                if (BigorSmall == 0)
                    fillRandomSmall(number, n);
                else
                    fillRandomBig(number, n);
            } else if (fillNumber == 3)
                fillConstant(number, n);
            else if (fillNumber == 4)
                fillMids(number, n);
            else
                System.out.println("No such filling scheme.");
        }

        public static int same(int[] a, int[] b, int n) {
            int result = -1;
            for (int i = 0; (i < n) && (result == -1); i++)
                if (a[i] != b[i])
                    result = i;
            return result;
        }

        public static void mydisplay(int[] a, int n) {
            for (int i = 0; i < n; i++)
                System.out.print(a[i] + " ");
            System.out.println();
        }

        public static void fillMids(int[] a, int n) {
            fillMids(a, 0, n - 1, 0);
        }

        private static void fillMids(int[] a, int lf, int rt, int ct) {

            while (lf <= rt) {
                a[lf] = ct;
                ct++;
                lf++;
                a[rt] = ct;
                ct++;
                rt--;
            }
        }

        public static void fillConstant(int a[], int n) {
            int inc = n / Math.max(1, (int) Math.log((double) n));
            for (int i = 0; i < n; i++) {
                a[i] = 52;
            }
            for (int i = 0; i < n; i += inc) {
                if (i % 2 == 0)
                    a[i] = 51;
                else
                    a[i] = 53;
            }
        }

        public static void fillIncreasing(int[] a, int n) {
            for (int i = 0; i < n; i++)
                a[i] = i;
            if (n > 2) {
                a[n - 2] = 2;
                a[1] = n - 2;
            }
        }

        public static void fillDecreasing(int[] a, int n) {
            for (int i = 0; i < n; i++)
                a[i] = n - i - 1;
            if (n > 2) {
                a[n - 2] = n - 2;
                a[1] = 1;
            }
        }

        public static void fillRandomSmall(int a[], int n) {
            for (int i = 0; i < n; i++)
                a[i] = (int) (Math.random() * n);
        }

        public static void fillRandomBig(int a[], int n) {
            for (int i = 0; i < n; i++)
                a[i] = (int) (Math.random() * n * 10);
        }

        public static String millis(long n) {
            String rtn;
            if (n < 10)
                rtn = "00" + n;
            else if (n < 100)
                rtn = "0" + n;
            else
                rtn = String.valueOf(n);
            return rtn;
        }

        public static String hundreds(long n) {
            String rtn;
            if (n < 10)
                rtn = "0" + n;
            else
                rtn = String.valueOf(n);
            return rtn;
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