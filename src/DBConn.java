import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class

DBConn {


    final static String PASS = "";
    final static String UID = "root";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Comp305";
    private Connection conn;
    private ResultSet resultSet;
    private PreparedStatement getAllUsersStmt, deleteUserStmt, insertUserStmt, updateUserStmt, findUserStmt;

    DBConn() throws Exception {

        conn = DriverManager.getConnection(DATABASE_URL, UID, PASS);
        insertUserStmt = conn.prepareStatement("insert into users (name, last_name, age) values (?, ?, ?)");

        updateUserStmt = conn.prepareStatement("update users set name = ?, last_name = ?, age = ? where usersid = ?");

        deleteUserStmt = conn.prepareStatement("delete from users where usersid = ?");

        getAllUsersStmt = conn.prepareStatement("Select * from users");

        findUserStmt = conn.prepareStatement("Select * from users where usersid = ?");

        System.out.println("Connection to MySQL Database succesfull!!!");
    }

    public void insertUser(User user) throws Exception {

        insertUserStmt.setString(1, user.getName());
        insertUserStmt.setString(2, user.getLastName());
        insertUserStmt.setInt(3, user.getAge());
        System.out.println(insertUserStmt.toString());
        insertUserStmt.execute();
        disconnect();
    }

    public void updateUser(User user) throws Exception {
        updateUserStmt.setString(1, user.getName());
        updateUserStmt.setString(2, user.getLastName());
        updateUserStmt.setInt(3, user.getAge());
        updateUserStmt.setInt(4, user.getUserId());
        System.out.println(updateUserStmt.toString());
        updateUserStmt.executeUpdate();
        disconnect();
    }

    public void deleteUser(User user) throws Exception {
        deleteUserStmt.setInt(1, user.getUserId());
        System.out.println(deleteUserStmt.toString());
        deleteUserStmt.executeUpdate();
        disconnect();
    }


    public User getUserByID (User user) throws Exception {
        findUserStmt.setInt(1, user.getUserId());
        resultSet = findUserStmt.executeQuery();
        if (resultSet != null){
            while (resultSet.next()){
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getInt("age"));
            }
        }

        return user;
    }

    public User[] getAllUsers() throws Exception {
        ArrayList<User> results = new ArrayList<>();
        User[] finalResults;
        resultSet = getAllUsersStmt.executeQuery();
        if (resultSet != null) {
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("usersid"));
                user.setLastName(resultSet.getString("last_name"));
                user.setName(resultSet.getString("name"));
                user.setAge(Integer.parseInt(resultSet.getString("age")));
                results.add(user);
            }
            resultSet.close();

            //returning array
            finalResults = new User[results.size()];
            results.toArray(finalResults);

        } else {
            finalResults = new User[0];
        }
        disconnect();
        return finalResults;
    }

    private void disconnect() throws Exception {

        getAllUsersStmt.close();
        deleteUserStmt.close();
        insertUserStmt.close();
        updateUserStmt.close();
        findUserStmt.close();
        conn.close();

    }
}
