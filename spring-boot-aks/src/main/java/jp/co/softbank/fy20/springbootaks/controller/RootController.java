package jp.co.softbank.fy20.springbootaks.controller;

import javax.websocket.Session;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.softbank.fy20.springbootaks.service.*;

@Controller
public class RootController {

    private final HistoryService historyService;

    // ServiceをDIする（@Autowiredは省略）
    public RootController(HistoryService historyService) {
        this.historyService = historyService;
    }
    
    @GetMapping("/")
    public String root(HttpSession session) {
        historyService.sessionSet(session);
        // "redirect:"を先頭につけるとリダイレクトになる
        return "redirect:words/index";
    }
}