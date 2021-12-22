var moveToMainFromEmpty= function() {
    var serviceInstanceId = sessionStorage.getItem("serviceInstanceId");

    if(serviceInstanceId != null) {
        location.href = '/?serviceInstanceId='+ serviceInstanceId;
        return false;
    }
    location.href= '/';
    return false;
}

