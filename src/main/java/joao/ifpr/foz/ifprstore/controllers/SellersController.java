package joao.ifpr.foz.ifprstore.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import joao.ifpr.foz.ifprstore.models.Seller;
import joao.ifpr.foz.ifprstore.repositories.SellerRepository;

import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"","/sellers"})
public class SellersController extends HttpServlet {

    private SellerRepository sellerRepository;
    public SellersController() {
        sellerRepository = new SellerRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Seller> sellerList = sellerRepository.getSellers();
        for(Seller seller: sellerList){
            System.out.println(seller);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/sellers.jsp");
        req.setAttribute("sellers", sellerList);
        dispatcher.forward(req, resp);
    }
}
