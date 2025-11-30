package com.congthanh.cartservice.model.mapper;

import com.congthanh.cartservice.model.document.CartDocument;
import com.congthanh.cartservice.model.dto.CartDTO;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.model.viewmodel.CartVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart mapCartDTOToEntity(CartDTO cartDTO);

    CartDTO mapCartEntityToDTO(Cart cart);

    CartVm mapCartDocumentToVm(CartDocument cart);

}
