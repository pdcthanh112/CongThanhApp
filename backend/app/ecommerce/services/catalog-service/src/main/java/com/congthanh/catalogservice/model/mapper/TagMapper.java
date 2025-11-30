package com.congthanh.catalogservice.model.mapper;

import com.congthanh.catalogservice.model.dto.TagDTO;
import com.congthanh.catalogservice.model.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag mapTagDTOToTag(TagDTO tagDTO);

    TagDTO mapTagEntityToDTO(Tag tag);
}