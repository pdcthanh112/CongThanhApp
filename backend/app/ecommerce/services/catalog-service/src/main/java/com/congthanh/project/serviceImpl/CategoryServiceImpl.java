package com.congthanh.project.serviceImpl;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.cqrs.command.command.category.CreateCategoryCommand;
import com.congthanh.project.dto.CategoryDTO;
import com.congthanh.project.entity.Category;
import com.congthanh.project.model.request.CreateCategoryRequest;
import com.congthanh.project.model.response.PaginationInfo;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.CategoryMapper;
import com.congthanh.project.repository.category.CategoryRepository;
import com.congthanh.project.service.CategoryService;
import com.congthanh.project.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public CategoryDTO getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(("not found")));
        return CategoryMapper.mapCategoryEntityToDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        Optional<Category> existCategory = categoryRepository.findByName(request.getName());
        if (existCategory.isPresent()) {
            throw new RuntimeException("Category ton tai");
        } else {
            CreateCategoryCommand category = CreateCategoryCommand.builder()
                    .name(request.getName())
                    .status(StateStatus.STATUS_ACTIVE)
                    .slug(new Helper().generateSlug(request.getName()))
                    .image(null)
                    .build();
            CategoryDTO response = commandGateway.sendAndWait(category);
            return response;
        }
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDTO.getName());
        category.setImage(category.getImage());

        Category result = categoryRepository.save(category);
        return CategoryMapper.mapCategoryEntityToDTO(result);
    }

    @Override
    public boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.getStatus().equalsIgnoreCase(StateStatus.STATUS_DELETED)) {
            throw new RuntimeException("Category have deleted before");
        } else {
            boolean result = categoryRepository.deleteCategory(id);
            return result;
        }
    }

}