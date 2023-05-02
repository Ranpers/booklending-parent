$(".user-delete").click(function () {
    $.ajax({
        url: "delete/" + $(this).attr("data-id"),
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            if (res === 'delete_success') {
                changePage("/booklending/" + $(".container-fluid").attr("data-role") + "/list/", currentPage);
            }
        }
    })
});
$(".user-edit").click(function () {
    let id = $(this).attr("data-id");
    let role = $(this).attr("data-role");
    let url1;
    if (role === "0") {
        url1 = "/booklending/reader/one/" + id;
    } else if (role === "1") {
        url1 = "/booklending/employee/one/" + id;
    }
    $.ajax({
        url: url1,
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#username').attr("value", data.username);
            $('#phone').attr("value", data.phone);
            $('#email').attr("value", data.email);
            $('#birthday').attr("value", data.birthday);
            if (data.gender === "女") {
                $('#gender_man').removeAttr("checked");
                $('#gender_woman').attr("checked", "checked");
            }
        },
        error: function () {
            alert("服务器走丢了，请稍后~");
        }
    })
});