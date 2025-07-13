# 📚 ImageInjector Tutorial & Examples

[![English](https://img.shields.io/badge/Language-English-blue)](TUTORIAL.md) | [![Русский](https://img.shields.io/badge/Язык-Русский-red)](TUTORIAL_RU.md)

---

## 🎯 Complete Step-by-Step Tutorial

### Prerequisites
- Java 21 or higher installed
- ImageInjector JAR file downloaded
- Sample images (PNG, JPG, GIF, BMP)
- Target JAR file for injection

---

## 📖 Tutorial 1: Basic ASCII Art Injection

### Step 1: Prepare Your Files
```
📁 tutorial-files/
├── 📄 target-app.jar          # Your target JAR file
├── 🖼️ logo.png               # Your image file
└── 📄 ImageInjector-1.0-java21-with-dependencies.jar
```

### Step 2: Launch ImageInjector
```bash
cd tutorial-files
java -jar ImageInjector-1.0-java21-with-dependencies.jar
```

### Step 3: Load Your JAR
1. Click **"Select JAR File"**
2. Choose `target-app.jar`
3. Wait for class loading (you'll see classes appear in the right panel)

### Step 4: Add Your Image
1. Click **"Add Images"**
2. Select `logo.png`
3. Image appears in the left panel

### Step 5: Configure Settings
- ✅ **"Use Original Image Size"** - for 1:1 pixel mapping
- OR uncheck and set **Custom ASCII Width**: 100-200 for good results

### Step 6: Select Classes
1. Choose which classes to inject into (or click "Select All")
2. **Tip**: Select fewer classes for testing

### Step 7: Process
1. Click **"Process JAR"**
2. Monitor progress in log area
3. Find output: `target-app_injected.jar`

---

## 📖 Tutorial 2: Multiple Images with Custom Settings

### Scenario: Adding Multiple Logos
You want to inject 3 different company logos into different classes.

### Files Needed:
```
📁 multi-image-tutorial/
├── 📄 my-application.jar
├── 🖼️ company-logo.png
├── 🖼️ product-logo.png
├── 🖼️ watermark.png
└── 📄 ImageInjector-1.0-java21-with-dependencies.jar
```

### Process:
1. **Load JAR**: Select `my-application.jar`
2. **Add Multiple Images**:
   - Click "Add Images"
   - Select all 3 PNG files at once (Ctrl+Click)
3. **Custom Settings**:
   - Uncheck "Use Original Image Size"
   - Set "Custom ASCII Width": 150
4. **Strategic Class Selection**:
   - Select main classes: `Main.class`, `Application.class`
   - Avoid utility classes for cleaner injection
5. **Process**: Images will be distributed across selected classes

### Expected Result:
- Each class gets ASCII art from different images
- Images cycle through: logo1 → logo2 → logo3 → logo1...

---

## 📖 Tutorial 3: Large Image Optimization

### Problem: Large Image (2000x1500px)
Large images can create massive ASCII art that bloats your JAR.

### Solution: Smart Sizing
```
Original Image: 2000x1500 = 3,000,000 pixels
ASCII Output: Could be 3MB+ of text!
```

### Best Practices:
1. **Use Custom Width**: Set to 200-400 pixels max
2. **Check Output Size**: Monitor log for size warnings
3. **Test Performance**: Large ASCII affects loading time

### Settings for Large Images:
- ❌ "Use Original Image Size" (unchecked)
- ⚙️ "Custom ASCII Width": 250
- 📊 Expected output: ~50KB per image

---

## 🔧 Advanced Techniques

### Technique 1: Selective Class Injection
```java
// Instead of injecting into ALL classes:
com/myapp/Main.class
com/myapp/util/StringUtils.class
com/myapp/util/FileUtils.class
com/myapp/core/Engine.class

// Inject only into key classes:
com/myapp/Main.class
com/myapp/core/Engine.class
```

**Benefits**:
- Smaller output JAR
- Faster processing
- Cleaner code structure

### Technique 2: Image Preparation
```bash
# Optimize images before injection
# Recommended sizes:
- Small logos: 100x100px
- Banners: 400x100px  
- Watermarks: 200x50px
```

### Technique 3: ASCII Art Preview
Before injection, preview your ASCII art:
```java
// Use ImageToAscii utility directly
String preview = ImageToAscii.convertImageToAscii(imageFile, 100);
System.out.println(preview);
```

---

## 🎨 Creative Use Cases

### Use Case 1: Version Watermarking
```java
// Generated in your classes:
public static final String build_v1_0_1 = "██╗   ██╗ ██╗    ██████╗     ██╗";
public static final String build_v1_0_2 = "██║   ██║███║   ██╔═████╗  ███║";
```

### Use Case 2: Team Signatures
Each team member adds their ASCII signature:
```java
public static final String dev_alice = "  ▄▀█ █░░ █ █▀▀ █▀▀";
public static final String dev_bob   = "  █▄█ █▄▄ █ █▄▄ ██▄";
```

### Use Case 3: Easter Eggs
Hidden ASCII art discoverable through decompilation:
```java
public static final String secret_msg = "  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░";
public static final String secret_art = "  ░░░██╗░░██╗██╗██████╗░██████╗░███████╗░░░";
```

---

## 🐛 Troubleshooting Common Issues

### Issue 1: "Classes not showing"
**Problem**: JAR loads but no classes appear
**Solutions**:
- Verify JAR is not corrupted: `jar -tf yourfile.jar`
- Check file permissions
- Ensure JAR contains .class files

### Issue 2: "ASCII art too large"
**Problem**: Generated ASCII creates huge files
**Solutions**:
- Use smaller custom width (50-200)
- Resize images before processing
- Select fewer classes

### Issue 3: "Processing fails"
**Problem**: Error during JAR processing
**Solutions**:
- Check available disk space
- Verify write permissions
- Try with smaller images first

### Issue 4: "Output JAR won't run"
**Problem**: Modified JAR doesn't execute
**Solutions**:
- Check if original JAR was signed (signatures removed)
- Verify main class still exists
- Test with simple JAR first

---

## 📊 Performance Tips

### Optimization Checklist:
- [ ] **Image Size**: Keep under 800x600px
- [ ] **ASCII Width**: Use 100-300 for balance
- [ ] **Class Count**: Select 5-20 classes max for testing
- [ ] **Memory**: Close other applications for large JARs
- [ ] **Storage**: Ensure 2x JAR size free space

### Benchmark Results:
```
Image Size    | ASCII Width | Processing Time | Output Size
100x100px     | 100         | 1-2 seconds     | +50KB
500x500px     | 200         | 3-5 seconds     | +200KB
1000x1000px   | 300         | 8-12 seconds    | +500KB
```

---

## 🎓 Best Practices Summary

### ✅ Do:
- Test with small images first
- Use meaningful image names
- Keep ASCII width reasonable (100-400)
- Select classes strategically
- Monitor log output for warnings

### ❌ Don't:
- Use extremely large images without resizing
- Inject into every class in large JARs
- Ignore memory warnings
- Skip testing output JAR functionality

---

## 🔗 Next Steps

1. **Try the basic tutorial** with a simple image
2. **Experiment with settings** to find optimal quality/size balance
3. **Test output JARs** thoroughly before deployment
4. **Share your results** and creative use cases!

---

## 📞 Need Help?

- 📖 **Documentation**: Check README files
- 🐛 **Issues**: Report on GitHub
- 💡 **Ideas**: Suggest new features
- 🤝 **Contribute**: Submit pull requests

---

**Happy ASCII Art Injecting! 🎨**
