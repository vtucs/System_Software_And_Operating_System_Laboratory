/*
8. Design, develop and implement a C/C++/Java program to implement Banker’s
algorithm. Assume suitable input required to demonstrate the results.

Note: All the inputs are based on index 1 (i.e. Process number starts from 1)
*/

import java.util.Scanner;

class Bankers {
    static int[][] allocation, maximum, need;
    static int[] available, request;
    static boolean[] finished;
    static int nProcess, nResource;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of processes");
        nProcess = scanner.nextInt();
        System.out.println("Enter number of resources");
        nResource = scanner.nextInt();

        allocation = new int[nProcess][nResource];
        maximum = new int[nProcess][nResource];
        need = new int[nProcess][nResource];
        finished = new boolean[nProcess];

        System.out.println("Enter Allocation for each process and for each resource");
        for (int i = 0; i < nProcess; i++) {
            for (int j = 0; j < nResource; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter Maximum value for each process and for each resource");
        for (int i = 0; i < nProcess; i++) {
            for (int j = 0; j < nResource; j++) {
                maximum[i][j] = scanner.nextInt();
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }

        available = new int[nResource];

        System.out.println("Enter availability for each resource");
        for (int j = 0; j < nResource; j++) {
            available[j] = scanner.nextInt();
        }

        System.out.println("Is there any resource request? (Reply 1 for yes or 0 for no)");
        int response = scanner.nextInt();

        if (response == 1) {
            request = new int[nResource];
            System.out.println("Enter Process Number");
            int p = scanner.nextInt();
            System.out.println("Enter Resource Requests");
            for (int i = 0; i < nResource; i++) {
                request[i] = scanner.nextInt();
            }

            if (!isArrayLessThanOrEqual(request, need[p - 1])) {
                System.out.println("Error, Request cannot be satisfied");
                System.exit(0);
            }

            if (!isArrayLessThanOrEqual(request, available)) {
                System.out.println("Process " + p + " must wait");
            }

            subtractArrays(available, request);
            addArrays(allocation[p - 1], request);
            subtractArrays(need[p - 1], request);

            System.out.println("New Allocation");
            printMatrix(allocation);

            System.out.println("New Need");
            printMatrix(need);
        }

        if (checkForSafeSequence()) {
            System.out.println("System is in Safe State");
        } else {
            System.out.println("System is Not in Safe State");
        }

    }

    static boolean checkForSafeSequence() {
        int[] work = available.clone();
        for (int k = 0; k < nProcess; k++) {
            for (int i = 0; i < nProcess; i++) {
                if (!finished[i] && isArrayLessThanOrEqual(need[i], work)) {
                    addArrays(work, allocation[i]);
                    finished[i] = true;
                    System.out.println("Process Number " + (i + 1));
                    break;
                }
            }
        }

        return areAllFinished();
    }

    static boolean isArrayLessThanOrEqual(int[] a, int[] b) {
        for (int i = 0; i < b.length; i++) {
            if (a[i] > b[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean areAllFinished() {
        for (int i = 0; i < finished.length; i++) {
            if (!finished[i]) {
                return false;
            }
        }
        return true;
    }

    static void addArrays(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] += b[i];
        }
    }

    static void subtractArrays(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] -= b[i];
        }
    }

    static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*
Output:

Enter number of processes
5
Enter number of resources
3
Enter Allocation for each process and for each resource
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
Enter Maximum value for each process and for each resource
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
Enter availability for each resource
3 3 2
Is there any resource request? (Reply 1 for yes or 0 for no)
1
Enter Process Number
2
Enter Resource Requests
1 0 2
New Allocation
0 1 0
3 0 2
3 0 2
2 1 1
0 0 2
New Need
7 4 3
0 2 0
6 0 0
0 1 1
4 3 1
Process Number 2
Process Number 4
Process Number 1
Process Number 3
Process Number 5
System is in Safe State
*/
