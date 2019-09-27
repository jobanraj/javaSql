import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFormPractical {

    public static void main(String[] args) {
        mainForm f = new mainForm("MySQl Basic Statements");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 400);
        f.setVisible(true);
    }
}

class mainForm extends JFrame {

    public mainForm(String title) {
        super(title);
        setLayout(new GridLayout(5, 1));

        SelectPanel selectPanel = new SelectPanel();
        selectPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        add(selectPanel);

        InsertPanel insertPanel = new InsertPanel();
        insertPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(insertPanel);

        DeletePanel deletePanel = new DeletePanel();
        deletePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(deletePanel);

        SelectSingleUserPanel selectSingleUserPanel = new SelectSingleUserPanel();
        selectSingleUserPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(selectSingleUserPanel);

        UpdatePanel updatePanel = new UpdatePanel();
        updatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(updatePanel);
    }

    //creating panels for each instruction in SQL

    //Select all Statament

    private JButton selectButton = new JButton("Select all");
    private JTextArea selectResult = new JTextArea(4, 30);

    private class SelectPanel extends JPanel {
        public SelectPanel() {

            setLayout(new FlowLayout());


            add(new JScrollPane(selectResult));

            selectButton.addActionListener(new ActionsPerformed());
            add(selectButton);
        }
    }


    //insert Statement
    private JTextField insertNameTextField = new JTextField(25);
    private JTextField insertLastNameTextField = new JTextField(25);
    private JTextField insertAgeTextField = new JTextField(25);
    private JButton insertButton = new JButton("Insert");

    private class InsertPanel extends JPanel {
        public InsertPanel() {
            setLayout(new GridLayout(4, 2));

            insertButton.addActionListener(new ActionsPerformed());

            add(new JLabel("Name"));
            add(insertNameTextField);
            add(new JLabel("Last Name"));
            add(insertLastNameTextField);
            add(new JLabel("Age"));
            add(insertAgeTextField);
            add(new JPanel());
            add(insertButton);
        }
    }

    //delete statement

    private JTextField deleteIdTextField = new JTextField(25);
    private JLabel deleteIdLabel = new JLabel("Please insert Id to erase");
    private JButton deleteButton = new JButton("delete item");

    private class DeletePanel extends JPanel {

        public DeletePanel() {

            setLayout(new FlowLayout());
            add(deleteIdLabel);
            add(deleteIdTextField);
            deleteButton.addActionListener(new ActionsPerformed());
            add(deleteButton);

        }
    }


    private JTextField searchIdTextfield = new JTextField(5);
    private JTextField searchResultTextfield = new JTextField(25);
    private JButton searchButton = new JButton("Search User");


    class SelectSingleUserPanel extends JPanel {

        public SelectSingleUserPanel() {
            setLayout(new FlowLayout());
            add(new JLabel("Insert User id"));
            add(searchIdTextfield);
            add(new JLabel("Name of User"));
            add(searchResultTextfield);
            searchButton.addActionListener(new ActionsPerformed());
            add(searchButton);
        }
    }

    private JTextField updateNameTextField = new JTextField(25);
    private JTextField updateLastNameTextField = new JTextField(25);
    private JTextField updateAgeTextField = new JTextField(25);
    private JTextField updateIdTextField = new JTextField(5);
    private JButton updateButton = new JButton("update");


    class UpdatePanel extends JPanel {

        public UpdatePanel() {
            setLayout(new GridLayout(5,2));

            updateButton.addActionListener(new ActionsPerformed());

            add(new JLabel("Name"));
            add(updateNameTextField);
            add(new JLabel("Last Name"));
            add(updateLastNameTextField);
            add(new JLabel("Age"));
            add(updateAgeTextField);
            add(new JLabel("Id"));
            add(updateIdTextField);

            add(updateButton);
        }

    }


    class ActionsPerformed implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //defining local buttons for switch statement
            String selectButtonCommand = selectButton.getActionCommand();
            String insertButtonCommand = insertButton.getActionCommand();
            String deleteButtonCommand = deleteButton.getActionCommand();
            String searchButtonCommand = searchButton.getActionCommand();
            String updateButtonCommand = updateButton.getActionCommand();

            //new DBConn for making a new connection to the MySQL Database


            if (e.getActionCommand().equals(selectButtonCommand)) {
                try {
                    DBConn dbConn = new DBConn();
                    User users[] = dbConn.getAllUsers();
                    selectResult.setText(null); // clean the result Text Area
                    //setting a format
                    String header = "user id\tName\tLast Name\tAge";
                    selectResult.append(header);
                    selectResult.append("\n");
                    for (User user : users) {
                        String lineToAppend = user.getUserId() + "\t" + user.getName() + "\t" + user.getLastName() +
                                "\t" + user.getAge();
                        selectResult.append(lineToAppend);
                        selectResult.append("\n");
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                //statement for the insert button
            } else if (e.getActionCommand().equals(insertButtonCommand)) {

                try {
                    DBConn dbConn = new DBConn();
                    //creating the user that has to be inserted

                    User newUser = new User();
                    newUser.setName(insertNameTextField.getText());
                    newUser.setLastName(insertLastNameTextField.getText());
                    newUser.setAge(Integer.parseInt(insertAgeTextField.getText()));
                    dbConn.insertUser(newUser);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                //statement for the delete button
            } else if (e.getActionCommand().equals(deleteButtonCommand)) {

                try {
                    DBConn dbConn = new DBConn();
                    //creating the user that has to be deleted

                    User oldUser = new User();
                    oldUser.setUserId(Integer.parseInt(deleteIdTextField.getText()));
                    dbConn.deleteUser(oldUser);

                } catch (Exception e1) {

                    e1.printStackTrace();

                }
            } else if (e.getActionCommand().equals(searchButtonCommand)) {
                try {
                    DBConn dbConn = new DBConn();

                    User oldUser = new User();
                    oldUser.setUserId(Integer.parseInt(searchIdTextfield.getText()));
                    User retrieveUser = dbConn.getUserByID(oldUser);
                    searchResultTextfield.setText(retrieveUser.getName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if (e.getActionCommand().equals(updateButtonCommand)) {
                try {
                    DBConn dbConn = new DBConn();
                    //creating the user that has to be inserted

                    User newUser = new User();
                    newUser.setName(updateNameTextField.getText());
                    newUser.setLastName(updateLastNameTextField.getText());
                    newUser.setAge(Integer.parseInt(updateAgeTextField.getText()));
                    newUser.setUserId(Integer.parseInt(updateIdTextField.getText()));
                    dbConn.updateUser(newUser);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
}






