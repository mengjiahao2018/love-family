<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<HTML>
<HEAD>
<TITLE>用户登录</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<LINK rel=stylesheet href="lib/element/element.min.css">
<LINK rel=stylesheet href="lib/font-awesome/css/font-awesome.min.css">
<LINK rel=stylesheet href="css/login.css">
</HEAD>
<BODY id=login_body>
		<div id="loginApp">
			<el-form id="loginForm" ref="loginForm" :model="loginData" action="j_spring_security_check" method="post" label-position="left" label-width="0px" class="demo-ruleForm login-container">
				<h3 class="title">系统登录</h3>
			    <el-form-item prop="username">
			    	<el-input type="text" v-model="loginData.username" auto-complete="off" placeholder="账号"></el-input>
			    </el-form-item>
			    <el-form-item prop="password">
			    	<el-input type="password" v-model="loginData.password" auto-complete="off" placeholder="密码"></el-input>
			    </el-form-item>
			    <el-form-item style="width:100%;">
			    	<el-button type="primary" style="width:100%;" @click.native.prevent="submitForm" >登录</el-button>
			    </el-form-item>
			    <input id="j_username" name="j_username" style="display: none" />
				<input id="j_password" name="j_password" style="display: none" />
  			</el-form>
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
				username:"mengjiahao",
				password:"mengjiahao"
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
    		$("#j_username").val(_this.loginData.username);
    		$("#j_password").val(_this.loginData.password);
    		loginForm.$el.submit();
    	}
    }
 })
</script>
</HTML>
