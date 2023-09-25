package name.cdd.tool.typora;

import com.google.common.collect.Lists;
import name.cdd.tool.typora.file.FileSearch;
import name.cdd.tool.typora.file.FileUtils;
import name.cdd.tool.typora.pojo.ImageLink;
import name.cdd.tool.typora.pojo.ImageLinkInMd;
import name.cdd.tool.typora.pojo.SearchResult;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

class Main {
    private static final String STRATEGY_HINT = "HINT";
    private static final String STRATEGY_DELETE = "DELETE";

    public static void main(String[]args){

//        String baseDir = "D:\\Temp\\typora_dir_test";
//        String imageDir = "D:\\Temp\\typora_dir_test\\图片";
//        String extraFileMoveDir = "D:\\Temp\\typora_extra_image";
//        String strategy = STRATEGY_DELETE;//HINT, DELETE

        String baseDir = args[0];
        String imageDir = args[1];
        String extraFileMoveDir = args[2];
        String strategy = args[3];

        List<File> imageFiles = new FileSearch(new File(imageDir)).searchForFiles(".png");

        List<File> mdFiles = new FileSearch(new File(baseDir)).searchForFiles(".md");
        List<SearchResult> fileAndImageContents = Lists.newArrayList();
        mdFiles.stream().forEach(f -> fileAndImageContents.addAll(new FileSearch(f).searchImageConntent()));

        List<ImageLinkInMd> imageLinkInMds = fileAndImageContents.stream().map(sr -> ImageLinkInMd.createFrom(sr)).collect(Collectors.toList());

        ImageLinkProcessor p = new ImageLinkProcessor(imageDir);
        List<ImageLink> links = p.compareAndLink(imageFiles, imageLinkInMds);

        new ImageLinkReport().report(links);

        switch (strategy){
            case STRATEGY_DELETE:
                System.out.println("\n========当前策略为：DELETE==============");
                new ImageLinkOps().moveFileWithNoLink(links, extraFileMoveDir);
                new ImageLinkOps().deleteEmptyDir(imageDir);
                break;
            case STRATEGY_HINT:
                System.out.println("\n========当前策略为：HINT==============");
                new ImageLinkHint().hintPossibleSituations(links);
                break;
        }


        //TODO 移动文件
        System.out.println("\n全部完成！");
    }
}