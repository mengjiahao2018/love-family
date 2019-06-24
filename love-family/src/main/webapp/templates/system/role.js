define(function(){
	return{
		data:function(){
			return {
				dataSearch:{
					roleName:''
				},
				tableHeight:250,
				dataRoel:[],
				editingRole:{},
				pageSize:10,
				currentPage:1,
				total:0,
				rules:{
					name:[{required:true,message:"角色名称不能为空！",trigger:"change"}],
					code:[{required:true,message:"角色代码不能为空！",trigger:"change"}],
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
						"roleName":this.dataSearch.roleName,
				};
				$.ajax({
					type:"GET",
					url:"roleManage/queryAllRole.do",
					data:searchData,
					dataType:"json",
					success:function(data){
						_this.dataRoel = data.resultList;
						_this.total = data.totalCount;
					}
				});
			},
			editRole:function(role){
				var _this = this;
				$.ajax({
					type:"GET",
					url:"roleManage/searchRoleDataById.do",
					data:{"id":role.id},
					dataType:"json",
					success:function(data){
						_this.editingRole = data;
						_this.showEditDialog = true;
					}
				});
			},			
			deleteRole:function(role){
				var _this = this;
				this.$confirm('确认删除此角色？','提示',{
					type:"warning"
				}).then(function(){
					var deleteData = {
							"id":role.id,
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"roleName":_this.dataSearch.roleName
					}
					$.ajax({
						type:"GET",
						url:"roleManage/deleteRoleDataById.do",
						data:deleteData,
						dataType:"json",
						success:function(data){
							if(data.result=='000000'){
								_this.dataRoel = data.resultList;
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
			addRole:function(){
				this.editingRole={
						id:null,
						name:'',
						code:''
				}
				this.showEditDialog=true;
			},
			saveRole:function(role){
				var _this = this;
				this.$refs.editForm.validate(function(isPass){
					if(isPass){
						var isRoleCodeOk = true;
						var url = "";
						if(role.id){
							url="roleManage/searchRoleDataByCodeUpd.do?roleCode="+role.code+"&id="+role.id;
						}else{
							url="roleManage/searchRoleDataByCode.do?roleCode="+role.code;
						}
						
						$.ajax({
							type:"GET",
							url:url,
							async:false,
							dataType:"json",
							success:function(data){
								if(data.totalCount!=0){
									isRoleCodeOk=false;
								}
							}
						});
						
						if(!isRoleCodeOk){
							_this.$alert("该角色编码已经存在，请使用新值！","提示",{
								type:"error"
							});
							return false;
						}
						
						if(role.id){
							var updateData={
									"id_update":_this.editingRole.id,
									"name_update":_this.editingRole.name,
									"code_update":_this.editingRole.code,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"roleName":_this.dataSearch.roleName
							}
							$.ajax({
								type:"GET",
								url:"roleManage/updateRoleDataById.do",
								data:updateData,
								dataType:"json",
								success:function(data){
									if(data.result=='000000'){
										_this.dataRoel = data.resultList;
										_this.total = data.totalCount;
										_this.showEditDialog=false;
									}else{
										_this.$message.error(data.message);
									}
								}
							});
						}else{
							var createData={
									"id_create":_this.editingRole.id,
									"name_create":_this.editingRole.name,
									"code_create":_this.editingRole.code,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"roleName":_this.dataSearch.roleName
							}
							$.ajax({
								type:"GET",
								url:"roleManage/createRoleData.do",
								data:createData,
								dataType:"json",
								success:function(data){
									if(data.result=='000000'){
										_this.dataRoel = data.resultList;
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