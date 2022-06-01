package service;

import model.Class;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService{
    ClassService classService = new ClassServiceImpl();

    protected Connection getConnection() {
        Connection connection = null;
        try {
            java.lang.Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false", "root", "123456");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();

        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("select * from student");) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String name = rs.getString("name");
                int classId = rs.getInt("classID");
                Class clazz = classService.findById(classId);
                students.add(new Student(id,name,age,clazz));

            }
        } catch (SQLException e) {
        }

        return students;

    }

    @Override
    public void add(Student student) throws SQLException {
        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("insert into student (name,age,classId) values (?,?,?)");) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setInt(3, student.getClazz().getId());
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Student findById(int id) {
        Student student = new Student();
        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("select * from student where id = ?");) {
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int age = rs.getInt("age");
                String name = rs.getString("name");
                int classId = rs.getInt("classID");
                Class clazz = classService.findById(classId);
                student = new Student(id, name,age,clazz);
            }
        } catch (SQLException e) {
        }
        return student;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("delete from student where id=?");) {
            statement.setInt(1, id);
            System.out.println(statement);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean update(Student student) throws SQLException {
        boolean upDate;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = getConnection().prepareStatement("update student set name = ? ,age = ?, classId=? where id = ?");) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setInt(3, student.getClazz().getId());
            preparedStatement.setInt(4, student.getId());
            upDate= preparedStatement.executeUpdate()>0;
        }
        return upDate;
    }

    @Override
    public List<Student> findByName(String key) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("select * from student where name like ?");) {
            preparedStatement.setString(1, "%"+key+"%");
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String name = rs.getString("name");
                int classId = rs.getInt("cID");
                Class clazz = classService.findById(classId);
                students.add(new Student(id,name,age,clazz));

            }
        } catch (SQLException e) {
        }

        return students;
    }

    @Override
    public List<Student> findAllOderByAge() {
        return null;
    }

    @Override
    public List<Student> findAllByClass(int cID) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("select * from student where classId = ?");) {
            preparedStatement.setInt(1, cID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String name = rs.getString("name");
                int classId = rs.getInt("classId");
                Class clazz = classService.findById(classId);
                students.add(new Student(id,name,age,clazz));

            }
        } catch (SQLException e) {
        }

        return students;
    }

    @Override
    public List<Student> findAllByNameContains(String name) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from student where name like ?");) {
            preparedStatement.setString(1, "%" + name + "%");
            System.out.println(preparedStatement); //in ra câu truy vấn.
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                name = rs.getString("name");
                int clazzId = rs.getInt("classId"); // lấy ra classId từ bảng student trong db
                Class clazz = classService.findById(clazzId); // từ classId vừa lấy được, dùng ClassService để tìm đối tượng class tương ứng
                students.add(new Student(id, name, age, clazz)); //thêm đối tượng là danh sách
            }
        } catch (SQLException e) {
        }
        return students;
    }
}