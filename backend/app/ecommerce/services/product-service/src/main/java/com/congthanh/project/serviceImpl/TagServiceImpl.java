package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.TagDTO;
import com.congthanh.project.entity.Tag;
import com.congthanh.project.exception.ecommerce.BadRequestException;
import com.congthanh.project.model.ecommerce.mapper.TagMapper;
import com.congthanh.project.repository.tag.TagRepository;
import com.congthanh.project.service.ecommerce.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> listTag = tagRepository.findAll();
        List<TagDTO> result = new ArrayList<>();
        for (Tag tag : listTag) {
            TagDTO tagDTO = TagMapper.mapTagEntityToDTO(tag);
            result.add(tagDTO);
        }
        return result;
    }

    @Override
    public TagDTO createTag(TagDTO tagDto) {
        Optional<Tag> check = tagRepository.findByTagName(tagDto.getName());
        if (check.isPresent()) {
            throw new RuntimeException("Tag name already exist");
        }
        Tag data = Tag.builder()
                .name(tagDto.getName())
                .createdDate(Instant.now().toEpochMilli())
                .build();
        Tag result = tagRepository.save(data);
        return TagMapper.mapTagEntityToDTO(result);
    }

    @Override
    public boolean addTagFromProduct(Long tagId, String productId) {
        boolean result = tagRepository.addTagFromProduct(tagId, productId);
        if (result) {
            return true;
        } else {
            throw new BadRequestException("Already exist");
        }
    }

    @Override
    public boolean removeTagFromProduct(Long tagId, String productId) {
        boolean result = tagRepository.removeTagFromProduct(tagId, productId);
        if (result) {
            return true;
        } else {
            throw new BadRequestException("Not exist");
        }
    }
}
