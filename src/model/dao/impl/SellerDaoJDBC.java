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
import java.util.List;


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
                Department dep = new Department (  );
                Seller obj = new Seller ();
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
    public List<Seller> findAll() {
        return null;
    }
}
