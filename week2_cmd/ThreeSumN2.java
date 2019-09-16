/******************************************************************************
 *  Compilation:  javac ThreeSumN2.java
 *  Execution:    java ThreeSumN2 input.txt
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   https://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program with n^2 log n running time. Reads n integers
 *  and counts the number of triples that sum to exactly 0.
 *
 *  Limitations
 *  -----------
 *     - we ignore integer overflow
 *     - doesn't handle case when input has duplicates
 *
 *
 *
 ******************************************************************************/

// package edu.princeton.cs.algs4;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

/**
 *  The {@code ThreeSumN2} class provides static methods for counting
 *  and printing the number of triples in an array of distinct integers that
 *  sum to 0 (ignoring integer overflow).
 *  <p>
 *  This implementation uses sorting and binary search and takes time 
 *  proportional to n^2 log n, where n is the number of integers.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/14analysis">Section 1.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class ThreeSumN2 {

    // Do not instantiate.
    private ThreeSumN2() { }

    // returns true if the sorted array a[] contains any duplicated integers
    private static boolean containsDuplicates(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] == a[i-1]) return true;
        return false;
    }

    /**
     * Prints to standard output the (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param a the array of integers
     * @throws IllegalArgumentException if the array contains duplicate integers
     */
    public static void printAll(int[] a) {
        int n = a.length;

        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int k = Arrays.binarySearch(a, -(a[i] + a[j]));
                if (k > j) StdOut.println(a[i] + " " + a[j] + " " + a[k]);
            }
        }
    }

    /**
     * Returns the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param a the array of integers
     * @return the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}
     */
    public static int count(int[] a) {
        int n = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
        int count = 0;
//        for (int i = 0; i < n - 1; i++) {
//            HashSet<Integer> out = new HashSet<Integer>();
//
//            for (int j = i + 1; j < n; j++) {
//                int x = -(a[i] + a[j]);
//
//                if(out.contains(x)) count++;
//                else out.add(a[j]);
//            }
//        }
        for (int i=0; i<n-1; i++) {
            // initialize left and right
            int l = i + 1;
            int r = n - 1;
            while(l < r) {
                if(a[i] + a[r] + a[l] == 0) {
                    count++;
                    l++;
                    r--;
                } else {
                    if(a[i] + a[r] + a[l] < 0) l++;
                    else r--; 
                }
            }
                
        }
//        for (int i = 0; i < n - 1; i++)
//        {
//            HashSet<Integer> s = new HashSet<Integer>();
//            for (int j = i + 1; j < n; j++)
//            {
//                int x = -(a[i] + a[j]);
//                if (s.contains(x))
//                {
//                    // System.out.printf("%d %d %d\n", x, a[i], a[j]);
//                    count++;
//                }
//                else
//                {
//                    s.add(a[j]);
//                }
//            }
//        }
        // for (int i=0; i<n-1; i++) {
//            // initialize left and right
//            int l = i + 1;
//            int r = n - 1;
//            int x = a[i];
//            while (l < r) {
//                if (x + a[l] + a[r] == 0) {
//                    // print elements if it's sum is zero
////                    System.out.print(x + " ");
////                    System.out.print(a[l] + " ");
////                    System.out.println(a[r] + " ");
//                    l++;
//                    r--;
//                    count++;
//                }
//
//                // If sum of three elements is less
//                // than zero then increment in left
//                else if (x + a[l] + a[r] < 0)
//                    l++;
//
//                    // if sum is greater than zero than
//                    // decrement in right side
//                else
//                    r--;
//            }
//        }


        return count;
    }
    
    public static boolean binarySearch(int[] a, int key, int lo, int hi){
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid])
                hi = mid - 1;
            else if (key > a[mid])
                lo = mid + 1;
            else
                return true;
        }

        return false;
    }

    /**
     * Reads in a sequence of distinct integers from a file, specified as a command-line argument;
     * counts the number of triples sum to exactly zero; prints out the time to perform
     * the computation.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  {
        In in = new In(args[0]);

        int[] a = in.readAllInts();

        Stopwatch timer = new Stopwatch();
        int count = count(a);
        StdOut.println("elapsed time = " + timer.elapsedTime());
        StdOut.println(count);
    }
}

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/