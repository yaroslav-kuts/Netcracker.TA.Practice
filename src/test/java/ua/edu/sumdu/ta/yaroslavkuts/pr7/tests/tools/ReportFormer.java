package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ярослав on 27.11.2016.
 */
public class ReportFormer {

    private static final String REP_START = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<ul>";
    private static final String REP_END = "</ul>\n" +
            "</body>\n" +
            "</html>";
    private static int numOfSteps;
    private File report;

    public ReportFormer(String testCaseName) {
        report = new File("reports/" + testCaseName + ".html");
        buildHtml(REP_START);
    }

    public void buildStep(String stepName, String imagePath) {
        numOfSteps++;
        String step = "<li>" + stepName + "<br>" + "<img src='" + imagePath + "' height='450' width='600'/>" + "</li>";
        buildHtml(step);
    }

    public void close() {
        buildHtml(REP_END);
    }

    private void buildHtml(String content) {
        try {
            FileUtils.writeStringToFile(report, content, "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumOfSteps() {
        return numOfSteps;
    }
}
