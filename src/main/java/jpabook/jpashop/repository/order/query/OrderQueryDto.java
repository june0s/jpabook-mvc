package jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDto(OrderFlatDto o) {
        orderId = o.getOrderId();
        name = o.getName();
        orderDate = o.getOrderDate();
        orderStatus = o.getOrderStatus();
        address = o.getAddress();
    }

    public OrderQueryDto(Map.Entry<OrderQueryDto, List<OrderItemQueryDto>> e) {
        orderId = e.getKey().getOrderId();
        name = e.getKey().getName();
        orderDate = e.getKey().getOrderDate();
        orderStatus = e.getKey().getOrderStatus();
        address = e.getKey().getAddress();
        orderItems = e.getValue();
    }
}
