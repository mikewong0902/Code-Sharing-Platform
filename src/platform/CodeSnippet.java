package platform;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@NamedNativeQuery(name = "CodeSnippet.findFirst10NonRestrictedSnippetByOrderByDateDesc",
        query = "SELECT * from code_snippet WHERE time <= 0 AND views <= 0 ORDER BY date Desc LIMIT 10",
        resultSetMapping = "Mapping.CodeSnippetDetails")
@SqlResultSetMapping(name = "Mapping.CodeSnippetDetails",
        classes = @ConstructorResult(targetClass = CodeSnippetDetails.class,
                columns = {@ColumnResult(name = "code"),
                        @ColumnResult(name = "time"),
                        @ColumnResult(name = "views"),
                        @ColumnResult(name = "date")}))
@Entity
public class CodeSnippet {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, length = 512)
    private String code;

    @Column
    private int time;

    @Column
    private int views;

    @Column
    private String date;

    @Column
    private boolean timeRestricted;

    @Column
    private boolean viewsRestricted;

    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

    public CodeSnippet() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public boolean isViewsRestricted() {
        return viewsRestricted;
    }

    public void setViewsRestricted(boolean viewsRestricted) {
        this.viewsRestricted = viewsRestricted;
    }

    public void decrementViews() {
        this.views -= 1;
    }

    public static String getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDateTime.format(formatter);
    }

    public void setResponseDateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CodeSnippet.DATE_FORMAT);
        DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern(CodeSnippetDetails.RESPONSE_DATE_FORMAT);

        LocalDateTime formattedDateTime = LocalDateTime.parse(getDate(), formatter);
        setDate(formattedDateTime.format(responseFormatter));
    }

    public int codeSnippetExpiresIn() {
        long currentTimeInEpochSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime creationTime = LocalDateTime.parse(getDate(), formatter);
        long expiryTimeInEpochSeconds = creationTime.toEpochSecond(ZoneOffset.ofHours(8)) + getTime();

        return Math.toIntExact(expiryTimeInEpochSeconds - currentTimeInEpochSeconds);
    }
}
