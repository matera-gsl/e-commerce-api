package com.roadmap.ecommerce.assembler;

import com.roadmap.ecommerce.controller.UserController;
import com.roadmap.ecommerce.dto.user.UserResponseDTO;
import com.roadmap.ecommerce.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<UserResponseDTO>> {

    @Override
    public EntityModel<UserResponseDTO> toModel(User user) {
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.isAdmin(),
                user.getCreatedAt(),
                user.getUpdatedAt());

        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll()).withRel("users"));
    }
}