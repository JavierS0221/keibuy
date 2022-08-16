var btnHide = document.getElementById("btn-visibility-language-hide");
var btnShow = document.getElementById("btn-visibility-language-show");

var btnLang = document.getElementById("btn-dropdown-lang");

function toggleVisibilityLang() {
    if(btnHide.style.visibility == "hidden") {
        btnHide.style.visibility = "visible";
        btnShow.style.visibility = "hidden";
        btnLang.style.visibility = "visible";
    } else {
        btnHide.style.visibility = "hidden";
        btnShow.style.visibility = "visible";
        btnLang.style.visibility = "hidden";
    }
}