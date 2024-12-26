package com.example.carmanagementbackend.entity.dto.garage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGarageDTO {
    private Long id;
    private String name;
    private String location;
    private String city;
    private int capacity;
}
