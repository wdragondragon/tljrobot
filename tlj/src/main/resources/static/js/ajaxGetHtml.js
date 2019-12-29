function instead(insteadUrl,insteadDom) {
    $.ajax({
        type: 'get',
        contentType: 'application/json;charset=utf-8',
        url: insteadUrl,
        dataType: 'text',
        success: function (data) {
            $(insteadDom).html(data);
        },
        error: function (err) {
            alert(JSON.stringify(err));
        }
    });
}
function insteadSafe(insteadUrl,insteadDom) {
    $.ajax({
        type: 'get',
        contentType: 'application/json;charset=utf-8',
        url: insteadUrl+'/'+sessionStorage.sessionId,
        dataType: 'text',
        success: function (data) {
            $(insteadDom).html(data);
        },
        error: function (err) {
            alert(JSON.stringify(err));
        }
    });
}
function getHttp(proFixUrl) {
    $(location).prop('href',proFixUrl+'/'+sessionStorage.sessionId);
}
function isLogin() {
    if(sessionStorage.sessionId==null||sessionStorage.sessionId=="")return false;
    else return true;
}
