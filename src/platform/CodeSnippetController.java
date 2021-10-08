package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
public class CodeSnippetController {

    @Autowired
    private CodeSnippetService codeSnippetService;

    @RequestMapping(value = "/api/code/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, String> createCodeSnippet(@RequestBody CodeSnippet codeSnippet) {
        String currentDate = codeSnippet.getCurrentDate();
        codeSnippet.setDate(currentDate);
        if (codeSnippet.getTime() > 0) {
            codeSnippet.setTimeRestricted(true);
        }
        if (codeSnippet.getViews() > 0) {
            codeSnippet.setViewsRestricted(true);
        }
        CodeSnippet codeSnippetCreate = codeSnippetService.save(codeSnippet);

        HashMap<String, String> codeSnippetIdMap = new HashMap<>();
        codeSnippetIdMap.put("id", String.valueOf(codeSnippetCreate.getId()));
        return codeSnippetIdMap;
    }

    @GetMapping("/api/code/{id}")
    @ResponseBody
    public HashMap<String, Object> getCodeSnippet(@PathVariable String id) {
        CodeSnippet codeSnippet = codeSnippetService.findCodeSnippetById(id);

        if (codeSnippet != null) {
            int codeExpiresInSecs = codeSnippet.codeSnippetExpiresIn();
            if (codeSnippet.isViewsRestricted() && codeSnippet.getViews() >= 0) {
                if (codeSnippet.getViews() - 1 < 0) {
                    codeSnippetService.deleteCodeSnippetById(id);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
                } else {
                    codeSnippet.decrementViews();
                }
            }
            if (codeSnippet.getTime() > 0) {
                if (codeExpiresInSecs <= 0) {
                    codeSnippetService.deleteCodeSnippetById(id);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
                }
            }
            codeSnippetService.save(codeSnippet);
            codeSnippet.setResponseDateFormat();

            HashMap<String, Object> codeSnippetHashMap = new HashMap<>();
            codeSnippetHashMap.put("code", codeSnippet.getCode());
            codeSnippetHashMap.put("date", codeSnippet.getDate());
            codeSnippetHashMap.put("time", codeExpiresInSecs);
            if (codeSnippet.getTime() <= 0) {
                codeSnippetHashMap.put("time", codeSnippet.getTime());
            }
            codeSnippetHashMap.put("views", codeSnippet.getViews());
            return codeSnippetHashMap;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public List<CodeSnippetDetails> get10RecentCodeSnippet() {
        return codeSnippetService.findFirst10NonRestrictedSnippetByOrderByDateDesc();
    }

    @GetMapping("/code/new")
    public String createCodeSnippetPage() {
        return "CodeSubmission";
    }

    @GetMapping("/code/{id}")
    public String getCodeSnippetPage(@PathVariable String id, Model model) {
        CodeSnippet codeSnippet = codeSnippetService.findCodeSnippetById(id);
        if (codeSnippet != null) {
            int codeExpiresInSecs = codeSnippet.codeSnippetExpiresIn();
            if (codeSnippet.isViewsRestricted() && codeSnippet.getViews() >= 0) {
                if (codeSnippet.getViews() - 1 < 0) {
                    codeSnippetService.deleteCodeSnippetById(id);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
                } else {
                    codeSnippet.decrementViews();
                }
            }
            if (codeSnippet.getTime() > 0) {
                if (codeExpiresInSecs <= 0) {
                    codeSnippetService.deleteCodeSnippetById(id);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
                }
            }
            codeSnippetService.save(codeSnippet);
            if (codeSnippet.getTime() > 0) {
                codeSnippet.setTime(codeExpiresInSecs);
            }
            codeSnippet.setResponseDateFormat();

            model.addAttribute("codeSnippet", codeSnippet);
            return "singlecodesnippet";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    @GetMapping("/code/latest")
    public String get10RecentCodeSnippetPage(Model model) {
        List codeSnippets = codeSnippetService.findFirst10NonRestrictedSnippetByOrderByDateDesc();
        model.addAttribute("codeSnippets", codeSnippets);
        return "codesnippet";
    }
}
