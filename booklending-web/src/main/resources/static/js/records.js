$('.book-return').click(function () {
    const recordId = $(this).attr("data-recordid");
    $.ajax({
        url: "/booklending/record/return/" + recordId,
        type: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            if (res === 'update_success') {
                changePage("/booklending/record/personal_not_returned/", currentPage);
                setTimeout(function (){
                    alert("请前往柜台进行还书审批~");
                }, 200);
            } else if (res === "no_permissions") {
                $(window).location.href = '/booklending/permission/no_permissions';
            }
        },
        error: function () {
            alert("服务器走丢了，请稍候~");
        }
    })
})