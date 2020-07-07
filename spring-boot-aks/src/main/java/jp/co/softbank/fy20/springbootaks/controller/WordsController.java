package jp.co.softbank.fy20.springbootaks.controller;

import jp.co.softbank.fy20.springbootaks.entity.Words;
import jp.co.softbank.fy20.springbootaks.entity.WordsByAbb;
import jp.co.softbank.fy20.springbootaks.service.WordsService;
import jp.co.softbank.fy20.springbootaks.form.WordsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.io.IOException;

@Controller
@RequestMapping("/words")
public class WordsController {
    private final WordsService wordsService;
    private String updateMessage;

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
    //Name検索
    @GetMapping("/searchName")
    public String searchName(@RequestParam String name, Model model) {
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if(wordsList != null){
            model.addAttribute("wordsList", wordsList);
            return "単語ページ";
        }

        wordsList = wordsService.findByNameAsInclude(name);
        model.addAttribute("wordsList", wordsList);
        //model.addAttribute("numOfSearch", wordsList.size());
        if(wordsList==null){
            model.addAttribute("message", name+"との一致はありません。");
        }
        return "候補ページ";
    }
    //単語ページ
    @GetMapping("/id/{name}")
    public String showWord(@PathVariable String name, Model model) {
        
        // sessionでもらったwordsListをMedelに追加
        return "単語ページ.html";
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
        model.addAttribute("wordsForm", new WordsForm());
        return "words/insertMain";
    }

    //追加insert(deleteID的な) エラー処理も
    @PostMapping("/insertComplete")
    public String insertComplet(@Validated WordsForm wordsForm, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            //return "redirect:insertMain";
            return "words/insertMain";
        }
        Words words = wordsForm.convertToEntity();
        wordsService.insert(words);
        model.addAttribute("name", wordsForm.getName());
        model.addAttribute("userId", wordsForm.getUserID());
        model.addAttribute("content", wordsForm.getContent());
        
        return "redirect:insertResult";
    }
    
    //insertResult
    @GetMapping("/insertResult")
    public String insertResult(Model model) {
        model.addAttribute("message", "追加できました");
        return "words/insertResult";
    }

    //updateMain
    @GetMapping("/updateMain")
    public String update(Model model) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("id", null);
        //model.addAttribute("wordsForm", new WordsForm());
        return "words/updateMain";
    }

    //ID検索
    @GetMapping("/updateSearchId")
    public String updateSearchId(@RequestParam String id, Model model) {
        Words words = wordsService.find(Integer.parseInt(id));
        model.addAttribute("words", words);
        model.addAttribute("id", id);
        if(words==null){
            model.addAttribute("message", "そのIDは存在しません");
        }
        return "words/updateMain";
    }



    //update(deleteID的な) エラー処理も
    @PostMapping("/updateComplete")
    public String updateComplete(@RequestParam String id,@RequestParam String content,Model model) throws Exception {
        int check = wordsService.update(content, Integer.parseInt(id));
        if(check >= 1){
            updateMessage = "更新しました。";
        }else{
            updateMessage =  "更新できませんでした。";
        }
               
        return "redirect:updateResult";
    }
    
    //updateResult
    @GetMapping("/updateResult")
    public String updateResult(Model model) {
        model.addAttribute("message",updateMessage);
        return "words/updateResult";
    }

    //updateResult
    /*
    @GetMapping("/badUpdateResult")
    public String badUpdateResult(Model model) {
        model.setAttribute("message","更新できませんでした。");
        return "words/updateResult";
    }
    */

}