package jp.co.softbank.fy20.springbootaks.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jp.co.softbank.fy20.springbootaks.entity.*;
import jp.co.softbank.fy20.springbootaks.form.UsersForm;
import jp.co.softbank.fy20.springbootaks.service.*;

@Controller
@RequestMapping("/mypage")
public class UsersController {

    private final UsersService usersService;
    private final HistoryService historyService;
    private final DeleteRequestService deleteRequestService;
    private final WordsService wordsService;

    // ServiceをDIする（@Autowiredは省略）
    public UsersController(UsersService usersService, HistoryService historyService, DeleteRequestService deleteRequestService, WordsService wordsService) {
        this.usersService = usersService;
        this.historyService = historyService;
        this.deleteRequestService = deleteRequestService;
        this.wordsService = wordsService;
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

    /**
     * 削除依頼確認画面にいく
     */
    @RequestMapping("deleterequest")
    public String deleteRequest(Model model, HttpSession session) {
        List<DeleteRequest> requestList = deleteRequestService.findAll();
        model.addAttribute("requestList", requestList);
        model.addAttribute("searchName", null);
        historyService.sessionSet(session);
        return "admin/deleterequest";
    }

    /**
     * 削除依頼確認画面にいく
     */
    @RequestMapping("requestresponse")
    public String requestResponse(@RequestParam("deletedata[]") List<String> deleteList , @RequestParam Boolean typeProcess,Model model, HttpSession session) {
        if (typeProcess){
            //データを消して良い時
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            for(String id: deleteList){
                DeleteRequest deleteRequest = deleteRequestService.findId(Integer.parseInt(id));
                wordsService.delete(wordsService.findByName(deleteRequest.getWordName()).get(0).getId(), auth.getName());
                deleteRequestService.deleteWord(deleteRequest.getWordName());
            }
        }
        else{
            //データを消さないとき
            for(String id: deleteList){
                deleteRequestService.deleteId(Integer.parseInt(id));
            }
        }
        historyService.sessionSet(session);
        return "redirect:deleterequest";
    }

    /**
     * 削除依頼画面での絞り込み
     */
    @RequestMapping("requestsearch")
    public String requestsearch(@RequestParam String searchName,Model model, HttpSession session) {
        List<DeleteRequest> requestList = deleteRequestService.findByName(searchName);
        model.addAttribute("requestList", requestList);
        model.addAttribute("searchName", searchName);
        historyService.sessionSet(session);
        return "admin/deleterequest";
    }
}