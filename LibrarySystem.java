import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {

    private static final String USERNAME = "proplayer";
    private static final String PASSWORD = "improve";

    static class Drill {
        String name;
        String focusArea;
        int difficulty;

        Drill(String name, String focusArea, int difficulty) {
            this.name = name;
            this.focusArea = focusArea;
            this.difficulty = difficulty;
        }
    }

    static class CoachingSession {
        String date;
        String skill;
        int beforeRating;
        int afterRating;
        String notes;

        CoachingSession(String date, String skill, int beforeRating, int afterRating, String notes) {
            this.date = date;
            this.skill = skill;
            this.beforeRating = beforeRating;
            this.afterRating = afterRating;
            this.notes = notes;
        }

        int improvement() {
            return afterRating - beforeRating;
        }
    }

    static class PlayerProfile {
        String name;
        String game;
        String role;
        int weeklyPracticeHours;
        List<Drill> drills;
        List<CoachingSession> sessions;

        PlayerProfile(String name, String game, String role, int weeklyPracticeHours) {
            this.name = name;
            this.game = game;
            this.role = role;
            this.weeklyPracticeHours = weeklyPracticeHours;
            this.drills = new ArrayList<>();
            this.sessions = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!login(scanner)) {
                System.out.println("Invalid login credentials.");
                return;
            }

            System.out.println("\n=== Pro Player Coaching App ===");
            PlayerProfile player = createProfile(scanner);
            seedRecommendedDrills(player);

            boolean running = true;
            while (running) {
                printMenu();
                int option = readInt(scanner, "Choose an option: ");
                switch (option) {
                    case 1:
                        addDrill(scanner, player);
                        break;
                    case 2:
                        logSession(scanner, player);
                        break;
                    case 3:
                        showProgress(player);
                        break;
                    case 4:
                        showImprovementPlan(player);
                        break;
                    case 5:
                        printFullReport(player);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Good luck with your training. Keep improving!");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return username.equals(USERNAME) && password.equals(PASSWORD);
    }

    private static PlayerProfile createProfile(Scanner scanner) {
        System.out.print("Player name: ");
        String name = scanner.nextLine();
        System.out.print("Main game/esport title: ");
        String game = scanner.nextLine();
        System.out.print("Primary role (e.g., IGL, Support, Duelist): ");
        String role = scanner.nextLine();
        int weeklyHours = readInt(scanner, "Weekly practice hours: ");

        System.out.println("\nProfile created for " + name + ".");
        return new PlayerProfile(name, game, role, weeklyHours);
    }

    private static void seedRecommendedDrills(PlayerProfile player) {
        player.drills.add(new Drill("Aim Tracking Routine", "Mechanical Skill", 3));
        player.drills.add(new Drill("VOD Review", "Decision Making", 2));
        player.drills.add(new Drill("Communication Scrim", "Team Coordination", 4));
    }

    private static void printMenu() {
        System.out.println("\n--- Coaching Menu ---");
        System.out.println("1. Add custom drill");
        System.out.println("2. Log coaching session");
        System.out.println("3. View progress summary");
        System.out.println("4. View improvement plan");
        System.out.println("5. Print full player report");
        System.out.println("0. Exit");
    }

    private static void addDrill(Scanner scanner, PlayerProfile player) {
        System.out.print("Drill name: ");
        String name = scanner.nextLine();
        System.out.print("Focus area: ");
        String focus = scanner.nextLine();
        int difficulty = readInt(scanner, "Difficulty (1-5): ");
        difficulty = Math.max(1, Math.min(5, difficulty));

        player.drills.add(new Drill(name, focus, difficulty));
        System.out.println("Drill added successfully.");
    }

    private static void logSession(Scanner scanner, PlayerProfile player) {
        System.out.print("Session date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Skill trained: ");
        String skill = scanner.nextLine();
        int before = readInt(scanner, "Performance before (1-10): ");
        int after = readInt(scanner, "Performance after (1-10): ");
        System.out.print("Session notes: ");
        String notes = scanner.nextLine();

        before = Math.max(1, Math.min(10, before));
        after = Math.max(1, Math.min(10, after));

        player.sessions.add(new CoachingSession(date, skill, before, after, notes));
        System.out.println("Session logged. Improvement: " + (after - before));
    }

    private static void showProgress(PlayerProfile player) {
        if (player.sessions.isEmpty()) {
            System.out.println("No sessions logged yet.");
            return;
        }

        int totalImprovement = 0;
        int bestGain = Integer.MIN_VALUE;
        String bestSkill = "";

        for (CoachingSession session : player.sessions) {
            int gain = session.improvement();
            totalImprovement += gain;
            if (gain > bestGain) {
                bestGain = gain;
                bestSkill = session.skill;
            }
        }

        double average = (double) totalImprovement / player.sessions.size();
        System.out.println("\nProgress Summary");
        System.out.println("Sessions logged: " + player.sessions.size());
        System.out.printf("Average improvement per session: %.2f%n", average);
        System.out.println("Best improvement area: " + bestSkill + " (" + bestGain + ")");
    }

    private static void showImprovementPlan(PlayerProfile player) {
        System.out.println("\nSuggested Weekly Plan for " + player.name + ":");
        System.out.println("Practice hours available: " + player.weeklyPracticeHours);

        if (player.drills.isEmpty()) {
            System.out.println("No drills available. Add drills first.");
            return;
        }

        int slots = Math.max(1, player.weeklyPracticeHours / 3);
        for (int i = 0; i < slots; i++) {
            Drill drill = player.drills.get(i % player.drills.size());
            System.out.println("- Block " + (i + 1) + ": " + drill.name
                    + " | Focus: " + drill.focusArea
                    + " | Difficulty: " + drill.difficulty);
        }

        System.out.println("Focus rule: 70% execution drills, 30% review and strategy.");
    }

    private static void printFullReport(PlayerProfile player) {
        System.out.println("\n=== Player Coaching Report ===");
        System.out.println("Name: " + player.name);
        System.out.println("Game: " + player.game);
        System.out.println("Role: " + player.role);
        System.out.println("Weekly Practice Hours: " + player.weeklyPracticeHours);

        System.out.println("\nDrills:");
        for (int i = 0; i < player.drills.size(); i++) {
            Drill d = player.drills.get(i);
            System.out.println((i + 1) + ". " + d.name + " | " + d.focusArea + " | Difficulty " + d.difficulty);
        }

        System.out.println("\nSessions:");
        if (player.sessions.isEmpty()) {
            System.out.println("No sessions logged yet.");
        } else {
            for (CoachingSession s : player.sessions) {
                System.out.println("- " + s.date + " | " + s.skill + " | "
                        + s.beforeRating + " -> " + s.afterRating
                        + " | Improvement: " + s.improvement());
                System.out.println("  Notes: " + s.notes);
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }
    }
}
