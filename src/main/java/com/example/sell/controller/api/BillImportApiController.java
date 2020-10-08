package com.example.sell.controller.api;


import com.example.sell.data.model.BillImport;

import com.example.sell.data.service.BillImportService;
import com.example.sell.model.dto.BillImportDTO;
import com.example.sell.model.dto.BillImportDetailDTO;
import com.example.sell.model.resutlData.BaseApiResult;
import com.example.sell.model.resutlData.DataApiResult;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/billImport")
@CrossOrigin(origins = "*")
public class BillImportApiController {

    private static final Logger logger = LogManager.getLogger(CommentApiController.class);

    @Autowired
    private BillImportService billImportService;

    //Lấy tất cả bill
    @GetMapping("/all")
    public List<BillImport> getAllBillImport(){
        return billImportService.getAllBillImport();
    }


    //Lấy bill theo page
    @GetMapping("/billPage")
    public BaseApiResult getListBill(@RequestParam(value = "page") int page){
        DataApiResult result= new DataApiResult();
        List<BillImport> listBill =billImportService.getAllBillImport();
        try {
            Pageable pageable = PageRequest.of(page,5);
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize())>listBill.size() ? listBill.size() : (start + pageable.getPageSize());
            Page<BillImport> listBillPage1=new PageImpl<>(listBill.subList(start,end),pageable,listBill.size());
            result.setData(listBillPage1);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            logger.error(e.getMessage());
        }
        return result;
    }

    //Lấy bill theo id
    @GetMapping("/{id}")
    public BillImport getBillById(@PathVariable String id){
        BillImport billImport = new BillImport();
        billImport = billImportService.getBillImportById(id);
        return  billImport;
    }

    //Lấy bill theo idSupplier

    @GetMapping("/searchByIdSupp")
    public BaseApiResult getListBillByIdSupplier(@RequestParam(value = "page") int page,
                                                 @RequestParam(value = "idSupplier") int idSupplier){
        DataApiResult result= new DataApiResult();
        List<BillImport> listBill =billImportService.getBillByIdSupplier(idSupplier);
        try {

            Pageable pageable = PageRequest.of(page,5);
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize())>listBill.size() ? listBill.size() : (start + pageable.getPageSize());
            Page<BillImport> listBillPage1=new PageImpl<>(listBill.subList(start,end),pageable,listBill.size());
            result.setData(listBillPage1);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            logger.error(e.getMessage());
        }
        return result;
    }

    //Thêm mới bill
    @PostMapping("/addBillImport")
    public BaseApiResult addNewBill(@RequestBody BillImportDTO billImportDTO){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(billImportDTO.getIdBillImport());
        if(billImport==null) {
            try {
                billImport = new BillImport();
                billImport.setIdBillImport(billImportDTO.getIdBillImport());
                billImport.setCreateDate(billImportDTO.getCreateDate());
                billImport.setSupplierImport(billImportDTO.getSupplier());
                billImport.setTotalMoney(billImportDTO.getTotalMoney());
                billImport.setTotalProduct(billImportDTO.getTotalProduct());
                billImportService.addNewBillImport(billImport);
                baseApiResult.setSuccess(true);
                baseApiResult.setMessage("Success add new Bill Import !");
            } catch (Exception e) {
                e.printStackTrace();
                baseApiResult.setSuccess(false);
                baseApiResult.setMessage("Fail to add new Bill Import!");
            }
        } else{
            baseApiResult.setSuccess(false);
            baseApiResult.setMessage("Bill Import đã tồn tại");
        }
        return baseApiResult;
    }

    //Sửa bill
    @PutMapping("/update/{id}")
    public BaseApiResult editBill(@RequestBody BillImportDTO billImportDTO,
                                  @PathVariable String id){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(id);

        billImport.setCreateDate(billImportDTO.getCreateDate());

        try{
            billImportService.addNewBillImport(billImport);
            baseApiResult.setMessage("Update success");
            baseApiResult.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            baseApiResult.setMessage("Update fail");
            baseApiResult.setSuccess(false);

        }
        return  baseApiResult;

    }


    //Xóa bill
    @DeleteMapping("delete/{id}")
    public BaseApiResult deleteBill(@PathVariable String id){
        BaseApiResult baseApiResult = new BaseApiResult();
        if(billImportService.deleteBillImportById(id)){
            baseApiResult.setMessage("Delete success");
            baseApiResult.setSuccess(true);
        } else {
            baseApiResult.setMessage("Delete fail");
            baseApiResult.setSuccess(false);
        }
        return  baseApiResult;
    }

    //Search bill
    @GetMapping("/search")
    public Page<BillImport> searchBillImport(@RequestParam( value = "page") int page,
                                         @RequestParam(value = "keyWord") String keyWord){

        Pageable pageable =  PageRequest.of(page,5);
        Page<BillImport> listBill = billImportService.searchBillById(pageable,keyWord);
        return listBill  ;
    }

    /*//Cập nhật giá tổng tiền & tổng sp
    @PutMapping("/updateMoney/{id}")
    public  BaseApiResult updateTotalMoney(@RequestBody BillImportDetailDTO billImportDetailDTO,
                                           @PathVariable String id){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(id);
        billImport.setTotalProduct(billImport.getTotalProduct()+billImportDetailDTO.getAmount());
        billImport.setTotalMoney(billImport.getTotalMoney()+billImportDetailDTO.getTotalPrice());
        try{
            billImportService.addNewBillImport(billImport);
            baseApiResult.setMessage("Update success");
            baseApiResult.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            baseApiResult.setMessage("Update fail");
            baseApiResult.setSuccess(false);
        }
        return baseApiResult;
    }
*/
    @PutMapping("/updateMoney/{id}")
    public  BaseApiResult updateBillImport(@RequestBody BillImportDetailDTO billImportDetailDTO,
                                            @PathVariable String id){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(id);
        billImport.setTotalProduct(billImportService.getTotalAmount(id));
        billImport.setTotalMoney(billImportService.getTotalPrice(id));
        try{
            billImportService.addNewBillImport(billImport);
            baseApiResult.setMessage("Update success");
            baseApiResult.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            baseApiResult.setMessage("Update fail");
            baseApiResult.setSuccess(false);
        }
        return baseApiResult;
    }

    //Thống kê
    @GetMapping("/thongKe")
    public List<Map> thongKe(@RequestParam(value = "year",defaultValue = "2020") int year
                            ){
        List list = new ArrayList();
        for (int i=1;i<=12;i++) {
            Map<String,Double> map = new HashMap<String,Double>();
            map.put("totalBill", billImportService.getTotalBill(i,year));
            map.put("totalProduct", billImportService.getAllProduct(i,year));
            map.put("totalMoney", billImportService.getAllMoney(i,year));
            list.add(map);
        }
         return list;
    }

}
