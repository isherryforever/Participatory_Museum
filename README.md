# Participatory_Museum（参与式博物馆）

#### 概述

系《物联网应用系统设计》作业安卓开发，结合STEM教育的思想，主要实现以下功能：

1、登录 ：使用Leancloud的开放接口，同意用户使用协议之后密码校验通过即可登录

2、NFC验证登录 ：用户名与密码存放在小熊派的NFC Tag中，以英文分号隔开，APP申请NFC权限后可使用NFC扫描标签快速登录

3、实时人流量显示：网页Post/Get得到的Json解析得到

4、实时轨迹显示：将坐标保存至数组中，用Canvas进行绘制

~~5、打卡 + 奖励~~

安卓开发主要工作分为四部分，分别是UI设计、NFC登录、数据获取和解析、轨迹绘制。

#### 一、UI设计

略，详见res/layout 下的布局文件。

主要用到了TextView（其余所有）、EditText（输入框）、CheckBox（同意协议、记住密码、自动登录）和Button（登录）控件。

大多采用线性布局（LinearLayout）和水平排列（android:orientation="vertical"）。

#### 二、NFC登录

###### 手机读取NFC Tag中的文本数据

- 首先，在AndroidManifest.xml申请软件调用NFC权限，详细代码如下：

```xml
    <!--nfc权限和允许google商店显示-->
    <uses-permission android:name="android.permission.NFC"/>
    <uses-feature android:name="android.hardware.nfc" android:required="true"/>
```

- 其次，在res/xml目录下新建nfc_tech_filter.xml，具体内容如下:

```xml
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
    <tech-list>
        <tech>android.nfc.tech.IsoDep</tech>
        <tech>android.nfc.tech.NfcA</tech>
        <tech>android.nfc.tech.NfcB</tech>
        <tech>android.nfc.tech.NfcF</tech>
        <tech>android.nfc.tech.NfcV</tech>
        <tech>android.nfc.tech.Ndef</tech>
        <tech>android.nfc.tech.NdefFormatable</tech>
        <tech>android.nfc.tech.MifareClassic</tech>
        <tech>android.nfc.tech.MifareUltralight</tech>
    </tech-list>
</resources>
```

- 将对NFC的读取操作封装成普通JAVA类 NFC.java，通过对NfcAdapter的操作实现序列号和文本内容的获取，详见main/java/NFC,java

###### 登录账号

此部分复用了暑期实习代码，主要利用Leancloud的接口，对用户名和密码进行校验。

###### NFC + 登录

软件首先读取NFC Tag中的文本数据（String类型），然后从中析取出用户名和密码，再调用Leancloud的登录接口进行校验，若匹配成功则进入软件，若匹配失败则提示错误信息。

#### 三、数据获取和解析

###### Http 获取数据（Post / Get）

常规操作，略。

###### Json解析

Json数据包主要有两类，具体格式如下：

```html
//测试接口:https://isherryforever.github.io/api1
//用于进行实时轨迹绘制
{
    'name':'yy', 
    'text':'当前位置：展馆-1', 
    'total':5, 
    'records':
    [ 
        { 'name': 'qys', 'text': 'room 01', 'loc_x':'-1', 'loc_y':'-1' },
        { 'name': 'qys', 'text': 'room 01', 'loc_x':'0', 'loc_y':'0' }, 
        { 'name': 'qys', 'text': 'room 01', 'loc_x':'100', 'loc_y':'0' }, 
        { 'name': 'qys', 'text': 'room 01', 'loc_x':'100', 'loc_y':'100' }, 
        { 'name': 'qys', 'text': 'room 01', 'loc_x':'0', 'loc_y':'100' } 
    ] 
}

//测试接口：https://isherryforever.github.io/api2
//主要进行场馆人流量的统计
{ 
    'room 01': 6, 
    'room 02': 8, 
    'room 03': 0, 
    'room 04': 8 
}
```

解析采用的Google提供的Gson解析包，可以在AndroidStudio 安装GsonFormatPlus插件生成模板类（Sensor.java、Room.java），然后调用Gson().fromJson方法进行解析。

详细代码如下：

```java
    Sensor data = new Gson().fromJson(res, Sensor.class);
    List<Sensor.UserBean> record = data.getRecords();
    name=data.getName();
    position=data.getText();
    int n=data.getTotal();
    for(int i=0;i<n;i++)//得到n个点的坐标
    {
        lastx[++num]=record.get(i).getLocx()*3+30;
        lasty[num]=record.get(i).getLocy()*3+30;//num加一次就够了
    }
```

#### 四、轨迹绘制

用canvas绘制一张空白画布，然后进行描点连线，在最后一个点处绘制儿童Icon。

更新轨迹时，先将画布清空，根据Path数组中的历史路径进行绘制，方法同上。

具体代码如下：

```java
    canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清空路径
    canvas.drawARGB(255,237,230,230);//着底色
    for(int i=1;i<=num;i++)
        canvas.drawLine(lastx[i-1], lasty[i-1], lastx[i], lasty[i], paint);//重新绘制之前保存的轨迹
    canvas.drawBitmap(kids, lastx[num]-10, lasty[num]-10, null);//绘制小孩Icon
```



