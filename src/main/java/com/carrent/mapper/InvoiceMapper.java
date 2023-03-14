package com.carrent.mapper;


import com.carrent.dao.OrderDao;
import com.carrent.dao.UserDao;
import com.carrent.domain.Invoice;
import com.carrent.dto.CreateInvoiceDto;
import com.carrent.dto.GetInvoiceDto;
import com.carrent.dto.InvoiceDto;
import com.carrent.exception.OrderNotFoundException;
import com.carrent.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    public Invoice mapToInvoice (final CreateInvoiceDto createInvoiceDto) throws UserNotFoundException, OrderNotFoundException {
        return new Invoice(
                userDao.findById(createInvoiceDto.getUserId()).orElseThrow(UserNotFoundException::new),
                orderDao.findById(createInvoiceDto.getOrderId()).orElseThrow(OrderNotFoundException::new)
        );
    }

    public List<GetInvoiceDto> getInvoiceDtoList (final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .map(i -> new GetInvoiceDto(
                        i.getId(),
                        i.getInvoiceNumber()))
                .collect(Collectors.toList());
    }

    public InvoiceDto mapToInvoiceDto (final Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getUser().getId(),
                invoice.getOrder().getId());
    }
}
