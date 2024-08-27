package joao.ifpr.foz.ifprstore.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import joao.ifpr.foz.ifprstore.models.Department;
import joao.ifpr.foz.ifprstore.models.Seller;
import joao.ifpr.foz.ifprstore.repositories.DepartmentRepository;
import joao.ifpr.foz.ifprstore.repositories.SellerRepository;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/sellers/create")
public class SellerCreateController extends HttpServlet {
    SellerRepository sellerRepository = new SellerRepository();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/sellers-create.jsp");

        dispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/sellers-create.jsp");

        Seller seller = new Seller();
        String name = req.getParameter("field_name");
        String email = req.getParameter("field_email");
        LocalDate birthDate = LocalDate.parse(req.getParameter("field_birthDate"));
        Double baseSalary = Double.parseDouble(req.getParameter("field_baseSalary"));
        Integer idDepartment = Integer.valueOf(req.getParameter("field_department"));

        seller.setName(name);
        seller.setEmail(email);
        seller.setBirthDate(birthDate);
        seller.setBaseSalary(baseSalary);
        DepartmentRepository departmentRepository = new DepartmentRepository();
        Department department = departmentRepository.getById(idDepartment);
        seller.setDepartment(department);

        sellerRepository.insert(seller);

        resp.sendRedirect("/sellers");
    }
}
