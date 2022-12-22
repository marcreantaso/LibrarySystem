import java.util.Scanner;

public class LibrarySystem {
    
    static final String USERNAME = "admin";
		  static final String PASSWORD = "admin";

		  static class Student {
		    String name;
		    String strand;
		    String gradeAndSection;
		    int numBooks;
		    String[] titles;
		    String[] authors;

		    Student(String name, String strand, String gradeAndSection, int numBooks, String[] titles, String[] authors) {
		      this.name = name;
		      this.strand = strand;
		      this.gradeAndSection = gradeAndSection;
		      this.numBooks = numBooks;
		      this.titles = titles;
		      this.authors = authors;
		    }
		  }

		  public static void main(String[] args) {
		    try (Scanner scanner = new Scanner(System.in)) {
                // Login
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (!username.equals(USERNAME) || !password.equals(PASSWORD)) {
                  System.out.println("Invalid login credentials.");
                  return;
                }

                // Create student
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                System.out.print("Enter student strand: ");
                String strand = scanner.nextLine();
                System.out.print("Enter student grade and section: ");
                String gradeAndSection = scanner.nextLine();
                System.out.print("Enter number of books: ");
                int numBooks = scanner.nextInt();
                scanner.nextLine(); // consume the remaining newline character
                String[] titles = new String[numBooks];
                String[] authors = new String[numBooks];
                for (int i = 0; i < numBooks; i++) {
                  System.out.print("Enter title of book #" + (i + 1) + ": ");
                  titles[i] = scanner.nextLine();
                  System.out.print("Enter author of book #" + (i + 1) + ": ");
                  authors[i] = scanner.nextLine();
                }
   
                
                Student student = new Student(name, strand, gradeAndSection, numBooks, titles, authors);

                // Print student information
                System.out.println("Student Information:");
                System.out.println("Name: " + student.name);
                System.out.println("Strand: " + student.strand);
                System.out.println("Grade and Section: " + student.gradeAndSection);
                System.out.println("Number of books: " + student.numBooks);
                for (int i = 0; i < student.numBooks; i++) {
                  System.out.println("Book #" + (i + 1) + ": " + student.titles[i] + " by " + student.authors[i]);
                }
            }
	}
}
