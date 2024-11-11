package com.congthanh.project.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankDetails {
    private String bankName;
    private String accountNumber;
    private String accountHolderName;
    private String branchCode;
    private String routingNumber;
}
