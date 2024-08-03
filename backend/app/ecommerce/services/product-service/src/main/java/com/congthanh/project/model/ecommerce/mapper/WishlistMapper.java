package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.WishlistDTO;
import com.congthanh.project.entity.Wishlist;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Wishlist mapWishlistDTOToEntity(WishlistDTO wishlistDTO) {
        return modelMapper.map(wishlistDTO, Wishlist.class);
    }

    public WishlistDTO mapWishlistEntityToDTO(Wishlist wishlist) {
        return modelMapper.map(wishlist, WishlistDTO.class);
    }
}
