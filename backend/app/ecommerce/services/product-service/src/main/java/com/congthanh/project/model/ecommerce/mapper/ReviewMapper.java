package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ReviewDTO;
import com.congthanh.project.entity.Review;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {

        modelMapper.typeMap(Review.class, ReviewDTO.class)
                .addMapping(src -> src.getProduct().getId(), ReviewDTO::setProduct)
                .addMapping(src -> src.getVariant().getId(), ReviewDTO::setVariant);

//        modelMapper.typeMap(ReviewDTO.class, Review.class)
//                .addMapping(dest -> dest.getProduct(), (src) -> dest.getProduct().setName());
    }

    public static Review mapReviewDTOToEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    public static ReviewDTO mapReviewEntityToDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }
}
