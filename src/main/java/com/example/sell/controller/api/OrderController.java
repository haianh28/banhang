package com.example.sell.controller.api;

import com.example.sell.constanst.RandomData;
import com.example.sell.data.model.*;
import com.example.sell.data.service.CustomerService;
import com.example.sell.data.service.OrderService;
import com.example.sell.model.dto.CategoryDTO;
import com.example.sell.model.dto.OrderDTO;
import com.example.sell.model.resutlData.BaseApiResult;
import com.example.sell.model.resutlData.DataApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {
    private static final Logger logger = LogManager.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
//    @Autowired
    //private

    //
    //@GetMapping("")
    // public ResponseEntity<?> getL
    @GetMapping("/fake")
    public BaseApiResult faBaseApiResult() {
        BaseApiResult result = new BaseApiResult();
        try {
            List<Order> orders = new ArrayList<>();
            int totalOrder = orderService.getTotalOrder(); // so luong oj
            Random random = new Random();
            RandomData randomData = new RandomData();
            List<Customer> customerList = customerService.getAllListCustomer();

            for (int i = totalOrder + 1; i < totalOrder + 10; i++) {
                Order order = new Order();
//                order.setIdOrder(i); ko can
                order.setIdOrder(new RandomData().randomText(4));
                order.setCustomerOrder(customerList.get(random.nextInt(customerList.size())));
                order.setCreateDate(new Date());
                order.setStatus(new RandomData().radomStatusOrder());
                order.setTotalMoney(random.nextGaussian()); // ti sua lai
                orders.add(order);
            }
            orderService.addNewListOrder(orders);
            result.setSuccess(true);
            result.setMessage("Face list order success");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    // lay  tat ca danh sach
    @GetMapping("")
    // list order mac dinh
    public ResponseEntity<?> getListOrder(){
        List<Order> orderList= orderService.getAllOrderList();
        return ResponseEntity.ok(orderList);
    }
    // list oder co page
    @GetMapping("/list")
    public Page<Order> PageOrder(@RequestParam(value = "page") int page){
        Pageable pageable = PageRequest.of(page,8);
        Page<Order> listPage= orderService.findAll(pageable);
        return listPage;
    }
    // lay theo id order
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id){
        Order order= orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    // lay theo status
    @GetMapping("/status")
    public Page<?> getSupplierByStatus(@RequestParam (value="page") int page , @RequestParam(value = "status", required = true) String status) {
        Pageable pageable = PageRequest.of(page,8);
        Page<Order> listPage = orderService.getListOrderByStatus(pageable,status);
        return listPage;
    }
    // ham tim kiem
//    @GetMapping("/serch")
//    public  BaseApiResult searchOrder(@RequestParam(value = "keyword") String keyWord,
//                                      @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
//                                      @RequestParam(value = "pageSize", required = false, defaultValue = "7") int pageSize){
//        DataApiResult result = new DataApiResult();
//        Sort sort = Sort.by("id").ascending();
//        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
//        try {
//            Page<Order> pageOrder = orderService.getOrderByIdOrName(pageable, keyWord);
//            if (pageOrder.isEmpty()){
//                result.setSuccess(false);
//                result.setMessage("Not Found.");
//            }else {
//                result.setSuccess(true);
//                result.setData(pageOrder);
//            }
//        }catch (Exception e){
//            result.setSuccess(false);
//            result.setMessage(e.getMessage());
//            logger.error(e.getMessage());
//        }
//        return result;
//    }
    // tim kiem theo id khach hang
    @GetMapping("/searchCustomer")
    public Page<Order> searchOrderByCustomer(@RequestParam( value = "page") int page,
                                   @RequestParam(name = "name") String name) {
        Pageable pageable =  PageRequest.of(page,8);
        Page<Order> listSearch = orderService.searchOrderPage(pageable,name);
        return listSearch ;
    }


    // tim kiem theo name customer
    // fontEnd loc list customer

    // thuc hien xoa doi tuong theo id
    @DeleteMapping("/delete/{id}")
    public BaseApiResult deleteOrder(@PathVariable String id) {
        BaseApiResult result = new BaseApiResult();
        if (orderService.deleteOrder(id)) {
            result.setSuccess(true);
            result.setMessage("Delete success");
        } else {
            result.setSuccess(false);
            result.setMessage("Delete fail");
        }
        return result;
    }
    // thêm đối tượng
   @PostMapping("/addOrder")
   public BaseApiResult addNewOrder(@RequestBody OrderDTO orderDTO) {
       BaseApiResult result = new BaseApiResult();

       try {
           Customer customer =customerService.findOne(orderDTO.getIdCustomer());
           Order order1=orderService.getOne(orderDTO.getIdOrder());

           if(order1==null){
               order1=new Order();
               order1.setIdOrder(orderDTO.getIdOrder());
               order1.setStatus("đang chờ");
               order1.setCustomerOrder(customer);
               order1.setCreateDate(orderDTO.getCreateDate());
               order1.setTotalMoney(orderDTO.getTotalMoney());
               orderService.addNewOrder(order1);
               result.setSuccess(true);
               result.setMessage("Success add new order !");
           }else {
               result.setSuccess(false);
               result.setMessage("Success add fail order !");
           }

       } catch (Exception e) {
           e.printStackTrace();
           result.setSuccess(false);
           result.setMessage("Fail to add new order!");
       }
       return result;

   }
    // edit theo id
    @PutMapping("/update/{id}")
    public BaseApiResult updateOrder(@PathVariable String id,@RequestBody Order order){
        BaseApiResult result=new BaseApiResult();
        Order odr= orderService.findOne(id);

        odr.setIdOrder(order.getIdOrder());
        odr.setIdCustomer(order.getIdCustomer());
        odr.setCreateDate(order.getCreateDate());
        odr.setCustomerOrder(order.getCustomerOrder());
        odr.setOrderDetails(order.getOrderDetails());
        odr.setStatus(order.getStatus());
        odr.setTotalMoney(order.getTotalMoney());

        try{
            orderService.addNewOrder(odr);
            result.setMessage("Update succes");
            result.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Update fail");
            result.setSuccess(false);
        }
        return result;

    }

    // update status
    @PutMapping("updateStatus/{id}")
        public BaseApiResult updateStatus(@PathVariable String id,@RequestBody Order order){
            BaseApiResult result=new BaseApiResult();
            Order order1=orderService.findOne(id);
            order1.setStatus(order.getStatus());
            order1.setCreateDate(order.getCreateDate());
        try{
            orderService.addNewOrder(order1);
            result.setMessage("Update succes");
            result.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Update fail");
            result.setSuccess(false);
        }
        return result;
        }

    // update money
    // dung de cam nhat tien trong database khi add,edit oder

    @PutMapping("/updateMoney/{idOrder}")
    public BaseApiResult updateMoney( @PathVariable String idOrder){

        BaseApiResult baseApiResult=new BaseApiResult();
        Order order=orderService.getOrderById(idOrder);
        order.setTotalMoney(orderService.getTotalMoney(idOrder));
        try{
            orderService.addNewOrder(order);
            baseApiResult.setMessage("Update success");
            baseApiResult.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            baseApiResult.setMessage("Update fail");
            baseApiResult.setSuccess(false);
        }
        return baseApiResult;

    }

    //
    //Thống kê
    @GetMapping("/thongKe")
    public List<Map> thongKe(@RequestParam(value = "year",defaultValue = "2020") int year){
        List list = new ArrayList();
        for (int i=1;i<=12;i++) {
            Map<String, Double> map = new HashMap<String, Double>();
            map.put("totalOrder", orderService.getAllOrder(i));
          //  map.put("totalProduct", orderService.getAllProduct(i));
            map.put("totalMoney", orderService.getAllMoney(i));
            list.add(map);
        }
        return list;
    }
// tim kiem



}