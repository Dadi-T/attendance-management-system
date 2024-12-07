package donkillha.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import donkillha.controller.DatabaseManager;
import donkillha.view.ActionListeners.StudentSearchButtonListener;

import java.awt.CardLayout;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ViewerManager {
    private void addTextField(String label, int columns, JPanel panel) {
        JPanel fieldPanel = new JPanel();
        JLabel fieldLabel = new JLabel(label);
        JTextField fieldTextField = new JTextField(columns);
        fieldPanel.add(fieldLabel);
        fieldPanel.add(fieldTextField);
        panel.add(fieldPanel);
    }

    public Object[][] convertTo2DObjectArray(ArrayList<ArrayList<Object>> twoDList) {
        // Get the dimensions
        int numRows = twoDList.size();
        int numCols = twoDList.isEmpty() ? 0 : twoDList.get(0).size();

        // Create the Object[][] array
        Object[][] objectArray = new Object[numRows][numCols];

        // Fill the Object[][] array
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                objectArray[i][j] = twoDList.get(i).get(j);
            }
        }

        return objectArray;
    }

    public ViewerManager() {
        // Create a new JFrame
        JFrame frame = new JFrame("Grades");

        frame.setLayout(new CardLayout());
        JPanel studentSearchPanel = new JPanel();
        addTextField("First name:", 15, studentSearchPanel);
        addTextField("Last name:", 15, studentSearchPanel);
        addTextField("Student id", 15, studentSearchPanel);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new StudentSearchButtonListener());
        studentSearchPanel.add(searchButton);
        frame.add(studentSearchPanel, "StudentPanel");

        DatabaseManager mysql = new DatabaseManager();
        ResultSet result = (ResultSet) mysql.executeQuery(
                "SELECT students_table.first_name,     students_table.last_name, students_table.id, classes_table.class_name, grades_table.score FROM Students students_table JOIN  Grades grades_table ON students_table.id = grades_table.student_id JOIN     Classes classes_table ON grades_table.class_id = classes_table.id",
                null);
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try {
            while (result.next()) {
                ArrayList<Object> row1 = new ArrayList<>();
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int id = result.getInt("id");
                String className = result.getString("class_name");
                int score = result.getInt("score");

                row1.add(firstName);
                row1.add(lastName);
                row1.add(id);
                row1.add(className);
                row1.add(score);
                data.add(row1);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String column[] = { "First Name", "Last Name", "Student id", "Class", "Score" };
        // Convert to Object[][]
        Object[][] objectArray = convertTo2DObjectArray(data);

        JTable gradesTable = new JTable(objectArray, column);

        JScrollPane sp = new JScrollPane(gradesTable);
        studentSearchPanel.add(sp);

        JPanel classesPanel = new JPanel();
        JLabel fieldLabel = new JLabel("Choose Class");
        // TODO: Change this call to be in the server - it needs to call the server to
        // get the names

        String classesNames[] = { "Mathematics", "Physics" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox comboBox = new JComboBox(classesNames);

        JButton selectButton = new JButton("Search");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Change this to call the server and get the students score but only
                // relative to that class
                DatabaseManager mysql = new DatabaseManager();
                ResultSet result = (ResultSet) mysql.executeQuery(
                        "SELECT students_table.first_name, students_table.last_name, students_table.id, classes_table.class_name, grades_table.score FROM Students students_table JOIN Grades grades_table ON students_table.id = grades_table.student_id JOIN Classes classes_table ON grades_table.class_id = classes_table.id WHERE classes_table.class_name = ? ",
                        List.of("Physics"));
                ArrayList<ArrayList<Object>> data = new ArrayList<>();
                try {
                    while (result.next()) {
                        ArrayList<Object> row1 = new ArrayList<>();
                        String firstName = result.getString("first_name");
                        String lastName = result.getString("last_name");
                        int id = result.getInt("id");
                        String className = result.getString("class_name");
                        int score = result.getInt("score");

                        row1.add(firstName);
                        row1.add(lastName);
                        row1.add(id);
                        row1.add(className);
                        row1.add(score);
                        data.add(row1);
                    }

                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }
                String column[] = { "First Name", "Last Name", "Student id", "Class", "Score" };
                // Convert to Object[][]
                Object[][] objectArray = convertTo2DObjectArray(data);

                JTable gradesTable = new JTable(objectArray, column);

                JScrollPane sp = new JScrollPane(gradesTable);
                classesPanel.add(sp);

                frame.revalidate();
                frame.repaint();
            }
        });
        classesPanel.add(fieldLabel);
        classesPanel.add(comboBox);
        classesPanel.add(selectButton);
        frame.add(classesPanel, "classesPanel");

        JMenu changeView = new JMenu("Change");
        JMenuItem gradesView = new JMenuItem("Grades");
        changeView.add(gradesView);
        JMenuItem classView = new JMenuItem("Class");
        changeView.add(classView);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(changeView);
        gradesView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        frame.setJMenuBar(menuBar);
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();

        classView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "classesPanel");
            }
        });

        gradesView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "StudentPanel");
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Set frame properties
        frame.setSize(screenSize);
        // Make it full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Close operation
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        frame.setVisible(true);
    }

}
