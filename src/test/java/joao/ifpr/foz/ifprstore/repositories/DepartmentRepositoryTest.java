package joao.ifpr.foz.ifprstore.repositories;

import joao.ifpr.foz.ifprstore.models.Department;
import joao.ifpr.foz.ifprstore.models.Seller;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DepartmentRepositoryTest {




    @Test
    public void deveMeRetornaUmDepartamento() {
        DepartmentRepository repository = new DepartmentRepository();
        List<Department> department = repository.getEmptyDepartments();
        Department dep = new Department();
        //dep.setName("jOA");
//        repository.insert(dep);
        System.out.println(department);
    }



    @Test
    public void deveDeletar() {
        DepartmentRepository repository = new DepartmentRepository();


    }
}
