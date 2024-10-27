package com.congthanh.project.serviceImpl;

import com.congthanh.project.constant.enums.TagStatus;
import com.congthanh.project.cqrs.command.command.tag.CreateTagCommand;
import com.congthanh.project.dto.TagDTO;
import com.congthanh.project.entity.Tag;
import com.congthanh.project.exception.ecommerce.BadRequestException;
import com.congthanh.project.model.mapper.TagMapper;
import com.congthanh.project.model.request.CreateTagRequest;
import com.congthanh.project.repository.tag.TagRepository;
import com.congthanh.project.service.TagService;
import com.congthanh.project.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

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
    public TagDTO createTag(CreateTagRequest request) {
//        Optional<Tag> check = tagRepository.findByTagName(request.getTagName());
//        if (check.isPresent()) {
//            throw new RuntimeException("Tag name already exist");
//        }
        CreateTagCommand tag = CreateTagCommand.builder()
                .id(snowflakeIdGenerator.nextId())
                .name(request.getTagName())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .status(TagStatus.ACTIVE)
                .build();
        System.out.println("Data"+tag);
        var response = commandGateway.sendAndWait(tag);
//        return (TagDTO) response;
        return null;
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