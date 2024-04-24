package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.ReviewDTO;
import com.congthanh.project.entity.ecommerce.Review;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

        modelMapper.typeMap(Review.class, ReviewDTO.class)
                .addMapping(src -> src.getProduct().getId(), ReviewDTO::setProduct);

//        modelMapper.typeMap(ReviewDTO.class, Review.class)
//                .addMapping(dest -> dest.getProduct(), (src) -> dest.getProduct().setName());
    }

    public Review mapReviewDTOToEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    public ReviewDTO mapReviewEntityToDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }
}
