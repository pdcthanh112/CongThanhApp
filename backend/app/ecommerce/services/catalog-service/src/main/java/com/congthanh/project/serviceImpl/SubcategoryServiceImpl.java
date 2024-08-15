package com.congthanh.project.serviceImpl;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.dto.SubcategoryDTO;
import com.congthanh.project.model.response.PaginationInfo;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.entity.Category;
import com.congthanh.project.entity.Subcategory;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.SubcategoryMapper;
import com.congthanh.project.repository.category.CategoryRepository;
import com.congthanh.project.repository.subcategory.SubcategoryRepository;
import com.congthanh.project.service.SubcategoryService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Object getAllSubcategory(Integer page, Integer limit) {
        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Subcategory> result = subcategoryRepository.findAll(pageable);
            ResponseWithPagination<SubcategoryDTO> response = new ResponseWithPagination<>();
            List<SubcategoryDTO> list = new ArrayList<>();
            if (result.hasContent()) {
                for (Subcategory subcategory : result.getContent()) {
                    SubcategoryDTO subcategoryDTO = SubcategoryMapper.mapSubcategoryEntityToDTO(subcategory);
                    list.add(subcategoryDTO);
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
            List<Subcategory> list = subcategoryRepository.findAll();
            List<SubcategoryDTO> result = new ArrayList<>();
            for (Subcategory subcategory : list) {
                SubcategoryDTO subcategoryDTO = SubcategoryMapper.mapSubcategoryEntityToDTO(subcategory);
                result.add(subcategoryDTO);
            }
            return result;
        }
    }

    @Override
    public SubcategoryDTO getSubcategoryById(int id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
        SubcategoryDTO result = SubcategoryMapper.mapSubcategoryEntityToDTO(subcategory);
        return result;
    }

    @Override
    public Subcategory createSubcategory(String name, int categoryId) {
        boolean existSubcategory = subcategoryRepository.checkExistSubcategory(name, categoryId);
        if (existSubcategory) {
            throw new RuntimeException("Sub ton tai");
        }
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("Category khong ton tai");
        } else {
            Subcategory subcategory = Subcategory.builder()
                    .name(name)
                    .category(category.get())
                    .status(StateStatus.STATUS_ACTIVE)
                    .build();
            Subcategory response = subcategoryRepository.save(subcategory);
            return response;
        }
    }

    @Override
    public Subcategory updateSubcategory(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryDTO.getId()).orElseThrow(() -> new RuntimeException("Subcategory not found"));

        subcategory.setName(subcategoryDTO.getName());
        //subcategory.setCategory(subcategoryDTO.getCategory());

        subcategoryRepository.save(subcategory);
        return subcategory;
    }

    @Override
    public boolean deleteSubcategory(int id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found"));
        if (subcategory.getStatus().equalsIgnoreCase(StateStatus.STATUS_DELETED)) {
            throw new RuntimeException("Category have deleted before");
        } else {
            boolean result = subcategoryRepository.deleteSubcategory(id);
            return result;
        }
    }

    @Override
    public List<SubcategoryDTO> getSubcategoryByCategoryId(int id) {
        List<Tuple> data = subcategoryRepository.findByCategoryId(id);
        List<SubcategoryDTO> result = new ArrayList<>();
        for (Tuple item : data) {
            SubcategoryDTO subcategoryDTO = SubcategoryDTO.builder()
                    .id(item.get("id", Long.class))
                    .name(item.get("name", String.class))
                    .build();
            result.add(subcategoryDTO);
        }
        return result;
    }
}