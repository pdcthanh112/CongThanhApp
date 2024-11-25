package com.congthanh.catalogservice.model.mapper;

import com.congthanh.catalogservice.model.dto.TagDTO;
import com.congthanh.catalogservice.model.entity.Tag;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private static  final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Tag mapTagDTOToTag(TagDTO tagDTO) {
        return modelMapper.map(tagDTO, Tag.class);
    }

    public static TagDTO mapTagEntityToDTO(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }
}