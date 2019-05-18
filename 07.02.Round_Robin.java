/*
7. Design, develop and implement a C/C++/Java program to simulate the working of
Shortest remaining time and Round Robin (RR) scheduling algorithms. Experiment
with different quantum sizes for RR algorithm.

Round Robin Part in this file
*/

import java.util.LinkedList;
import java.util.Scanner;

class Process {
    public int processId;
    public int burstTime;
    public int waitingTime;
    public int turnAroundTime;
    public int remainingTime;
    public int lastAccessTime;

    Process(int processId, int burstTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

class RoundRobin {

    private static LinkedList<Process> processQueue;
    private static Process[] processes;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of Processes");
        int n = scanner.nextInt();
        processes = new Process[n];
        processQueue = new LinkedList<>();

        System.out.println("Enter details");
        for (int i = 0; i < n; i++) {
            System.out.println("Enter burst time for Process " + (i + 1));
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);

            processQueue.add(processes[i]);
        }

        System.out.println("Enter Quantum Size");
        int quantumSize = scanner.nextInt();

        int currentTime = 0;
        System.out.println("Process Scheduling");
        while (!processQueue.isEmpty()) {
            Process p = processQueue.pollFirst();
            p.waitingTime += currentTime - p.lastAccessTime;
            System.out.println("Current Time: " + currentTime + ", Id: " + p.processId);

            if (p.remainingTime > quantumSize) {
                p.remainingTime -= quantumSize;
                currentTime += quantumSize;
                p.lastAccessTime = currentTime;

                processQueue.offerLast(p);
            } else {
                currentTime += p.remainingTime;
                p.remainingTime = 0;
            }
        }
        System.out.println("Current Time: " + currentTime);

        float averageWaitTime = 0f, averageTurnAroundTime = 0f;
        for (int i = 0; i < n; i++) {
            processes[i].turnAroundTime = processes[i].burstTime + processes[i].waitingTime;

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
3
Enter details
Enter burst time for Process 1
24
Enter burst time for Process 2
3
Enter burst time for Process 3
3
Enter Quantum Size
4
Process Scheduling
Current Time: 0, Id: 1
Current Time: 4, Id: 2
Current Time: 7, Id: 3
Current Time: 10, Id: 1
Current Time: 14, Id: 1
Current Time: 18, Id: 1
Current Time: 22, Id: 1
Current Time: 26, Id: 1
Current Time: 30
Average Wait Time: 5.6666665, Average Turn Around Time: 15.666667
*/
