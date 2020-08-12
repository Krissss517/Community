/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    comment2Target(questionId, 1, content);
}

function comment2Target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "POST",//请求方式
        url: "/comment",//请求路径
        contentType: "application/json",
        data: JSON.stringify(//需要传输的数据
            {
                "parentId": targetId,
                "content": content,
                "type": type
            }
        ),
        //JQuery的回调函数
        success: function (response) {
            if (response.code == 200) {
                //回复成功的话，刷新页面
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccept = confirm(response.message);
                    if (isAccept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=b5c58369cbc85cb2e5f1&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message());
                }


            }
        },
        //期待返回值类型
        dataType: "json"

    });

}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2Target(commentId, 2, content);
}



/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            //获取当前评论下的所有二级评论，id为该评论的唯一标识
            $.getJSON("/comment/" + id, function (data) {
                //循环展示二级评论，data为/comment/{id}接口返回的CommentDto集合
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}


/**
 * 将标签写入到文本框
 */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }


}

function showSelectTag() {

    $("#select-tag").show();

}