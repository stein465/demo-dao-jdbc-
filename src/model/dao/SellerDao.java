package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller seller);
    void update(Seller dept);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findByDepartment (Department dept);
    List<Seller> findAll();
}
