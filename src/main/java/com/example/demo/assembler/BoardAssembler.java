package com.example.demo.assembler;

import com.example.demo.controller.MainController;
import com.example.demo.dto.BoardResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequestMapping("/api/v1")
public class BoardAssembler implements RepresentationModelAssembler<BoardResponseDTO, EntityModel<BoardResponseDTO>> {

    @Override
    public EntityModel<BoardResponseDTO> toModel(BoardResponseDTO dto) {
        Link selfLink = linkTo(methodOn(MainController.class).getBoard(dto.getId())).withSelfRel();
        Link updateLink = linkTo(methodOn(MainController.class).editPost(dto.getId(), null)).withRel("update");
        Link listLink = linkTo(methodOn(MainController.class).getList()).withRel("list");

        return EntityModel.of(dto, selfLink, updateLink,listLink);
    }
}
