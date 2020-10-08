package com.example.sell.data.service;


import com.example.sell.data.model.BillImport;
import com.example.sell.data.repository.BillImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BillImportService {

    @Autowired
    private BillImportRepository billImportRepository;


    //Lấy tất cả bill
    public List<BillImport> getAllBillImport(){
        return billImportRepository.findAll();
    }

    //Lấy bill theo id
    public BillImport getBillImportById(String id){
        return billImportRepository.findById(id).orElse(null);
    }

    //Lấy bill theo id supplier
    public List<BillImport> getBillByIdSupplier(int id){
        return billImportRepository.getBillImportByIdSupplier(id);
    }

    //Thêm mới bill
    public Boolean addNewBillImport(BillImport billImport) {
        try {
            billImportRepository.save(billImport);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    //Lấy Bill Import theo page
    public Page<BillImport> getAllPage(Pageable pageable) {
        Page<BillImport> listBillImportPage =  billImportRepository.findAll(pageable);
        return listBillImportPage;
    }

    //Xóa Bill
    public Boolean deleteBillImportById(String id) {
        try {
            billImportRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //Tìm kiếm theo id
    public Page<BillImport> searchBillById(Pageable pageable, String id) {
        return billImportRepository.searchById(pageable,id);
    }

    //Tính tổng price trong bill
    public Double getTotalPrice(String idBillImport){
        return billImportRepository.totalPrice(idBillImport);
    }

    //Tính tổng amount trong bill
    public Integer getTotalAmount(String idBillImport){
        return billImportRepository.totalAmount(idBillImport);
    }

    //Total Bill
    public  Double getTotalBill(int month,int year){
        return billImportRepository.totalBillImport(month,year);
    }

    //Total product
    public Double getAllProduct(int month,int year){
        return billImportRepository.totalProduct(month,year);
    }

    //Total product
    public Double getAllMoney(int month,int year){
        return billImportRepository.totalMoney(month,year);
    }


}

