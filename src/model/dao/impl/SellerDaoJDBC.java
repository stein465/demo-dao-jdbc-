package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement ( "insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS );
            st.setString ( 1, seller.getName () );
            st.setString ( 2, seller.getEmail () );
            st.setDate ( 3, new java.sql.Date ( seller.getBirthDate ().getTime () )  );
            st.setDouble (4, seller.getBaseSalary () );
            st.setInt ( 5 ,  seller.getDept ().getId ());

            int rowsAffected = st.executeUpdate ();
            if (rowsAffected>0){
                ResultSet rs = st.getGeneratedKeys ();
                if (rs.next ()){
                    int id = rs.getInt ( 1 );
                    seller.setId ( id );
                }
                DB.closeResultSet ( rs );
            }
            else{
                throw  new DbException ( "unexpected erroe --- no rows affected" );
            }
        }
        catch (SQLException e){
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement ( st );
        }

    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement ( "UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?  " +
                    "WHERE id  = ? ", Statement.RETURN_GENERATED_KEYS );

            st.setString ( 1, seller.getName () );
            st.setString ( 2,seller.getEmail () );
            st.setDate ( 3,new java.sql.Date ( seller.getBirthDate ().getTime () ) );
            st.setDouble ( 4,4000. );
            st.setInt ( 5,seller.getDept ().getId () );
            st.setInt ( 6, seller.getId () );

            st.executeUpdate ();
        }
        catch (SQLException e){
            throw new DbException ( e.getMessage () );
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st =null;

        try{
            st = conn.prepareStatement ( "DELETE FROM seller WHERE id = ?" );
            st.setInt ( 1, id );

            int rows = st.executeUpdate ();
            if (rows == 0){
                throw new DbException ( "there is not any seller with this ID " );
            }

        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement ( st );
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement (
                    "select seller.*, department.Name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.id " +
                    "where seller.Id = ?" );

            st.setInt ( 1, id );
            rs = st.executeQuery ();
            if(rs.next ()){
                Department dep = instantiateDepartment ( rs );
                Seller obj = instantiateSeller ( rs,dep ) ;
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement ( st );
            DB.closeResultSet (rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department dept) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement (
                    "select seller.*, department.Name as DepName " +
                            "from seller inner join department " +
                            "on seller.DepartmentId = department.id " +
                            "where Department.id = ? " +
                            "order by Name" );

            st.setInt ( 1, dept.getId () );
            rs = st.executeQuery ();

            Map<Integer,Department> map = new HashMap<> (  );   //cria map com departamentos
            List<Seller> list = new ArrayList<> (  );

            while (rs.next ()){
                Department dep = map.get ( rs.getInt ( "DepartmentId" ) );         //salva algum departamento dentro da variavel podendo retornar nulo se nao estiver no map
                if (dep == null){                                                               // se retornar nulo cadastra dep no map para nao repetir cadastro de departamentos
                    dep = instantiateDepartment ( rs );
                    map.put ( rs.getInt ( "DepartmentId" ),dep );
                }
                Seller obj = instantiateSeller ( rs,dep ) ;
                list.add ( obj );
            }
            return list;
        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement ( st );
            DB.closeResultSet (rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement ( "select seller.*, department.Name as DepName from seller inner join department on " +
                    "seller.DepartmentId = department.id order by Name" );
            rs =st.executeQuery ();

            List<Seller> list = new ArrayList<> (  );
            Map<Integer, Department> map = new HashMap<> (  );

            while(rs.next ()){
                Department dep = map.get ( rs.getInt ( "DepartmentId" ) );

                if (dep == null){
                    dep = instantiateDepartment ( rs );
                    map.put ( rs.getInt ( "DepartmentId" ),dep );
                }

                Seller obj = instantiateSeller ( rs,dep );
                list.add ( obj );
            }
            return list;

        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement ( st );
            DB.closeResultSet ( rs );
        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department (  );
        dep.setId ( rs.getInt ( "DepartmentId" ) );
        dep.setName ( rs.getString ( "DepName" ) );
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs,Department dep) throws SQLException {
        Seller obj = new Seller (  );
        dep.setId ( rs.getInt ( "DepartmentId" ) );
        dep.setName ( rs.getString ( "DepName" ) );
        obj.setId ( rs.getInt ( "Id" ) );
        obj.setName ( rs.getString ( "Name" ) );
        obj.setEmail ( rs.getString ( "Email" ) );
        obj.setBirthDate ( rs.getDate ( "BirthDate" ) );
        obj.setBaseSalary ( rs.getDouble ( "BaseSalary" ) );
        obj.setDept ( dep );

        return obj;
    }



}
