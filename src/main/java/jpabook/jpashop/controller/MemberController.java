package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        final Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        final Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        // 저장이 끝나면 어디로 갈래? 저장 후 리로딩 되면 안 좋기 때문에 redirect 를 많이 사용한다. home 에 보내버리자.
        return "redirect:/";
    }

    @GetMapping("members")
    public String list(Model model) {
        final List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
