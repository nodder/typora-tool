package name.cdd.tool.typora;

import com.google.common.collect.Lists;
import name.cdd.tool.typora.pojo.ImageLink;
import name.cdd.tool.typora.pojo.ImageLinkInMd;
import name.cdd.tool.typora.file.FileUtils;

import java.io.File;
import java.util.List;

public class ImageLinkProcessor {
    private final String expectedPrefix;

    public ImageLinkProcessor(String expectedPrefix) {
        this.expectedPrefix = expectedPrefix;
    }

    public List<ImageLink> compareAndLink(List<File> imageFiles, List<ImageLinkInMd> imageLinkInMds) {
        List<ImageLink> imageLinks = Lists.newArrayList();

        for(ImageLinkInMd il : imageLinkInMds) {
            ImageLink imageLink = generateImageLinkFromMd(il, imageFiles);
            imageLinks.add(imageLink);
        }

        for(File f : imageFiles) {
            ImageLink imageLink = generateImageLinkFromFile(f, imageLinks);
            if(imageLink != null) {
                imageLinks.add(imageLink);
            }
        }

        return imageLinks;
    }

    private ImageLink generateImageLinkFromFile(File f, List<ImageLink> imageLinks) {
        boolean isAlreadyUsed = false;
        for (int j = 0; j < imageLinks.size(); j++) {
            if(FileUtils.fileEquals(f, imageLinks.get(j).getFile())) {
                isAlreadyUsed = true;
                break;
            }
        }

        if(isAlreadyUsed) {
            return null;
        }

        ImageLink imageLink = new ImageLink();
        imageLink.setFile(f);
        return imageLink;
    }

    private ImageLink generateImageLinkFromMd(ImageLinkInMd il, List<File> imageFiles) {
        ImageLink imageLink = new ImageLink();
        imageLink.setImageLinkInMd(il);

//        File imageLinkFile = il.getImageLinkFile();
//        if(imageLinkFile.exists()) {
//            if(FileUtils.filePathStartWith(imageLinkFile, expectedPrefix)) {
//                imageLink.setLinkExpected(true);
//            }
//        }

        for (int i = 0; i < imageFiles.size(); i++) {
            if(FileUtils.fileEquals(il.getImageLinkFile(), imageFiles.get(i))) {
                imageLink.setFile(imageFiles.get(i));
                break;
            }
        }
        return imageLink;
    }
}
