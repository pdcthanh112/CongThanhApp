package com.congthanh.catalogservice.model.request;

public record RequestFilter(int page, int size, String sort, String order, String filter) {
}
