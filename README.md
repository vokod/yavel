# yavel
**Y**et **A**nother Shared **P**reference **E**ncryption **L**ibrary (for Android)

[![](https://jitpack.io/v/vokod/yavel.svg)](https://jitpack.io/#vokod/yavel)

Yapel encrypts your Android shared preferences values.
If you access shared preferences via Yapel, all the values will be encrypted with AES256 in GCM mode with a key that is generated with and is stored in the Android keystore. Key material cannot be extracted from the keystore and only the app that created it, can access the key.

The benefits: pentesters and hackers won't be able to read and manipulate the preferences of your app on a rooted device. You can store security sensitive information in shared preferences.

## Features
- AES256 GCM encryption
- No need to set password
- Encryption key is stored in the Android Keystore. Key material never leaves the keystore, key cannot be extracted.

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
    com.github.vokod:yavel:rc1'
}
```

### Usage

```
 try {
  Yapel yavel = new Yapel("my_key_alias", this.getApplicationContext());
            
  yavel.setString("label1", "some string");
  yavel.setLong("label2", 1234);
            
  String readString = yavel.getString("label1", null);
  long readLong = yavel.getLong("label2", 0);
  
} catch (YapelException e) {
  e.printStackTrace();
}
```

### Compatibility

 minSdkVersion 23


### License


```
Copyright (C) 2017 Vokó Dániel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

### Author

Dániel Vokó (vokoda.reg@gmail.com)


