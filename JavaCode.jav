/*
Project Name : Student Grades Software
Author : Daniel Bennett
Version : 1
Date : 03/11/2023
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Scanner in = new Scanner(System.in);
    Scanner namein = new Scanner(System.in);
    Scanner gradein = new Scanner(System.in);
    ArrayList<String> StudentNames = new ArrayList<String>();
    ArrayList<Character> Grade = new ArrayList<Character>();

    //Main function that is set to start the program with the Main Options Menu
    public static void main(String[] args) {
        Main m = new Main();
        m.ReadFile();
        m.options();
    }

    //Main Option Menu with the options for the user to choose from
    void options(){
        System.out.println("1. Add Student Name and Grade");
        System.out.println("2. See Student Names and Grades");
        System.out.println("3. Remove Student Name and Grade");
        System.out.println("4. Modify Student Name and Grade");
        System.out.println("What would you like to do ?: ");
        int userOption = in.nextInt();

        if(userOption == 1){
            AddStudentName();
        }
        else if(userOption == 2){
            display();
        }
        else if(userOption == 3){
            RemoveStudent();
        }
        else if(userOption == 4){
            modifyOptions();
        }
    }

    //secondary options menu for if the user wants to edit a student name and or grade
    void modifyOptions(){
        System.out.println("1. Change Student Name");
        System.out.println("2. Change Student Grade");
        System.out.println("3. Return to Main Menu");
        System.out.println("What would you like to do ?: ");
        int userOption = in.nextInt();

        if(userOption == 1){
            ModifyStudentName();
        }
        else if(userOption == 2){
            UpdateGrade();
        }
        else if(userOption == 3){
            options();
        }
    }

    //allows the user to update a students grade if the student exists in the list
    //prompts the user to input the name of the student they want to change the grade of
    //runs through the student names list until the name is found, then prompts the user to enter the new grade for that student
    //then changes the existing grade to the new input grade
    void UpdateGrade(){
        String name;

        name = GetName("Name");

        for(int i = 0; i < StudentNames.size(); i++){
            if(StudentNames.get(i).contains(name)){
                System.out.println("----------");
                System.out.println("Name : " + StudentNames.get(i));
                System.out.println("Grade : " + Grade.get(i));
                System.out.println("----------");
                Grade.set(i, UpdateGrade('G'));
                WriteToFile();
                options();
            }
        }
    }

    //allows the user to change a students name if the student exists in the list
    //runs through the students name list until the name input for the name that needs changing
    //when it finds the name in the list the code then prompts the user to enter the new name for that student
    //then changes the student name then brings the user back to the main menu
    void ModifyStudentName(){
        String Wname;

        Wname = GetWrongName("WrongName");

        for(int i = 0; i < StudentNames.size(); i++){
            if(StudentNames.get(i).contains(Wname)){
                System.out.println("----------");
                System.out.println("Name : " + StudentNames.get(i));
                System.out.println("Grade : " + Grade.get(i));
                System.out.println("----------");
                StudentNames.set(i, SetNewStudnetName("NewName"));
                WriteToFile();
                options();
            }
            else{
                System.out.println("This Name Is Not In The System");
            }
        }
    }

    //allows the user to remove a student and their grade from the list
    //this function loops through the list of student name until it reaches the name that equals the name variable
    //when it does it remove`s that students name and grade from the list
    // if the name is not in the list is will print the name doesn't exist
    void RemoveStudent(){
        String name;

        name = GetName("Name");

        for(int i = 0; i < StudentNames.size(); i++) {
            if (StudentNames.get(i).contains(name)) {
                StudentNames.remove(StudentNames.get(i));
                Grade.remove(Grade.get(i));
                WriteToFile();
                options();
            } else {
                System.out.println("This Name Is Not In The System");
                options();
            }
        }
    }

    //allows the user to add a new student to the list
    void AddStudentName(){
        String name;

        name = GetName("Name");

        StoreStudents(name);
        AddStudentGrade();
    }

    //allows the user to add a grade to the students name that was just added to the list
    void AddStudentGrade(){
        char grade;

        grade = GetGrade('G');

        StoreStudentGrade(grade);
        options();
    }

    //stores the student names name to the list when called
    void StoreStudents(String name){
        StudentNames.add(name);
    }

    //stores the student grade to the list when called
    void StoreStudentGrade(char grade){
        Grade.add(grade);
        WriteToFile();
    }

    //function for getting the students name
    String GetName(String name){
        System.out.println("Enter Student Name : ");
        name = namein.nextLine();

        return name;
    }

    //function for getting the incorrect student name that's in the list
    String GetWrongName(String wrongname){
        System.out.println("Enter The incorrect name in the system that needs fixed : ");
        wrongname = namein.nextLine();

        return wrongname;
    }

    //function for setting the new student name over an existing student name in the list
    String SetNewStudnetName(String NewName){
        System.out.println("Enter new Name For This Student : ");

        NewName = namein.nextLine();

        return NewName;
    }

    //function for getting new student grade
    char GetGrade(char grade){
        System.out.println("Enter Student Grade : ");
        grade = gradein.nextLine().charAt(0);

        return grade;
    }

    //function for updating an existing student grade in the list
    char UpdateGrade(char newgrade){
        System.out.println("Enter New Grade For This Student : ");
        newgrade = gradein.nextLine().charAt(0);

        return newgrade;
    }

    //writes all the data stored in the StudentNames and Grade ArrayList when the function is called
    void WriteToFile(){
        try{
            File SN = new File("C:\\Users\\(users_UserAccountName)\\(File Location)\\StudentGrades\\StudentInfo\\Student Names.txt");
            File SG = new File("C:\\Users\\(users_UserAccountName)\\(File Location)\\StudentGrades\\StudentInfo\\Student Grades.txt");
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(SN));
            BufferedWriter outputWriter2 = new BufferedWriter(new FileWriter(SG));

            if(SN.exists() && SG.exists()){
                for(String names: StudentNames){
                    outputWriter.write(names + System.lineSeparator());
                }

                for(char grades: Grade){
                    outputWriter2.write(grades + System.lineSeparator());
                }

                outputWriter.close();
                outputWriter2.close();
            }

            options();
        }
        catch(IOException e){
            System.out.println("Invalid Path" + e);
        }
    }

    //reads the data from the files (Student Name.txt and Student Grades.txt)
    //then adds the data from the files to the StudentName and Grade ArrayList when called
    void ReadFile(){
        File SN = new File("C:\\Users\\(users_UserAccountName)\\(File Location)\\StudentGrades\\StudentInfo\\Student Names.txt");
        File SG = new File("C:\\Users\\(users_UserAccountName)\\(File Location)\\StudentGrades\\StudentInfo\\Student Grades.txt");

        if(SN.exists() || SG.exists()){
            try{
                Scanner Reader = new Scanner(SN);
                Scanner Reader2 = new Scanner(SG);

                while(Reader.hasNext()){
                    StudentNames.add(Reader.next());
                }
                while(Reader2.hasNext()){
                    Grade.add(Reader2.next().charAt(0));
                }
            }
            catch(FileNotFoundException e){
                System.out.println("An Error Occurred");
                e.printStackTrace();
            }
        }
    }

    //allows the user to display the list of students name and grade in the list
    void display(){
        for(int i = 0; i < StudentNames.size(); i++){
            System.out.println("----------");
            System.out.println("Name : " + StudentNames.get(i));
            System.out.println("Grade : " + Grade.get(i));
            System.out.println("----------");
        }
        options();
    }
}
