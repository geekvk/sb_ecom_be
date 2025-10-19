package com.ecomemerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "adresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adress_id")
    public Long addressId;

    @NotBlank
    @Size(min = 5, max = 50, message = "Street name must be 5 characters")
    public String street;

    @NotBlank
    @Size
    @Size(min = 5, max = 10, message = "Building name must be 5 characters")
    public String buildingName;

    @NotBlank
    @Size(min = 5, max = 20, message = "City name must be 5 characters")
    public String city;

    @NotBlank
    @Size(min = 5, max = 10, message = "PIN code must be 5 characters")
    public String pinCode;

    @ManyToMany(mappedBy = "adresses")
    private List<User> users = new ArrayList<>();


}
