package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void insert(Seller dept) {

    }

    @Override
    public void update(Seller dept) {

    }

    @Override
    public void deleteById(Integer id) {

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

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
