/*
7. Design, develop and implement a C/C++/Java program to simulate the working of
Shortest remaining time and Round Robin (RR) scheduling algorithms. Experiment
with different quantum sizes for RR algorithm.

Round Robin Part in this file
*/

import java.util.*;

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
        return Integer.compare(this.arrivalTime, p.arrivalTime);
    }
}

class RoundRobin {

    private static int quantumSize;
    private static Process[] processes;
    private static TreeSet<Integer> arrivalTimes;
    private static Queue<Process> arrivedProcesses;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Quantum Size");
        quantumSize = scanner.nextInt();

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

        arrivedProcesses = new LinkedList<>();

        System.out.println("Process Scheduling");
        roundRobin();

        float totalWaitingTime = 0f;
        System.out.println("\nWaiting Time");
        for (Process p : processes) {
            System.out.println("Process " + p.processId + ", Waiting Time: " + p.waitingTime);
            totalWaitingTime += p.waitingTime;
        }

        System.out.println("\nAverage Waiting Time: " + (totalWaitingTime / n));
    }

    private static void roundRobin() {
        int processCursor = 0;
        int currentTime = 0;
        Process currentProcess = null;

        while (true) {

            if (arrivalTimes.contains(currentTime)) {
                while (processCursor < processes.length && processes[processCursor].arrivalTime <= currentTime) {
                    arrivedProcesses.offer(processes[processCursor]);
                    processCursor += 1;
                }
            }

            currentProcess = arrivedProcesses.poll();

            int waitTime = 0;

            if (currentProcess != null) {
                System.out.println("Current Time: " + currentTime + " , Process: " + currentProcess.processId);

                if (currentProcess.remainingTime > quantumSize) {
                    currentTime += quantumSize;
                    waitTime = quantumSize;
                    currentProcess.remainingTime -= quantumSize;
                    arrivedProcesses.offer(currentProcess);
                } else {
                    currentTime += currentProcess.remainingTime;
                    waitTime = currentProcess.remainingTime;
                    currentProcess.remainingTime = 0;
                    arrivedProcesses.remove(currentProcess);
                }
            }

            for (Process p : arrivedProcesses) {
                if (p == currentProcess) {
                    continue;
                }
                p.waitingTime += waitTime;
            }

            if (arrivedProcesses.isEmpty() && currentTime > arrivalTimes.last()) {
                break;
            }
        }
        System.out.println("Current Time: " + currentTime);
    }
}

/*
Output:

Enter Quantum Size
2
Enter number of Processes
5
Enter details
Enter arrival time for Process 1
0
Enter burst time for Process 1
10

Enter arrival time for Process 2
0
Enter burst time for Process 2
1

Enter arrival time for Process 3
3
Enter burst time for Process 3
2

Enter arrival time for Process 4
5
Enter burst time for Process 4
1

Enter arrival time for Process 5
10
Enter burst time for Process 5
5

Process Scheduling
Current Time: 0 , Process: 1
Current Time: 2 , Process: 2
Current Time: 3 , Process: 1
Current Time: 5 , Process: 3
Current Time: 7 , Process: 1
Current Time: 9 , Process: 4
Current Time: 10 , Process: 1
Current Time: 12 , Process: 5
Current Time: 14 , Process: 1
Current Time: 16 , Process: 5
Current Time: 18 , Process: 5
Current Time: 19

Waiting Time
Process 1, Waiting Time: 6
Process 2, Waiting Time: 2
Process 3, Waiting Time: 2
Process 4, Waiting Time: 4
Process 5, Waiting Time: 4

Average Waiting Time: 3.6
*/