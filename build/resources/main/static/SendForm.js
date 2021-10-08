function send() {
    let object = {
        "code": document.getElementById("code_snippet").value,
        "time": document.getElementById("time_restriction").value,
        "views": document.getElementById("views_restriction").value
    };

    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);

//    document
//      .getElementById("code_snippet_search")
//      .addEventListener("submit", function(e) {
//        e.preventDefault();
//        var id = document.getElementById("code_snippet_id_search").value
//        window.location.href = "/code/" + id;
//      });
//
//    if (xhr.status == 200) {
//      document.getElementById("code_snippet_id").innerText = "Your Code Snippet id is " + JSON.parse(xhr.response).id;
//    }
}