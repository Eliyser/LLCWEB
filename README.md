# LLCWEB 
1. 接口名称：UserController.save() <br>
   url：/users/save
   接收参数：用户id,用户名username，密码password，关联人员peopleId,权限名role <br>
   返回参数：操作成功与否提示 <br>
   功能：正则验证用户名与密码是否符合要求，不允许用户名重复，若无关联人员则赋予默认值0，有则检测该人员是否存在，不存在则报错；若参数包含用户id则进行更新操作，否则进行插入操作，保存用户信息到用户表（密码进行md5加密），权限信息到用户_权限表 <br>
   
2. 接口名称：LoginController.getImageCode() <br>
   url：/getImageCode
   接收参数：无 <br>
   返回参数：操作成功与否提示 <br>
   功能：返回图片验证码 <br>
   
3. 接口名称：LoginController.updatePassword() <br>
   url：/users/updatePassword
   接收参数：用户id，新密码password <br>
   返回参数：操作成功与否提示 <br>
   功能：正则验证密码是否符合要求，更新指定id的密码（采用md5加密） <br>