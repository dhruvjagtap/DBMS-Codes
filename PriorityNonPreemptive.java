import java.util.*;

public class PriorityNonPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] at = new int[n], bt = new int[n], pt = new int[n];
        int[] ct = new int[n], tat = new int[n], wt = new int[n];
        boolean[] completed = new boolean[n];

        System.out.println("\n---- Note: Lower number = Higher Priority ----");

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for process " + (i + 1) + ":");
            System.out.print("Arrival time: ");
            at[i] = sc.nextInt();
            System.out.print("Burst time: ");
            bt[i] = sc.nextInt();
            System.out.print("Priority: ");
            pt[i] = sc.nextInt();
        }

        // ✅ For Gantt Chart
        List<Integer> ganttProcess = new ArrayList<>();
        List<Integer> ganttStart = new ArrayList<>();
        List<Integer> ganttEnd = new ArrayList<>();

        int completedCount = 0, currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        while (completedCount < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            // Find process with highest priority that has arrived
            for (int i = 0; i < n; i++) {
                if (!completed[i] && at[i] <= currentTime && pt[i] < highestPriority) {
                    idx = i;
                    highestPriority = pt[i];
                }
            }

            // If no process has arrived, increment currentTime (CPU idle)
            if (idx == -1) {
                currentTime++;
                continue;
            }

            // Record Gantt Chart segment
            ganttProcess.add(idx + 1); // process ID
            ganttStart.add(currentTime);

            // Run the selected process fully
            currentTime += bt[idx];

            ganttEnd.add(currentTime); // record end time

            // Compute times
            ct[idx] = currentTime;
            tat[idx] = ct[idx] - at[idx];
            wt[idx] = tat[idx] - bt[idx];

            totalTAT += tat[idx];
            totalWT += wt[idx];

            completed[idx] = true;
            completedCount++;
        }

        sc.close();

        // ✅ Display Results
        System.out.println("\n------------------------------------------------");
        System.out.println("P\tAT\tBT\tPT\tCT\tTAT\tWT");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
                    i + 1, at[i], bt[i], pt[i], ct[i], tat[i], wt[i]);
        }
        System.out.println("------------------------------------------------");
        System.out.printf("Average Turnaround Time: %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / n);
        System.out.println("------------------------------------------------");

        // ✅ Gantt Chart
        System.out.println("\nGantt Chart:");
        System.out.print(" ");
        for (int i = 0; i < ganttProcess.size(); i++)
            System.out.print("--------");
        System.out.println();

        System.out.print("|");
        for (int i = 0; i < ganttProcess.size(); i++)
            System.out.print("  P" + ganttProcess.get(i) + "   |");
        System.out.println();

        System.out.print(" ");
        for (int i = 0; i < ganttProcess.size(); i++)
            System.out.print("--------");
        System.out.println();

        System.out.print(ganttStart.get(0));
        for (int i = 0; i < ganttEnd.size(); i++)
            System.out.printf("%8d", ganttEnd.get(i));
        System.out.println();
    }
}
