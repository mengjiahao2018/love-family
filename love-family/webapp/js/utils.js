

//字符串 替换所有
String.prototype.replaceAll = function(s1,s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}

//日期格式化 
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate()       // 日
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

// 获取指定日期下个月的第一天
Date.prototype.calculateNextMonthMinDate = function() {
	var nowDay = this;
	var year = nowDay.getFullYear();
	var month = nowDay.getMonth()+1;
	var day = nowDay.getDate() ;
	if(month==12){
		return (year+1) + '-' + 1 + '-' + 1;
	}else{
		return year + '-' + (month+1) + '-' + 1;
	}
};

//根据指定日期判断，小于15号取当月1号，大于15号取下月1号
Date.prototype.calculateMinDate = function() {
	var nowDay = this;
	var year = nowDay.getFullYear();
	var month = nowDay.getMonth()+1;
	var day = nowDay.getDate() ;
	if(month==12){
		if(day <= 15){
			return year + '-' + 12 + '-' + '01';
		}else {
			return (year+1) + '-' + '01' + '-' + '01';
		}
	}else{
			if(day <= 15){
			month = month < 10 ? "0"+month : month;
			return year + '-' + month + '-' + '01';
		}else {
			var nextMonth = month+1;			
			nextMonth = nextMonth < 10 ? "0"+nextMonth : nextMonth;
			return year + '-' + nextMonth + '-' + '01';
		}
	}
};

//删除数组中与参数相等的第一个元素
Array.prototype.remove = function(element) {
	var i = this.indexOf(element);
	if(i>=0) {
		this.splice(i, 1);
	}
};



var tableMap = {
	"certypecode":{
		"tbl_key":"TBL_PERSON",
		"tbl_item":"CERTYPECODE"
	},"cernumber":{
		"tbl_key":"TBL_PERSON",
		"tbl_item":"PID"
	},"name":{
		"tbl_key":"TBL_CHANNELROLE",
		"tbl_item":"NAME"
	},"birthdate":{
		"tbl_key":"TBL_PERSON",
		"tbl_item":"BIRTHDATE"
	},"gender":{
		"tbl_key":"TBL_PERSON",
		"tbl_item":"gender"
	},"age":{
		"tbl_key":"TBL_LP_CHAROLE",
		"tbl_item":"AGE"
	},"education":{
		"tbl_key":"TBL_PERSON",
		"tbl_item":"education"
	},"highest":{
		"tbl_key":"TBL_LP_CHAROLE",
		"tbl_item":"HIGHEST"
	},"divid":{
		"tbl_key":"tbl_key",
		"tbl_item":"divisionid"
	},"deptid":{
		"tbl_key":"tbl_key",
		"tbl_item":"deptid"
	},"position":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"position"
	},"empgrade":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"empgrade"
	},"emptype":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"emptype"
	},"checkpremium":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"checkpremium"
	},"basepay":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"basepay"
	},"deptdate":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"deptdate"
	},"jobdate":{
		"tbl_key":"tbl_fr_key",
		"tbl_item":"jobdate"
	},"employment":{
		"tbl_key":"TBL_LP_CHAROLE",
		"tbl_item":"EMPLOYMENT"
	},"mainobject":{
		"tbl_key":"TBL_LP_CHAROLE",
		"tbl_item":"mainobject"
	},"supportcompany":{
		"tbl_key":"TBL_LP_CHAROLE_APPLY",
		"tbl_item":"supportCompany"
	},"checkstartdate":{
		"tbl_key":"TBL_LP_CHAROLE_APPLY",
		"tbl_item":"checkstartdate"
	}
}
//existCode:如果代码里判断必须存在code,但本项不为下拉框或者树的时候使用
function getModifyData(oldObj, newObj, props){
	var modifyData = [];
	$.each(props, function (index, obj) {
		if(oldObj[obj.field] != newObj[obj.field]){
	        var modifyObj = {};
	        modifyObj.id = obj.field;
	        modifyObj.item = obj.name;
	        modifyObj.tbl_key = tableMap[obj.field].tbl_key;
	        modifyObj.tbl_item = tableMap[obj.field].tbl_item;
	        if(obj.options){
	        	$.each(obj.options, function(index, optionsObj) {
	                if (optionsObj["id"] == newObj[obj.field]) {
	                	modifyObj.newCode = newObj[obj.field];
	                	modifyObj.newVal = optionsObj["label"];
	                }
	                if (optionsObj["id"] == oldObj[obj.field]) {
	                	modifyObj.oldCode = oldObj[obj.field];
	                	modifyObj.oldVal = optionsObj["label"];
	                }
	            });
	        }else{
	        	if(obj.val){
	            	modifyObj.newCode = newObj[obj.field];
	            	modifyObj.newVal = newObj[obj.val];
	            	modifyObj.oldCode = oldObj[obj.field];
	            	modifyObj.oldVal = oldObj[obj.val];
		        }else{
		        	if(obj.existCode){
			        	modifyObj.oldCode = oldObj[obj.field];
			        	modifyObj.newCode = newObj[obj.field];
		        	}
		        	modifyObj.oldVal = oldObj[obj.field];
		        	modifyObj.newVal = newObj[obj.field];
		        }
	        }
	        modifyData.push(modifyObj);
	    }
	})
	return modifyData;
}

function arrToStr(props){
	var str = "";
	var len = props.length;
	$.each(props,function (index, obj) {
		if(index != len-1){
			if(typeof(obj) == "number"){
				str += obj + ","; 
			}else if(typeof(obj) == "string"){
				str += obj + "','"; 
			}
		}else{
			str += obj;
		}
	});
	return str;
}

function getDataByUrl(url, val, callback) {
    $.ajax({
        type: "GET",
        url: url,
        data : val,
        dataType: "json",
        success: function (result) {
        	if(callback){
        		callback(result);
        	}
        }
    });
}

var cloneObj = function(obj){
    var str, newobj = obj.constructor === Array ? [] : {};
    if(typeof obj !== 'object'){
        return;
    } else if(window.JSON){
        str = JSON.stringify(obj), //系列化对象
        newobj = JSON.parse(str); //还原
    } else {
        for(var i in obj){
            newobj[i] = typeof obj[i] === 'object' ? cloneObj(obj[i]) : obj[i];
        }
    }
    return newobj;
};
