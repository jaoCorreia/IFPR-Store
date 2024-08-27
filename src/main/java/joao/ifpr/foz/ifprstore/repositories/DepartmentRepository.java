package joao.ifpr.foz.ifprstore.repositories;

import joao.ifpr.foz.ifprstore.connection.ConnectionFactory;
import joao.ifpr.foz.ifprstore.exceptions.DatabaseException;
import joao.ifpr.foz.ifprstore.models.Department;
import joao.ifpr.foz.ifprstore.models.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static joao.ifpr.foz.ifprstore.connection.ConnectionFactory.connection;

public class DepartmentRepository {
    Connection connection;

    public DepartmentRepository(){
        connection = ConnectionFactory.getConnection();
    }


    public void delete(int id){
        String sql = "DELETE FROM department WHERE Id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage()); 
        }finally {
            ConnectionFactory.closeConnection();
        }
        
    }

    public void insert(Department department){
        String sql = "INSERT INTO department (Name) VALUES (?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,department.getName());
            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0){
                ResultSet id = statement.getGeneratedKeys();
                System.out.println("Row Inserted : " + rowInserted);
                id.next();
                department.setId(id.getInt(1));
            }

        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public void update(Integer departmentId, String name){
         String sql = "UPDATE department SET Name = ? WHERE Id = ?";
         try{
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setString(1,name);
             statement.setInt(2,departmentId);

             int updateRow = statement.executeUpdate();
             if(updateRow > 0){
                 System.out.println("Row Updated : " + updateRow);
             }

         }catch(Exception e){
             throw  new RuntimeException(e.getMessage());
         }finally {
             ConnectionFactory.closeConnection();
         }
    }

    public List<Department> getAll(){
        List<Department> departments = new ArrayList<Department>();
        ResultSet resultSet;
        Department department;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");
            while(resultSet.next()){
                department = new Department();
                department.setId(resultSet.getInt("Id"));
                department.setName(resultSet.getString("Name"));
                departments.add(department);
            }
            resultSet.close();
            return departments;

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }

    }



    public Department getById(int id){
        Department department;
        String sql= "SELECT * FROM department WHERE Id = ? ";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                department = instantiateDepartment(resultSet);

            }else{
                throw new DatabaseException("Departamento nao Encontrado");
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return department;
    }

    public List<Department> getByName(String name){
        Department department;
        ResultSet resultSet;
        List<Department> departments = new ArrayList<Department>();
        String sql= "SELECT * FROM department WHERE name LIKE ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+name+"%");
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                department = new Department();
                department = instantiateDepartment(resultSet);
                departments.add(department);
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return departments;
    }



    public List<Department> getEmptyDepartments(){
        Department department;
        ResultSet resultSet;
        List<Department> departments = new ArrayList<Department>();
        String sql= "SELECT department.id, department.name FROM department\n"+
                    "LEFT JOIN seller ON seller.DepartmentId = department.Id\n"+
                    "WHERE seller.id IS NULL";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                department = new Department();
                department = instantiateDepartment(resultSet);
                departments.add(department);
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return departments;
    }


    private static Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();

        department.setName(resultSet.getString("Name"));
        department.setId(resultSet.getInt("Id"));



        return department;
    }



}
