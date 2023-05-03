//点击删除按钮后 调用删除接口 若删除成功对当前子页面进行重新加载
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

//点击编辑按钮后从后端获取数据 向模态框进行填充
$(".user-edit").click(function () {
    const id = $(this).attr("data-id");
    const role = $(this).attr("data-role");
    let url;
    if (role === "0") {
        url = "/booklending/reader/one/" + id;
    } else if (role === "1") {
        url = "/booklending/employee/one/" + id;
    }
    $.ajax({
        url: url,
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#username').val(data.username);
            $('#phone').val(data.phone);
            $('#email').val(data.email);
            $('#birthday')[0].valueAsDate = new Date(data.birthday);
            $('#user-update').attr("data-id", data.id);
            if (data.gender === "女") {
                $('#gender_man').removeAttr("checked");
                $('#gender_woman').attr("checked", "checked");
            }
            $('#userEditModal').modal('show');
        },
        error: function () {
            alert("服务器走丢了，请稍候~");
        }
    })
});

$("#user-update").click(function () {
    const id =  $('#user-update').attr("data-id");
    const role = $(".container-fluid").attr("data-role");
    let gender;
    let radio = document.getElementsByName("gender");
    for (let i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            gender = radio[i].value;
        }
    }
    let username = $('#username').val();
    let phone = $('#phone').val();
    let birthday = $('#birthday')[0].valueAsDate.toLocaleDateString();
    $.ajax({
        url: "/booklending/" + role + "/one/update",
        type: 'POST',
        data: JSON.stringify({id: id, username: username, gender: gender, phone: phone, birthday: birthday}),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            if (res === 'update_success') {
                setTimeout(function () {
                    changePage("/booklending/" + role + "/list/", currentPage)
                }, 500);
            } else if (res === "no_permissions") {
                $(window)[0].location.href = '/booklending/permission/no_permissions'
            }
        },
        error: function () {
            alert('服务器错误，请稍后再试！');
        }
    })
});