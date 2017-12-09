var url = "http://localhost:8080/findAllLiveChat";      //获取所有聊天记录url
var saveMessage = "http://localhost:8080/saveLiveChat"; //提交聊天信息url
var dateFormat = "yyyy-MM-dd hh:mm:ss";                //日期显示格式

/**
 * 从后台获取全部聊天记录，渲染数据到html
 */
var vm_1 = new Vue({
    el: '#message',
    data: {data: ""},
    created: function () {
        this.$http.get(url).then(function (data) {
            var json = data.body;
            this.data = convertInsertTime(json.resultValue._rows_);
        }, function (response) {
            console.info(response);
        })
    }
});

/**
 * 绑定发送按钮事件
 */
var vm_2 = new Vue({
    el: '#submit',
    data: {liveChat: {}},
    methods: {
        send: function () {
            if (!this.liveChat.userName) {
                alert("用户名不能为空！")
                return false;
            }
            if (!this.liveChat.content) {
                alert("聊天内容不能为空！")
                return false;
            }
            this.liveChat.insertTime = new Date();
            //保存后，向页面数据源添加数据，让发送消息立即生效，id为0
            scrollTop();
            vm_1.data.push({
                userName: this.liveChat.userName,
                content: this.liveChat.content,
                id: 0,
                insertTime: this.liveChat.insertTime.Format(dateFormat),
                roomId: 1
            });
            this.liveChat.roomId = 1;
            setTimeout(function(){scrollTop()},20);
            sendPost(saveMessage, this.liveChat);
            //避免每次输入用户，只是聊天内容清空
            this.liveChat.content = "";
        }
    }
});

/**
 * ajax 发送post请求，
 * @param url
 * @param data 请求参数 {}
 * @returns {Array}
 */
function sendPost(url, data) {
    var rows = [];
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        async: false,
        dataType: 'json',
        success: function (data) {
            if (data.errorCode == 0 && data.resultValue && data.resultValue._rows_) {
                rows = data.resultValue._rows_;
            }
        }
    });
    return rows;
}

/**
 * 让聊天记录框的滚动条移动到最底部
 */
function scrollTop() {
    var div = document.getElementById('message');
    div.scrollTop = div.scrollHeight;
}


$(document).ready(function () {
    getNewData();
    self.setInterval("getNewData()", 2000); //页面每隔 2 秒获取一次新的聊天内容
    scrollTop();
});

/**
 * 获取新的聊天记录
 * @returns {boolean}
 */
function getNewData() {
    if (!vm_1.data) {
        return false;
    }
    var obj = vm_1.data[vm_1.data.length - 1];
    if (!obj) {
        return false;
    }
    var maxid = obj.id;
    for (var i = vm_1.data.length - 1; i >= 0; i--) {//取出最大的真实的ID
        if (vm_1.data[i].id != 0) {
            maxid = vm_1.data[i].id;
            break;
        }
    }
    var rows = sendPost(url, {maxid: maxid});
    rows = convertInsertTime(rows);
    $.each(rows, function (k, v) {
        //如果当前最大id是0，且属于同一个roomId,同一个用户，相同的时间，认为是同一信息，
        // 不在向数据源添加
        if ((obj.id == 0 || obj.id == v.id) && obj.roomId == v.roomId
            && obj.userName == v.userName) {
            obj.id = v.id;
            vm_1.data.pop();
        }
        vm_1.data.push(v)
        scrollTop();
    });
}

/**
 * 日期类型增加属性方法，格式化日期
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 将JSON数组对象，格式化日期为
 * @param jsonRows
 * @returns {*}
 */
function convertInsertTime(jsonRows) {
    if (!jsonRows || jsonRows.length == 0) {
        return [];
    }
    $.each(jsonRows, function (k, v) {
        var insertdate = new Date();
        insertdate.setTime(v.insertTime);
        v.insertTime = insertdate.Format(dateFormat)
    });
    return jsonRows;
}