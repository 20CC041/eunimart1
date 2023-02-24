function setFormMessage(formElement)
document.addEventListener("DOMContentLoaded",()=> {
       const LoginForm = document.querySelevtor("#login");
       const createAccountForm = document.querySelector("#createAccount");
       document.querySelector("#linkCreateAccount".addEventListener("click",e => {
             e.preventDefault();
             LoginForm.classList.add("form--hidden");
             createAccountForm.classList.remove("form--hidden");
        })
        document.querySelector("#linkLogin".addEventListener("click",e => {
            e.PreventDefault();
             LoginForm.classList.add("form--hidden");
             createAccountForm.classList.remove("form--hidden");
        })
    });