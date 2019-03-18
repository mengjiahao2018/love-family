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
            isMenuCollapse: false,
            mainMenuIndex: '0',
            menus: [],
            todoTaskCount: 0,
            myTaskCount: 0,
            getUserFuns: []
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
