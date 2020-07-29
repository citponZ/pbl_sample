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

import java.util.ArrayList;
import java.util.Arrays;
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
    private final DeleteRequestService deleteRequestService;
    

    // ServiceをDIする（@Autowiredは省略）
    public WordsController(WordsService wordsService, HistoryService historyService, DeleteRequestService deleteRequestService) {
        this.wordsService = wordsService;
        this.historyService = historyService;
        this.deleteRequestService = deleteRequestService;
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
        return "words/index";
    }

    //Name検索
    @RequestMapping("/searchName")
    public String searchName(@RequestParam String name, Model model, 
                                RedirectAttributes attributes, HttpSession session)
                                    throws UnsupportedEncodingException {
        //語句名が同じものが存在したらその語句のページに遷移
        if (wordsService.checkByName(name) != null || wordsService.findDuplication().contains(name.toLowerCase())){
            return "redirect:can/" + URLEncode(name);
        }

        List<WordsByAbb> wordsList = wordsService.findByNameAsInclude(name);
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
    
    //履歴追加パターン
    @RequestMapping("/can/**")
    public String redirectWordsWithH(HttpServletRequest request, 
                            Model model, HttpSession session) throws UnsupportedEncodingException{
        final String name = extractPathFromPattern(request);
        if(name.contains(" ")){
            return "redirect:/words/can/"+ URLEncode(name);
        }
        List<String> duplicationList = wordsService.findDuplication();
        List<String> duplicationList2 = new ArrayList<>();
        for(String duplication: duplicationList){
            duplicationList2.add(duplication.replace(" ", "_"));
        }
        if(!duplicationList2.contains(name.toLowerCase())){
            String tmp = wordsService.findNameByAbb(name);
            if(tmp != null){
                return "redirect:/words/can/"+ URLEncode(tmp);
            }
        }
        else{
            //重複あり
            String referer = request.getHeader("Referer");
            List<WordsByAbb> wordsList = new ArrayList<>();
            String name2 = name.replace("_", " ");
            List<Words> tmpList = wordsService.findNameByAbbAndName(name2);
            String str = "";
            //名前だけリンク
            for (Words words : tmpList){
                String tmp = words.getContent();
                int Length = 20;
                int maxLength = (tmp.length() < Length)?tmp.length():Length;
                tmp = tmp.substring(0, maxLength);
                str += "・<a href=\"/words/cand/"+words.getName()+"\">"+words.getName()+"</a> - " + tmp + "......<br>";
            }
            str += "<br>一つの語句が複数の意味を有するために、異なる用法を一覧にしてあります。<br>お探しの用語に一番近い記事を選んで下さい。";
            wordsList.add(new WordsByAbb(name2,str));  
            //ページ名
            model.addAttribute("pageName", name2);
            model.addAttribute("wordsList", wordsList);
            historyService.sessionSet(session,referer);
            return "words/showWords";
        }

        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if (wordsList.size() == 0){
            historyService.sessionSet(session);
            return "redirect:/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        historyService.findInsert(auth.getName(), wordsList.get(0).getId());
        return "redirect:/words/id/" + URLEncode(name);
    }

    //履歴追加+曖昧ページに飛ばないパターン
    @RequestMapping("/cand/**")
    public String redirectWordsWithHT(HttpServletRequest request, 
                                Model model, HttpSession session) throws UnsupportedEncodingException{
        final String name = extractPathFromPattern(request);
        if(name.contains(" ")){
            return "redirect:/words/cand/"+ URLEncode(name);
        }
        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if (wordsList.size() == 0){
            historyService.sessionSet(session);
            return "redirect:/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        historyService.findInsert(auth.getName(), wordsList.get(0).getId());
        return "redirect:/words/id/" + URLEncode(name);
    }

    public static String URLEncode(String name) throws UnsupportedEncodingException{
        return URLEncoder.encode(name.replace(" ", "_"), "UTF-8").replace("%2F", "/").replace("%28", "(").replace("%29", ")");
    }

    //「/」で区切られている語句に対応
    //「%2F」(/をエンコードした際に出る文字)が語句に入っているとうまくいかない
    //単語ページ
    @RequestMapping("/id/**")
    public String showWord(HttpServletRequest request, 
                            Model model, HttpSession session) throws UnsupportedEncodingException{
        String name = extractPathFromPattern(request);
        String referer = request.getHeader("Referer");
        if(name.contains(" ")){
            return "redirect:/words/id/" + URLEncode(name);
        }

        List<WordsByAbb> wordsList = wordsService.findByName(name);
        if (wordsList.size() == 0){
            return "redirect:/words/can/" + URLEncode(name);
        }
        wordsList = wordsService.makeLink(wordsList);

        //ページ名
        model.addAttribute("pageName", name);
        model.addAttribute("wordsList", wordsList);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DeleteRequest deleteRequest = deleteRequestService.find(name,auth.getName());
        model.addAttribute("deleteRequest", deleteRequest);
        historyService.sessionSet(session,referer);
        return "words/showWords";
    }

    //削除画面に移動
    @RequestMapping("/delete")
    public String delete(Model model, HttpSession session) {
        return "redirect:/words/deleteMain";
    }
    //削除画面に移動
    @RequestMapping("/deleteMain")
    public String delete2(Model model, HttpSession session) {
        model.addAttribute("words", null);
        model.addAttribute("name", null);
        historyService.sessionSet(session);
        return "words/delete";
    }

    //ID削除
    @PostMapping("/deleteName")
    public String deleteId(@RequestParam String name, Model model, RedirectAttributes attributes, HttpSession session) {
        model.addAttribute("name", name);
        if (wordsService.checkByName(name) == null){
            //なかった時
            attributes.addFlashAttribute("message", "この語句は存在しません。");
            historyService.sessionSet(session);
            return "redirect/words/deleteMain";
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
        return "redirect:/words/insert";
    }
    //insertMain
    @RequestMapping("/insert")
    public String insert2(Model model, HttpSession session) {
        model.addAttribute("name", null);
        model.addAttribute("nameForm", new NameForm());
        historyService.sessionSet(session);
        return "words/insertMain";
    }

    //check insert or update
    @PostMapping("/inputContent")
    public String inputContent(@Validated NameForm nameForm, BindingResult bindingResult, 
                                Model model, HttpServletRequest request, HttpSession session) {
        String referer = request.getHeader("Referer");
        String[] list = referer.split("/");
        if(list[list.length-1].equals("inputContent")){
            referer = (String)session.getAttribute("referer");
        }
        // nameにブランク確認
        if (bindingResult.hasErrors()) {
            historyService.sessionSet(session,referer);
            return "words/insertMain";
        }
        //語句名が同じものが存在したらその語句の更新ページに遷移
        model.addAttribute("name", nameForm.getName());
        if (wordsService.checkByName(nameForm.getName()) != null){
            model.addAttribute("wordsList", wordsService.findByName(nameForm.getName()));
            model.addAttribute("error", "この語句はすでに登録されています。");
            historyService.sessionSet(session,referer,nameForm.getName());
            return "words/updatecontent";
        }
        //追加ページに遷移
        model.addAttribute("wordsForm", new WordsForm());
        historyService.sessionSet(session,referer,nameForm.getName());
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
        return "redirect:id/" + URLEncode(name);
    }

    //ID検索
    @PostMapping("/updateSearch")
    public String updateSearchId(@RequestParam String name, Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("name", name);
        if(wordsService.checkByName(name) == null){
            model.addAttribute("message", "その語句は存在しません");
            historyService.sessionSet(session);
            return "words/updateMain";
        }
        String referer = request.getHeader("Referer");
        model.addAttribute("wordsList", wordsService.findByName(name));
        model.addAttribute("error", null);
        historyService.sessionSet(session,referer,name);
        return "words/updatecontent";
    }

    //update(deleteID的な) エラー処理も
    @PostMapping("/updateComplete")
    public String updateComplete(@RequestParam String id,@RequestParam String content,@RequestParam("array[]") List<String> abbList,
                                    Model model, HttpSession session) throws Exception {
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
        return "redirect:id/" + URLEncode(name);
    }

    //削除依頼画面に遷移
    @PostMapping("/deleterequest")
    public String deleteRequest(@RequestParam String name, Model model, HttpServletRequest request, HttpSession session) throws Exception {
        model.addAttribute("name", name);
        String referer = request.getHeader("Referer");
        historyService.sessionSet(session,referer,name);
        return "words/deleteRequest";
    }

    //削除依頼を受け付けて単語ページに戻す
    @PostMapping("/deleterequestresult")
    public String deleteRequestResult(@RequestParam String name, @RequestParam String reason, Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        deleteRequestService.insert(new DeleteRequest(auth.getName(), name, reason));
        //return "redirect:id/"+ URLEncoder.encode(name.replace(" ", "_"), "UTF-8").replace("%2F", "/");
        return "redirect:id/" + URLEncode(name);
    }

    //削除依頼を取り消して単語ページに戻す
    @RequestMapping("/deleterequestcancel")
    public String deleteRequestCancel(@RequestParam String name, Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        deleteRequestService.delete(name, auth.getName());
        return "redirect:id/" + URLEncode(name);
    }

    @RequestMapping("/backURL")
    public String backURL(Model model, HttpSession session) throws Exception {
        historyService.sessionSet(session);
        String referer = (String)session.getAttribute("referer");
        return "redirect:" + referer;
    }

    @RequestMapping("/backwords")
    public String backWords(Model model, HttpSession session) throws Exception {
        String referer = (String)session.getAttribute("referer");
        List<String> splitList = Arrays.asList(referer.split("/"));
        String refererData = splitList.get(splitList.size()-1);
        historyService.sessionSet(session);
        if(refererData.equals("deleterequest")){
            String wordsName = (String)session.getAttribute("wordsName");
            return "redirect:/words/id/" + URLEncode(wordsName);
        }
        else if(refererData.equals("updateSearch")){
            String wordsName = (String)session.getAttribute("wordsName");
            return "redirect:/words/id/" + URLEncode(wordsName);
        }
        else if(refererData.equals("inputContent") || refererData.equals("insertComplete")){
            return "redirect:/words/insert";
        }
        return "redirect:" + referer;
    }

}