package controller;

import model.Class;
import model.Student;
import service.ClassService;
import service.ClassServiceImpl;
import service.StudentService;
import service.StudentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    ClassService classService = new ClassServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String cID = request.getParameter("cID");
        String key = request.getParameter("key");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        List<Class> classes = classService.findAll();
        request.setAttribute("classes", classes);
        List<Student> students = studentService.findAll();
        if (cID != null) {
            students = studentService.findAllByClass(Integer.parseInt(cID));
        }
        if (key != null) {
            students = studentService.findAllByNameContains(key);
        }
        request.setAttribute("students", students);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
    }
}
