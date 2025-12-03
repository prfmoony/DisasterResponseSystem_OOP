package src;

import java.io.IOException;
import java.util.Scanner;
import src.core.Module;
import src.core.Persistable;
import src.modules.*;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private final Module[] modules = {
        new SupplyModule(),
        new ContactDirectory("data/data.txt"),
        new PriorityModule(),
        new EvacueeModule("data/evacuees.txt"),
        new DisasterInfoModule()
    };

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        for (Module m : modules) {
            if (m instanceof Persistable p) {
                try { p.load(); } 
                catch (IOException ignored) {}
            }
        }
        int choice;
        do {
            System.out.println("\n=== Disaster Response System ===");
            for (int i = 0; i < modules.length; i++)
                System.out.printf("%d. %s%n", i + 1, modules[i].getModuleName());
            System.out.println("0. Save & Exit");
            System.out.print("Enter choice: ");
            choice = readIntRange(0, modules.length);
            if (choice == 0) {
                for (Module m : modules) {
                    if (m instanceof Persistable p) {
                        try { p.save(); } 
                        catch (IOException ignored) {}
                    }
                }
                break;
            }
            modules[choice - 1].run();
        } while (true);
    }

    static int readIntRange(int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(SC.nextLine().trim());
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (Exception e) {
                System.out.print("Invalid, try again: ");
            }
        }
    }
}
