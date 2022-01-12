package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService bs;

    @GetMapping("save")
    public String saveForm(Model model){
        model.addAttribute("board",new BoardDTO());
        return "/board/save";
    }
    @PostMapping("save")
    public String save(@Validated @ModelAttribute("board") BoardDTO boardDTO, BindingResult bindingResult){
        System.out.println("boardDTO = " + boardDTO + ", bindingResult = " + bindingResult);

        if (bindingResult.hasErrors()){
            return "/board/save";
        }

        bs.save(boardDTO);

        return "index";
    }

    @GetMapping
    public String findAll(Model model){
        List<BoardDetailDTO> boardDTOList = bs.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "/board/findAll";
    }

    @GetMapping("{boardId}")
    public String findById(@PathVariable("boardId") Long boardId, Model model){
        System.out.println("boardId = " + boardId );
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("board",boardDetailDTO);
        return "/board/findById";
    }

    @PostMapping("{boardId}")
    @ResponseBody
    public BoardDetailDTO findByIdAjax(@PathVariable("boardId") Long boardId){

        return bs.findById(boardId);
    }


}
