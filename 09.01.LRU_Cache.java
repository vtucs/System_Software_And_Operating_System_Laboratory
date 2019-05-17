import java.util.Scanner;

class Frame {
    int pageNumber = -1;
    int lastAccessTime = -1;

    void replaceFrame(int pageNumber, int lastAccessTime) {
        this.pageNumber = pageNumber;
        this.lastAccessTime = lastAccessTime;
    }

    void refreshFrame(int lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}

class CacheLRU {

    private static Frame[] cache;
    private static int nF;

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

        cache = new Frame[nF];
        for (int i = 0; i < nF; ++i) {
            cache[i] = new Frame();
        }

        int totalHits = 0, totalMisses = 0;

        for (int i = 0; i < nR; ++i) {
            int index = findPageNumber(pageNumbers[i]);
            if (index != -1) {
                totalHits += 1;
                cache[index].refreshFrame(i);
            } else {
                totalMisses += 1;
                int temp = getLruFrameIndex();
                cache[temp].replaceFrame(pageNumbers[i], i);
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
            if (cache[i].pageNumber == pageNumber) {
                return i;
            }
        }
        return -1;
    }

    public static int getLruFrameIndex() {
        int min = cache[0].lastAccessTime;
        int index = 0;

        for (int i = 1; i < nF; ++i) {
            if (cache[i].lastAccessTime < min) {
                min = cache[i].lastAccessTime;
                index = i;
            }
        }
        return index;
    }

    public static void printCache() {
        System.out.print("Cache Content: ");
        for (int i = 0; i < nF; ++i) {
            System.out.print(cache[i].pageNumber + " ");
        }
        System.out.println();
    }
}

/*
Output:

Enter number of page requests
13
Enter page requests
7 0 1 2 0 3 0 4 2 3 0 3 2
Enter number of frames
4
Cache Content: 7 -1 -1 -1
Cache Content: 7 0 -1 -1
Cache Content: 7 0 1 -1
Cache Content: 7 0 1 2
Cache Content: 7 0 1 2
Cache Content: 3 0 1 2
Cache Content: 3 0 1 2
Cache Content: 3 0 4 2
Cache Content: 3 0 4 2
Cache Content: 3 0 4 2
Cache Content: 3 0 4 2
Cache Content: 3 0 4 2
Cache Content: 3 0 4 2
Total Hits 7
Total Misses 6
Hit Ratio 0.53846157
*/