# 🔨 ImageInjector Build Guide

[![Back to README](https://img.shields.io/badge/←-Back%20to%20README-blue)](README.md)

---

## 📋 Prerequisites

### System Requirements
- **Operating System**: Windows 10+, macOS 10.14+, or Linux (Ubuntu 18.04+)
- **Java Development Kit**: OpenJDK 21 or Oracle JDK 21+
- **Memory**: Minimum 2GB RAM, 4GB+ recommended
- **Storage**: 500MB free space for build artifacts

### Required Tools
```bash
# Check Java version
java -version
# Should show: openjdk version "21.0.x" or higher

# Check Gradle (optional - wrapper included)
./gradlew --version
```

---

## 🚀 Quick Build

### 1. Clone Repository
```bash
git clone https://github.com/432Fowin/ImageInjector.git
cd ImageInjector
```

### 2. Build with Gradle Wrapper
```bash
# Windows
.\gradlew.bat shadowJar

# macOS/Linux
./gradlew shadowJar
```

### 3. Find Your JAR
```bash
# Output location:
build/libs/ImageInjector-1.0-SNAPSHOT-all.jar
```

### 4. Run
```bash
java -jar build/libs/ImageInjector-1.0-SNAPSHOT-all.jar
```

---

## 🔧 Detailed Build Process

### Project Structure
```
ImageInjector/
├── 📁 src/main/java/git/jar2dll/    # Source code
│   ├── 📄 Main.java                 # Application entry point
│   ├── 📁 gui/                      # User interface
│   ├── 📁 transformers/             # Bytecode transformers
│   └── 📁 utils/                    # Utility classes
├── 📁 src/main/resources/           # Resources (if any)
├── 📁 gradle/wrapper/               # Gradle wrapper files
├── 📄 build.gradle                  # Build configuration
├── 📄 settings.gradle               # Project settings
└── 📄 gradlew / gradlew.bat         # Gradle wrapper scripts
```

### Build Configuration Analysis

**build.gradle breakdown**:
```gradle
plugins {
    id 'java'                                    # Java compilation
    id 'application'                             # Application plugin
    id 'com.github.johnrengelman.shadow' version '8.1.1'  # Fat JAR creation
}

group = 'wtf.native'                            # Project group
version = '1.0-SNAPSHOT'                        # Version

application {
    mainClass = 'git.jar2dll.Main'              # Main class for 'run' task
}

repositories {
    mavenCentral()                              # Dependency repository
}

dependencies {
    implementation 'org.ow2.asm:asm:9.6'           # ASM core
    implementation 'org.ow2.asm:asm-commons:9.6'   # ASM commons
    implementation 'org.ow2.asm:asm-tree:9.6'      # ASM tree API
    implementation 'org.ow2.asm:asm-util:9.6'      # ASM utilities
}
```

---

## 🎯 Build Targets

### Standard Gradle Tasks
```bash
# Clean previous builds
./gradlew clean

# Compile Java sources
./gradlew compileJava

# Run tests (if any)
./gradlew test

# Create standard JAR
./gradlew jar

# Run application directly
./gradlew run

# Build everything
./gradlew build
```

### Shadow JAR Tasks
```bash
# Create fat JAR with all dependencies
./gradlew shadowJar

# Clean and build fat JAR
./gradlew clean shadowJar

# Show fat JAR contents
jar -tf build/libs/ImageInjector-1.0-SNAPSHOT-all.jar | head -20
```

### Custom Build Tasks
```bash
# Create distribution ZIP
./gradlew distZip

# Create distribution TAR
./gradlew distTar

# Install to local directory
./gradlew installDist
```

---

## 🔍 Build Verification

### Verify JAR Contents
```bash
# List main classes
jar -tf build/libs/ImageInjector-1.0-SNAPSHOT-all.jar | grep "git/jar2dll"

# Check manifest
jar -xf build/libs/ImageInjector-1.0-SNAPSHOT-all.jar META-INF/MANIFEST.MF
cat META-INF/MANIFEST.MF
```

### Test Execution
```bash
# Test JAR execution
java -jar build/libs/ImageInjector-1.0-SNAPSHOT-all.jar

# Test with specific JVM options
java -Xmx2G -jar build/libs/ImageInjector-1.0-SNAPSHOT-all.jar

# Test headless mode (for future CLI)
java -Djava.awt.headless=true -jar build/libs/ImageInjector-1.0-SNAPSHOT-all.jar
```

---

## 🐛 Troubleshooting Build Issues

### Common Problems

#### Problem: "JAVA_HOME not set"
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-21

# macOS/Linux
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
# or
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

#### Problem: "Permission denied: ./gradlew"
```bash
# Make wrapper executable
chmod +x gradlew
```

#### Problem: "Could not resolve dependencies"
```bash
# Check internet connection
# Try with --refresh-dependencies
./gradlew clean build --refresh-dependencies

# Use different repository if needed
# Edit build.gradle and add:
repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url 'https://repo1.maven.org/maven2' }
}
```

#### Problem: "OutOfMemoryError during build"
```bash
# Increase Gradle memory
export GRADLE_OPTS="-Xmx2g -XX:MaxMetaspaceSize=512m"

# Or create gradle.properties
echo "org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m" > gradle.properties
```

---

## 🚀 Advanced Build Options

### Custom Build Properties
Create `gradle.properties`:
```properties
# Build optimization
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.daemon=true

# JVM settings
org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m

# Custom version
version=1.0-custom
```

### Profile-Specific Builds
```bash
# Development build (with debug info)
./gradlew shadowJar -Pprofile=dev

# Production build (optimized)
./gradlew shadowJar -Pprofile=prod

# Add to build.gradle:
if (project.hasProperty('profile') && project.profile == 'prod') {
    jar {
        archiveClassifier = 'production'
    }
}
```

### Cross-Platform Builds
```bash
# Build for specific platform
./gradlew shadowJar -Pos.name=windows
./gradlew shadowJar -Pos.name=linux
./gradlew shadowJar -Pos.name=macos
```

---

## 📦 Distribution

### Creating Release Packages

**Manual Distribution**:
```bash
# Create distribution directory
mkdir -p dist/ImageInjector-1.0

# Copy JAR
cp build/libs/ImageInjector-1.0-SNAPSHOT-all.jar dist/ImageInjector-1.0/ImageInjector-1.0-java21-with-dependencies.jar

# Copy documentation
cp README*.md TUTORIAL*.md EXAMPLES.md dist/ImageInjector-1.0/

# Create sample files
mkdir dist/ImageInjector-1.0/samples
# Add sample images and JARs

# Create ZIP
cd dist
zip -r ImageInjector-1.0.zip ImageInjector-1.0/
```

**Automated Distribution Task**:
```gradle
// Add to build.gradle
task createDistribution(type: Zip) {
    archiveFileName = "ImageInjector-${version}.zip"
    destinationDirectory = file("$buildDir/distributions")
    
    from shadowJar
    from 'README.md', 'README_EN.md', 'README_RU.md'
    from 'TUTORIAL.md', 'TUTORIAL_RU.md'
    from 'EXAMPLES.md', 'TECHNICAL_SPECS.md'
    
    into("ImageInjector-${version}")
}

// Run with:
// ./gradlew createDistribution
```

---

## 🔄 Continuous Integration

### GitHub Actions Example
```yaml
# .github/workflows/build.yml
name: Build ImageInjector

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 21
      uses: actions/setup-java@v3
      with:
        java-version: '21'
        distribution: 'temurin'
    
    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: |
          ~/.gradle/caches
          ~/.gradle/wrapper
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build with Gradle
      run: ./gradlew clean shadowJar
    
    - name: Upload artifacts
      uses: actions/upload-artifact@v3
      with:
        name: ImageInjector-JAR
        path: build/libs/*.jar
```

---

## 📊 Build Performance

### Optimization Tips
```bash
# Use Gradle daemon
./gradlew --daemon shadowJar

# Enable parallel builds
./gradlew --parallel shadowJar

# Use build cache
./gradlew --build-cache shadowJar

# Skip tests for faster builds
./gradlew shadowJar -x test
```

### Build Time Benchmarks
```
Clean Build:          ~30-60 seconds
Incremental Build:    ~5-15 seconds
Shadow JAR only:      ~10-20 seconds
With tests:           ~45-90 seconds
```

---

## 🔐 Security Considerations

### Dependency Verification
```bash
# Check for known vulnerabilities
./gradlew dependencyCheckAnalyze

# Verify checksums
./gradlew --write-verification-metadata sha256 help
```

### Secure Build Environment
- Use official JDK distributions
- Verify Gradle wrapper checksums
- Keep dependencies updated
- Use dependency lock files for reproducible builds

---

## 📝 Build Documentation

### Generate Build Reports
```bash
# Dependency report
./gradlew dependencies > build-dependencies.txt

# Build scan (with --scan flag)
./gradlew build --scan

# Project report
./gradlew projects
```

---

**🎉 You're now ready to build ImageInjector from source!**

**For deployment and distribution, see the main [README](README.md) documentation.**
