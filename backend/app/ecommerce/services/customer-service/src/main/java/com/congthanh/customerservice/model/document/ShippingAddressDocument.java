package com.congthanh.customerservice.model.document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "shipping_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressDocument {

    @Id
    private String id;

    @NotNull
    private String customer;

    private List<AddressDetailDocument> address;

}
