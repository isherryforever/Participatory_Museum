

### 预期功能

登录（硬件设备分配唯一账号）

二次验证（随机出现关于小朋友的信息，验证是否是家长本人）

地图打卡

位置显示

心率（看硬件情况）

看板娘（加一层webview就行）





### 待解决

登录页面美化

APP logo

加上二次验证（根据登录的用户名匹配）

主界面模块划分





### 开发记录

#### 2021.09.10

建立工程，实现Login功能；

软件用户协议：挂到github上即可；

```shell
内置账号：
qiuyisheng  123456
guoxingguo  123456
xuyao       123456
```

**待实现**二次验证功能（输入小朋友的信息进行二次验证）；

```shell
一些信息:
小朋友今年多少岁了？
6
小朋友身份证后六位？
123456
小朋友的全名？
邱奕盛
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





