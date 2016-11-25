var const_index = '首页';
/*将form表单元素的值序列化成对象 */
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/*清空*/
function clearForm(datagrid) {
	//清空textbox
	$("#searchForm input[class^=easyui-textbox]").each(function(){
		$("#"+$(this).attr("id")).textbox('setValue','');
	});
	
	//清空datebox
	$("#searchForm input[class^=easyui-datebox]").each(function(){
		$("#"+$(this).attr("id")).textbox('setValue','');
	});
	
	//清空datetimebox
	$("#searchForm input[class^=easyui-datetimebox]").each(function(){
		$("#"+$(this).attr("id")).textbox('setValue','');
	});
	datagrid.datagrid('load',{});
	datagrid.datagrid('clearSelections');
	datagrid.datagrid('clearChecked');
}

/**
 * 限制textarea输入字数
 * @memberOf {TypeName} 
 */
function limit_textarea_input() {   
    $("textarea[maxlength]").bind('input propertychange', function() {   
        var maxLength = $(this).attr('maxlength');   
        if(maxLength == ""){
        	maxLength = 100;
        }
        if ($(this).val().length > maxLength) {   
            $(this).val($(this).val().substring(0, maxLength));   
        }else{
        	$(this).parent().find("span").remove();
        	var leave = maxLength-$(this).val().length;
        	$(this).parent().append('<span id="leaves">'+leave+'/'+maxLength+'</span>');
	    }   
    });   
}  

/**
 * 获得当前日期yyyy-mm-dd
 * @return {TypeName} 
 */
function getNowDateFormat(){
	var currentDate = new Date();
	var y = currentDate.getFullYear();
	var m = currentDate.getMonth()+1;
	var d = currentDate.getDate();            
	var date = y + "-";
    if(m < 10){
    	date += "0";
    }
    date += m + "-";
    if(d < 10){
    	date += "0";
    }
    date += d + "";

	return date;
}

/**
 * 两日期相差的天数
 * @param DateOne
 * @param DateTwo
 * @return
 */
function daysBetween(DateOne, DateTwo){   
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
  
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
  
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear) - Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
    return cha;
}

//easyui 释放iframe内存,覆盖全局的onBeforeDestroy事件。
$.fn.panel.defaults = $.extend({},$.fn.panel.defaults,{onBeforeDestroy:function(){  
    var frame=$('iframe', this);  
    if(frame.length>0){  
       frame[0].contentWindow.document.write('');  
       frame[0].contentWindow.close();  
       frame.remove();  
       if($.browser.msie){  
    	   CollectGarbage();  
       }  
    }  
}}); 

//判断浏览器版本
function getOs(){ 
    if(navigator.userAgent.indexOf("MSIE")>0) { 
         return "MSIE"; 
    } 
    if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
         return "Firefox"; 
    } 
    if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
         return "Safari"; 
    }  
    if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
         return "Camino"; 
    } 
    if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){ 
         return "Gecko"; 
    } 
} 

//获得数组的最大值
Array.max = function (array) {
    return Math.max.apply(Math, array);
}

//获得数组的最小值
Array.min = function (array) {
    return Math.min.apply(Math, array);
}

//只能输入英文字母
$.extend($.fn.validatebox.defaults.rules, {
	englishRule : {
	    validator : function(value, param) { 
	        return /^[A-Za-z]+$/.test(value);
	    }, 
	    message : '只能输入英文字母' 
	}
});

//只能输入汉字
$.extend($.fn.validatebox.defaults.rules, {
	chineseRule : {
	    validator : function(value, param) { 
	        return /^[\u4e00-\u9fa5]*$/.test(value);
	    }, 
	    message : '只能输入汉字' 
	}
});

//扩展固定电话验证规则
$.extend($.fn.validatebox.defaults.rules, {   
    telephoneIsValid: {   
        validator: function(value, param){
            return /^0\d{2,3}-\d{7,8}$/.test(value);
        },   
        message: '请输入合法的固定电话'  
    }
}); 

//扩展移动电话(手机)验证规则
$.extend($.fn.validatebox.defaults.rules, {   
    mobileIsValid: {   
        validator: function(value, param){
            return /^[1][3-8]\d{9}$/.test(value);
        },   
        message: '请输入合法的移动电话'  
    }
}); 

//手机号和固话验证规则
$.extend($.fn.validatebox.defaults.rules, {   
    isMobileOrTelephoneValid: {   
        validator: function(value, param){
            return /(^[1][3-8]\d{9}$)|(^0\d{2,3}-\d{7,8}$)/.test(value);
        },   
        message: '请输入合法的手机号或固话'  
    }
}); 

//只能输入数字和字母
$.extend($.fn.validatebox.defaults.rules, {   
    numberAndLetterIsValid: {   
        validator: function(value, param){
            return /^[A-Za-z0-9]+$/.test(value);
        },   
        message: '只能输入数字和字母'  
    }
});

//必须是以字母、数字、下划线组成的字符串
$.extend($.fn.validatebox.defaults.rules, {   
    letterNumUnderlineRule: {   
        validator: function(value, param){
            return /^[a-zA-Z0-9_]*$/.test(value);
        },   
        message: '必须是以字母、数字、下划线组成的字符串'  
    }
});

//必须是以字母、数字、下划线、横杠组成的字符串
$.extend($.fn.validatebox.defaults.rules, {   
    letterNumUnderlineRuleAndBars: {   
        validator: function(value, param){
            return /^[a-zA-Z0-9_-]*$/.test(value);
        },   
        message: '必须是以字母、数字、下划线、-组成的字符串'  
    }
});

//email 邮箱
$.extend($.fn.validatebox.defaults.rules, {   
    isEmail: {   
        validator: function(value, param){
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },   
        message: '请正确填写Email'  
    }
});


//角色代码必须是以\"ROLE_\"开头的大写字母、数字、下划线组成的字符串！
$.extend($.fn.validatebox.defaults.rules, {   
    roleRule: {   
        validator: function(value, param){
            return /^ROLE_[A-Z0-9_]*$/.test(value);
        },   
        message: '角色代码必须是以\"ROLE_\"开头的大写字母、数字、下划线组成的字符串！'  
    }
});


//邮政编码
$.extend($.fn.validatebox.defaults.rules, {
	zipCodeRule:{
		validator: function(value){
			return /^\d{6}$/.test(value);
		},
		message: '请输入合法的邮政编码'
	}
});


//只能输入整数
$.extend($.fn.validatebox.defaults.rules, {
	integer:{
		validator: function(value){
			return /^[0-9]*$/.test(value);
		},
		message: '只能输入数字'
	}
});

//验证银行卡号
$.extend($.fn.validatebox.defaults.rules, {   
    isBankCardNo: {   
        validator: function(value, param){
            return value.length == param[0] || value.length == param[1] || value.length == param[2] || value.length == param[3] || value.length == param[4];  
        },   
        message: '银行账户只能是{0}、{1}、{2}、{3}、{4}位'  
    }
}); 

//合同编号
$.extend($.fn.validatebox.defaults.rules, {   
    contractNoRule: {   
        validator: function(value, param){
            return /^[A-Za-z0-9]{8,}$/.test(value);
        },   
        message: '至少8位数字或与字母组合'  
    }
}); 

//三方代扣协议编号
$.extend($.fn.validatebox.defaults.rules, {
	withholdAgreementNoRule:{
		validator: function(value){
			return /^((cj-)|(CJ-))(([\d]{4}-[\d]{4})|([\d]{9}))$/.test(value);
		},
		message: '三方代扣协议编号不合法'
	}
});

//前后两次输入不一致
$.extend($.fn.validatebox.defaults.rules, {
	equalsTo: {
		validator: function(value,param){
			return value == $(param[0]).val();
		},
		message: '前后两次输入不一致!'
	}
});

//千分位转换
function format (num) {
    return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}

function dateFormat(date,fmt){ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}