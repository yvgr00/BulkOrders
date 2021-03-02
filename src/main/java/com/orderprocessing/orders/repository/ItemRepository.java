package com.orderprocessing.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderprocessing.orders.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
