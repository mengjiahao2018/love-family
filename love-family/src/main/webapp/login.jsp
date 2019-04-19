<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE HTML >
<HTML>
<HEAD>
<TITLE>用户登录</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<LINK href="lib/element/element.min.css" rel=stylesheet>
<LINK href="lib/font-awesome/css/font-awesome.min.css" rel=stylesheet>
<LINK href="css/index.css" rel=stylesheet>
</HEAD>
<BODY id=login_body>
	<div align="center" style="width: 100%">
		<div id="loginApp" style="width: 250px;" align="center">
			<div style="margin-bottom: 20px;font-weight: 600" >用户登录</div>
			<el-form id="loginForm" ref="loginForm" :model="loginData" action="j_spring_security_check" method="post">
				<el-form-item prop="username">
					<el-input v-model="loginData.username" size="small" placeholder="请输入用户名">
						<template slot="prepend">
							<span class="ico-user"></span>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input v-model="loginData.password" size="small" placeholder="请输入密码" type="password">
						<template slot="prepend">
							<span class="ico-lock"></span>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item>
					  <el-button type="primary" @click="submitForm" >登陆</el-button>
				</el-form-item>
				<input id="j_username" name="j_username" style="display: none" />
				<input id="j_password" name="j_password" style="display: none" />
			</el-form>
		</div>
	</div>
</BODY>

<script src="lib/vue/vue.js"></script>
<script src="lib/element/element.min.js"></script>
<script src="lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
var loginApp = new Vue({
    el: '#loginApp',
    data: function() {
      return { 
			loginData:{
				username:"",
				password:""
			}
      }
    },
    methods:{
    	submitForm:function(){
    		var _this=this;
    		var loginForm = _this.$refs.loginForm;
    		if(parent!=null){
    			loginForm.$el.target="_parent";
    		}
    		$("j_username").val(_this.loginData.username);
    		$("j_username").val(_this.loginData.password);
    		
    		loginForm.$el.submit();
    	}
    }
 })
</script>


</HTML>
