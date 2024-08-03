package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.TagDTO;
import com.congthanh.project.entity.Tag;
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
