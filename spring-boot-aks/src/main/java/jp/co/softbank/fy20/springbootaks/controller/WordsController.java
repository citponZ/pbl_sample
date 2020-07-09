package jp.co.softbank.fy20.springbootaks.controller;

import jp.co.softbank.fy20.springbootaks.entity.Words;
import jp.co.softbank.fy20.springbootaks.entity.WordsByAbb;
import jp.co.softbank.fy20.springbootaks.entity.WordsListAbb;
import jp.co.softbank.fy20.springbootaks.service.WordsService;
import jp.co.softbank.fy20.springbootaks.form.WordsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    @PostMapping("/findAll")
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
    public String searchName(@RequestParam String name, Model model, RedirectAttributes attributes) throws UnsupportedEncodingException {
        
        
        //語句名が同じものが存在したらその語句のページに遷移
        if (wordsService.checkByName(name) != null){
            attributes.addFlashAttribute("message", null);
            //return "redirect:id/"+name;
            return "redirect:id/"+ URLEncoder.encode(name, "UTF-8");
        }

        List<WordsByAbb> wordsList = wordsService.findByNameAsInclude(name);

        //List<WordsByAbb> -> List<WordsListAbb>
        //name,content,List<Abb>
        List<WordsListAbb> wordsAbbList = wordsService.converToWordsListAbb(wordsList);

        model.addAttribute("wordsList", wordsAbbList);
        //model.addAttribute("numOfSearch", wordsList.size());
        if(wordsList.size()==0){
            model.addAttribute("message", name+"との一致はありません。");
        }
        model.addAttribute("searchName", name+" - 検索");
        return "words/candidate";
    }
    //単語ページ
    @GetMapping("/id/{name}")
    public String showWord(@PathVariable String name, @ModelAttribute("message") String message, Model model) {
        

        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if (wordsList.size() == 0){
            return "redirect:../../words/index";
        }
        //ページ名
        model.addAttribute("pageName", name);
        model.addAttribute("wordsList", wordsList);
        model.addAttribute("message");

        // sessionでもらったwordsListをMedelに追加
        return "words/showWords";
    }

    //削除画面に移動
    @GetMapping("/delete")
    public String delete(Model model) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("name", null);

        return "words/delete";
    }
    //ID削除
    @PostMapping("/deleteName")
    public String deleteId(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        if (wordsService.checkByName(name) == null){
            //なかった時
            model.addAttribute("message", "この語句は存在しません。");
            return "words/delete";
        }
        //ある
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        boolean check = wordsService.delete(wordsList.get(0).getId());
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
        model.addAttribute("name", null);
        return "words/insertMain";
    }

    //check insert or update
    @PostMapping("/inputContent")
    public String inputContent(@RequestParam String name, Model model) {
        // nameにブランク確認
        //語句名が同じものが存在したらその語句の更新ページに遷移
        model.addAttribute("name", name);
        if (wordsService.checkByName(name) != null){
            List<WordsByAbb> wordsList = wordsService.findByName(name);
            model.addAttribute("wordsList", wordsList);
            model.addAttribute("error", "この語句はすでに登録されています。");
            return "words/updatecontent";
        }
        //追加ページに遷移
        model.addAttribute("wordsForm", new WordsForm());
        return "words/insertcontent";
    }

    //追加insert(deleteID的な) エラー処理も
    @PostMapping("/insertComplete")
    public String insertComplet(@Validated WordsForm wordsForm, BindingResult bindingResult, 
                                Model model, RedirectAttributes attributes) throws Exception {
        if (bindingResult.hasErrors()) {
            //return "redirect:insertMain";
            return "words/insertMain";
        }

        Words words = wordsForm.convertToEntity();
        wordsService.insert(words);
        String name = wordsForm.getName();
        
        //return "redirect:id/"+name;
        return "redirect:id/"+ URLEncoder.encode(name, "UTF-8");
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
        model.addAttribute("name", null);
        //model.addAttribute("wordsForm", new WordsForm());
        return "words/updateMain";
    }

    //ID検索
    @PostMapping("/updateSearchId")
    public String updateSearchId(@RequestParam String name, Model model) {

        model.addAttribute("name", name);
        if(wordsService.checkByName(name) == null){
            model.addAttribute("message", "その語句は存在しません");
            return "words/updateMain";
        }
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        model.addAttribute("wordsList", wordsList);
        model.addAttribute("error", null);
        return "words/updateContent";
    }



    //update(deleteID的な) エラー処理も
    @PostMapping("/updateComplete")
    public String updateComplete(@RequestParam String id,@RequestParam String content,Model model) throws Exception {
        int check = wordsService.update(content, Integer.parseInt(id));
        /*if(check >= 1){
            updateMessage = "更新しました。";
        }else{
            updateMessage =  "更新できませんでした。";
        }*/
        Words words = wordsService.find(Integer.parseInt(id));
        String name = words.getName();
               
        return "redirect:id/"+ URLEncoder.encode(name, "UTF-8");
    }
    
    //updateResult
    @GetMapping("/updateResult")
    public String updateResult(Model model) {
        model.addAttribute("message",updateMessage);
        return "words/updateResult";
    }


        //検索画面に移動
        @GetMapping("/test")
        public String test(Model model) {
            return "words/test";
        }

        //検索画面に移動
        @GetMapping("/word")
        public String words(Model model) {
            return "words/word";
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