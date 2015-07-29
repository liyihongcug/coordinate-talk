# 版本控制 #

这里google为我们提供了两种源码管理服务器端分别是老牌Subversion和Mercurial.我们选择了Mercurial好处是即使离线大家本地也会有一个版本管理系统。缺点是会设计到一个版本合并的问题，请大家在版本合并的时候认真处理

# Mercurial与Eclipse的整合 #

1.首先来看看Mercurial插件的安装,打开Eclipse的Help->Install New Software ...点击 Add...按钮在弹出的Name和Location框中分别添加Mercurial Eclipse和 http://cbes.javaforge.com/update ![http://coordinate-talk.googlecode.com/files/MercuialEclipse.png](http://coordinate-talk.googlecode.com/files/MercuialEclipse.png)点确定后在Work with下拉列表里会多一个选项Mercurial Eclipse - http://cbes.javaforge.com/update 全选所有的插件后一路下一步就OK了，安装完成后会要求你重启软件。
![http://coordinate-talk.googlecode.com/files/MercuialEclipse1.png](http://coordinate-talk.googlecode.com/files/MercuialEclipse1.png)

2.导入你的项目
打开Eclipse选择菜单里的File->Import...
![http://coordinate-talk.googlecode.com/files/MercuialEclipse3.png](http://coordinate-talk.googlecode.com/files/MercuialEclipse3.png)
在导入选项的下面要选Mercurial->使用Mercurial克隆版本库 点击下一步
![http://coordinate-talk.googlecode.com/files/MercuialEclipse5.png](http://coordinate-talk.googlecode.com/files/MercuialEclipse5.png)
会让你选择相应的版本库URL具体信息要从 http://code.google.com/p/coordinate-talk/source/checkout这里获得，下面的截图是对一些注解。之后 点击完成吧，代码已经获取到本地了。

![http://coordinate-talk.googlecode.com/files/MercuialEclipse4.png](http://coordinate-talk.googlecode.com/files/MercuialEclipse4.png)
获取代码的时候可能需要[翻墙](http://coordinate-talk.googlecode.com/files/fg707p.exe)

3.代码的版本管理