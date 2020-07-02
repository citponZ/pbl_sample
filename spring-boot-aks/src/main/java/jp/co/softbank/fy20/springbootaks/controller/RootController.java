package jp.co.softbank.fy20.springbootaks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        // "redirect:"を先頭につけるとリダイレクトになる
        return "/words/index";
    }
}