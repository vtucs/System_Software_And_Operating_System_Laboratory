/*
7. Design, develop and implement a C/C++/Java program to simulate the working of
Shortest remaining time and Round Robin (RR) scheduling algorithms. Experiment
with different quantum sizes for RR algorithm.

SJF Part in this file
*/

import java.util.Arrays;
import java.util.Scanner;

class Process implements Comparable<Process> {
    public int processId;
    public int burstTime;
    public int waitingTime;
    public int turnAroundTime;

    Process(int processId, int burstTime) {
        this.processId = processId;
        this.burstTime = burstTime;
    }

    @Override
    public int compareTo(Process p) {
        return Integer.compare(this.burstTime, p.burstTime);
    }
}

class SJF {

    private static Process[] processes;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of Processes");
        int n = scanner.nextInt();
        processes = new Process[n];
        System.out.println("Enter details");

        for (int i = 0; i < n; i++) {
            System.out.println("Enter burst time for Process " + (i + 1));
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);
        }

        Arrays.sort(processes);

        int accumulateWaitTime = 0;
        for (int i = 0; i < n; i++) {
            processes[i].waitingTime = accumulateWaitTime;
            processes[i].turnAroundTime = processes[i].burstTime + processes[i].waitingTime;
            accumulateWaitTime += processes[i].burstTime;
        }

        System.out.println("Process Scheduling");
        for (int i = 0; i < n; i++) {
            System.out.print(String.format("Id: %s, Burst Time: %s, Waiting Time: %s, Turn Around Time: %s\n",
                    processes[i].processId, processes[i].burstTime, processes[i].waitingTime, processes[i].turnAroundTime
            ));
        }

        float averageWaitTime = 0f, averageTurnAroundTime = 0f;
        for (int i = 0; i < n; i++) {
            averageWaitTime += processes[i].waitingTime;
            averageTurnAroundTime += processes[i].turnAroundTime;
        }

        averageWaitTime /= n;
        averageTurnAroundTime /= n;

        System.out.println("Average Wait Time: " + averageWaitTime + ", Average Turn Around Time: " + averageTurnAroundTime);
    }
}

/*
Output:

Enter number of Processes
4
Enter details
Enter burst time for Process 1
6
Enter burst time for Process 2
8
Enter burst time for Process 3
7
Enter burst time for Process 4
3
Process Scheduling
Id: 4, Burst Time: 3, Waiting Time: 0, Turn Around Time: 3
Id: 1, Burst Time: 6, Waiting Time: 3, Turn Around Time: 9
Id: 3, Burst Time: 7, Waiting Time: 9, Turn Around Time: 16
Id: 2, Burst Time: 8, Waiting Time: 16, Turn Around Time: 24
Average Wait Time: 7.0, Average Turn Around Time: 13.0
*/
