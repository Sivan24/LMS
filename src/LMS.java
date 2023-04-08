import java.util.Scanner;

public class LMS {
    static BookList bookList = new BookList("books.csv");

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to the Library Management System (supported by Sivan)");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1. Add book");
            System.out.println("2. Delete book");
            System.out.println("3. List books");
            System.out.println("4. Find book");
            System.out.println("0. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    if (bookList.isEmpty()) {
                        System.out.println("The library is empty.");
                        break;
                    }
                    deleteBook(scanner);
                    break;
                case 3:
                    if (bookList.isEmpty()) {
                        System.out.println("The library list is empty.");
                        break;
                    }
                    listBooks();
                    break;
                case 4:
                    if (bookList.isEmpty()) {
                        System.out.println("The library is empty.");
                        break;
                    }
                    findBook(scanner);
                    break;
                case 0:
                    System.out.println("Thank you for using, goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        //判断输入是否合法
        if (title.replaceAll("\\s+", "").length() == 0) {
            System.out.println("Invalid input!");
            return;
        }

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        if (author.replaceAll("\\s+", "").length() == 0) {
            System.out.println("Invalid input!");
            return;
        }

        System.out.print("Enter book year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        if (year < 1) {
            System.out.println("Invalid input!");
            return;
        }

        Book book = new Book(title, author, year);
        bookList.addBook(book);
    }

    public static void deleteBook(Scanner scanner) {
        System.out.println("1. Delete by title: ");
        System.out.println("2. Delete by author: ");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                bookList.deleteByTitle(title);
                break;
            case 2:
                System.out.print("Enter book author: ");
                String author = scanner.nextLine();
                bookList.deleteByAuthor(author);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    public static void listBooks() {
        bookList.listBooks();
    }

    public static void findBook(Scanner scanner) {
        System.out.println("1. Enter book title: ");
        System.out.println("2. Enter book author: ");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                bookList.findBookByTitle(title);
                break;
            case 2:
                System.out.print("Enter book author: ");
                String author = scanner.nextLine();
                bookList.findBookByAuthor(author);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}

