const isRedirect = /*[[${isRedirect}]]*/ "0";
$(document).ready(function () {
    window.setTimeout(function () {
        if (isRedirect === "1") {
            alert("请先登陆，然后再继续操作！");
        }
    }, 500)
    $('#login-form').submit(function (event) {
        event.preventDefault(); // 阻止表单默认提交行为
        const email = $('#email').val();
        const password = $('#password').val();
        $.ajax({
            url: 'login_verification',
            type: "POST",
            data: JSON.stringify({email: email, password: password}),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (res) {
                if (res === 'login_success') {
                    window.location.href = 'home';
                } else if (res === 'email_not_exist') {
                    alert("邮箱不存在！");
                } else if (res === 'password_incorrect') {
                    alert("密码错误！");
                }
            },
            error: function () {
                alert('服务器错误，请稍后再试！');
            }
        })
    })
});