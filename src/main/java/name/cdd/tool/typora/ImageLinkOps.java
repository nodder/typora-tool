package name.cdd.tool.typora;

import com.google.common.io.Files;
import name.cdd.tool.typora.file.FileUtils;
import name.cdd.tool.typora.pojo.ImageLink;
import name.cdd.tool.typora.pojo.LinkStatus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageLinkOps {
    public void moveFileWithNoLink(List<ImageLink> links, String toBaseDir) {
        System.out.println("\n======开始移动冗余的图片文件=================");
        links.stream().filter(link -> link.getLinkStatus() == LinkStatus.FILE_WITH_NO_LINK)
                      .forEach(link -> moveFileWithNoLink(link, toBaseDir));
    }

    private void moveFileWithNoLink(ImageLink link, String toBaseDir) {
        File from = link.getFile();

        File toDir = new File(toBaseDir, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        toDir.mkdirs();
        File toFile = new File(toDir, from.getName());
        if(toFile.exists()) {
            toFile =  new File(toDir, FileUtils.findAnotherFileName(toFile.getName()));
        }

        try {
            Files.move(from, toFile);
            System.out.println("已移动冗余图片文件。原文件：" + from + "| 目标文件：" + toFile);
        } catch (IOException e) {
            System.out.println("移动冗余图片文件失败，原文件：" + from + "| 目标文件：" + toFile);
            e.printStackTrace();
        }
    }

    public void deleteEmptyDir(String dir) {
        System.out.println("\n======开始删除空的图片文件夹=================");
        doDeleteEmptyDir(dir);
    }

    private void doDeleteEmptyDir(String dir) {
        List<File> emptyDirs = FileUtils.findEmptyDirs(new File(dir));
        emptyDirs.stream().forEach(emptyDir -> doDelete(emptyDir));
        if(emptyDirs.size() > 0) {
            doDeleteEmptyDir(dir);//解决删除子的空文件夹后，父的文件夹可能变为空文件的问题
        }
    }

    private void doDelete(File dir) {
        try {
            if(dir.delete()) {
                System.out.println("已删除目录: " + dir);
            } else {
                System.out.println("删除目录失败：" + dir);
            }
        } catch (Exception e) {
            System.out.println("删除目录发生异常：" + dir);
            e.printStackTrace();
        }

    }
}
