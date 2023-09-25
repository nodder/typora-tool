package name.cdd.tool.typora.file;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileUtils {
    private FileUtils(){}

    public static String findAnotherFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if(dotIndex == -1) {
            return fileName + "_" + UUID.randomUUID();
        }

        return fileName.substring(0, dotIndex) + "_" + UUID.randomUUID() + fileName.substring(dotIndex);
    }

    public static boolean fileEquals(File f1, File f2) {
        if(f1 == null || f2 == null) {
            return false;
        }

        try {
            return f1.getCanonicalFile().equals(f2.getCanonicalFile());
        } catch (IOException e) {
            System.out.println("file equals error:" + f1 + "||" + f2);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean filePathStartWith(File f, String path) {
        if(f == null) {
            return false;
        }

        try {
            return f.getCanonicalFile().getPath().startsWith(path);
        } catch (IOException e) {
            System.out.println("filePathStartWith error:" + f);
            return false;
        }
    }

    public static List<File> findEmptyDirs(File dir) {
        List<File> result = Lists.newArrayList();
        findEmptyDirs(dir, result);

        return result;
    }

    private static void doRemove(File emptyDir) {
        emptyDir.delete();
        System.out.println();
    }

    private static void findEmptyDirs(File baseFile, List<File> result) {
        if(baseFile.isDirectory()) {
            File[] files = baseFile.listFiles();

            if(files.length == 0) {
                result.add(baseFile);
            } else {
                Arrays.stream(files).forEach(f -> findEmptyDirs(f, result));
            }
        }
    }
}
