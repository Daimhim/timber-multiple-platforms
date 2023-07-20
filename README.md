# timber-multiple-platforms
跨平台log框架
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.Daimhim.timber-multiple-platforms:timber:1.0.3'
          // or
          implementation 'com.github.Daimhim.timber-multiple-platforms:timber-android:1.0.3'
	}
```

### 项目在此基础上开发：
https://github.com/JakeWharton/timber
