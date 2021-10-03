### 开发记录

#### 2021.10.03

基本实现了全部功能，UI美化和代码精简都推给组长了。

GsonFormatPlus是真的好用，和朋友很久没调出的错误很容易就解决了。



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

> 原本记在Excel中，后来丢失了



