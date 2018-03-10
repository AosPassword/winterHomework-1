# 寒假作业企划【雾】

### 要实现的功能

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

------

### 接口文档

1. 登陆相关

   1.1 登陆

   - 登陆

     请求为post请求 <http://www.mashiroc.cn/gayligayliapi/login>

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
         签名，需要按照顺序使用"."拼接value，然后使用base64编码
         最后使用HmacSHA-256加密，如本请求的签名为：
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

   - 刷新token <http://www.mashiroc.cn/gayligayliapi/refreshToken

     请求为post请求

     ```json
     {
         "timestamp":<timestamp>,
      	"signature":<signature>
     }
     ```

     *注：所有和用户登录后有关的操作都需要将一个JWT附在请求的header上*

     响应为一个JSON串

     ```json
     {
         "result":<resule>,
      	"jwt":<jwt>
     }
     ```

   ​

   1.2 注册

   - 检查用户名是否存在 <http://www.mashiroc.cn/gayligayliapi/isUserHas>

     请求为get请求，需要两个参数

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
         */
         "result":<result>
     }
     ```

     ​

   - 注册 <http://www.mashiroc.cn/gayligayliapi/register>

     请求为post请求

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

   - 发送验证码 <http://www.mashiroc.cn/gayligayliapi/verificationCode

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

   2.1 拉取主页信息 <http://www.mashiroc.cn/gayligayliapi/homePage>

   请求为get请求

   响应为一个JSON串

   ```json
   {
       /*
       以下是各分区的命名：
       cartoon 动画，anime 番剧，createdByNative 国创，music   音乐
       dance   舞蹈，game  游戏，autotuneRemix   鬼畜，science 科学与技术
       fashion 时尚，life  生活，advertisement   广告, entertainment 娱乐
       movies  影视，screeningHall 放映室
       */
    	"typeNum":{//各个分区的新视频数量
         ···
           "game":123;
         ···
       },
     	"carousel":[//轮播的视频的信息
           ···
         {
           "id":<id>,
         	"name":<name>,
         	"photoUrl":<photoUrl>//封面图片的url
         },
         ···
       ],
   	"topInfo":[//最上面轮播旁边的视频的信息
         ···
         {
           "id":<id>,
         	"name":<name>,
         	"author":{//所有的author的格式都是一样的 下面不重复
           	"id":<id>,
         		"name":<name>,
         		"description":<description>,
         		"level":<level>,
         		"photoUrl":<photoUrl>,//头像url
         	}
         	"views":<views>,
         	"photoUrl":<photoUrl>
         },
         ···
       ],
     	"spread":[//推广那一条
       ···
         {
           "id":<id>,
         	"name":<name>,
         	"length":<length>,//视频长度
         	"photoUrl":<photoUrl>
         },
         ···],
   	"live":[
       	···
         	{
         		"name":<name>,
         		"author":<author>,//播主名字
         		"type":<type>,//带着子分类的直播类型
         		"views":<views>,//直播人数
         		"photoUrl":<photoUrl>,//封面url
         		"videoUrl":<videoUrl>,//鼠标移上去出现的图片的url
         	},
         ···
       ],//直播那一条
       //下面是各个分区的信息
       ...
       "game":{
                 "info":[
     					···
    					 {
     						"id":<id>,
     						"name":<name>,
     						"author":<author>,
     						"length":<length>,
     						"views":<views>,//播放量
     						"barrageNum":<barrageNum>,//弹幕数
     						"photoUrl":<photoUrl>
   					}
     					···
     					]//左边的视频信息
     			 "rank":[
                		···
                  		{
                  			"id":<id>,
                  			"name":<name>,
                  			"author":<author>,
                  			"time":<time>,//上传时间
                  			"description":<description>,//视频描述
                  			"photoUrl":<photoUrl>,
                  			"views":<views>,
                  			"collection":<collection>,//收藏数
                  			"coin":<coin>,//硬币数
                  			"barrageNum":<barrageNum>,
                  			"score":<score>,//权
                  		}，
                  		···
                		]//右边的视频排名的视频信息
               }
     	...
   }
   ```

   ​

   2.2 拉取视频详细信息，视频弹幕信息，视频评论信息

   - 拉取 <http://www.mashiroc.cn/gayligayliapi/videoPage>

     请求为post请求

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
         "videoInfo":{
         				"id":726,
       					"name":"番茄焖饭—业余美食博主的周五放送",
       					"author":<author>,//上面找
       					"type":"life",
       					"childType":0,//子分类 暂时不用管
       					"avId":20270547,//av号
       					"description":"视频来自bilibili",//视频详细描述
       					"time":"1519982687",//发送时间戳
       					"length":"60",//视频长度 单位：秒
       					"views":66222,//浏览量
       					"coin":1286,//硬币数
       					"collection":1//收藏数
       					"barrageNum":1//弹幕数
                    		 "photoUrl":"url",//封面url
       					"videoUrl":"test"
     					},
     	//弹幕的信息
     	"barrage":[
           			{
                       "content":"test",//弹幕内容
                       "sendTime":"1520144809",//发弹幕的时间
                       "authorId":6,//创作者的id
                       "color":"red",//弹幕颜色
                       "position":0,//弹幕位置 -1是下面 0是滚动 1是上面
                       "appearTime":"10"//出现在视频的第几秒
                     }
         		],
     	"comment":[
           			{"id":1,//评论id
                      "author":<author>,//用户信息
                      "content":"test",//评论信息
                      "num":1,//楼数
                      "time":"1520145218",//评论时间的时间戳
                      "device":"windows",//评论使用的设备
                      "praise":0,//赞数
                      "childComment":[
                      //这里面的结构和父节点结构一样
                      //没有子评论的话这个array的size为0
                      ]},
         			···
         ]}
     				
       ,
     }
     ```

   2.3 上传视频

   - 获得上传凭证 <http://www.mashiroc.cn/gayligayliapi/uploadToken

     请求为一个JSON串 post请求

     ```json
     {
         "name":<name>,//视频的名字
       	"type":<type>,//视频的分区 上见分区命名
       	"description":<description>,//视频的详细描述
       	"length":<length>,//视频的长度
       	"timestamp":<timestamp>,//这个时间戳会成为视频上传时间
       	"signature":<signature>
     }
     ```

   - 视频上传成功通知  <http://www.mashiroc.cn/gayligayliapi/uploadSuccess

     请求为post请求

     ```json
     {
         "avId":<avId>,
       	"timestamp":<timestamp>,
       	"signature":<signature>
     }
     ```

3. 视频点击事件相关

   以下的请求都需要附带一个token在请求的header

   3.1 投硬币 http://www.mashiroc.cn/gayligayliapi/sendCoin

   请求为post请求

   ```json
   {
       "videoId":<videoId>,//要投硬币的视频的id
       "sendCoin":<sendCoin>,//要投的硬币的数量
       "timestamp":<timestamp>,
       "signature":<signature>
   }
   ```

   响应为一个JSON串

   ```json
   {
     "result":<result>
   }
   ```

   3.2 收藏 http://www.mashiroc.cn/gayligayliapi/addCollection

   请求为一个JSON串 post请求 

   ```json
   {
     "videoId":<videoId>,
     "timestamp":<timestamp>,
     "signature":<signature>
   }
   ```

   响应为一个JSON串

   ```json
   {
     "result":<result>
   }
   ```

   3.3 发送弹幕

   请求为一个JSON串 post请求 http://www.mashiroc.cn/gayligayliapi/sendBarrage

   ```json
   {
     "videoId":<videoId>,
     "content":<content>,//评论的具体内容
     "pid":<pid>,//该评论若为某评论下的回复，则是评论的id，若是新评论，则为0
     "device":<device>,//评论使用的设备 iOS,Android,Windows
     "timestamp":<timestamp>,
     "signature":<signature>
   }
   ```

   响应为一个JSON串

   ```json
   {
     "result":<result>
   }
   ```

   3.4 发送评论

   请求为post请求 http://www.mashiroc.cn/gayligayliapi/sendComment

   ```json
   {
     "videoId":<videoId>,
     "content":<content>,//弹幕的具体内容
     "appearTime":<appearTime>,//弹幕要出现的时间的秒数
     "device":<device>,//发送评论用的设备 例如 ： iphone20
     "color":<color>,//弹幕的颜色
     "fontsize":<fontsize>,//弹幕的字号
     "position":<position>,//弹幕的位置，0为滚动，-1为下面，1为上面
     "timestamp":<timestamp>,
     "signature":<signature>
   }
   ```

   响应为一个JSON串

   ```json
   {
     "result":<result>
   }
   ```

   3.5 搜索 http://www.mashiroc.cn/gayligayliapi/search

   请求为post请求 

   ```json
   {
       "data":<data>,//搜索的内容 使用base64编码
     	"page":<page>,//第几页
     	"timestamp":<timestamp>,
     	"signature":<signature>
   }
   ```

   响应为一个JSON串

   ```json
   {
     "result":"success",
     "page":2,//总共有多少页
     "data":[//最多有二十个
       ···
       {"id":464,
       "name":"戊戌年华语MAD文化祭第二日",
       "views":144,
       "length":"60",
       "time":"1520074910",
       "authorId":1,
       "authorName":"来自bilibili",
       "photoUrl":"url"}
     	···
     ]
   }
   ```

   ​

4. 个人信息相关

   4.1 拉取个人信息

   请求为post请求 http://www.mashiroc.cn/gayligayliapi/userInfo

   ```json
   {
       "id":<id>//用户id
     	"timestamp":<timestamp>
     	"signature":<signature>
   }
   ```

   ​

5. 用户和视频管理相关

   5.1 修改密码 http://www.mashiroc.cn/gayligayliapi/replacePassword

   ```json
   {
       "oldPassword":<oldPassword>,//旧密码
     	"newPassword":<newPassword>,//新密码
     	"timestamp":<timestamp>,
     	"signature":<signature>
   }
   ```

   ​

   5.2 上传头像 http://www.mashiroc.cn/gayligayliapi/replacePhoto

   ```json
   {
       "photoUrl":<photoUrl>,
     	"timestamp":<timestamp>,
     	"signature":<signature>
   }
   ```

   ​

6. 管理界面

http://www.mashiroc.cn/gayligayliapi/gayligayliapi/guojiaduiwotmchuibao.html