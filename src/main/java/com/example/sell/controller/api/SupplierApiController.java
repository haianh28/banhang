package com.example.sell.controller.api;


import com.example.sell.data.model.Supplier;
import com.example.sell.data.service.SupplierService;
import com.example.sell.model.resutlData.BaseApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/supplier")
public class SupplierApiController {

    @Autowired
    private SupplierService supplierService;

    //Lấy tất cả supplier
    @GetMapping("")
    public List<Supplier> getListSupplier()  {
        return supplierService.getAllListSuppliers();
    }

    //Lấy supplier theo page
    @GetMapping(value = "/list")
    public Page<Supplier> listSupplierPage(@RequestParam( value = "page") int page) {
        Pageable pageable =  PageRequest.of(page,5);
        Page<Supplier> listSupplierPage = supplierService.findAll(pageable);
        return listSupplierPage;
    }

    //Lấy supplier theo id
    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable int id) {
        return supplierService.getSupplierById(id);
    }


    //Tìm kiếm theo tên
    @GetMapping("/search")
    public List<Supplier> searchSupplier(@RequestParam(name = "name") String name){
        List<Supplier> listSearch = supplierService.searchSupplier(name);
        return listSearch ;
    }

    //Tìm kiếm theo status
    @GetMapping("/status")
    public List<Supplier> getSupplierByStatus(
            @RequestParam(value = "status") Boolean status) {

        List<Supplier> listSupplier = supplierService.getListSupplierByStatus(status);
        return listSupplier;
    }


    //Xóa supplier theo id
    @DeleteMapping("/delete")
    public BaseApiResult delete(@RequestParam(value = "id", required = true) int id) {
        BaseApiResult result = new BaseApiResult();
        if (supplierService.deleteSupplierById(id)) {
            result.setSuccess(true);
            result.setMessage("Delete success");
        } else {
            result.setSuccess(false);
            result.setMessage("Delete fail");
        }
        return result;
    }

    //Thêm mới supplier
    @PostMapping("/addSupplier")
    public BaseApiResult addNewSupplier(@RequestBody Supplier supplier) {
        BaseApiResult result = new BaseApiResult();
        Supplier supplier1 = supplierService.findOne(supplier.getIdSupplier());
        if (supplier1==null){
        try {
            supplierService.addNewSupplier(supplier);
            result.setSuccess(true);
            result.setMessage("Success add new supplier !");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Fail to add new supplier!");
        }
        }else{
            result.setSuccess(false);
            result.setMessage("Supplier đã tồn tại");
        }
        return result;

    }


    //Cập nhật supplier
    @PutMapping("/update/{id}")
    public BaseApiResult updateSupplier(@PathVariable int id, @RequestBody Supplier supplier) {
        BaseApiResult result = new BaseApiResult();
        Supplier sup = supplierService.findOne(id);
        sup.setAddress(supplier.getAddress());
        sup.setLogo(supplier.getLogo());
        sup.setName(supplier.getName());
        sup.setPhoneNumber(supplier.getPhoneNumber());
        sup.setStatus(supplier.getStatus());
        try {
            supplierService.addNewSupplier(sup);
            result.setMessage("Update success");
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Update fail");
            result.setSuccess(false);
        }
        return result;
    }



}









