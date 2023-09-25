package name.cdd.tool.typora.pojo;

import java.io.File;
import java.io.IOException;

public class ImageLinkInMd {
    private File mdFile;
    private String imageLink;//文本中的链接

    public static ImageLinkInMd createFrom(SearchResult searchResult) {
        ImageLinkInMd result = new ImageLinkInMd();
        result.setImageLink(searchResult.getContent());
        result.setMdFile(searchResult.getFile());

        return result;
    }

    public File getMdFile() {
        return mdFile;
    }

    public void setMdFile(File mdFile) {
        this.mdFile = mdFile;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


    public File getImageLinkFile() {
        if(imageLink.contains("file:///")) {//绝对路径
            return new File(imageLink.substring("file:///".length()));
        }

        if(imageLink.contains(":")) {//绝对路径
            return new File(imageLink);
        }
        return new File(mdFile.getParent(), imageLink);
    }

    @Override
    public String toString() {
        return "ImageLinkInMd{" +
                "mdFile=" + mdFile +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }

    public static void main(String[] args) throws IOException {
        String imageLink = "file:///c:\\Users\\admin\\1.png";
        File f = new File(imageLink.substring("file:///".length()));


//        System.out.println(new File(imageLink).getAbsolutePath());
        System.out.println(f.getCanonicalFile());

    }
}
