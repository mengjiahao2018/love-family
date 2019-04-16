/**
 * 封装组件
 */

//路由dialog组件
Vue.component("router-dialog", {
    data: function () {
        return {
            routerDialogVisible: false,
            parentPath: ""
        };
    },
    watch: {
        visible: function () {
            return this.routerDialogVisible = this.visible;
        }
    },
    props: ['path', 'query', 'title', 'visible', 'size', 'modal', 'bodyHeight'],
    methods: {
        onOpen: function () {
            var _this = this;
            this.parentPath = this.$route.fullPath;
            var fullPath = this.parentPath + "/_rd_" + this.path.replace("/templates/", "").replaceAll("/", "-");
            var routerQuery = this.query ? cloneObj(this.query) : {};
            if (this.$router.match(fullPath).matched.length > 0) {
                this.$router.push({path: fullPath, query: routerQuery});
            }
            else {
                $.ajax('.' + _this.path + '.html', {
                    type: "get",
                    success: function (data) {
                        require(['.' + _this.path], function (vueObj) {
                            var component = {template: data};
                            $.extend(component, vueObj);
                            _this.$router.addRoutes([{
                                path: fullPath,
                                component: component,
                                parentPath: _this.parentPath
                            }]);
                            _this.$router.push({path: fullPath, query: routerQuery});
                        }, function (e) {
                            console.log('无法获取访问路径' + _this.path + '中对应的js文件:' + _this.path + '.js');
                        });
                    },
                    error: function (XMLHttpRequest) {
                        console.log('无法获取访问路径' + _this.path + '中对应的页面文件:' + _this.path + '.html');
                    }
                });
            }
            //自定义高度
            if (this.bodyHeight) {
                this.$nextTick(function () {
                    $(_this.$el).find(".el-dialog__body").height(parseInt(_this.bodyHeight));
                });
            }
        },
        onClose: function () {
            this.$emit('update:visible', this.routerDialogVisible);
            this.$router.push(this.parentPath);
        }
    },
    template: '<el-dialog class="router-dialog" :title="title" :visible.sync="routerDialogVisible" @open="onOpen" @close="onClose" :size="size" :modal="modal">' +
    '    <router-view></router-view>' +
    '    <slot name="footer"></slot>' +
    '</el-dialog>'
});