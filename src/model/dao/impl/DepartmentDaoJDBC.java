package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    public Connection conn = null;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department dept) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement ( "INSERT INTO department (Name) values (?)", Statement.RETURN_GENERATED_KEYS );
            st.setString ( 1, dept.getName () );

            int rowsAffected = st.executeUpdate ();

            if (rowsAffected>1){
                ResultSet rs = st.getGeneratedKeys ();
                if (rs.next ()){
                    int getId = rs.getInt ( 1 );
                    dept.setId ( getId );
                }
                DB.closeResultSet (rs);

            }

        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement (st);
        }

    }

    @Override
    public void update(Department dept) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement ( "update department set Name = ? where id = ?");
            st.setString ( 1, dept.getName () );
            st.setInt ( 2, dept.getId () );
            int rowsAffected = st.executeUpdate ();
            System.out.println ("rows affected: " + rowsAffected);
        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement (st);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement ( "Delete from department where id = ?" );
            st.setInt ( 1,id );

            int rowsAff = st.executeUpdate ();
            if (rowsAff==0){
                throw new DbException ( "No rows affected" );
            }

        }catch (SQLException e){
            System.out.println (e.getMessage ());
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement ( "select department.* from department where department.Id = ?" );
            st.setInt ( 1, id );
            rs = st.executeQuery ();
            if (rs.next ()) {
                Department dep = new Department (  );
                dep.setId ( rs.getInt ( "Id" ) );
                dep.setName ( rs.getString ( "Name" ) );
                return dep;
            }

            return null;
        } catch (SQLException e) {
            throw new DbException ( e.getMessage () );
        }
        finally {
            DB.closeStatement ( st );
            DB.closeResultSet ( rs );
        }

    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement ( "select department.* FROM department order by Id" );
            rs = st.executeQuery ();
            List<Department> list = new ArrayList<> (  );
            while (rs.next ()){
                list.add ( new Department ( rs.getInt ( "Id" ), rs.getString ( "Name" ) ) );
            }
            return list;
        } catch (SQLException e) {
            throw new DbException (e.getMessage ());
        }
        finally {
            DB.closeStatement (st);
            DB.closeResultSet (rs);
        }

    }
}
