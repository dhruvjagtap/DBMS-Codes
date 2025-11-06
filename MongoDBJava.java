import com.mongodb.*;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.DBCursor;
import java.util.Scanner;

public class MongoDBJava {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] ars) {
        DBCollection coll = null;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("Institute");
            coll = db.getCollection("Students");
            System.out.println("Connected to database successfully.");

            int choice;

            do {
                System.out.print(
                        "Enter your choice of operartion \n1.Display All. \n2. Insert Document. \n3. Delete Document. \n4. Update Document. \n5. Conditional display. \n6. EXIT. \nEnter Choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        displayAll(coll);
                        break;
                    case 2:
                        insertDoc(coll);
                        break;
                    case 3:
                        deleteDoc(coll);
                        break;
                    case 4:
                        updateDoc(coll);
                        break;
                    case 5:
                        conditionalDisplay(coll);
                        break;
                    case 6:
                        println("Exiting Program...");
                        break;
                    default:
                        System.out.println("Enter a valid entry.");
                }
            } while (choice != 6);
        } catch (MongoException ex) {
            ex.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void println(String prompt) {
        System.out.println(prompt);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, please enter an integer.");
        }

        int value = sc.nextInt();
        sc.nextLine();

        return value;
    }

    private static String readString(String prompt) {
        System.out.print(prompt);

        sc.nextLine();

        return sc.nextLine();
    }

    private static void insertDoc(DBCollection coll) {
        println("--- Inserting Document ---");
        int sRoll = readInt("Enter Roll Number: ");
        String sName = readString("Enter Name: ");
        String sClass = readString("Enter Class: ");
        int sMarks = readInt("Enter Marks: ");
        String sti = readString("Enter student Interest: ");

        BasicDBObject document = new BasicDBObject("s_rollno", sRoll)
                .append("s_name", sName)
                .append("s_class", sClass)
                .append("s_marks", sMarks)
                .append("s_interest", sti);

        coll.insert(document);
        println("Document inserted successfully");
    }

    private static void deleteDoc(DBCollection coll) {
        println("--- Deleting Document ---");

        int s_roll = readInt("Enter roll no: ");
        BasicDBObject document = new BasicDBObject("s_rollno", s_roll);

        coll.remove(document);
        println("Document deleted successfully");
    }

    private static void updateDoc(DBCollection coll) {
        println("--- Updating Document ---");

        int s_roll = readInt("Enter roll no: ");
        BasicDBObject searchDocument = new BasicDBObject("s_rollno", s_roll);

        int s_marks = readInt("Enter marks: ");
        BasicDBObject updateFields = new BasicDBObject("s_marks", s_marks);
        BasicDBObject newDocument = new BasicDBObject("$set", updateFields);

        coll.update(searchDocument, newDocument);
        println("Document updated successfully");
    }

    private static void displayAll(DBCollection coll) {
        println("--- All Dcouments ---");
        DBCursor cursor = coll.find();

        while (cursor.hasNext()) {
            println(cursor.next().toString());
            println("---------------------");
        }
    }

    private static void conditionalDisplay(DBCollection coll) {
        println("--- Conditional Display ---");

        int min_marks = readInt("Enter Minimum marks: ");
        DBCursor cursor = coll.find();

        boolean found = false;

        while (cursor.hasNext()) {
            int marks = (int) cursor.next().get("s_marks");

            if (marks < min_marks) {
                println(cursor.curr().toString());
                found = true;
            }
        }

        if (!found)
            println("Record not found");
    }
}