package com.sjw.memberboard.controller;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import com.sjw.memberboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService bs;
    private final int BLOCK_LIMIT=3;

    @GetMapping("/save")
    public String saveForm(Model model){
        model.addAttribute("board",new BoardSaveDTO());
        return "/board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("board") BoardSaveDTO boardSaveDTO) throws IOException {
        bs.save(boardSaveDTO);

        return "/board/findAll";
    }


    @GetMapping
    public String findAll(@PageableDefault(page = 0,size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                          Model model){
        System.out.println("BoardController.findAll");
        System.out.println("pageable = " + pageable);
        Page<BoardDetailDTO> pageList = bs.findAll(pageable);
        int startPage =(((int)(Math.ceil((double) (pageable.getPageNumber()+1)/BLOCK_LIMIT)))-1)
                *BLOCK_LIMIT+1;
        int endPage = ((startPage+BLOCK_LIMIT-1)<(pageList.getTotalPages()+1)) ?
                startPage+BLOCK_LIMIT-1 :pageList.getTotalPages();
        model.addAttribute("boardList",pageList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pageLimit",pageable.getPageSize());
        return "/board/findAll";
    }

}
