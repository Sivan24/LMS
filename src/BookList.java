import java.io.*;
import java.util.Scanner;

public class BookList {
    private BookNode head;
    private String filePath;

    public BookList(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void loadBook(Book book) {
        if (head == null) {
            head = new BookNode(book);
        } else {
            BookNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(new BookNode(book));
        }
    }

    public void addBook(Book book) {
        if (head == null) {
            head = new BookNode(book);
        } else {
            BookNode current = head;
            while (current.getNext() != null) {
                if ((book.getTitle().equals(current.getBook().getTitle()))
                        && (book.getAuthor().equals(current.getBook().getAuthor()))
                        && (book.getYear() == current.getBook().getYear())) {
                    System.out.println(book.getTitle() + " is already in the library.");
                    return;
                }
                current = current.getNext();
            }
            current.setNext(new BookNode(book));
        }
        System.out.println(book.getTitle() + " has been added to the library.");
        saveToFile();
    }

    public int countByTitle(String title) {
        int count = 0;
        BookNode current = head;
        while (current != null ) {
            if (current.getBook().getTitle().equals(title)) {
                count++;
            }
            current = current.getNext();
        }
        return count;
    }
    public int countByAuthor(String author) {
        int count = 0;
        BookNode current = head;
        while (current != null ) {
            if (current.getBook().getAuthor().equals(author)) {
                count++;
            }
            current = current.getNext();
        }
        return count;
    }

    public void deleteByTitle(String title) {
        int count = countByTitle(title);
        if (count <= 1) {
            if (head.getBook().getTitle().equals(title)) {
                head = head.getNext();
                saveToFile();
                System.out.println(title + " has been deleted.");
                return;
            }
            BookNode current = head;
            while (current.getNext() != null && !current.getNext().getBook().getTitle().equals(title)) {
                current = current.getNext();
            }
            if (current.getNext() != null) {
                current.setNext(current.getNext().getNext());
                saveToFile();
                System.out.println(title + " has been deleted.");
            } else {
                System.out.println("There is no " + title + " in the library.");
            }
        } else {
            System.out.println("There are many books titled" + title + " in the library, which author's books do you want to delete?");
            findBookByTitle(title);
            System.out.print("Enter the author: ");
            Scanner scanner = new Scanner(System.in);
            String author = scanner.nextLine();
            deleteBook(title, author);
        }
    }

    public void deleteByAuthor(String author) {
        int count = countByAuthor(author);
        if (count <= 1) {
            if (head.getBook().getAuthor().equals(author)) {
                head = head.getNext();
                saveToFile();
                System.out.println("The book by author " + author + " has been deleted.");
                return;
            }
            BookNode current = head;
            while (current.getNext() != null && !current.getNext().getBook().getAuthor().equals(author)) {
                current = current.getNext();
            }
            if (current.getNext() != null) {
                current.setNext(current.getNext().getNext());
                saveToFile();
                System.out.println("The book by author" + author + " has been deleted.");
            } else {
                System.out.println("There is no " + author + " in the library.");
            }
        } else {
            System.out.println("There are many books written by " + author + " in the library, which one do you want to delete?");
            findBookByAuthor(author);
            System.out.print("Enter the title: ");
            Scanner scanner = new Scanner(System.in);
            String title = scanner.nextLine();
            deleteBook(title, author);
        }
    }

    public void deleteBook(String title, String author) {
        if ((head.getBook().getTitle().equals(title))
                && (head.getBook().getAuthor().equals(author))) {
            head = head.getNext();
            saveToFile();
            System.out.println("The book titled " + title + ", written by the author " + author + ", has been deleted.");
            return;
        }
        BookNode current = head;
        while ((current.getNext() != null)
                && (!current.getNext().getBook().getTitle().equals(title))
                &&(!current.getNext().getBook().getTitle().equals(author))) {
            current = current.getNext();
        }
        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
            saveToFile();
            System.out.println("The book titled " + title + ", written by the author " + author + ", has been deleted.");
        } else {
            System.out.println("There are no books titled " + title + ", written by the author " + author + ", in the library.");
        }
    }

    public void listBooks() {
        BookNode current = head;
        while (current != null) {
            System.out.println(current.getBook());
            current = current.getNext();
        }
    }

    public void findBookByTitle(String title) {
        boolean flag = false;
        BookNode current = head;
        while (current != null ) {
            if (current.getBook().getTitle().equals(title)) {
                System.out.println(current.getBook().toString());
                flag = true;
            }
            current = current.getNext();
        }
        if (!flag) {
            System.out.println(title + " is not in the library.");
        }
    }
    public void findBookByAuthor(String author) {
        boolean flag = false;
        BookNode current = head;
        while (current != null ) {
            if (current.getBook().getAuthor().equals(author)) {
                System.out.println(current.getBook().toString());
                flag = true;
            }
            current = current.getNext();
        }
        if (!flag) {
            System.out.println("There is no book by " + author + " in the library.");
        }
    }

    private void loadFromFile() {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String title = fields[0].trim();
                    String author = fields[1].trim();
                    int year = Integer.parseInt(fields[2].trim());
                    loadBook(new Book(title, author, year));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            BookNode current = head;
            while (current != null) {
                Book book = current.getBook();
                writer.println(book.getTitle() + ", " + book.getAuthor() + ", " + book.getYear());
                current = current.getNext();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
