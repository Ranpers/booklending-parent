//点击删除按钮后 调用删除接口 若删除成功对当前子页面进行重新加载
$(".book-delete").click(function () {
    $.ajax({
        url: "delete/" + $(this).attr("data-id"),
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            if (res === 'delete_success') {
                changePage("/booklending/book" + "/list/", currentPage);
            }
        }
    })
});
$(".book-status").click(function () {
    const id = $(this).attr("data-id");
    const status = $(this).attr("data-status");
    $.ajax({
        url: "/booklending/book/status/" + id + "/" + status,
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            if (res === 'update_success') {
                changePage("/booklending/book/list/", currentPage);
            } else if (res === "no_permissions") {
                $(window).location.href = '/booklending/permission/no_permissions';
            }
        },
        error: function () {
            alert("服务器走丢了，请稍候~");
        }
    })
})
//点击编辑按钮后从后端获取数据 向模态框进行填充
$(".book-edit").click(function () {
    const id = $(this).attr("data-id");
    $.ajax({
        url: "/booklending/book/one/" + id,
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#bookname').val(data.bookname);
            $('#press').val(data.press);
            $('#author').val(data.author);
            $('#price').val(data.price.toFixed(2)); // 将price格式化保留两位小数
            $('#book-update').attr("data-id", data.id);
            $('#bookEditModal').modal('show');
        },
        error: function () {
            alert("服务器走丢了，请稍候~");
        }
    })
});

$("#book-update").click(function () {
    const id = $(this).attr("data-id");
    let bookname = $('#bookname').val();
    let press = $('#press').val();
    let author = $('#author').val();
    let price = $('#price').val();
    $.ajax({
        url: "/booklending/book/one/update",
        type: 'POST',
        data: JSON.stringify({id: id, bookname: bookname, press: press, author: author, price: price}),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            if (res === 'update_success') {
                setTimeout(function () {
                    changePage("/booklending/book/list/", currentPage);
                }, 500);
            } else if (res === "no_permissions") {
                $(window).location.href = '/booklending/permission/no_permissions';
            }
        },
        error: function () {
            alert('服务器错误，请稍后再试！');
        }
    })
});