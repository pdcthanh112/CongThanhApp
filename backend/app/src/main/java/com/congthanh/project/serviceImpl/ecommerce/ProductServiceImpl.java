package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.ecommerce.Category;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.Store;
import com.congthanh.project.entity.ecommerce.Subcategory;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.ProductMapper;
import com.congthanh.project.repository.ecommerce.category.CategoryRepository;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.repository.ecommerce.store.StoreRepository;
import com.congthanh.project.repository.ecommerce.subcategory.SubcategoryRepository;
import com.congthanh.project.service.ecommerce.ProductService;
import com.congthanh.project.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private SubcategoryRepository subcategoryRepository;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private ProductMapper productMapper;

  private final Helper helper = new Helper();

  @Override
  public Object getAllProduct(Integer page, Integer limit) {
    if (page != null & limit != null) {
      Pageable pageable = PageRequest.of(page, limit);
      Page<Product> result = productRepository.findAll(pageable);
      ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
      if (result.hasContent()) {
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : result.getContent()) {
          ProductDTO productDTO = productMapper.mapProductEntityToDTO(product);
          list.add(productDTO);
        }
        response.setResponseList(list);
        response.setTotalPage(result.getTotalPages());
      } else {
        throw new RuntimeException("List empty exception");
      }
      return response;
    } else {
      List<Product> list = productRepository.findAll();
      List<ProductDTO> result = new ArrayList<>();
      for (Product product : list) {
        ProductDTO productDTO = productMapper.mapProductEntityToDTO(product);
        result.add(productDTO);
      }
      return result;
    }
  }

  @Override
  public ProductDTO getProductById(String id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    ProductDTO response = productMapper.mapProductEntityToDTO(product);
    return response;
  }

  @Override
  public ProductDTO getProductBySlug(String slug) {
    Product product = productRepository.findProductBySlug(slug).orElseThrow(() -> new NotFoundException("Product not found"));
    ProductDTO response = productMapper.mapProductEntityToDTO(product);
    return response;
  }

  @Override
  public ProductDTO createProduct(ProductDTO productDTO) {
    Optional<Product> existProduct = productRepository.findByName(productDTO.getName());
    if (existProduct.isPresent()) {
      throw new RuntimeException("Product ton taiii");
    } else {
      Category category = categoryRepository.findById(Integer.parseInt(productDTO.getCategory())).orElseThrow(() -> new NotFoundException(" not found"));
      Subcategory subcategory = subcategoryRepository.findById(Integer.parseInt(productDTO.getSubcategory())).orElseThrow(() -> new NotFoundException(" not found"));
      Store store = storeRepository.findById(productDTO.getStore()).orElseThrow(() -> new NotFoundException(" not found"));
      String productSlug = helper.createSlugFromProductName(productDTO.getName());
      Product product = Product.builder()
              .name(productDTO.getName())
              .category(category)
              .subcategory(subcategory)
              .quantity(productDTO.getQuantity())
              .price(productDTO.getPrice())
              .production(productDTO.getProduction())
              .description(productDTO.getDescription())
              .status(StateStatus.STATUS_ACTIVE)
              .slug(productSlug)
              .store(store)
              .build();
      Product result = productRepository.save(product);
      ProductDTO response = productMapper.mapProductEntityToDTO(result);
      return response;
    }
  }

  @Override
  public Product updateProduct(ProductDTO productDTO) {
    Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("Product not found"));

    product.setName(productDTO.getName());
    // product.setCategory(productDTO.getCategory());
    // product.setSubcategory(productDTO.getSubcategory());
    product.setQuantity(productDTO.getQuantity());
    product.setPrice(productDTO.getPrice());
    product.setProduction(productDTO.getProduction());
    product.setDescription(productDTO.getDescription());

    productRepository.save(product);
    return product;
  }

  @Override
  public boolean deleteProduct(String id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    if (product.getStatus().equalsIgnoreCase(StateStatus.STATUS_DELETED)) {
      throw new RuntimeException("Product have deleted before");
    } else {
      boolean result = productRepository.deleteProduct(id);
      return result;
    }
  }

  @Override
  public ResponseWithPagination<ProductDTO> getProductByCategory(int categoryId, int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
    Page<Product> result = productRepository.findByCategoryId(categoryId, pageable);
    ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
    if (result.hasContent()) {
      List<ProductDTO> list = new ArrayList<>();
      for (Product product : result.getContent()) {
        ProductDTO productDTO = productMapper.mapProductEntityToDTO(product);
        list.add(productDTO);
      }
      response.setResponseList(list);
      response.setTotalPage(result.getTotalPages());
    } else {
      throw new RuntimeException("List empty exception");
    }
    return response;
  }

  @Override
  public ResponseWithPagination<ProductDTO> getProductBySubcategory(int subcategoryId, int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
    Page<Product> result = productRepository.findBySubcategoryId(subcategoryId, pageable);
    ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
    if (result.hasContent()) {
      List<ProductDTO> list = new ArrayList<>();
      for (Product product : result.getContent()) {
        ProductDTO productDTO = productMapper.mapProductEntityToDTO(product);
        list.add(productDTO);
      }
      response.setResponseList(list);
      response.setTotalPage(result.getTotalPages());
    } else {
      throw new RuntimeException("List empty exception");
    }
    return response;
  }

  @Override
  public List<ProductDTO> searchProduct(String keyword) {
    List<Product> data = productRepository.searchProduct(keyword);
    System.out.println("CHECKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK" + data);

    if (data.size() > 0) {
      List<ProductDTO> result = new ArrayList<>();
      for (Product product : data) {
        ProductDTO productDTO = productMapper.mapProductEntityToDTO(product);
        result.add(productDTO);
      }
      return result;
    } else {
      return null;
    }
  }

  @Override
  public Long getSoldByProduct(String productId) {
    Long result = productRepository.countTotalSoldProduct(productId);
    return result != null ? result : 0;
  }
}
