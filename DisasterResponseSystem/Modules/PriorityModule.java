package src.modules;

import src.core.Module;
import src.core.AbstractRequest;
import java.util.*;

public class PriorityModule extends Module {
    static class PriorityRequest extends AbstractRequest {
        private int priority;
        private String type;
        public PriorityRequest(int id, String req, String loc, int p, String t) {
            super(id, req, loc);
            priority = p; type = t;
        }
        public int getPriority() { return priority; }
        @Override public String displaySummary() {
            return String.format("PR ID:%d | %s | %s | Type:%s | P:%d",
                    getId(), getRequester(), getLocation(), type, priority);
        }
    }

    private final PriorityQueue<PriorityRequest> queue =
            new PriorityQueue<>(Comparator.comparingInt(PriorityRequest::getPriority));
    private final PriorityRequest[] log = new PriorityRequest[5];
    private int logPos = 0;
    private int nextId = 1;
    private static final Scanner SC = new Scanner(System.in);

    @Override public String getModuleName() { return "Priority Request Queue"; }

    @Override public void run() {
        int ch;
        do {
            System.out.println("\n--- Priority Queue ---");
            System.out.println("1. Add Request");
            System.out.println("2. Process Next");
            System.out.println("3. View Pending");
            System.out.println("4. View Log");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            ch = readIntRange(0,4);
            switch (ch) {
                case 1 -> add();
                case 2 -> process();
                case 3 -> pending();
                case 4 -> log();
            }
        } while (ch != 0);
    }

    void add() {
        String name = readNonEmpty("Name: ");
        String loc = readNonEmpty("Location: ");
        String type = readNonEmpty("Type: ");
        int p = readIntRange(1,4);
        PriorityRequest r = new PriorityRequest(nextId++, name, loc, p, type);
        queue.add(r);
        System.out.println("Added: " + r.displaySummary());
    }

    void process() {
        if (queue.isEmpty()) return;
        PriorityRequest r = queue.poll();
        log[logPos % log.length] = r;
        logPos++;
        System.out.println("Processing: " + r.displaySummary());
    }

    void pending() {
        if (queue.isEmpty()) return;
        ArrayList<PriorityRequest> tmp = new ArrayList<>(queue);
        tmp.sort(Comparator.comparingInt(PriorityRequest::getPriority));
        for (PriorityRequest r : tmp) System.out.println(r.displaySummary());
    }

    void log() {
        int count = Math.min(logPos, log.length);
        for (int i = 0; i < count; i++) {
            PriorityRequest r = log[(logPos - count + i) % log.length];
            System.out.println(r.displaySummary());
        }
    }

    static int readIntRange(int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(SC.nextLine().trim());
                if (v>=min && v<=max) return v;
            } catch (Exception ignored) {}
            System.out.print("Try again: ");
        }
    }

    static String readNonEmpty(String p) {
        while (true) {
            System.out.print(p);
            String s = SC.nextLine().trim();
            if (!s.isEmpty()) return s;
        }
    }
}
