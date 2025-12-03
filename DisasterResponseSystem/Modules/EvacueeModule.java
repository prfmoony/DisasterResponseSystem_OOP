package src.modules;

import src.core.Module;
import src.core.Persistable;
import src.core.AbstractRequest;
import java.io.*;
import java.util.*;

public class EvacueeModule extends Module implements Persistable {

    static class EvacueeRequest extends AbstractRequest {
        private String contact;
        private int groupSize;
        private String status;

        public EvacueeRequest(int id, String requester, String location, String contact, int groupSize) {
            super(id, requester, location);
            this.contact = contact;
            this.groupSize = groupSize;
            this.status = "Pending";
        }

        public String getContact() { return contact; }
        public int getGroupSize() { return groupSize; }
        public String getStatus() { return status; }
        public void setStatus(String s) { status = s; }

        @Override
        public String displaySummary() {
            return String.format("EV ID:%d | %s | %s | Contact:%s | Group:%d | %s",
                    getId(), getRequester(), getLocation(), contact, groupSize, status);
        }

        public String serialize() {
            return String.join("|", String.valueOf(getId()),
                    esc(getRequester()), esc(getLocation()),
                    esc(contact), String.valueOf(groupSize), status);
        }

        public static EvacueeRequest deserialize(String s) {
            String[] p = s.split("\\|", 6);
            if (p.length < 6) return null;
            EvacueeRequest er = new EvacueeRequest(
                    Integer.parseInt(p[0]),
                    unesc(p[1]), unesc(p[2]),
                    unesc(p[3]), Integer.parseInt(p[4]));
            er.setStatus(p[5]);
            return er;
        }

        private static String esc(String s) { return s.replace("|", "/"); }
        private static String unesc(String s) { return s.replace("/", "|"); }
    }

    private final EvacueeRequest[] arr;
    private int count = 0;
    private int nextId = 1;
    private final String filename;
    private static final Scanner SC = new Scanner(System.in);
    private final int MAX = 100;

    public EvacueeModule(String filename) {
        this.filename = filename;
        this.arr = new EvacueeRequest[MAX];
    }

    @Override
    public String getModuleName() { return "Evacuee Request Tracker"; }

    @Override
    public void run() {
        int ch;
        do {
            System.out.println("\n--- Evacuee Tracker ---");
            System.out.println("1. Add Request");
            System.out.println("2. Complete Request");
            System.out.println("3. Search");
            System.out.println("4. Sort");
            System.out.println("5. Display All");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            ch = readIntRange(0,5);

            switch (ch) {
                case 1 -> add();
                case 2 -> complete();
                case 3 -> search();
                case 4 -> sort();
                case 5 -> display();
            }
        } while (ch != 0);
    }

    void add() {
        if (count >= MAX) return;
        String name = readNonEmpty("Name: ");
        String brgy = readNonEmpty("Barangay: ");
        String contact = readNonEmpty("Contact: ");
        int group = readIntRange(1, 10000);

        EvacueeRequest r = new EvacueeRequest(nextId++, name, brgy, contact, group);
        arr[count++] = r;
        System.out.println(r.displaySummary());
    }

    void complete() {
        if (count == 0) return;
        display();
        int id = readIntRange(1, nextId - 1);
        for (int i=0;i<count;i++)
            if (arr[i].getId() == id) {
                arr[i].setStatus("Completed");
                System.out.println(arr[i].displaySummary());
                return;
            }
    }

    void search() {
        if (count == 0) return;
        System.out.println("1) By Name  2) By Barangay");
        int ch = readIntRange(1,2);
        String key = readNonEmpty("Search: ").toLowerCase();

        for (int i=0;i<count;i++) {
            String f = (ch==1 ? arr[i].getRequester() : arr[i].getLocation()).toLowerCase();
            if (f.contains(key)) System.out.println(arr[i].displaySummary());
        }
    }

    void sort() {
        if (count < 2) return;
        System.out.println("1) Group Asc 2) Group Desc 3) Name Asc 4) Name Desc");
        int ch = readIntRange(1,4);

        for (int p=0;p<count-1;p++)
            for (int j=0;j<count-1-p;j++) {
                boolean s = false;
                switch (ch) {
                    case 1 -> s = arr[j].getGroupSize() > arr[j+1].getGroupSize();
                    case 2 -> s = arr[j].getGroupSize() < arr[j+1].getGroupSize();
                    case 3 -> s = arr[j].getRequester().compareToIgnoreCase(arr[j+1].getRequester()) > 0;
                    case 4 -> s = arr[j].getRequester().compareToIgnoreCase(arr[j+1].getRequester()) < 0;
                }
                if (s) {
                    EvacueeRequest t = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = t;
                }
            }
    }

    void display() {
        if (count == 0) return;
        int total = 0;

        System.out.printf("%-5s %-20s %-20s %-12s %-7s %-10s%n","ID","Name","Barangay","Contact","Group","Status");
        for (int i=0;i<count;i++) {
            EvacueeRequest r = arr[i];
            System.out.printf("%-5d %-20s %-20s %-12s %-7d %-10s%n",
                    r.getId(), r.getRequester(), r.getLocation(), r.getContact(), r.getGroupSize(), r.getStatus());
            total += r.getGroupSize();
        }
        System.out.println("Total Evacuees: " + total);
    }

    @Override
    public void load() throws IOException {
        File f = new File(filename);
        if (!f.exists()) { save(); return; }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String s; count = 0; nextId = 1;
            while ((s = br.readLine()) != null) {
                EvacueeRequest r = EvacueeRequest.deserialize(s);
                if (r != null && count < MAX) {
                    arr[count++] = r;
                    nextId = Math.max(nextId, r.getId() + 1);
                }
            }
        }
    }

    @Override
    public void save() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (int i=0;i<count;i++) pw.println(arr[i].serialize());
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
