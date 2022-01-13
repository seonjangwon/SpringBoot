package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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

    /*@PostMapping("{boardId}")
    @ResponseBody
    public BoardDetailDTO findByIdAjax(@PathVariable("boardId") Long boardId){

        return bs.findById(boardId);
    }*/

    @PostMapping("{boardId}")
    public ResponseEntity findById2(@PathVariable Long boardId){

        return new ResponseEntity<BoardDetailDTO>(bs.findById(boardId), HttpStatus.OK);
    }

    @GetMapping("update/{boardId}")
    public String updateForm(@PathVariable("boardId") Long boardId, Model model){
        model.addAttribute("board",bs.findById(boardId));
        return "/board/update";
    }

    @PostMapping("update")
    public String update(@Validated @ModelAttribute("board") BoardDetailDTO boardDetailDTO, BindingResult bindingResult, Model model){
        try {
            bs.update(boardDetailDTO);
        } catch (IllegalStateException e){
            model.addAttribute("board",boardDetailDTO);
            bindingResult.addError(new ObjectError("board",e.getMessage()));
            System.out.println("boardDetailDTO = " + boardDetailDTO + ", bindingResult = " + bindingResult);
            return "/board/update";
        }
        return "redirect:/board/"+boardDetailDTO.getBoardId();
    }

    @PutMapping("update")
    @ResponseBody
    public String updateAjax(@RequestBody BoardDetailDTO boardDetailDTO){
        try {
            bs.update(boardDetailDTO);
        } catch (IllegalStateException e){

            return "no";
        }

        return "ok";

    }

}
