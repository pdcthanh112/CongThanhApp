package com.congthanh.catalogservice.grpc;

import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.model.entity.Category;
import com.congthanh.catalogservice.repository.category.CategoryRepository;
import com.congthanh.catalogservice.service.CategoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class CategoryGrpcService extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    @Override
    public void getCategoryById(CategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try {
            CategoryDTO category = categoryService.getCategoryById(request.getCategoryId());
            CategoryResponse response = CategoryResponse.newBuilder()
                    .setId(category.getId())
                    .setName(category.getName())
                    .setSlug(category.getSlug())
                    .setDescription(category.getDescription())
                    .setImage(category.getImage())
                    .setStatus(category.getStatus().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCategoryBySlug(CategorySlugRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try {
            CategoryDTO category = categoryService.getCategoryById(request.getSlug());
            CategoryResponse response = CategoryResponse.newBuilder()
                    .setId(category.getId())
                    .setName(category.getName())
                    .setSlug(category.getSlug())
                    .setDescription(category.getDescription())
                    .setImage(category.getImage())
                    .setStatus(category.getStatus().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void validateCategories(ValidateCategoriesRequest request, StreamObserver<ValidateCategoriesResponse> responseObserver) {
        try {
            List<String> categoryIds = request.getCategoryIdsList();
            List<String> existingCategories = categoryRepository.findByIdIn(categoryIds)
                    .stream()
                    .map(Category::getId)
                    .toList();

            List<String> invalidCategories = categoryIds.stream()
                    .filter(id -> !existingCategories.contains(id))
                    .toList();

            ValidateCategoriesResponse response = ValidateCategoriesResponse.newBuilder()
                    .setValid(invalidCategories.isEmpty())
                    .addAllInvalidCategoryIds(invalidCategories)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getListCategoryByIds(ListCategoryRequest request, StreamObserver<ListCategoryResponse> responseObserver) {
        try {
            List<String> categoryIds = request.getCategoryIdList();

            List<Category> categories = categoryRepository.findAllById(categoryIds);

            List<CategoryResponse> categoryResponses = categories.stream()
                    .map(cat -> CategoryResponse.newBuilder()
                            .setId(cat.getId())
                            .setName(cat.getName())
                            .setSlug(cat.getSlug())
                            .setDescription(cat.getDescription())
                            .setImage(cat.getImage())
                            .setParentId(cat.getParentId() != null ? cat.getParentId() : "")
                            .setStatus(cat.getStatus().toString())
                            .build())
                    .collect(Collectors.toList());

            ListCategoryResponse response = ListCategoryResponse.newBuilder()
                    .addAllResponse(categoryResponses)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

}
