package com.congthanh.catalogservice.service.serviceImpl;

import com.congthanh.catalogservice.constant.common.ErrorCode;
import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import com.congthanh.catalogservice.cqrs.command.command.category.*;
import com.congthanh.catalogservice.cqrs.query.query.category.GetCategoryByIdQuery;
import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.model.entity.Category;
import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.model.request.AddSubcategoryRequest;
import com.congthanh.catalogservice.model.request.CreateCategoryRequest;
import com.congthanh.catalogservice.model.request.UpdateCategoryRequest;
import com.congthanh.catalogservice.model.response.PaginationInfo;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;
import com.congthanh.catalogservice.exception.ecommerce.NotFoundException;
import com.congthanh.catalogservice.repository.category.CategoryRepository;
import com.congthanh.catalogservice.service.CategoryService;
import com.congthanh.catalogservice.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final ModelMapper modelMapper;

    @Override
    public Object getAllCategory(Integer page, Integer limit) {
        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Category> result = categoryRepository.findAll(pageable);
            ResponseWithPagination<CategoryDTO> response = new ResponseWithPagination<>();
            List<CategoryDTO> list = new ArrayList<>();
            if (result.hasContent()) {
                for (Category category : result.getContent()) {
                    CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
                    list.add(categoryDTO);
                }
                PaginationInfo paginationInfo = PaginationInfo.builder()
                        .page(page)
                        .limit(limit)
                        .totalPage(result.getTotalPages())
                        .totalElement(result.getTotalElements())
                        .build();
                response.setResponseList(list);
                response.setPaginationInfo(paginationInfo);
            } else {
                throw new RuntimeException("List empty exception");
            }
            return response;
        } else {
            List<Category> list = categoryRepository.findAll();
            List<CategoryDTO> result = new ArrayList<>();
            for (Category item : list) {
                CategoryDTO categoryDTO = modelMapper.map(item, CategoryDTO.class);
                result.add(categoryDTO);
            }
            return result;
        }
    }

    @Override
    public CategoryDTO getCategoryById(String id) {
        GetCategoryByIdQuery query = new GetCategoryByIdQuery(id);
        CategoryDocument result = queryGateway.query(query, ResponseTypes.instanceOf(CategoryDocument.class)).join();

        return CategoryDTO.builder()
                .id(result.getId())
                .name(result.getName())
                .slug(result.getSlug())
                .description(result.getDescription())
                .image(result.getImage())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .status(result.getStatus())
                .build();
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        Optional<Category> existCategory = categoryRepository.findByName(request.getName());
        if (existCategory.isPresent()) {
            throw new RuntimeException("Category ton tai");
        }
        CreateCategoryCommand category = CreateCategoryCommand.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .status(CategoryStatus.ACTIVE)
                .description(request.getDescription())
                .slug(new Helper().generateSlug(request.getName()))
                .image(request.getImage())
                .parentId(null)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        var response = commandGateway.sendAndWait(category);
//        return (CategoryDTO) response;
        return null;
    }

    @Override
    public CategoryDTO updateCategory(UpdateCategoryRequest request, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(String.format(ErrorCode.CATEGORY_NOT_FOUND, categoryId)));
        UpdateCategoryCommand command = UpdateCategoryCommand.builder()
                .id(category.getId())
                .name(request.getName())
                .description(request.getDescription())
                .slug(request.getSlug())
                .image(request.getImage())
                .status(CategoryStatus.ACTIVE)
                .parentId(category.getParentId())
                .createdAt(category.getCreatedAt())
                .updatedAt(Instant.now())
                .build();
        var response = commandGateway.sendAndWait(command);
        return null;
    }

    @Override
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(ErrorCode.CATEGORY_NOT_FOUND, id)));
        if (category.getStatus().equals(CategoryStatus.INACTIVE)) {
            throw new RuntimeException("Category have deleted before");
        } else {
            DeleteCategoryCommand command = DeleteCategoryCommand.builder()
                    .categoryId(id)
                    .build();
            commandGateway.sendAndWait(command);
        }
    }

    @Override
    public void addSubcategory(AddSubcategoryRequest data, String parentId) {
        Category category = categoryRepository.findById(parentId).orElseThrow(() -> new NotFoundException("Category not found"));
        AddSubcategoryCommand command = AddSubcategoryCommand.builder()
                .id(UUID.randomUUID().toString())
                .name(data.getName())
                .status(CategoryStatus.ACTIVE)
                .description(data.getDescription())
                .slug(new Helper().generateSlug(data.getName()))
                .image(data.getImage())
                .parentId(parentId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        commandGateway.sendAndWait(command);
    }

    @Override
    public void removeSubcategory(String categoryId, String parentId) {
        RemoveSubcategoryCommand command = RemoveSubcategoryCommand.builder()
                .categoryId(categoryId)
                .parentId(parentId)
                .build();
        commandGateway.sendAndWait(command);
    }

}