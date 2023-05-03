let currentPage = 1;
$.ajaxSetup({
    cache: false
})
/**
 * 对子页面进行刷新 项目中多处复用！
 * @param url
 * @param page
 */
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

/**
 * 直接将接口写入home页按钮中的value属性即可
 * 填充子页面 项目中多处复用！
 */
$(function () {
    $("button").click(function () {
        currentPage = 1;
        changePage($(this).attr("value"), 1);
    });
});