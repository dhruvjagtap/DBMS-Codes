import java.util.*;

public class LRU1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size of frame: ");
        int frameSize = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int pageCount = sc.nextInt();

        int[] pages = new int[pageCount];
        System.out.println("Enter pages:");
        for (int i = 0; i < pageCount; i++) {
            pages[i] = sc.nextInt();
        }

        sc.close();
        int pageFaults = 0;
        int[] frames = new int[frameSize];
        Arrays.fill(frames, -1);

        for (int i = 0; i < pageCount; i++) {
            int page = pages[i];
            boolean found = false;

            // Check if page is already in frames
            for (int frame : frames) {
                if (frame == page) {
                    found = true;
                    break;
                }
            }

            // If not found, replace using LRU
            if (!found) {
                int replaceIndex = findLRU(frames, pages, i);
                frames[replaceIndex] = page;
                pageFaults++;
            }

            System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
            for (int f : frames) {
                if (f != -1) {
                    System.out.print(f + " ");
                }
            }
            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }

    public static int findLRU(int[] frames, int[] pages, int currentIndex) {
        int replaceIndex = -1;
        int leastRecent = Integer.MAX_VALUE;

        // If there's still empty space in frames
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == -1)
                return i;
        }

        // Otherwise, find the least recently used
        for (int i = 0; i < frames.length; i++) {
            int lastUsed = -1;
            int page = frames[i];

            // Find when this page was last used
            for (int j = currentIndex - 1; j >= 0; j--) {
                if (pages[j] == page) {
                    lastUsed = j;
                    break;
                }
            }

            if (lastUsed < leastRecent) {
                leastRecent = lastUsed;
                replaceIndex = i; // ✅ FIXED HERE
            }
        }

        return replaceIndex;
    }
}
