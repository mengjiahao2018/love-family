define(function(){
	return{
		data:function(){
			return {
				dataSearch:{
					userName:'',
					roleName:''
				},
				tableHeight:250,
				dataUserRole:[],
				addingUserRole:{},
				pageSize:10,
				currentPage:1,
				total:0,
				rules:{
					userName:[{required:true,message:"用户名称不能为空！",trigger:"change"}],
					roleName:[{required:true,message:"角色名称不能为空！",trigger:"change"}],
				},
				showAddDialog:false,
				roles:[],
				users:[]
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
						"roleName":this.dataSearch.roleName,
						"userName":this.dataSearch.userName
				};
				$.ajax({
					type:"GET",
					url:"userRole/queryAll.do",
					data:searchData,
					dataType:"json",
					success:function(data){
						_this.dataUserRole = data.resultList;
						_this.total = data.totalCount;
					}
				});
			},		
			deleteUserRole:function(userRole){
				var _this = this;
				this.$confirm('确认删除此用户角色？','提示',{
					type:"warning"
				}).then(function(){
					var deleteData = {
							"id":userRole.id,
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"userName":_this.dataSearch.userName,
							"roleName":_this.dataSearch.roleName
					}
					$.ajax({
						type:"GET",
						url:"userRole/deleteUserRoleDataById.do",
						data:deleteData,
						dataType:"json",
						success:function(data){
							if(data.result=='000000'){
								_this.dataUserRole = data.resultList;
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
			addUserRole:function(){
				this.addingUserRole={
						userId:'',
						userName:'',
						loginName:'',
						roleId:'',
						roleName:'',
						roleCode:''
				}
				this.showAddDialog=true;
			},
			saveUserRole:function(){
				var _this = this;
				this.$refs.editForm.validate(function(isPass){
					if(isPass){
						var isCheckOk = true;
						$.ajax({
							type:"GET",
							url:"userRole/searchUserRoleDataByUserIdAndRoleId.do?userId="+_this.addingUserRole.userId+"&roleId="+_this.addingUserRole.roleId,
							async:false,
							dataType:"json",
							success:function(data){
								if(data.totalCount!=0){
									isCheckOk=false;
								}
							}
						});
						
						if(!isCheckOk){
							_this.$alert("该用户角色关系已经存在，请重新选择！","提示",{
								type:"error"
							});
							return false;
						}
						

						var createData={
								"userId_create":_this.addingUserRole.userId,
								"roleId_create":_this.addingUserRole.roleId,
								"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
								"userName":_this.dataSearch.userName,
								"loginName":_this.dataSearch.loginName
						}
						$.ajax({
							type:"GET",
							url:"userRole/createUserRoleData.do",
							data:createData,
							dataType:"json",
							success:function(data){
								if(data.result=='000000'){
									_this.dataUserRole = data.resultList;
									_this.total = data.totalCount;
									_this.showAddDialog=false;
								}else{
									_this.$message.error(data.message);
								}
								
							}
						});
					
					}
				});	
			},
			roleIdChange:function(roleId){
				this.addingRoleFunction.functionIds =[];
				this.addingRoleFunction.functions =[];
			},
			functionIdsChange:function(functionIds){
				if(functionIds&&functionIds.length>0){
					var isRoleFunctionOk = true;
					$.ajax({
						type:"GET",
						url:"roleFunction/searchRoleFunctionDataByRoleIdAndFunctionId.do?roleId="+this.addingRoleFunction.roleId+"&functionId="+functionIds[functionIds.length-1],
						dataType:"json",
						async:false,
						success:function(data){
							if(data.totalCount!=0){
								isRoleFunctionOk = false;
							}
						}
					});
					if(!isRoleFunctionOk){
						this.$alert("该角色功能关联已经存在！","提示",{
							type:"error"
						});
						functionIds.pop();
						return false;
					}
				}
				var funcs = [] ;
				for(var i =0;i<this.functions.length;i++){
					if(functionIds.indexOf(this.functions[i].id)>=0){
						funcs.push(this.functions[i]);
					}
				}
				this.addingRoleFunction.functions = funcs;
			}
		},
		mounted:function(){
			var _this = this;
			this.$nextTick(function(){
				_this.tableHeight+=$(".body-inner").height()-$(".container").outerHeight(true);
				$.ajax({
					type:"GET",
					url:"roleManage/queryAllRoleHelpRoleFunction.do",
					data:{},
					dataType:"json",
					success:function(data){
						for(var i=0;i<data.length;i++){
							data[i].id = ''+data[i].id;
						}
						_this.roles = data;
					}
				});
				$.ajax({
					type:"GET",
					url:"user/queryAllUserHelpUserRole.do",
					data:{},
					dataType:"json",
					success:function(data){
						for(var i=0;i<data.length;i++){
							data[i].id = ''+data[i].id;
						}
						_this.users = data;
					}
				});
				_this.search();
			});
		}
	}
});