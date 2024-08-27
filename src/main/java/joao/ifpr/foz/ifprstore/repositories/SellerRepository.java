package joao.ifpr.foz.ifprstore.repositories;

import joao.ifpr.foz.ifprstore.connection.ConnectionFactory;
import joao.ifpr.foz.ifprstore.exceptions.DatabaseException;
import joao.ifpr.foz.ifprstore.models.Department;
import joao.ifpr.foz.ifprstore.models.Seller;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static joao.ifpr.foz.ifprstore.connection.ConnectionFactory.connection;

public class SellerRepository {

    private final Connection conn;

    public  SellerRepository(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        conn = ConnectionFactory.getConnection();
    }

    public List<Seller> getSellers(){

        Statement statement = null;
        ResultSet resultSet = null;
        Seller seller;
        Department department;
        List<Seller> sellersList = new ArrayList<Seller>();
        try{
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT seller.*,department.Name as DepName FROM seller " +
                    "INNER JOIN department ON seller.DepartmentId = department.Id");

            while (resultSet.next()){
                Map<Integer, Department> map = new HashMap<Integer, Department>();
                while (resultSet.next()){
                    department = map.get(resultSet.getInt("DepartmentId"));
                    if(department == null){
                        department = instantiateDepartment(resultSet);
                        map.put(resultSet.getInt("DepartmentId"), department );
                    }
                    seller = this.instantiateSeller(resultSet,department);
                    sellersList.add(seller);
                }
            }
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e);
        }finally {
            ConnectionFactory.closeConnection();
        }

        return sellersList;
    }


    public void insert(Seller s){

        String sql = "INSERT INTO seller (Name,Email,BirthDate, BaseSalary, DepartmentId) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement statement =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, s.getName());
            statement.setString(2, s.getEmail());
            statement.setDate(3, Date.valueOf(s.getBirthDate()));
            statement.setDouble(4, s.getBaseSalary());
            statement.setInt(5, 1);

            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0){
                ResultSet id = statement.getGeneratedKeys();
                System.out.println("Row inserted: "+rowInserted);
                id.next();
                s.setId(id.getInt(1));
            }


        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }


    public void updateSalary(Integer departmentId, Double bonus) {
        String sql = "UPDATE seller SET BaseSalary = BaseSalary+ ? WHERE DepartmentId = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1,bonus);
            statement.setInt(2,departmentId);

            int updateRow = statement.executeUpdate();

            if(updateRow > 0){
                System.out.println("Rows updated: "+ updateRow);
            }

        }catch(Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally{
            ConnectionFactory.closeConnection();
        }
    }

    public void delete(Integer id){
        String sql = "DELETE FROM seller WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int deleteRow = statement.executeUpdate();
            if(deleteRow > 0){
                System.out.println("Rows deleted "+ deleteRow);
            }
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public List<Seller> getByDepartment(Integer id){

        List<Seller> sellerList = new ArrayList<Seller>();
        Seller seller;
        Department department;
        String sql= "SELECT seller.*,department.Name as DepName "+
                "FROM seller INNER JOIN department "+
                "ON seller.DepartmentId = department.Id "+
                "WHERE sellerId = ? ";

        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Department> map = new HashMap<Integer, Department>();
            while (resultSet.next()){
                department = map.get(resultSet.getInt("DepartmentId"));
                if(department == null){
                    department = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department );
                }
                seller = this.instantiateSeller(resultSet,department);
                sellerList.add(seller);
            }

        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return  sellerList;
    }

    public Seller getById(Integer id){

        Seller seller;
        Department department;

        String sql= "SELECT seller.*,department.Name as DepName "+
                    "FROM seller INNER JOIN department "+
                    "ON seller.DepartmentId = department.Id "+
                    "WHERE sellerId = ? ";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                department = instantiateDepartment(resultSet);
                seller = instantiateSeller(resultSet, department);

            }else{
                throw new DatabaseException("Vendedor nao Encontrado");
            }
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return seller;
    }



    private static Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department  department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    private static Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        
        seller.setName(resultSet.getString("Name"));
        seller.setId(resultSet.getInt("Id"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));

        seller.setDepartment(department);
        return seller;
    }

}
