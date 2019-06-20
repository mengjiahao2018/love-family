define(function(){
	return{
		data:function(){
			return {
				dataSearch:{
					menuName:''
				},
				tableHeight:250,
				dataMenu:[],
				addingMenu:{},
				pageSize:10,
				currentPage:1,
				total:0,
				menuMap:{},
				menuTreeData:[],
				menuTreeProps:{
						children:"children",
						label:"label"
				},
				rules:{
					label:[{required:true,message:"菜单名称不能为空！",trigger:"change"}],
					code:[{required:true,message:"功能名称不能为空！",trigger:"change"}],
				},
				functions:[],
				showAddDialog:false,
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
						"functionName":this.dataSearch.functionName,
						"functionUrl":this.dataSearch.functionUrl
				};
				$.ajax({
					type:"GET",
					url:"function/queryAll.do",
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
					url:"function/searchFunctionDataById.do",
					data:{"id":func.id},
					dataType:"json",
					success:function(data){
						_this.editingFunction = data;
						_this.showEditDialog=true;
					}
				});
			},
			deleteMenu:function(func){
				var _this = this;
				this.$confirm('确认删除此功能？','提示',{
					type:"warning"
				}).then(function(){
					var deleteData = {
							"id":func.id,
							"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
							"functionName":_this.dataSearch.functionName
					}
					$.ajax({
						type:"GET",
						url:"function/deleteFunctionDataById.do",
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
			addMenu:function(){
				this.addingMenu={
						parentId:null,
						parentName:null,
						functionCodes:[],
						menus:[]
				}
				this.showAddDialog=true;
			},
			saveMenu:function(func){
				var _this = this;
				this.$refs.editForm.validate(function(isPass){
					if(isPass){
						if(func.id){
							var updateData={
									"id_update":_this.editingFunction.id,
									"name_update":_this.editingFunction.name,
									"url_update":_this.editingFunction.url,
									"type_update":_this.editingFunction.type,
									"code_update":_this.editingFunction.code,
									"status_update":_this.editingFunction.status,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"functionName":_this.dataSearch.functionName
							}
							$.ajax({
								type:"GET",
								url:"function/updateFunctionDataById.do",
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
						}else{
							var createData={
									"id_create":_this.editingFunction.id,
									"name_create":_this.editingFunction.name,
									"url_create":_this.editingFunction.url,
									"type_create":_this.editingFunction.type,
									"code_create":_this.editingFunction.code,
									"status_create":_this.editingFunction.status,
									"pageRequest":{"page":_this.currentPage,"size":_this.pageSize,"sort":"id"},
									"functionName":_this.dataSearch.functionName
							}
							$.ajax({
								type:"GET",
								url:"function/createFunctionData.do",
								data:createData,
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
					}
				});
			},
			onClickMenuNode:function(){
				
			},
			functionCodesChange:function(){
				
			},
			blur:function(e){
				e.target.blur();
			}
		},
		mounted:function(){
			var _this = this;
			this.$nextTick(function(){
				_this.tableHeight+=$(".body-inner").height()-$(".container").outerHeight(true);
				
				//初始化下拉菜单
				$.ajax({
					type:"GET",
					url:"function/queryAllFatherMenu.do",
					data:{},
					dataType:"json",
					success:function(data){
						if(data.result=='000000'){
							var menuTreeData = [];
							for(var i =0;i<data.length;i++){
								var menu = {id:data[i].id,label:data[i].label};
								if(data[i].hasSub=="Y"){
									menu.children=[];
								}
								if(data[i].fid){
									_this.menuMap[data[i].fid].children.push(menu);
								}else{
									menuTreeData.push(menu);
								}
								_this.menuMap[menu.id]=menu;
							}
							_this.menuTreeData=menuTreeData;
						}else{
							_this.$message.error(data.message);
						}
					}
				});
				
				$.ajax({
					type:"GET",
					url:"function/queryAllMenuFunction.do",
					data:{},
					dataType:"json",
					success:function(data){
						if(data.result=='000000'){
							_this.functions=data;
						}else{
							_this.$message.error(data.message);
						}
					}
				});
			});
		}
	}
});