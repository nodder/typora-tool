package name.cdd.tool.typora.pojo;

import java.io.File;
import java.util.List;

public class ImageLink {
    private ImageLinkInMd imageLinkInMd;
    private File file;

    public ImageLinkInMd getImageLinkInMd() {
        return imageLinkInMd;
    }

    public void setImageLinkInMd(ImageLinkInMd imageLinkInMd) {
        this.imageLinkInMd = imageLinkInMd;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public LinkStatus getLinkStatus() {
        if(imageLinkInMd == null && file == null) return null;

        if(imageLinkInMd == null) return LinkStatus.FILE_WITH_NO_LINK;

        if(file == null) {
            if(imageLinkInMd.getImageLinkFile().exists()) {
                return LinkStatus.LINK_TO_OUTSIDE_FILE;
            }

            return LinkStatus.LINK_TO_NO_FILE;
        }

        return LinkStatus.LINKED;
    }


    @Override
    public String toString() {
        return "ImageLink{\n" +
                "imageLinkInMd=" + imageLinkInMd +
                ", \nfile=" + file +
                ", \ngetLinkStatus()=" + getLinkStatus() +
                "}\n";
    }
}
