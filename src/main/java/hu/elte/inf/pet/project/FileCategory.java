package hu.elte.inf.pet.project;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum FileCategory {
    IMAGES(
        ext("png", "image/png"),
        ext("jpeg", "image/jpeg"),
        ext("jpg", "image/jpeg"),
        ext("bmp", "image/bmp"),
        ext("gif", "image/gif"),
        ext("tiff", "image/tiff"),
        ext("webp", "image/webp"),
        ext("svg", "image/svg+xml")
    ),
    VIDEOS(
        ext("mpeg", "video/mpeg"),
        ext("mpg", "video/mpeg"),
        ext("mkv", "video/x-matroska"),
        ext("avi", "video/x-msvideo"),
        ext("mp4", "video/mp4"),
        ext("mov", "video/quicktime"),
        ext("wmv", "video/x-ms-wmv"),
        ext("flv", "video/x-flv")
    ),
    DOCUMENTS(
        ext("pdf", "application/pdf"),
        ext("doc", "application/msword"),
        ext("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
        ext("txt", "text/plain"),
        ext("rtf", "application/rtf"),
        ext("odt", "application/vnd.oasis.opendocument.text"),
        ext("xls", "application/vnd.ms-excel"),
        ext("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    ),
    ARCHIVES(
        ext("zip", "application/zip"),
        ext("tar", "application/x-tar"),
        ext("gz", "application/gzip"),
        ext("rar", "application/vnd.rar"),
        ext("7z", "application/x-7z-compressed"),
        ext("bz2", "application/x-bzip2")
    ),
    AUDIO(
        ext("mp3", "audio/mpeg"),
        ext("wav", "audio/wav"),
        ext("flac", "audio/flac"),
        ext("aac", "audio/aac"),
        ext("ogg", "audio/ogg"),
        ext("wma", "audio/x-ms-wma")
    ),
    CODE(
        ext("java", "text/x-java-source"),
        ext("py", "text/x-python"),
        ext("js", "application/javascript"),
        ext("html", "text/html"),
        ext("css", "text/css"),
        ext("xml", "application/xml"),
        ext("json", "application/json")
    ),
    OTHER();

    public static class ExtensionInfo {
        private final String extension;
        private final String mimeType;

        public ExtensionInfo(String extension, String mimeType) {
            this.extension = extension;
            this.mimeType = mimeType;
        }

        public String getExtension() {
            return extension;
        }

        public String getMimeType() {
            return mimeType;
        }
    }

    private final Map<String, String> extensionToMimeType;

    FileCategory(ExtensionInfo... extensionInfos) {
        this.extensionToMimeType = new HashMap<>();
        for (ExtensionInfo info : extensionInfos) {
            this.extensionToMimeType.put(info.getExtension().toLowerCase(), info.getMimeType());
        }
    }

    private static ExtensionInfo ext(String extension, String mimeType) {
        return new ExtensionInfo(extension, mimeType);
    }

    public String[] getExtensions() {
        return extensionToMimeType.keySet().toArray(new String[0]);
    }

    public Optional<String> getMimeType(String extension) {
        return Optional.ofNullable(extensionToMimeType.get(extension.toLowerCase()));
    }

    public Map<String, String> getExtensionToMimeTypeMap() {
        return new HashMap<>(extensionToMimeType);
    }

    public static FileCategory fromExtension(String extension) {
        String lowerExt = extension.toLowerCase();
        for (FileCategory category : values()) {
            if (category.extensionToMimeType.containsKey(lowerExt)) {
                return category;
            }
        }
        return OTHER;
    }

    public static FileCategory fromMimeType(String mimeType){
        for (FileCategory category : values()) {
            if (category.extensionToMimeType.containsValue(mimeType)) {
                return category;
            }
        }
        return OTHER;
    }

    public static Optional<String> getMimeTypeForExtension(String extension) {
        FileCategory category = fromExtension(extension);
        return category.getMimeType(extension);
    }
}
