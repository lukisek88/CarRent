package com.carrent.dao;

import com.carrent.domain.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDao extends CrudRepository<Invoice, Long> {

    @Override
    List<Invoice> findAll();
}