let currentPage = 1;
function changePage(url, page) {
    // 发送AJAX请求获取数据
    $.ajax({
        url: url + page, // 后台接口地址
        success: function (data) {
            // 将数据渲染到页面
            $("#pageContent").html(data);
        }
    });
}

$(function () {
    $("button").click(function () {
        currentPage = 1;
        changePage($(this).attr("value"), 1);
    });
});