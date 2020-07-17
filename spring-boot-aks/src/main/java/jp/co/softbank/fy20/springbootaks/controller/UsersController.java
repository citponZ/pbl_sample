package jp.co.softbank.fy20.springbootaks.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.softbank.fy20.springbootaks.entity.Users;
import jp.co.softbank.fy20.springbootaks.form.UsersForm;
import jp.co.softbank.fy20.springbootaks.service.*;

@Controller
@RequestMapping("/mypage")
public class UsersController {

    private final UsersService usersService;
    private final HistoryService historyService;

    // ServiceをDIする（@Autowiredは省略）
    public UsersController(UsersService usersService, HistoryService historyService) {
        this.usersService = usersService;
        this.historyService = historyService;
    }
    
    /**
     * ユーザー登録
     */
    @RequestMapping("register")
    public String registerMain(Model model, HttpSession session) {

        model.addAttribute("usersForm", new UsersForm());
        historyService.sessionSet(session);
        return "users/register";
    }

    /**
     * ユーザー登録を行う
     */
    @RequestMapping("registerConplete")
    public String registerConplete(@Validated UsersForm usersForm, BindingResult bindingResult, 
                                        Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usersForm", usersForm);
            historyService.sessionSet(session);
            return "users/register";
        }
        if (usersService.find(usersForm.getId()) != null){
            model.addAttribute("message", usersForm.getId() + "は存在します。");
            model.addAttribute("usersForm", new UsersForm());
            return "users/register";
        }
        Users users = usersForm.convertToEntity();
        usersService.insert(users);
        historyService.sessionSet(session);
        return "users/test";
    }
}