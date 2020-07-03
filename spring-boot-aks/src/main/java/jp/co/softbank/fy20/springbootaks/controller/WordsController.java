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

    //検索画面に移動
    @GetMapping("/search")
    public String search(Model model) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("id", null);
        return "words/search";
    }
    //ID検索
    @GetMapping("/searchId")
    public String searchId(@RequestParam String id, Model model) {
        Words words = wordsService.find(Integer.parseInt(id));
        model.addAttribute("words", words);
        model.addAttribute("id", id);
        if(words==null){
            model.addAttribute("message", "そのIDは存在しません");
        }
        return "words/search";
    }


    //削除画面に移動
    @GetMapping("/delete")
    public String delete(Model model) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("id", null);
        return "words/delete";
    }
    //ID削除
    @PostMapping("/deleteId")
    public String deleteId(@RequestParam String id, Model model) {
        boolean check = wordsService.delete(Integer.parseInt(id));
        if (check){
            model.addAttribute("message", "削除できました");
        }else{
            model.addAttribute("message", "削除できませんでした");
        }
        return "words/deleteResult";
    }

    //insertMain
    @GetMapping("/insertMain")
    public String insert(Model model) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("id", null);
        return "words/insertMain";
    }

    //追加insert(deleteID的な)
    @PostMapping("/insertComplete")
    public String insertComplet(WordsForm wordsForm) {
        Words words = wordsForm.convertToEntity();
        wordsService.insert(words);
        return "redirect:words/insertResult";
    }
    


}