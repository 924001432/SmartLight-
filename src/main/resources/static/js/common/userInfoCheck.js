//校验用户名
function checkUserName(value) {
    //使用正则表达式校验用户名
    //只能由英文字母和数字组成，长度为4～16个字符，并且以英文字母开头
    //1. 编写一个正则表达式
    var reg = /^[a-zA-Z][a-zA-Z0-9]{3,15}$/
    //2. 使用正则表达式取校验用户名

    console.log(value);
    if(value.length <= 0){
        console.log("value.length <= 0");

    }else {
        if (reg.test(value)) {

            //查询用户名是否已使用
            $.ajax({
                //准确查找
                url: '/querySingleUserByName/' + value,

                async: false,
                cache: false,
                success: function (str) {

                    console.log("str" + str);

                    if(str == -1){//找到重复的
                        layer.msg("用户名已被使用",{icon:5,anim:6});
                        document.getElementById("userName").focus();
                    }else{
                        layer.msg("用户名合法",{icon:1,anim:4});
                    }

                }
            });

        }else {
            layer.msg("用户名不合法，请重新输入",{icon:5,anim:6});
            document.getElementById("userName").focus();
        }
    }

}
//校验手机号码
function checkUserPhone(value) {
    //使用正则表达式校验
    //11位数字，以1开头。
    //1. 编写一个正则表达式
    var reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
    //2. 使用正则表达式取校验
    if(value.length <= 0){
        console.log("value.length <= 0");

    }else {
        if (reg.test(value)) {

            layer.msg("手机号码合法",{icon:1,anim:4});

        }else {
            layer.msg("手机号码不合法，请重新输入",{icon:5,anim:6});
            document.getElementById("userPhone").focus();;
        }
    }
}
//校验邮箱
function checkUserEmail(value) {
    //使用正则表达式校验
    //姑且把邮箱地址分成“第一部分@第二部分”这样。第一部分：由字母、数字、下划线、短线“-”、点号“.”组成；第二部分：为一个域名，域名由字母、数字、
    //短线“-”、域名后缀组成，而域名后缀一般为.xxx或.xxx.xx，一区的域名后缀一般为2-4位，如cn、com、net，现在域名有的也会大于4位

    //1. 编写一个正则表达式
    var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
    //2. 使用正则表达式取校验
    if(value.length <= 0){
        console.log("value.length <= 0");

    }else {
        if (reg.test(value)) {

            layer.msg("邮箱合法",{icon:1,anim:4});

        }else {
            layer.msg("邮箱不合法，请重新输入",{icon:5,anim:6});
            document.getElementById("userEmail").focus();;
        }
    }
}