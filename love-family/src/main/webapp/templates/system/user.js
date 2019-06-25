define(function(){
	return{
		data:function(){
			return {
				dataSearch:{
					functionName:'',
					functionUrl:''
				},
				tableHeight:250,
				dataUser:[],
				pageSize:10,
				currentPage:1,
				total:0,
				editingUser:{},
				rules:{
					userName:[{required:true,message:"用户名称不能为空！",trigger:"change"}],
					loginName:[{required:true,message:"登陆账号不能为空！",trigger:"change"}],
					password:[{required:true,message:"密码不能为空！",trigger:"change"}],
					status:[{required:true,message:"标识位不能为空！",trigger:"change"}],
				},
				showEditDialog:false
			}
		},
		methods:{
			formatter:function(row,column,cellValue){
				var map = this.fieldMaps[column.property];
				for(var i=0;i<map.length;i++){
					if(map[i].value==cellValue){
						return map[i].label;
					}
				}
				return cellValue;
			},
			search:function(){
				var _this = this;
				var searchData = {
						"pageRequest":{
							"page":this.currentPage,
							"size":this.pageSize,
							"sort":"id"
						},
						"userName":this.dataSearch.userName,
						"loginName":this.dataSearch.loginName
				};
				$.ajax({
					type:"GET",
					url:"user/queryAll.do",
					data:searchData,
					dataType:"json",
					success:function(data){
						_this.dataUser = data.resultList;
						_this.total = data.totalCount;
					}
				});
			},
			editUser:function(user){
				var _this = this;
				$.ajax({
					type:"GET",
					url:"user/searchUserDataById.do",
					data:{"id":user.id},
					dataType:"json",
					success:function(data){
						_this.editingUser = data;
						_this.showEditDialog=true;
					}
				});
			},
			deleteUser:function(user){
				var _this = this;
				this.$confirm('确认删除此用户？','提示',{
					type:"warning"
				}).then(function(){
					var deleteData = {
							"id":user.id,
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"userName":_this.dataSearch.userName,
							"loginName":_this.dataSearch.loginName
					}
					$.ajax({
						type:"GET",
						url:"user/deleteUserDataById.do",
						data:deleteData,
						dataType:"json",
						success:function(data){
							if(data.result=='000000'){
								_this.dataUser = data.resultList;
								_this.total = data.totalCount;
								_this.showEditDialog=false;
							}else{
								_this.$message.error(data.message);
							}
						}
					});
				});
			},
			collapseSearchChange:function(activeNames){
				var _this=this;
				setTimeout(function(){
					_this.tableHeight+=$(".body-inner").height()-$(".container").outerHeight(true);
				},500);
			},
			addUser:function(){
				this.editingUser={
						id:null,
						userName:null,
						loginName:null,
						password:null,
						status:"1"
				}
				this.showEditDialog=true;
			},
			saveUser:function(user){
				var _this = this;
				this.$refs.editForm.validate(function(isPass){
					if(isPass){
						var isLoginNameOk = true;
						var url = "";
						if(user.id){
							url="user/searchUserDataByCodeUpd.do?loginName="+user.loginName+"&id="+user.id;
						}else{
							url="user/searchUserDataByCode.do?loginName="+user.loginName;
						}
						
						$.ajax({
							type:"GET",
							url:url,
							async:false,
							dataType:"json",
							success:function(data){
								if(data.totalCount!=0){
									isLoginNameOk=false;
								}
							}
						});
						
						if(!isLoginNameOk){
							_this.$alert("该用户账号已经存在，请使用新值！","提示",{
								type:"error"
							});
							return false;
						}
						
						if(user.id){
							var updateData={
									"id_update":_this.editingUser.id,
									"userName_update":_this.editingUser.userName,
									"loginName_update":_this.editingUser.loginName,
									"password_update":_this.editingUser.password,
									"status_update":_this.editingUser.status,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"userName":_this.dataSearch.userName,
									"loginName":_this.dataSearch.loginName
							}
							$.ajax({
								type:"GET",
								url:"user/updateUserDataById.do",
								data:updateData,
								dataType:"json",
								success:function(data){
									if(data.result=='000000'){
										_this.dataUser = data.resultList;
										_this.total = data.totalCount;
										_this.showEditDialog=false;
									}else{
										_this.$message.error(data.message);
									}
								}
							});
						}else{
							var createData={
									"userName_create":_this.editingUser.userName,
									"loginName_create":_this.editingUser.loginName,
									"password_create":_this.editingUser.password,
									"status_create":_this.editingUser.status,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"userName":_this.dataSearch.userName,
									"loginName":_this.dataSearch.loginName
							}
							$.ajax({
								type:"GET",
								url:"user/createUserData.do",
								data:createData,
								dataType:"json",
								success:function(data){
									if(data.result=='000000'){
										_this.dataUser = data.resultList;
										_this.total = data.totalCount;
										_this.showEditDialog=false;
									}else{
										_this.$message.error(data.message);
									}
									
								}
							});
						}
					}
				});
			}
		},
		mounted:function(){
			var _this = this;
			this.$nextTick(function(){
				_this.tableHeight+=$(".body-inner").height()-$(".container").outerHeight(true);
				_this.search();
			});
		}
	}
});