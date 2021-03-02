package com.orderprocessing.orders.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.ResponseDTO;
import com.orderprocessing.orders.entities.Customer;
import com.orderprocessing.orders.entities.Item;
import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.entities.OrderLineItems;
import com.orderprocessing.orders.entities.PaymentMethod;
import com.orderprocessing.orders.entities.Transaction;
import com.orderprocessing.orders.repository.CustomerRepository;
import com.orderprocessing.orders.repository.ItemRepository;
import com.orderprocessing.orders.repository.OrderRepository;
import com.orderprocessing.orders.repository.PaymentMethodRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private CustomerRepository customerRepo;

	private  static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Override
	public Order findOrderById(String theId) {
		// TODO Auto-generated method stub
		Optional<Order> result = orderRepository.findById(theId);

		Order order = null;
		if(result.isPresent()) {
			order = result.get();
		}else {
			throw new RuntimeException("Did not find order - "+theId);
		}

		return order;
	}

	@Override
	public ResponseDTO saveOrder(RequestDTO theOrder) {
		//check if order amount is correct
		ResponseDTO result = new ResponseDTO();
		double orderTotal = 0;
		for(int j=0; j<theOrder.getOrderLineItems().size(); j++) {
			OrderLineItems o = theOrder.getOrderLineItems().get(j);
			Optional<Item> io = itemRepository.findById(o.getOrderItemId());
			Item i = null;
			if(io.isPresent()) {
				i = io.get();
			}else {
				result.setOrder(null);
				result.setMessage("Did not find item - " + o.getOrderItemId());
				return result;
			}
			orderTotal = orderTotal + i.getItemPrice()*o.getQuantity();
		}
		if(theOrder.getOrderSubTotal() != orderTotal) {
			result.setOrder(null);
			result.setMessage("Did not match order sub total");
			return result;
		}
		// add tax
		orderTotal = orderTotal + orderTotal*0.0625;
		if(theOrder.getOrderTotalAmount() != orderTotal) {
			result.setOrder(null);
			result.setMessage("Did not match order total");
			return result;
		}

		// TODO Auto-generated method stub
		Customer cust = new Customer();
		cust.setFirstName(theOrder.getCustomer().getFirstName());
		cust.setLastName(theOrder.getCustomer().getLastName());
		cust.setMiddleName(theOrder.getCustomer().getMiddleName());
		cust.setEmail(theOrder.getCustomer().getEmail());

		List<PaymentMethod> payments = theOrder.getPaymentMethod();


		Order order = new Order();
		order.setCustomer(cust);
		order.setShippingAddressLine1(theOrder.getShipping_address_line1());
		order.setShippingAddressLine2(theOrder.getShipping_address_line2());
		order.setOrderShippingMethod(theOrder.getShippingMethod());
		order.setShippingCity(theOrder.getShipping_city());
		order.setShippingState(theOrder.getShipping_state());
		order.setShipping_zipcode(theOrder.getShipping_zipcode());
		order.setOrderSubTotal(theOrder.getOrderSubTotal());
		order.setOrderTotalAmount(theOrder.getOrderTotalAmount());
		order.setOrderStatus("Confirmed");
		order.setOrderTax(theOrder.getTax());
		Date orderedDate = new Date();
		order.setCreatedOn(orderedDate);
		order.setUpdatedOn(orderedDate);
		customerRepo.save(cust);

		for(int j=0; j<theOrder.getOrderLineItems().size(); j++) {
			OrderLineItems o = theOrder.getOrderLineItems().get(j);
			Optional<Item> io = itemRepository.findById(o.getOrderItemId());
			Item i = null;
			if(io.isPresent()) {
				i = io.get();
				OrderLineItems orderLineItems = new OrderLineItems();
				orderLineItems.setItem(i);
				orderLineItems.setQuantity(o.getQuantity());
				orderLineItems.setOrder(order);
				order.addOrderLineItems(orderLineItems);

			} 
		}


		for(int i1=0; i1<payments.size(); i1++) {
			PaymentMethod pIn = payments.get(i1);
			Optional<PaymentMethod> po = paymentMethodRepository.findById(payments.get(i1).getPaymentMethodId());
			if(!po.isPresent()) {
				PaymentMethod pwrite = new PaymentMethod();
				pwrite.setPaymentMethod(pIn.getPaymentMethod());
				logger.info("Payment_method:"+pIn.getPaymentMethod());
				pwrite.setPaymentMethodId(pIn.getPaymentMethodId());
				pwrite.setBillingAddressLine1(pIn.getBillingAddressLine1());
				pwrite.setBillingAddressLine2(pIn.getBillingAddressLine2());
				pwrite.setBillingCity(pIn.getBillingCity());
				pwrite.setBillingState(pIn.getBillingState());
				pwrite.setBillingZipcode(pIn.getBillingZipcode());
			}
			Transaction t = new Transaction();
			t.setPaymentMethod(pIn);
			t.setTransactionAmount(theOrder.getPaymentAmount().get(i1));
			t.setOrder(order);
			t.setTransactionStatus("complete");
			order.addTransactions(t);
		}
		orderRepository.save(order);

		result.setOrder(order);
		result.setMessage("success");

		return result;

	}


	@Override
	public String deleteById(String theId) {
		// TODO Auto-generated method stub
		Optional<Order> result = orderRepository.findById(theId);
		
		Order order = null;
		if(result.isPresent()) {
			order = result.get();
		}else {
			throw new RuntimeException("Did not find order - "+theId);
		}
		
		order.setOrderStatus("Canceled");
		orderRepository.save(order);
		
		return "Success";
	}

}
