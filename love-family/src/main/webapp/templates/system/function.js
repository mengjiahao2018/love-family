define(function(){
	return{
		data:function(){
			return {
				dataSearch:{
					functionName:'',
					functionUrl:''
				},
				tableHeight:250,
				dataFunction:[],
				fieldMaps:{status:[{value:'1',label:'有效'},{value:'0',label:'无效'}]},
				pageSize:10,
				currentPage:1,
				total:0,
				editingFunction:{},
				rules:{
					name:[{required:true,message:"功能名称不能为空！",trigger:"change"}],
					url:[{required:true,message:"链接地址不能为空！",trigger:"change"}],
					code:[{required:true,message:"功能代码不能为空！",trigger:"change"}],
					type:[{required:true,message:"链接类型不能为空！",trigger:"change"}],
					status:[{required:true,message:"状态位不能为空！",trigger:"change"}],
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
						"functionName":this.dataSearch.functionName,
						"functionUrl":this.dataSearch.functionUrl
				};
				$.ajax({
					type:"GET",
					url:"function/queryAll.do",
					data:searchData,
					dataType:"json",
					success:function(data){
						_this.dataFunction = data.resultList;
						_this.total = data.totalCount;
					}
				});
			},
			editFunction:function(func){
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
			deleteFunction:function(func){
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
								_this.dataFunction = data.resultList;
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
			addFunction:function(){
				this.editingFunction={
						id:null,
						name:null,
						url:null,
						code:null,
						type:"MENU",
						status:"1"
				}
				this.showEditDialog=true;
			},
			saveFunction:function(func){
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
										_this.dataFunction = data.resultList;
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
										_this.dataFunction = data.resultList;
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