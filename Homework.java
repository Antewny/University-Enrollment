//Anthony Rodriguez
package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Homework extends Application {
	
	
	ArrayList<student> students = new ArrayList<>();
	ArrayList<course> courses = new ArrayList<>();
	ArrayList<enrollment> enrollments = new ArrayList<>();
	
	private Label titleLabel;
    private Stage primaryStage; 

    @Override
    public void start(Stage primaryStage) throws IOException{
    	
    	StudentFileManager sm = new StudentFileManager();//stands for student manager "sm"
		CourseFileManager cm = new CourseFileManager();//stands for course manager "cm"
		EnrollmentFileManager em = new EnrollmentFileManager();//stands for enrollment manager "em"
		
		
        this.primaryStage = primaryStage; 

        VBox mainLayout = HomePage(sm,cm,em);

        //opening scene or homepage
        Scene mainScene = new Scene(mainLayout, 500, 400);
        primaryStage.setTitle("University Enrollment");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private VBox HomePage(StudentFileManager sm,CourseFileManager cm,EnrollmentFileManager em) {
       
    	  titleLabel = new Label("Welcome to University Enrollment");
    	  titleLabel.setFont(new Font("Impact", 24));
    	  
    	  StackPane title = new StackPane(titleLabel);
          title.setPrefHeight(350);
    	  
        MenuBar menuBar = createMenuBar(sm,cm,em);//creates menu bar in function
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //combines title and menubar for home page
        VBox mainLayout = new VBox( menuBar, gridPane,title);
        return mainLayout;
    }
    
    
    
    private void PopUp(String errorMessage) {
    	Stage popup = new Stage();
    	
    	popup.setTitle("Popup");
    	
    	Label errorLabel = new Label(errorMessage);
    	errorLabel.setFont(new Font("Impact", 16));
    	errorLabel.setWrapText(true);
        //errorLabel.setStyle("-fx-padding: 10; -fx-font-size: 16px;");
    	
    	VBox layout = new VBox(errorLabel);
    	layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
    	
    	Scene popupScene = new Scene(layout, 300, 200);
    	popup.setScene(popupScene);
    	
    	popup.showAndWait();
    	
    }
    

    
    
    
    private MenuBar createMenuBar(StudentFileManager sm,CourseFileManager cm,EnrollmentFileManager em) {//function to create menu bar which all options
        MenuBar menuBar = new MenuBar();
        Menu f = new Menu("File");
        
        Menu s = new Menu("Students");  //main tab   
        	MenuItem s1 = new MenuItem("View Student");//subtabs
        	MenuItem s2 = new MenuItem("Add Student");
        	MenuItem s3 = new MenuItem("Edit Student");
        
        Menu c = new Menu("Courses");
        	MenuItem c1 = new MenuItem("View Course");
        	MenuItem c2 = new MenuItem("Add Course");
        	MenuItem c3 = new MenuItem("Edit Course");
        
        Menu en = new Menu("Enrollment");
        	MenuItem en1 = new MenuItem("View/Edit Enrollment");
        	MenuItem en2 = new MenuItem("Add Enrollment");
        
        Menu g = new Menu("Grades");
        Menu g1 = new Menu("Add/Edit Grades");
        MenuItem g2 = new MenuItem("By Students");
    	MenuItem g3 = new MenuItem("By Course");
        
        Menu r = new Menu("Reports");
        MenuItem r1 = new MenuItem("View Reports");
        
        //adding menu items to menu
        s.getItems().addAll(s1, s2, s3);
        c.getItems().addAll(c1, c2, c3);
        en.getItems().addAll(en1, en2);
        g1.getItems().addAll(g2, g3);
        g.getItems().add(g1);
        r.getItems().add(r1);
        

        // add menu to menu bar
        menuBar.getMenus().addAll(f, s, c, en, g, r);

        // reroutes to menu different stages when menuitem is pressed
        s1.setOnAction(e -> showViewStudentPage(menuBar, sm));
        s2.setOnAction(e -> showAddStudentPage(menuBar,sm));
        s3.setOnAction(e -> showEditStudentPage(menuBar,sm));
        c1.setOnAction(e -> showViewCoursePage(menuBar, cm));
        c2.setOnAction(e -> showAddCoursePage(menuBar, cm));
        c3.setOnAction(e -> showEditCoursePage(menuBar, cm));
        en1.setOnAction(e -> showEditEnrollmentPage(menuBar,em,sm,cm));
        en2.setOnAction(e -> showAddEnrollmentPage(menuBar,em, sm, cm));
        r1.setOnAction(e -> showReportsPage(menuBar,cm, sm, em));
        
        
        
        return menuBar;
//=========================================================================================
    }
    private void showViewStudentPage(MenuBar menuBar, StudentFileManager sm) {
        // create view student page
    	Label addStudentLabel = new Label("New Student Information");
        TextField studentNameField = new TextField();
        TextField studentIDField = new TextField();
        TextField studentAddressField = new TextField();
        TextField studentCityField = new TextField();
        TextField studentZipField = new TextField();
        TextField studentStateField = new TextField();
        studentStateField.setPrefWidth(50);
        studentStateField.setMaxWidth(50);
        Button search = new Button("Search");
        
        //combo box not needed
        ComboBox<String> stateComboBox = new ComboBox<>();//combo box for states
        stateComboBox.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CO", "DE", "FL", "GA", //populating combo box
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
                "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", 
                "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
        stateComboBox.setPromptText("Select State");//what shows on combo box cover
        
        //making a grid pane to display scene
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addStudentLabel, 10, 1);
        gridPane.add(new Label("Student ID"), 9, 2);
        gridPane.add(studentIDField, 10, 2);
        gridPane.add(new Label("Student Name"), 9, 3);
        gridPane.add(studentNameField, 10, 3);
        gridPane.add(new Label("Adress"), 9, 4);
        gridPane.add(studentAddressField, 10, 4);
        gridPane.add(new Label("City"), 9, 5);
        gridPane.add(studentCityField, 10, 5);
        gridPane.add(new Label("State"), 9, 6);
        gridPane.add(studentStateField, 10, 6);
        gridPane.add(new Label("Zipcode"), 9, 7);
        gridPane.add(studentZipField, 10, 7);
        gridPane.add(search, 12, 2);

        // combine menuBar and gridPane for scene
        VBox addStudentLayout = new VBox(menuBar, gridPane);

        // makes new scene and sets new scene
        Scene addStudentScene = new Scene(addStudentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        
        search.setOnAction(e -> {
        	try {
        	int sID;
        	try {
                sID = Integer.parseInt(studentIDField.getText().trim());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Student ID must be an Integer");
            }
        	sID = Integer.parseInt(studentIDField.getText().trim());
        	student curStud = sm.GetStudent(sID);
        	if (curStud != null) {
        		studentNameField.setText(curStud.name);
        			studentNameField.setEditable(false);
        		studentAddressField.setText(curStud.address);
        			studentAddressField.setEditable(false);
        		studentCityField.setText(curStud.city);
        			studentCityField.setEditable(false);
        		studentStateField.setText(curStud.state);
        			studentStateField.setEditable(false);
        		studentZipField.setText(Integer.toString(curStud.zip));
        			studentZipField.setEditable(false);
        		
        		
        		
        		
        	}
        	else if (curStud == null) {
        		throw new IllegalArgumentException("Student ID does not exist");
        	}
        	
        	
        	}
            catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            }
        });
    }
       
    
    
    private void showAddStudentPage(MenuBar menuBar, StudentFileManager sm) {
    	// create add student page
        Label addStudentLabel = new Label("New Student Information");
        TextField studentNameField = new TextField();
        TextField studentIDField = new TextField();
        TextField studentAddressField = new TextField();
        TextField studentCityField = new TextField();
        TextField studentZipField = new TextField();
        Button createStudent = new Button("Create Student");

        ComboBox<String> stateComboBox = new ComboBox<>();//makes combo box and loads info into it
        stateComboBox.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CO", "DE", "FL", "GA", 
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
                "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", 
                "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
        stateComboBox.setPromptText("Select State");
        
        //making a grid pane to display scene
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addStudentLabel, 10, 1);
        gridPane.add(new Label("Student ID"), 9, 2);
        gridPane.add(studentIDField, 10, 2);
        gridPane.add(new Label("Student Name"), 9, 3);
        gridPane.add(studentNameField, 10, 3);
        gridPane.add(new Label("Address"), 9, 4);
        gridPane.add(studentAddressField, 10, 4);
        gridPane.add(new Label("City"), 9, 5);
        gridPane.add(studentCityField, 10, 5);
        gridPane.add(new Label("State"), 12, 5);
        gridPane.add(stateComboBox, 12, 6);
        gridPane.add(new Label("Zipcode"), 9, 6);
        gridPane.add(studentZipField, 10, 6);
        gridPane.add(createStudent, 10, 7);
        

        // combine menuBar and gridPane for scene
        VBox addStudentLayout = new VBox(menuBar, gridPane);

     // makes new scene and sets new scene
        Scene addStudentScene = new Scene(addStudentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        
        createStudent.setOnAction(e -> {
            try {
                //error checking
                if (studentIDField.getText().isEmpty() || studentNameField.getText().isEmpty() || 
                    studentAddressField.getText().isEmpty() || studentCityField.getText().isEmpty() || 
                    stateComboBox.getValue() == null || studentZipField.getText().isEmpty()) {
                    throw new IllegalArgumentException("All fields must be completed.");
                }

                //more error checking
                int id, zipcode;
                try {
                    id = Integer.parseInt(studentIDField.getText().trim());
                    zipcode = Integer.parseInt(studentZipField.getText().trim());
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Student ID and Zipcode must be integers.");
                }
                    student stud = sm.GetStudent(Integer.parseInt(studentIDField.getText().trim()));
                    if (stud != null) {
                    throw new IllegalArgumentException("Student ID already exists");}
                

                //storing values to create a student object
                String name = studentNameField.getText().trim();
                String address = studentAddressField.getText().trim();
                String city = studentCityField.getText().trim();
                String state = stateComboBox.getValue();

                //creating student object
                sm.AddStudent(id, name, address, city, state, zipcode);

                //clearing fields after submission
                studentIDField.clear();
                studentNameField.clear();
                studentAddressField.clear();
                studentCityField.clear();
                studentZipField.clear();
                stateComboBox.setValue(null);

                PopUp("Student successfully added!");

            } catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            } catch (IOException ex) {
                PopUp("Error: Unexpected error occurred. Please try again.");
                ex.printStackTrace();
            }
        });
    }
    
    private void showEditStudentPage(MenuBar menuBar ,StudentFileManager sm) {
    	// create edit student page
    	Label addStudentLabel = new Label("Edit Student Information");
        TextField studentNameField = new TextField();
        TextField studentIDField = new TextField();
        TextField studentAddressField = new TextField();
        TextField studentCityField = new TextField();
        TextField studentZipField = new TextField();
        TextField studentStateField = new TextField();
        studentStateField.setPrefWidth(50);
        studentStateField.setMaxWidth(50);
        Button search = new Button("Search");
        Button reset = new Button("Reset");
        Button update = new Button("Update");

        ComboBox<String> stateComboBox = new ComboBox<>();
        stateComboBox.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CO", "DE", "FL", "GA", 
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
                "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", 
                "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
        stateComboBox.setPromptText("Select State");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addStudentLabel, 10, 1);
        gridPane.add(new Label("Student ID"), 9, 2);
        gridPane.add(studentIDField, 10, 2);
        gridPane.add(new Label("Student Name"), 9, 3);
        gridPane.add(studentNameField, 10, 3);
        	studentNameField.setEditable(false);
        gridPane.add(new Label("Adress"), 9, 4);
        gridPane.add(studentAddressField, 10, 4);
        	studentAddressField.setEditable(false);
        gridPane.add(new Label("City"), 9, 5);
        gridPane.add(studentCityField, 10, 5);
        	studentCityField.setEditable(false);
        gridPane.add(new Label("State"), 9, 6);//make a drop menu
        gridPane.add(stateComboBox, 10, 6);
        	stateComboBox.setDisable(true);
        gridPane.add(new Label("Zipcode"), 9, 7);
        gridPane.add(studentZipField, 10, 7);
        	studentZipField.setEditable(false);
        gridPane.add(search, 12, 2);
        gridPane.add(reset, 10, 8);
        gridPane.add(update, 12, 8);

        VBox addStudentLayout = new VBox(menuBar, gridPane);

        Scene addStudentScene = new Scene(addStudentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        
        search.setOnAction(e -> {
        	try {
        	int sID;
        	try {
                sID = Integer.parseInt(studentIDField.getText().trim());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Student ID must be integers");
            }
        	sID = Integer.parseInt(studentIDField.getText().trim());
        	student curStud = sm.GetStudent(sID);
        	if (curStud != null) {
        		studentIDField.setDisable(true);
        		studentNameField.setText(curStud.name);
        			studentNameField.setEditable(true);
        		studentAddressField.setText(curStud.address);
        			studentAddressField.setEditable(true);
        		studentCityField.setText(curStud.city);
        			studentCityField.setEditable(true);
        		stateComboBox.setValue(curStud.state);
        			stateComboBox.setDisable(false);
        		studentZipField.setText(Integer.toString(curStud.zip));
        			studentZipField.setEditable(true);
        		
        		
        	reset.setOnAction(n ->{
        		studentIDField.clear();
                studentNameField.clear();
                studentAddressField.clear();
                studentCityField.clear();
                studentZipField.clear();
                stateComboBox.setValue(null);
                studentIDField.setDisable(false);
        	});
        	
        	update.setOnAction(b ->{
        		try {
                    //error checking
                    if (studentIDField.getText().isEmpty() || studentNameField.getText().isEmpty() || 
                        studentAddressField.getText().isEmpty() || studentCityField.getText().isEmpty() || 
                        stateComboBox.getValue() == null || studentZipField.getText().isEmpty()) {
                        throw new IllegalArgumentException("All fields must be completed.");
                    }

                    //more error checking
                    int id, zipcode;
                    try {
                        id = Integer.parseInt(studentIDField.getText().trim());
                        zipcode = Integer.parseInt(studentZipField.getText().trim());
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Student ID and Zipcode must be integers.");
                    }

                    //storing values to create a student object
                    String name = studentNameField.getText().trim();
                    String address = studentAddressField.getText().trim();
                    String city = studentCityField.getText().trim();
                    String state = stateComboBox.getValue();

                    //creating student object
                    sm.updateStudent(id, name, address, city, state, zipcode);

                    //clearing fields after submission
                    studentIDField.clear();
                    studentNameField.clear();
                    studentAddressField.clear();
                    studentCityField.clear();
                    studentZipField.clear();
                    stateComboBox.setValue(null);
                    studentIDField.setDisable(false);
                    

                    PopUp("Student successfully added!");

                } catch (IllegalArgumentException ex) {
                    PopUp("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    PopUp("Error: Unexpected error occurred. Please try again.");
                    ex.printStackTrace();
                }
  	
        	});
 
        	}

        	
        	}
            catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            }
        });
    }
//===========================================================================
    private void showAddCoursePage(MenuBar menuBar, CourseFileManager cm) {
    	// create add course page
        Label addCourseLabel = new Label("New Course Information");
        TextField courseNameField = new TextField();
        TextField courseIDField = new TextField();
        TextField courseNumField = new TextField();
        TextField instructorNameField = new TextField();
        Button createCourse = new Button("Create Course");

        ComboBox<String> departmentComboBox = new ComboBox<>();//combobox for department followed by adding info
        departmentComboBox.getItems().addAll("MATH", "EN", "CS", "SCI", "PHY","ART");
        departmentComboBox.setPromptText("Select Department");
     

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addCourseLabel, 10, 1);
        gridPane.add(new Label("Course ID"), 9, 2);
        gridPane.add(courseIDField, 10, 2);
        gridPane.add(new Label("Course Name"), 9, 3);
        gridPane.add(courseNameField, 10, 3);
        gridPane.add(new Label("Department"), 9, 4);
        gridPane.add(departmentComboBox, 10, 4);
        gridPane.add(new Label("Course Number"), 9, 5);
        gridPane.add(courseNumField, 10, 5);
        gridPane.add(new Label("Instructor"), 9, 6);
        gridPane.add(instructorNameField, 10, 6);
        gridPane.add(createCourse, 10, 7);
        

        VBox addStudentLayout = new VBox(menuBar, gridPane);
        
        Scene addStudentScene = new Scene(addStudentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        createCourse.setOnAction(e -> {
            try {
                //error checking
                if (courseIDField.getText().isEmpty() || courseNameField.getText().isEmpty() || 
                		courseNumField.getText().isEmpty() || instructorNameField.getText().isEmpty() || 
                    departmentComboBox.getValue() == null) {
                    throw new IllegalArgumentException("All fields must be completed.");
                }

                //more error checking
                int id, number;
                try {
                    id = Integer.parseInt(courseIDField.getText().trim());
                    number = Integer.parseInt(courseNumField.getText().trim());
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Course ID must be integers.");
                }
                course cour = cm.GetCourse(Integer.parseInt(courseIDField.getText().trim()));
                if (cour != null) {
                throw new IllegalArgumentException("Course ID already exists");}

                //storing values to create a course object
                String cName = courseNameField.getText().trim();
                String instruc = instructorNameField.getText().trim();
                String dept = departmentComboBox.getValue();
                
                String desc = (dept + " " + number).trim();
                
                cm.AddCourse(id, cName, desc, instruc);

                //clearing fields after submission
                courseIDField.clear();
                courseNameField.clear();
                instructorNameField.clear();
                courseNumField.clear();
                departmentComboBox.setValue(null);

                PopUp("Course successfully added!");

            } catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            } catch (IOException ex) {
                PopUp("Error: Unexpected error occurred. Please try again.");
                ex.printStackTrace();
            }
        });
    }
    
    private void showViewCoursePage(MenuBar menuBar, CourseFileManager cm) {
    	// create view course page
        Label addCourseLabel = new Label("View Course Information");
        TextField courseNameField = new TextField();
        TextField courseIDField = new TextField();
        TextField courseNumField = new TextField();
        TextField instructorNameField = new TextField();
        TextField departmentField = new TextField();
        Button search = new Button("Search");

        ComboBox<String> departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll("MATH", "EN", "CS", "SOC SC", "PHY","ART");
        departmentComboBox.setPromptText("Select Department");


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addCourseLabel, 10, 1);
        gridPane.add(new Label("Course ID"), 9, 2);
        gridPane.add(courseIDField, 10, 2);
        gridPane.add(new Label("Course Name"), 9, 3);
        gridPane.add(courseNameField, 10, 3);
        gridPane.add(new Label("Department"), 9, 4);
        gridPane.add(departmentField, 10, 4);
        gridPane.add(new Label("Course Number"), 9, 5);
        gridPane.add(courseNumField, 10, 5);
        gridPane.add(new Label("Instructor"), 9, 6);
        gridPane.add(instructorNameField, 10, 6);
        gridPane.add(search, 12, 2);
        
        
        VBox addStudentLayout = new VBox(menuBar, gridPane);

        Scene addStudentScene = new Scene(addStudentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        search.setOnAction(e -> {
        	try {
        	int cID;
        	try {
                cID = Integer.parseInt(courseIDField.getText().trim());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Course ID and Course number must be integers");
            }
        	cID = Integer.parseInt(courseIDField.getText().trim());
        	course curCour = cm.GetCourse(cID);
        	if (curCour != null) {
        		String data[] = curCour.courseDescrip.split(" ");
        		String courdep = data[0].trim();
        		String courNum = data[1].trim();
        		courseNameField.setText(curCour.courseName);
        			courseNameField.setEditable(false);
        		courseNumField.setText(courNum);
        			courseNumField.setEditable(false);
        		departmentField.setText(courdep);
        			courseNumField.setEditable(false);
        		instructorNameField.setText(curCour.professor);
        			instructorNameField.setEditable(false);
        		
        	}
        	else if (curCour == null) {
        		throw new IllegalArgumentException("Course ID does not exist");
        	}

        	}
            catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            }
        });
    }
    
    private void showEditCoursePage(MenuBar menuBar, CourseFileManager cm) {
    	// create edit course page
        Label addCourseLabel = new Label("Edit Course Information");
        TextField courseNameField = new TextField();
        TextField courseIDField = new TextField();
        TextField courseNumField = new TextField();
        TextField instructorNameField = new TextField();
        Button search = new Button("Search");
        Button reset = new Button("Reset");
        Button update = new Button("Update");
        
        ComboBox<String> departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll("MATH", "EN", "CS", "SOC SC", "PHY","ART");
        departmentComboBox.setPromptText("Select Department");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addCourseLabel, 10, 1);
        gridPane.add(new Label("Course ID"), 9, 2);
        gridPane.add(courseIDField, 10, 2);
        gridPane.add(new Label("Course Name"), 9, 3);
        gridPane.add(courseNameField, 10, 3);
        gridPane.add(new Label("Department"), 9, 4);
        gridPane.add(departmentComboBox, 10, 4);
        gridPane.add(new Label("Course Number"), 9, 5);
        gridPane.add(courseNumField, 10, 5);
        gridPane.add(new Label("Instructor"), 9, 6);
        gridPane.add(instructorNameField, 10, 6);
        gridPane.add(search, 12, 2);
        gridPane.add(reset, 10, 7);
        gridPane.add(update, 12, 7);
        

        VBox addStudentLayout = new VBox(menuBar, gridPane);

        Scene addStudentScene = new Scene(addStudentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        search.setOnAction(e -> {
        	try {
        	int sID;
        	try {
                sID = Integer.parseInt(courseIDField.getText().trim());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Course ID must be an Integer");
            }
        	sID = Integer.parseInt(courseIDField.getText().trim());
        	course curCour = cm.GetCourse(sID);
        	if (curCour != null) {
        		courseIDField.setDisable(true);
        		String data[] = curCour.courseDescrip.split(" ");
        		String courdep = data[0].trim();
        		String courNum = data[1].trim();
        		courseNameField.setText(curCour.courseName);
        			courseNameField.setEditable(true);
        		courseNumField.setText(courNum);
        			courseNumField.setEditable(true);
        		departmentComboBox.setValue(courdep);
        			courseNumField.setEditable(true);
        		instructorNameField.setText(curCour.professor);
        			instructorNameField.setEditable(true);
        		
        		
        	reset.setOnAction(n ->{
        		courseIDField.clear();
        		courseNameField.clear();
        		courseNumField.clear();
        		instructorNameField.clear();
                departmentComboBox.setValue(null);
                courseIDField.setDisable(false);
        	});
        	
        	update.setOnAction(b ->{
        		try {
                    //error checking
                    if (courseIDField.getText().isEmpty() || courseNameField.getText().isEmpty() || 
                    		courseNumField.getText().isEmpty() || instructorNameField.getText().isEmpty() || 
                    		departmentComboBox.getValue() == null) {
                        throw new IllegalArgumentException("All fields must be completed.");
                    }

                    //more error checking
                    int id, cNum;
                    try {
                        id = Integer.parseInt(courseIDField.getText().trim());
                        cNum = Integer.parseInt(courseNumField.getText().trim());
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Course ID and Course number must be integers.");
                    }

                    //storing values to create a student object
                    String name = courseNameField.getText().trim();
                    String instructor = instructorNameField.getText().trim();
                    String dept = departmentComboBox.getValue();

                    String cDesc = (dept + " " + cNum);
                    //creating student object
                    cm.updateCourse(id, name, cDesc, instructor);

                    //clearing fields after submission
                    courseIDField.clear();
                    courseNameField.clear();
                    courseNumField.clear();
                    instructorNameField.clear();
                    departmentComboBox.setValue(null);
                    courseIDField.setDisable(false);
                    

                    PopUp("Course successfully added!");

                } catch (IllegalArgumentException ex) {
                    PopUp("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    PopUp("Error: Unexpected error occurred. Please try again.");
                    ex.printStackTrace();
                }
 
        	});

        	}
        	
        	}
            catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            }
        });
    }
//=============================================================================================
    //should be done
    private void showAddEnrollmentPage(MenuBar menuBar , EnrollmentFileManager em, StudentFileManager sm, CourseFileManager cm) {
    	// create add enrollment page
        Label addEnrollmentLabel = new Label("New Enrollment Information");
        TextField studentNameField = new TextField();
        	studentNameField.setPromptText("Student Name");
        TextField studentIDField = new TextField();
        	studentIDField.setPromptText("Student ID");
        TextField courseIDField = new TextField();
        	courseIDField.setPromptText("Course ID");
        TextField courseNumberField = new TextField();
        	courseNumberField.setPromptText("Course Number");
        TextField courseNameField = new TextField();
        	courseNameField.setPromptText("CourseName");
        Button createEnrollment = new Button("Create Enrollment");
        Button studentSearch = new Button("Find Student");
        Button courseSearch = new Button("Find Course");

        
        ComboBox<String> semesterComboBox = new ComboBox<>();//combo boxs for semester followed by adding semesters to box
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterComboBox.setPromptText("Select Semester");
        
        ComboBox<String> yearComboBox = new ComboBox<>();//combo box for years followed by adding years to box
        yearComboBox.getItems().addAll("2020", "2021", "2022", "2023","2024", "2025");
        yearComboBox.setPromptText("Select Year");

        

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(addEnrollmentLabel, 13, 1);
        gridPane.add(studentIDField, 13, 2);
        gridPane.add(studentNameField, 13, 3);
        gridPane.add(courseIDField, 13, 4);
        gridPane.add(courseNumberField, 13, 5);
        gridPane.add(courseNameField, 13, 6);
        gridPane.add(createEnrollment, 13, 10);
        gridPane.add(studentSearch, 15, 2);
        gridPane.add(courseSearch, 15, 4);
        
        gridPane.add(new Label("Semester"), 15, 7);
        gridPane.add(semesterComboBox, 15, 8);
        gridPane.add(new Label("Year"), 13, 7);
        gridPane.add(yearComboBox, 13, 8);


        VBox addEnrollmentLayout = new VBox(menuBar, gridPane);

        Scene addStudentScene = new Scene(addEnrollmentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        //searches course info with only course id
        courseSearch.setOnAction(w -> {
        	try {
        	int cID;
        	
        	cID = Integer.parseInt(courseIDField.getText().trim());
        	course curCour = cm.GetCourse(cID);
        	if (curCour != null) {
        		courseNameField.setText(curCour.courseName);
        		courseNameField.setEditable(false);
        		courseNumberField.setText(curCour.courseDescrip);
        		courseNumberField.setEditable(false);
        	}
        	}
            catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            }
        });
        //searches student info with only student id
        studentSearch.setOnAction(w -> {
        	try {
        	int sID;
        	
        	sID = Integer.parseInt(studentIDField.getText().trim());
        	student curStud = sm.GetStudent(sID);
        	if (curStud != null) {
        		studentNameField.setText(curStud.name);
        		studentNameField.setEditable(false);
        	}
        	}
            catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            }
        });
        createEnrollment.setOnAction(e -> {
            try {
                //error checking
                if (studentIDField.getText().isEmpty() || studentNameField.getText().isEmpty() || 
                		courseIDField.getText().isEmpty() || courseNumberField.getText().isEmpty() || 
                		courseNameField.getText().isEmpty() || semesterComboBox.getValue() == null || 
                				yearComboBox.getValue() == null) {
                    throw new IllegalArgumentException("All fields must be completed.");
                }

                //more error checking
                int ID , cID;
                try {
                    ID = Integer.parseInt(studentIDField.getText().trim());
                    cID = Integer.parseInt(courseIDField.getText().trim());
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Student ID and Zipcode must be integers.");
                }
                

                
                int year = Integer.parseInt(yearComboBox.getValue());
                String sem = semesterComboBox.getValue();
                
                enrollment enrol = em.GetEnrollment(ID, cID, year,sem);
                if (enrol != null) {
                throw new IllegalArgumentException("Enrollment already exists");
                }

                
                
                
                
                //creating student object
                em.AddEnrollment(ID, cID, year, sem,"X", sm, cm);
                //clearing fields after submission
                studentIDField.clear();
                studentNameField.clear();
                courseIDField.clear();
                courseNumberField.clear();
                courseNameField.clear();
                semesterComboBox.setValue(null);
                yearComboBox.setValue(null);

                
                
                PopUp("Enrollment successfully added!");

            } catch (IllegalArgumentException ex) {
                PopUp("Error: " + ex.getMessage());
            } catch (IOException ex) {
                PopUp("Error: Unexpected error occurred. Please try again.");
                ex.printStackTrace();
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     void showEditEnrollmentPage(MenuBar menuBar,EnrollmentFileManager em, StudentFileManager sm, CourseFileManager cm){
    	 
    	// create edit enrollment page
    	HBox enrolBox = new HBox(10); // spacing between things in Hbox
    	enrolBox.setStyle("-fx-padding: 6;"); // padding around enroll box

        Label studentIDLabel = new Label("Student ID");
        TextField studentIDField = new TextField();
        	studentIDField.setPrefWidth(30);
        Button populateButton = new Button("Populate");

        Label courseIDLabel = new Label("Course ID");
        TextField courseIDField = new TextField();
        	courseIDField.setPrefWidth(30);
        Button ChangeGrade = new Button("Change Grade");
        
        Label newGradeLabel = new Label("New Grade");
        TextField newGradeField = new TextField();
        	newGradeField.setPrefWidth(30);
        //adds objects to enrolbox
        enrolBox.getChildren().addAll(studentIDLabel, studentIDField, populateButton, courseIDLabel, 
        		courseIDField,newGradeLabel,newGradeField,ChangeGrade);


        //creating table and columns stores enrollment objects
        TableView<enrolUser> enrollmentTable= new TableView<>();
        TableColumn<enrolUser, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        	nameCol.setPrefWidth(125);
        TableColumn<enrolUser, String> courseNumberCol = new TableColumn<>("Course #");
        courseNumberCol.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        	courseNumberCol.setPrefWidth(75);
        TableColumn<enrolUser, Integer> courseIDCol = new TableColumn<>("Course ID");
        courseIDCol.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        	courseIDCol.setPrefWidth(75);
        TableColumn<enrolUser, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        	semesterCol.setPrefWidth(75);
        TableColumn<enrolUser, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        	yearCol.setPrefWidth(75);
        TableColumn<enrolUser, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        	gradeCol.setPrefWidth(75);
        	
        //populating table with columns
        enrollmentTable.getColumns().addAll(nameCol, courseNumberCol, courseIDCol, semesterCol, yearCol, gradeCol);
        
        VBox viewEnrollmentLayout = new VBox(menuBar,enrolBox ,enrollmentTable);
        
        Scene viewEnroll = new Scene(viewEnrollmentLayout, 500,400);//changed for experimenting with views
        primaryStage.setScene(viewEnroll);
        //changes the grade for the sID and cID then refreshes table
        ChangeGrade.setOnAction(z ->{
        	int sID = Integer.parseInt(studentIDField.getText().trim());
        	int cID = Integer.parseInt(courseIDField.getText().trim());
        	String grade = newGradeField.getText().trim();
        	
        	enrollments = em.getEnrollments()
;
        	for (enrollment enrol : enrollments) {
        		if (sID == enrol.studentID && cID == enrol.courseID) {
        			enrol.grade = grade;
        			//update enrollment
        			try {
						em.updateEnrollment(sID, cID, enrol.year, enrol.semester, grade);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
        			PopUp("Successfully updated");
        			ObservableList<enrolUser> enrolls = FXCollections.observableArrayList();
        			student stud = sm.GetStudent(sID);
        			for(enrollment enroll : enrollments) {
                    	if (enroll.studentID == sID) {
                    		course cour = cm.GetCourse(enroll.courseID);
                    		enrolls.add(new enrolUser(stud.name,cour.courseDescrip, enroll.courseID, enroll.semester
                    				, enroll.year, enroll.grade));
        		}
        	}
        			enrollmentTable.setItems(enrolls);
        }}}
        );

        //loads everything into the table
        populateButton.setOnAction(e -> {
            try {
                int sID = Integer.parseInt(studentIDField.getText().trim());

                
                enrollments = em.getEnrollments();
                
                ObservableList<enrolUser> enrolls = FXCollections.observableArrayList();
                student stud = sm.GetStudent(sID);
                for(enrollment enrol : enrollments) {
                	if (enrol.studentID == sID) {
                		course cour = cm.GetCourse(enrol.courseID);
                		enrolls.add(new enrolUser(stud.name,cour.courseDescrip, enrol.courseID, enrol.semester
                				, enrol.year, enrol.grade));
                	
                	}
                }

                enrollmentTable.setItems(enrolls);

            } catch (NumberFormatException ex) {
                PopUp("Invalid Student ID. Please enter a valid number.");
            }
        });}

    
//==============================================================================================================================
    //done
    void showReportsPage(MenuBar menuBar,CourseFileManager cm, StudentFileManager sm, EnrollmentFileManager em) {
    	Label courseIDLabel = new Label("Course ID");
        TextField courseIDField = new TextField();
        	courseIDField.setPromptText("Course ID");
        	courseIDField.setPrefWidth(75);
        TextArea report = new TextArea();
        report.setPrefWidth(450);
        report.setPrefHeight(200);
        report.setEditable(false);

        Button generateReport = new Button("Generate Report");

        
        ComboBox<String> semesterComboBox = new ComboBox<>();//combo boxs for semester followed by adding semesters to box
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterComboBox.setPrefWidth(75);
        
        ComboBox<String> yearComboBox = new ComboBox<>();//combo box for years followed by adding years to box
        yearComboBox.getItems().addAll("2020", "2021", "2022", "2023","2024", "2025");       
        yearComboBox.setPrefWidth(75);

        GridPane repBox = new GridPane();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        
        gridPane.add(courseIDField, 2, 4);
        gridPane.add(courseIDLabel, 2, 3);

        
        gridPane.add(new Label("Semester"), 6, 3);
        gridPane.add(semesterComboBox, 6, 4);
        gridPane.add(new Label("Year"), 4, 3);
        gridPane.add(yearComboBox, 4, 4);
        gridPane.add(generateReport, 8, 4);
        
        repBox.add(report, 6, 12);

        VBox.setMargin(repBox, new Insets(20, 0, 0, 0));
        VBox addEnrollmentLayout = new VBox(menuBar, gridPane, repBox);
        addEnrollmentLayout.setPadding(new Insets(10));
        Scene addStudentScene = new Scene(addEnrollmentLayout, 500, 400);
        primaryStage.setScene(addStudentScene);
        //when generate button is pressed
        generateReport.setOnAction(e ->{
        	try {
        		if (courseIDField.getText().isEmpty() || semesterComboBox.getValue() == null || 
            		yearComboBox.getValue() == null) {
                throw new IllegalArgumentException("All fields must be completed.");
            }
        	
    		int cID = Integer.parseInt(courseIDField.getText());
    		int year = Integer.parseInt(yearComboBox.getValue());
    		String semester = semesterComboBox.getValue().trim();
    		course cour = cm.GetCourse(cID);
    		enrollments = em.getEnrollments();
    		try {
    		report.setText(cour.courseDescrip + " " + cour.courseName + " Report\n" + "===========================\n");
    		}catch(Exception ex) {
    			PopUp("Not a valid course");
    		}//will pop up report for date and course if empty none enrolled
    		
    		for (enrollment enrol : enrollments) {
    			if (enrol.courseID == cID && enrol.year == year && enrol.semester.equals(semester)) {
    				student stud = sm.GetStudent(enrol.studentID);
    				report.appendText(stud.name + "     " + enrol.grade + "\n");
    			}}}//will add students to the report if there is students
    		
    		
    		catch(IllegalArgumentException ex) {
    			PopUp("Error: " + ex.getMessage());
    		}
    	});
        }

//=================================================================================================================================
    //manager classes below
class StudentFileManager{

	ArrayList<student> students = new ArrayList<>();
	
	StudentFileManager () throws IOException{
		File studentFile = new File("student.txt");
		
		if (!studentFile.exists()) {
			studentFile.createNewFile();		
			}
	
		
		BufferedReader sreader = new BufferedReader(new FileReader(studentFile));
		
		String cur = sreader.readLine();
		while (cur != null) {
			String[] data = cur.split(",");
			
			int ID = Integer.parseInt(data[0].trim());
			String NAME = (data[1].trim());
			String ADDRESS = (data[2].trim());
			String CITY = (data[3].trim());
			String STATE = (data[4].trim());
			int ZIP = Integer.parseInt(data[5].trim());
			
			
			student stud = new student(ID, NAME, ADDRESS, CITY, STATE, ZIP);
			students.add(stud);
			cur = sreader.readLine();
			
		}
		
		sreader.close();
	}
	
	boolean AddStudent(int ID, String Name, String Address, String City, String State, int Zip) throws IOException{
		try (BufferedWriter swriter = new BufferedWriter(new FileWriter("student.txt"))) {
			
			if (GetStudent(ID) == null) {
				
				student newstud = new student(ID, Name, Address, City, State, Zip);
			
				students.add(newstud);
				
				for(student stud : students) {
					String studData = String.join(",", Integer.toString(stud.id), stud.name, stud.address, stud.city, 
							stud.state, Integer.toString(stud.zip)).trim();
				swriter.write(studData + "\n");
				}
				swriter.flush();
				
				return true;		
			}
		
		for(student stud : students) {
			String studData = String.join(",", Integer.toString(stud.id), stud.name, stud.address, stud.city, 
					stud.state, Integer.toString(stud.zip)).trim();
		swriter.write(studData + "\n");
		}
		swriter.flush();
		//System.out.println("Error, student ID already exists");
		return false;
	}}
	
	
	student GetStudent(int id) {
		for (student stud : students) {
			if (stud.id == id) {
				return stud;
			}
		}
		return null;
		
	}
			 
	
boolean updateStudent(int ID, String Name, String Address, String City, String State, int Zip) throws IOException{
	try (BufferedWriter swriter = new BufferedWriter(new FileWriter("student.txt"))) {
		if (GetStudent(ID) != null) {
			
			student updateStud = GetStudent(ID);
			if (updateStud != null) {
				
				updateStud.name = Name;
				updateStud.address = Address;
				updateStud.city = City;
				updateStud.state = State;
				updateStud.zip = Zip;
			
			
			for(student stud: students) {
				String studData = String.join(",", Integer.toString(stud.id), stud.name, stud.address, stud.city, 
						stud.state, Integer.toString(stud.zip)).trim();
				swriter.write(studData + "\n");
			}
			swriter.flush();
			return true;
		}
	}
}
	
	//System.out.println("Error, Student ID does not exist");
	return false;
}
}

	

//=================================================================================================================================
class CourseFileManager{
	
	ArrayList<course> courses = new ArrayList<>();
	
	CourseFileManager () throws IOException{
		File courseFile = new File("course.txt");
		
		if (!courseFile.exists()) {
			courseFile.createNewFile();		
			}
		BufferedReader creader = new BufferedReader(new FileReader(courseFile));
		
		String cur = creader.readLine();
		
		while (cur != null) {
			String[] data = cur.split(",");
			
			int cID = Integer.parseInt(data[0].trim());
			String cName = data[1].trim();
			String cDescrip = data[2];
			String prof = data[3].trim();
			
			course cour = new course(cID, cName, cDescrip, prof);
			
			courses.add(cour);
			cur = creader.readLine();
			
		}
		creader.close();
	}
	
	boolean AddCourse(int CID, String Coursename, String Coursedescrip,String professor) throws IOException{
		try (BufferedWriter cwriter = new BufferedWriter(new FileWriter("course.txt"))) {
			
			if (GetCourse(CID) == null) {
				
				course newcour = new course(CID, Coursename, Coursedescrip, professor);
				courses.add(newcour);
				
				for(course cour : courses) {
					String courseData = String.join(",", Integer.toString(cour.courseID), 
							cour.courseName, cour.courseDescrip, cour.professor).trim();
					cwriter.write(courseData + "\n");
				}
				cwriter.flush();
				
				return true;		
			}
		
		for(course cour : courses) {
			String courseData = String.join(",", Integer.toString(cour.courseID), 
					cour.courseName, cour.courseDescrip, cour.professor).trim();
			cwriter.write(courseData + "\n");
		}
		cwriter.flush();
		//System.out.println("Error, course ID already exists");
		return false;
	}}
	
	
	course GetCourse(int cid) {
		
		for (course cour : courses) {
			if (cour.courseID == cid) {
				return cour;
			}
		}
		return null;
		}
			 

boolean updateCourse(int CID, String Coursename, String Coursedescrip, String Professor) throws IOException{
	try (BufferedWriter cwriter = new BufferedWriter(new FileWriter("course.txt"))) {
		
			course updateCour = GetCourse(CID);
			
			if (updateCour != null) {
				updateCour.courseName = Coursename;
				updateCour.courseDescrip = Coursedescrip;
				updateCour.professor = Professor;
			
			
			for (course cour : courses) {
				String courData = String.join("," ,Integer.toString(cour.courseID), 
						cour.courseName, cour.courseDescrip, cour.professor).trim();
	
				cwriter.write(courData + "\n");
			}
			
			cwriter.flush();
			return true;
			}}
	//System.out.println("Error, course ID does not exist");
	return false;
	}

}
//}
//====================================================================================================================================
class EnrollmentFileManager{
	
	ArrayList<enrollment> enrollments = new ArrayList<>();
	
	EnrollmentFileManager () throws IOException{
		File enrollmentFile = new File("enroll.txt");
		
		if (!enrollmentFile.exists()) {
			enrollmentFile.createNewFile();		
			}
		BufferedReader ereader = new BufferedReader(new FileReader(enrollmentFile));
		
		String cur = ereader.readLine();
		while (cur != null) {
			String[] data = cur.split(",");
			
			
				//int EID = Integer.parseInt(data[0].trim());
				int SID = Integer.parseInt(data[0].trim());
				int CID = Integer.parseInt(data[1].trim());
				int YEAR = Integer.parseInt(data[2].trim());
				String SEMESTER = data[3].trim();
				String GRADE = data[4].trim();
				
				
			enrollment enrol = new enrollment(SID, CID, YEAR, SEMESTER, GRADE);
			enrollments.add(enrol);
			cur = ereader.readLine();
			
		}
		ereader.close();
		
	}
	public ArrayList<enrollment> getEnrollments() {
        return enrollments;
    }
	
	boolean AddEnrollment(int SID, int CID, int YEAR, String SEMESTER,String GRADE, StudentFileManager sm, CourseFileManager cm) throws IOException{
		try (BufferedWriter ewriter = new BufferedWriter(new FileWriter("enroll.txt"))) {
			if (sm.GetStudent(SID) == null) {
				PopUp("Error, Student ID does not exist cannot add enrollment");
				return false;
			}
			if (cm.GetCourse(CID) == null) {
				PopUp("Error, Course ID does not exist cannot add enrollment");
				return false;
			}
				
			if (GetEnrollment(SID, CID, YEAR, GRADE) == null) {
					
				
				
				enrollment newenroll = new enrollment(SID, CID, YEAR, SEMESTER, GRADE);
				
				enrollments.add(newenroll);
				
				for (enrollment enrol : enrollments) {
					String data = (String.join(","  , Integer.toString(enrol.studentID) , 
							Integer.toString(enrol.courseID),Integer.toString(enrol.year) , enrol.semester , enrol.grade)).trim();
					ewriter.write(data + "\n");
				}
				
				ewriter.flush();
				
				return true;		
			}
		
		for (enrollment enrol : enrollments) {
			String data = (String.join(","  , Integer.toString(enrol.studentID) , 
					Integer.toString(enrol.courseID),Integer.toString(enrol.year) , enrol.semester , enrol.grade)).trim();
			ewriter.write(data + "\n");
		}
		
		ewriter.flush();
		PopUp("Error, enrollment already exists");
		return false;
	}}
	
	
	enrollment GetEnrollment(int SID, int CID, int YEAR, String SEMESTER) {
		
		for (enrollment enrol : enrollments) {
			if(enrol.studentID  == SID && enrol.courseID  == 
					CID && enrol.year  == YEAR && enrol.semester.equals(SEMESTER)) 
				return enrol;
			
		}
		return null;
	}
	
	 

boolean updateEnrollment(int SID, int CID, int YEAR, String SEMESTER,String GRADE) throws IOException{
	try (BufferedWriter ewriter = new BufferedWriter(new FileWriter("enroll.txt"))) {
		
		
		enrollment updateEnrol = GetEnrollment(SID, CID, YEAR, SEMESTER);
		
		if (updateEnrol != null) {
		
			updateEnrol.grade = GRADE;
		
			for(enrollment enrol : enrollments) {
				String data = (String.join("," , Integer.toString(enrol.studentID) , 
						Integer.toString(enrol.courseID),Integer.toString(enrol.year) , enrol.semester , enrol.grade)).trim();
				ewriter.write(data + "\n");
			}
			
			ewriter.flush();
			return true;
		
	}
	
	
		PopUp("Error, enrollment does not exist");
	return false;
}
}
}
//=================================================================================================================================
    
class student { 
	int id;
	String name;
	String address;
	String city;
	String state;
	int zip;
	
	student(int ID, String Name, String Address, String City, String State, int Zip){
		id = ID;
		name = Name;
		address = Address;
		city = City;
		state = State;
		zip = Zip;
		
		
	}
}

class course {
	int courseID;
	String courseName;
	String courseDescrip;
	String professor;
	
	course(int COURSEID, String COURSENAME, String COURSEDESCRIP,String PROFESSOR){
		courseID = COURSEID;
		courseName = COURSENAME;
		courseDescrip = COURSEDESCRIP;
		professor = PROFESSOR;
	}
	
	
	
}
class enrollment {
	int studentID;
	int courseID;
	int year;
	String semester;
	String grade;
	 String courseNum;
	
	enrollment(int SID, int CID, int YEAR, String SEMESTER, String GRADE){
		this.studentID = SID;
		this.courseID = CID;
		this.year = YEAR;
		this.semester = SEMESTER;
		this.grade = GRADE;
	}	
	
}
//the datatype that is stored in the table
public static class enrolUser{
	private String name;
	private String courseNumber;
	private String semester;
	private String grade;
	private int year;
	private int courseID;
	
	enrolUser(String Name, String CourseNum, int CourseID, String Semester,
			int Year, String Grade){
		this.name = Name;
		this.courseNumber = CourseNum;
		this.courseID = CourseID;
		this.semester = Semester;
		this.year = Year;
		this.grade = Grade;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	
	
}
    	
    
    public static void main(String[] args) {
        launch(args);
    }
    }
