# 发生版本冲突 #
用户小Q和小P同时在服务器取了一份最新版本开始开发程序。2点的时候小Q向服务器推了一个版本，3点的时候小P也向服务器推了一处新版本，这时小P就会报版本冲突

![http://coordinate-talk.googlecode.com/files/VersionConflict01.png](http://coordinate-talk.googlecode.com/files/VersionConflict01.png)

下面我们来看看该怎么解决这个问题
# 解决版本冲突 #
小P同学很恼火怎么办？怎么办？这时只要我们先从服务器取回最新版本做一次拉的操作

![http://coordinate-talk.googlecode.com/files/VersionConflict02.png](http://coordinate-talk.googlecode.com/files/VersionConflict02.png)

另外在取新版本时一定记得要勾选Update after pull和Merge and, if there are no conflicts, commit after update选项，在更新指令后系统会自己更新，如果没有发生冲突系统会自动合并，并在合并后提交变更，但假如发生冲突我们就要手动来做合并了。

![http://coordinate-talk.googlecode.com/files/VersionConflict03.png](http://coordinate-talk.googlecode.com/files/VersionConflict03.png)

版本冲突发生时文件图标的变化

![http://coordinate-talk.googlecode.com/files/VersionConflict04.png](http://coordinate-talk.googlecode.com/files/VersionConflict04.png)