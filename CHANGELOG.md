# 📝 Changelog

All notable changes to ImageInjector will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.0] - 2024-01-XX (Current Release)

### 🎉 Initial Release

#### ✨ Added
- **Core Functionality**
  - Image to ASCII art conversion engine
  - JAR file loading and manipulation
  - Selective class injection system
  - Multi-format image support (PNG, JPG, GIF, BMP)

- **User Interface**
  - Modern Swing-based GUI
  - Drag & drop file selection
  - Real-time processing logs
  - Progress tracking with visual feedback
  - Intuitive layout with organized panels

- **Image Processing Features**
  - Smart image resizing algorithms
  - Original size (1:1 pixel mapping) option
  - Custom ASCII width configuration (50-1000px)
  - High-quality bicubic interpolation
  - Automatic optimization for large images

- **JAR Manipulation**
  - ObjectWeb ASM integration for bytecode modification
  - Non-destructive JAR processing
  - Resource preservation (non-class files)
  - Class selection interface
  - Batch processing capabilities

- **ASCII Art Generation**
  - Optimized character mapping: `"@#S%?*+;:,. "`
  - Grayscale conversion with ITU-R BT.709 luminance formula
  - Memory-efficient processing
  - Large ASCII art chunking (400,000 char limit)

- **Build System**
  - Gradle build configuration
  - Shadow plugin for fat JAR creation
  - Java 21 compatibility
  - Cross-platform support (Windows, macOS, Linux)

#### 🔧 Technical Features
- **Architecture**
  - Clean separation of concerns
  - Modular transformer system
  - Event-driven GUI with SwingWorker
  - Exception handling and error recovery

- **Performance Optimizations**
  - Lazy class loading
  - Background processing for UI responsiveness
  - Memory usage optimization
  - Automatic image size limiting

- **Security**
  - No network access required
  - Minimal file system permissions
  - Safe bytecode manipulation
  - Input validation and sanitization

#### 📚 Documentation
- Comprehensive README with dual language support (English/Russian)
- Step-by-step tutorials and examples
- Technical specifications and API documentation
- Troubleshooting guides
- Build instructions

#### 🎯 Supported Formats
- **Input Images**: PNG, JPG, JPEG, GIF, BMP
- **Input JARs**: Standard Java JAR files
- **Output**: Modified JAR with "_injected" suffix

---

## [Unreleased] - Future Versions

### 🚀 Planned Features

#### Version 1.1.0 (Planned)
- **Command Line Interface**
  - Batch processing from terminal
  - Scriptable operations
  - CI/CD integration support

- **Enhanced Image Processing**
  - Custom ASCII character sets
  - Color-to-ASCII mapping
  - Advanced dithering algorithms
  - Image preprocessing filters

#### Version 1.2.0 (Planned)
- **Advanced JAR Features**
  - JAR signing preservation
  - Manifest modification options
  - Resource injection capabilities
  - Multi-JAR batch processing

- **User Experience Improvements**
  - Drag & drop for images and JARs
  - Preview window for ASCII art
  - Undo/redo functionality
  - Settings persistence

#### Version 1.3.0 (Planned)
- **Plugin Architecture**
  - Custom transformer plugins
  - Third-party integrations
  - Extension API
  - Plugin marketplace

- **Performance Enhancements**
  - Multi-threaded processing
  - Streaming for large files
  - Memory usage optimization
  - Progress estimation improvements

#### Version 2.0.0 (Future)
- **Modern UI Framework**
  - JavaFX migration
  - Dark/light theme support
  - Responsive design
  - Touch-friendly interface

- **Cloud Integration**
  - Online ASCII art gallery
  - Sharing capabilities
  - Collaborative features
  - Version control integration

---

## 🐛 Known Issues

### Current Limitations
- **Memory Usage**: Large images (>10MB) may cause memory issues
- **JAR Signatures**: Original signatures are removed during processing
- **Single Threading**: GUI operations are single-threaded
- **File Size**: Very large JARs (>100MB) may process slowly

### Workarounds
- Use image resizing for large files
- Re-sign JARs after processing if needed
- Process large JARs in smaller batches
- Close other applications for memory-intensive operations

---

## 🔄 Migration Guide

### From Development to v1.0.0
This is the initial release, no migration needed.

### Future Migration Notes
- Configuration file format changes will be documented here
- API breaking changes will include migration examples
- Deprecated features will have removal timelines

---

## 🤝 Contributing

### How to Contribute
1. **Bug Reports**: Use GitHub Issues with detailed reproduction steps
2. **Feature Requests**: Describe use cases and expected behavior
3. **Code Contributions**: Follow the coding standards and include tests
4. **Documentation**: Help improve guides and examples

### Development Setup
```bash
git clone https://github.com/432Fowin/ImageInjector.git
cd ImageInjector
./gradlew build
```

### Testing
```bash
./gradlew test           # Run unit tests
./gradlew integrationTest # Run integration tests (if available)
```

---

## 📊 Statistics

### v1.0.0 Metrics
- **Lines of Code**: ~1,500 Java LOC
- **Classes**: 7 main classes
- **Dependencies**: 4 ASM libraries
- **Supported Formats**: 5 image formats
- **Documentation**: 4 comprehensive guides

---

## 🙏 Acknowledgments

### Libraries Used
- **ObjectWeb ASM**: Bytecode manipulation framework
- **Java Swing**: GUI framework
- **Java AWT**: Image processing capabilities
- **Gradle**: Build automation

### Inspiration
- ASCII art generation techniques
- Java bytecode manipulation best practices
- Modern GUI design principles

---

## 📄 License

This project is open source. See [LICENSE](LICENSE) file for details.

---

## 🔗 Links

- **Repository**: [GitHub](https://github.com/432Fowin/ImageInjector)
- **Issues**: [Bug Reports](https://github.com/432Fowin/ImageInjector/issues)
- **Releases**: [Download](https://github.com/432Fowin/ImageInjector/releases)
- **Documentation**: [Wiki](https://github.com/432Fowin/ImageInjector/wiki)

---

**Last Updated**: 2024-01-XX  
**Maintainer**: [432Fowin](https://github.com/432Fowin)
