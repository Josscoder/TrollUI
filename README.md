<h1>TrollUI<img src="https://github.com/Josscoder/TrollUI/blob/production/brand/logo.png" height="64" width="64" align="left" alt=""></h1><br> 

[![](https://jitpack.io/v/Josscoder/TrollUI.svg)](https://jitpack.io/#Josscoder/TrollUI)

## 📙 Description
This is a plugin to troll users of your server, inspired by the /troll of SURVILAND a series created by Apixelados

This can also be used as a dependency, to execute the "traps" from another plugin.

## 🚩Features

- Command Permission
- Permission per Trap
- Command auto completer:
![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_1.png)
![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_2.png)

- Player Selector:
![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_7.png)

- Variety of traps:
  ![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_3.png)
  ![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_4.png)
  ![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_5.png)
  ![](https://github.com/Josscoder/TrollUI/blob/production/brand/Screenshot_6.png)


## 🌏 Add as dependency
Maven:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<depencies>
<dependency>
  <groupId>com.github.Josscoder</groupId>
  <artifactId>TrollUI</artifactId>
  <version>production-SNAPSHOT</version>
</dependency>
</depencies>
```

Gradle:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        implementation 'com.github.Josscoder:TrollUI:production-SNAPSHOT'
	}
```

## 📜 LICENSE

This game is licensed under the [Apache License 2.0](https://github.com/Josscoder/TrollUI/blob/production/LICENSE), this game was completely created by Josscoder (José Luciano Mejia Arias)