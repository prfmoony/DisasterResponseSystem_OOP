package src.modules;

import src.core.Module;
import java.util.Scanner;

public class DisasterInfoModule extends Module {
    private static final Scanner SC = new Scanner(System.in);

    @Override
    public String getModuleName() { return "Disaster Info & Safety Tips"; }

    @Override
    public void run() {
        int ch;
        do {
            System.out.println("\n--- Disaster Safety Tips ---");
            System.out.println("1. Earthquake");
            System.out.println("2. Flood");
            System.out.println("3. Typhoon");
            System.out.println("4. Fire");
            System.out.println("5. Landslide");
            System.out.println("6. Volcanic Eruption");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            ch = readIntRange(0,6);

            switch (ch) {
                case 1 -> earthquake();
                case 2 -> flood();
                case 3 -> typhoon();
                case 4 -> fire();
                case 5 -> landslide();
                case 6 -> volcano();
            }
        } while (ch != 0);
    }

    void earthquake() {
        System.out.println("\nEARTHQUAKE");
        System.out.println("What: Sudden shaking caused by tectonic plates.");
        System.out.println("Before: Secure objects, prepare emergency kit.");
        System.out.println("During: Drop, Cover, Hold. Stay away from windows.");
        System.out.println("After: Expect aftershocks, check for injuries.");
    }

    void flood() {
        System.out.println("\nFLOOD");
        System.out.println("What: Overflow of water onto land.");
        System.out.println("Before: Move valuables up high.");
        System.out.println("During: Go to higher ground, avoid floodwater.");
        System.out.println("After: Watch for contamination and damage.");
    }

    void typhoon() {
        System.out.println("\nTYPHOON");
        System.out.println("What: Strong winds and heavy rain.");
        System.out.println("Before: Secure home, stock supplies.");
        System.out.println("During: Stay indoors, avoid windows.");
        System.out.println("After: Watch out for debris and flooding.");
    }

    void fire() {
        System.out.println("\nFIRE");
        System.out.println("What: Rapid burning and smoke.");
        System.out.println("Before: Install alarms, keep extinguishers.");
        System.out.println("During: Stay low, exit immediately.");
        System.out.println("After: Do not re-enter damaged buildings.");
    }

    void landslide() {
        System.out.println("\nLANDSLIDE");
        System.out.println("What: Movement of rock and soil.");
        System.out.println("Before: Avoid slopes, monitor soil movement.");
        System.out.println("During: Move away quickly and uphill.");
        System.out.println("After: Stay clear of the slide area.");
    }

    void volcano() {
        System.out.println("\nVOLCANIC ERUPTION");
        System.out.println("What: Ash, gas, and lava release.");
        System.out.println("Before: Prepare masks and evacuation routes.");
        System.out.println("During: Stay indoors, close all openings.");
        System.out.println("After: Avoid ashfall and protect water.");
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
}
