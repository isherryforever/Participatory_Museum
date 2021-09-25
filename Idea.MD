

### 下一步要做

拿到测试接口，post/get测试。

简单美化下直接交作业。





**大问题：**

1分钟之后会闪退，偶然发生------不常出现，不改了



### 开发记录

#### 2021.09.20

实现了canvas demo，可以根据数组画出轨迹，并在末尾加上儿童头像。

<img src="https://gitee.com/yuyang201907/Imags/raw/master/image-20210925105347799.png" alt="image-20210925105347799" style="zoom:50%;" />



#### 2021.09.18

根据mycanvas自己写一个canvas的画图demo，能根据数组自己绘制轨迹。

```java
//大致思路如下
float last_x=0,last_y=0,x,y;
(x,y)=get(x,y);//从服务器获取
while(x>0&&y>0)
{
    canvas.drawLine(last_x, last_y, x, y, paint);//轨迹
    last_x=x;
    last_y=y;
    (x,y)=get(x,y);
}
```

<img src="https://gitee.com/yuyang201907/Imags/raw/master/image-20210917224244164.png" alt="image-20210917224244164" style="zoom: 50%;" />

> 硬件接口返回的x和y在0-100，所以通过运算使其显示在居中位置。



#### 2021.09.17

写两个页面

页面一：实时在地图中显示孩子的当前位置，用画布和精灵画出轨迹。

页面二：实时在地图中显示当前人流量

NFC功能：实现自动登录

> 想加一个管理员端，除了基本页面外多一个注册账号、注销账号和写入NFC的功能

canvas画布模拟轨迹移动

案例一：https://github1s.com/mxdldev/android-amap-track-move

经测试，不同手机的画布是按比例缩放的，所以在自己手机上做的APP在其他手机一样使用。



#### 2021.09.12

加入NFC绕过登录功能。

具体实现见【NFC.java】



#### 2021.09.10

建立工程，实现Login功能；

软件用户协议：挂到github上即可；

```shell
内置账号：
qiuyisheng  123456
guoxingguo  123456
xuyao       123456
```

~~放弃~~二次验证功能（输入小朋友的信息进行二次验证）；

```shell
一些信息:
小朋友今年多少岁了？

小朋友身份证后六位？
123456
小朋友的全名？

```

进入主页面（空白）

### BUG记录

1、Cannot fit requested classes in a single dex file (# methods: 87264 > 65536)

解决：在build.gradle中的defaultConfig加一句multiDexEnabled true

2、莫名闪退

onCreate()方法中，下面两行必须在最开始的位置

```java
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
```

3、修改了各种尺寸下的APP图标，但未刷新

![image-20210910144216659](https://gitee.com/yuyang201907/Imags/raw/master/image-20210910144216659.png)

解决：删除该文件目录下的xml文件

