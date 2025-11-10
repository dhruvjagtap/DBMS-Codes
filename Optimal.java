import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Optimal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the frame size: ");
        int frameSize = sc.nextInt();

        List<Integer> frames = new ArrayList<>();

        System.out.print("Enter number of pages: ");
        int pageCount = sc.nextInt();
        int pages[] = new int[pageCount];

        for (int i = 0; i < pageCount; i++) {
            pages[i] = sc.nextInt();
        }
        sc.close();

        System.out.println("Replacement Algo:");

        int pageFault = 0;

        for (int i = 0; i < pageCount; i++) {
            int page = pages[i];

            if (frames.contains(page)) {
                System.out.print("Step " + (i + 1) + " (Page " + page + " )");
                printFrames(frames);
                continue;
            }

            pageFault++;

            if (frames.size() < frameSize) {
                frames.add(page);
            } else {
                int indexToReplace = findOptimal(frames, pages, i + 1);
                frames.set(indexToReplace, page);
            }

            System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
            printFrames(frames);
        }

        System.out.println("\nTotal Page Faults: " + pageFault);

    }

    public static int findOptimal(List<Integer> frames, int[] pages, int start) {
        int farthest = start, indexToReplace = -1;

        for (int i = 0; i < frames.size(); i++) {
            int page = frames.get(i);

            int j;
            for (j = start; j < pages.length; j++) {
                if (page == pages[j]) {
                    if (j > farthest) {
                        farthest = j;
                        indexToReplace = i;
                    }
                    break;
                }
            }

            if (j == pages.length)
                return i;
        }

        return (indexToReplace == -1) ? 0 : indexToReplace;
    }

    public static void printFrames(List<Integer> frames) {
        for (int num : frames)
            System.out.print(num + " ");

        System.out.println();
    }
}