package com.carrent.service;


import com.carrent.dao.InvoiceDao;
import com.carrent.dao.OrderDao;
import com.carrent.dao.UserDao;
import com.carrent.domain.Invoice;
import com.carrent.domain.Order;
import com.carrent.domain.User;
import com.carrent.exception.OrderNotFoundException;
import com.carrent.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    public Invoice saveInvoice (final Invoice invoice) throws UserNotFoundException, OrderNotFoundException {
        Invoice newInvoice = invoiceDao.save(invoice);
        String orderNumber = newInvoice.getOrder().getOrderNumber();
        newInvoice.setInvoiceNumber(changeOrderToInvoice(orderNumber));
        addInvoiceToUserList(newInvoice);
        changeOrderStatus(invoice.getOrder().getId());
        return invoiceDao.save(newInvoice);
    }

    public List<Invoice> getInvoiceList() {
        return invoiceDao.findAll();
    }

    public Optional<Invoice> getInvoice (Long id) {
        return invoiceDao.findById(id);
    }

    public void deleteInvoice (Long id) {
        invoiceDao.deleteById(id);
    }

    private String changeOrderToInvoice (String orderNumber) {
        return orderNumber.replace("Order", "Invoice");
    }

    private void changeOrderStatus (final Long orderId) throws OrderNotFoundException {
        Order order = orderDao.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.setStatusOrder(true);
        orderDao.save(order);
    }

    private void addInvoiceToUserList (final Invoice invoice) throws UserNotFoundException {
        User user = userDao.findById(invoice.getUser().getId()).orElseThrow(UserNotFoundException::new);
        user.getInvoiceList().add(invoice);
        userDao.save(user);
    }
}