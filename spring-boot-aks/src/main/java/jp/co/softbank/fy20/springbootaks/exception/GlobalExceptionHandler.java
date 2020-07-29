package jp.co.softbank.fy20.springbootaks.exception;

import org.springframework.dao.DuplicateKeyException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.co.softbank.fy20.springbootaks.service.WordsService;

@ControllerAdvice
public class GlobalExceptionHandler {
    /*private final WordsService wordsService;

    // EmployeeServiceをDIする（@Autowiredは省略）
    public GlobalExceptionHandler(WordsService wordsService) {
        this.wordsService = wordsService;
    }*/

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        String message = e.getMessage();
        model.addAttribute("message", message);
        return "redirect:/";
    }

    /*
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateKeyException(DuplicateKeyException e, HttpSession session) {
        String message = "この語句は登録されています。";
        //model.addAttribute("message", message);
        String storedName = (String) session.getAttribute("name"); //words or name
        List<WordsByAbb> wordsList = wordsService.findByName(storedName);
        

        return "words/id/"+storedName;
    }*/
    

    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateKeyException(DuplicateKeyException e, Model model) {
        String message = "この語句は登録されています。";
        model.addAttribute("message", message);
        return "redirect:/";
    }
}