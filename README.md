# 寒假作业企划【雾】

###要实现的功能

#### 加分项

- 有自己的域名, 服务器
- https
- 输入验证
- 前端语义化, 使用HTML5标签
- 使用CSS3制作前端动画
- 考虑安全性 (比如前端xss,后端sql注入)
- 考虑性能因素
- 考虑缓存

#### 普通

1. 用户登录注册

2. 视频文件上传

3. 视频播放/评论

4. 视频分类

5. 搜索:视频根据视频标签和标题进行搜索

6. 后台管理系统:视频管理 用户管理

7. [选做]

   弹幕功能

   - 可以做用户等级, 自己想等级制度, 等级越高, 发的弹幕越大
   - 比如说一级的时候弹幕是h5标签, 二级的时候弹幕是h4标签

8. [选做]浏览历史, 收藏功能

------------------------

### 接口概述

1. 登陆相关

   1.1 登陆

   - 登陆

        请求为一个JSON串，使用post请求

        ```json
        {
            //用户名的类型，如telephone，username，nickname，下同
            "usernameType":<usernameType>,
            
            //用户名，下同
         	"username":<username>,
            
            //密码，不需要加密，下同
         	"password":<password>,
            
            //UNIX时间戳，下同
         	"timestamp":<timestamp>,
         	
            /*
            签名，需要按照顺序使用"."拼接value，
            然后使用HmacSHA-256加密，如本请求的签名为：
            <usernameType>.<username>.<password>.<timestamp>
            */
            "signature":<signature>
        }
        ```

        响应为一个JSON串

        ```json
        {
            /*
            result有如下几种：
            "success":成功，会附带其他数据，若不成功，则返回的JSON只有错误原因
            "passwordError":密码错误
            "requestError":请求错误
            "requestOvertime":请求超时，时限为十分钟
            "signatureError":请求的签名验证错误
            "tokenOvertime":token已过期
            */
            "result":<result>,
            
            //token
         	"jwt":<jwt>
        }
        ```

        ​

   - 刷新token

        请求为一个JSON串，使用post请求

        ```json
        {
            "timestamp":<timestamp>,
         	"signature":<signature>
        }
        ```

        *注：需要将一个jwt附在请求的header上*

        响应为一个JSON串

        ```json
        {
            "result":<resule>,
         	"jwt":<jwt>
        }
        ```

   ​

   1.2 注册

   - 检查用户名是否存在

      请求为一个get请求，需要两个参数

     ```
     usernameType=<usernameType>
     username=<username>
     ```

     响应为一个JSON串

     ```json
     {
         /*
         此处的result与其他不同，只有两种
         "userExist":该用户名已存在
         "canRegister":该用户名可使用
         "result":<result>
     }
     ```

     ​

   - 注册     用户名 user 密码 pass      token

     请求为一个JSON串，使用post请求

     ```json
     {
         "usernameType":<usernameType>,//此处的usernameType不可为nickname
         "username":<username>,
         "password":<password>,
         "nickname":<nickname>,//昵称
         "timestamp":<timestamp>,
         "signature":<signature>
     }
     ```

     响应为一个JSON串

     ```json
     {
         "result":<result>,
         "jwt":<jwt>
     }
     ```

     ​

   - 发送验证码 手机号telephone        code{1-成功 0-失败}   yanzhengma{sdljfds（sha-256）}

     请求为一个JSON串，使用post请求

     ```json
     {
         "telephone":<telephone>,//需要发送验证码的手机号
         "timestamp":<timestamp>,
         "signature":<signature>
     }
     ```

     响应为一个JSON串

     ```json
     {
         "resule":<result>,
         "verification":<code>//已经被加密的验证码，使用HmacSHA-256加密
     }
     ```

     ​

2. 视频相关

   2.1 拉取主页信息

   请求为get请求

   响应为一个json串

   ```json
   {
       /*
       以下是各分区的命名：
       CARTOON 动画，ANIME 番剧，CREATED_BY_NATIVE 国创，MUSIC   音乐
       DANCE   舞蹈，GAME  游戏，AUTOTUNE_REMIX    鬼畜，SCIENCE 科学与技术
       FASHION 时尚，LIFE  生活，ADVERTISEMENT     广告, ENTERTAINMENT 娱乐
       MOVIES  影视，SCREENING_HALL 放映室
       */
       
       /*
       作者的信息那里是我刚改的 还没做好 等一下
       以下是视频中可能会出现的元素的命名：
       id   不解释   name  名字       user   作者的信息   type 分区
       avId 视频号   time  上传时间   length 长度         description 视频详细描述
       coin 硬币数   views 播放数     collection 收藏数   barrageNum 弹幕数
       photoUrl 封面url   videoUrl 视频url    childType 子分区
       
       //各个分区的新视频数量
       "typeNum":{  ...
           		"cartoon":xxx
                 	 ...},
       
       //轮播那一条的 <carousel>为一个视频信息的jsonarray 每个元素存放视频信息
       "carousel":<carousel>,
       
       //推广那一条
       "spread":<spread>,
       
       //直播那一条 不是从数据库取得 做样子的直播
       "live":<live>,
       
       //下面是各分区的信息 每个分区里面有info和rank两个jsonarray 
       //分别为左边的是个视频信息和右边的排名
       ...
       "music":{
       		"info":<info>
       		"rank":<rank>
   			}
   	...
   }
   ```

   ​

   2.2 拉取视频详细信息，视频弹幕信息，视频评论信息

   - 拉取

     请求为一个JSON串 post请求

     ```json
     {
         //视频的id
         "id":<id>,
         "timestamp":<timestamp>,
         "signature":<signature>
     }
     ```

     响应为一个JSON串

     ```json
     {
         "result":<result>,
     	//视频的信息
         "video":<video>.
         //弹幕列表
         "barrage":<barrage>
         //评论列表 树形结构
         "comment":<comment>
     }
     ```

   2.3 上传视频

   2.4 上传视频信息

3. 视频点击事件相关

   3.1 投硬币

   3.2 收藏

   3.3 发送弹幕

   3.4 发送评论

   3.5 添加标签

4. 个人信息相关

   4.1 拉取个人信息

   4.2 拉取粉丝列表

   4.3 拉取关注列表

   4.4 拉取收藏

5. 用户和视频管理相关

   5.1 修改密码

   5.2 上传头像

   5.3 设置用户信息

   5.4 设置视频信息

   ​