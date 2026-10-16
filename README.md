# Marketplace Only – Android prototype

An experimental Kotlin Android WebView shell with Marketplace and Messenger tabs.

## Build
Open this folder in Android Studio (recent version with JDK 17), allow Gradle sync, and run the `app` configuration on an Android device. To export an APK, choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Important limitations
- This is **source code**, not a prebuilt APK; build has not been verified in this environment.
- Meta may block Facebook/Messenger login in embedded WebView or change routes without notice.
- The URL filter applies to main-frame navigation only. Embedded feeds, Reels, modal dialogs, links rendered inside Marketplace, and secondary windows are **not guaranteed to be blocked**.
- Facebook login/security paths and root are allowed for sign-in, so this is **not** a strict, tamper-proof distraction blocker.
- Messenger may require an external browser or the native Messenger app to work reliably.
- No credentials are collected by this application; sign in only on authentic HTTPS Meta pages.
- This is an independent prototype, not affiliated with Meta.

## Next steps
Test login and messaging on your Android phone, harden the allowlist for actual observed navigation, and implement a custom-tab or supported browser-extension variant if WebView authentication is blocked.

## GitHub Actions: APK direkt via telefonen
Lägg projektets innehåll (inklusive den dolda `.github`-mappen) i roten av ett GitHub-repo på `main`. Öppna **Actions → Build Android APK → Run workflow**. När körningen är klar: öppna körningen, ladda ner artefakten **MarketplaceOnly-debug-apk**, packa upp ZIP-filen och installera `app-debug.apk` på Android. Detta är en debugversion för testning, inte en publiceringsklar app. GitHub-webbplatsen på Android kan kräva skrivbordsläge för att hantera uppladdningen.
