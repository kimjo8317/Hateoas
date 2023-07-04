package com.example.demo.controller;

import com.example.demo.assembler.BoardAssembler;
import com.example.demo.dto.BoardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {
    // HAL+JSON (Hypermedia as the Engine of Application State)
    private final BoardAssembler boardAssembler;

    @GetMapping("/board/{id}")
    public EntityModel<BoardResponseDTO> getBoard(@PathVariable int id) {
        // 데이터베이스나 서비스에서 검색하여 가져와야 하는 데이터
        BoardResponseDTO dto = BoardResponseDTO.builder()
                .id(id) // 경로 변수에서 제공된 ID 사용
                .title("제목")
                .build();

        //linkTo  -> link 객체를 생성해줌
        //methodOn -> 클래스를 참조함
        // 게시판 리소스의 self-link 생성
        Link selfLink = linkTo(methodOn(MainController.class).getBoard(id)).withSelfRel();
        // 게시물 편집과 관련된 API로 이동하는 링크
        Link updateLink = linkTo(methodOn(MainController.class).editPost(id, null)).withRel("update");

        return EntityModel.of(dto, selfLink);
    }


    //@Get Post이 없음
    //API 문서의 용도가 아님
    //서버측에서 클라이언트가 요청 가능한 API를 제한
    //ex1)배민 = 가게리스트 -> 가게선택 -> 메뉴선택 -> 결제   //결제창으로 바로 가지않도록 제한
    //ex2)배민 = 가게리스트 -> 가게선택 -> 프로세스 -> 메뉴선택 -> 결제 //프로세스의 변경이 생겼을때
    //ex3) 엔드포인트가 변경될때 api서버의 버전이 변경될때 v1 -> v2 //프론트에서 헤이티오스 활용해서 요청할때
    //서버 측에서 접근 가능한 서버의 URL앤드포인트만 제공함으로써 , 클라이언트의 프로세스 이해가 쉬워지고 잘못된접근을 방지할수있다.

    //2.게시물 편집
    @PutMapping("/board/{id}")
    public EntityModel<BoardResponseDTO> editPost(@PathVariable int id, @RequestBody BoardResponseDTO updatedDto) {
        return null;
        // 제공된 ID로 게시물 편집을 위한 구현 추가
    }

    //3.게시글 리스트
    @GetMapping("/board")
    public CollectionModel<EntityModel<BoardResponseDTO>> getList() {
        List<BoardResponseDTO> list = new ArrayList<>();
        list.add(BoardResponseDTO.builder()
                .id(1)
                .title("Title")
                .build());
        list.add(BoardResponseDTO.builder()
                .id(2)
                .title("Title")
                .build());

        List<EntityModel<BoardResponseDTO>> result = list.stream().map(boardAssembler::toModel).toList();

        Link selfLink = linkTo(methodOn(MainController.class).getList()).withSelfRel();

        return CollectionModel.of(result, selfLink);
    }
    //정리
    //단건 리턴 -> EntityModel
    //리스트 리턴 -> CollectionModel

    //dto 를 EntituModel 로 바꾸는 거는 RepresentationModelAssembler
    //인터페이스를 구현하면 된다

    @GetMapping("/board2/{id}")
    public ResponseEntity<EntityModel<BoardResponseDTO>> getBoard2(@PathVariable int id) {
        BoardResponseDTO dto = BoardResponseDTO.builder()
                .id(id)
                .title("제목")
                .build();
        Link selfLink = linkTo(methodOn(MainController.class).getBoard(id)).withSelfRel();
        Link updateLink = linkTo(methodOn(MainController.class).editPost(id, null)).withRel("update");

        return ResponseEntity.ok(EntityModel.of(dto, selfLink, updateLink));
    }
}
