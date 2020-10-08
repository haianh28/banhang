package com.example.sell.data.repository;

import com.example.sell.data.model.Supplier;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {


    @Query("select sup from dbo_supplier sup " +
            "where sup.status=:status")

    List<Supplier> getListSupplierByStatus(@Param("status") boolean status) ;

    @Query("select s from dbo_supplier s " +
            "where (upper(s.name) like concat('%',upper(:name),'%') ) ")
    List<Supplier> getSuppByName(@Param("name") String name);

}
