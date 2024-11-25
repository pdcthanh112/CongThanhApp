package com.congthanh.customerservice.model.mapper;

import com.congthanh.customerservice.model.dto.WishlistDTO;
import com.congthanh.customerservice.model.entity.Wishlist;
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