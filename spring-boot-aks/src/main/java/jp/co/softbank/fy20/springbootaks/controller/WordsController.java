package jp.co.softbank.fy20.springbootaks.controller;

import jp.co.softbank.fy20.springbootaks.entity.*;
import jp.co.softbank.fy20.springbootaks.service.*;
import jp.co.softbank.fy20.springbootaks.form.*;
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

import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/words")
public class WordsController {
    private final WordsService wordsService;
    private final HistoryService historyService;
    private String updateMessage;

    // ServiceをDIする（@Autowiredは省略）
    public WordsController(WordsService wordsService, HistoryService historyService) {
        this.wordsService = wordsService;
        this.historyService = historyService;
    }

    /**
     * 全件検索を行い、一覧画面に遷移する。
     */
    @GetMapping("/findAll")
    public String find(Model model, HttpSession session) {
        List<Words> wordsList = wordsService.findAll();
        model.addAttribute("wordsList", wordsList);
        historyService.sessionSet(session);
        return "words/findAll";
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        historyService.sessionSet(session);
        return "words/index";
    }

    //検索画面に移動
    @GetMapping("/search")
    public String search(Model model, HttpSession session) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("id", null);
        historyService.sessionSet(session);
        return "words/search";
    }
    //ID検索
    @GetMapping("/searchId")
    public String searchId(@RequestParam String id, Model model, HttpSession session) {
        Words words = wordsService.find(Integer.parseInt(id));
        model.addAttribute("words", words);
        model.addAttribute("id", id);
        if(words==null){
            model.addAttribute("message", "そのIDは存在しません");
        }
        historyService.sessionSet(session);
        return "words/search";
    }
    //Name検索
    @GetMapping("/searchName")
    public String searchName(@RequestParam String name, Model model, 
                                RedirectAttributes attributes, HttpSession session)
                                    throws UnsupportedEncodingException {
        
        
        //語句名が同じものが存在したらその語句のページに遷移
        if (wordsService.checkByName(name) != null){
            attributes.addFlashAttribute("message", null);
            //return "redirect:id/"+name;
            historyService.sessionSet(session);
            return "redirect:id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8");
        }

        List<WordsByAbb> wordsList = wordsService.findByNameAsInclude(name);

        //List<WordsByAbb> -> List<WordsListAbb>
        //name,content,List<Abb>
        List<WordsListAbb> wordsAbbList = wordsService.converToWordsListAbb(wordsList);

        model.addAttribute("wordsList", wordsAbbList);
        //model.addAttribute("numOfSearch", wordsList.size());
        if(wordsAbbList.size()==0){
            model.addAttribute("message", "「"+name+"」"+"との一致はありません。");
        }
        else{
            model.addAttribute("message", "「"+name+"」"+"の検索結果："+wordsAbbList.size()+"件");
        }
        model.addAttribute("searchName", name+" - 検索");
        historyService.sessionSet(session);
        return "words/candidate";
    }
    //単語ページ
    @GetMapping("/id/{name}")
    public String showWord(@PathVariable String name, @ModelAttribute("message") String message, 
                            Model model, HttpSession session) throws UnsupportedEncodingException{
        
        //半角スペースがあった場合、アンダーバーに置換してリダイレクト
        if(name.contains(" ")){
            return "redirect:"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8");
        }
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if (wordsList.size() == 0){
            historyService.sessionSet(session);
            return "redirect:../../words/index";
        }
        String content = wordsService.makeLink(wordsList.get(0).getContent());
        wordsList.get(0).setContent(content);
        //ページ名
        model.addAttribute("pageName", name);
        model.addAttribute("wordsList", wordsList);
        model.addAttribute("message");
        //userIDは現状1を入力しているが本来はsessonからログイン情報を入手して代入
        historyService.findInsert(1, wordsList.get(0).getId());
        historyService.sessionSet(session);
        return "words/showWords";
    }

    //削除画面に移動
    @GetMapping("/delete")
    public String delete(Model model, HttpSession session) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("name", null);
        historyService.sessionSet(session);
        return "words/delete";
    }
    //ID削除
    @PostMapping("/deleteName")
    public String deleteId(@RequestParam String name, Model model, HttpSession session) {
        model.addAttribute("name", name);
        if (wordsService.checkByName(name) == null){
            //なかった時
            model.addAttribute("message", "この語句は存在しません。");
            historyService.sessionSet(session);
            return "words/delete";
        }
        //ある
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        //userIDは現状1を入力しているが本来はsessonからログイン情報を入手して代入
        boolean check = wordsService.delete(wordsList.get(0).getId(), 1);
        if (check){
            model.addAttribute("message", "削除できました");
        }else{
            model.addAttribute("message", "削除できませんでした");
        }
        historyService.sessionSet(session);
        return "words/deleteResult";
    }

    //insertMain
    @GetMapping("/insertMain")
    public String insert(Model model, HttpSession session) {
        model.addAttribute("name", null);
        model.addAttribute("nameForm", new NameForm());
        historyService.sessionSet(session);
        return "words/insertMain";
    }

    //check insert or update
    @PostMapping("/inputContent")
    public String inputContent(@Validated NameForm nameForm, BindingResult bindingResult, 
                                Model model, HttpSession session) {
        // nameにブランク確認
        if (bindingResult.hasErrors()) {
            //return "redirect:insertMain";
            historyService.sessionSet(session);
            return "words/insertMain";
        }
        //語句名が同じものが存在したらその語句の更新ページに遷移
        model.addAttribute("name", nameForm.getName());
        if (wordsService.checkByName(nameForm.getName()) != null){
            List<WordsByAbb> wordsList = wordsService.findByName(nameForm.getName());
            model.addAttribute("wordsList", wordsList);
            model.addAttribute("error", "この語句はすでに登録されています。");
            historyService.sessionSet(session);
            return "words/updatecontent";
        }
        //追加ページに遷移
        model.addAttribute("wordsForm", new WordsForm());
        historyService.sessionSet(session);
        return "words/insertcontent";
    }

    //追加insert(deleteID的な) エラー処理も
    @PostMapping("/insertComplete")
    public String insertComplet(@Validated WordsForm wordsForm, BindingResult bindingResult, 
                                Model model, RedirectAttributes attributes, HttpSession session) 
                                    throws Exception {
        
        if (bindingResult.hasErrors()) {
            //return "redirect:insertMain";
            model.addAttribute("name", wordsForm.getName());
            historyService.sessionSet(session);
            return "words/insertcontent";
        }

        Words words = wordsForm.convertToEntity();
        wordsService.insert(words);
        String name = wordsForm.getName();
        
        historyService.sessionSet(session);
        //return "redirect:id/"+name;
        return "redirect:id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8");
    }
    
    //insertResult
    @GetMapping("/insertResult")
    public String insertResult(Model model, HttpSession session) {
        model.addAttribute("message", "追加できました");
        historyService.sessionSet(session);
        return "words/insertResult";
    }

    //updateMain
    @GetMapping("/updateMain")
    public String update(Model model, HttpSession session) {
        Words words = null;
        model.addAttribute("words", words);
        model.addAttribute("name", null);
        //model.addAttribute("wordsForm", new WordsForm());
        historyService.sessionSet(session);
        return "words/updateMain";
    }

    //ID検索
    @PostMapping("/updateSearchId")
    public String updateSearchId(@RequestParam String name, Model model, HttpSession session) {

        model.addAttribute("name", name);
        if(wordsService.checkByName(name) == null){
            model.addAttribute("message", "その語句は存在しません");
            historyService.sessionSet(session);
            return "words/updateMain";
        }
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        model.addAttribute("wordsList", wordsList);
        model.addAttribute("error", null);
        historyService.sessionSet(session);
        return "words/updatecontent";
    }



    //update(deleteID的な) エラー処理も
    @PostMapping("/updateComplete")
    public String updateComplete(@RequestParam String id,@RequestParam String content,Model model, 
                                    HttpSession session) throws Exception {
        //userIDは現状1を入力しているが本来はsessonからログイン情報を入手して代入
        int check = wordsService.update(content, Integer.parseInt(id), 1);
        /*if(check >= 1){
            updateMessage = "更新しました。";
        }else{
            updateMessage =  "更新できませんでした。";
        }*/
        Words words = wordsService.find(Integer.parseInt(id));
        String name = words.getName();
        historyService.sessionSet(session);
        return "redirect:id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8");
    }
    
    //updateResult
    @GetMapping("/updateResult")
    public String updateResult(Model model, HttpSession session) {
        model.addAttribute("message",updateMessage);
        historyService.sessionSet(session);
        return "words/updateResult";
    }


        //検索画面に移動
        @GetMapping("/ranking")
        public String ranking(Model model) {
            return "words/ranking";
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