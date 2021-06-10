<p align="center">
  <img src="https://i.imgur.com/4FyinoS.png" alt="Banner" />
</p>

[![Code Quality](https://www.codefactor.io/repository/github/iamgio/chorus/badge)](https://www.codefactor.io/repository/github/iamgio/chorus) ![Downloads](https://img.shields.io/github/downloads/iamgio/chorus/total.svg) [![Release](https://img.shields.io/github/release/iamgio/chorus.svg)](https://chorusmc.org) [![GitHub license](https://img.shields.io/github/license/iAmGio/chorus)](https://github.com/iAmGio/chorus/blob/master/LICENSE)

## What is Chorus?
Chorus is an easy-to-use and lightweight [YAML](http://yaml.org/) editor, created for anyone who works on [Spigot](https://spigotmc.org)-based Minecraft servers.  
Thanks to its features, Chorus is perfect to edit configurations quickly and easily. 

## Why Chorus?
**Chorus has a lot of features that will help you edit your configuration with extreme ease:**   
   
   
* Edit **remote files** (SFTP and FTP);

![SFTP](https://i.imgur.com/hbDH51r.png)

* Show **previews** of **chat**, **title**, **scoreboard**, **item**, **GUI**, **sign**, **action bar**, **boss bar**, **mob bar**, **MOTD** and **animated text**;   
   
![Chat preview](https://i.imgur.com/wHnDKvA.png)   

![GUI preview](https://i.imgur.com/ENLn206.png)   

![Animated text preview](https://i.imgur.com/RkKJMPm.gif)   

* Use **autocompletion** to insert elements quicker;
   
![Autocompletion](https://i.imgur.com/PvvNhs5.png)
   
* Quickly show colored text;   
    
![Quick preview](https://i.imgur.com/N2MrEMD.png)   
   
* Insert colored text via an **editor**;  
    
![Inline editor](https://i.imgur.com/coRGKGt.png)   
   
* Insert **items** by name/ID;
* Insert **particles** by name;
* Insert **effects** by name/ID;
* Insert **sounds** by name;
* Insert **entities** by name;
* Insert **enchantments** by name/ID;   
   
![Insert item](https://i.imgur.com/TbRM1L9.png)   
   
* Convert milliseconds, seconds, minutes, hours, days to **ticks** and vice-versa;
      
![Tick conversion](https://i.imgur.com/o8pQhmJ.png)   

* Fetch information of items, effects, entities, enchantments;  
**Pro tip:** CTRL + click the game element;
   
![Fetch information](https://i.imgur.com/Rak3cUa.png)   

* Add **variables** that are applied during previews;   
   
![Add variables](https://i.imgur.com/Fg0FC69.png)  
 
* View complete path of any key by hovering it;   
* **Auto-save** your files (with customizable delays);
* Replace **TABs** with **spaces**;

## Writing your own add-on
Since 1.3, Chorus supports custom **JavaScript add-ons**.   
Check out the official [wiki](https://github.com/iAmGio/chorus/wiki) for API documentation.  
Although an official marketplace is not ready yet, you can publish your add-ons by submitting your files to the [add-ons repository](https://github.com/iAmGio/chorus-addons) following the instructions given in its README.  
In order to load an add-on, place the .js file inside the `chorus/addons` folder.

## Creating your own theme
Chorus supports custom themes. If you want to create your own, create a folder into chorus/themes named as your theme's name.   
In this folder, you have to create the following files:
* **{name}.css**, to style the main view;    
* **{name}-highlight.css** to style keywords;    
* **{name}-settings.css** to style setting view;    

_Note: {name} must be the same as your folder's name._

To see the structure of a style file, take a look at this theme:   
* [Main](https://github.com/iAmGio/chorus/blob/master/src/assets/styles/light.css)       
* [Highlight](https://github.com/iAmGio/chorus/blob/master/src/assets/styles/light-highlight.css)    
* [Settings](https://github.com/iAmGio/chorus/blob/master/src/assets/styles/light-settings.css)

If you want to make it public, you can add it to the official [themes repository](https://github.com/iAmGio/chorus-themes) following [these](https://github.com/iAmGio/chorus-themes/blob/master/README.md) guidelines.

## Downloading
Chorus is downloadable on [chorusmc.org](https://chorusmc.org).  
Older versions can be found [here](https://github.com/iAmGio/chorus/releases).  
Make sure you have Java 8 or above installed.

#### Windows 
Download chorus-X.X.X.exe, move it to the desired installation folder and run it.  

#### macOS
Download chorus-X.X.X.app.zip, extract the .app file and open it.

#### Linux
Download chorus-X.X.X.jar, move it to the desired installation folder and run it.  

### Donating
I (yes, Chorus is mantained by a single guy) worked so hard on this project.  
If you like this software, or just want to support me, I'd really enjoy donations.     
By donating, you'll be added to the 'Donators List' accessible from the program.

### License
Chorus and its source code are under GPL-3 license. For further information, check [LICENSE](https://github.com/iAmGio/chorus/blob/master/LICENSE).  
Unauthorized distribution and/or sale are prohibited.