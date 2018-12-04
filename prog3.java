/* Michael Merabi #1460322
 This is a project that denotes the various sorting algorithms we have gone
over in class. Testing the sorts with various partitions and combinations.
*/

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

        for (int FU = left + 1; FU <= right; FU++) {
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

    private static pair twoPivotPartition(int a[], int left, int right, int leftpivot, int rightpivot) {

        boolean toggle = true;
        //Putting the random pivot in the right spot
        swap(a, rightpivot, right);
        //Putting the random pivot in the left spot.
        swap(a, leftpivot, left);


        if (a[left] > a[right]) {
            swap(a, left, right);
        }

        int ls = left;
        int fu = left + 1;
        int fb = right;

        while (fu < fb) {

            //Checking to see if smaller first
            if (a[fu] < a[left]) {
                swap(a, ls + 1, fu);
                ls++;
                fu++;
            }

            //Check to see if bigger than right
            else if (a[fu] > a[right]) {
                fb--;
                swap(a, fu, fb);
            } else {
                if (toggle) {
                    if (a[fu] == a[ls]) {
                        ls++;
                        swap(a, fu, ls);
                        fu++;
                    }
                } else {
                    fu++;
                }
                toggle = !toggle;
            }

        }
        swap(a, left, ls);
        swap(a, right, fb);

        return new pair(ls, fb);

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
        while ((right - left + 1) >= cutoff) {
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

    //quicksort + 3 pivot partition with no insertion sort
    public static void AlmostQS5(int a[], int n, int cutoff) {
        QuickSort5(a, 0, n - 1, cutoff);
    }


    // heapsort with linear build heap (NO RECURSION)
    public static void HeapSortBU(int a[], int n) {
        //requires trickle-down (building the heap)

        for (int bottom = (n - 2) / 2; bottom > -1; bottom--) {
            trickleDown(a, bottom, n - 1);
        }

        for (int top = n - 1; top > 0; top--) {
            int temp = a[top];
            a[top] = a[0];
            a[0] = temp;
            trickleDown(a, 0, top - 1);
        }
    }

    public static void HeapSortTD(int a[], int n) {
        //requires trickle-up and trickle-down (building the heap)

        for (int i = 0; i < n; i++) {
            trickleUp(a, 0, i);
        }

        for (int end = n - 1; end > 0; end--) {
            int temp = a[end];
            a[end] = a[0];
            a[0] = temp;
            trickleDown(a, 0, end - 1);
        }
    }

    private static void trickleDown(int[] a, int index, int n) {
        int temp = a[index];
        int newchild = index * 2 + 1;

        if (newchild + 1 <= n && a[newchild] < a[newchild + 1]) {
            newchild++;
        }
        while ((index * 2 + 1) <= n && temp < a[newchild]) {
            a[index] = a[newchild];
            index = newchild;
            newchild = index * 2 + 1;
            if (newchild + 1 <= n && a[newchild] < a[newchild + 1]) {
                newchild++;
            }
        }
        a[index] = temp;
    }

    private static void trickleUp(int[] a, int root, int n) {
        int parent = (n - 1) / 2;
        int hold = a[n];

        if (a[n] > a[parent]) {
            while (n > root && hold > a[(n - 1) / 2]) {
                a[n] = a[(n - 1) / 2];
                n = (n - 1) / 2;
            }
            a[n] = hold;
        }
    }

    public static String myName() {
        return "Michael Merabi";
    }
}

    //pair class to return two variables
class pair {
    public int left, right;

    public pair(int left, int right) {
        this.left = left;
        this.right = right;
    }
}