package jp.co.softbank.fy20.springbootaks.controller;

import jp.co.softbank.fy20.springbootaks.entity.*;
import jp.co.softbank.fy20.springbootaks.service.*;
import jp.co.softbank.fy20.springbootaks.form.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/words")
public class WordsController {
    private final WordsService wordsService;
    private final HistoryService historyService;

    // ServiceをDIする（@Autowiredは省略）
    public WordsController(WordsService wordsService, HistoryService historyService) {
        this.wordsService = wordsService;
        this.historyService = historyService;
    }

    /**
     * 全件検索を行い、一覧画面に遷移する。
     */
    @RequestMapping("/findAll")
    public String find(Model model, HttpSession session) {
        List<Words> wordsList = wordsService.findAll();
        model.addAttribute("wordsList", wordsList);
        historyService.sessionSet(session);
        return "words/findAll";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpSession session) {
        historyService.sessionSet(session);

        //ログインユーザを取得
        //ログインしていない場合は「anonymousUser」となる
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Principalからログインユーザの情報を取得
        //String userName = auth.getName();
        return "words/index";
    }

    //検索画面に移動
    @RequestMapping("/search")
    public String search(Model model, HttpSession session) {
        model.addAttribute("words", null);
        model.addAttribute("id", null);
        historyService.sessionSet(session);
        return "words/search";
    }
    //Name検索
    @RequestMapping("/searchName")
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

    
    /*
     * リクエストのURLパスから、今回マッチしたパターンを取り除いた文字列を返す。
     */
    private static String extractPathFromPattern(final HttpServletRequest request){
        String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
    
    //「/」で区切られている語句に対応
    //「%2F」(/をエンコードした際に出る文字)が語句に入っているとうまくいかない
    //単語ページ
    @RequestMapping("/id/**")
    public String showWord(HttpServletRequest request, @ModelAttribute("message") String message, 
                            Model model, HttpSession session) throws UnsupportedEncodingException{
        final String name = extractPathFromPattern(request);
        //半角スペースがあった場合、アンダーバーに置換してリダイレクト
        if(name.contains(" ")){
            return "redirect:/words/id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8").replace("%2F", "/");
        }
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if (wordsList.size() == 0){
            historyService.sessionSet(session);
            return "redirect:/";
        }
        String content = wordsService.makeLink(wordsList.get(0).getContent());
        List<String> dict = wordsService.findAllName();
        for (WordsByAbb words : wordsList){
            if (dict.contains(words.getAbbName())){
                String tmp = "<a href=\"/spring-boot-aks/words/id/"+words.getAbbName()+"\">"+words.getAbbName()+"</a>";
                words.setAbbName(tmp);
            }
        }
        wordsList.get(0).setContent(content);
        //ページ名
        model.addAttribute("pageName", name);
        model.addAttribute("wordsList", wordsList);
        //userIDは現状1を入力しているが本来はsessonからログイン情報を入手して代入
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        historyService.findInsert(auth.getName(), wordsList.get(0).getId());
        historyService.sessionSet(session);
        return "words/showWords";
    }

    //削除画面に移動
    @RequestMapping("/delete")
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean check = wordsService.delete(wordsList.get(0).getId(), auth.getName());
        if (check){
            model.addAttribute("message", "削除できました");
        }else{
            model.addAttribute("message", "削除できませんでした");
        }
        historyService.sessionSet(session);
        return "words/deleteResult";
    }

    //insertMain
    @RequestMapping("/insertMain")
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
            historyService.sessionSet(session);
            return "words/insertMain";
        }
        //語句名が同じものが存在したらその語句の更新ページに遷移
        model.addAttribute("name", nameForm.getName());
        if (wordsService.checkByName(nameForm.getName()) != null){
            model.addAttribute("wordsList", wordsService.findByName(nameForm.getName()));
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
        @RequestParam("array[]") List<String> abbList,Model model, RedirectAttributes attributes, HttpSession session) 
                                    throws Exception {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("name", wordsForm.getName());
            historyService.sessionSet(session);
            return "words/insertcontent";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        wordsForm.setUserID(auth.getName());
        Words words = wordsForm.convertToEntity();
        wordsService.insert(words);
        String name = wordsForm.getName();
        for (String abb : abbList){
            String trimAbb = abb.replaceAll("　", " ").trim();
            if(wordsService.checkByNameAbb(wordsForm.getName(), abb) == null && !StringUtils.isEmpty(trimAbb)){
                wordsService.insertAbb(wordsForm.getName(), abb);
            }
        }
        historyService.sessionSet(session);
        return "redirect:id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8").replace("%2F", "/");
    }

    //updateMain
    @RequestMapping("/updateMain")
    public String update(Model model, HttpSession session) {
        model.addAttribute("name", null);
        model.addAttribute("nameForm", new NameForm());
        historyService.sessionSet(session);
        return "words/updateMain";
    }

    //ID検索
    @PostMapping("/updateSearch")
    public String updateSearchId(@RequestParam String name, Model model, HttpSession session) {
        model.addAttribute("name", name);
        if(wordsService.checkByName(name) == null){
            model.addAttribute("message", "その語句は存在しません");
            historyService.sessionSet(session);
            return "words/updateMain";
        }
        model.addAttribute("wordsList", wordsService.findByName(name));
        model.addAttribute("error", null);
        historyService.sessionSet(session);
        return "words/updatecontent";
    }

    //update(deleteID的な) エラー処理も
    @PostMapping("/updateComplete")
    public String updateComplete(@RequestParam String id,@RequestParam String content,@RequestParam("array[]") List<String> abbList,
                                    Model model, HttpSession session) throws Exception {
        //userIDは現状1を入力しているが本来はsessonからログイン情報を入手して代入
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        wordsService.update(content, Integer.parseInt(id), auth.getName());
        Words words = wordsService.find(Integer.parseInt(id));
        String name = words.getName();
        List<String> oldAbbList = wordsService.findAllByNameAbb(name);
        for (String abb : oldAbbList){
            wordsService.deleteAbb(name, abb);
        }
        for (String abb : abbList){
            String trimAbb = abb.replaceAll("　", " ").trim();
            if(wordsService.checkByNameAbb(name, abb) == null && !StringUtils.isEmpty(trimAbb) ){
                //空白ではなく、かつ、すでにあるリスト内になかったら
                wordsService.insertAbb(name, abb);
            }
        }
        historyService.sessionSet(session);
        return "redirect:id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8").replace("%2F", "/");
    }

        //検索画面に移動
        @RequestMapping("/ranking")
        public String ranking(Model model) {
            return "words/ranking";
        }

        //検索画面に移動
        @RequestMapping("/word")
        public String words(Model model) {
            return "words/word";
        }
        @RequestMapping("/test")
        public String test(Model model) {
            return "words/test";
        }

}