package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeSnippetDetails {
    private String code;
    private int time;
    private int views;
    private String date;

    public CodeSnippetDetails(String code, int time, int views, String date) {
        this.code = code;
        this.time = time;
        this.views = views;
        this.date = date;
        setResponseDateFormat();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static final String RESPONSE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public void setResponseDateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CodeSnippet.DATE_FORMAT);
        DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern(RESPONSE_DATE_FORMAT);

        LocalDateTime formattedDateTime = LocalDateTime.parse(getDate(), formatter);
        setDate(formattedDateTime.format(responseFormatter));
    }
}
