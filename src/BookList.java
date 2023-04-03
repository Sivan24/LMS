import java.io.*;
import java.util.Scanner;

public class BookList {
    private BookNode head;
    private String filePath;

    public BookList(String filePath) {
        this.filePath = filePath;
        loadFromFile();
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
        saveToFile();
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

    public void removeBook(String title) {
        if (head.getBook().getTitle().equals(title)) {
            head = head.getNext();
            saveToFile();
            System.out.println(title + " has been removed.");
            return;
        }
        BookNode current = head;
        while (current.getNext() != null && !current.getNext().getBook().getTitle().equals(title)) {
            current = current.getNext();
        }
        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
            saveToFile();
            System.out.println(title + " has been removed.");
        } else {
            System.out.println("There is no " + title + " in the library.");
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

    public boolean isEmpty() {
        return head == null;
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
