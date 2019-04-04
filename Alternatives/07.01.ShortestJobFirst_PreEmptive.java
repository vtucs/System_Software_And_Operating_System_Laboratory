/*
7. Design, develop and implement a C/C++/Java program to simulate the working of
Shortest remaining time and Round Robin (RR) scheduling algorithms. Experiment
with different quantum sizes for RR algorithm.

SJF Part in this file
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

class Process implements Comparable<Process> {
    public int processId;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int waitingTime;

    Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
    }

    @Override
    public int compareTo(Process p) {
        if (this.arrivalTime == p.arrivalTime) {
            return Integer.compare(this.burstTime, p.burstTime);
        } else {
            return Integer.compare(this.arrivalTime, p.arrivalTime);
        }
    }
}

class SJF {

    private static Process[] processes;
    private static TreeSet<Integer> arrivalTimes;
    private static ArrayList<Process> arrivedProcesses;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of Processes");
        int n = scanner.nextInt();
        processes = new Process[n];
        System.out.println("Enter details");

        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time for Process " + (i + 1));
            int arrivalTime = scanner.nextInt();
            System.out.println("Enter burst time for Process " + (i + 1));
            int burstTime = scanner.nextInt();
            System.out.println();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        Arrays.sort(processes);

        arrivalTimes = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            arrivalTimes.add(processes[i].arrivalTime);
        }

        arrivedProcesses = new ArrayList<>();

        System.out.println("Process Scheduling");
        sjf();

        float totalWaitingTime = 0f;
        System.out.println("\nWaiting Time");
        for (Process p : processes) {
            System.out.println("Process " + p.processId + ", Waiting Time: " + p.waitingTime);
            totalWaitingTime += p.waitingTime;
        }

        System.out.println("\nAverage Waiting Time: " + (totalWaitingTime / n));
    }

    private static void sjf() {
        int processCursor = 0;
        int currentTime = 0;
        boolean checkForNextMinimumProcess = false;
        Process minProcess = null;

        while (true) {

            if (arrivalTimes.contains(currentTime)) {
                while (processCursor < processes.length && processes[processCursor].arrivalTime <= currentTime) {
                    arrivedProcesses.add(processes[processCursor]);
                    processCursor += 1;
                }
                checkForNextMinimumProcess = true;
            }

            if (checkForNextMinimumProcess) {
                int minRemainingTime = Integer.MAX_VALUE;
                for (Process p : arrivedProcesses) {
                    if (p.remainingTime < minRemainingTime) {
                        minProcess = p;
                        minRemainingTime = p.remainingTime;
                    }
                }

                checkForNextMinimumProcess = false;
            }

            for (Process p : arrivedProcesses) {
                if (p == minProcess) {
                    continue;
                }
                p.waitingTime += 1;
            }

            if (minProcess != null) {
                minProcess.remainingTime -= 1;

                System.out.println("Current Time: " + currentTime + " , Process: " + minProcess.processId);

                if (minProcess.remainingTime == 0) {
                    arrivedProcesses.remove(minProcess);
                    checkForNextMinimumProcess = true;
                    minProcess = null;
                }
            }

            currentTime += 1;

            if (arrivedProcesses.isEmpty() && currentTime > arrivalTimes.last()) {
                break;
            }
        }
        System.out.println("Current Time: " + currentTime);
    }
}

/*
Output:

Enter number of Processes
4
Enter details
Enter arrival time for Process 1
0
Enter burst time for Process 1
8

Enter arrival time for Process 2
1
Enter burst time for Process 2
4

Enter arrival time for Process 3
2
Enter burst time for Process 3
9

Enter arrival time for Process 4
3
Enter burst time for Process 4
5

Process Scheduling
CurrentTime: 0 , Process: 1
CurrentTime: 1 , Process: 2
CurrentTime: 2 , Process: 2
CurrentTime: 3 , Process: 2
CurrentTime: 4 , Process: 2
CurrentTime: 5 , Process: 4
CurrentTime: 6 , Process: 4
CurrentTime: 7 , Process: 4
CurrentTime: 8 , Process: 4
CurrentTime: 9 , Process: 4
CurrentTime: 10 , Process: 1
CurrentTime: 11 , Process: 1
CurrentTime: 12 , Process: 1
CurrentTime: 13 , Process: 1
CurrentTime: 14 , Process: 1
CurrentTime: 15 , Process: 1
CurrentTime: 16 , Process: 1
CurrentTime: 17 , Process: 3
CurrentTime: 18 , Process: 3
CurrentTime: 19 , Process: 3
CurrentTime: 20 , Process: 3
CurrentTime: 21 , Process: 3
CurrentTime: 22 , Process: 3
CurrentTime: 23 , Process: 3
CurrentTime: 24 , Process: 3
CurrentTime: 25 , Process: 3
Current Time: 26

Waiting Time
Process 1, Waiting Time: 9
Process 2, Waiting Time: 0
Process 3, Waiting Time: 15
Process 4, Waiting Time: 2

Average Waiting Time: 6.5
*/
