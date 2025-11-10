import java.util.*;

public class RoundRobin {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] at = new int[n];
        int[] bt = new int[n];
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];
        int[] rt = new int[n];
        int completed = 0;

        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.println("\nEnter details for Process " + pid[i] + ":");
            System.out.print("Arrival Time: ");
            at[i] = sc.nextInt();
            System.out.print("Burst Time: ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }

        System.out.print("\nEnter Time Quantum: ");
        int tq = sc.nextInt();

        sc.close();

        List<Integer> ganttProcess = new ArrayList<>();
        List<Integer> ganttStart = new ArrayList<>();
        List<Integer> ganttEnd = new ArrayList<>();

        Queue<Integer> readyQueue = new LinkedList<>();
        boolean[] visited = new boolean[n];

        int currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++)
            index[i] = i;
        Arrays.sort(index, Comparator.comparingInt(i -> at[i]));

        readyQueue.add(index[0]);
        visited[index[0]] = true;
        currentTime = at[index[0]];

        while (!readyQueue.isEmpty()) {
            int i = readyQueue.poll();
            ganttProcess.add(pid[i]);
            ganttStart.add(currentTime);

            if (rt[i] > tq) {
                rt[i] -= tq;
                currentTime += tq;
            } else {
                currentTime += rt[i];
                rt[i] = 0;
                completed++;

                ct[i] = currentTime;
                tat[i] = ct[i] - at[i];
                wt[i] = tat[i] - bt[i];
                totalTAT += tat[i];
                totalWT += wt[i];
            }

            ganttEnd.add(currentTime);

            for (int j = 0; j < n; j++) {
                if (!visited[j] && at[j] <= currentTime) {
                    readyQueue.add(j);
                    visited[j] = true;
                }
            }

            if (rt[i] > 0) {
                readyQueue.add(i);
            }

            if (readyQueue.isEmpty()) {
                for (int j = 0; j < n; j++) {
                    if (!visited[j]) {
                        readyQueue.add(j);
                        visited[j] = true;
                        currentTime = at[j];
                        break;
                    }
                }
            }
        }

        // Output results
        System.out.println("\n------------------------------------------------------------");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out
                    .println("P" + pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }
        System.out.println("------------------------------------------------------------");
        System.out.printf("Average Turnaround Time: %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / n);
        System.out.println("------------------------------------------------------------");

        System.out.println("\nGantt Chart:");
        System.out.print(" ");
        for (int i = 0; i < ganttProcess.size(); i++) {
            System.out.print("--------");
        }
        System.out.println();
        System.out.print("|");
        for (int i = 0; i < ganttProcess.size(); i++) {
            System.out.print("  P" + ganttProcess.get(i) + "   |");
        }
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < ganttProcess.size(); i++) {
            System.out.print("--------");
        }
        System.out.println();

        System.out.print(ganttStart.get(0));
        for (int i = 0; i < ganttEnd.size(); i++) {
            System.out.printf("%8d", ganttEnd.get(i));
        }
        System.out.println();
    }
}
