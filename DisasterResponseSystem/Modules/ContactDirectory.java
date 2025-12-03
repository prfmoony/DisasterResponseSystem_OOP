package src.modules;

import src.core.Module;
import src.core.Persistable;
import java.io.*;
import java.util.*;

public class ContactDirectory extends Module implements Persistable {
    static class Contact {
        private String agency;
        private String info;
        public Contact(String agency, String info) { this.agency = agency; this.info = info; }
        public String getAgency() { return agency; }
        public String getInfo() { return info; }
        @Override public String toString() { return agency + "," + info; }
    }

    private final List<Contact> list = new ArrayList<>();
    private final String filename;
    private static final Scanner SC = new Scanner(System.in);

    public ContactDirectory(String filename) { this.filename = filename; }

    @Override public String getModuleName() { return "Emergency Contact Directory"; }

    @Override public void run() {
        int ch;
        do {
            System.out.println("\n--- Contact Directory ---");
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Sort Contacts");
            System.out.println("5. Display All");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            ch = readIntRange(0,5);
            switch (ch) {
                case 1 -> add();
                case 2 -> remove();
                case 3 -> search();
                case 4 -> sort();
                case 5 -> display();
            }
        } while (ch != 0);
    }

    void add() {
        String agency = readNonEmpty("Agency: ");
        String info = readNonEmpty("Contact: ");
        list.add(new Contact(agency, info));
    }

    void remove() {
        String key = readNonEmpty("Agency to remove: ");
        list.removeIf(c -> c.getAgency().equalsIgnoreCase(key));
    }

    void search() {
        if (list.isEmpty()) return;
        for (int i=0;i<list.size();i++)
            System.out.printf("%d. %s%n", i+1, list.get(i).getAgency());
        int i = readIntRange(1,list.size()) - 1;
        Contact c = list.get(i);
        System.out.println(c.getAgency() + " | " + c.getInfo());
    }

    void sort() {
        list.sort(Comparator.comparing(c -> c.getAgency().toLowerCase()));
    }

    void display() {
        System.out.printf("%-35s %s%n","Agency","Contact");
        for (Contact c : list)
            System.out.printf("%-35s %s%n", c.getAgency(), c.getInfo());
    }

    @Override
    public void load() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println("PDRRMO,043-786-0693");
                pw.println("BATANGAS MEDICAL CENTER,043-723-0911");
                pw.println("RED CROSS BATANGAS,043-723-3027");
                pw.println("POLICE STATION,043-723-2030");
                pw.println("FIRE STATION,043-723-7299");
            }
        }
        list.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] p = s.split(",",2);
                if (p.length == 2) list.add(new Contact(p[0], p[1]));
            }
        }
    }

    @Override
    public void save() throws IOException {
        File file = new File(filename);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Contact c : list) pw.println(c);
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
