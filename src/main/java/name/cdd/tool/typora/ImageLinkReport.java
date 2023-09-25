package name.cdd.tool.typora;

import name.cdd.tool.typora.pojo.ImageLink;
import name.cdd.tool.typora.pojo.LinkStatus;

import java.util.List;

public class ImageLinkReport {
    public void report(List<ImageLink> links) {
        System.out.println("==============冗余图片文件（图片存在，但无链接引用）===========");
        links.stream().filter(link -> link.getLinkStatus() == LinkStatus.FILE_WITH_NO_LINK)
                      .forEach(link -> System.out.println(link.getFile()));

        System.out.println("\n==============链接引用无效（图片文件不存在）===========");
        links.stream().filter(link -> link.getLinkStatus() == LinkStatus.LINK_TO_NO_FILE)
                .forEach(link -> System.out.println("链接字符串：" + link.getImageLinkInMd().getImageLink() + "\n所属文件：" + link.getImageLinkInMd().getMdFile() + "\n----------"));


        System.out.println("\n==============引用的图片文件存在于外部路径===========");
        links.stream().filter(link -> link.getLinkStatus() == LinkStatus.LINK_TO_OUTSIDE_FILE)
                .forEach(link -> System.out.println(link.getImageLinkInMd().getImageLink()));

//        links.stream().forEach(link -> System.out.println(link));
    }
}
