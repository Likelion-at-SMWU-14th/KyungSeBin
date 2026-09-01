package com.likelion.seminar.member.controller;

import com.likelion.seminar.member.dto.MemberCreateRequest;
import com.likelion.seminar.member.dto.MemberPageResponse;
import com.likelion.seminar.member.dto.MemberResponse;
import com.likelion.seminar.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(
            @RequestBody MemberCreateRequest request
    ) {
        return memberService.create(request);
    }

    @GetMapping
    public List<MemberResponse> findAll(){
        return memberService.findAll();
    }

    @GetMapping("/{memberId}")
    public MemberResponse findById(
            @PathVariable Long memberId
    ) {
        return memberService.findById(memberId);
    }

    @GetMapping("/search")
    public List<MemberResponse> search(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "minAge") int minAge
    ) {
        return memberService.search(keyword, minAge);
    }

    @GetMapping("/page")
    public MemberPageResponse findPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size
    ) {
        return memberService.findPage(page,size);
    }

    @GetMapping("/jpql")
    public List<MemberResponse> searchByJpql(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "minAge") int minAge
    ) {
        return memberService.searchByJpql(keyword, minAge);
    }

    @PatchMapping("/{memberId}/age")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAge(
            @PathVariable Long memberId,
            @RequestParam(name = "age") int age
    ) {
        memberService.changeAge(memberId,age);
    }
}
