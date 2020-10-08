package com.example.sell.data.repository;

import com.example.sell.data.model.BillImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillImportRepository extends JpaRepository<BillImport,String> {
    @Query("select bill from dbo_bill_import bill " +
            "where bill.idBillImport=:keyWord ")
    Page<BillImport> searchById(Pageable pageable, @Param("keyWord") String keyWord);

    @Query("select sum(detail.totalPrice) from dbo_bill_import_detail detail " +
            "where detail.idBillImport=:idBillImport")
    Double totalPrice(@Param("idBillImport") String idBillImport);

    @Query("select sum(detail.amount) from dbo_bill_import_detail detail " +
            "where detail.idBillImport=:idBillImport")
    Integer totalAmount(@Param("idBillImport") String idBillImport);

    List<BillImport> getBillImportByIdSupplier(@Param("idSupplier") int idSupplier);
//
//
    @Query("SELECT count(bill.idBillImport) FROM dbo_bill_import bill where month(bill.createDate) =:month and year(bill.createDate)=:year")
    Double totalBillImport(@Param("month") int month,@Param("year") int year);

    @Query("SELECT sum(bill.totalProduct) FROM dbo_bill_import bill where month(bill.createDate) =:month and year(bill.createDate)=:year")
    Double totalProduct(@Param("month") int month,@Param("year") int year);

    @Query("SELECT sum(bill.totalMoney) FROM dbo_bill_import bill where month(bill.createDate) =:month and year(bill.createDate)=:year")
    Double totalMoney(@Param("month") int month,@Param("year") int year);




}
