package com.congthanh.authservice.model.request;

import java.util.List;

public record TokenPayload(String accountId, List<String> role) {
}
