# CLAUDE.md
This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
This is a file management and processing tool (fprocessor) built in Java 11 using Maven. The application is designed to:
- Group and categorize files by type based on metadata
- Extract content from documents for searchable indexing
- Store file metadata and content in a database for efficient searching
- Provide a command-line interface with search capabilities
- Offer an API for integration with web/desktop applications

Images (expand current):
png, jpeg, jpg, bmp, gif, tiff, tif, webp, svg, ico, raw, cr2, nef, arw

Videos (expand current):
mpeg, mpg, mkv, avi, mp4, mov, wmv, flv, webm, m4v, 3gp, ogv

Documents (expand current):
pdf, doc, docx, txt, rtf, odt, pages, xls, xlsx, ppt, pptx, ods, odp

Archives (expand current):
zip, tar, targz, gz, rar, 7z, bz2, xz, lz, cab, deb, rpm

Additional Categories to Consider:
Audio:
mp3, wav, flac, aac, ogg, wma, m4a, opus, aiff

Code/Text:
java, py, js, html, css, xml, json, yaml, yml, sql, sh, bat, md

Executables:
exe, msi, dmg, pkg, deb, rpm, appimage, jar

Fonts:
ttf, otf, woff, woff2, eot
Other/Unknown:
(catch-all for unrecognized extensions)

## Build and Development Commands

### Building the project
```bash
mvn clean package
```

### Running the application
```bash
mvn exec:java -Dexec.mainClass="hu.elte.inf.pet.project.App"
```

### Running tests
```bash
mvn test
```

### Running a single test
```bash
mvn test -Dtest=AppTest
```

## Architecture

### Package Structure
- Base package: `hu.elte.inf.pet.project`
- Main entry point: `App.java` - handles command-line argument parsing with key=value format

### Key Technical Requirements
- **Java Version**: Java 11
- **Build Tool**: Maven
- **Performance**: Multi-threaded processing utilizing all CPU cores
- **Memory**: Memory-efficient implementation required
- **Database**: File metadata and extracted content storage
- **CLI**: Command-line interface with argument parsing (key=value format)

### File Categories
The application should categorize files into predefined groups:
- **Images**: png, jpeg, jpg, bmp
- **Videos**: mpeg, mpg, mkv, avi  
- **Documents**: pdf, doc, docx
- **Archives**: zip, tar, targz, gz

### Search Capabilities
- Free-text content search
- Date-based filtering
- File category/type filtering
- Combined search queries (e.g., "documents created in 2024 july")
- Natural language date parsing (e.g., "summer of 2025")

### Dependencies
- JUnit 3.8.1 (legacy version for testing)
- AssertJ 3.24.2 (fluent assertions for testing)
- Apache Tika 2.9.2 (file type detection and content extraction)
- SLF4J 2.0.9 (logging API)
- Logback 1.4.14 (logging implementation)

## Current Implementation Status
The project has the following implemented features:
- **App.java**: Main entry point with command-line argument parsing and SLF4J logging
- **FileHandler.java**: File processing with Tika-based content detection and categorization
- **FileCategory.java**: Enum with file extension to MIME type mapping and category classification
- **Logging**: SLF4J with Logback configuration (console + file appenders)
- **Tika Configuration**: Custom configuration to use only internal detectors (no external tools like ffmpeg)

### Logging Configuration
- Console logging for development with timestamp and log levels
- File logging with daily rotation (logs/fprocessor.log)
- Async file appender for performance
- Separate log levels for application vs. library code

### Tika Configuration
- Custom `tika-config.xml` excludes external parsers to avoid dependencies on system tools
- Built-in detectors only: TypeDetector, MagicDetector, ZipContainerDetector, OOXMLDetector
- Fallback to default Tika configuration if custom config fails to load


