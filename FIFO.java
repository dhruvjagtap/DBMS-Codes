import java.util.*;

public class FIFO {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Frame size: ");
        int frameCount = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int pageCount = sc.nextInt();

        System.out.println("Enter Pages: ");

        int[] pages = new int[pageCount];

        for (int i = 0; i < pageCount; i++) {
            pages[i] = sc.nextInt();
        }

        sc.close();

        Queue<Integer> frameQueue = new LinkedList<>();
        Set<Integer> frameSet = new HashSet<>();

        int pageFaults = 0;

        System.out.println("\nPage Replacement Process: ");

        for (int i = 0; i < pageCount; i++) {
            int page = pages[i];

            boolean hit = frameSet.contains(page);

            if (!frameSet.contains(page)) {
                pageFaults++;

                if (frameQueue.size() == frameCount) {
                    int removed = frameQueue.poll();
                    frameSet.remove(removed);
                }

                frameQueue.add(page);
                frameSet.add(page);
            }

            System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
            for (int f : frameQueue)
                System.out.print(f + " ");

            System.out.print(" <- " + (hit ? "Hit" : "Fault"));

            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }
}