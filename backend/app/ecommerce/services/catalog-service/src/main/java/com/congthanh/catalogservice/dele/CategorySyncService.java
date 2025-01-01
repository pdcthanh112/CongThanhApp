package com.congthanh.catalogservice.dele;

import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.model.document.SubcategoryDocument;
import com.congthanh.catalogservice.model.entity.Category;
import com.congthanh.catalogservice.repository.category.CategoryDocumentRepository;
import com.congthanh.catalogservice.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategorySyncService {
    @Autowired
    private CategoryRepository categoryRepository; // PostgreSQL repository

    @Autowired
    private CategoryDocumentRepository categoryDocumentRepository; // MongoDB repository

    public void syncCategories() {
        // Lấy tất cả categories từ PostgreSQL
        List<Category> allCategories = categoryRepository.findAll();

        // Tạo map để lưu trữ tạm thời quan hệ parent-child
        Map<String, List<Category>> parentChildMap = new HashMap<>();

        // Lọc ra các category cha (parent_id = null)
        List<Category> parentCategories = allCategories.stream()
                .filter(category -> category.getParentId() == null)
                .collect(Collectors.toList());

        // Tạo map chứa các category con theo parent_id
        allCategories.stream()
                .filter(category -> category.getParentId() != null)
                .forEach(category -> {
                    parentChildMap.computeIfAbsent(category.getParentId(), k -> new ArrayList<>())
                            .add(category);
                });

        // Chuyển đổi và lưu các category cha vào MongoDB
        List<CategoryDocument> categoryDocuments = parentCategories.stream()
                .map(category -> convertToDocument(category, parentChildMap))
                .collect(Collectors.toList());

        // Lưu vào MongoDB
        categoryDocumentRepository.deleteAll(); // Xóa dữ liệu cũ
        categoryDocumentRepository.saveAll(categoryDocuments);
    }

    private CategoryDocument convertToDocument(Category category, Map<String, List<Category>> parentChildMap) {
        CategoryDocument document = new CategoryDocument();
        document.setId(category.getId());
        document.setName(category.getName());
        document.setSlug(category.getSlug());
        document.setDescription(category.getDescription());
        document.setImage(category.getImage());
        document.setStatus(category.getStatus());
        document.setCreatedAt(category.getCreatedAt());
        document.setCreatedBy(category.getCreatedBy());
        document.setUpdatedAt(category.getUpdatedAt());
        document.setUpdatedBy(category.getUpdatedBy());

        // Đệ quy chuyển đổi các category con
        Set<SubcategoryDocument> children = convertChildren(category.getId(), parentChildMap);
        document.setChildren(children);

        return document;
    }

    private Set<SubcategoryDocument> convertChildren(String parentId, Map<String, List<Category>> parentChildMap) {
        List<Category> children = parentChildMap.getOrDefault(parentId, Collections.emptyList());

        return children.stream()
                .map(child -> {
                    SubcategoryDocument subDocument = new SubcategoryDocument();
                    subDocument.setId(child.getId());
                    subDocument.setName(child.getName());
                    subDocument.setSlug(child.getSlug());
                    subDocument.setDescription(child.getDescription());
                    subDocument.setImage(child.getImage());
                    subDocument.setStatus(child.getStatus());
                    subDocument.setCreatedAt(child.getCreatedAt());
                    subDocument.setCreatedBy(child.getCreatedBy());
                    subDocument.setUpdatedAt(child.getUpdatedAt());
                    subDocument.setUpdatedBy(child.getUpdatedBy());

                    // Đệ quy chuyển đổi các category con của con
                    Set<SubcategoryDocument> grandChildren = convertChildren(child.getId(), parentChildMap);
                    subDocument.setChildren(grandChildren);

                    return subDocument;
                })
                .collect(Collectors.toSet());
    }
}
