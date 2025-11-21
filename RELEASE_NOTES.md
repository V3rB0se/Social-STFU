# Release Notes - Social STFU v1.0

**Release Date:** November 21, 2025  
**Version:** 1.0 (Build 1)  
**Minimum Android:** 12 (API 31)  
**Target Android:** 15 (API 35)

---

## What's New

### Initial Release

Social STFU is a Material 3-powered Android app that gives you instant control over social media distractions. Disable all your social apps with a single tap - no terminal commands, no third-party tools needed.

---

## Features

### Core Functionality
- **One-Tap Toggle** - Disable or enable all social media apps instantly
- **Quick Settings Tile** - Toggle from your notification panel without opening the app
- **Smart Detection** - Automatically detects which apps are installed on your device
- **Root Integration** - Uses libsu for secure, reliable root command execution

### Supported Apps (13 Total)
- Discord
- Facebook
- Instagram
- Messenger
- Pinterest
- Reddit
- Snapchat
- Telegram
- Threads
- TikTok
- Tumblr
- Twitter/X
- WhatsApp

### Material 3 Design
- **Dynamic Colors** - Automatically adapts to your wallpaper on Android 12+
- **Dark Mode Support** - Seamless light and dark theme switching
- **Modern UI** - Clean, intuitive interface following Material Design 3 guidelines
- **Adaptive Icons** - All app icons automatically adjust for light/dark modes

### User Interface
- **Fixed Header** - Logo and app branding always visible while scrolling
- **Interactive Logo** - Click the header logo to toggle all apps at once
- **Live Status** - See which apps are enabled or disabled in real-time
- **Smooth Animations** - Material motion and transitions throughout

### Build System
- **Java 17** - Modern language features and optimizations
- **Gradle 8.11** - Latest stable build tools
- **AGP 8.7.3** - Full Android 15 (API 35) support
- **CI/CD Ready** - GitHub Actions workflow with automatic APK artifacts

---

## Technical Details

### Requirements
- **Android Version:** 12+ (API 31 minimum)
- **Root Access:** Required for system-level app control
- **Permissions:** 
  - Root access (via Magisk, KernelSU, or similar)
  - Quick Settings tile permission

### Architecture
- **Language:** Java 17
- **UI Framework:** AndroidX + Material Components 3
- **Root Library:** libsu 5.0.4
- **Min SDK:** 31 (Android 12)
- **Target SDK:** 35 (Android 15)

### What Makes This Release Special

1. **No Deprecated APIs** - All modern Android APIs, future-proof codebase
2. **Material 3 Throughout** - Not just themed, but properly implemented M3 design
3. **Efficient Root Usage** - Single shell session for all operations
4. **Lightweight** - Minimal dependencies, fast startup
5. **Open Source** - Full source code available on GitHub

---

## Installation

### Prerequisites
1. Rooted Android device (Magisk, KernelSU, etc.)
2. Android 12 or newer
3. Root access granted to the app

### Steps
1. Download the APK from GitHub Releases
2. Install the APK
3. Grant root access when prompted
4. Add Quick Settings tile (optional but recommended)
5. Start managing your social media apps!

---

## Usage

### Method 1: Main App
1. Open Social STFU
2. Toggle individual apps with switches
3. Or click the logo to toggle all apps at once

### Method 2: Quick Settings
1. Swipe down notification panel
2. Tap "Social STFU" tile
3. All apps toggle instantly

### How It Works
```
Enable:  pm enable <package.name>
Disable: pm disable-user --user 0 <package.name>
```

Disabled apps:
- Won't appear in launcher
- Can't run in background
- Stop all notifications
- Preserve all data and settings

---

## Known Limitations

1. **Root Required** - Cannot work without root access (this is by design)
2. **Android 12+** - Does not support older Android versions
3. **System Apps Only** - Only affects installed apps, not system services
4. **No Scheduling** - Manual toggle only (no timers or automation yet)

---

## Changelog

### v1.0 (Initial Release)
- Material 3 design implementation
- Dynamic color theming support
- Support for 13 popular social media apps
- Quick Settings tile integration
- Individual and bulk app toggle
- Interactive header with logo click
- Fixed header positioning
- Modern build system (Java 17, Gradle 8.11)
- GitHub Actions CI/CD with APK artifacts
- Comprehensive icon set with light/dark mode support

---

## What's Next?

Potential features for future releases (not promised):
- App scheduling (disable at specific times)
- Custom app list (add your own apps)
- Usage statistics
- Break reminders
- Exemption list (whitelist certain apps)
- Tasker integration

---

## Support & Feedback

This is a personal project, but feedback is welcome:
- **Issues:** [GitHub Issues](https://github.com/yourusername/Social-STFU/issues)
- **Source Code:** [GitHub Repository](https://github.com/yourusername/Social-STFU)

---

## Credits

- **Material 3 Design:** Google
- **Root Library:** TopJohnWu's libsu
- **Icons:** Icons8
- **Build System:** Android Gradle Plugin, Gradle

---

## License

This is a personal project. Use at your own risk.

---

## Security Note

This app requires root access to function. Root access gives apps full control over your device. Only grant root to apps you trust. Always download from official sources.

The app only executes `pm enable` and `pm disable-user` commands for the specific social media packages listed above. No data collection, no network requests, no hidden features.

---

**Thank you for using Social STFU!**

*Reclaim your peace of mind, one tap at a time.*
