//定义路由,初始化时为空,用户访问时按需加载组件
var router = new VueRouter({
    routes: []
});

//按需获取页面模板文件,包括对应路径的html和js
router.beforeEach(function (to, from, next) {
    if (to.path != '/' && to.matched.length == 0) {
    	var pagePath = to.path;
    	var pagePath = pagePath.indexOf('/_rd_')>=0 ? pagePath.substr(0, pagePath.indexOf('/_')) : pagePath;
        $.ajax('.' + pagePath + '.html', {
            type: "get",
            success: function (data) {
                require(['.' + pagePath], function (vueObj) {
                    var component = {template: data};
                    $.extend(component, vueObj);
                    router.addRoutes([{
                        path: pagePath,
                        component: component
                    }]);
                    next(pagePath);
                }, function (e) {
                    console.log('无法获取访问路径' + to.fullPath + '中对应的js文件:' + pagePath + '.js');
                    next(false);
                });
            },
            error: function (XMLHttpRequest) {
                console.log('无法获取访问路径' + to.fullPath + '中对应的页面文件:' + pagePath + '.html');
                next(false);
            }
        });
    }
    else {
        next();
    }
});

//定义vue对象,并挂载到app元素
new Vue({
    router: router,
    data: function() {
        return {
        	eventHub: new Vue(),
            loading: false,
            loadingCount: 0,
            bodyHeight: 500,
            bodyInnerHeight: 350,
            initBodyLeftWidth: 200,
            bodyLeftWidth: 200,
            layout: function () {
                this.bodyHeight = window.innerHeight-$(".header").outerHeight(true);
                this.bodyInnerHeight = this.bodyHeight-64;//$(".submenu-container").outerHeight(true);
            },
            user: {},
            mainMenuIndex: '0',
            menus: [],
            getUserFuns: [],
            sysName:'系统后台管理系统',
            collapsed:false,
            structureMenuArr:[],
            isMenuCollapse:false
        };
    },
    methods: {
        //获取路径对应的菜单索引数组
        findMenuIndexPathByPath: function(menus, path, currentIndexPath) {
    		currentIndexPath = currentIndexPath || [];
    		for(var i=0; i<menus.length; i++) {
				currentIndexPath.push(i);
    			if(menus[i].demoMenus) {
            		var indexPath = this.findMenuIndexPathByPath(menus[i].demoMenus, path, currentIndexPath);
            		if(indexPath) {
            			return indexPath;
            		}
    			}
    			else {
            		var menuItemPath = menus[i].code.substr(0, menus[i].code.lastIndexOf('.html'));
                	if(menuItemPath==path) {
                		return currentIndexPath;
                	}
    			}
    			currentIndexPath.pop();
    		}
    		return null;
        },
        getUser: function(callback) {
        	if(this.user.usercode) {
        		callback(this.user);
        	} else {
        		this.getUserFuns.push(callback);
        	}
        },
        changeRole:function(){
        	top.location.href = "role-select.html";
        },
        logout:function(){
        	top.location.href="j_spring_security_logout";
        },
        selectMenuItem:function(index,indexPath){
        	var menu = this.menus[this.mainMenuIndex];
        	for(var i =0;i<indexPath.length;i++){
        		for(var j=0;j<menu.demoMenus.length;j++){
        			if(menu.demoMenus[j].id==indexPath[i]){
        				menu=menu.demoMenus[j];
        				break;
        			}
        		}
        	}
        	//动态构建页面导航菜单
        	this.navigationMenu(menu);
        	var path = menu.code.substr(0,menu.code.lastIndexOf('.html'));
        	router.push(path);
        },
        navigationMenu:function(menu){
        	this.menuArr=[];
        	var sortStructureMenuArr=[];
        	this.getMenuArray(menu,this.menus[this.mainMenuIndex]);
        	if(this.menuArr.length>0){
        		for(var i=0;i<this.menuArr.length;i++){
        			var j = this.menuArr.length-1-i;
        			var singMenu = {id:i,name:this.menuArr[j]};
        			sortStructureMenuArr.push(singMenu);
        		}
        	}
        	this.structureMenuArr=sortStructureMenuArr;
        	console.log(this.structureMenuArr);
        },
        getMenuArray:function(menu,mainMenu){
        	var _this = this;
        	var menuParentId = menu.parentId;//父级菜单
        	if(menuParentId == mainMenu.id){
        		_this.menuArr.push(menu.label);
        		_this.menuArr.push(mainMenu.label);
        	}else{
        		if(mainMenu.demoMenus!=null){
        			var flag =false;
        			for(var i =0;i<mainMenu.demoMenus.length;i++){
        				var sonMenu = mainMenu.demoMenus[i];
        				if(menuParentId==sonMenu.id){
        					_this.menuArr.push(menu.label);
        	        		_this.menuArr.push(sonMenu.label);
        	        		flag = true;
        	        		break;
        				}else{
        					flag = false;
        				}
        			}
        			if(flag){
        				_this.menuArr.push(mainMenu.label);
        			}else{
            			for(var i =0;i<mainMenu.demoMenus.length;i++){
            				var sonMenu = mainMenu.demoMenus[i];
            				_this.getMenuArray(menu,sonMenu);
            			}
        			}
        		}
        	}
        },
        selectMainMenu:function(index){
        	this.mainMenuIndex = index;
        	if(this.mainMenuIndex==0){
        		router.push("/templates/mainPage/mainPage");
        	}else{
        		this.$nextTick(function(){
        			$(".submenu-container .el-menu-item").first().trigger("click");
        		});
        	}
        },
        collapseMenu:function(){
        	var menu = $(this.$refs.mainMenu.$el);
        	if(this.isMenuCollapse){
        		this.isMenuCollapse=false;
        		menu.removeClass("el-menu--collapse");
        		this.bodyLeftWidth=this.initBodyLeftWidth;
        	}else{
        		this.isMenuCollapse=true;
        		menu.addClass("el-menu--collapse");
        		this.bodyLeftWidth=menu.width()+10;
        	}
        },
        loadMenu:function(){
        	var _this = this;
        	$.ajax({
        		type:"POST",
        		url:"menuManage/queryFirstMenus.action",
        		data:{},
        		dataType:"json",
        		success:function(data){
        			_this.menus = data[1];
        			//焦点设置到对应的菜单项
        			_this.$nextTick(function () {
        	            var urlPath = window.location.hash.substr(1);
        	            if(urlPath){
        	            	var menuIndePath = _this.findMenuIndexPathByPath(_this.menus,urlPath);
        	            	if(menuIndePath&&menuIndePath.length>=2){
        	            		//打开主菜单
        	            		this.mainMenuIndex = menuIndePath[0];
        	            		_this.$nextTick(function () {
        	            			var submenu = $(".submenu-container").children(".el-menu").children("li").eq(menuIndePath[1]);
        	            			if(menuIndePath.length==2){
        	            				submenu.trigger("click");
        	            			}else if(menuIndePath.length==3){
        	            				submenu.children(".el-menu").children("li").eq(menuIndePath[2]).trigger("click");
        	            			}
        	            	    });        	            		
        	            	}else{
        	            		if("0SHOUYE"==_this.menus[0].code||"/templates/mainPage/mainPage.html"==_this.menus[0].code){
        	            			router.push("/templates/mainPage/mainPage");
        	            		}else{
        	            			$(".menu-container .el-menu-item").first().trigger("click");
        	            			_this.nextTick(function(){
            	            			$(".submenu-container .el-menu-item").first().trigger("click");
        	            			});
        	            		}
        	            	}
        	            }
        	        });
        		}
        	});
        }
    },
    created: function () {
    	var _this = this;
    	//设置全局行为
    	$.ajaxSetup({
    		cache: false,
    		beforeSend: function() {
    			if(!this.hideMask) {
        			_this.loadingCount++;
        			if(_this.loadingCount>0) {
        				_this.loading = true;
        			}
    			}
    		},
    		complete: function() {
    			if(!this.hideMask) {
        			_this.loadingCount--;
        			if(_this.loadingCount<=0) {
        				_this.loading = false;
        			}
    			}
    		}
    	});
    	
    	$.ajax({
    		type:"POST",
    		url:"getUserinfo.do",
    		data:{},
    		dataType:"json",
    		success:function(data){
    			_this.user = data;
    			for(var i = 0;i<_this.getUserFuns;i++){
    				_this.getUserFuns[i](_this.user);
    			}
    		}
    	});
    	//加载菜单
    	this.loadMenu();
    },
    mounted: function () {
        this.$nextTick(function () {
            //自适应窗口大小调整布局
            var _this = this;
            _this.layout();
            $(window).resize(function () {
                _this.layout();
            });
        });
    }
}).$mount('#app');
