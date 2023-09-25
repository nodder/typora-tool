package name.cdd.tool.typora;

import name.cdd.tool.typora.pojo.ImageLink;
import name.cdd.tool.typora.pojo.ImageLinkInMd;
import name.cdd.tool.typora.pojo.LinkStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImageLinkHint {
    public void hintPossibleSituations(List<ImageLink> links) {
        System.out.println("==============开始检查图片与链接没有匹配成功，但文件名相同的列表，请检查是否需要修改引用地址 =====");

        List<File> existingImageFiles = new ArrayList<>(links.stream()
                          .map(l -> l.getFile())
                          .filter(Objects::nonNull)
                          .collect(Collectors.toSet()));

        List<ImageLinkInMd> linksWithoutFile = links.stream()
                                .filter(l -> l.getLinkStatus() == LinkStatus.LINK_TO_NO_FILE)
                                .map(l -> l.getImageLinkInMd())
                                .collect(Collectors.toList());


        for(ImageLinkInMd link: linksWithoutFile) {
            for(File existingImage: existingImageFiles) {
                if(link.getImageLinkFile().getName().equals(existingImage.getName())) {
                    System.out.println("链接字符串：" + link.getImageLink()
                                   + "\n所属文件：" + link.getMdFile()
                                   + "\n图片文件：" + existingImage.getAbsolutePath()
                                   + "\n----------");
                }
            }
        }

    }
}
