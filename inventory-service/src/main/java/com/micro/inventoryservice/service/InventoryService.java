package com.micro.inventoryservice.service;
import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.micro.inventoryservice.dto.InventoryResponse;
import com.micro.inventoryservice.repository.InventoryRepository;
import java.util.List;

@Service
public class InventoryService {
	@Autowired
    private final InventoryRepository inventoryRepository;
	public InventoryService (InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}
    @Transactional(readOnly = true)
    
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
