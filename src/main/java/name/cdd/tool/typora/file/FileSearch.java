package name.cdd.tool.typora.file;

import com.google.common.collect.Lists;
import name.cdd.tool.typora.pojo.SearchResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSearch {

    private final File baseFile;

    public FileSearch(File baseFile) {
        this.baseFile = baseFile;
    }

    public List<File> searchForFiles(String pattern) {
        List<File> result = Lists.newArrayList();
        searchForFile(this.baseFile, pattern, result);
        return result;
    }
    
    private void searchForFile(File baseFile, String pattern, List<File> result) {
        if(baseFile.isDirectory()) {
            File[] files = baseFile.listFiles();
            Arrays.stream(files).forEach(f -> searchForFile(f, pattern ,result));
        }

        if(baseFile.isFile() && baseFile.getName().endsWith(pattern)) {
            result.add(baseFile);
        }
    }

    public List<SearchResult> searchImageConntent() {
        List<SearchResult> result = Lists.newArrayList();
        
        try {
            List<String> lines = Files.readAllLines(this.baseFile.toPath());
            lines.stream().forEach(l -> result.addAll(parseToSearchResult(l)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<SearchResult> parseToSearchResult(String content) {
        List<SearchResult> result = Lists.newArrayList();
        parse1(content, result);
        parse2(content, result);
        return result;
    }

    private void parse2(String content, List<SearchResult> result) {
//        String regex = "\\((.*?\\.png)\\)";
        String regex = "\\(([^\\(\\)]*?\\.png)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            SearchResult temp = new SearchResult();
            temp.setContent(matcher.group(1));
            temp.setFile(baseFile);
            result.add(temp);
        }
    }

    private void parse1(String content, List<SearchResult> result) {
        String regex = "(<img src=\"(.*?\\.png)\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            SearchResult temp = new SearchResult();
            temp.setContent(matcher.group(2));
            temp.setFile(baseFile);
            result.add(temp);
        }
    }

//    public static void main(String[] args) {
////        String input = "Hello (../../图片/xxx.png) dfdfd (../../图片/xxx.png2)";
////        String regex = "\\((.*?\\.png)\\)";
//        String input = "xxx<imag src=\"../../links/xxx.png\" alt=\"xxximage\" style=\"yyyy;\"/> xxx<imag src=\"../../links/xxx2.png\" alt=\"xxximage\" style=\"yyyy;\"/>dddd";
//        String regex = "(<imag src=\"(.*?\\.png)\")";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            String result = matcher.group(2);
//            System.out.println("括号内的内容：" + result); // 输出：括号内的内容：World
//        }
//    }

}
