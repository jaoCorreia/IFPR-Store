package joao.ifpr.foz.ifprstore.repositories;
import joao.ifpr.foz.ifprstore.models.Seller;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;


public class SellerRepositoryTest {

@Test

    public void deveInserirUmRegistro(){

        SellerRepository repository = new SellerRepository();
        Seller seller = new Seller();
        seller.setName("joao");
        seller.setEmail("email");
        seller.setBaseSalary(1000.00);
        seller.setBirthDate(LocalDate.of(2024,10,11));

        repository.insert(seller);
    }

    @Test

    public void deveAtualizarBaseSalarial(){
        SellerRepository repository = new SellerRepository();
        double bonus  = 300.00;
        int idDepartment = 3;
        repository.updateSalary(3,bonus);
    }

    @Test

    public  void deveDeletarSeller(){
        SellerRepository repository = new SellerRepository();
        int id = 1;
        repository.delete(id);
    }

    @Test
    public void deveMeRetornaUmSeller(){
        SellerRepository repository = new SellerRepository();
        Seller seller = repository.getById(1);
        System.out.println(seller);
        System.out.println(seller.getDepartment());
    }


    @Test
    public void deveBuscarTodosSellers(){
    SellerRepository repository = new SellerRepository();
    List<Seller> seller  = repository.getSellers();
        System.out.println(seller);
    }
}
