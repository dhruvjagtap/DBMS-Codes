import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of process: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] at = new int[n];
        int[] bt = new int[n];
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for process " + (i + 1) + " : ");
            pid[i] = i + 1;

            System.out.print("Enter arrival time: ");
            at[i] = sc.nextInt();

            System.out.print("Enter burst time: ");
            bt[i] = sc.nextInt();
        }

        sc.close();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (at[j] > at[j + 1]) {
                    int temp = at[j];
                    at[j] = at[j + 1];
                    at[j + 1] = temp;

                    temp = bt[j];
                    bt[j] = bt[j + 1];
                    bt[j + 1] = temp;

                    temp = pid[j];
                    pid[j] = pid[j + 1];
                    pid[j + 1] = temp;
                }
            }
        }

        ct[0] = at[0] + bt[0];
        for (int i = 1; i < n; i++) {
            if (ct[i - 1] < at[i])
                ct[i] = at[i] + bt[i];
            else
                ct[i] = ct[i - 1] + bt[i];
        }

        int totalTAT = 0, totalWT = 0;
        for (int i = 0; i < n; i++) {
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
            totalTAT += tat[i];
            totalWT += wt[i];
        }

        System.out.println("\n---------------------------------------------------");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < n; i++)
            System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);

        System.out.println("-----------------------------------------------------");
        System.out.println("Average Turnaround time: " + totalTAT / n);
        System.out.println("Average Waiting time: " + totalWT / n);
        System.out.println("-----------------------------------------------------");

        System.out.println("Gantt Chart:");
        for (int i = 0; i < n; i++) {
            System.out.print("|  P" + pid[i] + " ");
        }
        System.out.println("|");

        for (int num : ct)
            System.out.print(num + "\t");

        System.out.println();
    }
}