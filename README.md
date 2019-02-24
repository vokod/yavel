# yavel
**Y**et **A**nother (primitive type) **V**alue **E**ncryption **L**ibrary (for Android)

[![](https://jitpack.io/v/vokod/yavel.svg)](https://jitpack.io/#vokod/yavel)

Yavel encrypts primitive types for secure storage (in local or remote databases). Encrypted values are stored in Strings.
If you store values via Yavel, all the values will be encrypted with AES256 in GCM mode with a key that is generated with and is stored in the Android keystore. Key material cannot be extracted from the keystore and only the app that created it can access the key.

The benefits: pentesters and hackers won't be able to read and manipulate the values of your data even on a rooted device or a compromised remote server. You can store security sensitive information in local or remote databases.

## Features
- AES256 GCM encryption
- No need to set password
- Encryption key is stored in the Android Keystore. Key material never leaves the keystore, key cannot be extracted.
- Reasonably fast

## Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then, add the library to your module `build.gradle`
```gradle
dependencies {
    implementation com.github.vokod:yavel:LATEST'
}
```

### Usage

```
 String encryptedString = yavel.encryptString(str);
 String encryptedInt = yavel.encryptInt(value);
 String encryptedBoolean = yavel.encryptBoolean(true);
 String encryptedLong = yavel.encryptLong(value);
 String encryptedFloat = yavel.encryptFloat(value);
 String encryptedDouble = yavel.encryptDouble(value);
```

### Compatibility

 minSdkVersion 23


