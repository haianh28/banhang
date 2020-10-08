package com.example.sell.model.dto;

import com.example.sell.data.model.Order;
import com.example.sell.data.model.OrderDetail;
import com.example.sell.data.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private int idOrderDetail;
    private String idOrder;
    private Order order;
    private String idProduct;
    private Product productOrderDetail;
    private int amount;
    private int totalPrice;

    public OrderDetailDTO converOrderDetail(OrderDetail orderDetail){
            OrderDetailDTO orderDetailDTO= new OrderDetailDTO();
            orderDetailDTO.setIdOrderDetail(orderDetail.getIdOrderDetail());
            orderDetailDTO.setIdOrder(orderDetail.getIdOrder());
            orderDetailDTO.setOrder(orderDetail.getOrder());
            orderDetailDTO.setAmount(orderDetail.getAmount());
            orderDetailDTO.setTotalPrice((int) (orderDetail.getAmount()*orderDetail.getProductOrderDetail().getPrice()));
            orderDetailDTO.setIdProduct(orderDetail.getIdProduct());
            orderDetailDTO.setProductOrderDetail(orderDetail.getProductOrderDetail());
            return orderDetailDTO;
    }

}
