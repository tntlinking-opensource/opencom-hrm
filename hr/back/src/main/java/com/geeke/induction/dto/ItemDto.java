package com.geeke.induction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yi.luo
 * @date 2023-04-26 14:08
 */
@AllArgsConstructor
@Data
public class ItemDto {
    private String id;
    private String label;
    private String value;
}
