package com.congthanh.searchservice.constant.common;

public class ProductField {

    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String PRICE = "price";
    public static final String IS_PUBLISHED = "isPublished";
    public static final String CATEGORIES = "categories";
    public static final String ATTRIBUTES = "attributes";
    public static final String CREATE_ON = "createdOn";

    private ProductField() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
