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
					url:"menuManage/queryAllRole.do",
					data:searchData,
					dataType:"json",
					success:function(data){
						_this.dataMenu = data.resultList;
						_this.total = data.totalCount;
					}
				});
			},
			editMenu:function(func){
				var _this = this;
				$.ajax({
					type:"GET",
					url:"menuManage/searchMenuDataById.do",
					data:{"id":func.id},
					dataType:"json",
					success:function(data){
						if(data.parentId){
							data.parentName = _this.menuMap[data.parentId].label;
						}
						_this.editingMenu = data;
						_this.showEditDialog = true;
					}
				});
			},			
			deleteMenu:function(func){
				var _this = this;
				this.$confirm('确认删除此 菜单？','提示',{
					type:"warning"
				}).then(function(){
					var deleteData = {
							"id":func.id,
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"menuName":_this.dataSearch.menuName
					}
					$.ajax({
						type:"GET",
						url:"menuManage/deleteMenuDataById.do",
						data:deleteData,
						dataType:"json",
						success:function(data){
							if(data.result=='000000'){
								_this.dataMenu = data.resultList;
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
			saveMenu:function(func){
				var _this = this;
				if(this.showAddDialog){
					if(_this.addingMenu.menus.length==0){
						_this.$alert("请至少选择一个功能","提示",{
							type:"error"
						});
						return false;
					}
					for(var i=0;i<this.addingMenu.menus.length;i++){
						if(!this.addingMenu.menus[i].label_create){
							_this.$alert("存在菜单名称为空","提示",{
								type:"error"
							});
						}
					}
					var createData ={
							"parentId_create":this.addingMenu.parentId,
							"dataRequest":JSON.stringify(this.addingMenu.menus),
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"menuName":_this.dataSearch.menuName
					}
					$.ajax({
						type:"GET",
						url:"menuManage/createMenuData.do",
						data:createData,
						dataType:"json",
						success:function(data){
							if(data.result=='000000'){
								_this.dataMenu = data.resultList;
								_this.total = data.totalCount;
								_this.showAddDialog=false;
							}else{
								_this.$message.error(data.message);
							}
						}
					});
				}else if(this.showEditDialog){
					this.$refs.editForm.validate(function(isPass){
						if(isPass){
							var updateData={
									"id_update":_this.editingMenu.id,
									"parentId_update":_this.editingMenu.parentId,
									"code_update":_this.editingMenu.code,
									"icon_update":_this.editingMenu.icon,
									"label_update":_this.editingMenu.label,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"menuName":_this.dataSearch.menuName
							}
							$.ajax({
								type:"GET",
								url:"menuManage/updateMenuDataById.do",
								data:updateData,
								dataType:"json",
								success:function(data){
									if(data.result=='000000'){
										_this.dataMenu = data.resultList;
										_this.total = data.totalCount;
										_this.showEditDialog=false;
									}else{
										_this.$message.error(data.message);
									}
								}
							});
						}
					});
				}
			},
			onClickMenuNode:function(menu,code,component){
				component.tree.$parent.$parent.hide();
				if(this.showAddDialog){
					this.addingMenu.parentId = menu.id;
					this.addingMenu.parentName = menu.label;
				}else{
					this.editingMenu.parentId = menu.id;
					this.editingMenu.parentName = menu.label;
				}
			},
			functionCodesChange:function(functionCodes){
				var menus = [];
				for(var i =0;i<this.functions.length;i++){
					if(functionCodes.indexOf(this.functions[i].id)>=0){
						menus.push({
							functionName : this.functions[i].name,
							icon_create :'',
							label_create:this.functions[i].name,
							code_create:this.functions[i].id
						});
					}
				}
				this.addingMenu.menus = menus;
			},
			blur:function(e){
				e.target.blur();
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