/*
9. Design, develop and implement a C/C++/Java program to implement page
replacement algorithms LRU and FIFO. Assume suitable input required to
demonstrate the results.

FIFO Part
*/

import java.util.Arrays;
import java.util.Scanner;

class CacheFIFO {

    private static int[] cache;
    private static int nF, nextFrameIndex = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of page requests");
        int nR = scanner.nextInt();

        int[] pageNumbers = new int[nR];
        System.out.println("Enter page requests");
        for (int i = 0; i < nR; ++i) {
            pageNumbers[i] = scanner.nextInt();
        }

        System.out.println("Enter number of frames");
        nF = scanner.nextInt();

        cache = new int[nF];
        Arrays.fill(cache, -1);

        int totalHits = 0, totalMisses = 0;

        for (int i = 0; i < nR; ++i) {
            int index = findPageNumber(pageNumbers[i]);
            if (index != -1) {
                totalHits += 1;
            } else {
                totalMisses += 1;
                cache[nextFrameIndex] = pageNumbers[i];
                nextFrameIndex = (nextFrameIndex + 1) % nF;
            }
            printCache();
        }

        System.out.println("Total Hits " + totalHits);
        System.out.println("Total Misses " + totalMisses);
        float hitRatio = ((float) totalHits) / (totalHits + totalMisses);
        System.out.println("Hit Ratio " + hitRatio);

    }

    public static int findPageNumber(int pageNumber) {
        for (int i = 0; i < nF; ++i) {
            if (cache[i] == pageNumber) {
                return i;
            }
        }
        return -1;
    }

    public static void printCache() {
        System.out.print("Cache Content: ");
        for (int i = 0; i < nF; ++i) {
            System.out.print(cache[i] + " ");
        }
        System.out.println();
    }
}

/*
Output:

Enter number of page requests
10
Enter page requests
2 3 5 4 2 5 7 3 8 7
Enter number of frames
3
Cache Content: 2 -1 -1
Cache Content: 2 3 -1
Cache Content: 2 3 5
Cache Content: 4 3 5
Cache Content: 4 2 5
Cache Content: 4 2 5
Cache Content: 4 2 7
Cache Content: 3 2 7
Cache Content: 3 8 7
Cache Content: 3 8 7
Total Hits 2
Total Misses 8
Hit Ratio 0.2
*/