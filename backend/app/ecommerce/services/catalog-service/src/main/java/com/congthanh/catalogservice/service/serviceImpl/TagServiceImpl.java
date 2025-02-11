package com.congthanh.catalogservice.service.serviceImpl;

import com.congthanh.catalogservice.constant.common.ErrorCode;
import com.congthanh.catalogservice.constant.enums.TagStatus;
import com.congthanh.catalogservice.cqrs.commands.command.tag.CreateTagCommand;
import com.congthanh.catalogservice.cqrs.commands.command.tag.UpdateTagCommand;
import com.congthanh.catalogservice.cqrs.queries.query.tag.GetAllTagQuery;
import com.congthanh.catalogservice.cqrs.queries.query.tag.GetTagByIdQuery;
import com.congthanh.catalogservice.model.dto.TagDTO;
import com.congthanh.catalogservice.model.entity.Tag;
import com.congthanh.catalogservice.exception.ecommerce.BadRequestException;
import com.congthanh.catalogservice.exception.ecommerce.NotFoundException;
import com.congthanh.catalogservice.model.document.TagDocument;
import com.congthanh.catalogservice.model.request.CreateTagRequest;
import com.congthanh.catalogservice.model.request.UpdateTagRequest;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;
import com.congthanh.catalogservice.repository.tag.TagRepository;
import com.congthanh.catalogservice.service.TagService;
import com.congthanh.catalogservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public ResponseWithPagination<TagDTO> getAllTags() {

        GetAllTagQuery query = new GetAllTagQuery();

        ResponseWithPagination<TagDocument> data = queryGateway.query(query, ResponseTypes.instanceOf(ResponseWithPagination.class)).join();

        List<TagDTO> list = data.getResponseList().stream()
                .map(tag -> TagDTO.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .createAt(tag.getCreatedAt())
                        .updateAt(tag.getUpdatedAt())
                        .status(tag.getStatus())
                        .build())
                .collect(Collectors.toList());

        return ResponseWithPagination.<TagDTO>builder()
                .responseList(list)
                .paginationInfo(data.getPaginationInfo())
                .build();
    }

    @Override
    public TagDTO getTagById(Long id) {
        GetTagByIdQuery query = new GetTagByIdQuery(id);
        TagDocument result = queryGateway.query(query, ResponseTypes.instanceOf(TagDocument.class)).join();

        return TagDTO.builder()
                .id(result.getId())
                .name(result.getName())
                .createAt(result.getCreatedAt())
                .updateAt(result.getUpdatedAt())
                .status(result.getStatus())
                .build();
    }

    @Override
    public TagDTO createTag(CreateTagRequest request) {
        System.out.println("Vô Service, trước commandGateway.sendAndWait(event);");
        Optional<Tag> check = tagRepository.findByName(request.getName());
        if (check.isPresent()) {
            throw new RuntimeException("Tag name already exist");
        }
        CreateTagCommand event = CreateTagCommand.builder()
                .id(snowflakeIdGenerator.nextId())
                .name(request.getName())
                .createdAt(Instant.now())
                .createdBy("aaa")
                .updatedAt(Instant.now())
                .updatedBy("bbb")
                .status(TagStatus.ACTIVE)
                .build();
        System.out.println("Service, build Event:"+event);
        var response = commandGateway.sendAndWait(event);
        System.out.println("Service, Sau command, response:"+response);
        return null;
    }

    @Override
    public TagDTO updateTag(UpdateTagRequest request, Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundException("Tag not fount"));
        UpdateTagCommand event = UpdateTagCommand.builder()
                .id(tagId)
                .name(request.getName())
                .createAt(tag.getCreatedAt())
                .updateAt(Instant.now())
                .build();

        var result = commandGateway.sendAndWait(event);

        return null;
    }

    @Override
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundException(String.format(ErrorCode.TAG_NOT_FOUND, tagId)));
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