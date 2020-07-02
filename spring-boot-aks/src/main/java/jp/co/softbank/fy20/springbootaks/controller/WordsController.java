package jp.co.softbank.fy20.springbootaks.controller;

import jp.co.softbank.fy20.springbootaks.entity.Words;
import jp.co.softbank.fy20.springbootaks.service.WordsService;
import jp.co.softbank.fy20.springbootaks.form.WordsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/words")
public class WordsController {
    private final WordsService wordsService;

    // EmployeeServiceをDIする（@Autowiredは省略）
    public WordsController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    /**
     * 全件検索を行い、一覧画面に遷移する。
     */
    @GetMapping("/findAll")
    public String find(Model model) {
        List<Words> wordsList = wordsService.findAll();
        model.addAttribute("wordsList", wordsList);
        return "words/findAll";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "words/index";
    }
}