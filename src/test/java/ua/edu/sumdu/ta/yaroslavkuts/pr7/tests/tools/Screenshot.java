package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools;

/**
 * Created by Ярослав on 27.11.2016.
 */
public class Screenshot {
    private String name;
    private String url;

    public Screenshot(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
