package joao.ifpr.foz.ifprstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import joao.ifpr.foz.ifprstore.repositories.SellerRepository;

import java.io.IOException;

@WebServlet("/sellers/delete")
public class SellerDeleteController extends HttpServlet {
    SellerRepository sellerRepository = new SellerRepository();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Integer id = Integer.parseInt(req.getParameter("id"));
        sellerRepository.delete(id);

        resp.sendRedirect("http://localhost:8585/IFPRStore_war_exploded/");
    }
}
