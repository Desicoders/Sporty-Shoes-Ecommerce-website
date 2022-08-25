package com.sporty.shoes.enteties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
	int id;
    String name;
    Double mrp;
    int Size;
    String category;
    String colour; 
    
    //Gender gender;
    
    @Pattern(regexp = "^(?:male|female)$",message = "gender can be male or female for shoes")
    String gender;
    
    @PositiveOrZero(message = "quantity can be +ve or 0")
    int quantityInStock;
    String productVendor;
    
    
}
