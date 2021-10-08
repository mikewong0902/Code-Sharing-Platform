package platform;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeSet;

@Service
public class CodeSnippetService {

    private final CodeSnippetRepository codeSnippetRepository;

    @Autowired
    public CodeSnippetService(CodeSnippetRepository codeSnippetRepository) {
        this.codeSnippetRepository = codeSnippetRepository;
    }

    public CodeSnippet save(CodeSnippet toSave) {
        return codeSnippetRepository.save(toSave);
    }

    public CodeSnippet findCodeSnippetById(String id) {
        return codeSnippetRepository.findCodeSnippetById(id);
    }

    public List<CodeSnippetDetails> findFirst10NonRestrictedSnippetByOrderByDateDesc() {
        return  codeSnippetRepository.findFirst10NonRestrictedSnippetByOrderByDateDesc();
    }

    @Modifying
    @Transactional
    public void deleteCodeSnippetById(String id) {
        codeSnippetRepository.deleteCodeSnippetById(id);
    }

    @Transactional
    public void decrementViews(String id) {
        codeSnippetRepository.decrementViews(id);
    }

}
