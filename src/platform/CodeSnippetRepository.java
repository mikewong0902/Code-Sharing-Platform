package platform;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, Integer> {
    CodeSnippet findCodeSnippetById(String id);
    void deleteCodeSnippetById(String id);
    @Query(nativeQuery = true)
    List<CodeSnippetDetails> findFirst10NonRestrictedSnippetByOrderByDateDesc();
    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE code_snippet cs set cs.views = cs.views - 1 WHERE cs.id = :id", nativeQuery = true)
    void decrementViews(@Param("id") String id);
}

