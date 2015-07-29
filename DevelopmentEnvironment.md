先来看看你是否适合开发,要是只想做个本例中的Hello World!那你不用会任何知识，如果想做个像样点的程序来至少要会点类java的面向对象的语言。还有就是时间和兴趣。

上面说了android的程序是由java开发的那jdk肯定是第一个要下载的。请到 http://www.oracle.com/technetwork/java/javase/downloads/index.html 下载最后新版本的JDK并安装，这事很简单不说了。有了JDK下一个要用的就是IDE了，官方推荐使用Eclipse那我们就去官网 http://www.eclipse.org/downloads/ 下载最新版本的Eclipse IDE for Java Developers解压就可以使用了。

下面说说Android的SDK官方网站是<a href='http://developer.android.com/'><a href='http://developer.android.com/'>http://developer.android.com/</a></a>如果你打不开这个网站，那么恭喜你，你99%在中国大陆....杯具的GFW...我在这里帮大家做了个中转站点<a href='http://developer.android.guohai.org/'><a href='http://developer.android.guohai.org/'>http://developer.android.guohai.org/</a></a>.希望能给大家带来帮助。目前最新版本的SDK是r9官方网站推荐使用exe版本那我们就也下exe的吧。回来后安装后会自动帮你执行Android SDK 该装的都装上就行了，不过至少要装2.1和2.2两个SDK过程可能会很漫长很痛苦多等等吧。SDK装完了我们就可以启动Eclipse了。

Eclipse启动后，我们第一步要做的就是安装Android Development Tools(下面我们都叫ADT)，我们选择Help->Install New Software...->Add...在Name里填写ADT在Location里填写https://dl-ssl.google.com/android/eclipse/ 确定后会在Work with下拉列表里找到ADT在下面的Developer Tools上打钩一路下一步就OK了。
<a href='http://blog.guohai.org/wp-content/uploads/2011/02/adtinstall.png'><img src='http://blog.guohai.org/wp-content/uploads/2011/02/adtinstall-300x291.png' alt='' height='291' width='300' title='adtinstall' /></a>
之后 软件会要求你重启，重启后还要配置一下ADT插件Windows->Preferences->Android在SDK Location里填写刚刚你下载好的SDK的目录。确定后我们就可以自己的第一个HelloWorld程序了。

> 今天有点晚了，明天我们继续.....

在创建虚拟机的时候可能会报如下错误
[2011-02-17 16:32:03 - SDK Manager] Error: Target location is not inside the SDK.

请修改系统变量
ANDROID\_SDK\_HOME