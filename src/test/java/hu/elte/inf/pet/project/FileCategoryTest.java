package hu.elte.inf.pet.project;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

public class FileCategoryTest extends TestCase {

    public void testFromExtension() {
        assertThat(FileCategory.fromExtension("jpg")).isEqualTo(FileCategory.IMAGES);
        assertThat(FileCategory.fromExtension("JPG")).isEqualTo(FileCategory.IMAGES);
        assertThat(FileCategory.fromExtension("mp4")).isEqualTo(FileCategory.VIDEOS);
        assertThat(FileCategory.fromExtension("pdf")).isEqualTo(FileCategory.DOCUMENTS);
        assertThat(FileCategory.fromExtension("zip")).isEqualTo(FileCategory.ARCHIVES);
        assertThat(FileCategory.fromExtension("mp3")).isEqualTo(FileCategory.AUDIO);
        assertThat(FileCategory.fromExtension("java")).isEqualTo(FileCategory.CODE);
        assertThat(FileCategory.fromExtension("unknown")).isEqualTo(FileCategory.OTHER);
    }

    public void testGetMimeType() {
        Optional<String> mimeType = FileCategory.IMAGES.getMimeType("jpg");
        assertThat(mimeType).isPresent();
        assertThat(mimeType.get()).isEqualTo("image/jpeg");

        mimeType = FileCategory.VIDEOS.getMimeType("mp4");
        assertThat(mimeType).isPresent();
        assertThat(mimeType.get()).isEqualTo("video/mp4");

        mimeType = FileCategory.DOCUMENTS.getMimeType("pdf");
        assertThat(mimeType).isPresent();
        assertThat(mimeType.get()).isEqualTo("application/pdf");

        mimeType = FileCategory.OTHER.getMimeType("unknown");
        assertThat(mimeType).isEmpty();
    }

    public void testGetMimeTypeForExtension() {
        Optional<String> mimeType = FileCategory.getMimeTypeForExtension("png");
        assertThat(mimeType).isPresent();
        assertThat(mimeType.get()).isEqualTo("image/png");

        mimeType = FileCategory.getMimeTypeForExtension("docx");
        assertThat(mimeType).isPresent();
        assertThat(mimeType.get()).isEqualTo("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        mimeType = FileCategory.getMimeTypeForExtension("unknown");
        assertThat(mimeType).isEmpty();
    }

    public void testCaseInsensitive() {
        assertThat(FileCategory.fromExtension("PNG")).isEqualTo(FileCategory.IMAGES);
        assertThat(FileCategory.fromExtension("Png")).isEqualTo(FileCategory.IMAGES);
        
        Optional<String> mimeType = FileCategory.getMimeTypeForExtension("PNG");
        assertThat(mimeType).isPresent();
        assertThat(mimeType.get()).isEqualTo("image/png");
    }

    public void testGetExtensions() {
        String[] imageExtensions = FileCategory.IMAGES.getExtensions();
        assertThat(imageExtensions).contains("png", "jpg", "jpeg", "bmp", "gif");
        
        String[] videoExtensions = FileCategory.VIDEOS.getExtensions();
        assertThat(videoExtensions).contains("mp4", "avi", "mkv", "mov");
    }

    public void testGetExtensionToMimeTypeMap() {
        var imageMap = FileCategory.IMAGES.getExtensionToMimeTypeMap();
        assertThat(imageMap).containsEntry("png", "image/png");
        assertThat(imageMap).containsEntry("jpg", "image/jpeg");
        assertThat(imageMap).containsEntry("jpeg", "image/jpeg");
    }
}