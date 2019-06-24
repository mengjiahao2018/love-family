define(function(){
	return{
		data:function(){
			return {
				dataSearch:{
					roleName:'',
					functionName:''
				},
				tableHeight:250,
				dataRoelFunction:[],
				addingRoleFunction:{},
				pageSize:10,
				currentPage:1,
				total:0,
				rules:{
					name:[{required:true,message:"角色名称不能为空！",trigger:"change"}],
					code:[{required:true,message:"角色代码不能为空！",trigger:"change"}],
				},
				showAddDialog:false,
				roles:[],
				functions:[]
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
						"functionName":this.dataSearch.functionName
				};
				$.ajax({
					type:"GET",
					url:"roleFunction/queryAllRoleFunction.do",
					data:searchData,
					dataType:"json",
					success:function(data){
						_this.dataRoelFunction = data.resultList;
						_this.total = data.totalCount;
					}
				});
			},		
			deleteRoleFunction:function(roleFunction){
				var _this = this;
				this.$confirm('确认删除此角色功能关联？','提示',{
					type:"warning"
				}).then(function(){
					var deleteData = {
							"id":roleFunction.id,
							"functionId":roleFunction.functionId,
							"roleId":roleFunction.roleId,
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"roleName":_this.dataSearch.roleName,
							"functionName":_this.dataSearch.functionName
					}
					$.ajax({
						type:"GET",
						url:"roleFunction/deleteRoleFunctionDataById.do",
						data:deleteData,
						dataType:"json",
						success:function(data){
							if(data.result=='000000'){
								_this.dataRoelFunction = data.resultList;
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
			addRoleFunction:function(){
				this.addingRoleFunction={
						roleId:'',
						roleCode:'',
						functionIds:[],
						functions:[]
				}
				this.showAddDialog=true;
			},
			saveRoleFunction:function(role){
				var _this = this;
				if(this.addingRoleFunction.functions.length==0){
					this.$alert("请至少选择一个功能！","提示",{
						type:"error"
					});
					return false;
				}
				
				var functionIds = "";
				for(var i =0 ;i<this.addingRoleFunction.functions.length;i++){
					functionIds+=this.addingRoleFunction.functions[i].id+",";
				}
				
				functionIds = functionIds.substr(0,functionIds.length-1);
				var createData={
						"roleId_create":_this.addingRoleFunction.roleId,
						"functionId_create":functionIds,
						"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
						"functionName":_this.dataSearch.functionName,
						"roleName":_this.dataSearch.roleName
				}
				$.ajax({
					type:"GET",
					url:"roleFunction/createRoleFunctionData.do",
					data:createData,
					dataType:"json",
					success:function(data){
						if(data.result=='000000'){
							_this.dataRoelFunction = data.resultList;
							_this.total = data.totalCount;
							_this.showAddDialog=false;
						}else{
							_this.$message.error(data.message);
						}
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
					url:"function/queryAllFunctionHelpFunction.do",
					data:{},
					dataType:"json",
					success:function(data){
						for(var i=0;i<data.length;i++){
							data[i].id = ''+data[i].id;
						}
						_this.functions = data;
					}
				});
				_this.search();
			});
		}
	}
});