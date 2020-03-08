package com.gdg.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String customerName;
    private String orderedProductName;
    private long orderedProductAmount;
    private Boolean sent;
}
