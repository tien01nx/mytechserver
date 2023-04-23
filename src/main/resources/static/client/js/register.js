const showErrorStyle = (container, inputBox , spanBox) => {
    container.classList.add("error");
    inputBox.classList.add("error");
    spanBox.classList.add("error");
    container.classList.remove("allow");
    inputBox.classList.remove("allow");
}

const removeErrorStyle = (container, inputBox , spanBox) => {
    container.classList.add("error");
    inputBox.classList.add("error");
    spanBox.classList.add("error");
    container.classList.remove("allow");
    inputBox.classList.remove("allow");
}

const fullnameValidatting = () => {
    const fullnameContainer = document.querySelector(".fullname_box");
    const fullnameInputBox = document.querySelector("#fullname_register_err");
    const fullnameSpanBox = document.querySelector(".fullname_message");

    let fullname = fullnameInputBox.value;

    if(fullname === "") {
        showErrorStyle(fullnameContainer,fullnameInputBox,fullnameSpanBox);
        fullnameSpanBox.innerHTML = "Hãy điền tên tài khoản" ;
    }
    else {
        removeErrorStyle(fullnameContainer,fullnameInputBox,fullnameSpanBox);
        fullnameSpanBox.innerHTML = "";
    }
}
const emailValidating = () => {
    const emailContainer = document.querySelector(".email_box");
    const emailInputBox = document.querySelector("#email_register_err");
    const emailSpanBox = document.querySelector(".email_message");

    let emailRegex = /^\w+@[a-zA-Z]{3,}\.com$/i;
    let email = emailInputBox.value;

    if(email === "") {
        showErrorStyle(emailContainer,emailInputBox,emailSpanBox);
        emailSpanBox.innerHTML = "Hãy điền email" ;
    }
    else if(emailRegex.test(email) == false) {
        showErrorStyle(emailContainer,emailInputBox,emailSpanBox);
        emailSpanBox.innerHTML = "Email không đúng định dạng" ;
    }
    else {
        removeErrorStyle(emailContainer, emailInputBox, emailSpanBox);
        emailSpanBox.innerHTML = "";
    }
}
const passValidating = () => {
    const passContainer = document.querySelector(".password_box");
    const passInputBox = document.querySelector("#password_register_err");
    const passSpanBox = document.querySelector(".password_message");

    let password = passInputBox.value;

    if(password === "") {
        showErrorStyle(passContainer, passInputBox, passSpanBox);
        passSpanBox.innerHTML = "Hãy điền mật khẩu";
    }
    else {
        removeErrorStyle(passContainer, passInputBox, passSpanBox);
        passSpanBox.innerHTML = "";
    }
}
function Register() {
    let name = $('#fullname_register_err').val();
    let email = $('#email_register_err').val();
    let password = $('#password_register_err').val();

    if (name !== "" &&  email !== "" && password !== "") {
        req = {
            name: name,
            email: email,
            password: password
        }
        let myJSON = JSON.stringify(req);
        $.ajax({
            url: '/api/auth/register',
            type: 'POST',
            data: myJSON,
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                alert(data);
                location.href = "/admin/categories";
            },
            error: function(data) {
                console.log(data)
            }
        });
    }
}